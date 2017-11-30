package com.ttmaps.maps;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashMap;

import static com.ttmaps.maps.MainActivity.POIList;
import static com.ttmaps.maps.MyApplication.db;

public class MapsActivity extends AppCompatActivity implements GoogleMap.OnMarkerClickListener,GoogleMap.OnInfoWindowClickListener,GoogleMap.InfoWindowAdapter,OnMapReadyCallback {

    private GoogleMap mMap;

    //Hard Coded locations
    private static final LatLng price_Center = new LatLng(32.879616,-117.236317);
    private static final LatLng center_Hall = new LatLng( 32.878035, -117.237351);
    private static final LatLng geisel_Library = new LatLng( 32.880915, -117.237562);
    private static final LatLng warren_LecHall = new LatLng (32.880700, -117.234406);
    private static final LatLng ssc = new LatLng(32.878938, -117.235783);

    private Marker PC;
    private Marker CH;
    private Marker GL;
    private Marker WLH;
    private Marker SSC;

    private ArrayList<String> r;
    private HashMap<String, Marker> hash;
    private Polyline polyline;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Bundle bundle = getIntent().getExtras();
        hash = new HashMap<String, Marker>();
        if (bundle != null) {
            r = bundle.getStringArrayList("result");
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Setting Map Boundaries
        final LatLngBounds ucsdBounds = new LatLngBounds
                (new LatLng(32.870850, -117.243257), new LatLng(
                        32.890570, -117.228039));
        mMap.setLatLngBoundsForCameraTarget(ucsdBounds);

        //Starting point located outside PC
        LatLng startPoint = new LatLng(32.87913,-117.236207);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startPoint,17));

        //Setting Max/Min Camera Zoom
        mMap.setMinZoomPreference(14.5f);
        mMap.setMaxZoomPreference(20.0f);

        // Setting up POI markers

        for(POI poi: POIList.values())
        {
            if(!(poi.getLatLng().latitude == 0 || poi.getLatLng().longitude == 0)) {
                Marker m = mMap.addMarker(new MarkerOptions().position(poi.getLatLng()).title(poi.getName()));
                m.setTag(0);
            }
        }

        if (r != null && r.size() != 0) {
            String result = r.remove(r.size()-1);
            PolylineOptions lineOptions = new PolylineOptions();
            ArrayList<LatLng> points = new ArrayList<LatLng>();
            for (int i = 0; i < r.size(); i++) {
                if(!(POIList.get(r.get(i)).getLatLng().latitude == 0 ||
                        POIList.get(r.get(i)).getLatLng().longitude == 0)) {
                    points.add(POIList.get(r.get(i)).getLatLng());
                }
                else{
                    points.clear();
                    Intent intent;
                    intent = new Intent(this, Result.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("result", result);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }
            }
            if (points.size() >= 2) {
                lineOptions.addAll(points);
                lineOptions.width(10);
                lineOptions.color(Color.RED);
                polyline = mMap.addPolyline(lineOptions);
            }
        }

        //Info Window
        mMap.setInfoWindowAdapter(this);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
    }

    // Will take the user to info screen about poi. Include description and a "Go" Button
    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent intent = new Intent(this, poi_info_window.class);
        intent.putExtra("POI_Name", marker.getTitle());
        intent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
        startActivity(intent);
        finish();
    }

    // Info window that pops up when marker is tapped. Includes picture and name of POI
    @Override
    public View getInfoWindow(Marker marker) {
        View view = getLayoutInflater().inflate(R.layout.map_info_window,null,false);
        TextView location = view.findViewById(R.id.poi_text);
        location.setText(marker.getTitle());
        return view;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }


}

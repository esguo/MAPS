package com.ttmaps.maps;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

public class MapsActivity extends AppCompatActivity implements GoogleMap.OnMarkerClickListener,GoogleMap.OnInfoWindowClickListener,GoogleMap.InfoWindowAdapter,OnMapReadyCallback {

    private GoogleMap mMap;

    //Hard Coded locations
    private static final LatLng price_Center = new LatLng(32.879616,-117.236317);
    private static final LatLng center_Hall = new LatLng( 32.878035, -117.237351);
    private static final LatLng geisel_Library = new LatLng( 32.880915, -117.237562);
    private static final LatLng warren_LecHall = new LatLng (32.880700, -117.234406);

    private Marker PC;
    private Marker CH;
    private Marker GL;
    private Marker WLH;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
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
        PC = mMap.addMarker(new MarkerOptions()
                .position(price_Center)
                .title("Price Center"));
        PC.setTag(0);

        CH = mMap.addMarker(new MarkerOptions()
                .position(center_Hall)
                .title("Center Hall"));
        CH.setTag(0);

        GL = mMap.addMarker(new MarkerOptions()
                .position(geisel_Library)
                .title("Geisel Library"));
        GL.setTag(0);

        WLH = mMap.addMarker(new MarkerOptions()
                .position(warren_LecHall)
                .title("Warren Lecture Hall"));
        WLH.setTag(0);

        //Info Window
        mMap.setInfoWindowAdapter(this);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
    }

    // Will take the user to info screen about poi. Include description and a "Go" Button
    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(this, "Actual: Will take you to another screen",
                Toast.LENGTH_SHORT).show();
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

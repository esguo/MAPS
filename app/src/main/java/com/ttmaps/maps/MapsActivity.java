package com.ttmaps.maps;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.ttmaps.maps.MainActivity.POIList;
import static com.ttmaps.maps.MyApplication.db;

public class MapsActivity extends AppCompatActivity implements GoogleMap.OnMarkerClickListener,GoogleMap.OnInfoWindowClickListener,GoogleMap.InfoWindowAdapter,OnMapReadyCallback {

    private static final String TAG = MapsActivity.class.getSimpleName();
    private GoogleMap mMap;
    private CameraPosition mCameraPosition;

    // The entry points to the Places API.
    private GeoDataClient mGeoDataClient;
    private PlaceDetectionClient mPlaceDetectionClient;

    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient mFusedLocationProviderClient;

    // A default location (Geisel) and default zoom to use when location permission is not granted.
    private final LatLng mDefaultLocation = new LatLng(32.880915, -117.237562);
    private static final int DEFAULT_ZOOM = 17;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location mLastKnownLocation;

    // Keys for storing activity state.
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";


    // Used for selecting the current place.
    private static final int M_MAX_ENTRIES = 5;
    private String[] mLikelyPlaceNames;
    private String[] mLikelyPlaceAddresses;
    private String[] mLikelyPlaceAttributions;
    private LatLng[] mLikelyPlaceLatLngs;
/*
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
*/

    private ArrayList<String> r;
    private HashMap<String, Marker> hash;
    private Polyline polyline;

    ArrayList<String> filteredPOIs;
    Spinner dropdown;
    //final DBHandler db = new DBHandler(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set a default mLastKnownLocation
        if (mLastKnownLocation == null) {
            mLastKnownLocation = new Location("geisel");
            mLastKnownLocation.setLatitude(32.880915);
            mLastKnownLocation.setLongitude(-117.237562);
        }

        // Retrieve location and camera position from saved instance state.
        if (savedInstanceState != null) {
            mLastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

        setContentView(R.layout.activity_maps);

        // Construct a GeoDataClient, a PlaceDetectionClient, and a FusedLocationProviderClient
        mGeoDataClient = Places.getGeoDataClient(this, null);
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        dropdown = (Spinner)findViewById(R.id.spinner1);
        Bundle bundle = getIntent().getExtras();
        hash = new HashMap<String, Marker>();
        if (bundle != null && bundle.size() != 0) {
            r = bundle.getStringArrayList("result");
            dropdown.setEnabled(false);
        }

        filteredPOIs = new ArrayList<>();
        String[] items = new String[]{"No filters selected", "Admin", "Classroom", "Food", "Parking", "Recreation", "Residential Hall", "Study Area"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter1);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected (AdapterView < ? > parent, View v,int position, long id){

                switch (position) {
                    case 0:
                        boolean[] flags = {false, false, false, false, false, false, false};
                        filteredPOIs = getFilteredPOIs(flags);
                        Log.d("FILTER POI: ", "CASE 0");
                        for (String s : filteredPOIs) {
                            Log.d("FILTER POI: ", s);
                        }
                        break;
                    case 1:
                        // Whatever you want to happen when the second item gets selected
                        boolean[] flags1 = {true, false, false, false, false, false, false};
                        filteredPOIs = getFilteredPOIs(flags1);
                        Log.d("FILTER POI: ", "CASE 1");
                        for (String s : filteredPOIs) {
                            Log.d("FILTER POI: ", s);
                        }
                        break;
                    case 2:
                        boolean[] flags2 = {false, true, false, false, false, false, false};
                        filteredPOIs = getFilteredPOIs(flags2);
                        Log.d("FILTER POI: ", "CASE 2");
                        for (String s : filteredPOIs) {
                            Log.d("FILTER POI: ", s);
                        }
                        break;
                    case 3:
                        boolean[] flags3 = {false, false, true, false, false, false, false};
                        filteredPOIs = getFilteredPOIs(flags3);
                        Log.d("FILTER POI: ", "CASE 3");
                        for (String s : filteredPOIs) {
                            Log.d("FILTER POI: ", s);
                        }
                        break;
                    case 4:
                        boolean[] flags4 = {false, false, false, true, false, false, false};
                        filteredPOIs = getFilteredPOIs(flags4);
                        Log.d("FILTER POI: ", "CASE 4");
                        for (String s : filteredPOIs) {
                            Log.d("FILTER POI: ", s);
                        }
                        break;
                    case 5:
                        boolean[] flags5 = {false, false, false, false, true, false, false};
                        filteredPOIs = getFilteredPOIs(flags5);
                        Log.d("FILTER POI: ", "CASE 5");
                        for (String s : filteredPOIs) {
                            Log.d("FILTER POI: ", s);
                        }
                        break;
                    case 6:
                        boolean[] flags6 = {false, false, false, false, false, true, false};
                        filteredPOIs = getFilteredPOIs(flags6);
                        Log.d("FILTER POI: ", "CASE 6");
                        for (String s : filteredPOIs) {
                            Log.d("FILTER POI: ", s);
                        }
                        break;
                    case 7:
                        boolean[] flags7 = {false, false, false, false, false, false, true};
                        filteredPOIs = getFilteredPOIs(flags7);
                        Log.d("FILTER POI: ", "CASE 7");
                        for (String s : filteredPOIs) {
                            Log.d("FILTER POI: ", s);
                        }
                        break;

                }
                ArrayAdapter<String> listWithFilter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.select_dialog_singlechoice, filteredPOIs);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("FILTER POI: ", "CASE NONE");
                boolean[] flags = {false, false, false, false, false, false, false};
                filteredPOIs = getFilteredPOIs(flags);
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Saves the state of the map when the activity is paused.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, mLastKnownLocation);
            super.onSaveInstanceState(outState);
        }
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

        //Setting Max/Min Camera Zoom
        mMap.setMinZoomPreference(14.5f);
        mMap.setMaxZoomPreference(20.0f);

        //Starting point located at Geisel
        LatLng startPoint = new LatLng(32.880915, -117.237562);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startPoint,17));

        // Setting up POI markers
        for(POI poi: POIList.values())
        {
            if(!(poi.getLatLng().latitude == 0 || poi.getLatLng().longitude == 0)) {
                Marker m = mMap.addMarker(new MarkerOptions().position(poi.getLatLng()).title(poi.getName()));
                m.setTag(0);
                hash.put(poi.getName(), m);
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
                }
            }
            if (points.size() >= 2) {
                lineOptions.addAll(points);
                lineOptions.width(10);
                lineOptions.color(Color.RED);
                polyline = mMap.addPolyline(lineOptions);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(points.get(0),17));
            } else {
                // Prompt the user for permission.
                getLocationPermission();
                // Turn on the My Location layer and the related control on the map.
                updateLocationUI();
                // Get the current location of the device and set the position of the map.
                getDeviceLocation();
            }
        } else {
            // Prompt the user for permission.
            getLocationPermission();
            // Turn on the My Location layer and the related control on the map.
            updateLocationUI();
            // Get the current location of the device and set the position of the map.
            getDeviceLocation();
        }

        //Info Window
        mMap.setInfoWindowAdapter(this);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);


    }

    // Gets the current location of the device, and positions the map's camera.
    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = task.getResult();
                            //set a default mLastKnownLocation
                            if (mLastKnownLocation == null) {
                                mLastKnownLocation = new Location("geisel");
                                mLastKnownLocation.setLatitude(32.880915);
                                mLastKnownLocation.setLongitude(-117.237562);
                            }
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    // Prompts the user for permission to use the device location.
    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    //Handles the result of the request for location permissions.
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }

    // Prompts the user to select the current place from a list of likely places, and shows the
    // current place on the map - provided the user has granted location permission.
    private void showCurrentPlace() {
        if (mMap == null) {
            return;
        }

        if (mLocationPermissionGranted) {
            // Get the likely places - that is, the businesses and other points of interest that
            // are the best match for the device's current location.
            @SuppressWarnings("MissingPermission") final
            Task<PlaceLikelihoodBufferResponse> placeResult =
                    mPlaceDetectionClient.getCurrentPlace(null);
            placeResult.addOnCompleteListener
                    (new OnCompleteListener<PlaceLikelihoodBufferResponse>() {
                        @Override
                        public void onComplete(@NonNull Task<PlaceLikelihoodBufferResponse> task) {
                            if (task.isSuccessful() && task.getResult() != null) {
                                PlaceLikelihoodBufferResponse likelyPlaces = task.getResult();

                                // Set the count, handling cases where less than 5 entries are returned.
                                int count;
                                if (likelyPlaces.getCount() < M_MAX_ENTRIES) {
                                    count = likelyPlaces.getCount();
                                } else {
                                    count = M_MAX_ENTRIES;
                                }

                                int i = 0;
                                mLikelyPlaceNames = new String[count];
                                mLikelyPlaceAddresses = new String[count];
                                mLikelyPlaceAttributions = new String[count];
                                mLikelyPlaceLatLngs = new LatLng[count];

                                for (PlaceLikelihood placeLikelihood : likelyPlaces) {
                                    // Build a list of likely places to show the user.
                                    mLikelyPlaceNames[i] = (String) placeLikelihood.getPlace().getName();
                                    mLikelyPlaceAddresses[i] = (String) placeLikelihood.getPlace()
                                            .getAddress();
                                    mLikelyPlaceAttributions[i] = (String) placeLikelihood.getPlace()
                                            .getAttributions();
                                    mLikelyPlaceLatLngs[i] = placeLikelihood.getPlace().getLatLng();

                                    i++;
                                    if (i > (count - 1)) {
                                        break;
                                    }
                                }

                                // Release the place likelihood buffer, to avoid memory leaks.
                                likelyPlaces.release();

                                // Show a dialog offering the user the list of likely places, and add a
                                // marker at the selected place. ()
                                // openPlacesDialog();

                            } else {
                                Log.e(TAG, "Exception: %s", task.getException());
                            }
                        }
                    });
        } else {
            // The user has not granted permission.
            Log.i(TAG, "The user did not grant location permission.");

            // Add a default marker, because the user hasn't selected a place.
            mMap.addMarker(new MarkerOptions()
                    .title(getString(R.string.default_info_title))
                    .position(mDefaultLocation)
                    .snippet(getString(R.string.default_info_snippet)));

            // Prompt the user for permission.
            getLocationPermission();
        }
    }

    //Updates the map's UI settings based on whether the user has granted location permission.
    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
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

    public ArrayList<String> getFilteredPOIs(boolean[] filterList){
        ArrayList<String> canUse = new ArrayList<>();
        ArrayList<String> remove = new ArrayList<>();
        //List<POI> allPOI = db.getPOIs();

        Log.d("ALL POI SIZE",POIList.keySet().size() + "" );
        for(POI poi: POIList.values())
        {
            canUse.add(poi.getName());
        }

        Log.d("FILTER LIST1",filterList[0] + "" );
        //Log.d("ALL POI SIZe",allPOI.size() + "" );
        for(POI poi: POIList.values()){
            if(filterList[0]) {
                if (!poi.getIsAdmin()){

                    remove.add(poi.getName());
                    continue;
                }
            }
            if(filterList[1]){
                if (!poi.getIsClassroom()){
                    remove.add(poi.getName());
                    continue;
                }
            }
            if(filterList[2]){
                if (!poi.getIsFood()){
                    remove.add(poi.getName());
                    continue;
                }
            }
            if(filterList[3]){
                if (!poi.getIsParking()){
                    remove.add(poi.getName());
                    continue;
                }
            }
            if(filterList[4]){
                if (!poi.getIsRec()){
                    remove.add(poi.getName());
                    continue;
                }
            }
            if(filterList[5]){
                if (!poi.getIsResHall()){
                    remove.add(poi.getName());
                    continue;
                }
            }
            if(filterList[6]){
                if (!poi.getIsStudyArea()){
                    remove.add(poi.getName());
                    continue;
                }
            }
        }
        String s1 = "" + canUse.size();
        Log.d("SIZE: ", s1);
        for(int i = 0; i < remove.size(); i++){
            canUse.remove(remove.get(i));
        }
        String s = "" + canUse.size();
        Log.d("SIZE: ", s);
        for (int i = 0; i < canUse.size(); i++) {
            if (hash.get(canUse.get(i)) != null) {
                hash.get(canUse.get(i)).setVisible(true);
            }
        }
        for (int i = 0; i < remove.size(); i++) {
            if (hash.get(remove.get(i)) != null) {
                hash.get(remove.get(i)).setVisible(false);
            }
        }
        return canUse;
    }

}

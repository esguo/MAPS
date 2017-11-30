package com.ttmaps.maps;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static String TAG = MainActivity.class.getSimpleName();
    ListView mDrawerList;
    RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();

    private AutoCompleteTextView loc_input1;
    private AutoCompleteTextView loc_input2;
    private Button btn_submit;
    private Button rating_btn_submit;

    private CheckBox safeRoute;
    private CheckBox wellLit;
    private CheckBox wheelChair;

    int rate = 0;
    int set_dest = 1;
    final DBHandler db = new DBHandler(this);
    String[] data;
    static ArrayList<String> POIs;
    static HashMap<String, POI> POIList;
    ArrayList<String> filteredPOIs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loc_input1 = (AutoCompleteTextView) findViewById(R.id.input);
        loc_input2 = (AutoCompleteTextView) findViewById(R.id.input2);
        btn_submit = (Button) findViewById(R.id.psearchSubmit);
        safeRoute = (CheckBox) findViewById(R.id.checkBox);
        wellLit = (CheckBox) findViewById(R.id.checkBox2);
        wheelChair = (CheckBox) findViewById(R.id.checkBox3);

        //final DBHandler db = new DBHandler(this);
        POIs = new ArrayList<>();
        POIList = new HashMap<>();
        readFromFile(db);

        /*creates list of POIs to choose from */
        for(POI poi: db.getPOIs()) {
            POIs.add(poi.getName());
            POIList.put(poi.getName(), poi);
        }



        Log.d("HERE", "HERE");
        ArrayAdapter<String> list = new ArrayAdapter<>(this, android.R.layout.select_dialog_singlechoice, POIs);
        ArrayAdapter<String> listWithFilters = new ArrayAdapter<>(this, android.R.layout.select_dialog_singlechoice, POIs);
        String s3 = "" + listWithFilters.getCount();
        Log.d("SIZE OF AUTO: ", s3);
        //db.updateDb();
        /* sets dropdown autocomplete feature */
        loc_input1.setThreshold(1);
        loc_input2.setThreshold(1);
        loc_input1.setAdapter(list);
        loc_input2.setAdapter(listWithFilters);

        filteredPOIs = new ArrayList<>();
        Spinner dropdown = (Spinner)findViewById(R.id.spinner1);
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
                loc_input2.setAdapter(listWithFilter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("FILTER POI: ", "CASE NONE");
                boolean[] flags = {false, false, false, false, false, false, false};
                filteredPOIs = getFilteredPOIs(flags);
            }
        });



        mNavItems.add(new NavItem("Map", "View map",R.drawable.ic_action_map));
        mNavItems.add(new NavItem("Search", "Find a path", R.drawable.ic_action_path));
        mNavItems.add(new NavItem("Ratings", "View path ratings", R.drawable.ic_action_ratings));
        mNavItems.add(new NavItem("Favorites", "View saved paths", R.drawable.ic_action_favorites));
        mNavItems.add(new NavItem("Find a Room", "View available rooms", R.drawable.ic_action_rooms));
        mNavItems.add(new NavItem("Preferences", "Change your settings",R.drawable.ic_action_settings));
        mNavItems.add(new NavItem("About", "Our team", R.drawable.ic_action_about));

        // DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        // Populate the Navigation Drawer with options
        mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
        mDrawerList = (ListView) findViewById(R.id.navList);
        DrawerListAdapter adapter = new DrawerListAdapter(this, mNavItems);
        mDrawerList.setAdapter(adapter);

        // Drawer Item click listeners
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItemFromDrawer(position);
            }
        });

        String poi1 = db.getAllPOIs();
        Log.d("INFO OF ALL POIS: ", poi1);
//        int poi3 = db.getRating(0);
//        Log.d("CURRENT RATING IS: ", String.valueOf(poi3));
       // int rating = db.updatePOI(0, "Warren", 5);
//        String poi2 = db.getPOI(0);
//        Log.d("NEW RATING IS: ", String.valueOf(rating));
//        Log.d("INFO ON UPDATED POI: ", poi2);
//        int rating1 = db.updatePOI(0, "Warren", 3);
        /*
        String poi1 = db.getAllPOIs();
        Log.d("INFO OF ALL POIS: ", poi1);
        int poi3 = db.getRating(0);
        Log.d("CURRENT RATING IS: ", String.valueOf(poi3));
        int rating = db.updatePOI(0, "Warren", 5);
        String poi2 = db.getPOI(0);
        Log.d("NEW RATING IS: ", String.valueOf(rating));
        Log.d("INFO ON UPDATED POI: ", poi2);
        int rating1 = db.updatePOI(0, "Warren", 3);
        */
        /* testing database stuff*/
        /*Log.d("Reading: ", "Reading all POIs...");
        POI poi1 = db.getPOI(1);
        POI poi2 = db.getPOIByName("Muir");
        String log = "Id: " + poi1.getId() + ", Name: " + poi1.getName();
        Log.d("POI: ", log);
        String log1 = "Count of POIs: " + db.getPOIsCount();
        Log.d("POI Count: ", log1);
        db.deletePOI(poi1);
        String log2 = "Count of POIs after deleting: " + db.getPOIsCount();
        Log.d("POI Count: ", log2);
        
        List<POI> pois = db.getAllPOIs();
        for (POI poi : pois) {
            String log3 = "Id: " + poi.getId() + ", Name: " + poi.getName();
            Log.d("POI: ", log3);
        }*/

        final Context context = this;
        btn_submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if ((loc_input1.getText().length() > 0) && (loc_input2.getText().length() > 0 )){
                    String loc1 = loc_input1.getText().toString();
                    String loc2 = loc_input2.getText().toString();

                    /**
                    Intent intent;
                    intent = new Intent(context, Result.class);
                    Dijkstra d = new Dijkstra(db);
                    Bundle bundle = new Bundle();
                    boolean[] filter = new boolean[3];
                    filter[0] = safeRoute.isChecked();
                    filter[1] = wellLit.isChecked();
                    filter[2] = wheelChair.isChecked();

                    bundle.putString("result", d.dijkstra(loc1, loc2, filter));
                    intent.putExtras(bundle);
                    startActivity(intent);
                     */
                    Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                    Dijkstra d = new Dijkstra(db);
                    Bundle bundle = new Bundle();
                    boolean[] filter = new boolean[3];
                    filter[0] = safeRoute.isChecked();
                    filter[1] = wellLit.isChecked();
                    filter[2] = wheelChair.isChecked();

                    bundle.putStringArrayList("result", d.dijkstra(loc1, loc2, filter));

                    intent.putExtras(bundle);
                    startActivityForResult(intent, set_dest);
                }
                else{
                    Toast.makeText(MainActivity.this, "Please enter locations in both operand fields", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == rate && resultCode == RESULT_OK && data != null) {
            String num1 = data.getStringExtra("name");
            String num2 = data.getStringExtra("rateNum");
            String num3 = data.getStringExtra("comment");
            Log.d("POI NAME", num1);
            Log.d("RATE NUM ", num2);
            POI poi = db.getPOIByName(num1);
            if (Integer.parseInt(num2) == 0) {
                return;
            }
            db.updatePOI(poi.getId(), poi.getName(), Integer.parseInt(num2), num3);
            //Log.d("OMG JUST WORK ", String.valueOf(db.getAvgRating(poi.getId())));
        }
        if (requestCode == set_dest && resultCode == RESULT_OK && data != null) {
            loc_input2.setText(data.getStringExtra("result"));
        }
    }

    private void readFromFile(DBHandler db) {
    /* read in from excel file (csv) and add to db */
        InputStream inputStream = getResources().openRawResource(R.raw.poilist);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try{
            String csvLine;
            while((csvLine = reader.readLine()) != null){
                data = csvLine.split(",");
                try{
                    boolean[] filters = {Boolean.parseBoolean(data[2]), Boolean.parseBoolean(data[3]), Boolean.parseBoolean(data[4]), Boolean.parseBoolean(data[5]), Boolean.parseBoolean(data[6]), Boolean.parseBoolean(data[7]), Boolean.parseBoolean(data[8])};
                    try{
                        db.populateHash(Integer.parseInt(data[0]), data[1], data[9], filters);
                    }
                    catch(Exception e){
                        db.populateHash(Integer.parseInt(data[0]), data[1], "", filters);
                    }
                }
                catch (Exception e){
                    Log.d("Problem", e.toString());
                }
            }
        }
        catch (IOException ex){
            throw new RuntimeException("Error in reading CSV file: " + ex);
        }

        inputStream = getResources().openRawResource(R.raw.pathslist);
        reader = new BufferedReader(new InputStreamReader(inputStream));
        try{
            String csvLine;
            while((csvLine = reader.readLine()) != null){
                data = csvLine.split(",");
                try{
                    db.createPair(data[0], data[1], data[2], Integer.parseInt(data[3]));

                }
                catch (Exception e){
                    Log.d("Problem1", e.toString());
                    Log.d("Problem1", data[0] + data[1] + data[2] + Integer.parseInt(data[3]) + "");
                }
            }
        }
        catch (IOException ex){
            throw new RuntimeException("Error in reading CSV file: " + ex);
        }
    }

    class NavItem {
        String mTitle;
        String mSubtitle;
        int mIcon;

        public NavItem(String title, String subtitle, int icon){
            mTitle = title;
            mSubtitle = subtitle;
            mIcon = icon;
        }
    }

    class DrawerListAdapter extends BaseAdapter {
        Context mContext;
        ArrayList<NavItem> mNavItems;

        public DrawerListAdapter(Context context, ArrayList<NavItem> navItems) {
            mContext = context;
            mNavItems = navItems;
        }

        @Override
        public int getCount() {
            return mNavItems.size();
        }

        @Override
        public Object getItem(int position){
            return mNavItems.get(position);
        }

        @Override
        public long getItemId(int position){
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.drawer_item, null);
            }
            else {
                view = convertView;
            }

            TextView titleView = (TextView) view.findViewById(R.id.title);
            TextView subtitleView = (TextView) view.findViewById(R.id.subTitle);
            ImageView iconView = (ImageView) view.findViewById(R.id.icon);

            titleView.setText( mNavItems.get(position).mTitle );
            subtitleView.setText( mNavItems.get(position).mSubtitle );
            iconView.setImageResource(mNavItems.get(position).mIcon);

            return view;
        }

    }

    private void selectItemFromDrawer(int position) {

        if (position == 0) {

            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
            startActivityForResult(intent, set_dest);
        }

        if (position == 2) {

            Intent intent = new Intent(MainActivity.this, add_ratings.class);
            startActivityForResult(intent, rate);

            /**
            Fragment fragment = new RatingsFragment();

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.activity_main, fragment);
            transaction.commit();
             */
        }

        mDrawerList.setItemChecked(position, true);
        setTitle(mNavItems.get(position).mTitle);
        Log.d("test", "fragment");

        // Close the drawer
        mDrawerLayout.closeDrawer(mDrawerPane);

    }

    public ArrayList<String> getFilteredPOIs(boolean[] filterList){
        ArrayList<String> canUse = new ArrayList<>();
        ArrayList<String> remove = new ArrayList<>();
        List<POI> allPOI = db.getPOIs();
        Log.d("ALL POI SIZE",allPOI.size() + "" );
        for(int i = 0; i < allPOI.size(); i++){
            canUse.add(allPOI.get(i).getName());
        }
        Log.d("FILTER LIST1",filterList[0] + "" );
        Log.d("ALL POI SIZe",allPOI.size() + "" );
        for(int i = 0; i < allPOI.size(); i++){
            if(filterList[0]) {
                if (!allPOI.get(i).getIsAdmin()){

                    remove.add(allPOI.get(i).getName());
                    continue;
                }
            }
            if(filterList[1]){
                if (!allPOI.get(i).getIsClassroom()){
                    remove.add(allPOI.get(i).getName());
                    continue;
                }
            }
            if(filterList[2]){
                if (!allPOI.get(i).getIsFood()){
                    remove.add(allPOI.get(i).getName());
                    continue;
                }
            }
            if(filterList[3]){
                if (!allPOI.get(i).getIsParking()){
                    remove.add(allPOI.get(i).getName());
                    continue;
                }
            }
            if(filterList[4]){
                if (!allPOI.get(i).getIsRec()){
                    remove.add(allPOI.get(i).getName());
                    continue;
                }
            }
            if(filterList[5]){
                if (!allPOI.get(i).getIsResHall()){
                    remove.add(allPOI.get(i).getName());
                    continue;
                }
            }
            if(filterList[6]){
                if (!allPOI.get(i).getIsStudyArea()){
                    remove.add(allPOI.get(i).getName());
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
        return canUse;
    }

}

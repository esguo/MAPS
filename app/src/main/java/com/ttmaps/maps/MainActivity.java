package com.ttmaps.maps;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import java.util.List;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static String TAG = MainActivity.class.getSimpleName();
    ListView mDrawerList;
    RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();

    private EditText loc_input1;
    private EditText loc_input2;
    private Button btn_submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loc_input1 = (EditText) findViewById(R.id.input);
        loc_input2 = (EditText) findViewById(R.id.input2);
        btn_submit = (Button) findViewById(R.id.button);

        DBHandler db = new DBHandler(this);

        //Log.d("Insert: ","Inserting..");
        db.addPOI(new POI(1, "Warren"));
        db.addPOI(new POI(2, "Muir"));
        db.addPOI(new POI(3, "Revelle"));
        db.addPOI(new POI(4, "Marshall"));

        mNavItems.add(new NavItem("Home", "Find routes",R.drawable.ic_action_home));
        mNavItems.add(new NavItem("Preferences", "Change your settings",R.drawable.ic_action_settings));
        mNavItems.add(new NavItem("About", "Our team", R.drawable.ic_action_about));

        // DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        // Populate the Navigtion Drawer with options
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


                    Intent intent;
                    intent = new Intent(context, Result.class);
                    Dijkstra d = new Dijkstra();
                    Bundle bundle = new Bundle();
                    bundle.putString("result", d.dijkstra(loc1, loc2));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(MainActivity.this, "Please enter locations in both operand fields", Toast.LENGTH_LONG).show();
                }
            }
        });


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
        Fragment fragment = new PreferencesFragment();

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.mainContent, fragment)
                .commit();

        mDrawerList.setItemChecked(position, true);
        setTitle(mNavItems.get(position).mTitle);

        // Close the drawer
        mDrawerLayout.closeDrawer(mDrawerPane);
    }


}

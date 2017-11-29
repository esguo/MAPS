package com.ttmaps.maps;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

//import static com.ttmaps.maps.DBHandler.poilist;
//import static com.ttmaps.maps.MainActivity.POIs;

/**
 * Created by Eddie on 11/28/2017.
 */

public class FloorPlans extends AppCompatActivity{
    private AutoCompleteTextView poiSearch;
    private Button submitQuery;
    private ImageView floorPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_floorplan);
        poiSearch = (AutoCompleteTextView) findViewById(R.id.search_floor_plan_textbox);
        submitQuery = (Button) findViewById(R.id.submit_search_floorplan);
        floorPlan = (ImageView) findViewById(R.id.imageView2);

        DBHandler db = new DBHandler(FloorPlans.this);
        for(POI poi: db.getPOIs()) {
            //POIs.add(poi.getName());
        }
        //ArrayAdapter<String> list = new ArrayAdapter<>(this, android.R.layout.select_dialog_singlechoice, POIs);
        //poiSearch.setAdapter(list);

        submitQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                String file = poiSearch.getText().toString();
                floorPlan.setImageResource(getResources().getIdentifier(file, "drawable", getPackageName()));
            }
        });
    }
}

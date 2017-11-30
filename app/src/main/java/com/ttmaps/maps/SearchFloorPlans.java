package com.ttmaps.maps;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import static com.ttmaps.maps.MainActivity.POIs;

/**
 * Created by Eddie on 11/29/2017.
 */

public class SearchFloorPlans extends AppCompatActivity{
    private AutoCompleteTextView poiSearch;
    private Button submitQuery;
    private ImageView floorPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_floorplan);
        poiSearch = (AutoCompleteTextView) findViewById(R.id.search_floor_plan_textbox);
        submitQuery = (Button) findViewById(R.id.submit_search_floorplan);

        SubsamplingScaleImageView imageView = (SubsamplingScaleImageView)findViewById(R.id.imageView);

        DBHandler db = new DBHandler(SearchFloorPlans.this);
        for(POI poi: db.getPOIs()) {
            //POIs.add(poi.getName());
        }
        ArrayAdapter<String> list = new ArrayAdapter<>(this, android.R.layout.select_dialog_singlechoice, POIs);
        poiSearch.setAdapter(list);

        submitQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                Intent intent = new Intent(SearchFloorPlans.this, FloorPlans.class);
                intent.putExtra("floorPlan", poiSearch.getText().toString());
                startActivity(intent);
            }
        });
    }
}

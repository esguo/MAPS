package com.ttmaps.maps;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import static com.ttmaps.maps.MainActivity.POIList;

/**
 * Created by Eddie on 11/28/2017.
 */

public class FloorPlans extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_floorplan);

        SubsamplingScaleImageView imageView = (SubsamplingScaleImageView)findViewById(R.id.imageView);

        String target = getIntent().getExtras().getString("floorPlan");

        try {
            int id = getResources().getIdentifier(POIList.get(target).getFileName(), "drawable", getPackageName());
            imageView.setImage(ImageSource.resource(id));
        }
        catch (Exception e){
            imageView.setImage(ImageSource.resource(R.drawable.no_plan));
        }
    }
}

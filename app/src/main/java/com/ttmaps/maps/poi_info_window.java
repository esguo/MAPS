package com.ttmaps.maps;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import static com.ttmaps.maps.MainActivity.POIList;

public class poi_info_window extends AppCompatActivity implements View.OnClickListener {

    String target;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poi_info_window);

        Bundle b = getIntent().getExtras();
        target = b.getString("POI_Name");

        Button one = (Button) findViewById(R.id.go_button);
        one.setOnClickListener(this); // calling onClick() method
        ImageButton two = (ImageButton) findViewById(R.id.back_button);
        two.setOnClickListener(this);
        Button three = (Button) findViewById(R.id.floor_plans);
        three.setOnClickListener(this); // calling onClick() method
        TextView POIName = (TextView) findViewById(R.id.poi_name);
        POIName.setText(target);

        /// get image and set to floorplan
        try {
            ImageView floorPlan = (ImageView) findViewById(R.id.loc_pic);
            int id = getResources().getIdentifier(POIList.get(target).getFileName(), "drawable", getPackageName());
            Drawable drawable = getResources().getDrawable(id);
            floorPlan.setImageDrawable(drawable);
        }
        catch (Exception e){

        }
    }

    // Button options on the info screen
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.go_button:
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("result", target);

                setResult(RESULT_OK, intent);
                finish();
                break;

            case R.id.floor_plans:
                Intent intent1 = new Intent(this, FloorPlans.class);
                intent1.putExtra("floorPlan", target);
                startActivity(intent1);
                break;

            case R.id.back_button:
                Intent back = new Intent(this, MapsActivity.class);
                startActivity(back);
                break;

            default:
                break;
        }
    }
}

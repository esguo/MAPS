package com.ttmaps.maps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class poi_info_window extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poi_info_window);

        Button one = (Button) findViewById(R.id.go_button);
        one.setOnClickListener(this); // calling onClick() method
        ImageButton two = (ImageButton) findViewById(R.id.back_button);
        two.setOnClickListener(this);
        Button three = (Button) findViewById(R.id.floor_plans);
        one.setOnClickListener(this); // calling onClick() method
    }

    // Button options on the info screen
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.go_button:
                Toast.makeText(poi_info_window.this, " In progess",
                        Toast.LENGTH_LONG).show();
                break;

            case R.id.floor_plans:
                Toast.makeText(poi_info_window.this, "Working on it",
                        Toast.LENGTH_LONG).show();
                break;

            case R.id.back_button:
                super.onBackPressed();
                break;

            default:
                break;
        }
    }
}

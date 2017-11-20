package com.ttmaps.maps;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.Rating;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

public class add_ratings extends AppCompatActivity {
    private Button rateButton;
    private Button viewRateButton;
    private EditText poiToRate;
    private RatingBar ratingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //db = ((MyApplication)getApplication()).db;
        DBHandler db = new DBHandler(add_ratings.this);
        setContentView(R.layout.activity_add_ratings);
        poiToRate = (EditText) findViewById(R.id.poiToRate);
        ratingBar = (RatingBar) findViewById(R.id.ratePoiBar);
        rateButton = (Button) findViewById(R.id.rateButton);

        rateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                if(poiToRate.getText().length() > 0){
                    final DBHandler db = new DBHandler(add_ratings.this);
                    String poi = poiToRate.getText().toString();
                    POI rateThisPOI = db.getPOIByName(poi);
                    float stars = ratingBar.getRating();
                    Log.d("STAAAAAAAAAAAAAARS: ", String.valueOf(stars));
                    //int rating = db.updatePOI(rateThisPOI.getId(), poi, (int)stars);
                    Log.d("NOOOOOOOOOOOOOOOW: ", String.valueOf(db.getAvgRating(rateThisPOI.getId())));
                    Intent data = new Intent();
                    data.putExtra("name", poi);
                    data.putExtra("rateNum", "" + (int)stars);
                    if(getParent() == null){
                        setResult(RESULT_OK, data);
                    }
                    else{
                        getParent().setResult(RESULT_OK, data);
                    }
                    finish();
                }
            }
        });

        final Context context = this;
        viewRateButton = (Button) findViewById(R.id.viewRateButton);
        viewRateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                if(poiToRate.getText().length() > 0){
                    final DBHandler db = new DBHandler(add_ratings.this);
                    String poi = poiToRate.getText().toString();
                    POI rateThisPOI = db.getPOIByName(poi);
                    int rating = db.getRating(rateThisPOI.getId());
                    Log.d("CURRENT RATING IS: ", String.valueOf(rating));
                    Intent intent;
                    intent = new Intent(context, Result.class);
                    Bundle bundle = new Bundle();
                    if(db.getRatingCount(rateThisPOI.getId()) != 0) {
                        bundle.putString("result", "" + (rating / (db.getRatingCount(rateThisPOI.getId()))));
                    }
                    else{
                        bundle.putString("result", "No ratings yet!");
                    }
                    intent.putExtras(bundle);
                    startActivity(intent);
                    intent.putExtra("name", poi);
                    intent.putExtra("rateNum", "2");
                    finish();
                }

            }
        });
    }
}

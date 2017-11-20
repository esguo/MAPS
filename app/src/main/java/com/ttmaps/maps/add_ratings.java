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

    // well oncreate just defines what happens when u press a button
    // is there a way to NOT have the enter button (set rating) go back to the other page
    // just stay on curr page do it
    //yeahhh
    //actually you cant do that here because I need values to be returned to mainacitivty in order to update the db becuase
    //I can't get the db from in here I tried like all night yesterday and nothing worked because it kept just not updating/
    //i think it made copies etc.
    //yeah java pass by value and ref, but parameters are copied and not reffed
    //I just instantiation a new db bc online said that works but actually it gave me the same result so it worked or no?
    //actually idr but sort of to some extent
    /*
    have a parameter as db, then return the db that you create
    where would i pass in a parameter here tho
     */
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
            //seems you're recreating the database in ONCREATE in the mainactibity
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

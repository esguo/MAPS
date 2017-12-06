package com.ttmaps.maps;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.Rating;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.ttmaps.maps.MainActivity.POIList;
import static com.ttmaps.maps.MainActivity.list;

public class add_ratings extends AppCompatActivity {
    private Button rateButton;
    private Button viewRateButton;
    private AutoCompleteTextView poiToRate;
    private RatingBar ratingBar;
    private EditText comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //db = ((MyApplication)getApplication()).db;
        DBHandler db = new DBHandler(add_ratings.this);
        setContentView(R.layout.activity_add_ratings);
        poiToRate = (AutoCompleteTextView) findViewById(R.id.poiToRate);
        ratingBar = (RatingBar) findViewById(R.id.ratePoiBar);
        rateButton = (Button) findViewById(R.id.rateButton);
        comment = (EditText) findViewById(R.id.comment);

        poiToRate.setThreshold(1);
        poiToRate.setAdapter(list);

        rateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                if(POIList.containsKey(poiToRate.getText().length())){
                    final DBHandler db = new DBHandler(add_ratings.this);
                    String poi = poiToRate.getText().toString().toLowerCase();
                    POI rateThisPOI = db.getPOIByName(poi);
                    float stars = ratingBar.getRating();

                    Intent data = new Intent();
                    if(comment.getText().length() > 0){
                        String com = comment.getText().toString();
                        data.putExtra("comment", com);
                    }
                    else{
                        data.putExtra("comment", "");
                    }
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
                else{
                    Toast.makeText(add_ratings.this, "Please enter valid POI location", Toast.LENGTH_LONG).show();
                }
            }
        });

        final Context context = this;
        viewRateButton = (Button) findViewById(R.id.viewRateButton);
        viewRateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                if(POIList.containsKey(poiToRate.getText().length())){
                    final DBHandler db = new DBHandler(add_ratings.this);
                    String poi = poiToRate.getText().toString().toLowerCase();
                    POI rateThisPOI = db.getPOIByName(poi);
                    int rating = db.getRating(rateThisPOI.getId());
                    Log.d("CURRENT RATING IS: ", String.valueOf(rating));
                    Intent intent;
                    intent = new Intent(context, Result.class);
                    Bundle bundle = new Bundle();
                    if(db.getRatingCount(rateThisPOI.getId()) != 0) {
                        ArrayList<String> a = new ArrayList<>();
                        a.add("Rating\n" + (rating / (db.getRatingCount(rateThisPOI.getId()))) + "\n\nComments" + db.getRatingCom(rateThisPOI.getId()));
                        bundle.putStringArrayList("result", a);
                    }
                    else{
                        ArrayList<String> a = new ArrayList<>();
                        a.add("No ratings yet!");
                        bundle.putStringArrayList("result", a);
                    }
                    intent.putExtra("FromRatings", "1");
                    intent.putExtras(bundle);
                    startActivity(intent);
                    intent.putExtra("name", poi);
                    intent.putExtra("rateNum", "0");
                    intent.putExtra("comment", "");
                    finish();
                }
                else{
                    Toast.makeText(add_ratings.this, "Please enter valid POI location", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}

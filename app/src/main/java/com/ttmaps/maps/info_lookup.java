package com.ttmaps.maps;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by kylechang on 11/28/17.
 */

public class info_lookup extends AppCompatActivity {
    private AutoCompleteTextView dest;
    private TextView description;
    private TextView title;
    private Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //db = ((MyApplication)getApplication()).db;
        DBHandler db = new DBHandler(info_lookup.this);
        setContentView(R.layout.activity_info);
        dest = (AutoCompleteTextView)findViewById(R.id.input);
        description = (TextView)findViewById(R.id.description);
        title = (TextView)findViewById(R.id.title);
        search = (Button) findViewById(R.id.search);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                if(dest.getText().length() > 0){
                    final DBHandler db = new DBHandler(info_lookup.this);
                    String poiName = dest.getText().toString().toLowerCase();
                    POI poi = db.getPOIByName(poiName);
                    //int rating = db.updatePOI(rateThisPOI.getId(), poi, (int)stars);
                    //description.setText(poi.getDescription());
                    /**
                    Intent data = new Intent();
                    data.putExtra("description", poiDescription.getDescription());
                    data.putExtra("name", poi);

                    if(getParent() == null){
                        setResult(RESULT_OK, data);
                    }
                    else{
                        getParent().setResult(RESULT_OK, data);
                    }
                     */
                    finish();
                }
            }
        });

        /**
        final Context context = this;
        viewRateButton = (Button) findViewById(R.id.viewRateButton);
        viewRateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                if(poiToRate.getText().length() > 0){
                    final DBHandler db = new DBHandler(add_ratings.this);
                    String poi = poiToRate.getText().toString().toLowerCase();
                    POI rateThisPOI = db.getPOIByName(poi);
                    int rating = db.getRating(rateThisPOI.getId());
                    Log.d("CURRENT RATING IS: ", String.valueOf(rating));
                    Intent intent;
                    intent = new Intent(context, Result.class);
                    Bundle bundle = new Bundle();
                    if(db.getRatingCount(rateThisPOI.getId()) != 0) {
                        bundle.putString("result", "Rating\n" + (rating / (db.getRatingCount(rateThisPOI.getId()))) + "\n\nComments" + db.getRatingCom(rateThisPOI.getId()));
                    }
                    else{
                        bundle.putString("result", "No ratings yet!");
                    }
                    intent.putExtras(bundle);
                    startActivity(intent);
                    intent.putExtra("name", poi);
                    intent.putExtra("rateNum", "0");
                    intent.putExtra("comment", "");
                    finish();
                }

            }
        });
         */
    }
}
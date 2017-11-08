package com.ttmaps.maps;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import java.util.List;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

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


}

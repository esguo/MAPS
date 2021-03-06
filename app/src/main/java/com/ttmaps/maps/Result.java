package com.ttmaps.maps;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Result extends AppCompatActivity {

    private Button btn_viewmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);

        btn_viewmap = (Button) findViewById(R.id.viewMapBtn);

        Bundle bundle = getIntent().getExtras();
        final ArrayList<String> r;
        r = bundle.getStringArrayList("result");
        String b = bundle.getString("FromRatings");
        if(b != null && b.equals("1")){
            TextView t = (TextView)findViewById(R.id.textView);
            t.setText("Current Ratings");
        }
        TextView result = (TextView) findViewById(R.id.result);

        String resultString = r.get(r.size()-1);
        result.setText(resultString);

        final Context context = this;
        btn_viewmap.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent intent = new Intent(Result.this, MapsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("result", r);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

}

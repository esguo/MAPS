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
        String r;
        r = bundle.getString("result");

        TextView result = (TextView) findViewById(R.id.result);
        result.setText(r);

        final Context context = this;
        btn_viewmap.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent;
                intent = new Intent(context, MapsActivity.class);
                startActivity(intent);
            }
        });
    }

}

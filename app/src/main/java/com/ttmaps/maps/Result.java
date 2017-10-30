package com.ttmaps.maps;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

public class Result extends AppCompatActivity {

    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);

        Bundle bundle = getIntent().getExtras();
        String r;
        r = bundle.getString("result");

        result = (TextView) findViewById(R.id.result);
        result.setText(r);
    }

}

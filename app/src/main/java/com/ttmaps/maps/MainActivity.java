package com.ttmaps.maps;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;

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

package com.ttmaps.maps;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

/**
 * Created by Eddie on 12/6/2017.
 */

public class About extends AppCompatActivity{
    String aboutUs = "Created by:\n\n" +
            "Alex Tran\n" +
            "Eason Chang\n" +
            "Eddie Guo\n" +
            "Emily Chou\n" +
            "Gabriel Ang\n" +
            "Jiahao Sun\n" +
            "Komail Rizvi\n" +
            "Kyle Chang\n" +
            "Mason Wong\n" +
            "Tesia Huang\n" +
            "Vincent Alday";

    String desc = "Where will you go next?";
    int clicks = 0;
    Toast t;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.transparentlogo1)
                .setDescription(desc + "\n")
                .addGroup("Version 1.0")
                .addItem(getMembers())
                .addGitHub("esguo/MAPS")
                .create();

        setContentView(aboutPage);
    }
    Element getMembers(){

        Element members = new Element();
        members.setTitle(aboutUs);

        members.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicks++;
                if (t == null) {
                    t = Toast.makeText(About.this, "", Toast.LENGTH_SHORT);
                }
                t.setText("Hidden button! You've clicked: " + clicks + " times!");
                t.show();
            }
        });

        return members;
    }

}

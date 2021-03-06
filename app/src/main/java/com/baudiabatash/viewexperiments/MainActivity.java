package com.baudiabatash.viewexperiments;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.baudiabatash.viewexperiments.CustomView.MyView;

public class MainActivity extends AppCompatActivity {

    private MyView myView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myView = (MyView) findViewById(R.id.myView);
        myView.setProgress(.5);
        myView.setTextColorFromResource(R.color.my_color);
        myView.setRimColorFromResource(R.color.rim_color);
        myView.setProgressColorFromResource(R.color.selected_item_color);
    }
}

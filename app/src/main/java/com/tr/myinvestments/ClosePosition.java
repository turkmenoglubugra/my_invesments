package com.tr.myinvestments;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class ClosePosition extends AppCompatActivity {
    private ListView closePositionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closeposition);

        closePositionList = (ListView) findViewById(R.id.closePositionList);
        ViewGroup headerView = (ViewGroup)getLayoutInflater().inflate(R.layout.headerclosepositions, closePositionList,false);
        closePositionList.addHeaderView(headerView);

    }
}

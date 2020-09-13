package com.tr.myinvestments;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.List;

public class Graphic extends AppCompatActivity {
    private GraphView graph1;
    private List<Investment> listInvestment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphic);

        graph1 = (GraphView) findViewById(R.id.graph1);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph1.addSeries(series);
    }
    private void invListele() {
        Database vt = new Database(Graphic.this);
        listInvestment = vt.VeriListeleInvestment(1);

    }

}

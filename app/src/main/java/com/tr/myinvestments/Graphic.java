package com.tr.myinvestments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class Graphic extends AppCompatActivity {
    private GraphView graph1;
    private List<Investment> listLine;
    private List<Investment> listPie;
    private HashMap<String,Double> lineChart;
    private HashMap<String,Double> pieChart;
    private Menu menu;
    private DataPoint[] pointArray;
    private PieChartView pieChartView;
    private String value;
    private MenuItem register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphic);

        Intent intent = getIntent();
        value = intent.getStringExtra("key");

        graph1 = (GraphView) findViewById(R.id.graph1);
        pieChartView = findViewById(R.id.chart);

        invListele();
        if(value.equals("0")){
            pieChart();
        } else if (value.equals("1")){
            chartLine();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        this.menu = menu;
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.graph,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.lineGraph:
                if(listLine.size() > 1){
                    chartLine();
                } else {
                    new SweetAlertDialog(Graphic.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("You must have close position by two currencies at least!")
                            .show();
                }
                return true;
            case R.id.pieGraph:
                if(listPie.size() > 0){
                    pieChart();
                } else {
                    new SweetAlertDialog(Graphic.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("You must have an open position at least!")
                            .show();
                }
                return true;
            default:
                /*
                if(value.equals("0")){
                    pieChart();
                    register = menu.findItem(R.id.lineGraph);
                    register.setEnabled(false);
                } else if (value.equals("1")){
                    chartLine();
                    register = menu.findItem(R.id.pieGraph);
                    register.setEnabled(false);
                },*/
                return super.onOptionsItemSelected(item);
        }
    }

    private void pieChart(){
        graph1.setVisibility(View.INVISIBLE);
        pieChartView.setVisibility(View.VISIBLE);
        List<SliceValue> pieData = new ArrayList<>();
        List<Integer> colors = new ArrayList<Integer>();
        colors.add(Color.RED);
        colors.add(Color.BLUE);
        colors.add(Color.GREEN);
        colors.add(Color.GRAY);
        colors.add(Color.MAGENTA);
        colors.add(Color.CYAN);
        colors.add(Color.YELLOW);
        colors.add(Color.BLACK);

        for(int i = 0; i < pieChart.size(); i++){
            pieData.add(new SliceValue(pieChart.get(pieChart.keySet().toArray()[i]).floatValue(), colors.get(i%7)).setLabel(pieChart.keySet().toArray()[i]+" : "+pieChart.get(pieChart.keySet().toArray()[i])));

        }
        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true);
        pieChartView.setPieChartData(pieChartData);
    }

    private void invListele() {
        Database vt = new Database(Graphic.this);
        listLine = vt.VeriListeleInvestment(1);
        listPie = vt.VeriListeleInvestment(0);

        lineChart = new HashMap<String, Double>();
        pieChart = new HashMap<String, Double>();

        for(Investment inv : listLine){
            if(!lineChart.containsKey(inv.getCurrencyBuyStr().trim())){
                Double total = 0.0;
                for(Investment obj : listLine){
                    if(obj.getCurrencyBuyStr().trim().equals(inv.getCurrencyBuyStr().trim())){
                        total = total + obj.getProfitLoss();
                    }
                }
                lineChart.put(inv.getCurrencyBuyStr(),total);
            }
        }
        for(Investment inv : listPie){
            if(!pieChart.containsKey(inv.getCurrencyBuyStr().trim())){
                Double total2 = 0.0;
                for(Investment obj : listPie){
                    if(obj.getCurrencyBuyStr().trim().equals(inv.getCurrencyBuyStr().trim())){
                        total2 = total2 + obj.getValueBuy() * obj.getValue();
                    }
                }
                pieChart.put(inv.getCurrencyBuyStr(),total2);
            }
        }
    }

    private void chartLine(){
        pieChartView.setVisibility(View.INVISIBLE);
        graph1.setVisibility(View.VISIBLE);
        pointArray = new DataPoint[lineChart.size()];
        for(int i = 0; i < lineChart.size(); i++){
            pointArray[i] = new DataPoint(i,lineChart.get( lineChart.keySet().toArray()[i]));
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(pointArray);
        graph1.addSeries(series);

        String[] curSet = new String[lineChart.size()];
        curSet = lineChart.keySet().toArray(curSet);

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph1);
        staticLabelsFormatter.setHorizontalLabels(curSet);
        graph1.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
    }
}

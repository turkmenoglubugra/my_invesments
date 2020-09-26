package com.tr.myinvestments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.security.spec.ECField;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ClosePosition extends AppCompatActivity {
    private ListView closePositionList;
    private List<Investment> listInvestment;
    private EditText edtCmpTotal;
    private int positionInv = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closeposition);
        setTitle("Gain or Loss");
        closePositionList = (ListView) findViewById(R.id.closePositionList);
        edtCmpTotal = (EditText) findViewById(R.id.edtCmpTotal);


        closePositionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                positionInv = position;
            }
        });

        ViewGroup headerView = (ViewGroup)getLayoutInflater().inflate(R.layout.headerclosepositions, closePositionList,false);
        closePositionList.addHeaderView(headerView);
        invListele();
    }
    private void invListele() {
        Database vt = new Database(ClosePosition.this);
        listInvestment = vt.VeriListeleInvestment(1);
        if(listInvestment.size() > 0){
            double total = 0.0;
            for(Investment inv : listInvestment){
                total = total + inv.getProfitLoss();
            }
            edtCmpTotal.setText(String.format("%.2f",total));
            if(total > 0){
                edtCmpTotal.setTextColor(Color.parseColor("#10D210"));
            } else if (total < 0) {
                edtCmpTotal.setTextColor(Color.parseColor("#FF0000"));
            } else {
                edtCmpTotal.setTextColor(Color.parseColor("#000000"));
            }
        } else {
            edtCmpTotal.setText("Closed Position Not Found!");
            edtCmpTotal.setTextColor(Color.parseColor("#000000"));
        }

        ClosePositionAdapter adapter = new ClosePositionAdapter(ClosePosition.this, listInvestment);
        closePositionList.setAdapter(adapter);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.closeposition,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.investmentDeleteAction:
                try {
                    if(positionInv == -1){
                        new SweetAlertDialog(ClosePosition.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Entry Must Be Selected!")
                                .show();
                    } else {
                        Database db  = new Database(ClosePosition.this);
                        db.VeriSilInvestment(listInvestment.get(positionInv-1).getId());
                        invListele();
                        positionInv = -1;
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

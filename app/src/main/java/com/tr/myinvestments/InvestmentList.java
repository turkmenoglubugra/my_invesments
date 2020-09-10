package com.tr.myinvestments;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class InvestmentList extends AppCompatActivity {
    private ListView veriListele;
    private List<Investment> listInvestment;
    private List<Currency> listCurrency;
    private List<String> listCurrencyStr;
    private ProgressBar pgsBar;
    private int i = 0;
    private Handler hdlr = new Handler();
    private AutoCompleteTextView edtCmpCurrency;
    private View popupInputDialogView = null;
    private EditText valueEditText = null;
    private AutoCompleteTextView currencyEditText = null;
    private EditText buyPriceEditText = null;
    private Button saveUserDataButton = null;
    private Button cancelUserDataButton = null;
    private String selection = "0";
    private int positionSaveCur = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investmentlist);
        veriListele = (ListView) findViewById(R.id.investmentList);
        pgsBar = (ProgressBar) findViewById(R.id.pBar);
        edtCmpCurrency = (AutoCompleteTextView) findViewById(R.id.edtCmpCurrency);

        Database vt = new Database(InvestmentList.this);
        // Inflate header view
        ViewGroup headerView = (ViewGroup)getLayoutInflater().inflate(R.layout.header, veriListele,false);
        // Add header view to the ListView
        veriListele.addHeaderView(headerView);
        listCurrency = vt.VeriListeleCurrency();
        if(listCurrency.size() == 0){
            vt.VeriEkleCurrency("TRY");
            vt.VeriEkleCurrency("USD");
            vt.VeriEkleCurrency("EUR");
            vt.VeriEkleCurrency("GBP");
            listCurrency = vt.VeriListeleCurrency();
            listCurrencyStr = new ArrayList<String>();
            for(Currency cur : listCurrency){
                listCurrencyStr.add(cur.getName());
            }
        } else {
            listCurrencyStr = new ArrayList<String>();
            for(Currency cur : listCurrency){
                listCurrencyStr.add(cur.getName());
            }
        }
        String[] stockArr = new String[listCurrencyStr.size()];
        stockArr = listCurrencyStr.toArray(stockArr);

       ArrayAdapter<String> adapterCurrency = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, stockArr);
       edtCmpCurrency.setAdapter(adapterCurrency);
       edtCmpCurrency.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
            }
        });

        listInvestment = vt.VeriListeleInvestment();
        InvesmentAdapter adapter = new InvesmentAdapter(InvestmentList.this, listInvestment);
        veriListele.setAdapter(adapter);
        i = pgsBar.getProgress();
        new Thread(new Runnable() {
            public void run() {
                while (i < 100) {
                    i += 1;
                    // Update the progress bar and display the current value in text view
                    hdlr.post(new Runnable() {
                        public void run() {
                            pgsBar.setProgress(i);
                        }
                    });
                    try {
                        // Sleep for 100 milliseconds to show the progress slowly.
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


        LayoutInflater layoutInflater = LayoutInflater.from(InvestmentList.this);
        popupInputDialogView = layoutInflater.inflate(R.layout.popup_input_dialog, null);

        valueEditText = (EditText) popupInputDialogView.findViewById(R.id.userName);
        currencyEditText = (AutoCompleteTextView) popupInputDialogView.findViewById(R.id.edtCmpCurrency);
        currencyEditText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                selection = (String)parent.getItemAtPosition(position);
                positionSaveCur = position;
            }
        });
        ArrayAdapter<String> adapterCurrency2 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, stockArr);
        currencyEditText.setAdapter(adapterCurrency2);
        buyPriceEditText = (EditText) popupInputDialogView.findViewById(R.id.email);
        saveUserDataButton = popupInputDialogView.findViewById(R.id.button_save_user_data);
        cancelUserDataButton = popupInputDialogView.findViewById(R.id.button_cancel_user_data);
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.mainpage,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.investmentAddAction:
                try {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(InvestmentList.this);
                    alertDialogBuilder.setTitle("Add Open Position");
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.setView(popupInputDialogView);

                    final AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                    // When user click the save user data button in the popup dialog.
                    saveUserDataButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (positionSaveCur == -1) {
                                new SweetAlertDialog(InvestmentList.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Oops...")
                                        .setContentText("Currency Must Be Selected!")
                                        .show();
                                return;
                            }

                            // Get user data from popup dialog editeext.
                            String value = valueEditText.getText().toString();
                            Integer idCurrency = 0;
                            for (Currency cr : listCurrency) {
                                if (cr.getName().equals(selection)) {
                                    idCurrency = cr.getId();
                                }
                            }
                            if (idCurrency == 0) {
                                new SweetAlertDialog(InvestmentList.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Oops...")
                                        .setContentText("Currency Not Found!")
                                        .show();
                                return;
                            }
                            String buyPrice = buyPriceEditText.getText().toString();

                            Database db = new Database(InvestmentList.this);
                            db.VeriEkleInvestment(idCurrency, Double.parseDouble(buyPrice), (int) Calendar.getInstance().getTimeInMillis(), null, null, null, null, Double.parseDouble(value));
                            Database vt = new Database(InvestmentList.this);
                            listInvestment = vt.VeriListeleInvestment();
                            InvesmentAdapter adapter = new InvesmentAdapter(InvestmentList.this, listInvestment);
                            veriListele.setAdapter(adapter);
                            alertDialog.cancel();
                        }
                    });

                    cancelUserDataButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.cancel();
                        }
                    });


                /*Intent intent = new Intent(this, AddInvestment.class );
                startActivity(intent);*/
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

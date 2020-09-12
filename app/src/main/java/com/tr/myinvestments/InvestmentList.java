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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
    private View popupInputDialogSaveInvestment,popupInputDialogAddCurrency,popupInputDialogClosePosition = null;
    private EditText valueEditTextAddInvestment,valueEditTextAddCurrency,valueEditTextClosePosition = null;
    private AutoCompleteTextView currencyEditText = null;
    private EditText buyPriceEditText = null;
    private Button saveButtonAddInvesment, saveButtonAddCurrency,saveButtonClosePosition = null;
    private Button cancelButtonAddInvesment, cancelButtonAddCurrency,cancelButtonClosePosition = null;
    private String selection = "0";
    private int positionSaveCur = -1;
    private AlertDialog.Builder alertDialogBuilderSaveInvestment,alertDialogBuilderAddCurrency, alertDialogBuilderClosePosition;
    private AlertDialog alertDialogAddInvestment,alertDialogAddCurrency, alertDialogClosePosition;
    private int positionInv = -1;
    private FloatingActionButton fab;
    private AutoCompleteTextView edtCmpCurrency;
    private TextView distTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investmentlist);


        LayoutInflater layoutInflaterAddInvestment = LayoutInflater.from(InvestmentList.this);
        LayoutInflater layoutInflaterAddCurrency = LayoutInflater.from(InvestmentList.this);
        LayoutInflater layoutInflaterClosePosition = LayoutInflater.from(InvestmentList.this);

        popupInputDialogSaveInvestment = layoutInflaterAddInvestment.inflate(R.layout.popup_input_dialog, null);
        popupInputDialogAddCurrency = layoutInflaterAddCurrency.inflate(R.layout.addcurrency_input_dialog, null);
        popupInputDialogClosePosition = layoutInflaterAddCurrency.inflate(R.layout.closeposition_input_dialog, null);

        alertDialogBuilderSaveInvestment = new AlertDialog.Builder(InvestmentList.this);
        alertDialogBuilderAddCurrency = new AlertDialog.Builder(InvestmentList.this);
        alertDialogBuilderClosePosition = new AlertDialog.Builder(InvestmentList.this);

        alertDialogBuilderSaveInvestment.setTitle("Add Open Position");
        alertDialogBuilderSaveInvestment.setCancelable(false);
        alertDialogBuilderSaveInvestment.setView(popupInputDialogSaveInvestment);
        alertDialogAddInvestment = alertDialogBuilderSaveInvestment.create();
        alertDialogBuilderAddCurrency.setTitle("Add Currency");
        alertDialogBuilderAddCurrency.setCancelable(false);
        alertDialogBuilderAddCurrency.setView(popupInputDialogAddCurrency);
        alertDialogAddCurrency = alertDialogBuilderAddCurrency.create();
        alertDialogBuilderClosePosition.setTitle("Close Position");
        alertDialogBuilderClosePosition.setCancelable(false);
        alertDialogBuilderClosePosition.setView(popupInputDialogClosePosition);
        alertDialogClosePosition = alertDialogBuilderClosePosition.create();

        veriListele = (ListView) findViewById(R.id.investmentList);
        distTextView = (TextView) findViewById(R.id.distTextView);
        edtCmpCurrency = (AutoCompleteTextView) findViewById(R.id.edtCmpCurrency);

        valueEditTextAddInvestment = (EditText) popupInputDialogSaveInvestment.findViewById(R.id.userName);
        valueEditTextAddCurrency = (EditText) popupInputDialogAddCurrency.findViewById(R.id.userName);
        currencyEditText = (AutoCompleteTextView) popupInputDialogSaveInvestment.findViewById(R.id.edtCmpSaveCurrency);
        buyPriceEditText = (EditText) popupInputDialogSaveInvestment.findViewById(R.id.email);
        saveButtonAddInvesment = popupInputDialogSaveInvestment.findViewById(R.id.button_save_user_data);
        cancelButtonAddInvesment = popupInputDialogSaveInvestment.findViewById(R.id.button_cancel_user_data);
        saveButtonAddInvesment = popupInputDialogSaveInvestment.findViewById(R.id.button_save_user_data);
        cancelButtonAddInvesment = popupInputDialogSaveInvestment.findViewById(R.id.button_cancel_user_data);
        saveButtonAddCurrency = popupInputDialogAddCurrency.findViewById(R.id.button_save_user_data);
        cancelButtonAddCurrency = popupInputDialogAddCurrency.findViewById(R.id.button_cancel_user_data);
        saveButtonClosePosition = popupInputDialogClosePosition.findViewById(R.id.button_save_user_data);
        cancelButtonClosePosition = popupInputDialogClosePosition.findViewById(R.id.button_cancel_user_data);
        valueEditTextClosePosition = popupInputDialogClosePosition.findViewById(R.id.sellPrice);

/*
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
*/


        currencyEditText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                selection = (String)parent.getItemAtPosition(position);
                positionSaveCur = position;
            }
        });

        veriListele.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               positionInv = position;
           }
       });
        edtCmpCurrency.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                try {
                    double sum = 0.0;
                    for (Investment inv : listInvestment) {
                        if (inv.getCurrencyBuyStr().trim().equals(edtCmpCurrency.getText().toString().trim())) {
                            sum = sum + inv.getValue();
                        }
                    }
                    distTextView.setText(String.valueOf(sum));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        ViewGroup headerView = (ViewGroup)getLayoutInflater().inflate(R.layout.header, veriListele,false);
        veriListele.addHeaderView(headerView);

        curListele();
        invListele();
    }

    private void curListele(){
        Database vt = new Database(InvestmentList.this);
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

        ArrayAdapter<String> adapterCurrency2 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, stockArr);
        currencyEditText.setAdapter(adapterCurrency2);

    }

    private void invListele() {
        Database vt = new Database(InvestmentList.this);
        listInvestment = vt.VeriListeleInvestment();

        InvesmentAdapter adapter = new InvesmentAdapter(InvestmentList.this, listInvestment);
        veriListele.setAdapter(adapter);
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
                    currencyEditText.setText("");
                    valueEditTextAddInvestment.setText("");
                    buyPriceEditText.setText("");
                    alertDialogAddInvestment.show();

                    saveButtonAddInvesment.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (positionSaveCur == -1) {
                                new SweetAlertDialog(InvestmentList.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Oops...")
                                        .setContentText("Currency Must Be Selected!")
                                        .show();
                                return;
                            }

                            String value = valueEditTextAddInvestment.getText().toString();
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
                            db.VeriEkleInvestment(idCurrency, Double.parseDouble(buyPrice), (int) Calendar.getInstance().getTimeInMillis(), null, null, null, Double.parseDouble(value));
                            Database vt = new Database(InvestmentList.this);
                            listInvestment = vt.VeriListeleInvestment();
                            InvesmentAdapter adapter = new InvesmentAdapter(InvestmentList.this, listInvestment);
                            veriListele.setAdapter(adapter);
                            edtCmpCurrency.setSelection(0);
                            edtCmpCurrency.setText("");
                            distTextView.setText("X X X X X X");
                            alertDialogAddInvestment.cancel();
                        }
                    });

                    cancelButtonAddInvesment.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialogAddInvestment.cancel();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            case R.id.investmentAssetsAction:
                if(listInvestment.size() == 0){
                    new SweetAlertDialog(InvestmentList.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("There is no investment!")
                            .show();
                }
                return true;
            case R.id.investmentClearAction:
                edtCmpCurrency.setText("");
                distTextView.setText("X X X X X X");
                return true;
            case R.id.investmentDeleteAction:
                Database db = new Database(InvestmentList.this);
                if(positionInv == -1) {
                    new SweetAlertDialog(InvestmentList.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Entry Must Be Selected!")
                            .show();
                } else {
                    db.VeriSilInvestment(listInvestment.get(positionInv-1).getId());
                    listInvestment = db.VeriListeleInvestment();
                    InvesmentAdapter adapter = new InvesmentAdapter(InvestmentList.this, listInvestment);
                    veriListele.setAdapter(adapter);
                    positionInv=-1;
                }
                return true;
            case R.id.investmentAddCurrencyAction:
                try {
                    alertDialogAddCurrency.show();
                    valueEditTextAddCurrency.setText("");
                    saveButtonAddCurrency.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(valueEditTextAddCurrency.getText().toString().trim().equals("")){
                                new SweetAlertDialog(InvestmentList.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Oops...")
                                        .setContentText("Currency Must Be Written!")
                                        .show();
                                return;
                            }
                            boolean control = true;
                            for(Currency cr : listCurrency){
                                if(cr.getName().trim().equals(valueEditTextAddCurrency.getText().toString().trim())){
                                    control = false;
                                }
                            }
                            if(!control){
                                new SweetAlertDialog(InvestmentList.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Oops...")
                                        .setContentText("Currency Already Exists!")
                                        .show();
                                return;
                            } else {
                                Database db = new Database(InvestmentList.this);
                                db.VeriEkleCurrency(valueEditTextAddCurrency.getText().toString().trim());
                                curListele();
                            }
                            alertDialogAddCurrency.cancel();
                        }
                    });
                    cancelButtonAddCurrency.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialogAddCurrency.cancel();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            case R.id.investmentClosePositionAction:
                if(positionInv == -1) {
                    new SweetAlertDialog(InvestmentList.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Entry Must Be Selected!")
                            .show();
                } else {
                    try {
                        alertDialogClosePosition.show();
                        saveButtonClosePosition.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(valueEditTextClosePosition.getText().toString().trim().equals("")){
                                    new SweetAlertDialog(InvestmentList.this, SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("Oops...")
                                            .setContentText("Sell Price Must Be Written!")
                                            .show();
                                }
                                Database db2 = new Database(InvestmentList.this);
                                Investment obj = listInvestment.get(positionInv-1);
                                double profit = (obj.getValue() * new Double(valueEditTextClosePosition.getText().toString()))  - (obj.getValue() * obj.getValueBuy());
                                db2.closePosition(obj.getId(), profit, new Double(valueEditTextClosePosition.getText().toString()));
                                alertDialogClosePosition.cancel();
                                invListele();
                            }
                        });
                        cancelButtonClosePosition.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialogAddCurrency.cancel();
                            }
                        });
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

package com.tr.myinvestments;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class InvesmentAdapter extends BaseAdapter {
    private LayoutInflater courseInflater;
    private List<Investment> investmentList;
    private List<Investment> listInvestment;

    public InvesmentAdapter(Activity activity, List<Investment> investmentList) {
        courseInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        this.investmentList = investmentList;
    }

    @Override
    public int getCount() {
        return investmentList.size();
    }

    @Override
    public Object getItem(int i) {
        return investmentList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View lineView;
        lineView = courseInflater.inflate(R.layout.investmentlistview, null);
        TextView textViewValue = (TextView) lineView.findViewById(R.id.textViewValue);
        TextView textViewCurrency = (TextView) lineView.findViewById(R.id.textViewCurrency);
        TextView textViewBuyValue = (TextView) lineView.findViewById(R.id.textViewBuyValue);

        Investment investment = investmentList.get(i);
        textViewValue.setText(String.valueOf(investment.getValue()));
        textViewCurrency.setText(investment.getCurrencyBuyStr());
        textViewBuyValue.setText(String.valueOf(investment.getValueBuy()));

        return lineView;
    }
}
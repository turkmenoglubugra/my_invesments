package com.tr.myinvestments;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class ClosePositionAdapter extends BaseAdapter {
    private LayoutInflater courseInflater;
    private List<Investment> investmentList;
    private List<Investment> listInvestment;
    private Activity activity;

    public ClosePositionAdapter(Activity activity, List<Investment> investmentList) {
        courseInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        this.activity = activity;
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
        return investmentList.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View lineView;
        lineView = courseInflater.inflate(R.layout.profitlistview, null);
        TextView textViewValue = (TextView) lineView.findViewById(R.id.textViewValue);
        TextView textViewCurrency = (TextView) lineView.findViewById(R.id.textViewCurrency);
        TextView textViewBuyValue = (TextView) lineView.findViewById(R.id.textViewBuyValue);
        TextView textViewSellValue = (TextView) lineView.findViewById(R.id.textViewSellValue);
        TextView textViewProfit = (TextView) lineView.findViewById(R.id.textViewProfit);
        Animation animation = null;
        animation = AnimationUtils.loadAnimation(activity, R.anim.slide_in_top);
        animation.setDuration(1000);
        lineView.startAnimation(animation);
        animation = null;
        Investment investment = investmentList.get(i);
        textViewValue.setText(String.format("%.2f",investment.getValue()));
        textViewCurrency.setText(investment.getCurrencyBuyStr());
        textViewBuyValue.setText(String.format("%.2f",investment.getValueBuy()));
        textViewSellValue.setText(String.format("%.2f",investment.getValueSell()));
        textViewProfit.setText(String.format("%.2f",investment.getProfitLoss()));
        return lineView;
    }
}
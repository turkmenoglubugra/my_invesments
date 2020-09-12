package com.tr.myinvestments;

import java.util.Date;

public class Investment {
    private Integer currencyBuy;
    private Double valueBuy;
    private Date buyDate;
    private Double valueSell;
    private Date sellDate;
    private Double profitLoss;
    private Double value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String currencyBuyStr;
    private String currencySellStr;
    private int id;

    public String getCurrencyBuyStr() {
        return currencyBuyStr;
    }

    public void setCurrencyBuyStr(String currencyBuyStr) {
        this.currencyBuyStr = currencyBuyStr;
    }

    public String getCurrencySellStr() {
        return currencySellStr;
    }

    public void setCurrencySellStr(String currencySellStr) {
        this.currencySellStr = currencySellStr;
    }

    public Investment(Integer currencyBuy, Double valueBuy, Date buyDate, Double valueSell, Date sellDate, Double profitLoss, Double value, Integer id) {
        this.currencyBuy = currencyBuy;
        this.valueBuy = valueBuy;
        this.buyDate = buyDate;
        this.valueSell = valueSell;
        this.sellDate = sellDate;
        this.profitLoss = profitLoss;
        this.value = value;
        this.id = id;
    }

    public Investment() {
    }

    public Integer getCurrencyBuy() {
        return currencyBuy;
    }
    public void setCurrencyBuy(Integer currencyBuy) {
        this.currencyBuy = currencyBuy;
    }

    public Double getValueBuy() {
        return valueBuy;
    }

    public void setValueBuy(Double valueBuy) {
        this.valueBuy = valueBuy;
    }

    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }

    public Double getValueSell() {
        return valueSell;
    }

    public void setValueSell(Double valueSell) {
        this.valueSell = valueSell;
    }

    public Date getSellDate() {
        return sellDate;
    }

    public void setSellDate(Date sellDate) {
        this.sellDate = sellDate;
    }

    public Double getProfitLoss() {
        return profitLoss;
    }

    public void setProfitLoss(Double profitLoss) {
        this.profitLoss = profitLoss;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }


}

package com.tr.myinvestments;

import java.util.Date;

public class Investment {
    private Integer currencyBuy;
    private Double valueBuy;
    private Date buyDate;
    private Integer currencySell;
    private Double valueSell;
    private Date sellDate;
    private Double profitLoss;
    private Double value;
    private String currencyBuyStr;
    private String currencySellStr;

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

    public Investment(Integer currencyBuy, Double valueBuy, Date buyDate, Integer currencySell, Double valueSell, Date sellDate, Double profitLoss, Double value) {
        this.currencyBuy = currencyBuy;
        this.valueBuy = valueBuy;
        this.buyDate = buyDate;
        this.currencySell = currencySell;
        this.valueSell = valueSell;
        this.sellDate = sellDate;
        this.profitLoss = profitLoss;
        this.value = value;
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

    public Integer getCurrencySell() {
        return currencySell;
    }

    public void setCurrencySell(Integer currencySell) {
        this.currencySell = currencySell;
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

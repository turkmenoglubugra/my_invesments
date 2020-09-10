package com.tr.myinvestments;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MYINVESTMENTS1";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLO_INVESTMENT = "investment";
    private static final String ROW_ID_INVESTMENT = "id";
    private static final String currencyBuy = "currencyBuy";
    private static final String valueBuy = "valueBuy";
    private static final String buyDate = "buyDate";
    private static final String currencySell = "currencySell";
    private static final String valueSell = "valueSell";
    private static final String sellDate = "cusellDaterrencySell";
    private static final String profitLoss = "profitLoss";
    private static final String value = "value";

    private static final String TABLO_CURRENCY = "currency";
    private static final String ROW_ID_CURRENCY = "id";
    private static final String name = "name";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLO_INVESTMENT + "("
                + ROW_ID_INVESTMENT + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + currencyBuy + " INTEGER NOT NULL, "
                + valueBuy + " DOUBLE  NOT NULL, "
                + buyDate + " INTEGER NOT NULL, "
                + currencySell + " INTEGER, "
                + valueSell + " DOUBLE, "
                + sellDate + " INTEGER, "
                + profitLoss + " DOUBLE,"
                + value +  " DOUBLE NOT NULL);");

        db.execSQL("CREATE TABLE " + TABLO_CURRENCY + "("
                + ROW_ID_CURRENCY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + name + " String NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLO_INVESTMENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLO_CURRENCY);
        onCreate(db);
    }
    public void VeriEkleInvestment(int currencyBuy, Double valueBuy, Integer buyDate, Integer currencySell, Double valueSell, Integer sellDate, Double profitLoss, Double value){
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put(this.currencyBuy, currencyBuy);
            cv.put(this.valueBuy, valueBuy);
            cv.put(this.buyDate, buyDate);
            cv.put(this.currencySell, currencySell);
            cv.put(this.valueSell, valueSell);
            cv.put(this.sellDate, sellDate);
            cv.put(this.profitLoss, profitLoss);
            cv.put(this.value, value);
            db.insert(TABLO_INVESTMENT, null,cv);
        }catch (Exception e){
            e.printStackTrace();
        }
        db.close();
    }

    public void VeriEkleCurrency(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put(this.name, name);
            db.insert(TABLO_CURRENCY, null,cv);
        }catch (Exception e){
            e.printStackTrace();
        }
        db.close();
    }

    public List<Investment> VeriListeleInvestment(){
        List<Investment> veriler = new ArrayList<Investment>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            List<Currency> verilerCur = new ArrayList<Currency>();
            String[] stunlarCur = {ROW_ID_CURRENCY,name};
            Cursor cursorCur = db.query(TABLO_CURRENCY, stunlarCur,null,null,null,null,null);
            while (cursorCur.moveToNext()){
                Currency objCur = new Currency(cursorCur.getInt(0),cursorCur.getString(1));
                verilerCur.add(objCur);
            }
            String[] stunlar = {ROW_ID_INVESTMENT,currencyBuy,valueBuy, buyDate,currencySell,valueSell,sellDate,profitLoss,value};
            Cursor cursor = db.query(TABLO_INVESTMENT, stunlar,null,null,null,null,null);
            while (cursor.moveToNext()){
                Calendar calendar1 = Calendar.getInstance();
                calendar1.setTimeInMillis(cursor.getInt(2));
                Calendar calendar2 = Calendar.getInstance();
                calendar2.setTimeInMillis(cursor.getInt(5));
                Investment obj = new Investment(cursor.getInt(0),cursor.getDouble(1),calendar1.getTime(),cursor.getInt(3),cursor.getDouble(4),calendar2.getTime(),cursor.getDouble(6), cursor.getDouble(7));
                for(Currency cr : verilerCur){
                    if(cr.getId() == cursor.getInt(0)){
                        obj.setCurrencyBuyStr(cr.getName());
                    }
                }
                veriler.add(obj);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        db.close();
        return veriler;
    }

    public List<Currency> VeriListeleCurrency(){
        List<Currency> veriler = new ArrayList<Currency>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String[] stunlar = {ROW_ID_CURRENCY,name};
            Cursor cursor = db.query(TABLO_CURRENCY, stunlar,null,null,null,null,null);
            while (cursor.moveToNext()){
                Currency obj = new Currency(cursor.getInt(0),cursor.getString(1));
                veriler.add(obj);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        db.close();
        return veriler;
    }


    public void VeriSilInvestment(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            // id ye göre verimizi siliyoruz
            String where = ROW_ID_INVESTMENT + " = " + id ;
            db.delete(TABLO_INVESTMENT,where,null);
        }catch (Exception e){
        }
        db.close();
    }

    public void VeriSilCurrency(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            // id ye göre verimizi siliyoruz
            String where = ROW_ID_CURRENCY + " = " + id ;
            db.delete(TABLO_CURRENCY,where,null);
        }catch (Exception e){
        }
        db.close();
    }

/*
    public void VeriDuzenle(int id, String ad, String malzeme, String tarif, byte[] resim){
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put(ROW_YEMEK_ADI, ad);
            cv.put(ROW_MALZEME, malzeme);
            cv.put(ROW_TARIF, tarif);
            cv.put(ROW_RESIM, resim);
            String where = ROW_ID +" = '"+ id + "'";
            db.update(TABLO_TARIFLER,cv,where,null);
        }catch (Exception e){
        }
        db.close();
    }
*/
}
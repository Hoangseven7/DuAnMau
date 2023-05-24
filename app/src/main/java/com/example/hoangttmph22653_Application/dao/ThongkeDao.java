package com.example.hoangttmph22653_Application.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hoangttmph22653_Application.Database.DbHelper;
import com.example.hoangttmph22653_Application.models.Sach;

import java.util.ArrayList;



public class ThongkeDao {
    public ArrayList<Sach> getTop10(Context context){
        ArrayList<Sach> list=new ArrayList<>();
        DbHelper dbHelper=new DbHelper(context);
        Cursor cursor= dbHelper.Getdata("SELECT pm.MASACH,sc.TENSACH,COUNT(pm.MASACH) FROM PHIEUMUON pm,SACH sc WHERE pm.MASACH = sc.MASACH GROUP BY pm.masach,sc.TENSACH ORDER BY COUNT(pm.MASACH) DESC LIMIT 10");
        if(cursor.getCount()!=0){
            while (cursor.moveToNext()){
                list.add(new Sach(cursor.getInt(0),cursor.getString(1),cursor.getInt(2)));
            }
        }
        return list;
    }
    public int getDoanhThu(Context context,String ngaybatdau,String ngayketthuc){
        ngaybatdau=ngaybatdau.replace("/","");
        ngayketthuc=ngayketthuc.replace("/","");
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT SUM(TIENTHUE) FROM  PHIEUMUON WHERE substr(ngay,7)||substr(ngay,4,2)||substr(ngay,1,2) BETWEEN ? AND ?", new String[]{ngaybatdau,ngayketthuc});
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            return cursor.getInt(0);
        } else {
            return 0;
        }
    }
}

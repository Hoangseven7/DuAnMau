package com.example.hoangttmph22653_Application.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.hoangttmph22653_Application.Database.DbHelper;
import com.example.hoangttmph22653_Application.models.Sach;

import java.util.ArrayList;


public class SachDao {
    public ArrayList<Sach> getDSDauSach(Context context) {
        ArrayList<Sach> list = new ArrayList<>();
        DbHelper dbHelper = new DbHelper(context);
        Cursor cursor = dbHelper.Getdata("SELECT * FROM SACH");
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                list.add(new Sach(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3)));
            }
        }
        return list;
    }

    public ArrayList<Sach> getDSDauSach_TENLOAI(Context context) {
        ArrayList<Sach> list = new ArrayList<>();
        DbHelper dbHelper = new DbHelper(context);
        Cursor cursor = dbHelper.Getdata("SELECT sc.masach,sc.tensach,sc.giathue,ls.maloai,ls.tenloai FROM SACH sc,LOAISACH ls WHERE sc.maloai=ls.maloai;");
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                list.add(new Sach(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getString(4)));
            }
        }
        return list;
    }

    public boolean themSach(Context context, String tensach, int giathue, int maloai) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TENSACH", tensach);
        contentValues.put("GIATHUE", giathue);
        contentValues.put("MALOAI", maloai);
        long check = sqLiteDatabase.insert("SACH", null, contentValues);
        if (check == -1) {
            return false;
        } else {
            return true;
        }
    }



    public int xoaSach(Context context, int id) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM  PHIEUMUON WHERE MASACH =?", new String[]{String.valueOf(id)});
        if (cursor.getCount() != 0) {
            return -1;
        }
        long check = sqLiteDatabase.delete("SACH", "MASACH = ?", new String[]{String.valueOf(id)});
        if (check == -1) {
            return 0;
        } else {
            return 1;

        }

    }

    public String capNhatSach(Context context, String masach, Sach sach) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM SACH WHERE MASACH= ?", new String[]{masach});
        if (cursor.getCount() > 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("TENSACH", sach.getTensach());
            contentValues.put("GIATHUE", sach.getGiathue());
            contentValues.put("MALOAI", sach.getMaloai());
            Log.i("CHECKMALOAI",sach.getMaloai()+"");
            long check = sqLiteDatabase.update("SACH", contentValues, "MASACH = ?", new String[]{masach});
            if (check == -1) {
                return "Cập Nhật Thất Bại";
            } else {
                return "thanh cong";
            }
        }
        return "Thành công";
    }
}

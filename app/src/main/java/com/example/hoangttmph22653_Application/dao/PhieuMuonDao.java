package com.example.hoangttmph22653_Application.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hoangttmph22653_Application.Database.DbHelper;
import com.example.hoangttmph22653_Application.models.PhieuMuon;

import java.util.ArrayList;
import java.util.List;


public class PhieuMuonDao {
    DbHelper dbHelper;

    public ArrayList<PhieuMuon> getDSPhieuMuon(Context context) {
        ArrayList<PhieuMuon> list = new ArrayList<>();
        DbHelper dbHelper = new DbHelper(context);
        Cursor cursor = dbHelper.Getdata("SELECT pm.mapm, pm.matv, tv.hoten, pm.matt, tt.hoten, pm.masach, sc.tensach, pm.ngay, pm.trasach, pm.tienthue\n" +
                "FROM PHIEUMUON pm, THANHVIEN tv, THUTHU tt, SACH sc \n" +
                "WHERE pm.matv = tv.matv and pm.matt = tt.matt AND pm.masach = sc.masach ORDER BY pm.mapm DESC");
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                list.add(
                        new PhieuMuon(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5), cursor.getString(6), cursor.getString(7), cursor.getInt(8), cursor.getInt(9)));
            }
        }
        return list;
    }

    public boolean thayDoiTrangThai(Context context, int mapm) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("trasach", 1);
        long check = sqLiteDatabase.update("PHIEUMUON", contentValues, "mapm = ?", new String[]{String.valueOf(mapm)});
        if (check == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean themPhieuMuon(Context context, PhieuMuon phieuMuon) {
        dbHelper = new DbHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("matv", phieuMuon.getMatv());
        contentValues.put("matt", phieuMuon.getMatt());
        contentValues.put("masach", phieuMuon.getMasach());
        contentValues.put("ngay", phieuMuon.getNgay());
        contentValues.put("trasach", phieuMuon.getTrasach());
        contentValues.put("tienthue", phieuMuon.getTienthue());
        long check = sqLiteDatabase.insert("PHIEUMUON", null, contentValues);
        if (check == -1) {
            return false;
        } else {
            return true;
        }
    }


    public int xoaPhieuMuon(Context context, int id) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        long check = sqLiteDatabase.delete("PHIEUMUON", "MAPM = ?", new String[]{String.valueOf(id)});
        if (check == -1) {
            return 0;
        } else {
            return 1;

        }
    }

    public String capNhatPhieuMuon(Context context,String MAPM,PhieuMuon phieuMuon) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM PHIEUMUON WHERE MAPM= ?", new String[]{MAPM});
        if (cursor.getCount() > 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("matv", phieuMuon.getMatv());
            contentValues.put("matt", phieuMuon.getMatt());
            contentValues.put("masach", phieuMuon.getMasach());
            contentValues.put("ngay", phieuMuon.getNgay());
            contentValues.put("trasach", phieuMuon.getTrasach());
            contentValues.put("tienthue", phieuMuon.getTienthue());
            long check = sqLiteDatabase.update("PHIEUMUON", contentValues, "MAPM = ?", new String[]{MAPM});
            if (check == -1) {
                return "Cập Nhật Thất Bại";
            } else {
                return "thanh cong";
            }
        }
        return "Thành công";
    }
}

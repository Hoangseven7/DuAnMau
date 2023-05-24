package com.example.hoangttmph22653_Application.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hoangttmph22653_Application.Database.DbHelper;
import com.example.hoangttmph22653_Application.models.LoaiSach;

import java.util.ArrayList;



public class LoaiSachDAO {
    public ArrayList<LoaiSach> getLoaiSach(Context context) {
        ArrayList<LoaiSach> list = new ArrayList<>();
        DbHelper dbHelper = new DbHelper(context);
        Cursor cursor = dbHelper.Getdata("SELECT * FROM LOAISACH");
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                list.add(new LoaiSach(cursor.getInt(0), cursor.getString(1)));
            }
        }
        return list;
    }

    //THÊM LOẠI SÁCH
    public boolean themLoaiSach(Context context, String tenloai) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TENLOAI", tenloai);
        long check = sqLiteDatabase.insert("LOAISACH", null, contentValues);
        if (check == -1) {
            return false;
        } else {
            return true;
        }
    }
    //xóa loại sách

    //1: xóa thành công
    //0 xóa thất bại
    //-1 có sách tồn tại trong thể loại đó
    public int xoaloaiSach(Context context, int id) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM  SACH WHERE MALOAI =?", new String[]{String.valueOf(id)});
        if (cursor.getCount() != 0) {
            return -1;
        }
        long check = sqLiteDatabase.delete("LOAISACH", "MALOAI = ?", new String[]{String.valueOf(id)});
        if (check == -1) {
            return 0;
        } else {
            return 1;
        }
    }

    public String capNhatLoaiSach(Context context, String MALOAI, LoaiSach TENLOAI) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM LOAISACH WHERE MALOAI= ?", new String[]{MALOAI});
        if (cursor.getCount() > 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("TENLOAI", TENLOAI.getTenLoai());
            long check = sqLiteDatabase.update("LOAISACH", contentValues, "MALOAI = ?", new String[]{MALOAI});
            if (check == -1) {
                return "Cập Nhật Thất Bại";
            } else {
                return "thanh cong";
            }
        }
        return "Thành công";
    }
}

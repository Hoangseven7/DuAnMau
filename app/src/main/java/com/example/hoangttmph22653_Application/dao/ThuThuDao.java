package com.example.hoangttmph22653_Application.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hoangttmph22653_Application.Database.DbHelper;

import java.util.List;


public class ThuThuDao {
    public Boolean getDSThuThu(Context context, String matt, String matkhau,boolean luuhaykhong) {
        DbHelper dbHelper = new DbHelper(context);
        Cursor cursor = dbHelper.Getdata("SELECT * FROM THUTHU");
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                if (matt.equalsIgnoreCase(cursor.getString(0)) && matkhau.equalsIgnoreCase(cursor.getString(2))) {
                    if(luuhaykhong){
                        SharedPreferences sharedPreferences = context.getSharedPreferences("THONGTINLOGIN", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("matt", cursor.getString(0));
                        editor.putString("hoten", cursor.getString(1));
                        editor.putString("password", cursor.getString(2));
                        editor.commit();
                    } else {
                        SharedPreferences sharedPreferences = context.getSharedPreferences("THONGTINLOGIN", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("matt", "");
                        editor.putString("hoten", "");
                        editor.putString("password", "");
                        editor.commit();
                    }
                    return true;
                }
            }
        }
        return false;
    }


    public String capNhatMatKhau(Context context, String username, String oldPass, String newPass) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM THUTHU WHERE MATT= ? AND MATKHAU =?", new String[]{username, oldPass});
        if (cursor.getCount() > 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("matkhau", newPass);
            long check = sqLiteDatabase.update("THUTHU", contentValues, "matt = ?", new String[]{username});
            if (check == -1) {
                return "Cập Nhật Thất Bại";
            } else {
                return "thanhcong";
            }
        }
        return "Mật Khẩu Không Đúng";
    }


    public Boolean tao_tai_khoan(Context context, String tk, String pass, String hoten) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("MATT", tk);
        contentValues.put("HOTEN", hoten);
        contentValues.put("MATKHAU", pass);
        long check = sqLiteDatabase.insert("THUTHU", null, contentValues);
        if (check == -1) {
            return false;
        } else {
            return true;
        }
    }

}

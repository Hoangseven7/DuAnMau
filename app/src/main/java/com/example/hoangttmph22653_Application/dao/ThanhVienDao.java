package com.example.hoangttmph22653_Application.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hoangttmph22653_Application.Database.DbHelper;
import com.example.hoangttmph22653_Application.models.ThanhVien;

import java.util.ArrayList;



public class ThanhVienDao {
    public ArrayList<ThanhVien> getDSThanhVien(Context context){
        ArrayList<ThanhVien> list=new ArrayList<>();
        DbHelper dbHelper=new DbHelper(context);
        Cursor cursor= dbHelper.Getdata("SELECT * FROM THANHVIEN");
        if(cursor.getCount()!=0){
            while (cursor.moveToNext()){
                list.add(new ThanhVien(cursor.getInt(0),cursor.getString(1),cursor.getString(2)));
            }
        }
        return list;
    }
    public boolean themThanhVien(Context context,String hoten,String namsinh){
        DbHelper dbHelper=new DbHelper(context);
        SQLiteDatabase sqLiteDatabase=dbHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("HOTEN",hoten);
        contentValues.put("NAMSINH",namsinh);
        long check =  sqLiteDatabase.insert("THANHVIEN",null,contentValues);;
      if(check==-1){
          return false;
      } else {
          return true;
      }
    }
//    public boolean xoaThanhVien(Context context,String hoten,String namsinh){
//        DbHelper dbHelper=new DbHelper(context);
//        SQLiteDatabase sqLiteDatabase=dbHelper.getWritableDatabase();
//        long check =  sqLiteDatabase.delete("THANHVIEN","");;
//        if(check==-1){
//            return false;
//        } else {
//            return true;
//        }
//    }


    public int xoaThanhVien(Context context, int id) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM  PHIEUMUON WHERE MATV =?", new String[]{String.valueOf(id)});
        if (cursor.getCount() != 0) {
            return -1;
        }
        long check = sqLiteDatabase.delete("THANHVIEN", "MATV = ?", new String[]{String.valueOf(id)});
        if (check == -1) {
            return 0;
        } else {
            return 1;

        }

    }


    public String capNhatThanhVien(Context context, String matv, ThanhVien thanhVien) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM THANHVIEN WHERE MATV= ?", new String[]{matv});
        if (cursor.getCount() > 0) {
            ContentValues contentValues=new ContentValues();
            contentValues.put("HOTEN",thanhVien.getHoten());
            contentValues.put("NAMSINH",thanhVien.getNamsinh());
            long check = sqLiteDatabase.update("THANHVIEN", contentValues, "MATV = ?", new String[]{matv});
            if (check == -1) {
                return "Cập Nhật Thất Bại";
            } else {
                return "thanh cong";
            }
        }
        return "Thành công";
    }
}

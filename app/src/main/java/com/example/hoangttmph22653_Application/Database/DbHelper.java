package com.example.hoangttmph22653_Application.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context) {
        super(context, "DANGKYMONHOC", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String dbThuThu = "CREATE TABLE THUTHU(MATT TEXT PRIMARY KEY,HOTEN TEXT,MATKHAU TEXT)";
        sqLiteDatabase.execSQL(dbThuThu);

        String dbThanhVien ="CREATE TABLE THANHVIEN(MATV INTEGER PRIMARY KEY AUTOINCREMENT,HOTEN TEXT,NAMSINH TEXT)";
        sqLiteDatabase.execSQL(dbThanhVien);

        String dbLoai ="CREATE TABLE LOAISACH(MALOAI INTEGER PRIMARY KEY AUTOINCREMENT,TENLOAI TEXT)";
        sqLiteDatabase.execSQL(dbLoai);

        String dbSach ="CREATE TABLE SACH(MASACH INTEGER PRIMARY KEY AUTOINCREMENT,TENSACH TEXT,GIATHUE INTEGER,MALOAI INTEGER REFERENCES LOAISACH(MALOAI))";
        sqLiteDatabase.execSQL(dbSach);

        String dbPhieuMuon ="CREATE TABLE PHIEUMUON(MAPM INTEGER PRIMARY KEY AUTOINCREMENT,MATV INTEGER REFERENCES THANHVIEN(MATV),MATT TEXT REFERENCES THUTHU(MATT),MASACH INTEGER REFERENCES SACH(MASACH),NGAY TEXT,TRASACH INTEGER,TIENTHUE INTEGER)";
        sqLiteDatabase.execSQL(dbPhieuMuon);

        //DATA MẪU
        sqLiteDatabase.execSQL("INSERT INTO LOAISACH VALUES (1, 'Thiếu nhi'),(2,'Tình cảm'),(3, 'Giáo khoa')");
        sqLiteDatabase.execSQL("INSERT INTO SACH VALUES (1, 'Hãy đợi đấy', 2500, 1), (2, 'Thằng cuội', 1000, 1), (3, 'Lập trình Android', 2000, 3)");
        sqLiteDatabase.execSQL("INSERT INTO THUTHU VALUES ('thuthu01','Nguyễn Văn Anh','abc123'),('thuthu02','Trần Văn Hùng','123abc'),('admin','Trần Văn Hùng','admin')");
        sqLiteDatabase.execSQL("INSERT INTO THANHVIEN VALUES (1,'Cao Thu Trang','2000'),(2,'Trần Mỹ Kim','2000')");
        //trả sách: 1: đã trả - 0: chưa trả
        sqLiteDatabase.execSQL("INSERT INTO PHIEUMUON VALUES (1,1,'thuthu01', 1, '19/03/2022', 1, 2500),(2,1,'thuthu01', 3, '19/03/2022', 0, 2000),(3,2,'thuthu02', 1, '19/03/2022', 1, 2000)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //KHÁC VERSION SẼ XÓA DATABASE CẬP NHẬT LẠI
        if(i!= i1){
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS THUTHU");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS THANHVIEN");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS LOAISACH");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS SACH");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS PHIEUMUON");
            onCreate(sqLiteDatabase);
        }
    }
    public void Querydata(String sql) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public Cursor Getdata(String sql) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

}

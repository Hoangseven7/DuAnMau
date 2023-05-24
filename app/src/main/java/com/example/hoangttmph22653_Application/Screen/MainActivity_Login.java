package com.example.hoangttmph22653_Application.Screen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hoangttmph22653_Application.MainActivity;
import com.example.hoangttmph22653_Application.R;
import com.example.hoangttmph22653_Application.dao.ThuThuDao;


public class MainActivity_Login extends AppCompatActivity {
    EditText edtPass, edtTk;
    Button btnDangNhap, btnHuy;
    ThuThuDao dao;
    CheckBox cbk;
    Boolean luuhaykhong = true;
    private  String u,p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        dao = new ThuThuDao();
        Anhxa();
        kiemtra();
        taikhoanmatkhau();
    }

    private void Anhxa() {
        edtTk = findViewById(R.id.edt_Tai_Khoan);
        edtPass = findViewById(R.id.edt_Mat_Khau);
        btnDangNhap = findViewById(R.id.btn_Dang_Nhap);
        btnHuy = findViewById(R.id.btn_Huy);
        cbk = findViewById(R.id.checkBox);
    }

    private void kiemtra() {
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Boolean kiemtra=dao.getDSThuThu(MainActivity_Login.this,edtTk.getText().toString().trim(),edtPass.getText().toString().trim(),luuhaykhong);
                    if (kiemtra) {
                        Intent intent = new Intent(MainActivity_Login.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity_Login.this);
                        builder.setMessage("Vui lòng điền tài khoản && mật khẩu!\n");
                        builder.setTitle("Thông báo");
                        builder.show();
                    }
                } catch (Exception e) {
                    Log.i("CHECK", e.toString());

                }
            }
        });


        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();
            }
        });

    }

    private void taikhoanmatkhau() {
        SharedPreferences sharedPreferences = getSharedPreferences("THONGTINLOGIN", MODE_PRIVATE);
        String taikhoan = sharedPreferences.getString("matt", "");
        String matkhau = sharedPreferences.getString("password", "");
        edtTk.setText(taikhoan);
        edtPass.setText(matkhau);
    }
}
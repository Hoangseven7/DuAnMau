package com.example.hoangttmph22653_Application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoangttmph22653_Application.Fragment.Fragment_Doanh_thu;
import com.example.hoangttmph22653_Application.Fragment.Fragment_ql_loai_sach;
import com.example.hoangttmph22653_Application.Fragment.Fragment_ql_phieu_muon;
import com.example.hoangttmph22653_Application.Fragment.Fragment_ql_sach;
import com.example.hoangttmph22653_Application.Fragment.Fragment_ql_thanh_vien;
import com.example.hoangttmph22653_Application.Fragment.Fragment_top10_sach_muon;
import com.example.hoangttmph22653_Application.Screen.MainActivity_Login;
import com.example.hoangttmph22653_Application.dao.SachDao;
import com.example.hoangttmph22653_Application.dao.ThuThuDao;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ImageView img_git;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        Actionbar();
        evenClick();
        SachDao dao = new SachDao();
        dao.getDSDauSach(this);
        toolbar.setTitle("Màn hình Chính");
        SharedPreferences sharedPreferences = getSharedPreferences("THONGTINLOGIN", MODE_PRIVATE);
//        Menu menuNav = navigationView.getMenu();
//        MenuItem createAcount = menuNav.findItem(R.id.menu_admin_them_tai_khoan);
//        if (sharedPreferences.getString("matt", "").equals("admin")) {
//            createAcount.setVisible(true);
//        } else {
//            createAcount.setVisible(false);
//        }
//        Toast.makeText(this, sharedPreferences.getString("matt", "") + ":" + sharedPreferences.getString("matt", "").equals("admin"), Toast.LENGTH_SHORT).show();
    }

    private void evenClick() {
    }

    private void anhXa() {
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigationview);
        navigationView.setNavigationItemSelectedListener(this);//gắn loa lắng nghe
        View headerLayout = navigationView.getHeaderView(0);
    }

    private void Actionbar() {
        setSupportActionBar(toolbar);//add toolbar vao ung dung
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar, 0, 0);
        toggle.syncState();//tao nut 3 gach
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_ql_phieu_muon: {
                drawerLayout.closeDrawer(navigationView);
                replaceFlagment(Fragment_ql_phieu_muon.newInstance());
                toolbar.setTitle("Quản Lý Phiếu Mượn");
                break;
            }
            case R.id.menu_ql_loai_sach: {
                drawerLayout.closeDrawer(navigationView);
                replaceFlagment(Fragment_ql_loai_sach.newInstance());
                toolbar.setTitle("Quản Lý Loại Sách");
                break;
            }
            case R.id.menu_ql_sach: {
                drawerLayout.closeDrawer(navigationView);
                replaceFlagment(Fragment_ql_sach.newInstance());
                toolbar.setTitle("Quản Lý Sách");
                break;
            }
            case R.id.menu_ql_thanh_vien: {
                drawerLayout.closeDrawer(navigationView);
                replaceFlagment(Fragment_ql_thanh_vien.newInstance());
                toolbar.setTitle("Quản Lý Thành Viên");
                break;
            }
            case R.id.menu_top10_muon: {
                drawerLayout.closeDrawer(navigationView);
                replaceFlagment(Fragment_top10_sach_muon.newInstance());
                toolbar.setTitle("Top 10 Sách Mượn Nhiều Nhất");
                break;
            }
            case R.id.menu_doanh_thu: {
                drawerLayout.closeDrawer(navigationView);
                replaceFlagment(Fragment_Doanh_thu.newInstance());
                toolbar.setTitle("Doanh Thu");
                break;
            }
            case R.id.menu_change_passworđ: {
                drawerLayout.closeDrawer(navigationView);
                showDialogDoiMatKhau();
//                toolbar.setTitle("Đổi mật khẩu");
                break;
            }
            case R.id.menu_log_out: {
                Intent intent = new Intent(MainActivity.this, MainActivity_Login.class);
                startActivity(intent);
                finishAffinity();
                break;
            }
            case R.id.menu_admin_them_tai_khoan: {
                drawerLayout.closeDrawer(navigationView);
                Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.dialog_tao_tai_khoan);
                EditText edtTaiKhoan = dialog.findViewById(R.id.dialog_TaiKhoan_TV);
                EditText edtMatKhau = dialog.findViewById(R.id.dialog_matkhau_TV);
                EditText edtHoTen = dialog.findViewById(R.id.dialog_hoten_create_account);
                Button btnThem = dialog.findViewById(R.id.btn_them_account);
                Button btnHuy = dialog.findViewById(R.id.btn_huy_account);
                btnThem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (edtTaiKhoan.getText().toString().equals("") || edtMatKhau.getText().toString().equals("") || edtHoTen.getText().toString().equals("")) {
                            Toast.makeText(MainActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        ThuThuDao thuThuDao = new ThuThuDao();
                        boolean check = thuThuDao.tao_tai_khoan(getApplication(), edtTaiKhoan.getText().toString(), edtMatKhau.getText().toString(), edtTaiKhoan.getText().toString());
                        if (check) {
                            Toast.makeText(MainActivity.this, "Đăng ký Tài Khoản Thành công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(MainActivity.this, "Đăng ký Thất Bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        }
        return true;


    }


    private void showDialogDoiMatKhau() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_doimatkhau, null);
        EditText edtPasswordOld = view.findViewById(R.id.edt_PassOld),
                edtPassWordnew = view.findViewById(R.id.edt_NewPass), edt_ReNewPass = view.findViewById(R.id.edt_ReNewPass);
        builder.setView(view);
        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("Cập nhật", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SharedPreferences sharedPreferences = getSharedPreferences("THONGTINLOGIN", MODE_PRIVATE);
                String matt = sharedPreferences.getString("matt", "");
                String oldPass = edtPasswordOld.getText().toString();
                String newPass = edtPassWordnew.getText().toString();
                String edtReNewPass = edt_ReNewPass.getText().toString();
                if (newPass.equals(edtReNewPass)) {
                    ThuThuDao thuThuDao = new ThuThuDao();
                    String check = thuThuDao.capNhatMatKhau(MainActivity.this, matt, oldPass, newPass);
                    if (check.equals("thanhcong")) {
                        Toast.makeText(MainActivity.this, "Cập Nhật Thành Công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, MainActivity_Login.class));
                    } else {
                        Toast.makeText(MainActivity.this, check, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Nhập Mật khẩu không trùng với nhau", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setCancelable(false);
        builder.show();
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void replaceFlagment(Fragment flagment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.layoutcontent, flagment);
        transaction.commit();
    }
}
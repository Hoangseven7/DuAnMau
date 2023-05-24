package com.example.hoangttmph22653_Application.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hoangttmph22653_Application.R;
import com.example.hoangttmph22653_Application.adapter.ThanhVienAdapter;
import com.example.hoangttmph22653_Application.dao.ThanhVienDao;
import com.example.hoangttmph22653_Application.models.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;



/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_ql_thanh_vien#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_ql_thanh_vien extends Fragment {
    RecyclerView recyclerViewThanhVien;
    FloatingActionButton floatAdd;
    ThanhVienDao thanhVienDao;

    public Fragment_ql_thanh_vien() {
        // Required empty public constructor
    }

    public static Fragment_ql_thanh_vien newInstance() {
        Fragment_ql_thanh_vien fragment = new Fragment_ql_thanh_vien();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ql_thanh_vien, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        floatAdd = view.findViewById(R.id.fab_screen_ql_thanh_vien);
        recyclerViewThanhVien = view.findViewById(R.id.recyclerViewThanhVien);
        recyclerview();
        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_them_thanhvien);
                EditText edtHoTenTV = dialog.findViewById(R.id.dialog_HoTenTV_TV);
                EditText edtNamSinhTV = dialog.findViewById(R.id.dialog_NamSinh_TV);
                Button btn_them = dialog.findViewById(R.id.btn_them_Thanh_Vien);
                Button btn_huy = dialog.findViewById(R.id.btn_huy_ThanhVien);
                btn_them.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(edtHoTenTV.getText().toString().equals("") | edtNamSinhTV.getText().toString().equals("")){
                            Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ thông tin để tiếp tục", Toast.LENGTH_SHORT).show();
                       return;
                        }
                        boolean check = new ThanhVienDao().themThanhVien(getActivity(), edtHoTenTV.getText().toString(), edtNamSinhTV.getText().toString());
                        recyclerview();
                        if (check) {
                            Toast.makeText(getActivity(), "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(getActivity(), "Thêm Thành Viên Thất Bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                btn_huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    private void recyclerview() {
        //data
        thanhVienDao = new ThanhVienDao();
        ArrayList<ThanhVien> list = thanhVienDao.getDSThanhVien(getContext());
        //Adapter
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewThanhVien.setLayoutManager(layoutManager);
        ThanhVienAdapter thanhVienAdapter = new ThanhVienAdapter(getContext(), list);
        recyclerViewThanhVien.setAdapter(thanhVienAdapter);
    }
}
package com.example.hoangttmph22653_Application.Fragment;

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
import com.example.hoangttmph22653_Application.adapter.AdapterLoaiSach;
import com.example.hoangttmph22653_Application.dao.LoaiSachDAO;
import com.example.hoangttmph22653_Application.models.LoaiSach;

import java.util.ArrayList;


public class Fragment_ql_loai_sach extends Fragment {
    LoaiSachDAO loaiSachDAO;
    RecyclerView recyclerViewLoaiSach;
    EditText edtThemLoaiSach;
    Button btnThem;

    public Fragment_ql_loai_sach() {
    }

    public static Fragment_ql_loai_sach newInstance() {
        Fragment_ql_loai_sach fragment = new Fragment_ql_loai_sach();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ql_loai_sach, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loaiSachDAO = new LoaiSachDAO();
        recyclerViewLoaiSach = view.findViewById(R.id.recyclerViewLoaiSach);
        edtThemLoaiSach = view.findViewById(R.id.edtLoaiSach);
        btnThem = view.findViewById(R.id.btn_Them_LoaiSach);
        recyclerViewLoadData();
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtThemLoaiSach.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "VUI LÒNG NHẬP TÊN SÁCH", Toast.LENGTH_SHORT).show();
                    return;
                }
              boolean check= loaiSachDAO.themLoaiSach(getActivity(),edtThemLoaiSach.getText().toString());
                if(check){
                    Toast.makeText(getActivity(), "THÊM THÀNH CÔNG", Toast.LENGTH_SHORT).show();
                    recyclerViewLoadData();
                    edtThemLoaiSach.setText("");
                }else {
                    Toast.makeText(getActivity(), "THÊM THẤT BẠI", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void recyclerViewLoadData() {
        ArrayList<LoaiSach> list = loaiSachDAO.getLoaiSach(getContext());
        //Adapter
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewLoaiSach.setLayoutManager(layoutManager);
        AdapterLoaiSach adapterLoaiSach = new AdapterLoaiSach(getContext(), list);
        recyclerViewLoaiSach.setAdapter(adapterLoaiSach);
    }
}
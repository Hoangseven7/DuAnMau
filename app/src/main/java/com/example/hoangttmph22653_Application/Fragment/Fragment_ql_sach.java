package com.example.hoangttmph22653_Application.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hoangttmph22653_Application.R;
import com.example.hoangttmph22653_Application.adapter.SachAdapter;
import com.example.hoangttmph22653_Application.dao.LoaiSachDAO;
import com.example.hoangttmph22653_Application.dao.SachDao;
import com.example.hoangttmph22653_Application.models.LoaiSach;
import com.example.hoangttmph22653_Application.models.Sach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;



public class Fragment_ql_sach extends Fragment {
    RecyclerView recyclerViewPhieuMuon;
    FloatingActionButton floatAdd;
    SachDao sachDao;

    public Fragment_ql_sach() {
    }

    public static Fragment_ql_sach newInstance() {
        Fragment_ql_sach fragment = new Fragment_ql_sach();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ql_sach, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewPhieuMuon = view.findViewById(R.id.recyclerViewSach);
        floatAdd = view.findViewById(R.id.floatAddSach);
        recyclerview();
        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_sach);
                Spinner spnLoaiSach = dialog.findViewById(R.id.spnLoaiSach);
                TextView edtTenSach_Sach = dialog.findViewById(R.id.edtTenSach_Sach);
                TextView edtGiaSach_Sach = dialog.findViewById(R.id.edtGiaSach_Sach);
                Button btnHuy_Sach = dialog.findViewById(R.id.btnHuy_Sach);
                Button btnThem_Sach = dialog.findViewById(R.id.btnThem_Sach);
                btnHuy_Sach.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                btnThem_Sach.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (edtGiaSach_Sach.getText().toString().equals("") | edtTenSach_Sach.getText().toString().equals("")) {
                            Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ thông tin để tiếp tục", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        HashMap<String, Object> hsTV = (HashMap<String, Object>) spnLoaiSach.getSelectedItem();
                        String maloai = String.valueOf(hsTV.get("MALOAI"));
                        SachDao sachDao = new SachDao();
                        boolean check = sachDao.themSach(getActivity(), edtTenSach_Sach.getText().toString(), Integer.parseInt(edtGiaSach_Sach.getText().toString()), Integer.parseInt(maloai));
                        if (check) {
                            Toast.makeText(getActivity(), "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                            recyclerview();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(getActivity(), "Thêm Thất Bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                getDataSach(spnLoaiSach);
                dialog.show();
            }
        });
    }

    private void recyclerview() {
        //data
        sachDao = new SachDao();
        ArrayList<Sach> list = sachDao.getDSDauSach_TENLOAI(getContext());
        //Adapter
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewPhieuMuon.setLayoutManager(layoutManager);
        SachAdapter sachAdapter = new SachAdapter(getContext(), list);
        recyclerViewPhieuMuon.setAdapter(sachAdapter);
    }

    private void getDataSach(Spinner spnSach) {
        LoaiSachDAO loaiSachDAO = new LoaiSachDAO();
        ArrayList<LoaiSach> list = loaiSachDAO.getLoaiSach(getContext());
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for (LoaiSach x : list) {
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("MALOAI", x.getId());
            hs.put("TENLOAI_MALOAI", x.getId() + ":" + x.getTenLoai());
            listHM.add(hs);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(), listHM, android.R.layout.simple_list_item_1,
                new String[]{"TENLOAI_MALOAI"},
                new int[]{android.R.id.text1}
        );
        spnSach.setAdapter(simpleAdapter);
    }
}
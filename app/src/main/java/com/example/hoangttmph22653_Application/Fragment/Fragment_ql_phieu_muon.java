package com.example.hoangttmph22653_Application.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
import com.example.hoangttmph22653_Application.adapter.PhieuMuonAdapter;
import com.example.hoangttmph22653_Application.dao.PhieuMuonDao;
import com.example.hoangttmph22653_Application.dao.SachDao;
import com.example.hoangttmph22653_Application.dao.ThanhVienDao;
import com.example.hoangttmph22653_Application.models.PhieuMuon;
import com.example.hoangttmph22653_Application.models.Sach;
import com.example.hoangttmph22653_Application.models.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class Fragment_ql_phieu_muon extends Fragment {
    RecyclerView recyclerViewPhieuMuon;
    FloatingActionButton floatAdd;
    PhieuMuonDao phieuMuonDao;
    RecyclerView recyclerView;
    List<PhieuMuon> list;

    public Fragment_ql_phieu_muon() {
    }

    public static Fragment_ql_phieu_muon newInstance() {
        Fragment_ql_phieu_muon fragment = new Fragment_ql_phieu_muon();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ql_phieu_muon, container, false);
        Button btnTimDay = v.findViewById(R.id.btnTimDay);
        EditText edTimDay = v.findViewById(R.id.ed_Timtheoday);
        recyclerView = v.findViewById(R.id.recyclerViewPhieuMuon);
        phieuMuonDao = new PhieuMuonDao();
return recyclerView;
    }
    void capNhatLv(){

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewPhieuMuon = view.findViewById(R.id.recyclerViewPhieuMuon);
        floatAdd = view.findViewById(R.id.floatAddPhieuMuon);
        recyclerview();
        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_them_phieumuon);
                Spinner spnThanhVien = dialog.findViewById(R.id.spnThanhVien);
                Spinner spnSach = dialog.findViewById(R.id.spnSach);
                TextView txtTien = dialog.findViewById(R.id.edtTien);
                Button btn_them = dialog.findViewById(R.id.btn_them_phieu_muon);
                Button btn_huy = dialog.findViewById(R.id.btn_huy_phieu_muon);

                getDataThanhVien(spnThanhVien);
                getDataSach(spnSach);
                spnSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        HashMap<String, Object> hsSach = (HashMap<String, Object>) spnSach.getSelectedItem();
                        txtTien.setText(hsSach.get("giathue").toString());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                btn_them.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //lấy mã thành viên
                        HashMap<String, Object> hsTV = (HashMap<String, Object>) spnThanhVien.getSelectedItem();
                        int matv = (int) hsTV.get("matv");
                        //lấy mã sách
                        HashMap<String, Object> hsSach = (HashMap<String, Object>) spnSach.getSelectedItem();
                        int masach = (int) hsSach.get("masach");
                        int tien = (int) hsSach.get("giathue");
                        themPhieuMuon(matv, masach, tien,dialog);
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
        phieuMuonDao = new PhieuMuonDao();
        ArrayList<PhieuMuon> list = phieuMuonDao.getDSPhieuMuon(getContext());
        //Adapter
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewPhieuMuon.setLayoutManager(layoutManager);
        PhieuMuonAdapter phieuMuonAdapter = new PhieuMuonAdapter(getContext(), list);
        recyclerViewPhieuMuon.setAdapter(phieuMuonAdapter);
    }

    private void themPhieuMuon(int matv, int masach, int tien,Dialog dialog) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("THONGTINLOGIN", Context.MODE_PRIVATE);
        String matt = sharedPreferences.getString("matt", "");
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String ngay = simpleDateFormat.format(date);


        PhieuMuon phieuMuon = new PhieuMuon(matv, matt, masach, ngay, 0,tien);


        boolean kiemtra = phieuMuonDao.themPhieuMuon(getContext(), phieuMuon);
        if (kiemtra) {
            Toast.makeText(getContext(), "Thêm Phiếu Mượn Thành Công", Toast.LENGTH_SHORT).show();
            recyclerview();
            dialog.dismiss();
        } else {
            Toast.makeText(getContext(), "Thêm Phiếu Mượn Thất Bại", Toast.LENGTH_SHORT).show();
        }
    }

    private void getDataThanhVien(Spinner spnThanhVien) {
        ThanhVienDao thanhVienDao = new ThanhVienDao();
        ArrayList<ThanhVien> list = thanhVienDao.getDSThanhVien(getContext());
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for (ThanhVien thanhVien : list) {
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("matv", thanhVien.getMatv());
            hs.put("hoten", thanhVien.getHoten());
            listHM.add(hs);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(), listHM, android.R.layout.simple_list_item_1,
                new String[]{"hoten"},
                new int[]{android.R.id.text1}
        );
        spnThanhVien.setAdapter(simpleAdapter);
    }

    private void getDataSach(Spinner spnSach) {
        SachDao sachDao = new SachDao();
        ArrayList<Sach> list = sachDao.getDSDauSach(getContext());
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for (Sach sach : list) {
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("masach", sach.getMasach());
            hs.put("tensach", sach.getTensach());
            hs.put("giathue", sach.getGiathue());
            listHM.add(hs);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(), listHM, android.R.layout.simple_list_item_1,
                new String[]{"tensach"},
                new int[]{android.R.id.text1}
        );
        spnSach.setAdapter(simpleAdapter);
    }

}
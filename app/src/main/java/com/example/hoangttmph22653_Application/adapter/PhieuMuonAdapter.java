package com.example.hoangttmph22653_Application.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hoangttmph22653_Application.R;
import com.example.hoangttmph22653_Application.dao.PhieuMuonDao;
import com.example.hoangttmph22653_Application.dao.SachDao;
import com.example.hoangttmph22653_Application.dao.ThanhVienDao;
import com.example.hoangttmph22653_Application.models.PhieuMuon;
import com.example.hoangttmph22653_Application.models.Sach;
import com.example.hoangttmph22653_Application.models.ThanhVien;

import java.util.ArrayList;
import java.util.HashMap;


public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.ViewHoder> {
    Context context;
    private ArrayList<PhieuMuon> list;

    public PhieuMuonAdapter(Context context, ArrayList<PhieuMuon> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycler_phieumuon, parent, false);
        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {
        String TrangThai = null;
        if (list.get(position).getTrasach() == 0) {
            TrangThai = "Chưa trả sách";
            holder.btnTraSach.setVisibility(View.VISIBLE);
        } else {
            TrangThai = "Đã Trả sách";
            holder.btnTraSach.setVisibility(View.GONE);
        }
        holder.txtMaPM.setText("MÃ PHIẾU MƯỢN:" + list.get(position).getMapm());
        holder.txtMaTV.setText("MÃ THÀNH VIÊN:" + list.get(position).getMatv());
        holder.txtTenTV.setText("TÊN THÀNH VIÊN:" + list.get(position).getTentv());
        holder.txtMaTT.setText("MÃ THỦ THƯ:" + list.get(position).getMatt());
        holder.txtTenTT.setText("TÊN THỦ THƯ:" + list.get(position).getTentt());
        holder.txtMaSach.setText("MÃ SÁCH:" + list.get(position).getMasach());
        holder.txtTenSach.setText("TÊN SÁCH:" + list.get(position).getTensach());
        holder.txtNgay.setText("NGÀY:" + list.get(position).getNgay());
        holder.txtTrangThai.setText("TRẠNG TRÁI:" + TrangThai);
        holder.txtTien.setText("GIÁ TIỀN:" + list.get(position).getTienthue());
        holder.btnTraSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhieuMuonDao phieuMuonDao = new PhieuMuonDao();
                Boolean kiemtra = phieuMuonDao.thayDoiTrangThai(context, list.get(holder.getAdapterPosition()).getMapm());
                if (kiemtra) {
                    list.clear();
                    list = phieuMuonDao.getDSPhieuMuon(context);
                    notifyDataSetChanged();
                    Toast.makeText(context, "Trả sách thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Trả sách không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.btnXoaPM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Bạn có muốn xóa không");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        PhieuMuonDao phieuMuonDao = new PhieuMuonDao();
                        int check = phieuMuonDao.xoaPhieuMuon(context, list.get(holder.getAdapterPosition()).getMapm());
                        switch (check) {
                            case 1:
                                list.clear();
                                list = phieuMuonDao.getDSPhieuMuon(context);
                                notifyDataSetChanged();
                                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                break;

                            case 0:
                                Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("No", null);
                builder.show();
            }
        });

        holder.item_Adapter_phieumuon.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("SỬA PHIẾU MƯỢN");
                view = (View) LayoutInflater.from(context).inflate(R.layout.dialog_sua_phieumuon, null);
                builder.setView(view);

                TextView giatienthue = view.findViewById(R.id.dialog_sua_txt_tien);
                Spinner spnSach = view.findViewById(R.id.dialog_spnSua_Sach_PhieuMuon);
                getDataSach(spnSach);
                Spinner spnThanhVien = view.findViewById(R.id.dialog_spnSua_ThanhVien_PhieuMuon);
                getDataThanhVien(spnThanhVien);

                spnSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        HashMap<String, Object> hsSach = (HashMap<String, Object>) spnSach.getSelectedItem();
                        giatienthue.setText(hsSach.get("giathue").toString());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                builder.setPositiveButton("SỬA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //lấy mã thành viên
                        HashMap<String, Object> hsTV = (HashMap<String, Object>) spnThanhVien.getSelectedItem();
                        int matv = (int) hsTV.get("matv");
                        //lấy mã sách
                        HashMap<String, Object> hsSach = (HashMap<String, Object>) spnSach.getSelectedItem();
                        int masach = (int) hsSach.get("masach");
                        PhieuMuonDao phieuMuonDao = new PhieuMuonDao();

                        PhieuMuon phieuMuon = new PhieuMuon(matv, list.get(holder.getAdapterPosition()).getMatt(), masach, list.get(holder.getAdapterPosition()).getNgay(), list.get(holder.getAdapterPosition()).getTrasach(), Integer.parseInt(giatienthue.getText().toString()));

                        String check = phieuMuonDao.capNhatPhieuMuon(context, String.valueOf(list.get(holder.getAdapterPosition()).getMapm()), phieuMuon);
                        Toast.makeText(context, check, Toast.LENGTH_SHORT).show();
                        list.clear();
                        list = phieuMuonDao.getDSPhieuMuon(context);
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Hủy", null);
                builder.show();
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void getDataThanhVien(Spinner spnThanhVien) {
        ThanhVienDao thanhVienDao = new ThanhVienDao();
        ArrayList<ThanhVien> list = thanhVienDao.getDSThanhVien(context);
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for (ThanhVien thanhVien : list) {
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("matv", thanhVien.getMatv());
            hs.put("hoten", thanhVien.getHoten());
            listHM.add(hs);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                context, listHM, android.R.layout.simple_list_item_1,
                new String[]{"hoten"},
                new int[]{android.R.id.text1}
        );
        spnThanhVien.setAdapter(simpleAdapter);
    }

    private void getDataSach(Spinner spnSach) {
        SachDao sachDao = new SachDao();
        ArrayList<Sach> list = sachDao.getDSDauSach(context);
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for (Sach sach : list) {
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("masach", sach.getMasach());
            hs.put("tensach", sach.getTensach());
            hs.put("giathue", sach.getGiathue());
            listHM.add(hs);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                context, listHM, android.R.layout.simple_list_item_1,
                new String[]{"tensach"},
                new int[]{android.R.id.text1}
        );
        spnSach.setAdapter(simpleAdapter);
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        TextView txtMaPM, txtMaTV, txtTenTV, txtMaTT, txtTenTT, txtMaSach, txtTenSach, txtNgay, txtTrangThai, txtTien;
        Button btnTraSach, btnXoaPM;
        LinearLayout item_Adapter_phieumuon;

        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            txtMaPM = itemView.findViewById(R.id.txtMaPM);
            txtMaTV = itemView.findViewById(R.id.txtMaTV);
            txtTenTV = itemView.findViewById(R.id.txtTenTV);
            txtMaTT = itemView.findViewById(R.id.txtMaTT);
            txtTenTT = itemView.findViewById(R.id.txtTenTT);
            txtMaSach = itemView.findViewById(R.id.txtMaSach);
            txtTenSach = itemView.findViewById(R.id.txtTenSach);
            txtNgay = itemView.findViewById(R.id.txtNgay);
            txtTrangThai = itemView.findViewById(R.id.txtTrangThai);
            txtTien = itemView.findViewById(R.id.txtTien);
            btnTraSach = itemView.findViewById(R.id.btnTraSach);
            btnXoaPM = itemView.findViewById(R.id.btnXoaPM);
            item_Adapter_phieumuon = itemView.findViewById(R.id.item_Adapter_phieumuon);

        }
    }

}

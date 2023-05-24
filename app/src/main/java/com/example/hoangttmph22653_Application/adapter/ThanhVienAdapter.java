package com.example.hoangttmph22653_Application.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hoangttmph22653_Application.R;
import com.example.hoangttmph22653_Application.dao.ThanhVienDao;
import com.example.hoangttmph22653_Application.models.ThanhVien;

import java.util.ArrayList;


public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ViewHodel> {
    Context context;
    ArrayList<ThanhVien> list;

    public ThanhVienAdapter(Context context, ArrayList<ThanhVien> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycler_thanhvien, parent, false);
        return new ViewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodel holder, int position) {
        holder.txtMATV_ThanhVien.setText("MÃ THÀNH VIÊN :" + String.valueOf(list.get(position).getMatv()));
        holder.txtHoten_ThanhVien.setText("HỌ VÀ TÊN :" + list.get(position).getHoten());
        holder.txtNamSinh_ThanhVien.setText("MÃ SINH VIÊN :" + list.get(position).getNamsinh());
        holder.img_delete_thanhvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ThanhVienDao thanhVienDao = new ThanhVienDao();
                        int check = thanhVienDao.xoaThanhVien(context, list.get(holder.getAdapterPosition()).getMatv());
                        switch (check) {
                            case 1:
                                list.clear();
                                list = thanhVienDao.getDSThanhVien(context);
                                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();
                                break;
                            case -1:
                                Toast.makeText(context, "Không Thể xóa thành viên  này vì đã có trong phiếu mượn", Toast.LENGTH_SHORT).show();
                                break;
                            case 0:
                                Toast.makeText(context, "xóa thành viên không thành công ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Hủy", null);
                builder.show();
            }
        });
        holder.item_Adapter_thanhvien.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("SỬA THÀNH VIÊN");
                view = (View) LayoutInflater.from(context).inflate(R.layout.dialog_sua_thanhvien, null);
                builder.setView(view);
                EditText hoten = view.findViewById(R.id.dialog_edtSua_NameTV);
                EditText namsinh = view.findViewById(R.id.dialog_edtSua_DateTV);

                builder.setPositiveButton("SỬA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (hoten.getText().toString().equals("") || namsinh.getText().toString().equals("")) {
                            Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                            return ;
                        }
                        ThanhVien thanhVien = new ThanhVien(list.get(holder.getAdapterPosition()).getMatv(), hoten.getText().toString(),namsinh.getText().toString());
                        ThanhVienDao thanhVienDao = new ThanhVienDao();
                        String check = thanhVienDao.capNhatThanhVien(context, String.valueOf(list.get(holder.getAdapterPosition()).getMatv()), thanhVien);
                        Toast.makeText(context, check, Toast.LENGTH_SHORT).show();
                        list.clear();
                        list = thanhVienDao.getDSThanhVien(context);
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

    public class ViewHodel extends RecyclerView.ViewHolder {
        TextView txtMATV_ThanhVien, txtHoten_ThanhVien, txtNamSinh_ThanhVien;
        ImageView img_delete_thanhvien;
        LinearLayout item_Adapter_thanhvien;

        public ViewHodel(@NonNull View itemView) {
            super(itemView);
            txtMATV_ThanhVien = itemView.findViewById(R.id.txtMaTV_ThanhVien);
            txtHoten_ThanhVien = itemView.findViewById(R.id.txtHotenTV_ThanhVien);
            txtNamSinh_ThanhVien = itemView.findViewById(R.id.txtNamSinhTV_ThanhVien);
            img_delete_thanhvien = itemView.findViewById(R.id.img_delete_thanhvien);
            item_Adapter_thanhvien = itemView.findViewById(R.id.item_Adapter_thanhvien);
        }
    }
}

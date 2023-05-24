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
import com.example.hoangttmph22653_Application.dao.LoaiSachDAO;
import com.example.hoangttmph22653_Application.models.LoaiSach;

import java.util.ArrayList;


public class AdapterLoaiSach extends RecyclerView.Adapter<AdapterLoaiSach.ViewHodel> {
    Context context;
    ArrayList<LoaiSach> list;

    public AdapterLoaiSach(Context context, ArrayList<LoaiSach> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycler_loaisach, parent, false);
        return new ViewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodel holder, int position) {
        holder.txtMaLoai_loaisach.setText("MÃ LOẠI:" + String.valueOf(list.get(position).getId()));
        holder.txtTenLoai_loaisach.setText("TÊN LOẠI:" + String.valueOf(list.get(position).getTenLoai()));
        holder.edtDelete_loaisach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        LoaiSachDAO loaiSachDAO = new LoaiSachDAO();
                        int check = loaiSachDAO.xoaloaiSach(context, list.get(holder.getAdapterPosition()).getId());
                        switch (check) {
                            case 1:
                                list.clear();
                                list = loaiSachDAO.getLoaiSach(context);
                                notifyDataSetChanged();
                                break;
                            case -1:
                                Toast.makeText(context, "Không Thể xóa loại sách này vì đã có sách thuộc thể loại này", Toast.LENGTH_SHORT).show();
                                break;
                            case 0:
                                Toast.makeText(context, "xóa loại sách không thành công ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Hủy", null);
                builder.show();
            }
        });
        holder.item_SuaLS.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("SỬA LOẠI SÁCH");
                view = (View) LayoutInflater.from(context).inflate(R.layout.dialog_sua_loaisach, null);
                builder.setView(view);
                EditText loaisach = view.findViewById(R.id.dialog_edtSua_LoaiSach);

                builder.setPositiveButton("SỬA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (loaisach.getText().toString().equals("")) {
                            Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                        } else {
                            LoaiSach sach = new LoaiSach(list.get(holder.getAdapterPosition()).getId(), loaisach.getText().toString());
                            LoaiSachDAO loaiSachDAO = new LoaiSachDAO();
                            String check = loaiSachDAO.capNhatLoaiSach(context, String.valueOf(list.get(holder.getAdapterPosition()).getId()), sach);
                            Toast.makeText(context, check, Toast.LENGTH_SHORT).show();
                            list.clear();
                            list = loaiSachDAO.getLoaiSach(context);
                            notifyDataSetChanged();
                        }
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
        TextView txtMaLoai_loaisach, txtTenLoai_loaisach;
        ImageView edtDelete_loaisach;
        LinearLayout item_SuaLS;

        public ViewHodel(@NonNull View itemView) {
            super(itemView);
            txtMaLoai_loaisach = itemView.findViewById(R.id.txtMaLoai_loaisach);
            txtTenLoai_loaisach = itemView.findViewById(R.id.txtTenLoai_loaisach);
            edtDelete_loaisach = itemView.findViewById(R.id.edtDelete_loaisach);
            item_SuaLS = itemView.findViewById(R.id.item_Adapter_loaiSach);
        }
    }
}

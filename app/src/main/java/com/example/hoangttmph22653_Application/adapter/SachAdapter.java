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
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hoangttmph22653_Application.R;
import com.example.hoangttmph22653_Application.dao.LoaiSachDAO;
import com.example.hoangttmph22653_Application.dao.SachDao;
import com.example.hoangttmph22653_Application.models.LoaiSach;
import com.example.hoangttmph22653_Application.models.Sach;

import java.util.ArrayList;
import java.util.HashMap;


public class SachAdapter extends RecyclerView.Adapter<SachAdapter.ViewHodel> {
    Context context;
    ArrayList<Sach> list;

    public SachAdapter(Context context, ArrayList<Sach> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycler_sach, parent, false);
        return new ViewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodel holder, int position) {
        holder.txtMaSach_Sach.setText("MÃ SÁCH" + String.valueOf(list.get(position).getMasach()));
        holder.txtTenSach_Sach.setText("TÊN SÁCH:" + list.get(position).getTensach());
        holder.txtGiaSach_Sach.setText("GIÁ SÁCH:" + String.valueOf(list.get(position).getGiathue()));
        holder.txtLoaiSach_Sach.setText(list.get(position).getMaloai() + ":" + list.get(position).getLoaisach());
        holder.img_delete_sach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Bạn có muốn xóa không");
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SachDao sachDao = new SachDao();
                        int check = sachDao.xoaSach(context, list.get(holder.getAdapterPosition()).getMasach());
                        switch (check) {
                            case 1:
                                list.clear();
                                list = sachDao.getDSDauSach_TENLOAI(context);
                                notifyDataSetChanged();
                                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                break;

                            case -1:
                                Toast.makeText(context, "Không thể xóa sách này vì phiếu mượn có", Toast.LENGTH_SHORT).show();
                                break;

                            case 0:
                                Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });

        holder.item_Adapter_sach.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("SỬA SÁCH");
                view = (View) LayoutInflater.from(context).inflate(R.layout.dialog_sua_sach, null);
                builder.setView(view);
                EditText tensach = view.findViewById(R.id.dialog_edtSua_TenSach_Sach);
                EditText giasach = view.findViewById(R.id.dialog_edtSua_GiaSach_sach);


                Spinner spnLoaiSach = view.findViewById(R.id.dialog_spn_SuaLoaiSach_Sach);
                getDataSach(spnLoaiSach);
                builder.setPositiveButton("SỬA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (tensach.getText().toString().equals("") || giasach.getText().toString().equals("")) {
                            Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                            return ;
                        }
                         HashMap<String, Object> hsTV = (HashMap<String, Object>) spnLoaiSach.getSelectedItem();
                        int maloai = Integer.parseInt(String.valueOf(hsTV.get("MALOAI")));
                        Sach sach = new Sach(list.get(holder.getAdapterPosition()).getMasach(), tensach.getText().toString(), Integer.parseInt(giasach.getText().toString()), maloai);
                        SachDao sachDao = new SachDao();
                        String check = sachDao.capNhatSach(context, String.valueOf(list.get(holder.getAdapterPosition()).getMasach()), sach);
                        Toast.makeText(context, check, Toast.LENGTH_SHORT).show();
                        list.clear();
                        list = sachDao.getDSDauSach_TENLOAI(context);
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Hủy", null);
                builder.show();
                return false;
            }
        });
    }

    private void getDataSach(Spinner spnSach) {
        LoaiSachDAO loaiSachDAO = new LoaiSachDAO();
        ArrayList<LoaiSach> list = loaiSachDAO.getLoaiSach(context);
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for (LoaiSach x : list) {
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("MALOAI", x.getId());
            hs.put("TENLOAI_MALOAI", x.getId() + ":" + x.getTenLoai());
            listHM.add(hs);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                context, listHM, android.R.layout.simple_list_item_1,
                new String[]{"TENLOAI_MALOAI"},
                new int[]{android.R.id.text1}
        );
        spnSach.setAdapter(simpleAdapter);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHodel extends RecyclerView.ViewHolder {

        TextView txtMaSach_Sach, txtTenSach_Sach, txtGiaSach_Sach, txtLoaiSach_Sach;
        ImageView img_delete_sach;
        LinearLayout item_Adapter_sach;

        public ViewHodel(@NonNull View itemView) {

            super(itemView);

            txtMaSach_Sach = itemView.findViewById(R.id.txtMaSach_Sach);
            txtTenSach_Sach = itemView.findViewById(R.id.txtTenSach_Sach);
            txtGiaSach_Sach = itemView.findViewById(R.id.txtGiaSach_Sach);
            txtLoaiSach_Sach = itemView.findViewById(R.id.txtLoaiSach_Sach);
            img_delete_sach = itemView.findViewById(R.id.img_delete_sach);
            item_Adapter_sach = itemView.findViewById(R.id.item_Adapter_sach);

        }
    }
}

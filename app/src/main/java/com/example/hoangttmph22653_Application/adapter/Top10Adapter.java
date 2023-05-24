package com.example.hoangttmph22653_Application.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hoangttmph22653_Application.R;
import com.example.hoangttmph22653_Application.models.Sach;

import java.util.ArrayList;


public class Top10Adapter extends RecyclerView.Adapter<Top10Adapter.ViewHoder>{
    Context context;
    ArrayList<Sach> list;

    public Top10Adapter(Context context, ArrayList<Sach> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.recycler_top10, parent, false);
        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {
        holder.txtMasach_Top10.setText("MÃ SÁCH:"+String.valueOf(list.get(position).getMasach()));
        holder.txtTenSach_Top10.setText("TÊN SÁCH:"+list.get(position).getTensach());
        holder.txtSoLuong_Top10.setText("SỐ LƯỢNG MƯỢN:"+String.valueOf(list.get(position).getSoluongdamuon()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        TextView txtMasach_Top10, txtTenSach_Top10, txtSoLuong_Top10;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            txtMasach_Top10 = itemView.findViewById(R.id.txtMasach_Top10);
            txtTenSach_Top10 = itemView.findViewById(R.id.txtTenSach_Top10);
            txtSoLuong_Top10 = itemView.findViewById(R.id.txtSoLuong_Top10);
        }
    }
}

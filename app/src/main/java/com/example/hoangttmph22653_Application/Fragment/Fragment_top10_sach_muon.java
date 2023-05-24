package com.example.hoangttmph22653_Application.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hoangttmph22653_Application.R;
import com.example.hoangttmph22653_Application.adapter.Top10Adapter;
import com.example.hoangttmph22653_Application.dao.ThongkeDao;
import com.example.hoangttmph22653_Application.models.Sach;

import java.util.ArrayList;



public class Fragment_top10_sach_muon extends Fragment {
    ThongkeDao thongkedao;
    RecyclerView recyclerViewTop10;
    public Fragment_top10_sach_muon() {
    }
    public static Fragment_top10_sach_muon newInstance() {
        Fragment_top10_sach_muon fragment = new Fragment_top10_sach_muon();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_top10_sach_muon, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        thongkedao = new ThongkeDao();
        recyclerViewTop10=view.findViewById(R.id.recyclerViewTop10);
        ArrayList<Sach> list = thongkedao.getTop10(getContext());
        //Adapter
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewTop10.setLayoutManager(layoutManager);
        Top10Adapter top10Adapter = new Top10Adapter(getContext(), list);
        recyclerViewTop10.setAdapter(top10Adapter);
    }

}
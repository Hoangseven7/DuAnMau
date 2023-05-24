package com.example.hoangttmph22653_Application.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hoangttmph22653_Application.R;
import com.example.hoangttmph22653_Application.dao.ThongkeDao;

import java.util.Calendar;



public class Fragment_Doanh_thu extends Fragment {
    public Fragment_Doanh_thu() {
    }

    public static Fragment_Doanh_thu newInstance() {
        Fragment_Doanh_thu fragment = new Fragment_Doanh_thu();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment__doanh_thu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText edtStart = view.findViewById(R.id.edDayStart);
        EditText edtEnd = view.findViewById(R.id.edtDayEnd);
        Button btnThongKe = view.findViewById(R.id.btnThongKe);
        TextView txtKetQua = view.findViewById(R.id.txtKetQua);
        Calendar calendar = Calendar.getInstance();
        edtStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                       String ngay="",thang="";
                       if(i2<10){
                           ngay="0"+i2;
                       } else {
                           ngay=String.valueOf(i2);
                       }
                       if((i1+1) <10){
                           thang="0"+(i1+1);
                       } else {
                           thang=String.valueOf((i1+1));
                       }
                        edtStart.setText( i+ "/" +thang + "/" +ngay);
                    }
                }, calendar.get(Calendar.YEAR)
                        , calendar.get(Calendar.MONTH)
                        , calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        edtEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        String ngay="",thang="";
                        if(i2<10){
                            ngay="0"+i2;
                        } else {
                            ngay=String.valueOf(i2);
                        }
                        if((i1+1) <10){
                            thang="0"+(i1+1);
                        } else {
                            thang=String.valueOf((i1+1));
                        }
                        edtEnd.setText( i+ "/" +thang + "/" +ngay);
                    }
                }, calendar.get(Calendar.YEAR)
                        , calendar.get(Calendar.MONTH)
                        , calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThongkeDao thongkeDao=new ThongkeDao();
                int doanhthu=thongkeDao.getDoanhThu(getContext(),edtStart.getText().toString(),edtEnd.getText().toString());
            txtKetQua.setText("DOANH THU:"+doanhthu);
            }
        });
    }
}
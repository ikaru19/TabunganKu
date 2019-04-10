package com.syafrizal.tabunganku.Fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.syafrizal.tabunganku.Activities.DetailActivity;
import com.syafrizal.tabunganku.Constant;
import com.syafrizal.tabunganku.Databases.DatabaseHelper;
import com.syafrizal.tabunganku.Models.Tabungan;
import com.syafrizal.tabunganku.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabunganFragment extends Fragment implements TabunganAdapter.OnAdapterClickListener {
    private SharedPreferences preferences;
    int user_money;
    TextView tv_Total;
    private List<Tabungan> tabungans = new ArrayList<>();
    private RecyclerView recyclerView;
    DatabaseHelper dbHelper;
    Tabungan tabungan;

    public TabunganFragment() {
        // Required empty public constructor


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tabungan, container, false);


        //getting shared preferences
        preferences = getActivity().getSharedPreferences(Constant.USER_SP, MODE_PRIVATE);
        tv_Total = v.findViewById(R.id.textViewTotal);
        user_money = preferences.getInt(Constant.USER_MONEY, 0);
        if (user_money <= 0) {
            tv_Total.setTextColor(Color.RED);
        }

        tv_Total.setText("Rp. " + user_money);

        //getting rv
        recyclerView = v.findViewById(R.id.rv_tabungan);


        dbHelper = new DatabaseHelper(getActivity());
        tabungans = new ArrayList<Tabungan>();
        tabungans = initData1();
        TabunganAdapter adapter = new TabunganAdapter(tabungans, getActivity());
        adapter.setListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);


        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    private ArrayList<Tabungan> initData1() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor data = dbHelper.getData();
        ArrayList<Tabungan> list = new ArrayList<>();

        data.moveToLast();

        if (data.getCount() > 0) {
            do {
                tabungan = new Tabungan();
                tabungan.setId(Integer.parseInt(data.getString(0)));
                tabungan.setJudul(data.getString(1));
                tabungan.setJumlah(Integer.parseInt(data.getString(2)));
                tabungan.setTipe(data.getString(3));
                list.add(tabungan);
                data.moveToPrevious();
            } while (!data.isBeforeFirst());

        }

        return list;
    }

    public void getData() {

    }

    @Override
    public void DetailonClick(Tabungan tabungan) {
        Intent intent = new Intent(getActivity(),DetailActivity.class);
        intent.putExtra("id",tabungan.getId());
        intent.putExtra("judul",tabungan.getJudul());
        intent.putExtra("jumlah",tabungan.getJumlah());
        intent.putExtra("tipe",tabungan.getTipe());
        startActivity(intent);

    }

}

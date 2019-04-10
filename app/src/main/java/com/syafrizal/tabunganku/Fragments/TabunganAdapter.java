package com.syafrizal.tabunganku.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.syafrizal.tabunganku.Models.Tabungan;
import com.syafrizal.tabunganku.R;

import java.util.List;

public class TabunganAdapter extends RecyclerView.Adapter<TabunganAdapter.ViewHolder> {
    private List<Tabungan> tabungans;
    private Context context;
    public OnAdapterClickListener listener;

    interface OnAdapterClickListener{
        void DetailonClick(Tabungan tabungan);
    }

    public TabunganAdapter(List<Tabungan> tabungans, Context context) {
        this(tabungans,context,null);
    }

    public TabunganAdapter(List<Tabungan> tabungans, Context context, OnAdapterClickListener listener) {
        this.tabungans = tabungans;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TabunganAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_tabungan,viewGroup,false);
        return new ViewHolder(view);
    }

    public void setListener(OnAdapterClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull TabunganAdapter.ViewHolder viewHolder, int i) {
        Tabungan tabungan = tabungans.get(i);
        viewHolder.judulText.setText(tabungan.getJudul());
        viewHolder.jumlahText.setText("Rp."+ Integer.toString(tabungan.getJumlah()));
        if (tabungan.getTipe().equalsIgnoreCase("pemasukkan")){
            viewHolder.tipeText.setTextColor(Color.GREEN);
        }else{
            viewHolder.tipeText.setTextColor(Color.RED);
        }
        viewHolder.tipeText.setText(tabungan.getTipe());
    }



    @Override
    public int getItemCount() {
        return (tabungans != null) ? tabungans.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView judulText;
        TextView jumlahText;
        TextView tipeText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            judulText = itemView.findViewById(R.id.tv_judul);
            jumlahText = itemView.findViewById(R.id.tv_jumlah);
            tipeText = itemView.findViewById(R.id.tv_tipe);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.DetailonClick(tabungans.get(getAdapterPosition()));
                }
            });
        }
    }
}

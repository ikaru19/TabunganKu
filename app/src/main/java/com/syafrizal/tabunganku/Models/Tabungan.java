package com.syafrizal.tabunganku.Models;

public class Tabungan {
    private int id;
    private String judul;
    private int jumlah;
    private String tipe;


    public Tabungan() {
    }

    public Tabungan(String judul, int jumlah, String tipe) {
        this.judul = judul;
        this.jumlah = jumlah;
        this.tipe = tipe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }
}

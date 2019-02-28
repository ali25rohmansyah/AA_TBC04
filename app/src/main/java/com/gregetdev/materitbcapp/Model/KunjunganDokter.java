package com.gregetdev.materitbcapp.Model;

public class KunjunganDokter {

    private String Catatan, Judul, Tanggal;

    public KunjunganDokter() {
    }

    public KunjunganDokter(String catatan, String judul, String tanggal) {
        Catatan = catatan;
        Judul = judul;
        Tanggal = tanggal;
    }

    public String getCatatan() {
        return Catatan;
    }

    public void setCatatan(String catatan) {
        Catatan = catatan;
    }

    public String getJudul() {
        return Judul;
    }

    public void setJudul(String judul) {
        Judul = judul;
    }

    public String getTanggal() {
        return Tanggal;
    }

    public void setTanggal(String tanggal) {
        Tanggal = tanggal;
    }
}

package com.gregetdev.materitbcapp.Model;

public class Users {
    private String JenisTb, Jkel, Kota, KunjunganDokter, MinumObat, Nama, NoHandphone, PostTest, PreTest, TanggalDiagnosa, TanggalLahir;

    public Users() {
    }

    public Users(String jenisTb, String jkel, String kota, String kunjunganDokter, String minumObat, String nama, String noHandphone, String postTest, String preTest, String tanggalDiagnosa, String tanggalLahir) {
        JenisTb = jenisTb;
        Jkel = jkel;
        Kota = kota;
        KunjunganDokter = kunjunganDokter;
        MinumObat = minumObat;
        Nama = nama;
        NoHandphone = noHandphone;
        PostTest = postTest;
        PreTest = preTest;
        TanggalDiagnosa = tanggalDiagnosa;
        TanggalLahir = tanggalLahir;
    }

    public String getJenisTb() {
        return JenisTb;
    }

    public void setJenisTb(String jenisTb) {
        JenisTb = jenisTb;
    }

    public String getJkel() {
        return Jkel;
    }

    public void setJkel(String jkel) {
        Jkel = jkel;
    }

    public String getKota() {
        return Kota;
    }

    public void setKota(String kota) {
        Kota = kota;
    }

    public String getKunjunganDokter() {
        return KunjunganDokter;
    }

    public void setKunjunganDokter(String kunjunganDokter) {
        KunjunganDokter = kunjunganDokter;
    }

    public String getMinumObat() {
        return MinumObat;
    }

    public void setMinumObat(String minumObat) {
        MinumObat = minumObat;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getNoHandphone() {
        return NoHandphone;
    }

    public void setNoHandphone(String noHandphone) {
        NoHandphone = noHandphone;
    }

    public String getPostTest() {
        return PostTest;
    }

    public void setPostTest(String postTest) {
        PostTest = postTest;
    }

    public String getPreTest() {
        return PreTest;
    }

    public void setPreTest(String preTest) {
        PreTest = preTest;
    }

    public String getTanggalDiagnosa() {
        return TanggalDiagnosa;
    }

    public void setTanggalDiagnosa(String tanggalDiagnosa) {
        TanggalDiagnosa = tanggalDiagnosa;
    }

    public String getTanggalLahir() {
        return TanggalLahir;
    }

    public void setTanggalLahir(String tanggalLahir) {
        TanggalLahir = tanggalLahir;
    }
}


package com.gregetdev.materitbcapp.Model;

public class MateriModel {
    private String Judul,materi,type;

    public MateriModel() {

    }

    public MateriModel(String judul, String materi, String type) {
        Judul = judul;
        this.materi = materi;
        this.type = type;
    }

    public String getJudul() {
        return Judul;
    }

    public void setJudul(String judul) {
        Judul = judul;
    }

    public String getMateri() {
        return materi;
    }

    public void setMateri(String materi) {
        this.materi = materi;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

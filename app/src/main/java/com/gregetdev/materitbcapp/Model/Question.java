package com.gregetdev.materitbcapp.Model;

public class Question {
    private String jawabanA;
    private String jawabanB;
    private String jawabanBenar;
    private String jawabanC;
    private String jawabanD;
    private String pertanyaan;

    public Question() {
    }

    public Question(String jawabanA, String jawabanB, String jawabanBenar, String jawabanC, String jawabanD, String pertanyaan) {
        this.jawabanA = jawabanA;
        this.jawabanB = jawabanB;
        this.jawabanBenar = jawabanBenar;
        this.jawabanC = jawabanC;
        this.jawabanD = jawabanD;
        this.pertanyaan = pertanyaan;
    }

    public String getJawabanA() {
        return jawabanA;
    }

    public void setJawabanA(String jawabanA) {
        this.jawabanA = jawabanA;
    }

    public String getJawabanB() {
        return jawabanB;
    }

    public void setJawabanB(String jawabanB) {
        this.jawabanB = jawabanB;
    }

    public String getJawabanBenar() {
        return jawabanBenar;
    }

    public void setJawabanBenar(String jawabanBenar) {
        this.jawabanBenar = jawabanBenar;
    }

    public String getJawabanC() {
        return jawabanC;
    }

    public void setJawabanC(String jawabanC) {
        this.jawabanC = jawabanC;
    }

    public String getJawabanD() {
        return jawabanD;
    }

    public void setJawabanD(String jawabanD) {
        this.jawabanD = jawabanD;
    }

    public String getPertanyaan() {
        return pertanyaan;
    }

    public void setPertanyaan(String pertanyaan) {
        this.pertanyaan = pertanyaan;
    }
}



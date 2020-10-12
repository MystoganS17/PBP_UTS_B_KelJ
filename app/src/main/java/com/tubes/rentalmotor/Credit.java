package com.tubes.rentalmotor;

public class Credit {
    public String npm;
    public String nama;
    public String fakultas;
    public String jurusan;

    public Credit (String npm, String nama, String fakultas, String jurusan){
        this.npm = npm;
        this.nama = nama;
        this.fakultas = fakultas;
        this.jurusan = jurusan;
    }

    public String getNpm(){
        return npm;
    }

    public void setNpm(String npm){
        this.npm = npm;
    }

    public String getNama(){
        return nama;
    }

    public void setNama(String nama){
        this.nama = nama;
    }

    public String getFakultas(){
        return fakultas;
    }

    public void setFakultas(String fakultas){
        this.fakultas = fakultas;
    }

    public String getJurusan(){
        return jurusan;
    }

    public void setJurusan(String jurusan){
        this.jurusan = jurusan;
    }

}
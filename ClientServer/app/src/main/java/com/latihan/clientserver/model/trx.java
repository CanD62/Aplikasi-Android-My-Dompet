package com.latihan.clientserver.model;

import com.google.gson.annotations.SerializedName;

public class trx {

    @SerializedName("transaksi_id")
    private String transaksi_id;
    @SerializedName("status")
    private String status;
    @SerializedName("jumlah")
    private String jumlah;
    @SerializedName("keterangan")
    private String keterangan;
    @SerializedName("tanggal")
    private String tanggal;


    public String gettransaksi_id() {
        return transaksi_id;
    }

    public void settransaksi_id(String transaksi_id) {
        this.transaksi_id = transaksi_id;
    }

    public String getstatus() {
        return status;
    }

    public void setstatus(String status) {
        this.status = status;
    }

    public String getjumlah() {
        return jumlah;
    }

    public void setJob(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getketerangan() {
        return keterangan;
    }

    public void setketerangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String gettanggal() {
        return tanggal;
    }

    public void settanggal(String tanggal) {
        this.tanggal = tanggal;
    }

}

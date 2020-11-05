package com.latihan.clientserver.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class data_sum {
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("masuk")
    @Expose
    private String masuk;
    @SerializedName("keluar")
    @Expose
    private String keluar;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getMasuk() {
        return masuk;
    }

    public void setMasuk(String masuk) {
        this.masuk = masuk;
    }

    public String getKeluar() {
        return keluar;
    }

    public void setKeluar(String keluar) {
        this.keluar = keluar;
    }

}

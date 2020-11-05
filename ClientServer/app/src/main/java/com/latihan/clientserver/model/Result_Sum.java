package com.latihan.clientserver.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result_Sum {
    @SerializedName("kode")
    @Expose
    private Integer kode;
    @SerializedName("pesan")
    @Expose
    private List<data_sum> pesan = null;

    public Integer getKode() {
        return kode;
    }

    public void setKode(Integer kode) {
        this.kode = kode;
    }

    public List<data_sum> getPesan() {
        return pesan;
    }

    public void setPesan(List<data_sum> pesan) {
        this.pesan = pesan;
    }



}

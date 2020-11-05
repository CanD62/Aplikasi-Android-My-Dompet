package com.latihan.clientserver.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result_Data {

    @SerializedName("kode")
    @Expose
    private Integer kode;
    @SerializedName("data")
    @Expose
    private List<trx> data = null;

    public Integer getKode() {
        return kode;
    }

    public void setKode(Integer kode) {
        this.kode = kode;
    }

    public List<trx> getData() {
        return data;
    }

    public void setData(List<trx> data) {
        this.data = data;
    }
}

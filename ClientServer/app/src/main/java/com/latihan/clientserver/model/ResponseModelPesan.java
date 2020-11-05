package com.latihan.clientserver.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Rohmad on 12-Sep-19.
 */

@SuppressWarnings("unused")
public class ResponseModelPesan {

    @SerializedName("kode")
    private String mKode;
    public String getKode() {
        return mKode;
    }
    public void setKode(String kode) {
        mKode = kode;
    }


    List<DataModelInfo> result;  //array yang ada pada nilai kembalian web
    public List<DataModelInfo> getResult() {
        return result;
    }

    public void setResult(List<DataModelInfo> result) {
        this.result = result;
    }



    @SerializedName("pesan")
    private String mPesan;
    public String getPesan() {
        return mPesan;
    }
    public void setPesan(String pesan) {
        mPesan = pesan;
    }


}

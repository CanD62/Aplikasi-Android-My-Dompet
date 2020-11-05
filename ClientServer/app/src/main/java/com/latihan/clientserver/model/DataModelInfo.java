package com.latihan.clientserver.model;


import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class DataModelInfo {

    @SerializedName("transaksi_id")
    private String mId;
    public String getId() {
        return mId;
    }
    public void setId(String Id) {
        mId = Id;
    }

    @SerializedName("status")
    private String mJudul;
    public String getJudul() {
        return mJudul;
    }
    public void setJudul(String Judul) {
        mJudul = Judul;
    }

    @SerializedName("jumlah")
    private String mIsi;
    public String getIsi() {
        return mIsi;
    }
    public void setIsi(String Isi) {
        mIsi = Isi;
    }

    @SerializedName("keterangan")
    private String mOleh;
    public String getOleh() {
        return mOleh;
    }
    public void setOleh(String Oleh) {
        mOleh = Oleh;
    }


    @SerializedName("tanggal")
    private String mTgl;
    public String getTgl() {
        return mTgl;
    }
    public void set(String Tgl) {
        mTgl = Tgl;
    }


}

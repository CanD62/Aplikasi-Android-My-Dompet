package com.latihan.clientserver.api;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import com.latihan.clientserver.model.ResponseModelInfo;
import com.latihan.clientserver.model.Result_Data;
import com.latihan.clientserver.model.Result_Sum;

/**
 * Created by Rohmad on 12-Sep-19.
 */


public interface RestApiInfo {
    @FormUrlEncoded
    @POST("input.php")
    Call<ResponseModelInfo> inputTrx(@Field("status") String status,
                                      @Field("jumlah") String edit_jumlah,
                                      @Field("keterangan") String edit_keterangan
    );


    @POST("data.php")
    Call<Result_Data> getTrx();

    @POST("data-saldo.php")
    Call<Result_Sum> getSaldo();

    @FormUrlEncoded
    @POST("edit.php")
    Call<Result_Data> getTrx(@Field("transaksi_id") String transaksi_id);


    @FormUrlEncoded
    @POST("update.php")
    Call<ResponseModelInfo> updateTrx(@Field("transaksi_id") String transaksi_id,
                                @Field("status") String status,
                                @Field("jumlah") String edit_jumlah,
                                @Field("tanggal") String tanggal,
                                @Field("keterangan") String edit_keterangan
    );


    @FormUrlEncoded
    @POST("hapus.php")
    Call<ResponseModelInfo> hapusTrx(@Field("transaksi_id") String transaksi_id);

}

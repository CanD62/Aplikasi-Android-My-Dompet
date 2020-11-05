package com.latihan.clientserver.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import com.latihan.clientserver.model.ResponseModelPesan;

/**
 * Created by Rohmad on 12-Sep-19.
 */

public interface RestApiPesan {
    @POST("data.php")
    Call<ResponseModelPesan> getPesan();

    @FormUrlEncoded
    @POST("pesan/updatepesan.php")
    Call<ResponseModelPesan> updatePesan(@Field("pesan") String pesan);


}

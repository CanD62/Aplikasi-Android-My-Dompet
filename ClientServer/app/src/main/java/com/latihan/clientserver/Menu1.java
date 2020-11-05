package com.latihan.clientserver;

import android.content.Intent;
import android.icu.text.IDNA;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.latihan.clientserver.api.RestApiPesan;
import com.latihan.clientserver.api.RetroServer;
import com.latihan.clientserver.model.ResponseModelPesan;

public class Menu1 extends AppCompatActivity {

    Button ambilpesan, kirimdata, tambahinfo;
    EditText isipesan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.menu );

        ambilpesan = (Button) findViewById( R.id.btambildata );

        ambilpesan.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RestApiPesan api = RetroServer.getClient().create(RestApiPesan.class);
                final Call<ResponseModelPesan> getdata = api.getPesan();
                Log.d( "RETRO", "RESPONSE! : " + getdata);
                getdata.enqueue(new Callback<ResponseModelPesan>() {

                    @Override
                    public void onResponse(Call<ResponseModelPesan> call, Response<ResponseModelPesan> response) {
                        if (response.isSuccessful()) {
                            Log.d( "RETRO", "RESPONSE : " + response.body().getKode() );

                            String kode = response.body().getKode();
                            String pesan = response.body().getPesan();

                            Toast.makeText( getApplicationContext(), "Data dari server\nkode : "+kode+"\nIsi : "+pesan, Toast.LENGTH_LONG ).show();

                        } else {
                            // error case
                            switch (response.code()) {
                                case 404:
                                    Toast.makeText( getApplicationContext(), "not found", Toast.LENGTH_SHORT ).show();
                                    break;
                                case 500:
                                    Toast.makeText( getApplicationContext(), "server broken", Toast.LENGTH_SHORT ).show();
                                    break;
                                default:
                                    Toast.makeText( getApplicationContext(), "unknown error", Toast.LENGTH_SHORT ).show();
                                    break;
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModelPesan> call, Throwable t) {
                        Log.d("RETRO", "FAILED : respon gagal");
                        Log.d("Kembalian", t.toString());
                        if(t.toString().contains( "connect timed out" )){
                            Toast.makeText( getApplicationContext(),"Time Out. silahkan cobalagi",Toast.LENGTH_SHORT ).show();
                        }
                    }
                });

            }
        } );


        isipesan = (EditText) findViewById( R.id.txtisipesan );

        kirimdata = (Button) findViewById( R.id.btkirimdata );
        kirimdata.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String isi =  isipesan.getText().toString();

                RestApiPesan api = RetroServer.getClient().create(RestApiPesan.class);
                final Call<ResponseModelPesan> getdata = api.updatePesan(isi);
                getdata.enqueue(new Callback<ResponseModelPesan>() {
                    @Override
                    public void onResponse(Call<ResponseModelPesan> call, Response<ResponseModelPesan> response) {
                        if (response.isSuccessful()) {
                            Log.d( "RETRO", "RESPONSE : " + response.body().getKode() );

                            String kode = response.body().getKode();
                            String pesan = response.body().getPesan();

                            Toast.makeText( getApplicationContext(), "Kode : "+kode+"\nHasil "+pesan, Toast.LENGTH_LONG ).show();

                        } else {
                            // error case
                            switch (response.code()) {
                                case 404:
                                    Toast.makeText( getApplicationContext(), "not found", Toast.LENGTH_SHORT ).show();
                                    break;
                                case 500:
                                    Toast.makeText( getApplicationContext(), "server broken", Toast.LENGTH_SHORT ).show();
                                    break;
                                default:
                                    Toast.makeText( getApplicationContext(), "unknown error", Toast.LENGTH_SHORT ).show();
                                    break;
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModelPesan> call, Throwable t) {
                        Log.d("RETRO", "FAILED : respon gagal");
                        Log.d("Kembalian", t.toString());
                        if(t.toString().contains( "connect timed out" )){
                            Toast.makeText( getApplicationContext(),"Time Out. silahkan cobalagi",Toast.LENGTH_SHORT ).show();
                        }
                    }
                });

            }
        } );

        tambahinfo = (Button) findViewById( R.id.btInputInfo );
        tambahinfo.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent( getApplicationContext(), MainActivity.class );
                startActivity( a );

            }
        } );
    }
}

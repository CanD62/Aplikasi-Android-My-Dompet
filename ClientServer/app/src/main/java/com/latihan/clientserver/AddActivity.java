package com.latihan.clientserver;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.andexert.library.RippleView;

import com.latihan.clientserver.api.RestApiInfo;
import com.latihan.clientserver.api.RetroServer;
import com.latihan.clientserver.helper.SqliteHelper;
import com.latihan.clientserver.model.ResponseModelInfo;
import com.latihan.clientserver.adapter.ConvertVariabel;
import com.latihan.clientserver.adapter.NumberTextWatcherForThousand;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddActivity extends AppCompatActivity {

    RadioGroup radio_status;
    EditText edit_jumlah, edit_keterangan;
    Button btn_simpan;
    RippleView  rip_simpan;

    String status;
    SqliteHelper sqliteHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        status = "";
        sqliteHelper = new SqliteHelper(this);

        radio_status    = (RadioGroup) findViewById(R.id.radio_status);
        edit_jumlah     = (EditText) findViewById(R.id.edit_jumlah);
        edit_keterangan = (EditText) findViewById(R.id.edit_keterangan);
        btn_simpan      = (Button) findViewById(R.id.btn_simpan);
        rip_simpan      = (RippleView) findViewById(R.id.rip_simpan);
//perubahan saat ketik
        edit_jumlah.addTextChangedListener(new NumberTextWatcherForThousand(edit_jumlah));
        radio_status.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch(checkedId){
                    case R.id.radio_masuk:
                        status = "MASUK";
                        break;
                    case R.id.radio_keluar:
                        status = "KELUAR";
                        break;
                }

                Log.d("Log status", status);
            }
        });

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        rip_simpan.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                String xedit_jumlah = NumberTextWatcherForThousand.trimDotOfString(edit_jumlah.getText().toString());

           // String xedit_jumlah = edit_jumlah.getText().toString();
              String xedit_keterangan = edit_keterangan.getText().toString();
                if (status.equals("") || edit_jumlah.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Data transaksi tidak boleh kosong",
                            Toast.LENGTH_LONG).show();
                } else {

//                    SQLiteDatabase database = sqliteHelper.getWritableDatabase();
//                    database.execSQL("INSERT INTO transaksi(status, jumlah, keterangan) VALUES('" +
//                            status + "','" +
//                            edit_jumlah.getText().toString() + "','" +
//                            edit_keterangan.getText().toString() + "')");

                    RestApiInfo api = RetroServer.getClient().create(RestApiInfo.class);
                    final Call<ResponseModelInfo> getdata = api.inputTrx(status, xedit_jumlah, xedit_keterangan);
                    getdata.enqueue(new Callback<ResponseModelInfo>() {
                        @Override
                        public void onResponse(Call<ResponseModelInfo> call, Response<ResponseModelInfo> response) {


                            if (response.isSuccessful()) {
                                Log.d( "RETRO", "RESPONSE : " + response.body().getKode() );

                                if(response.body().getKode() != "0"){ //jika respon tidak sama 0
                                  //  Toast.makeText( getApplicationContext(),"Input Data Sukses",Toast.LENGTH_LONG ).show();

//                                    Intent a = new Intent( getApplicationContext(), MainActivity.class );
//                                    startActivity( a );

                                }else{
                                    Toast.makeText( getApplicationContext(),"Input Gagal",Toast.LENGTH_LONG ).show();
                                }
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
                        public void onFailure(Call<ResponseModelInfo> call, Throwable t) {

                            Log.d("RETRO", "FAILED : respon gagal");
                            Log.d("Kembalian", t.toString());
                            if(t.toString().contains( "connect timed out" )){
                                Toast.makeText( getApplicationContext(),"Time Out. silahkan cobalagi",Toast.LENGTH_SHORT ).show();
                            }
                        }
                    });

                    Toast.makeText(getApplicationContext(), "Data transaksi berhasil disimpan", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tambah");

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}

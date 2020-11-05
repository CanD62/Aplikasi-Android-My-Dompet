package com.latihan.clientserver;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.andexert.library.RippleView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import com.google.gson.Gson;
import com.latihan.clientserver.adapter.ConvertVariabel;
import com.latihan.clientserver.adapter.NumberTextWatcherForThousand;
import com.latihan.clientserver.api.RestApiInfo;
import com.latihan.clientserver.api.RetroServer;
import com.latihan.clientserver.helper.CurrentDate;
import com.latihan.clientserver.helper.SqliteHelper;
import com.latihan.clientserver.model.ResponseModelInfo;
import com.latihan.clientserver.model.Result_Data;
import com.latihan.clientserver.model.Result_Sum;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditActivity extends AppCompatActivity {

    RadioGroup radio_status;
    RadioButton radio_masuk, radio_keluar;

    EditText edit_jumlah, edit_keterangan, edit_tanggal;
    Button btn_simpan;
    RippleView rip_simpan;

    SqliteHelper sqliteHelper;
    Cursor cursor;

    String status, tanggal;

    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        status = ""; tanggal = "";

        radio_status    = (RadioGroup)  findViewById(R.id.radio_status);
        radio_masuk     = (RadioButton) findViewById(R.id.radio_masuk);
        radio_keluar    = (RadioButton) findViewById(R.id.radio_keluar);

        edit_jumlah     = (EditText)    findViewById(R.id.edit_jumlah);
        edit_keterangan = (EditText)    findViewById(R.id.edit_keterangan);
        edit_tanggal    = (EditText)    findViewById(R.id.edit_tanggal);
        btn_simpan      = (Button)      findViewById(R.id.btn_simpan);
        rip_simpan      = (RippleView) findViewById(R.id.rip_simpan);

//        sqliteHelper = new SqliteHelper(this);
//        SQLiteDatabase database = sqliteHelper.getReadableDatabase();
//        cursor = database.rawQuery(
//                "SELECT *, strftime('%d-%m-%Y', tanggal) AS tanggal FROM transaksi WHERE transaksi_id ='" + MainActivity.transaksi_id + "'"
//                , null
//        );
//        cursor.moveToFirst();
        //Log.d( "RETRO", "RESPONSE!! : " + MainActivity.transaksi_id );
        final String trx_id = MainActivity.transaksi_id;
        RestApiInfo api = RetroServer.getClient().create(RestApiInfo.class);
        final Call<Result_Data> getdata = api.getTrx(trx_id);
        // Log.d( "RETRO", "RESPONSE! : " + getdata);
        getdata.enqueue(new Callback<Result_Data>() {

            @Override
            public void onResponse(Call<Result_Data> call, Response<Result_Data> response) {
                if (response.isSuccessful()) {

                    Locale localeID = new Locale("us", "US");
                    final NumberFormat rupiahFormat = NumberFormat.getInstance(localeID);

                    // List<trx> data = response.body().getData();
                    String p = new Gson().toJson(response.body().getData().toArray());
                   // Log.d("Log ", p );
                    try {
                        JSONArray array = new JSONArray(p);

                        int i;
                        for (i = 0; i < array.length(); i++) {

                            JSONObject arrayELement = array.getJSONObject(i);

                            status = arrayELement.getString("status");

                            String jumlah = arrayELement.getString("jumlah");
                            String keterangan = arrayELement.getString("keterangan");
                            tanggal = arrayELement.getString("tanggal");


                            //status = "MASUK";
                            switch (status){
                                case "MASUK":
                                    radio_masuk.setChecked(true); break;
                                case "KELUAR":
                                    radio_keluar.setChecked(true); break;
                            }

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

                            edit_jumlah.setText( ConvertVariabel.rupiah(jumlah) );
                            //perubahan saat ketik
                            edit_jumlah.addTextChangedListener(new NumberTextWatcherForThousand(edit_jumlah));
                            edit_keterangan.setText( keterangan );

                           // tanggal = "2020-10-11";
                            edit_tanggal.setText( tanggal);

                            edit_tanggal.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    datePickerDialog = new DatePickerDialog(EditActivity.this, new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year, int month_of_year, int day_of_month) {
                                            // set day of month , month and year value in the edit text
                                            NumberFormat numberFormat = new DecimalFormat("00");
                                            tanggal = year + "-" + numberFormat.format(( month_of_year +1 )) + "-" +
                                                    numberFormat.format(day_of_month);
                                            edit_tanggal.setText(year + "-" + numberFormat.format(( month_of_year +1 )) +
                                                    "-" + numberFormat.format(day_of_month) );
                                        }
                                    }, CurrentDate.year, CurrentDate.month, CurrentDate.day);
                                    datePickerDialog.show();
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
                                    if (status.equals("") || edit_jumlah.getText().toString().equals("")){
                                        Toast.makeText(getApplicationContext(), "Data transaksi tidak boleh kosong",
                                                Toast.LENGTH_LONG).show();
                                    } else {
//                                        SQLiteDatabase database = sqliteHelper.getWritableDatabase();
//                                        database.execSQL(
//                                                "UPDATE transaksi SET status='" + status + "', jumlah='" + edit_jumlah.getText().toString() +
//                                                        "', " + "keterangan='" + edit_keterangan.getText().toString() + "', tanggal='" + tanggal +
//                                                        "' WHERE transaksi_id='" + MainActivity.transaksi_id + "'"
//                                        );


                                        RestApiInfo api = RetroServer.getClient().create(RestApiInfo.class);
                                        final Call<ResponseModelInfo> getdata = api.updateTrx(trx_id, status, edit_jumlah.getText().toString(), edit_tanggal.getText().toString(), edit_keterangan.getText().toString());
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


                                        Toast.makeText(getApplicationContext(), "Perubahan transaksi berhasil disimpan", Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                }
                            });

                            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                            getSupportActionBar().setTitle("Edit");





                        }
//                        if (i == 0){
//                            Toast.makeText(getApplicationContext(), "Transaksi masih kosong",
//                                    Toast.LENGTH_LONG).show();
//                        }
//                        String[] form =  {"transaksi_id", "status", "jumlah", "keterangan", "tanggal"};
//                        int[] to= {R.id.text_transaksi_id, R.id.text_status, R.id.text_jumlah, R.id.text_keterangan,
//                                R.id.text_tanggal};
//                        SimpleAdapter simpleAdapter = new SimpleAdapter(getApplicationContext(), aruskas, R.layout.list_kas,form,to);
//
//                        list_kas.setAdapter(simpleAdapter);
//                        list_kas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                transaksi_id = ((TextView) view.findViewById(R.id.text_transaksi_id)).getText().toString();
//                                ListMenu();
//                            }
//                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    //JSONArray contacts = p.getJSONArray("contacts");

                    //Toast.makeText( getApplicationContext(), "Data dari server\n"+list, Toast.LENGTH_LONG ).show();
//                int    list = response.body().getData().size();
//                    String data = new Gson().toJson(response.body().getData());
                    //List<String> list = new ArrayList<>();
//                    for (int i=0; i<data.length(); i++) {
//                        Log.e("P", "response: "+data);
//                    }









//                    int i;
//                    for (i=0; i < list.length(); i++){
//
//                        Log.e("TAG", "response 33: "+list);
//
//
//                    }



//                    String kode = response.body().getKode();
//                    String pesan = response.body().getPesan();
//                    //Log.d( "RETRO", "RESPONSE!! : " + response.body().getKode() );
//                    Toast.makeText( getApplicationContext(), "Data dari server\nkode : "+kode+"\nIsi : "+pesan, Toast.LENGTH_LONG ).show();

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
            public void onFailure(Call<Result_Data> call, Throwable t) {
                Log.d("RETRO", "FAILED : respon gagal");
                Log.d("Kembalian", t.toString());
                if(t.toString().contains( "connect timed out" )){
                    Toast.makeText( getApplicationContext(),"Time Out. silahkan cobalagi",Toast.LENGTH_SHORT ).show();
                }
            }
        });




       // System.exit(1);

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}

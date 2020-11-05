package com.latihan.clientserver;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.google.gson.Gson;
import com.latihan.clientserver.api.RestApiInfo;
import com.latihan.clientserver.api.RetroServer;
import com.latihan.clientserver.helper.SqliteHelper;
import com.latihan.clientserver.model.DataModelInfo;
import com.latihan.clientserver.model.ResponseModelInfo;
import com.latihan.clientserver.model.Result_Data;
import com.latihan.clientserver.model.Result_Sum;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private List<DataModelInfo> mItems = new ArrayList<>();
    TextView text_masuk, text_keluar, text_total;
    ListView list_kas;
    SwipeRefreshLayout swipe_refresh;
    ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();
    //List<trx> list = new ArrayList<>();
    public static TextView text_filter;
    public static String transaksi_id;
    public static String tgl_dari;
    public static String tgl_ke;
    public static boolean filter;

    String query_kas, query_total;
    SqliteHelper sqliteHelper;
    Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        transaksi_id = ""; tgl_dari = ""; tgl_ke = ""; query_kas = ""; query_total = "";  filter = false;
        sqliteHelper = new SqliteHelper(this);

        text_filter     = (TextView) findViewById(R.id.text_filter);
        text_masuk      = (TextView) findViewById(R.id.text_masuk);
        text_keluar     = (TextView) findViewById(R.id.text_keluar);
        text_total      = (TextView) findViewById(R.id.text_total);
        list_kas        = (ListView) findViewById(R.id.list_kas);
        swipe_refresh   = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        //KasAdapter();

        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                KasAdapter();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddActivity.class));
            }
        });


    }

    private void KasAdapter(){

        RestApiInfo api = RetroServer.getClient().create(RestApiInfo.class);
        final Call<Result_Data> getdata = api.getTrx();
       // Log.d( "RETRO", "RESPONSE! : " + getdata);
        getdata.enqueue(new Callback<Result_Data>() {

            @Override
            public void onResponse(Call<Result_Data> call, Response<Result_Data> response) {
                if (response.isSuccessful()) {
                    aruskas.clear(); list_kas.setAdapter(null);
                    Locale localeID = new Locale("us", "US");
                    final NumberFormat rupiahFormat = NumberFormat.getInstance(localeID);

                 // List<trx> data = response.body().getData();
                    String p = new Gson().toJson(response.body().getData().toArray());
                    try {
                        JSONArray array = new JSONArray(p);
                        int i;
                        for (i = 0; i < array.length(); i++) {

                            JSONObject arrayELement = array.getJSONObject(i);
                            String transaksi_id = arrayELement.getString("transaksi_id");
                            String status = arrayELement.getString("status");
                            int jumlah = Integer.parseInt(arrayELement.getString("jumlah"));
                            //int jumlah1 = Integer.parseInt(arrayELement.getString("jumlah"));
                            String keterangan = arrayELement.getString("keterangan");
                            String tanggal = arrayELement.getString("tanggal");


                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put("transaksi_id", transaksi_id);
                            map.put("status", status);
                            map.put("jumlah", rupiahFormat.format(jumlah));
                            map.put("keterangan", keterangan);
                            map.put("tanggal", tanggal);
                            aruskas.add(map);

                            //Log.e("P", "response: "+array.length());
                        }
                        if (i == 0){
                            Toast.makeText(getApplicationContext(), "Transaksi masih kosong",
                                    Toast.LENGTH_LONG).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    String[] form =  {"transaksi_id", "status", "jumlah", "keterangan", "tanggal"};
                    int[] to= {R.id.text_transaksi_id, R.id.text_status, R.id.text_jumlah, R.id.text_keterangan,
                            R.id.text_tanggal};
                    SimpleAdapter simpleAdapter = new SimpleAdapter(getApplicationContext(), aruskas, R.layout.list_kas,form,to);

                    list_kas.setAdapter(simpleAdapter);
                    list_kas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            transaksi_id = ((TextView) view.findViewById(R.id.text_transaksi_id)).getText().toString();
                            ListMenu();
                        }
                    });
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
                Toast.makeText(getApplicationContext(), "Transaksi masih kosong",
                        Toast.LENGTH_LONG).show();
                Log.d("RETRO", "FAILED : respon gagal");
                Log.d("Kembalian", t.toString());
                if(t.toString().contains( "connect timed out" )){
                    Toast.makeText( getApplicationContext(),"Time Out. silahkan cobalagi",Toast.LENGTH_SHORT ).show();
                }
            }
        });




//        Locale localeID = new Locale("in", "ID");
//        NumberFormat rupiahFormat = NumberFormat.getInstance(localeID);


//        SQLiteDatabase database = sqliteHelper.getReadableDatabase();
//        cursor = database.rawQuery( query_kas, null);
//        cursor.moveToFirst();

//        int i;
//        for (i=0; i < cursor.getCount(); i++){
//            cursor.moveToPosition(i);
//
//
//        }













        KasTotal();
    }

    private void KasTotal(){


        RestApiInfo api = RetroServer.getClient().create(RestApiInfo.class);
        final Call<Result_Sum> getdata = api.getSaldo();
        // Log.d( "RETRO", "RESPONSE! : " + getdata);
        getdata.enqueue(new Callback<Result_Sum>() {

            @Override
            public void onResponse(Call<Result_Sum> call, Response<Result_Sum> response) {
                if (response.isSuccessful()) {

                    Locale localeID = new Locale("us", "US");
                    final NumberFormat rupiahFormat = NumberFormat.getInstance(localeID);
                    aruskas.clear(); list_kas.setAdapter(null);
                    // List<trx> data = response.body().getData();
                    String p = new Gson().toJson(response.body().getPesan().toArray());
                    try {
                        JSONArray array = new JSONArray(p);

                        int i;
                        for (i = 0; i < array.length(); i++) {

                            JSONObject arrayELement = array.getJSONObject(i);

                            int masuk = Integer.parseInt(arrayELement.getString("masuk"));
                            int keluar = Integer.parseInt(arrayELement.getString("keluar"));
                            int total = Integer.parseInt(arrayELement.getString("total"));

                            text_masuk.setText(rupiahFormat.format(masuk));
                            text_keluar.setText(rupiahFormat.format(keluar));
                            text_total.setText(rupiahFormat.format(total));


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
            public void onFailure(Call<Result_Sum> call, Throwable t) {
                Log.d("RETRO", "FAILED : respon gagal");
                Log.d("Kembalian", t.toString());
                if(t.toString().contains( "connect timed out" )){
                    Toast.makeText( getApplicationContext(),"Time Out. silahkan cobalagi",Toast.LENGTH_SHORT ).show();
                }
            }
        });

//        SQLiteDatabase database = sqliteHelper.getReadableDatabase();
//        cursor = database.rawQuery( query_total, null);
//        cursor.moveToFirst();
////        text_total.setText( rupiahFormat.format(cursor.getDouble(0)) );
//        text_masuk.setText( rupiahFormat.format(cursor.getDouble(1)) );
//        text_keluar.setText( rupiahFormat.format(cursor.getDouble(2)) );
//        text_total.setText(
//                rupiahFormat.format(cursor.getDouble(1) - cursor.getDouble(2))
//        );

        swipe_refresh.setRefreshing(false);

        if (!filter) { text_filter.setVisibility(View.GONE); }
        filter = false;
    }

    @Override
    public void onResume(){
        super.onResume();

        query_kas   =
                "SELECT *, strftime('%d-%m-%Y', tanggal) AS tgl FROM transaksi ORDER BY transaksi_id DESC";
        query_total =
                "SELECT SUM(jumlah) AS total, " +
                "(SELECT SUM(jumlah) FROM transaksi WHERE status='MASUK') AS masuk, " +
                "(SELECT SUM(jumlah) FROM transaksi WHERE status='KELUAR') AS keluar " +
                "FROM transaksi";

        if (filter) {

            query_kas   =
                    "SELECT *, strftime('%d-%m-%Y', tanggal) AS tgl FROM transaksi  " +
                    "WHERE (tanggal >= '" + tgl_dari + "') AND (tanggal <= '" + tgl_ke + "') ORDER BY transaksi_id ASC ";
            query_total =
                    "SELECT SUM(jumlah) AS total, " +
                    "(SELECT SUM(jumlah) FROM transaksi WHERE status='MASUK' AND (tanggal >= '" + tgl_dari + "') AND (tanggal <= '" + tgl_ke + "') ), " +
                    "(SELECT SUM(jumlah) FROM transaksi WHERE status='KELUAR' AND (tanggal >= '" + tgl_dari + "') AND (tanggal <= '" + tgl_ke + "')) " +
                    "FROM transaksi " +
                    "WHERE (tanggal >= '" + tgl_dari + "') AND (tanggal <= '" + tgl_ke + "') ";
//            filter = false;
        }

        KasAdapter();

    }

    private void ListMenu(){

        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.list_menu);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        TextView text_edit  = (TextView) dialog.findViewById(R.id.text_edit);
        TextView text_hapus = (TextView) dialog.findViewById(R.id.text_hapus);
        dialog.show();

        text_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(new Intent(MainActivity.this, EditActivity.class));
            }
        });
        text_hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Hapus();
            }
        });
    }

    private void Hapus(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Konfirmasi");
        builder.setMessage("Apakah anda yakin untuk menghapus transaksi ini?");
        builder.setPositiveButton(
                "Ya",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();

//                        SQLiteDatabase database = sqliteHelper.getWritableDatabase();
//                        database.execSQL("DELETE FROM transaksi WHERE transaksi_id = '" + transaksi_id + "'");

                        RestApiInfo api = RetroServer.getClient().create(RestApiInfo.class);
                        final Call<ResponseModelInfo> getdata = api.hapusTrx(transaksi_id);
                        getdata.enqueue(new Callback<ResponseModelInfo>() {
                            @Override
                            public void onResponse(Call<ResponseModelInfo> call, Response<ResponseModelInfo> response) {


                                if (response.isSuccessful()) {
                                    Log.d( "RETRO", "RESPONSE : " + response.body().getKode() );

                                    if(response.body().getKode() != "0"){ //jika respon tidak sama 0
                                        //  Toast.makeText( getApplicationContext(),"Input Data Sukses",Toast.LENGTH_LONG ).show();
                                        KasAdapter();
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



                        Toast.makeText(getApplicationContext(), "Tranksaki berhasil dihapus",
                                Toast.LENGTH_LONG).show();



                    }
                });

        builder.setNegativeButton(
                "Tidak",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_aboutme) {
            startActivity(new Intent(MainActivity.this, AboutMe.class));
            return true;
        } else if(id == R.id.action_galeri){
            startActivity(new Intent(MainActivity.this, Galeri.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

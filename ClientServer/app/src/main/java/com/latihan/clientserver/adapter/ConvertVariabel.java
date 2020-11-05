package com.latihan.clientserver.adapter;

import java.text.DecimalFormat;

/**
 * Created by rohmad on 03-Jan-18.
 */

public class ConvertVariabel {

    public static String tanggal(String tgl){
        String bulan;
        String hasil="";
        String[] separated = tgl.split("-");
        if(separated.length==3) {
            if(separated[1].equals("01")){
                bulan="Januari";
            }else if(separated[1].equals("02")){
                bulan="Februari";
            }else if(separated[1].equals("03")){
                bulan="Maret";
            }else if(separated[1].equals("04")){
                bulan="April";
            }else if(separated[1].equals("05")){
                bulan="Mei";
            }else if(separated[1].equals("06")){
                bulan="Juni";
            }else if(separated[1].equals("07")){
                bulan="Juli";
            }else if(separated[1].equals("08")){
                bulan="Agustus";
            }else if(separated[1].equals("09")){
                bulan="September";
            }else if(separated[1].equals("10")){
                bulan="Oktober";
            }else if(separated[1].equals("11")){
                bulan="November";
            }else if(separated[1].equals("12")){
                bulan="Desember";
            }else {
                bulan="00";
            }
            hasil=separated[2]+" "+bulan+" "+separated[0];
        }else{
            hasil="-";
        }
        return hasil;
    }


    public static String tanggalsingkat(String tgl){
        String bulan;
        String hasil="";
        String[] separated = tgl.split("-");
        if(separated.length==3) {
            if(separated[1].equals("01")){
                bulan="Jan";
            }else if(separated[1].equals("02")){
                bulan="Feb";
            }else if(separated[1].equals("03")){
                bulan="Mar";
            }else if(separated[1].equals("04")){
                bulan="Apr";
            }else if(separated[1].equals("05")){
                bulan="Mei";
            }else if(separated[1].equals("06")){
                bulan="Jun";
            }else if(separated[1].equals("07")){
                bulan="Jul";
            }else if(separated[1].equals("08")){
                bulan="Agust";
            }else if(separated[1].equals("09")){
                bulan="Sept";
            }else if(separated[1].equals("10")){
                bulan="Okt";
            }else if(separated[1].equals("11")){
                bulan="Nov";
            }else if(separated[1].equals("12")){
                bulan="Des";
            }else {
                bulan="00";
            }
            hasil=separated[2]+" "+bulan+" "+separated[0];
        }else{
            hasil="-";
        }
        return hasil;
    }

    public static String rupiah(String rp){
        DecimalFormat decimalFormat = new DecimalFormat("#");
        decimalFormat.setGroupingUsed(true);
        decimalFormat.setGroupingSize(3);

       return decimalFormat.format(Integer.valueOf(rp));
    }

    public static String angka_rupiah(int satuan){

        String[] huruf ={"","Satu","Dua","Tiga","Empat","Lima","Enam","Tujuh","Delapan","Sembilan","Sepuluh","Sebelas"};
        String hasil="";
        if(satuan<12)
            hasil=hasil+huruf[satuan];
        else if(satuan<20)
            hasil=hasil+angka_rupiah(satuan-10)+" Belas";
        else if(satuan<100)
            hasil=hasil+angka_rupiah(satuan/10)+" Puluh "+angka_rupiah(satuan%10);
        else if(satuan<200)
            hasil=hasil+"Seratus "+angka_rupiah(satuan-100);
        else if(satuan<1000)
            hasil=hasil+angka_rupiah(satuan/100)+" Ratus "+angka_rupiah(satuan%100);
        else if(satuan<2000)
            hasil=hasil+"Seribu "+angka_rupiah(satuan-1000);
        else if(satuan<1000000)
            hasil=hasil+angka_rupiah(satuan/1000)+" Ribu "+angka_rupiah(satuan%1000);
        else if(satuan<1000000000)
            hasil=hasil+angka_rupiah(satuan/1000000)+" Juta "+angka_rupiah(satuan%1000000);
        else if(satuan>=1000000000)
            hasil="Angka terlalu besar, harus kurang dari 1 milyar!";
        return hasil;
    }
}

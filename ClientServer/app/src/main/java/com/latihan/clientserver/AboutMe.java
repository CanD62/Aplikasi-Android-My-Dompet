package com.latihan.clientserver;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class AboutMe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
    }




    public void wa_(View view) {
        Uri uri = Uri.parse("https://wa.me/628985944764?text=Halo%20Mas");


        Intent i= new Intent(Intent.ACTION_VIEW,uri);

        i.setPackage("com.whatsapp");

        try {
            startActivity(i);
        } catch (ActivityNotFoundException e) {

            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://wa.me/628985944764?text=Halo%20Mas")));
        }
    }

    public void fb_(View view) {
        Uri uri = Uri.parse("fb://puputzmagic");


        Intent i= new Intent(Intent.ACTION_VIEW,uri);

        i.setPackage("com.facebook.katana");

        try {
            startActivity(i);
        } catch (ActivityNotFoundException e) {

            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://facebook.com/puputzmagic")));
        }
    }
    public void ig_(View view) {
        Uri uri = Uri.parse("https://instagram.com/puput.cs");


        Intent i= new Intent(Intent.ACTION_VIEW,uri);

        i.setPackage("com.instagram.android");

        try {
            startActivity(i);
        } catch (ActivityNotFoundException e) {

            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://instagram.com/puput.cs")));
        }
    }
}

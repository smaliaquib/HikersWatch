package com.example.aquib.hikerswatch;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    LocationManager LocManage;
    LocationListener LocListen;

    TextView Txt1;
    TextView Txt2;
    TextView Txt3;
    TextView Txt4;
    TextView Txt5;
    TextView Txt6;

    public void updateLocation(Location location){

        Log.i("updateLocation: ",location.toString());

        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        try {

            String s1="";

            List<Address> list = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);

            if(list.get(0).getThoroughfare()!=null){

            }

            if(list.get(0).getSubThoroughfare()!=null){

            }

            if(list.get(0).getCountryCode()!=null){

            }

            if(list.get(0).getCountryName()!=null){

            }

            if(list.get(0).getPostalCode()!=null){

            }

            if(list.get(0).getLocality()!=null){
                s1 = list.get(0).getLocality();
            }

            if(list.get(0).getAdminArea()!=null){
                Txt1.setText(s1+","+list.get(0).getAdminArea());
            }

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    public void startListen(){


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            LocManage.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,LocListen);

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==1){

            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED) {

                startListen();

            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Txt1 = (TextView)findViewById(R.id.textView);

        LocManage = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);

        LocListen = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                updateLocation(location);

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if(Build.VERSION.SDK_INT<23){

            startListen();

        }else {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            } else {

                LocManage.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, LocListen);

                Location location = LocManage.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                if(location!=null) {

                    updateLocation(location);

                }
            }
        }
    }
}

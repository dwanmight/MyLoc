package com.junior.dwan.myloc;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener {

    LocationManager mLocationManager;
    String nameProvider;
    TextView tvMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        nameProvider = mLocationManager.getBestProvider(new Criteria(), false);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }
        Location location = mLocationManager.getLastKnownLocation(nameProvider);
        if(location!=null){
            onLocationChanged(location);
        } else {
            mLocationManager.requestLocationUpdates(nameProvider,1000,0,this);
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        String provider = location.getProvider();
        Double latitude = location.getLatitude();
        Double longitude = location.getLongitude();
        Double altitude = location.getAltitude();
        Float speed = location.getSpeed();
        Float accuracy = location.getAccuracy();

        Geocoder geocoder=new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            List<Address> addressList = geocoder.getFromLocation(latitude,longitude,1);
            if(addressList!=null && addressList.size()>1){
                Log.i("TAG",addressList.get(0).toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        Log.i("provider", String.valueOf(provider));
        Log.i("latitude", String.valueOf(latitude));
        Log.i("longitude", String.valueOf(longitude));
        Log.i("altitude", String.valueOf(altitude));
        Log.i("speed", String.valueOf(speed));
        Log.i("accuracy", String.valueOf(accuracy));
//        tvMain.setText(" provider "+ String.valueOf(provider)+" latitude "+ String.valueOf(latitude)
//                +" longitude "+ String.valueOf(longitude)+" altitude "+ String.valueOf(altitude)
//                +" speed "+ String.valueOf(speed)+" accuracy "+ String.valueOf(accuracy));
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

    @Override
    protected void onPause() {
        super.onPause();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }
        mLocationManager.removeUpdates(MainActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }
        mLocationManager.requestLocationUpdates(nameProvider, 1000, 1, this);
    }
}

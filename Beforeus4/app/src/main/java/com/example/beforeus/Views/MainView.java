package com.example.beforeus.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.beforeus.Models.MainModel;
import com.example.beforeus.Models.Palace;
import com.example.beforeus.Models.PalaceAdapter;
import com.example.beforeus.Presenters.MainPresenter;
import com.example.beforeus.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.security.Permission;
import java.util.ArrayList;
import java.util.List;

public class MainView extends AppCompatActivity implements OnMapReadyCallback {

    private MainPresenter presenter;
    private GoogleMap mMap;
    private boolean permissionChecked = true;
    private List<Palace> palaces;
    ListView palacesList;

    private float PxFromDp(int dp) {
        return dp * getApplicationContext()
                .getResources()
                .getDisplayMetrics()
                .density;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenter(this, new MainModel());
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //
            } else {
                ActivityCompat.requestPermissions(MainView.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                permissionChecked = false;
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //
            } else {
                ActivityCompat.requestPermissions(MainView.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                permissionChecked = false;
            }
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);
        presenter.StartServer();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (permissionChecked) {
            mMap = googleMap;
            mMap.setMyLocationEnabled(true);
            mMap.setBuildingsEnabled(true);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(GetLocationCoord(), 17));
            new Thread(new RunnableUpdateLocation(this)).start();

        } else {
            PrintMyLocation("Пожалуйста примите разрешения и перезапустите приложение!");
        }
    }

    public void PrintMyLocation(String text) {

        runOnUiThread(new RunnableText(text, ((TextView) findViewById(R.id.tvLocation))));
    }

    public LatLng GetLocationCoord() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //
            } else {
                ActivityCompat.requestPermissions(MainView.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //
            } else {
                ActivityCompat.requestPermissions(MainView.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }
        }
        Criteria criteria = new Criteria();
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        String provider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(provider);
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        return new LatLng(latitude, longitude);
    }


    public void GetData(final ArrayList<Palace> palaces) {
        this.palaces = palaces;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                palacesList = (ListView) findViewById(R.id.palacesList);
                PalaceAdapter palaceAdapter = new PalaceAdapter(getBaseContext(), R.layout.list_item,palaces);
                palacesList.setAdapter(palaceAdapter);
                AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                        Palace selectedPalace = (Palace)parent.getItemAtPosition(position);
                        Intent intent = new Intent(MainView.this, MoreInfoView.class);
                        intent.putExtra("title",selectedPalace.title);
                        intent.putExtra("coord",selectedPalace.coord);
                        startActivity(intent);
                    }
                };
                palacesList.setOnItemClickListener(itemListener);
            }
        });

    }

    public void ShowMessage(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }


}

class RunnableText implements Runnable {
    private String text;
    private TextView textView;

    public RunnableText(String text, TextView textView) {
        this.text = text;
        this.textView = textView;
    }

    public void run() {
        textView.setText(text);
    }
}

class RunnableUpdateLocation implements Runnable {

    private LatLng location;
    private MainView view;

    public RunnableUpdateLocation(MainView view) {
        this.view = view;
    }

    public void run() {
        String addressStr;
        Geocoder geocoder = new Geocoder(view);
        Address address = null;
        while (true) {
            location = view.GetLocationCoord();
            try {
                address = geocoder.getFromLocation(location.latitude, location.longitude, 1).get(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                addressStr = address.getAddressLine(0);
                view.PrintMyLocation(addressStr);
            } catch (NullPointerException ex) {

            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}



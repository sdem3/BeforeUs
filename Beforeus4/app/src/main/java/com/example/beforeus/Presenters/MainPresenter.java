package com.example.beforeus.Presenters;

import android.location.Address;
import android.location.Geocoder;
import android.net.IpSecManager;
import android.os.Build;
import android.util.JsonReader;

import com.example.beforeus.Models.MainModel;
import com.example.beforeus.R;
import com.example.beforeus.Views.MainView;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;

public class MainPresenter {
    public static MainView view;
    private MainModel model;

    public MainPresenter(MainView view, MainModel model) {
        this.view = view;
        this.model = model;
    }

    public void StartServer()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //model.GetData(view.GetLocationCoord());
                view.GetData(model.GetData(view.GetLocationCoord()));
                System.gc();
            }
        }).start();
    }


}



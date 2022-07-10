package com.example.beforeus.Models;

import android.app.Notification;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.media.Image;
import android.view.View;
import android.widget.Toast;

import com.example.beforeus.Presenters.MainPresenter;
import com.example.beforeus.R;
import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class MainModel {

    public ArrayList<Palace> GetData(LatLng coord) {

        ArrayList<Palace> palaces = new ArrayList<>();
        Palace palace;
        byte[] bytesImage1;
        String coords = new String(ServerConnect.ServerRequest("GetCoords#" + coord));
        while (coords.contains("#")) {
            palace = new Palace();
            palace.coord = coords.substring(0,coords.indexOf("#"));
            bytesImage1 = ServerConnect.ServerRequest("GetImage1#" + palace.coord);
            palace.image = BitmapFactory.decodeByteArray(bytesImage1, 0, bytesImage1.length);
            palace.title = new String(ServerConnect.ServerRequest("GetTitle#" + palace.coord));
            palace.distance = new String(ServerConnect.ServerRequest("GetDistance#" + palace.coord) );
            coords = coords.substring(coords.indexOf("#") + 1);
            palaces.add(palace);
        }
        return palaces;
    }





}

package com.example.beforeus.Models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class MoreInfoModel {
    public Bitmap GetImage(String coord, int n) {
        byte[] bytesImage = bytesImage = ServerConnect.ServerRequest("GetImage" + n + "#" + coord);
        return BitmapFactory.decodeByteArray(bytesImage, 0, bytesImage.length);
    }

    public String[] GetText(String coord, int n) {
        String[] array = new String[2];
        array[0] = new String(ServerConnect.ServerRequest("GetMainText"+ n + "#" + coord));
        array[1] = new String(ServerConnect.ServerRequest("GetYearText"+ n + "#" + coord));
        return array;
    }


}

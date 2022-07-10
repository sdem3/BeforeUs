package com.example.beforeus.Presenters;

import android.graphics.Bitmap;

import com.example.beforeus.Models.MoreInfoModel;
import com.example.beforeus.Models.MoreInfoSlide;
import com.example.beforeus.Views.MoreInfoView;

public class MoreInfoPresenter {
    public static MoreInfoView view;
    private MoreInfoModel model;

    public MoreInfoPresenter(MoreInfoView view, MoreInfoModel model) {
        this.view = view;
        this.model = model;
    }


    public void GetData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String coord = view.GetCoord();
                MoreInfoSlide slide1 = new MoreInfoSlide();
                MoreInfoSlide slide2 = new MoreInfoSlide();
                slide1.image = model.GetImage(coord, 1);
                String[] array = model.GetText(coord, 1);
                slide1.MainText = array[0];
                slide1.YearText = array[1];
                slide2.image = model.GetImage(coord, 2);
                array = model.GetText(coord, 2);
                slide2.MainText = array[0];
                slide2.YearText = array[1];
                view.FillData(slide1,slide2);
                if (view.selectedSlide == 0) {
                    view.SetData(slide1);
                    view.selectedSlide = 1;
                }
                System.gc();
            }
        }).start();
    }

}
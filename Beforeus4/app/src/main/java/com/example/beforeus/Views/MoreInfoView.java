package com.example.beforeus.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.beforeus.Models.MoreInfoModel;
import com.example.beforeus.Models.MoreInfoSlide;
import com.example.beforeus.Models.SwipeTouchListener;
import com.example.beforeus.Presenters.MoreInfoPresenter;
import com.example.beforeus.R;

public class MoreInfoView extends AppCompatActivity {

    private MoreInfoPresenter presenter;
    private String title;
    private String coord;
    private MoreInfoSlide slide1;
    private MoreInfoSlide slide2;

    public int selectedSlide = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moreinfo);
        presenter = new MoreInfoPresenter(this, new MoreInfoModel());
        title = getIntent().getStringExtra("title");
        coord = getIntent().getStringExtra("coord");
        ((TextView) findViewById(R.id.textViewTitle)).setText(title);
        presenter.GetData();
        ImageView imageView = ((ImageView) findViewById(R.id.imageMore));
        imageView.setOnTouchListener(new SwipeTouchListener(this,this));

    }

    public String GetCoord() {
        return coord;
    }

    public MoreInfoSlide GetSlide(int i)
    {
        if (i == 1)
            return slide1;
        else if (i==2)
            return slide2;
        else
            return null;
    }



    public void FillData(MoreInfoSlide slide1, MoreInfoSlide slide2) {
        this.slide1 = slide1;
        this.slide2 = slide2;
    }

    public void SetData(final MoreInfoSlide slide)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((ImageView) findViewById(R.id.imageMore)).setImageBitmap(slide.image);
                ((TextView) findViewById(R.id.textViewMain)).setText(slide.MainText);
                ((TextView) findViewById(R.id.textViewYear)).setText(slide.YearText);
            }
        });
    }



}

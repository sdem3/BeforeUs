package com.example.beforeus.Models;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.beforeus.Views.MoreInfoView;


public class SwipeTouchListener implements View.OnTouchListener {

    private static final String LOG_TAG = "SwipeTouchListener";
    private Activity activity;
    private MoreInfoView view;
    private static int MIN_DISTANCE;
    private float downX;
    private float downY;

    public SwipeTouchListener(Activity _activity, MoreInfoView view) {
        activity = _activity;
        this.view = view;
        DisplayMetrics dm = activity.getResources().getDisplayMetrics();
        MIN_DISTANCE = (int) (120.0f * dm.densityDpi / 160.0f + 0.5);
    }

    private void onRightToLeftSwipe() {
        //Toast.makeText(activity.getApplicationContext(), "справа налево", Toast.LENGTH_LONG).show();
        if (view.selectedSlide == 1)
        {
            view.SetData(view.GetSlide(2));
            view.selectedSlide = 2;
        }
    }

    private void onLeftToRightSwipe() {
        //Toast.makeText(activity.getApplicationContext(), "слева направо", Toast.LENGTH_LONG).show();
        if (view.selectedSlide == 2)
        {
            view.SetData(view.GetSlide(1));
            view.selectedSlide = 1;
        }
    }

    private void onTopToBottomSwipe() {
        //Toast.makeText(activity.getApplicationContext(), "сверху вниз", Toast.LENGTH_LONG).show();
    }

    private void onBottomToTopSwipe() {
        //Toast.makeText(activity.getApplicationContext(), "снизу вверх", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                downX = event.getX();
                downY = event.getY();
                return true;
            }
            case MotionEvent.ACTION_UP: {
                float upX = event.getX();
                float upY = event.getY();

                float deltaX = downX - upX;
                float deltaY = downY - upY;

                // горизонтальный свайп
                if (Math.abs(deltaX) > MIN_DISTANCE) { // если дистанция не меньше минимальной
                    // слева направо
                    if (deltaX < 0) {
                        this.onLeftToRightSwipe();
                        return true;
                    }
                    //справа налево
                    if (deltaX > 0) {
                        this.onRightToLeftSwipe();
                        return true;
                    }
                }

                // вертикальный свайп
                if (Math.abs(deltaY) > MIN_DISTANCE) { //если дистанция не меньше минимальной
                    // сверху вниз
                    if (deltaY < 0) {
                        this.onTopToBottomSwipe();
                        return true;
                    }
                    // снизу вверх
                    if (deltaY > 0) {
                        this.onBottomToTopSwipe();
                        return true;
                    }
                }

                return false;
            }
        }
        return false;
    }
}
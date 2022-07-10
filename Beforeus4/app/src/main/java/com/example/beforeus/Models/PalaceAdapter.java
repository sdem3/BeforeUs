package com.example.beforeus.Models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.beforeus.R;

import java.util.List;


public class PalaceAdapter extends ArrayAdapter<Palace> {

    private LayoutInflater inflater;
    private int layout;
    private List<Palace> palaces;

    public PalaceAdapter(Context context, int resource, List<Palace> palaces) {
        super(context, resource, palaces);
        this.palaces = palaces;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null) {
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Palace palace = palaces.get(position);
        viewHolder.imageView.setImageBitmap(palace.image);
        viewHolder.titleView.setText(palace.title);
        viewHolder.coordView.setText(palace.coord);
        viewHolder.distanceView.setText(palace.distance);
        return convertView;
    }
}
 class ViewHolder {
    final ImageView imageView;
    final TextView titleView, coordView, distanceView;
    ViewHolder(View view){
        imageView = (ImageView)view.findViewById(R.id.palaceImage);
        titleView = (TextView) view.findViewById(R.id.palaceTitle);
        coordView = (TextView) view.findViewById(R.id.palaceCoord);
        distanceView = (TextView) view.findViewById(R.id.palaceDistance);
    }
}

package com.example.ankit.imgshare2android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ankit on 11/9/14.
 */

final class NearbyAdaptor extends BaseAdapter {
    private final Context context;
    private final List<String> urls = new ArrayList<String>();
    Map<String, Double> photo_dist;

    public NearbyAdaptor(Context context, Map<String, Double> photo_dist) {
        this.context = context;
        this.photo_dist = photo_dist;

        // Ensure we get a different ordering of images on each run.
        //  Collections.addAll(urls, Data.URLS);
        //  Collections.shuffle(urls);

        for (String streamUrls : photo_dist.keySet()) {
            urls.add(streamUrls);
        }

        System.out.println("Reached.");
        // Triple up the list.
        //ArrayList<String> copy = new ArrayList<String>(urls);
        //urls.addAll(copy);
        //urls.addAll(copy);
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        //SquaredImageView view = (SquaredImageView) convertView;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView = null;
        ImageView imageView = null;
        if (convertView == null) {
            gridView = new View(context);


            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.row_grid, null);

            // set image based on selected text
            imageView = (ImageView) gridView
                    .findViewById(R.id.grid_image);


            // set value into textview
            TextView textView = (TextView) gridView
                    .findViewById(R.id.grid_item_label);


            // Get the image URL for the current position.
            String url = getItem(position);
            textView.setText(photo_dist.get(url).toString());

            // Trigger the download of the URL asynchronously into the image view.
            Picasso.with(context) //
                    .load(url) //
                    .placeholder(R.drawable.placeholder) //
                    .error(R.drawable.error) //
                    .fit() //
                            //.tag(context) //
                    .into(imageView);


            //view = new SquaredImageView(context);
            //view.setScaleType(CENTER_CROP);
        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

    @Override public int getCount() {
        return urls.size();
    }

    @Override public String getItem(int position) {
        return urls.get(position);
    }

    @Override public long getItemId(int position) {
        return position;
    }
}
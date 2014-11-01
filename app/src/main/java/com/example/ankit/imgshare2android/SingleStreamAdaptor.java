package com.example.ankit.imgshare2android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static android.widget.ImageView.ScaleType.CENTER_CROP;

final class SingleStreamAdaptor extends BaseAdapter {
    private final Context context;
    private final List<String> urls = new ArrayList<String>();

    public SingleStreamAdaptor(Context context, List<StreamUrls> streamUrlsList) {
        this.context = context;

        // Ensure we get a different ordering of images on each run.
        //  Collections.addAll(urls, Data.URLS);
        //  Collections.shuffle(urls);

        for (StreamUrls streamUrls : streamUrlsList) {
            urls.add(streamUrls.url);
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

            // Get the image URL for the current position.
            String url = getItem(position);

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
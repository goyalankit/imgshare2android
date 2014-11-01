package com.example.ankit.imgshare2android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SingleStreamActivity extends PicassoSampleActivity {
    List<StreamUrls> streamUrlsList = new ArrayList<StreamUrls>();

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_gridview_activity);

        Intent intent = getIntent();
        int position = intent.getIntExtra(SampleGridViewActivity.STREAM_INDEX, 0);
        getImageUrlsForStream(position);
    }

    public void setGridView() {
        GridView gv = (GridView) findViewById(R.id.grid_view);
        gv.setAdapter(new SingleStreamAdaptor(SingleStreamActivity.this, streamUrlsList));

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SingleStreamActivity.this, position + "#Selected",
                        Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void getImageUrlsForStream(int position) {
        Stream stream = CachedStreams.allStreams.get(position);
        HttpClient.get("stream/view.json?id=" + stream.id, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){

                Gson gson = new Gson();


                try {
                    JSONArray resultArray = (JSONArray)response.get("result");


                    for (int i = 0; i < resultArray.length(); i++) {
                        StreamUrls streamUrls = gson.fromJson(resultArray.get(i).toString(), StreamUrls.class);
                        streamUrlsList.add(streamUrls);
                    }

                    setGridView();

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {

                }
            }
        });

    }
}
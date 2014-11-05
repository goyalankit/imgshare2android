package com.example.ankit.imgshare2android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SingleStreamActivity extends FragmentActivity {
    List<StreamUrls> streamUrlsList = new ArrayList<StreamUrls>();
    Button loadMore;

    public static final String STREAM_NAME = "stream_name";
    public static final String STREAM_ID = "stream_id";

    String stream_id;
    String stream_name;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_gridview_activity);

        Intent intent = getIntent();
        int position = intent.getIntExtra(SampleGridViewActivity.STREAM_INDEX, 0);
        if (intent.getStringExtra(SearchActivity.BY_ID) == "true") {
            stream_id = intent.getStringExtra(SearchActivity.STREAM_ID);
            stream_name = intent.getStringExtra(SearchActivity.STREAM_NAME);
        } else {
            Stream stream = CachedStreams.allStreams.get(position);
            stream_id = stream.id;
            stream_name = stream.name;
        }
        getImageUrlsForStream();
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

    public void searchBtnHandler(View target) {
        Toast.makeText(SingleStreamActivity.this, "Search Button Clicked.",
                Toast.LENGTH_SHORT).show();
    }

    public void uploadImageBtn(View target) {
        Intent intent = new Intent(SingleStreamActivity.this, PhotoIntentActivity.class);
        intent.putExtra(STREAM_ID, stream_id);
        intent.putExtra(STREAM_NAME, stream_name);

        startActivity(intent);
    }

    public void getImageUrlsForStream() {
        HttpClient.get("stream/view.json?id=" + stream_id, null, new JsonHttpResponseHandler() {
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
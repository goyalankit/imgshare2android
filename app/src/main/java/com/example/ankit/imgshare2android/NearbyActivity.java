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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NearbyActivity extends FragmentActivity {
    public static List<StreamUrls> streamUrlsList = new ArrayList<StreamUrls>();;
    Button loadMore;

    public static final String STREAM_NAME = "stream_name";
    public static final String STREAM_ID = "stream_id";


    String stream_id;
    String stream_name;
    public static boolean getMore = false;
    private double latitude;
    private double longitude;
    public Map<String, Double> photo_dist = new HashMap<String, Double>();


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby);

        Intent intent = getIntent();
        getImageUrlsForStream();
    }

    public void streamBtnHandler(View view) {
        onBackPressed();

    }

    public void setGridView() {
        GridView gv = (GridView) findViewById(R.id.grid_view);
        gv.setAdapter(new NearbyAdaptor(NearbyActivity.this, photo_dist));

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(NearbyActivity.this, position + "#Selected",
                        Toast.LENGTH_SHORT).show();

            }
        });
    }


    public void getLocation() {
        GPSTracker gpsTracker = new GPSTracker(this);
        if (gpsTracker.canGetLocation()) {
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
            Toast.makeText(this, "Long: " + longitude + " Lat: " + latitude, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Couldn't get your location.", Toast.LENGTH_LONG).show();
        }
    }



    public void getImageUrlsForStream() {
        getLocation();
        HttpClient.get("nearby.json?latitude=" + latitude + "&longitude=" + longitude, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                Gson gson = new Gson();

                try {
                    JSONObject resultObject = (JSONObject)response.get("result");
                    JSONArray resultArray = (JSONArray)resultObject.get("response");

                    for (int i = 0; i < resultArray.length(); i++) {
                        JSONObject obj = (JSONObject)((JSONObject)resultArray.get(i)).get("photo");
                        Double dist = (Double)((JSONObject)resultArray.get(i)).get("dist");
                        photo_dist.put(obj.get("url").toString(), dist);

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

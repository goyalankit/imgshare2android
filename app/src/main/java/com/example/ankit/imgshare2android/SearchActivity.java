package com.example.ankit.imgshare2android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends FragmentActivity {

    List<Stream> searchResults = new ArrayList<Stream>();
    public final static String STREAM_ID = "stream_id";
    public final static String STREAM_NAME = "stream_name";
    public final static String BY_ID = "by_id";

    private Button searchBtn;
    private double latitude;
    private double longitude;

    TextView textView;
    GridView gv;
    String term;
    Boolean nearby = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_view);

        textView = (TextView) findViewById(R.id.resulttext);
        Intent intent = getIntent();
        term = intent.getStringExtra(SampleGridViewActivity.SEARCH_TERM);
        nearby = Boolean.parseBoolean(intent.getStringExtra(SampleGridViewActivity.NEARBY));

        getLocationAndCreateView();
    }

    public void getLocationAndCreateView() {
        GPSTracker gpsTracker = new GPSTracker(this);
        if (nearby && gpsTracker.canGetLocation()) {
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
            Toast.makeText(this, "Long: " + longitude + " Lat: " + latitude, Toast.LENGTH_LONG).show();
            getStreamFromSearchQuery(term, true);

        } else {
            Toast.makeText(this, "Couldn't get your location.", Toast.LENGTH_LONG).show();
            getStreamFromSearchQuery(term, false);
        }
    }

    public void setGridView() {
        textView.setText("Found "+ searchResults.size() +" results matching your query for " + term );
        gv = (GridView) findViewById(R.id.gridphoto);
        //sd = new SearchAdaptor(SearchActivity.this, searchResults);
        gv.setAdapter(new SearchAdaptor(SearchActivity.this, searchResults));

        searchBtn = (Button) findViewById(R.id.searchbtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                EditText editText = (EditText) findViewById(R.id.search_term);
                term = editText.getText().toString();
                searchResults.clear();
                getLocationAndCreateView();
            }
        });

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startSingleStreamActivity(position);

            }
        });
    }

    public void startSingleStreamActivity(int position) {
        Intent intent = new Intent(this, SingleStreamActivity.class);
        Stream stream = searchResults.get(position);
        intent.putExtra(STREAM_ID, stream.id);
        intent.putExtra(STREAM_NAME, stream.name);
        intent.putExtra(BY_ID, "true");
        startActivity(intent);
    }

    public void getStreamFromSearchQuery(String term, boolean use_location) {
        String url;
        if (use_location)
            url = "search.json?name=" + term + "&latitude=" + latitude + "&longitude=" + longitude;
        else
            url = "search.json?name=" + term;

        HttpClient.get(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Gson gson = new Gson();
                try {
                    JSONArray resultArray = (JSONArray) response.get("result");
                    for (int i = 0; i < resultArray.length(); i++) {
                        Stream stream = gson.fromJson(resultArray.get(i).toString(), Stream.class);
                        searchResults.add(stream);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    setGridView();
                }
            }
        });
    }
}
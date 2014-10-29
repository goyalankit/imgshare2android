package com.example.ankit.imgshare2android;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ManageActivity extends Activity {

    List<Stream> subStreams = new ArrayList<Stream>();
    List<Stream> ownStreams = new ArrayList<Stream>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        try {
            getManageContents();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_manage);

    }

    public void getManageContents() throws JSONException{
        HttpClient.get("manage.json", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                Gson gson = new Gson();

                try {
                    JSONObject result = (JSONObject) response.get("result");
                    JSONArray subscribed = ((JSONArray)result.getJSONArray("subscribed"));
                    JSONArray owned = ((JSONArray)result.getJSONArray("owned"));


                    for (int i = 0; i < subscribed.length(); i++) {
                        Stream stream = gson.fromJson(subscribed.get(i).toString(), Stream.class);
                        subStreams.add(stream);
                    }

                    for (int i = 0; i < owned.length(); i++) {
                        Stream stream = gson.fromJson(owned.get(i).toString(), Stream.class);
                        ownStreams.add(stream);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.manage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

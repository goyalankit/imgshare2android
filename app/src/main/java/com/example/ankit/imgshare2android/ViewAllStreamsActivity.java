package com.example.ankit.imgshare2android;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class ViewAllStreamsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_stream_with_button);

        try {
            getManageContents();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getManageContents() throws JSONException{
        HttpClient.get("view.json", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){

                Gson gson = new Gson();

                try {
                    JSONObject result = (JSONObject) response.get("result");
                    JSONArray subscribed = ((JSONArray)result.getJSONArray("subscribed"));
                    JSONArray owned = ((JSONArray)result.getJSONArray("owned"));


                    for (int i = 0; i < subscribed.length(); i++) {
                        Stream stream = gson.fromJson(subscribed.get(i).toString(), Stream.class);
                        CachedStreams.subStreams.add(stream);
                    }

                    for (int i = 0; i < owned.length(); i++) {
                        Stream stream = gson.fromJson(owned.get(i).toString(), Stream.class);
                        CachedStreams.ownStreams.add(stream);
                    }

                    CachedStreams.allStreams = new ArrayList<Stream>();
                    CachedStreams.allStreams.addAll(CachedStreams.ownStreams);
                    CachedStreams.allStreams.addAll(CachedStreams.subStreams);

                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(ViewAllStreamsActivity.this, SampleGridViewActivity.class);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
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

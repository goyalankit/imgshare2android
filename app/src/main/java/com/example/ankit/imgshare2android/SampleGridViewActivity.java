package com.example.ankit.imgshare2android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;



import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class SampleGridViewActivity extends FragmentActivity {
    public final static String SEARCH_TERM = "search_term";
    public final static String STREAM_INDEX = "stream_index";
    public final static String NEARBY = "nearby";
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_stream_with_button);

        GridView gv = (GridView) findViewById(R.id.gridphoto);
        gv.setAdapter(new SampleGridViewAdapter(this));
        gv.setOnScrollListener(new SampleScrollListener(this));

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(SampleGridViewActivity.this, position + "#Selected",
                                    Toast.LENGTH_SHORT).show();
            startSingleStreamActivity(position);
            }


//            public void onItemClick(AdapterView<?> parent, View v,
//                                                int position, long id) {
//
//            Toast.makeText(SampleGridViewActivity.this, position + "#Selected",
//                                    Toast.LENGTH_SHORT).show();
//            }
        });

    }

    public void nearby_search(View target) {

    }

    public void mySubStreamsHandler(View target) {

    }

    public void searchBtnHandler(View target) {

        Boolean nearby = ((Button) target).getText().toString().contains("Nearby");

        Intent intent = new Intent(this, SearchActivity.class);
        EditText editText = (EditText) findViewById(R.id.search_term);
        String message = editText.getText().toString();
        intent.putExtra(SEARCH_TERM, message);
        intent.putExtra(NEARBY, nearby.toString());
        startActivity(intent);
        Toast.makeText(SampleGridViewActivity.this, "Search Button Clicked with message = " + message,
                Toast.LENGTH_SHORT).show();
    }

    public void startSingleStreamActivity(int position) {
        Intent intent = new Intent(this, SingleStreamActivity.class);
        intent.putExtra(STREAM_INDEX, position);
        startActivity(intent);
    }
}
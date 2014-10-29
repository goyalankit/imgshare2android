package com.example.ankit.imgshare2android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;



import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class SampleGridViewActivity extends PicassoSampleActivity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_gridview_activity);

        GridView gv = (GridView) findViewById(R.id.grid_view);
        gv.setAdapter(new SampleGridViewAdapter(this));
        gv.setOnScrollListener(new SampleScrollListener(this));

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SampleGridViewActivity.this, position + "#Selected",
                                    Toast.LENGTH_SHORT).show();
            }


//            public void onItemClick(AdapterView<?> parent, View v,
//                                                int position, long id) {
//
//            Toast.makeText(SampleGridViewActivity.this, position + "#Selected",
//                                    Toast.LENGTH_SHORT).show();
//            }
        });


    }
}
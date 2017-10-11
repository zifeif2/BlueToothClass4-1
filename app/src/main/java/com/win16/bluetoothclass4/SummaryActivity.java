package com.win16.bluetoothclass4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by zifeifeng on 10/10/17.
 */

public class SummaryActivity extends Activity {
    private TextView motion_range_tv;
    private TextView max_v_tv;
    private TextView max_r_tv;
    private TextView bimuscle_status_tv;
    private TextView trimuscle_status_tv;
    private TextView extra_tv;
    private Button shared_btn;
    private Button reset_btn;
    private Button next_btn;
    private String[] attr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        initUI();
        Intent i = getIntent();

    }

    private void initUI(){
        motion_range_tv = (TextView) findViewById(R.id.datasummary_rm_tv);
        max_r_tv = (TextView) findViewById(R.id.datasummary_maxv_tv);
        max_v_tv = (TextView) findViewById(R.id.datasummary_maxr_tv);
        bimuscle_status_tv = (TextView) findViewById(R.id.datasummary_bistatus_tv);
        trimuscle_status_tv = (TextView) findViewById(R.id.datasummary_tristatus_tv);
        extra_tv =(TextView) findViewById(R.id.datasummary_extrainfo_tv);
        shared_btn = (Button) findViewById(R.id.datasummary_share_btn);
        reset_btn=(Button) findViewById(R.id.datasummary_reset_btn);
        next_btn=(Button) findViewById(R.id.datasummary_newsub_btn);
        shared_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);

                sendIntent.putExtra(Intent.EXTRA_TEXT, getSharedString());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SummaryActivity.this, MainActivity.class);
                startActivity(i);
            }
        });


    }

    private String getSharedString(){
        //return getString(R.string.shared_content, )
        return null;
    }
}

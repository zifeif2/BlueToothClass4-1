package com.win16.bluetoothclass4;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by zifeifeng on 11/8/17.
 */

public class PreRecordActivity extends Activity {

    private TextView tv_instruction1;
    private TextView tv_instruction2;
    private TextView tv_instruction3;
    private Button btn_start;
    private int step = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_record);
        initUI();
    }
    private void initUI(){
        tv_instruction1 = (TextView) findViewById(R.id.tv_instruction1);
        tv_instruction2 = (TextView) findViewById(R.id.tv_instruction2);
        tv_instruction3 = (TextView) findViewById(R.id.tv_instruction3);
        btn_start = (Button) findViewById(R.id.btn_calibration_start);

    }
}

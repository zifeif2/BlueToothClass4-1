package com.win16.bluetoothclass4;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.win16.bluetoothclass4.connect.ConnectThread;

import java.io.UnsupportedEncodingException;

import static com.win16.bluetoothclass4.MainActivity.EXTRA_SUBJECT_ID;
import static com.win16.bluetoothclass4.Shared.ACTIVITY_TRACKER;
import static com.win16.bluetoothclass4.Shared.STEP_TRACKER;

/**
 * Created by zifeifeng on 11/8/17.
 */

public class PreRecordActivity extends AppCompatActivity {

    private Button btn_instruction1;
    private Button btn_instruction2;
    private Button btn_instruction3;
    private Button btn_start;
    private int step;
    private CountDownTimer mCountDownTimer;
    private CountDownTimer mCountdownTimerAfterStart;
    private ConnectThread mConnectThread;
    private Toolbar myToolbar;
    private Button goback_btn;
    private Button next_btn;
    private MyFileWriter mMyFileWriter;
    private String subjectID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_record);
        subjectID = getIntent().getStringExtra(EXTRA_SUBJECT_ID);
        initUI();
        Shared.putInt(getApplicationContext(), ACTIVITY_TRACKER, 1);
        step = Shared.getInt(getApplicationContext(), STEP_TRACKER, 0);
        initializeInstruction();
        mMyFileWriter = MyFileWriter.get(subjectID, "", PreRecordActivity.this);
        mConnectThread = ConnectThread.get(null, null, null);
        mCountDownTimer = new CountDownTimer(1*1000, 250) {//apple
            int secondsLeft = 0;
            @Override
            public void onTick(long ms) {

                if (Math.round((float)ms / 1000.0f) != secondsLeft)
                {
                    secondsLeft = Math.round((float)ms / 1000.0f);
                    btn_start.setText(""+secondsLeft+" seconds left");
                    btn_start.setEnabled(false);
                }
            }
            @Override
            public void onFinish() {
//                startService(stopRingtoneIntent);
                say("q");//if there is not enough storage space, can't send data
                mMyFileWriter.writeData("\n-----------------------"+getTag()+"--------------------\n");
                mCountdownTimerAfterStart.start();
            }
        };
       mCountdownTimerAfterStart = new CountDownTimer(1*1000, 1000) {//apple
            @Override
            public void onTick(long l) {
                int secondsLeft = Math.round((float)l / 1000.0f);
                btn_start.setText("Start to record: "+secondsLeft+" seconds left");
            }
            @Override
            public void onFinish() {
                say("s");
                Toast.makeText(PreRecordActivity.this, "Finish recording", Toast.LENGTH_SHORT).show();
               updateStep();

                btn_start.setEnabled(true);
            }
        };

    }
    private View.OnClickListener InstructionOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id){
                case R.id.btn_instruction1:
                    step = 10;
                    initializeInstruction();

                    break;
                case R.id.btn_instruction2:
                    step = 11;
                    initializeInstruction();

                    break;
                case R.id.btn_instruction3:
                    step = 12;
                    initializeInstruction();

                    break;
                default:

            }
        }
    };
    private void initUI(){
        btn_instruction1 = (Button) findViewById(R.id.btn_instruction1);
        btn_instruction2 = (Button) findViewById(R.id.btn_instruction2);
        btn_instruction3 = (Button) findViewById(R.id.btn_instruction3);
        btn_instruction1.setOnClickListener(InstructionOnClickListener);
        btn_instruction2.setOnClickListener(InstructionOnClickListener);
        btn_instruction3.setOnClickListener(InstructionOnClickListener);
        btn_start = (Button) findViewById(R.id.btn_calibration_start);
        myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        goback_btn = (Button) findViewById(R.id.btn_back);
        next_btn = (Button) findViewById(R.id.btn_forward);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PreRecordActivity.this, DataRecordActivity.class);
                i.putExtra(EXTRA_SUBJECT_ID, subjectID);
                startActivity(i);
            }
        });

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCountDownTimer.start();
            }
        });
        next_btn.setEnabled(false);
    }

    private void say(String word) {

        if( mConnectThread != null) {
            try {
                mConnectThread.sendData(word.getBytes("utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        else{
            Log.e("Main Activity", "mConnect Thread is null");
        }
    }

    private void updateStep(){
        step++;
        switch(step){
            case 0:
                //The button is first click
                break;
            case 1:
                Shared.putInt(getApplicationContext(), STEP_TRACKER, 1);
            case 11:
                if(Shared.getInt(getApplicationContext(), STEP_TRACKER, 1)<1)
                    Shared.putInt(getApplicationContext(), STEP_TRACKER, 1);
                //The button is after first click
                //highlight the second step
                btn_instruction2.setTextColor(getResources().getColor(R.color.color_black));
                btn_instruction1.setTextColor(getResources().getColor(R.color.inactive_color));
                btn_start.setText(getResources().getString(R.string.sensitivity_collect));
                btn_instruction2.setEnabled(true);

                break;
            case 2:
                Shared.putInt(getApplicationContext(), STEP_TRACKER, 2);
            case 12:
                if(Shared.getInt(getApplicationContext(), STEP_TRACKER, 1)<2){
                    Shared.putInt(getApplicationContext(), STEP_TRACKER, 2);
                }
                btn_instruction3.setTextColor(getResources().getColor(R.color.color_black));
                btn_instruction2.setTextColor(getResources().getColor(R.color.inactive_color));
                btn_start.setText(getResources().getString(R.string.sensitivity_collect));

                btn_instruction3.setEnabled(true);
                break;
            default:
                btn_instruction3.setTextColor(getResources().getColor(R.color.inactive_color));
                next_btn.setEnabled(true);
                btn_start.setText("calibration finished");
                Shared.putInt(getApplicationContext(), STEP_TRACKER, 3);
                btn_start.setEnabled(false);
        }
    }
    private String getTag(){
        switch(step) {
            case 10:
            case 0:
                //The button is first click
                return "rest_arm";
            case 11:
            case 1:
                //The button is clicked the second time
                //highlight the second step
                return "flex_biceps";
            case 12:
            case 2:
                return "flex_triceps";
            default:
                btn_instruction3.setTextColor(getResources().getColor(R.color.inactive_color));
                next_btn.setEnabled(true);
                return "";
        }
    }
    private void initializeInstruction(){
        switch (step){
            case 0:
                btn_instruction2.setEnabled(false);
                btn_instruction3.setEnabled(false);
            case 10:
                btn_instruction3.setTextColor(getResources().getColor(R.color.inactive_color));
                btn_instruction2.setTextColor(getResources().getColor(R.color.inactive_color));
                btn_instruction1.setTextColor(getResources().getColor(R.color.color_black));

                break;
            case 1:
                btn_instruction3.setEnabled(false);
            case 11:
                //finish the first step last time
                btn_instruction2.setTextColor(getResources().getColor(R.color.color_black));
                btn_instruction1.setTextColor(getResources().getColor(R.color.inactive_color));
                btn_instruction3.setTextColor(getResources().getColor(R.color.inactive_color));
                break;
            case 12:
            case 2:
                //finish the second step last time
                btn_instruction3.setTextColor(getResources().getColor(R.color.color_black));
                btn_instruction1.setTextColor(getResources().getColor(R.color.inactive_color));
                btn_instruction2.setTextColor(getResources().getColor(R.color.inactive_color));
                break;
            default:
                //finish the third step last time
                btn_instruction1.setTextColor(getResources().getColor(R.color.inactive_color));
                btn_instruction2.setTextColor(getResources().getColor(R.color.inactive_color));
                btn_instruction3.setTextColor(getResources().getColor(R.color.inactive_color));
                next_btn.setEnabled(true);
                break;
        }
    }
}


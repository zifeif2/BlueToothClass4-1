package com.win16.bluetoothclass4;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.win16.bluetoothclass4.connect.ConnectThread;

import java.io.UnsupportedEncodingException;

import static com.win16.bluetoothclass4.Shared.ACTIVITY_TRACKER;
import static com.win16.bluetoothclass4.Shared.HAS_BEEN_SAHRED;
import static com.win16.bluetoothclass4.Shared.RECORD_STEP_TRACKER;
import static com.win16.bluetoothclass4.Shared.TO_UNFINISH;

/**
 * Created by zifeifeng on 11/11/17.
 */

public class DataRecordActivity extends AppCompatActivity {
    ImageButton[] btns = new ImageButton[12];
    Button btn_share;
    TextView textview_speed,  textview_bicep_active,  textview_tricep_active,  textview_flexion_title,  textview_extension_title;
    TextView tv_flexion, tv_extension;
    int done_step;
    int step;
    ConnectThread mConnectThread;
    MyFileWriter mMyFileWriter;
    String userid;
    CountDownTimer mCountdownTimerBeforeStart;
    CountDownTimer mCountdownTimerAfterStart;
    private static String TEST_TERM;
    int lowerbounce = 40;
    int higherbounce = 100;
//done step is the number of the steps that is done == the step number that need to be done next
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        Shared.putInt(getApplicationContext(), ACTIVITY_TRACKER, 2);
        done_step = step = Shared.getInt(getApplicationContext(), RECORD_STEP_TRACKER, 0);
        Log.e("done step", ""+done_step);
        initUI();
        mMyFileWriter = MyFileWriter.get(null, null, null);
        mConnectThread = ConnectThread.get(null, null, null); //use the connect thread to send command
        userid = getIntent().getStringExtra(MainActivity.EXTRA_SUBJECT_ID);
        //mMyFileWriter = MyFileWriter.get(null, null, null);

        mCountdownTimerBeforeStart =  new CountDownTimer(2*1000, 250) {
            int secondsLeft = 0;
            @Override
            public void onTick(long ms) {

                if (Math.round((float)ms / 1000.0f) != secondsLeft)
                {
                    secondsLeft = Math.round((float)ms / 1000.0f);
                    btn_share.setText(""+secondsLeft);
                }
            }

            @Override
            public void onFinish() {
                mMyFileWriter.writeData("\n---------------------trail"+step%12+"-----------------------\n");
                say("q");//if there is not enough storage space, can't send data
                mCountdownTimerAfterStart.start();
            }
        };

        mCountdownTimerAfterStart = new CountDownTimer(1*1000, 250) {
            int secondsLeft = 0;
            @Override
            public void onTick(long ms) {
                if (Math.round((float)ms / 1000.0f) != secondsLeft)
                {
                    secondsLeft = Math.round((float)ms / 1000.0f);
                    btn_share.setText(""+secondsLeft);

                }
            }

            @Override
            public void onFinish() {
                say("s");
                Toast.makeText(DataRecordActivity.this, "Finish recording", Toast.LENGTH_SHORT).show();
                float speed = mMyFileWriter.getAvgNumber();
                Log.e("speedavg", ""+speed);
                int remainder= step%12;
                btn_share.setText("SHARE");
                if((remainder < 3 && speed>lowerbounce)||(remainder>5 &&remainder<9 && speed < higherbounce)&&(remainder<6 && remainder>2&&(speed<lowerbounce||speed>higherbounce)) ){
                    Bundle bundle = new Bundle();
                    bundle.putString(NegativeResultFragment.ARG_RESULT, "Please adapt the speed");
                    NegativeResultFragment dialog = new NegativeResultFragment();
                    dialog.setArguments(bundle);
                    dialog.show(getSupportFragmentManager(),"speed");
                }
                else {
                    done_step = done_step <= step % 12 ? step % 12 + 1 : done_step;
                    Shared.putInt(getApplicationContext(), RECORD_STEP_TRACKER, done_step);
                    step++;
                    if(done_step == 12){
                        Shared.putBoolean(getApplicationContext(), TO_UNFINISH ,false);
                    }
                    updateUI();
                }
            }

        };
    }

    private View.OnClickListener ButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            for(int i = 0; i < 12; i++){
                if(btns[i].getId() == id){
                    step = i+12;

                    for(int j =0; j < done_step; j++){
                        if(j!=i){
                            btns[j].setImageResource(getCorrectResource(j, 1));
                        }
                    }
                    btns[done_step].setImageResource(getCorrectResource(done_step, 0));
                    btns[i].setImageResource(getCorrectResource(i, 2));
                    break;
                }
            }

            mCountdownTimerBeforeStart.start();

        }
    };
    private void initUI(){
        textview_speed = (TextView) findViewById(R.id.ID_textview_speed);
        textview_bicep_active = (TextView) findViewById(R.id.ID_textview_bicep_active);
        textview_tricep_active = (TextView) findViewById(R.id.ID_textview_tricep_active);
        textview_flexion_title = (TextView) findViewById(R.id.ID_textview_flexion_title);
        textview_extension_title = (TextView) findViewById(R.id.ID_textview_extension_title);

        btns[0] = (ImageButton) findViewById(R.id.ID_imagebutton_slow_1);
        btns[1] = (ImageButton) findViewById(R.id.ID_imagebutton_slow_2);
        btns[2] = (ImageButton) findViewById(R.id.ID_imagebutton_slow_3);
        btns[3] = (ImageButton) findViewById(R.id.ID_imagebutton_medium_1);
        btns[4] = (ImageButton) findViewById(R.id.ID_imagebutton_medium_2);
        btns[5] = (ImageButton) findViewById(R.id.ID_imagebutton_medium_3);
        btns[6] = (ImageButton) findViewById(R.id.ID_imagebutton_fast_1);
        btns[7] = (ImageButton) findViewById(R.id.ID_imagebutton_fast_2);
        btns[8] = (ImageButton) findViewById(R.id.ID_imagebutton_fast_3);
        btns[9] = (ImageButton) findViewById(R.id.ID_imagebutton_preference_1);
        btns[10] = (ImageButton) findViewById(R.id.ID_imagebutton_preference_2);
        btns[11]= (ImageButton) findViewById(R.id.ID_imagebutton_preference_3);
        for(int i = 0; i < 12; i++) {
            btns[i].setEnabled(false);
            btns[i].setOnClickListener(ButtonClickListener);

        }
        btn_share = (Button) findViewById(R.id.ID_button_share);
        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareByEmail();
                Shared.putBoolean(getApplicationContext(), HAS_BEEN_SAHRED, true);
                Shared.putBoolean(getApplicationContext(), TO_UNFINISH, false);
            }
        });
        initializeInstruction();
    }

    private void initializeInstruction(){
            for(int i = 0; i < done_step; i++){
                btns[i].setImageResource(getCorrectResource(i, 1));
                btns[i].setEnabled(true);
            }
            btns[done_step].setImageResource(getCorrectResource(done_step, 2));
            btns[done_step].setEnabled(true);

    }

    private int getCorrectResource(int i, int type){
        if(type == 0) {//the color is grey
            if (i % 3 == 0) {
                return (R.drawable.ic_looks_one_teal_24dp);
            } else if (i % 3 == 1) {
                return (R.drawable.ic_looks_two_teal_24dp);
            } else {
                return (R.drawable.ic_looks_3_teal_24dp);
            }
        }
        if(type == 1) {//the color is green
            if (i % 3 == 0) {
                return (R.drawable.ic_looks_one_finish_24dp);
            } else if (i % 3 == 1) {
                return (R.drawable.ic_looks_two_finish_24dp);
            } else {
                return (R.drawable.ic_looks_3_finish_24dp);
            }
        }
        else{//the color is red
            if (i % 3 == 0) {
                return (R.drawable.ic_looks_one_red_24dp);
            } else if (i % 3 == 1) {
                return (R.drawable.ic_looks_two_red_24dp);
            } else {
                return (R.drawable.ic_looks_3_red_24dp);
            }
        }
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
    private void updateUI(){
        if(step%12>0) {
            btns[step%12-1].setImageResource(getCorrectResource(step-1, 1));
            btns[done_step].setImageResource(getCorrectResource(done_step, 2));
            btns[done_step].setEnabled(true);
        }
        else{
            btns[11].setImageResource(getCorrectResource(step, 1));
        }
    }
    private void shareByEmail(){
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822").putExtra(Intent.EXTRA_EMAIL, new String[]{"77bisko@gmail.com"}).putExtra(android.content.Intent.EXTRA_SUBJECT, "Patient Data");
        String targetFilePath = MyFileWriter.getFilepath();
        Log.e("share File", targetFilePath);
        Uri attachmentUri = Uri.parse(targetFilePath);
        emailIntent.putExtra(android.content.Intent.EXTRA_STREAM, Uri.parse("file://" + attachmentUri));
        startActivity(emailIntent);
    }
}

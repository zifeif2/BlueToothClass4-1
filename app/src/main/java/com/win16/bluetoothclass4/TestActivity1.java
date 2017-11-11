package com.win16.bluetoothclass4;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.win16.bluetoothclass4.connect.ConnectThread;
import com.win16.bluetoothclass4.connect.Constant;

import java.io.UnsupportedEncodingException;

/**
 * Created by zifeifeng on 11/7/17.
 */

public class TestActivity1 extends Activity {
    Button btn1;
    Button btn2;
    Button btn3;
    ConnectThread mConnectThread;
    FileWriter mFileWriter;
    String userid;
    CountDownTimer mCountdownTimerBeforeStart;
    CountDownTimer mCountdownTimerAfterStart;
    private static String TEST_TERM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        mConnectThread = ConnectThread.get(null, null, null); //use the connect thread to send command
        userid = getIntent().getStringExtra(MainActivity.EXTRA_SUBJECT_ID);
        mFileWriter = FileWriter.get(null, null, null);

        mCountdownTimerBeforeStart =  new CountDownTimer(3*1000, 250) {
            int secondsLeft = 0;
            @Override
            public void onTick(long ms) {

                if (Math.round((float)ms / 1000.0f) != secondsLeft)
                {
                    secondsLeft = Math.round((float)ms / 1000.0f);
                    Toast.makeText(TestActivity1.this, ""+secondsLeft, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFinish() {
//                startService(stopRingtoneIntent);
                mFileWriter.writeData("\n---------------------"+TEST_TERM+"-----------------------\n");
                say("q");//if there is not enough storage space, can't send data
                mCountdownTimerAfterStart.start();
            }
        };

        mCountdownTimerAfterStart = new CountDownTimer(5*1000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                say("s");
                Toast.makeText(TestActivity1.this, "Finish recording", Toast.LENGTH_SHORT).show();
            }

        };
    }


    private void initUI(){
        setContentView(R.layout.test_activity1);
        btn1 = (Button) findViewById(R.id.btn_section1);
        btn2 = (Button) findViewById(R.id.btn_section2);
        btn3 = (Button) findViewById(R.id.btn_section3);
        btn1.setOnClickListener(ButtonClickListener);
        btn2.setOnClickListener(ButtonClickListener);
        btn3.setOnClickListener(ButtonClickListener);
    }
    private View.OnClickListener ButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!mFileWriter.isExternalStorageWritable()){
                Toast.makeText(TestActivity1.this, "Do not have storage to write data", Toast.LENGTH_SHORT).show();
                return;
            }
            switch(v.getId())
            {
                case R.id.btn_section1 :
                    TEST_TERM = "section1";
                    break;
                case R.id.btn_section2 :
                    TEST_TERM = "section2";
                    break;
                case R.id.btn_section3 :
                    TEST_TERM = "section3";
                    break;
            }

            mCountdownTimerBeforeStart.start();

        }
    };


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
        // J mBluetoothConnection.write(word.getBytes());
    }

}

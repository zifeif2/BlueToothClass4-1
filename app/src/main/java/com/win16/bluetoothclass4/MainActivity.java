package com.win16.bluetoothclass4;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

//import com.win16.bluetoothclass4.connect.AcceptThread;
import com.win16.bluetoothclass4.connect.ConnectThread;
import com.win16.bluetoothclass4.connect.Constant;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Rex on 2015/5/27.
 */
public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_SUBJECT_ID = "com.win16.bluetoothclass4.subject_id";
    public static final String EXTRA_SUBJECT_GENDER = "com.win16.bluetoothclass4.subject_gender";
    public static final String EXTRA_CATEGORY_SELECTED = "com.win16.bluetoothclass4.category_selected";
    public static final String EXTRA_HEIGHT_FEET = "com.win16.bluetoothclass4.height_feet";
    public static final String EXTRA_HEIGHT_INCH = "com.win16.bluetoothclass4.height_inch";
    public static final String EXTRA_FOREARM_LENGTH = "com.win16.bluetoothclass4.forearm_length";
    public static final String EXTRA_SUBJECT_WEIGHT = "com.win16.bluetoothclass4.subject_weight";
    public static final String EXTRA_SUBJECT_DOB = "com.win16.bluetoothclass4.subject_dob";
    public static final String EXTRA_SUBJECT_TEST_DATE = "com.win16.bluetoothclass4.subject_test_date";
    public static final String EXTRA_POSITION = "com.win16.bluetoothclass4.position";
    public static final String EXTRA_VELOCITY = "com.win16.bluetoothclass4.velocity";
    public static final String EXTRA_RESISTANT = "com.win16.bluetoothclass4.resistant";
    public static final String EXTRA_BICOUNT = "com.win16.bluetoothclass4.bicount";
    public static final String EXTRA_TRICOUNT = "com.win16.bluetoothclass4.tricount";
    public static final String EXTRA_CALLER = "com.win16.bluetoothclass4.caller";
    public static final int REQUEST_CODE = 0;
    private List<BluetoothDevice> mDeviceList = new ArrayList<>();
    private List<BluetoothDevice> mBondedDeviceList = new ArrayList<>();

    private BlueToothController mController = new BlueToothController();
    private ListView mListView;
    private DeviceAdapter mAdapter;
    private Toast mToast;
   // private AcceptThread mAcceptThread;
    private ConnectThread mConnectThread;

    String subjectID = null;
    String categorySelected;
    String heightFeet;
    String heightInch;
    String forearmLength;
    String subjectWeight;
    String subjectDOB;
    String subjectTestDate;
    String subjectGender;


    private TextView position_tv;
    private TextView velocity_tv;
    private TextView resistance_tv;
    private TextView bimuscle_tv;
    private TextView trimuscletv;
    private TextView server_tv;
    private Button start_btn;
    private Button pause_btn;
    private Button stop_btn;
    private float maxPosition=0;
    private float maxVelocity = 0;
    private float maxResistant = 0;
    BluetoothDevice device;
    FileWriter mFileWriter;
    int bi_sample_counter = 0;
    int tri_sample_counter = 0;
    private static final float THRESHOLD = 1.0f;
    private CountDownTimer mCountDownTimer;
    File report_file;
    FileOutputStream outputStream;

    //boolean start_btn_pressed = false;
    //J
//    BluetoothConnection mBluetoothConnection;
//    BluetoothAdapter mBluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActionBar();
        setContentView(R.layout.activity_main);
        initUI();
        registerBluetoothReceiver();
        mController.turnOnBlueTooth(this, REQUEST_CODE);
        Intent i = getIntent();
        forearmLength = i.getStringExtra(EXTRA_FOREARM_LENGTH);
        subjectID = i.getStringExtra(EXTRA_SUBJECT_ID);
        mFileWriter = new FileWriter(subjectID, this, composeInitialContent());
        mCountDownTimer = new CountDownTimer(3*1000, 1000) {
            @Override
            public void onTick(long l) {
                Toast.makeText(getApplicationContext(), "Ready in "+l/1000+" seconds", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish() {
//                startService(stopRingtoneIntent);
                say("q");//if there is not enough storage space, can't send data
                Log.e("finish timer", "finish saying q");
            }
        };

        //outputStream = openFileOutput()
////J
//        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter("sentArduinoData"));
//        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    }
    BroadcastReceiver mJReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String text = intent.getStringExtra("theData");

            updateData(text);
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        maxPosition=0;
         maxVelocity = 0;
        maxResistant = 0;
        position_tv.setText("0.0");
        velocity_tv.setText("0.0");
        bimuscle_tv.setText("0.0");
        trimuscletv.setText("0.0");
        resistance_tv.setText("0.0");

    }

    //    /*Helper function J */
//    public void startBtConnection(BluetoothDevice device, UUID uuid){
//        mBluetoothConnection = new BluetoothConnection(MainActivity.this, mBluetoothAdapter);
//        mBluetoothConnection.startClient(device, uuid);
//    }

    private void registerBluetoothReceiver() {
        IntentFilter filter = new IntentFilter();
        //start to discover devices
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        //finish discovering devices
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        //found the device
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        //change the scan mode
        filter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        //bounded status change
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);

        registerReceiver(mReceiver, filter);
    }

    private Handler mUIHandler = new MyHandler();

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if( BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action) ) {
                setProgressBarIndeterminateVisibility(true);
                //initialize the list
                mDeviceList.clear();
                mAdapter.notifyDataSetChanged();
            }
            else if( BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                setProgressBarIndeterminateVisibility(false);
            }
            else if( BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //add the device that we found
                mDeviceList.add(device);
                mAdapter.notifyDataSetChanged();
            }
            else if( BluetoothAdapter.ACTION_SCAN_MODE_CHANGED.equals(action)) {
               int scanMode = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE,0);
                if( scanMode == BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
                    setProgressBarIndeterminateVisibility(true);
                }
                else {
                    setProgressBarIndeterminateVisibility(false);
                }
            }
            else if( BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action) ) {
                BluetoothDevice remoteDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if( remoteDevice == null ) {
                    showToast("no device");
                    server_tv.setText(getString(R.string.no_connection));
                    return;
                }
                int status = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE,0);
                if( status == BluetoothDevice.BOND_BONDED) {
                    showToast("Bonded " + remoteDevice.getName());
                }
                else if( status == BluetoothDevice.BOND_BONDING){
                    showToast("Bonding " + remoteDevice.getName());
                }
                else if(status == BluetoothDevice.BOND_NONE){
                    showToast("Not bond " + remoteDevice.getName());
                }
            }
        }
    };


    private void initUI() {
        mListView = (ListView) findViewById(R.id.device_list);
        mAdapter = new DeviceAdapter(mDeviceList, this);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(bindDeviceClick);
        position_tv = (TextView) findViewById(R.id.datarecord_position);
        resistance_tv = (TextView) findViewById(R.id.datarecord_resistance);
        velocity_tv = (TextView) findViewById(R.id.datarecord_velocity);
        bimuscle_tv = (TextView) findViewById(R.id.datarecord_bimuscleemg);
        trimuscletv = (TextView) findViewById(R.id.datarecord_trisemg);
        server_tv = (TextView) findViewById(R.id.server_tv);
        start_btn = (Button) findViewById(R.id.datarecord_start);
        pause_btn =(Button) findViewById(R.id.datarecord_pause);
        stop_btn =(Button) findViewById(R.id.datarecord_stop);
        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mFileWriter.isExternalStorageWritable()){
                    Toast.makeText(MainActivity.this, "Do not have storage to write data", Toast.LENGTH_SHORT).show();
                    return;
                }
                mCountDownTimer.start();
                start_btn.setClickable(false);
                pause_btn.setClickable(true);

            }
        });

        pause_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                say("s");
                mCountDownTimer.cancel();
                //start_btn_pressed = false;
                start_btn.setClickable(true);
                pause_btn.setClickable(false);
            }
        });

        stop_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                say("s");
                mCountDownTimer.cancel();
                //start_btn_pressed = false;
                start_btn.setClickable(true);
                pause_btn.setClickable(false);
                mFileWriter.closeOutputStream();
                Intent intent = new Intent(MainActivity.this, SummaryActivity.class);
                intent.putExtra(EXTRA_POSITION, maxPosition);
                intent.putExtra(EXTRA_VELOCITY, maxVelocity);
                intent.putExtra(EXTRA_RESISTANT, maxResistant);
                intent.putExtra(EXTRA_SUBJECT_ID, getIntent().getStringExtra(EXTRA_SUBJECT_ID));
                intent.putExtra(EXTRA_CATEGORY_SELECTED, getIntent().getStringExtra(EXTRA_CATEGORY_SELECTED));
                intent.putExtra(EXTRA_HEIGHT_FEET, getIntent().getStringExtra(EXTRA_HEIGHT_FEET));
                intent.putExtra(EXTRA_HEIGHT_INCH, getIntent().getStringExtra(EXTRA_HEIGHT_INCH));
                intent.putExtra(EXTRA_FOREARM_LENGTH, getIntent().getStringExtra(EXTRA_FOREARM_LENGTH));
                intent.putExtra(EXTRA_SUBJECT_WEIGHT, getIntent().getStringExtra(EXTRA_SUBJECT_WEIGHT));
                intent.putExtra(EXTRA_SUBJECT_DOB, getIntent().getStringExtra(EXTRA_SUBJECT_DOB));
                intent.putExtra(EXTRA_SUBJECT_TEST_DATE, getIntent().getStringExtra(EXTRA_SUBJECT_TEST_DATE));
                intent.putExtra(EXTRA_BICOUNT, bi_sample_counter);
                intent.putExtra(EXTRA_TRICOUNT, tri_sample_counter);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if( mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }
        //start_btn_pressed = false;
        unregisterReceiver(mReceiver);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void showToast(String text) {

        if( mToast == null) {
            mToast = Toast.makeText(this, text, Toast.LENGTH_LONG);
        }
        else {
            mToast.setText(text);
        }
        mToast.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.enable_visiblity) {
            //mController.enableVisibly(this);
            mFileWriter.writeData("hello\n");
        }
        if( id == R.id.find_device) {
            //look for device
            mAdapter.refresh(mDeviceList);
            mController.findDevice();
            mListView.setOnItemClickListener(bindDeviceClick);
        }
         if (id == R.id.bonded_device) {
            //look for the bounded device
            mBondedDeviceList = mController.getBondedDeviceList();
            //J mBondedDeviceList.addAll(mBluetoothAdapter.getBondedDevices());
            mAdapter.refresh(mBondedDeviceList);
            mListView.setOnItemClickListener(bindedDeviceClick);
        }
        else if( id == R.id.disconnect) {
            if( mConnectThread != null) {
                mConnectThread.cancel();
                mConnectThread = null;
                server_tv.setText(getString(R.string.no_connection));
            }
        }
        else if( id == R.id.say_hi_to_test) {
           say("*");
        }
        else if( id == R.id.say_hi_to_real) {
            say("q");
        }

        return super.onOptionsItemSelected(item);
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
        // J mBluetoothConnection.write(word.getBytes());
    }

    private AdapterView.OnItemClickListener bindDeviceClick = new AdapterView.OnItemClickListener() {
        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            BluetoothDevice device = mDeviceList.get(i);
            if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                device.createBond();
            }
        }
    };

    private AdapterView.OnItemClickListener bindedDeviceClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
             device = mBondedDeviceList.get(i);
            if( mConnectThread != null) {
                mConnectThread.cancel();
                mConnectThread = null;
            }

            // TODO:  might have problem with connect thread
          //J  startBtConnection(device, Constant.CONNECTTION_UUID);


            mConnectThread = new ConnectThread(device, mController.getAdapter(), mUIHandler);
            mConnectThread.start();
            if(mConnectThread == null){
                mConnectThread = new ConnectThread(device, mController.getAdapter(), mUIHandler);
                mConnectThread.start();
            }
        }
    };

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String id = intent.getStringExtra(EXTRA_SUBJECT_ID);

        if(id !=null) { // come from userdatapage
            subjectID = id;
            setIntent(intent);
        }
        if(mFileWriter!=null){
            mFileWriter.updateId(subjectID, composeInitialContent());
        }

    }

    private void initActionBar() {

        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        setProgressBarIndeterminate(true);
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class
                    .getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeData(String str){
        if(outputStream == null) {
            try {
                outputStream = openFileOutput(subjectID + ".txt", Context.MODE_PRIVATE);
                outputStream.write("get to main activity".getBytes());
                outputStream.write("write the second time".getBytes());
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Write File", e.toString());
            }
        }
        else{
            try {
                outputStream.write("write for the third time".getBytes());
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Write File", e.toString());
            }
        }
    }

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.MSG_START_LISTENING:
                    setProgressBarIndeterminateVisibility(true);
                    break;
                case Constant.MSG_FINISH_LISTENING:
                    setProgressBarIndeterminateVisibility(false);
                    break;
                case Constant.MSG_GOT_DATA:
                        String str = String.valueOf(msg.obj);
                        updateData(str);
                        mFileWriter.writeData(str);
                    break;
                case Constant.MSG_ERROR:
                    showToast("error: "+String.valueOf(msg.obj));
                    break;
                case Constant.MSG_CONNECTED_TO_SERVER:
                    String name = String.valueOf(msg.obj);
                    showToast("Connected to Server "+name);
                    server_tv.setText("Connected to Server "+name);
                    break;
            }
        }
    }

    private void updateData(String str){
        if(str.length() < 10){
            position_tv.setText(str);
            velocity_tv.setText(str);
            bimuscle_tv.setText(str);
            trimuscletv.setText(str);
            resistance_tv.setText(str);
            return;
        }
        else{
            String[] value = str.split(",");
            if(value.length < 5 ||str.contains("q")) {
                Log.e("Main Activity", "omit data "+value.length);

                return;
            }
            float tmpposition=0;
            float tmpv=0;
            float tmpr =0;
            float bi_tmp=0;
            float tri_tmp=0;
            try {
                 tmpposition = Math.abs(Float.parseFloat(value[1]));
                 tmpv = Math.abs(Float.parseFloat(value[2]));
                 tmpr = Math.abs(Float.parseFloat(value[0]));
                 bi_tmp = Math.abs(Float.parseFloat(value[3]));
                 tri_tmp = Math.abs(Float.parseFloat(value[4]));
            }
            catch (Exception e){
                for(String i: value)
                    Log.e("invalid_Data", i);

            }
            position_tv.setText(value[1]);
            velocity_tv.setText(value[2]);
            bimuscle_tv.setText(value[3]);
            trimuscletv.setText(value[4]);
            bi_sample_counter = bi_tmp > THRESHOLD? bi_sample_counter+1: 0;
            tri_sample_counter = tri_tmp > THRESHOLD? tri_sample_counter+1: 0;
            tmpr = Float.parseFloat(forearmLength)*tmpr;
            String s = String.format("%.2f", tmpr);
            resistance_tv.setText(s);
            maxPosition = maxPosition < tmpposition? tmpposition: maxPosition;
            maxResistant = maxResistant < tmpr? tmpr: maxResistant;
            maxVelocity = maxVelocity < tmpv? tmpv: maxVelocity;
        }

    }

    private String composeInitialContent(){
        Intent i = getIntent();
        subjectID = i.getStringExtra(EXTRA_SUBJECT_ID);
        categorySelected = i.getStringExtra(EXTRA_CATEGORY_SELECTED);
        heightFeet = i.getStringExtra(EXTRA_HEIGHT_FEET);
        heightInch = i.getStringExtra(EXTRA_HEIGHT_INCH);
        forearmLength = i.getStringExtra(EXTRA_FOREARM_LENGTH);
        subjectWeight = i.getStringExtra(EXTRA_SUBJECT_WEIGHT);
        subjectDOB =  i.getStringExtra(EXTRA_SUBJECT_DOB);
        subjectTestDate =  i.getStringExtra(EXTRA_SUBJECT_TEST_DATE);
        subjectGender = i.getStringExtra(EXTRA_SUBJECT_GENDER);
        String sharing = "Patient ID: "+subjectID+"\n Gender: "+ subjectGender+"\n Date of Birth: "+subjectDOB+"\n Category: "
                + categorySelected + "\n Height: " + heightFeet + heightInch +" inch\nWeight: " + subjectWeight+"\n Forearm Length: "
                +forearmLength +"\nTested Date: "+subjectTestDate +"\n";
        return sharing;
    }
}

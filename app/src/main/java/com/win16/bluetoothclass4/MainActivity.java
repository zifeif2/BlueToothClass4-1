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
import android.widget.Toast;

//import com.win16.bluetoothclass4.connect.AcceptThread;
import com.win16.bluetoothclass4.connect.ConnectThread;
import com.win16.bluetoothclass4.connect.Constant;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rex on 2015/5/27.
 */
public class MainActivity extends AppCompatActivity {
    // extra Tags
    private static final String EXTRA_SUBJECT_ID = "com.win16.bluetoothclass4.subject_id";
    private static final String EXTRA_CATEGORY_SELECTED = "com.win16.bluetoothclass4.category_selected";
    private static final String EXTRA_HEIGHT_FEET = "com.win16.bluetoothclass4.height_feet";
    private static final String EXTRA_HEIGHT_INCH = "com.win16.bluetoothclass4.height_inch";
    private static final String EXTRA_FOREARM_LENGTH = "com.win16.bluetoothclass4.forearm_length";
    private static final String EXTRA_SUBJECT_WEIGHT = "com.win16.bluetoothclass4.subject_weight";
    private static final String EXTRA_SUBJECT_DOB = "com.win16.bluetoothclass4.subject_dob";
    private static final String EXTRA_SUBJECT_TEST_DATE = "com.win16.bluetoothclass4.subject_test_date";

    public static final int REQUEST_CODE = 0;
    private List<BluetoothDevice> mDeviceList = new ArrayList<>();
    private List<BluetoothDevice> mBondedDeviceList = new ArrayList<>();

    private BlueToothController mController = new BlueToothController();
    private ListView mListView;
    private DeviceAdapter mAdapter;
    private Toast mToast;
   // private AcceptThread mAcceptThread;
    private ConnectThread mConnectThread;

    private Button end_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initActionBar();
        setContentView(R.layout.activity_main);
        initUI();

        registerBluetoothReceiver();
        mController.turnOnBlueTooth(this, REQUEST_CODE);

    }

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

        end_Button = (Button)findViewById(R.id.end_button);
        end_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PatientActivity.class);

                intent.putExtra(EXTRA_SUBJECT_ID, getIntent().getStringExtra(EXTRA_SUBJECT_ID));
                intent.putExtra(EXTRA_CATEGORY_SELECTED, getIntent().getStringExtra(EXTRA_CATEGORY_SELECTED));
                intent.putExtra(EXTRA_HEIGHT_FEET, getIntent().getStringExtra(EXTRA_HEIGHT_FEET));
                intent.putExtra(EXTRA_HEIGHT_INCH, getIntent().getStringExtra(EXTRA_HEIGHT_INCH));
                intent.putExtra(EXTRA_FOREARM_LENGTH, getIntent().getStringExtra(EXTRA_FOREARM_LENGTH));
                intent.putExtra(EXTRA_SUBJECT_WEIGHT, getIntent().getStringExtra(EXTRA_SUBJECT_WEIGHT));
                intent.putExtra(EXTRA_SUBJECT_DOB, getIntent().getStringExtra(EXTRA_SUBJECT_DOB));
                intent.putExtra(EXTRA_SUBJECT_TEST_DATE, getIntent().getStringExtra(EXTRA_SUBJECT_TEST_DATE));

                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if( mAcceptThread != null) {
//            mAcceptThread.cancel();
//        }
        if( mConnectThread != null) {
            mConnectThread.cancel();
        }

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
            mController.enableVisibly(this);
        }
        else if( id == R.id.find_device) {
            //look for device
            mAdapter.refresh(mDeviceList);
            mController.findDevice();
            mListView.setOnItemClickListener(bindDeviceClick);
        }
        else if (id == R.id.bonded_device) {
            //look for the bounded device
            mBondedDeviceList = mController.getBondedDeviceList();
            mAdapter.refresh(mBondedDeviceList);
            mListView.setOnItemClickListener(bindedDeviceClick);
        }
//        }
//        else if( id == R.id.listening) {
//            if( mAcceptThread != null) {
//                mAcceptThread.cancel();
//            }
//            mAcceptThread = new AcceptThread(mController.getAdapter(), mUIHandler);
//            mAcceptThread.start();
//        }
//        else if( id == R.id.stop_listening) {
//            if( mAcceptThread != null) {
//                mAcceptThread.cancel();
//            }
//        }
        else if( id == R.id.disconnect) {
            if( mConnectThread != null) {
                mConnectThread.cancel();
            }
        }
        else if( id == R.id.say_hello) {
           say("*");
        }
//        else if( id == R.id.say_hi) {
//            say("Hi");
//        }

        return super.onOptionsItemSelected(item);
    }

    private void say(String word) {
//        if( mAcceptThread != null) {
//            try {
//                mAcceptThread.sendData(word.getBytes("utf-8"));
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//        }
        word = "r";
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
            BluetoothDevice device = mBondedDeviceList.get(i);
            if( mConnectThread != null) {
                mConnectThread.cancel();
            }
            mConnectThread = new ConnectThread(device, mController.getAdapter(), mUIHandler);
            mConnectThread.start();
//            if(mConnectThread == null){
//                mConnectThread = new ConnectThread(device, mController.getAdapter(), mUIHandler);
//                mConnectThread.start();
//            }
        }
    };

    private void initActionBar() {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
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
                    showToast("data: "+String.valueOf(msg.obj));
                    //Log.e("MainActivity Gotdata", String.valueOf(msg.obj));
                    break;
                case Constant.MSG_ERROR:
                    showToast("error: "+String.valueOf(msg.obj));
                    break;
                case Constant.MSG_CONNECTED_TO_SERVER:
                    showToast("Connected to Server");
                    break;
                case Constant.MSG_GOT_A_CLINET:
                    showToast("Got a Client");
                    break;
            }
        }
    }
}

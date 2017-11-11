package com.win16.bluetoothclass4;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.win16.bluetoothclass4.connect.Constant;

import static android.R.attr.name;
import static com.win16.bluetoothclass4.FileWriter.mFileWriter;
import static com.win16.bluetoothclass4.R.id.server_tv;

/**
 * Created by zifeifeng on 11/7/17.
 */

public class MyDataHandler extends Handler {
    Context mContext;
    public MyDataHandler(Context context){
        mContext = context;
    }
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case Constant.MSG_START_LISTENING:
                break;
            case Constant.MSG_FINISH_LISTENING:
                break;
            case Constant.MSG_GOT_DATA:
                String str = String.valueOf(msg.obj);
                mFileWriter.writeData(str);
                Log.e("receiving data", "receiving data");
                break;
            case Constant.MSG_ERROR:
                Toast.makeText(mContext,"error: "+String.valueOf(msg.obj), Toast.LENGTH_SHORT);
                break;
            case Constant.MSG_CONNECTED_TO_SERVER:
                String name = String.valueOf(msg.obj);
                Toast.makeText(mContext, "Connected to Server "+name, Toast.LENGTH_SHORT);
                break;
        }
    }
}

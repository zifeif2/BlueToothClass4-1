package com.win16.bluetoothclass4.connect;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import static com.win16.bluetoothclass4.connect.Constant.MSG_GOT_DATA;

public class ConnectedThread extends Thread {
    private final BluetoothSocket mmSocket;
    private final InputStream mmInStream;
    private final OutputStream mmOutStream;
    private final Handler mHandler;
    public ConnectedThread(BluetoothSocket socket, Handler handler) {
        mHandler = handler;
        mmSocket = socket;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;
        try {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) {
            Log.e("ConnectedThread stream", "tmpIn/Out might be null");
        }
        mmInStream = tmpIn;
        mmOutStream = tmpOut;

    }
    public void run() {
        byte[] buffer = new byte[1024];
        int begin = 0;
        int bytes = 0;
        String a ="";
        while (true) {
//                try {
//                    bytes += mmInStream.read(buffer, bytes, buffer.length - bytes);
//                    String str = new String(buffer);
//                    Log.e("Connected Thread", str);
//                    int j = 0;
//                    for(int i = 0; i < str.length(); i++){
//                        if(str.charAt(i) == '#'){
//                            String tmp = str.substring( j, i + 1);
//                            Log.e("Receive something haha", a+tmp);
//                            mHandler.obtainMessage(MSG_GOT_DATA, a+tmp).sendToTarget();
//                            a="";
//                            j = i+1;
//                        }
//                        else if(str.charAt(i) > 57 ||str.charAt(i) <48){//if there is not a number behind hash tag
//                            a = str.substring(j, i);
//                            break;
//                        }
//                    }
//                    buffer = new byte[1024];
//                    bytes = 0;
//                    begin = 0;
//
//                } catch (IOException e) {
//                    Log.e("Connected Thread", e.toString());
//                    break;
//                }
            try {
                bytes += mmInStream.read(buffer, bytes, buffer.length - bytes);
                for(int i = begin; i < bytes; i++) {
                    if(buffer[i] == "#".getBytes()[0]) {

                        byte[] buffer1 = Arrays.copyOfRange(buffer, begin, i);
                        String str = new String(buffer1);
                        Log.e("Connected Thread haha", str);
                        mHandler.obtainMessage(MSG_GOT_DATA, str).sendToTarget();
                        begin = i + 1;
                        if(i == bytes - 1) {
                            bytes = 0;
                            begin = 0;
                        }
                    }
                }
            } catch (IOException e) {
                break;
            }

        }
    }
    public void write(byte[] bytes) {
        try {
            mmOutStream.write(bytes);
        } catch (IOException e) {
            Log.e("ConnectedThread write", "output Stream can't write data");
        }
    }
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) { }
    }
}
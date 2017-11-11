package com.win16.bluetoothclass4;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.win16.bluetoothclass4.connect.Constant;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.UUID;

import static com.win16.bluetoothclass4.connect.Constant.MSG_GOT_DATA;

public class BluetoothConnection extends Thread {
    private final String TAG = "ConnectTag";
    private final BluetoothAdapter mBluetoothAdapter;
    Context mContext;

    private BluetoothDevice mDevice;
    private Handler mHandler;
    private final UUID hc05 = Constant.CONNECTTION_UUID;

    private ConnectThread mConnectThread;
    private ConnectedThread mConnectedThread;

    public BluetoothConnection(Context context, BluetoothAdapter bluetoothAdapter){
        mContext = context;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        /*This is the BluetoothConnection's start(), which will invoke the start() of the
        * respective thread, depending on the state of each thread*/
        start();
    }

    public synchronized void start(){
        //Cancel any threads attempting a connection
        if(mConnectThread != null){
            mConnectThread.cancel();
            mConnectThread = null;
        }

    }

    public void startClient(BluetoothDevice device, UUID uuid){
        mConnectThread = new ConnectThread(device, uuid);
        mConnectThread.start();
    }

    private void manageConnection(BluetoothSocket mmSocket, BluetoothDevice mmDevice){
        //Start thread to manage the connection and perform transactions
        mConnectedThread = new ConnectedThread(mmSocket);
        mConnectedThread.start();
    }

    public void write(byte[] out){
        //Synchronize a copy of the ConnectedThread
        Log.d(TAG, "write: Write Called");
        mConnectedThread.write(out);
    }

    /**Here are the individual threads*/
    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device, UUID uuid) {
            BluetoothSocket tmp = null;
            mmDevice = device;
            try {
                tmp = device.createRfcommSocketToServiceRecord(hc05);
            } catch (IOException e) {
                Log.e(TAG, "Socket's create() failed", e);
            }
            mmSocket = tmp;
        }

        public void run() {
            //Cancel discovery, since it slows down connection
            mBluetoothAdapter.cancelDiscovery();

            try{
                //Connect to remote device through socket. Blocking call
                mmSocket.connect();
            } catch (IOException e) {
                //Unable to connect, close socket and return
                try {
                    mmSocket.close();
                } catch (IOException e1) {
                    Log.e(TAG, "Could not close client socket", e1);
                }
            }

            // Connection was successful, pass the connected socket to a separate thread
            // to do work associated with connection
            manageConnection(mmSocket, mmDevice);
        }

        public void cancel(){
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "Couldn't close client socket", e);
            }
        }
    }

    private class ConnectedThread extends Thread{
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;
        private byte[] mmBuffer; // mmBuffer store for the stream

        // Initialize class's local socket to the socket connected to device, and
        // initializes IO streams
        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams; using temp objects because
            // member streams are final.
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Log.e(TAG, "Error occurred when creating input stream", e);
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[1024];
            int begin = 0;
            int bytes = 0;
            String a ="";
            int lastHashtag = 0;
            while (true) {
                try {
                    bytes += mmInStream.read(buffer, bytes, buffer.length - bytes);//bytes counts the valid elements in buffer
                    String tmp = new String(buffer);
                    for(int i = begin; i < bytes; i++) {
                        if(buffer[i] == "#".getBytes()[0]) {
                            lastHashtag = i;
                            byte[] buffer1 = Arrays.copyOfRange(buffer, begin, i);//the first byte read from buffer
                            String str = new String(buffer1);
                            Log.e("Connected Thread haha", str);
                            mHandler.obtainMessage(MSG_GOT_DATA, str).sendToTarget();
                            begin = i + 1;//update begin so that when the next hashtag is read it knows where to start
                            if(i == bytes - 1) {//when the buffer becomes full and the last byte is #
                                bytes = 0;
                                begin = 0;
                            }
                        }
                        else {
                            if(i == buffer.length - 1) {//when the buffer becomes full and the last byte is a number
                                begin = 0;
                                int j = 0;
                                for(; j <buffer.length-lastHashtag-1; j++){
                                    buffer[j] = buffer[lastHashtag+1+j];
                                }
                                bytes = j;
                                lastHashtag = 0;
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

                /*
                // Share the sent message with the UI activity.
                Message writtenMsg = mHandler.obtainMessage(
                        69, -1, -1, mmBuffer);
                writtenMsg.sendToTarget();
                */
            } catch (IOException e) {
                Log.e(TAG, "Error occurred when sending data", e);

                // Send a failure message back to the activity.
                Message writeErrorMsg =
                        mHandler.obtainMessage(68);
                Bundle bundle = new Bundle();
                bundle.putString("toast",
                        "Couldn't send data to the other device");
                writeErrorMsg.setData(bundle);
                mHandler.sendMessage(writeErrorMsg);
            }
        }
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "Could not close the connect socket", e);
            }
        }
    }
}

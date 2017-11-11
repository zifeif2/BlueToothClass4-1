package com.win16.bluetoothclass4.connect;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by Rex on 2015/5/30.
 */
public class ConnectThread extends Thread {
    private static final UUID MY_UUID = Constant.CONNECTTION_UUID;
    private BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    private BluetoothAdapter mBluetoothAdapter;
    private final Handler mHandler;
    private ConnectedThread mConnectedThread;
    private String TAG = "connect thread";
    private final String mSocketType = "RFCOMM";
    public ConnectThread(BluetoothDevice device, BluetoothAdapter adapter, Handler handler) {
        // Use a temporary object that is later assigned to mmSocket,
        // because mmSocket is final
        BluetoothSocket tmp = null;
        mmDevice = device;
        mBluetoothAdapter = adapter;
        mHandler = handler;
        // Get a BluetoothSocket to connect with the given BluetoothDevice
        try {
            // MY_UUID is the app's UUID string, also used by the server code
            tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e) { }
        mmSocket = tmp;

    }
//
//    public void run() {
//
//        mBluetoothAdapter.cancelDiscovery();
//        try {
//            // This is a blocking call and will only return on a successful connection or an exception
//            Log.i(TAG, "Connecting to socket...");
//            mmSocket.connect();
//        } catch (IOException e) {
//            mHandler.sendMessage(mHandler.obtainMessage(Constant.MSG_ERROR, e));
//            Log.e(TAG, e.toString());
//            //  e.printStackTrace();
//            try {
//                Log.i(TAG, "Trying fallback...");
//                mmSocket = (BluetoothSocket) mmDevice.getClass().getMethod("createRfcommSocket", new Class[]{int.class}).invoke(mmDevice, 1);
//                mmSocket.connect();
//                Log.i(TAG, "Connected");
//            } catch (Exception e2) {
//                Log.e(TAG, "Couldn't establish Bluetooth connection!");
//                try {
//                    mmSocket.close();
//                } catch (IOException e3) {
//                    Log.e(TAG, "unable to close() " + mSocketType + " socket during connection failure", e3);
//                }
//                Log.e("ConnectThread", "Connection Fail after fallback");
//                return;
//            }
//        }
//
//        // Do work to manage the connection (in a separate thread)
//        manageConnectedSocket(mmSocket);
//        // Cancel discovery because it will slow down the connection
////        mBluetoothAdapter.cancelDiscovery();
////
////        try {
////            // Connect the device through the socket. This will block
////            // until it succeeds or throws an exception
////            mmSocket.connect();
////        } catch (Exception connectException) {
////            mHandler.sendMessage(mHandler.obtainMessage(Constant.MSG_ERROR, connectException));
////            // Unable to connect; close the socket and get out
////            try {
////                mmSocket.close();
////            } catch (IOException closeException) { }
////            return;
////        }
////
////        // Do work to manage the connection (in a separate thread)
////        manageConnectedSocket(mmSocket);
//    }

    public void run(){
            mBluetoothAdapter.cancelDiscovery();
            try {
                mmSocket.connect();
            } catch (IOException connectException) {
                try {
                    Log.e("Connect Thread", connectException.toString());
                    mmSocket.close();
                } catch (IOException closeException) {

                }
                return;
            }
        manageConnectedSocket(mmSocket);

    }
    private void manageConnectedSocket(BluetoothSocket mmSocket) {
        mHandler.obtainMessage(Constant.MSG_CONNECTED_TO_SERVER,mmSocket.getRemoteDevice().getName()).sendToTarget();//apple
        mConnectedThread = new ConnectedThread(mmSocket, mHandler);
        mConnectedThread.start();
    }

    /** Will cancel an in-progress connection, and close the socket */
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) { }
    }

    public void sendData(byte[] data) {
        if( mConnectedThread!=null){
            mConnectedThread.write(data);
        }
        else{
            Log.e("ConnectThread send Data", "mConnectedThread == null");
        }
    }
}
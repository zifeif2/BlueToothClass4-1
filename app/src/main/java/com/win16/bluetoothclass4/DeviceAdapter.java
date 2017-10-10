package com.win16.bluetoothclass4;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Rex on 2015/5/27.
 */
public class DeviceAdapter extends BaseAdapter {

    private List<BluetoothDevice> mData;
    private Context mContext;

    public DeviceAdapter(List<BluetoothDevice> data, Context context) {
        mData = data;
        mContext = context.getApplicationContext();
    }


    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemView = view;

        if( itemView == null) {
            itemView = LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_2,viewGroup,false);
        }

        TextView line1 = (TextView) itemView.findViewById(android.R.id.text1);
        TextView line2 = (TextView) itemView.findViewById(android.R.id.text2);

        // get the device corresponding to the selected
        BluetoothDevice device = (BluetoothDevice) getItem(i);

        //Show the name of the device
        line1.setText(device.getName());
        //Show the address of device
        line2.setText(device.getAddress());

        return itemView;
    }

    public void refresh(List<BluetoothDevice> data) {
        mData = data;
        notifyDataSetChanged();
    }
}

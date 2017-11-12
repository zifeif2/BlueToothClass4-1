package com.win16.bluetoothclass4;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by zifeifeng on 10/20/17.
 */

public class MyFileWriter {
    private String filename;
    //FileOutputStream outputStream;
    File file;
    Context context;
    private static FileWriter mFileWriter;
    private static MyFileWriter mMyFileWriter;
    static String mUserId;
    public static final String filepath = "bluetoothclass4";
    private void _create(String userid, String initialContent){
        filename = userid+".txt";

        mUserId = userid;
        Shared.putBoolean(context, Shared.HAS_BEEN_SAHRED, false);
        Shared.putString(context, Shared.SUBJECT_ID, userid);

        File directory = new File(Environment.getExternalStorageDirectory()+File.separator+filepath);
        directory.mkdirs();
        file = new File(context.getExternalFilesDir(filepath), filename);
        try {
            mFileWriter = new FileWriter(file, true);
        }catch (IOException e ){
            Log.e("MyFileWriter", e.toString());
        }
//        try {
//            outputStream = new FileOutputStream(file);
//        }catch (IOException e){
//            Log.e("File Writer", "File not found");
//        }
        Log.e("File create", "File is created in "+file.getAbsolutePath());
        writeData(initialContent);
    }


    public static MyFileWriter get(String userid, String initialContent, Context mContext){
        if(mMyFileWriter == null){
            mMyFileWriter = new MyFileWriter(userid,mContext, initialContent);
        }
        else if(!mUserId.equals(userid) && userid!=null){
            mMyFileWriter.updateId(userid, initialContent);
        }
        return mMyFileWriter;
    }
    private MyFileWriter(String userid, Context context, String initialContent){
        this.context = context;
        _create(userid, initialContent);
    }

    public  void updateId(String userid, String initialContent){
        if(file!=null){
            try {
                mFileWriter.close();
            }catch (Exception e){

            }
        }
        file = null;
        filename = userid;
        _create(userid, initialContent);
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }



    public void writeData(String str){
        try {
            mFileWriter.write(str);
            Log.e("FileWriter", str);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Write File", e.toString());
        }finally {
            try {
                mFileWriter.flush();
            }catch (IOException e){
                Log.e("Filewriter flush", e.toString());
            }
        }
    }

    public void closeFileWriter() {
        try {
            mFileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getFilepath(){
        return Environment.getExternalStorageDirectory().getPath() + File.separator + "Android/data/com.win16.bluetoothclass3/files/bluetoothclass4" + File.separator + mUserId+".txt";
    }

}

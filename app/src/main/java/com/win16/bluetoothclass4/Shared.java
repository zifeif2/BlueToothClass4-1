package com.win16.bluetoothclass4;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by zifeifeng on 7/1/17.
 */

public class Shared {
    private static final String CONFIGS = "configs";
    public static final String SUBJECT_ID = "subject_id";
    public static final String HAS_BEEN_SAHRED= "hasbeenshared";
    public static final String STEP_TRACKER ="steptracker";
    public static final String ACTIVITY_TRACKER = "activity_tracker";
    private static SharedPreferences msp;
    public static final String LAST_USERID = "last_user_id";
    private static SharedPreferences mPreferences=null;
    private static final String SHARED_PREFERNECE_NAME="name";
    private static final String TAG="SharedPreference";
    public static final String TO_UNFINISH = "un_unfinish";
    public static final String RECORD_STEP_TRACKER= "step_tracker";



    private static SharedPreferences getSharePreferenceCheck(Context context){
        if(mPreferences == null) {
            mPreferences = context.getSharedPreferences(SHARED_PREFERNECE_NAME, 0);
            Log.e(TAG, "it creates again");
        }
        return mPreferences;
    }


    public static Long getLong(Context context, String key, Long idk){
        SharedPreferences P = getSharePreferenceCheck(context);
        return P.getLong(key, idk);
    }


    public static void putLong(Context context, String key, Long value){
        SharedPreferences P = getSharePreferenceCheck(context);
        P.edit().putLong(key, value).commit();
    }

    private static SharedPreferences getSharedPreferencesCheck(Context context){
        if(msp==null){
            msp = context.getSharedPreferences(CONFIGS, Context.MODE_PRIVATE);
        }
        return msp;
    }


    public static String getString(Context context, String key){
        SharedPreferences sp = getSharedPreferencesCheck(context);
        return sp.getString(key, null);
    }

    public static void putString(Context context, String key, String value){
        SharedPreferences sp = getSharedPreferencesCheck(context);
        sp.edit().putString(key, value).commit();
    }

    public static boolean getBoolean(Context context, String key, Boolean b){
        SharedPreferences sp = getSharedPreferencesCheck(context);
        return sp.getBoolean(key, b);
    }

    public static void putBoolean(Context context, String key, Boolean value){
        SharedPreferences sp = getSharedPreferencesCheck(context);
        sp.edit().putBoolean(key, value).commit();
    }
    //store int
    public static void putInt(Context context, String key, int value){
        SharedPreferences sp=getSharedPreferencesCheck(context);
        sp.edit().putInt(key, value).commit();
    }

    //get int
    public static int getInt(Context context, String key, int markup){
        SharedPreferences sp = getSharedPreferencesCheck(context);
        return sp.getInt(key, markup);
    }



}

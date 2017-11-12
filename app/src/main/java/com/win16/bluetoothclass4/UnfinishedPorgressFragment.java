package com.win16.bluetoothclass4;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.io.File;

import static com.win16.bluetoothclass4.MainActivity.EXTRA_CALLER;
import static com.win16.bluetoothclass4.MainActivity.EXTRA_SUBJECT_ID;
import static com.win16.bluetoothclass4.MainActivity.EXTRA_UNFINISH;
import static com.win16.bluetoothclass4.MyFileWriter.filepath;
import static com.win16.bluetoothclass4.MyFileWriter.get;
import static com.win16.bluetoothclass4.Shared.ACTIVITY_TRACKER;
import static com.win16.bluetoothclass4.Shared.LAST_USERID;
import static com.win16.bluetoothclass4.Shared.SUBJECT_ID;

/**
 * Created by zifeifeng on 11/11/17.
 */

public class UnfinishedPorgressFragment extends DialogFragment {
    private TextView mText;
    private String mResultText;

    public static final String ARG_RESULT = "result_text";
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_unifinished_process, null);
        mText = (TextView) v.findViewById(R.id.tv_unfinished_process_prompt);
        return new AlertDialog.Builder(getActivity()).setView(v)
                .setPositiveButton(getResources().getString(R.string.go_to_unfinished_step), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent2 = new Intent(getActivity(), MainActivity.class);
                        intent2.putExtra( EXTRA_SUBJECT_ID,Shared.getString(getActivity(), SUBJECT_ID) );
                        intent2.putExtra( EXTRA_UNFINISH, true);
                        startActivity(intent2);
                    }
                })
                .setNegativeButton(R.string.discard_last_file, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Shared.putBoolean(getActivity(), Shared.HAS_BEEN_SAHRED, true);
                        Shared.putInt(getActivity(), Shared.ACTIVITY_TRACKER, 0);
                        Shared.putInt(getActivity(), Shared.STEP_TRACKER, 0);
                        Shared.putBoolean(getActivity(), Shared.TO_UNFINISH, false);

                    }
        })
                .create();
    }
    private void deleteFile(){
        File file = new File(getActivity().getExternalFilesDir(filepath), Shared.getString(getActivity(), SUBJECT_ID)+".txt");
        file.delete();
    }

}

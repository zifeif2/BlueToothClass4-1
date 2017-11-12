package com.win16.bluetoothclass4;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.win16.bluetoothclass4.MainActivity.EXTRA_CALLER;
import static com.win16.bluetoothclass4.MainActivity.EXTRA_CATEGORY_SELECTED;
import static com.win16.bluetoothclass4.MainActivity.EXTRA_FOREARM_LENGTH;
import static com.win16.bluetoothclass4.MainActivity.EXTRA_HEIGHT_FEET;
import static com.win16.bluetoothclass4.MainActivity.EXTRA_HEIGHT_INCH;
import static com.win16.bluetoothclass4.MainActivity.EXTRA_SUBJECT_DOB;
import static com.win16.bluetoothclass4.MainActivity.EXTRA_SUBJECT_ID;
import static com.win16.bluetoothclass4.MainActivity.EXTRA_SUBJECT_TEST_DATE;
import static com.win16.bluetoothclass4.MainActivity.EXTRA_SUBJECT_WEIGHT;
import static com.win16.bluetoothclass4.MainActivity.EXTRA_SUBJECT_GENDER;

//import com.win16.bluetoothclass3.R;

public class PatientDataActivity extends AppCompatActivity {

    String subjectID;
    String categorySelected;
    String heightFeet;
    String heightInch;
    String forearmLength;
    String subjectWeight;
    String subjectDOB;
    String subjectTestDate;
    String subjectGender;


    EditText subjectID_editText;
    Spinner category_Spinner;
    Spinner subject_gender_Spinner;
    NumberPicker subjectHeightFeet_Spinner;
    NumberPicker subjectHeightInch_Spinner;
    EditText subjectForearmLength_editText;
    EditText subjectWeight_editText;
    EditText subjectDOB_editText;
    EditText subjectTestDate_editText;
    Button confirm_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_data);
        if(!Shared.getBoolean(this, Shared.HAS_BEEN_SAHRED, true)){
            ShareLastFileFragment dialog = new ShareLastFileFragment();
            dialog.show(getSupportFragmentManager(), "share_last_file");
        }
        if(Shared.getBoolean(this, Shared.TO_UNFINISH, false)){
            UnfinishedPorgressFragment dialog = new UnfinishedPorgressFragment();
            dialog.show(getSupportFragmentManager(), "back_to_unfinished_process");
        }

        //Initialize items
        subjectID_editText = (EditText)findViewById(R.id.patient_ID);
        category_Spinner = (Spinner) findViewById(R.id.patient_category_spinner);
        category_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categorySelected = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // no need for implementation
            }
        });
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.patient_category, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_Spinner.setAdapter(spinnerAdapter);
        spinnerAdapter.notifyDataSetChanged();

        subject_gender_Spinner = (Spinner) findViewById(R.id.patient_gender_spinner);
        subject_gender_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subjectGender = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // no need for implementation
            }
        });
        ArrayAdapter<CharSequence> genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.patient_gender, android.R.layout.simple_spinner_item);
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subject_gender_Spinner.setAdapter(genderSpinnerAdapter);
        genderSpinnerAdapter.notifyDataSetChanged();

        subjectHeightFeet_Spinner = (NumberPicker)findViewById(R.id.patientHeightPicker_ft);
        subjectHeightFeet_Spinner.setMinValue(0);
        subjectHeightFeet_Spinner.setMaxValue(getResources().getStringArray(R.array.patient_ft).length-1);
        subjectHeightFeet_Spinner.setDisplayedValues(getResources().getStringArray(R.array.patient_ft));
        subjectHeightFeet_Spinner.setWrapSelectorWheel(false);
        subjectHeightFeet_Spinner.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                heightFeet = getResources().getStringArray(R.array.patient_ft)[newVal];
            }
        });

        subjectHeightInch_Spinner = (NumberPicker)findViewById(R.id.patientHeightPicker_in);
        subjectHeightInch_Spinner.setMinValue(0);
        subjectHeightInch_Spinner.setMaxValue(getResources().getStringArray(R.array.patient_in).length-1);
        subjectHeightInch_Spinner.setDisplayedValues(getResources().getStringArray(R.array.patient_in));
        subjectHeightInch_Spinner.setWrapSelectorWheel(false);
        subjectHeightInch_Spinner.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {heightInch = getResources().getStringArray(R.array.patient_in)[newVal];
            }
        });

        subjectForearmLength_editText = (EditText)findViewById(R.id.patient_forearmlength);
        subjectWeight_editText = (EditText)findViewById(R.id.patient_weight);
        subjectDOB_editText = (EditText)findViewById(R.id.patient_dob);
        subjectTestDate_editText = (EditText)findViewById(R.id.test_date);
        confirm_button = (Button)findViewById(R.id.confirm_button);
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientDataActivity.this, MainActivity.class);
                subjectID = subjectID_editText.getText().toString();
                forearmLength = subjectForearmLength_editText.getText().toString();
                subjectWeight = subjectWeight_editText.getText().toString();
                subjectDOB = subjectDOB_editText.getText().toString();
                subjectTestDate = DateFormat.getDateTimeInstance().format(new Date());

                String tmp = checkEmptyEntries();
                if(!tmp.isEmpty()){
                    showAlert(tmp);
                    return ;
                }
                intent.putExtra(EXTRA_SUBJECT_ID, subjectID);
                intent.putExtra(EXTRA_SUBJECT_GENDER, subjectGender);
                intent.putExtra(EXTRA_CATEGORY_SELECTED, categorySelected);
                intent.putExtra(EXTRA_HEIGHT_FEET, heightFeet);
                intent.putExtra(EXTRA_HEIGHT_INCH, heightInch);
                intent.putExtra(EXTRA_FOREARM_LENGTH, forearmLength);
                intent.putExtra(EXTRA_SUBJECT_WEIGHT, subjectWeight);
                intent.putExtra(EXTRA_SUBJECT_DOB, subjectDOB);
                intent.putExtra(EXTRA_SUBJECT_TEST_DATE, subjectTestDate);
                intent.putExtra(EXTRA_CALLER, "PatientDataActivity");
                startActivity(intent);
            }
        });
    }
    private String checkEmptyEntries(){

        String prefix = "Please enter ";
        if(subjectID.length()==0){
            return prefix+"subject ID";
        }
        else if(forearmLength.length() ==0){
            return prefix+"forearm length";
        }
        else if(subjectWeight.isEmpty()){
            return prefix + "subject weight";
        }
        else if(subjectDOB.isEmpty()){
            return prefix +" subject date of birth";
        }

        else
            return "";
    }

    private void showAlert(String emptyField){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Alert")
                .setMessage(emptyField)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


}

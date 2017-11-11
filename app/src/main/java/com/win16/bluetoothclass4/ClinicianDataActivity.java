package com.win16.bluetoothclass4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;

import static com.win16.bluetoothclass4.MainActivity.EXTRA_CATEGORY_SELECTED;
import static com.win16.bluetoothclass4.MainActivity.EXTRA_FOREARM_LENGTH;
import static com.win16.bluetoothclass4.MainActivity.EXTRA_HEIGHT_FEET;
import static com.win16.bluetoothclass4.MainActivity.EXTRA_HEIGHT_INCH;
import static com.win16.bluetoothclass4.MainActivity.EXTRA_SUBJECT_DOB;
import static com.win16.bluetoothclass4.MainActivity.EXTRA_SUBJECT_ID;
import static com.win16.bluetoothclass4.MainActivity.EXTRA_SUBJECT_TEST_DATE;
import static com.win16.bluetoothclass4.MainActivity.EXTRA_SUBJECT_WEIGHT;
import static com.win16.bluetoothclass4.MainActivity.EXTRA_SUBJECT_GENDER;



public class PatientDataActivity extends AppCompatActivity {

    String clinicianName;
    /*
    String subjectID;
    String categorySelected;
    String heightFeet;
    String heightInch;
    String forearmLength;
    String subjectWeight;
    String subjectDOB;
    String subjectTestDate;
    String subjectGender;
    */
//    //intent extra tags
//    private static final String EXTRA_SUBJECT_ID = "com.win16.bluetoothclass4.subject_id";
//    private static final String EXTRA_CATEGORY_SELECTED = "com.win16.bluetoothclass4.category_selected";
//    private static final String EXTRA_HEIGHT_FEET = "com.win16.bluetoothclass4.height_feet";
//    private static final String EXTRA_HEIGHT_INCH = "com.win16.bluetoothclass4.height_inch";
//    private static final String EXTRA_FOREARM_LENGTH = "com.win16.bluetoothclass4.forearm_length";
//    private static final String EXTRA_SUBJECT_WEIGHT = "com.win16.bluetoothclass4.subject_weight";
//    private static final String EXTRA_SUBJECT_DOB = "com.win16.bluetoothclass4.subject_dob";
//    private static final String EXTRA_SUBJECT_TEST_DATE = "com.win16.bluetoothclass4.subject_test_date";

    Spinner spinner_clinician;
    Spinner spinner_mas;
    Spinner spinner_mts;
    Spinner spinner_updrs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_data);

        /**Clinician Spinner**/
        spinner_clinician = (Spinner)findViewById(R.id.ID_spinner_clinician);
        spinner_clinician.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //TODO: update MAS counter in app, launch instructions
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        ArrayAdapter<CharSequence> adapter_clinician = ArrayAdapter.createFromResource
                (this, R.array.STRING_ARR_clinician_names, R.layout.support_simple_spinner_dropdown_item);
        adapter_clinician.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_clinician.setAdapter(adapter_clinician);
        /********************/

        /**MAS Spinner**/
        spinner_mas = (Spinner)findViewById(R.id.ID_spinner_mas);
        spinner_mas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               //TODO: update MAS counter in app, launch instructions
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        ArrayAdapter<CharSequence> adapter_mas = ArrayAdapter.createFromResource
                (this, R.array.STRING_ARR_mas_levels, R.layout.support_simple_spinner_dropdown_item);
        adapter_mas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_mas.setAdapter(adapter_mas);
        /********************/

        /**MTS Spinner**/
        spinner_mts = (Spinner)findViewById(R.id.ID_spinner_mts);
        spinner_mts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //TODO: update MAS counter in app, launch instructions
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        ArrayAdapter<CharSequence> adapter_mts = ArrayAdapter.createFromResource
                (this, R.array.STRING_ARR_mts_levels, R.layout.support_simple_spinner_dropdown_item);
        adapter_mts.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_mts.setAdapter(adapter_mts);
        /********************/

        /**UPPRS Spinner**/
        spinner_updrs = (Spinner)findViewById(R.id.ID_spinner_updrs);
        spinner_updrs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //TODO: update MAS counter in app, launch instructions
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        ArrayAdapter<CharSequence> adapter_udprs = ArrayAdapter.createFromResource
                (this, R.array.STRING_ARR_udprs_levels, R.layout.support_simple_spinner_dropdown_item);
        adapter_udprs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_updrs.setAdapter(adapter_udprs);
        /********************/



    }


}

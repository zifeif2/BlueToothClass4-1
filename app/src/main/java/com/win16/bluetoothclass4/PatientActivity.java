package com.win16.bluetoothclass4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

//import com.win16.bluetoothclass3.R;

public class PatientActivity extends AppCompatActivity {

    //intent extra tags
    private static final String EXTRA_SUBJECT_ID = "com.win16.bluetoothclass4.subject_id";
    private static final String EXTRA_CATEGORY_SELECTED = "com.win16.bluetoothclass4.category_selected";
    private static final String EXTRA_HEIGHT_FEET = "com.win16.bluetoothclass4.height_feet";
    private static final String EXTRA_HEIGHT_INCH = "com.win16.bluetoothclass4.height_inch";
    private static final String EXTRA_FOREARM_LENGTH = "com.win16.bluetoothclass4.forearm_length";
    private static final String EXTRA_SUBJECT_WEIGHT = "com.win16.bluetoothclass4.subject_weight";
    private static final String EXTRA_SUBJECT_DOB = "com.win16.bluetoothclass4.subject_dob";
    private static final String EXTRA_SUBJECT_TEST_DATE = "com.win16.bluetoothclass4.subject_test_date";

    TextView patientSummary_textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_summary);

        patientSummary_textView = (TextView)findViewById(R.id.patient_summary);

        patientSummary_textView.append("Subject ID: " + getIntent().getStringExtra(EXTRA_SUBJECT_ID));
        patientSummary_textView.append("\n");
        patientSummary_textView.append("Category Selected: " + getIntent().getStringExtra(EXTRA_CATEGORY_SELECTED));
        patientSummary_textView.append("\n");
        patientSummary_textView.append("Subject Height: " + getIntent().getStringExtra(EXTRA_HEIGHT_FEET)
                + " " + getIntent().getStringExtra(EXTRA_HEIGHT_INCH));
        patientSummary_textView.append("\n");
        patientSummary_textView.append("Subject Forearm Length: " + getIntent().getStringExtra(EXTRA_FOREARM_LENGTH)
                + "in");
        patientSummary_textView.append("\n");
        patientSummary_textView.append("Subject Weight: " + getIntent().getStringExtra(EXTRA_SUBJECT_WEIGHT)
                + "lbs");
        patientSummary_textView.append("\n");
        patientSummary_textView.append("Subject Date of Birth: " + getIntent().getStringExtra(EXTRA_SUBJECT_DOB));
        patientSummary_textView.append("\n");
        patientSummary_textView.append("Subject Test Date: " + getIntent().getStringExtra(EXTRA_SUBJECT_TEST_DATE));
        patientSummary_textView.append("\n");
    }

}

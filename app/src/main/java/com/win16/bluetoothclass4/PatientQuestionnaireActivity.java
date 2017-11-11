package com.win16.bluetoothclass4;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class PatientQuestionnaireActivity extends AppCompatActivity {



    /**Top Left elements **/
    TextView textView_gender_title;
    Spinner spinner_gender;
    TextView textView_weight_title;
    EditText editText_weight;
    TextView textView_weight_label;
    TextView textView_tested_arm_title;
    RadioGroup radioGroup_arm;
    TextView textView_wrist_angle_title;
    EditText editText_wrist_angle;
    TextView textView_wrist_angle_label;

    /**Top Right elements **/
    TextView textView_date_of_birth_title;
    DatePicker datepicker_date_of_birth;
    TextView textView_height_title;
    EditText editText_height_ft;
    TextView textView_height_ft_label;
    EditText editText_height_in;
    TextView textView_height_in_label;
    TextView textView_arm_angle_title;
    EditText editText_arm_angle;
    TextView textView_arm_angle_label;

    /**Bottom elements**/
    TextView textView_presense_of_tremor_title;
    RadioGroup radioGroup_presence_of_tremor;
    EditText editText_presence_of_tremor;
    TextView textView_high_stress_title;
    RadioGroup radioGroup_high_stress;
    EditText editText_high_stress;
    TextView textView_fatigued_title;
    RadioGroup radioGroup_fatigued;
    EditText editText_fatigued;
    TextView textView_have_infection_title;
    EditText editText_have_infection;
    RadioGroup radioGroup_have_infection;
    TextView textView_missed_medication_title;
    EditText editText_missed_medication;
    RadioGroup radioGroup_missed_medication;
    TextView textView_taking_new_medication_title;
    EditText editText_taking_new_medication;
    RadioGroup radioGroup_taking_new_medication;
    TextView textView_injured_arm_title;
    RadioGroup radioGroup_injured_arm;
    EditText editText_injured_arm;
    TextView textView_feeling_pain_title;
    EditText editText_feeling_pain;
    RadioGroup radioGroup_feeling_pain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_questionnaire);

        initUITopLeft();
        initUITopRight();
        initUIBottom();

    }

    public void initUITopLeft() {
        textView_gender_title = (TextView)findViewById(R.id.ID_gender_title);
        spinner_gender = (Spinner)findViewById(R.id.ID_spinner_gender);
        spinner_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //TODO: store selected gender to local variable
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        ArrayAdapter<CharSequence> adapter_gender = ArrayAdapter.createFromResource
                (this, R.array.STRING_ARR_gender, R.layout.support_simple_spinner_dropdown_item);
        adapter_gender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_gender.setAdapter(adapter_gender);

        textView_weight_title = (TextView)findViewById(R.id.ID_weight_title);
        editText_weight = (EditText)findViewById(R.id.ID_edittext_weight);
        textView_weight_label = (TextView)findViewById(R.id.ID_label_weight);

        textView_tested_arm_title = (TextView)findViewById(R.id.ID_tested_arm_title);
        radioGroup_arm = (RadioGroup)findViewById(R.id.ID_radiogroup_arm);
        radioGroup_arm.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(checkedId == R.id.ID_radiobutton_leftarm) {
                    //TODO: save the selected arm to local variable
                } else if (checkedId == R.id.ID_radiobutton_rightarm) {
                    //TODO: save the selected arm to local variable
                }
            }
        });

        textView_wrist_angle_title = (TextView)findViewById(R.id.ID_wrist_angle_title);
        editText_wrist_angle = (EditText)findViewById(R.id.ID_edittext_wristangle);
        textView_wrist_angle_label = (TextView)findViewById(R.id.ID_label_wristangle);
    }

    public void initUITopRight() {
        textView_date_of_birth_title = (TextView)findViewById(R.id.ID_date_of_birth_title);
        Calendar calendar = Calendar.getInstance();
        datepicker_date_of_birth = (DatePicker)findViewById(R.id.ID_datepicker_date_of_birth);
        datepicker_date_of_birth.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(calendar.DAY_OF_MONTH), null);

        textView_height_title = (TextView)findViewById(R.id.ID_height_title);
        editText_height_ft = (EditText)findViewById(R.id.ID_editText_heightft);
        textView_height_ft_label = (TextView)findViewById(R.id.ID_label_heightft);
        editText_height_in = (EditText)findViewById(R.id.ID_edittext_heightin);
        textView_height_in_label = (TextView)findViewById(R.id.ID_label_heightin);

        textView_arm_angle_title = (TextView)findViewById(R.id.ID_arm_angle_title);
        editText_arm_angle = (EditText)findViewById(R.id.ID_edittext_armangle);
        textView_arm_angle_label = (TextView)findViewById(R.id.ID_label_armangle);
    }

    public void initUIBottom() {
        editText_presence_of_tremor = (EditText)findViewById(R.id.ID_edittext_presence_of_tremor);
        textView_presense_of_tremor_title = (TextView)findViewById(R.id.ID_presence_of_tremor_title);
        radioGroup_presence_of_tremor = (RadioGroup)findViewById(R.id.ID_radiogroup_presence_of_tremor);
        radioGroup_presence_of_tremor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(checkedId == R.id.ID_radiobutton_presence_of_tremor_yes) {
                    editText_presence_of_tremor.setVisibility(View.VISIBLE);
                    //TODO: save the selected arm to local variable
                } else if (checkedId == R.id.ID_radiobutton_presence_of_tremor_no) {
                    editText_presence_of_tremor.setText("");
                    editText_presence_of_tremor.setVisibility(View.GONE);
                    //TODO: save the selected arm to local variable
                }
            }
        });

        editText_high_stress = (EditText)findViewById(R.id.ID_edittext_high_stress);
        textView_high_stress_title = (TextView)findViewById(R.id.ID_high_stress_title);
        radioGroup_high_stress = (RadioGroup)findViewById(R.id.ID_radiogroup_high_stress);
        radioGroup_high_stress.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(checkedId == R.id.ID_radiobutton_high_stress_yes) {
                    editText_high_stress.setVisibility(View.VISIBLE);
                    //TODO: save the selected arm to local variable
                } else if (checkedId == R.id.ID_radiobutton_high_stress_no) {
                    editText_high_stress.setText("");
                    editText_high_stress.setVisibility(View.GONE);
                    //TODO: save the selected arm to local variable
                } else if (checkedId == R.id.ID_radiobutton_high_stress_idk) {
                    editText_high_stress.setText("");
                    editText_high_stress.setVisibility(View.GONE);
                }
            }
        });

        editText_fatigued= (EditText)findViewById(R.id.ID_edittext_fatigued);
        textView_fatigued_title = (TextView)findViewById(R.id.ID_fatigued_title);
        radioGroup_fatigued = (RadioGroup)findViewById(R.id.ID_radiogroup_fatigued);
        radioGroup_fatigued.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(checkedId == R.id.ID_radiobutton_fatigued_yes) {
                    editText_fatigued.setVisibility(View.VISIBLE);
                    //TODO: save the selected arm to local variable
                } else if (checkedId == R.id.ID_radiobutton_fatigued_no) {
                    editText_fatigued.setText("");
                    editText_fatigued.setVisibility(View.GONE);
                    //TODO: save the selected arm to local variable
                } else if (checkedId == R.id.ID_radiobutton_fatigued_idk) {
                    editText_fatigued.setText("");
                    editText_fatigued.setVisibility(View.GONE);
                }
            }
        });

        editText_have_infection= (EditText)findViewById(R.id.ID_edittext_have_infections);
        textView_have_infection_title = (TextView)findViewById(R.id.ID_have_infections_title);
        radioGroup_have_infection = (RadioGroup)findViewById(R.id.ID_radiogroup_have_infections);
        radioGroup_have_infection.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(checkedId == R.id.ID_radiobutton_have_infections_yes) {
                    editText_have_infection.setVisibility(View.VISIBLE);
                    //TODO: save the selected arm to local variable
                } else if (checkedId == R.id.ID_radiobutton_have_infections_no) {
                    editText_have_infection.setText("");
                    editText_have_infection.setVisibility(View.GONE);
                    //TODO: save the selected arm to local variable
                } else if (checkedId == R.id.ID_radiobutton_have_infections_idk) {
                    editText_have_infection.setText("");
                    editText_have_infection.setVisibility(View.GONE);
                }
            }
        });

        editText_missed_medication= (EditText)findViewById(R.id.ID_edittext_missed_medications);
        textView_missed_medication_title = (TextView)findViewById(R.id.ID_missed_medications_title);
        radioGroup_missed_medication= (RadioGroup)findViewById(R.id.ID_radiogroup_missed_medications);
        radioGroup_missed_medication.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(checkedId == R.id.ID_radiobutton_missed_medications_yes) {
                    editText_missed_medication.setVisibility(View.VISIBLE);
                    //TODO: save the selected arm to local variable
                } else if (checkedId == R.id.ID_radiobutton_missed_medications_no) {
                    editText_missed_medication.setText("");
                    editText_missed_medication.setVisibility(View.GONE);
                    //TODO: save the selected arm to local variable
                } else if (checkedId == R.id.ID_radiobutton_missed_medications_idk) {
                    editText_missed_medication.setText("");
                    editText_missed_medication.setVisibility(View.GONE);
                }
            }
        });

        editText_taking_new_medication = (EditText)findViewById(R.id.ID_edittext_taking_new_medications);
        textView_taking_new_medication_title = (TextView)findViewById(R.id.ID_taking_new_medications_title);
        radioGroup_taking_new_medication = (RadioGroup)findViewById(R.id.ID_radiogroup_taking_new_medications);
        radioGroup_taking_new_medication.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(checkedId == R.id.ID_radiobutton_taking_new_medications_yes) {
                    editText_taking_new_medication.setVisibility(View.VISIBLE);
                    //TODO: save the selected arm to local variable
                } else if (checkedId == R.id.ID_radiobutton_taking_new_medications_no) {
                    editText_taking_new_medication.setText("");
                    editText_taking_new_medication.setVisibility(View.GONE);
                    //TODO: save the selected arm to local variable
                } else if (checkedId == R.id.ID_radiobutton_taking_new_medications_idk) {
                    editText_taking_new_medication.setText("");
                    editText_taking_new_medication.setVisibility(View.GONE);
                }
            }
        });

        editText_injured_arm= (EditText)findViewById(R.id.ID_edittext_injured_arm);
        textView_injured_arm_title = (TextView)findViewById(R.id.ID_injured_arm_title);
        radioGroup_injured_arm = (RadioGroup)findViewById(R.id.ID_radiogroup_injured_arm);
        radioGroup_injured_arm.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(checkedId == R.id.ID_radiobutton_injured_arm_yes) {
                    editText_injured_arm.setVisibility(View.VISIBLE);
                    //TODO: save the selected arm to local variable
                } else if (checkedId == R.id.ID_radiobutton_injured_arm_no) {
                    editText_injured_arm.setText("");
                    editText_injured_arm.setVisibility(View.GONE);
                    //TODO: save the selected arm to local variable
                } else if (checkedId == R.id.ID_radiobutton_injured_arm_idk) {
                    editText_injured_arm.setText("");
                    editText_injured_arm.setVisibility(View.GONE);
                }
            }
        });

        editText_feeling_pain= (EditText)findViewById(R.id.ID_edittext_feeling_pain);
        textView_feeling_pain_title = (TextView)findViewById(R.id.ID_feeling_pain_title);
        radioGroup_feeling_pain = (RadioGroup)findViewById(R.id.ID_radiogroup_feeling_pain);
        radioGroup_feeling_pain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(checkedId == R.id.ID_radiobutton_feeling_pain_yes) {
                    editText_feeling_pain.setVisibility(View.VISIBLE);
                    //TODO: save the selected arm to local variable
                } else if (checkedId == R.id.ID_radiobutton_feeling_pain_no) {
                    editText_feeling_pain.setText("");
                    editText_feeling_pain.setVisibility(View.GONE);
                    //TODO: save the selected arm to local variable
                } else if (checkedId == R.id.ID_radiobutton_feeling_pain_idk) {
                    editText_feeling_pain.setText("");
                    editText_feeling_pain.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_patient_activity, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ID_menuitem_clinician_next:
                Intent intent = new Intent(PatientQuestionnaireActivity.this, InstructionActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

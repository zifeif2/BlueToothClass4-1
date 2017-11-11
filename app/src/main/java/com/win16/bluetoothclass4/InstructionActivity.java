package com.win16.bluetoothclass4;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class InstructionActivity extends AppCompatActivity {

    ViewPager viewPager;

    int [] instruction_index = new int[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);

        viewPager = (ViewPager) findViewById(R.id.ID_viewpager_instruction);

        FragmentManager fragmentManager = getSupportFragmentManager();

        viewPager.setAdapter(new FragmentPagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                return InstructionFragment.newInstance(position);
            }

            @Override
            public int getCount() {
                return instruction_index.length;
            }
        });
    }
}

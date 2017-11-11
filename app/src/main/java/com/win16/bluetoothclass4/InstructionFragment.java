package com.win16.bluetoothclass4;

import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class InstructionFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "whichInstruction";

    ImageView topPic;
    ImageView botPic;
    TextView instruction_one;
    TextView instruction_two;

    int instruction_key;

    int[] pic_resource_ids = {R.drawable.number_zero, R.drawable.number_one, R.drawable.number_two,
                            R.drawable.number_three, R.drawable.number_four, R.drawable.number_five};
    String[] instruction_array_text = {"Prepare the biceps and tripceps of the subject's testing arm and the clavicle near the sternal notch",
                                        "a. Briskly wipe the skin using an alcohol wipe to remove surface oils and other contaminants. Too much alcohol is desired as this causes the skin to dry",
                                        "b. If dry skin cells are causing difficulties, these can be easily dislodged by dabbing the surface with medical grade tape. Ensure than no adhesive residue remains on the skin by wiping the areas with alcohol wipes",
                                        "In cases when skin surface is persistently dry, a very samll amount of electrode gel can be placed on the EMG sensor contacts. \n\n Ensure that the prepared skin does not come into contact with any foreign object",
                                        "Attach the Biceps and Triceps EMG sensors with disposable EMG electrodes (with the wires disconnected from the main module) on the middle of the subject's bicep snf the middle of the longhead of the tricep (parallel to the humerus)",
                                        "Attach the Reference Electrode on the clavicle near the sternal notch."};

    public static InstructionFragment newInstance(int whichInstruction) {
        InstructionFragment fragment = new InstructionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, whichInstruction);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            instruction_key = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_instruction, container, false);
        topPic = (ImageView)v.findViewById(R.id.ID_image_instruction_top);
        botPic = (ImageView)v.findViewById(R.id.ID_image_instruction_bot);
        instruction_one = (TextView)v.findViewById(R.id.ID_textview_instruction_top);
        instruction_two = (TextView)v.findViewById(R.id.ID_textview_instruction_bot);

        topPic.setImageResource(pic_resource_ids[instruction_key]);
        botPic.setImageResource(pic_resource_ids[instruction_key+1]);
        instruction_one.setText(instruction_array_text[instruction_key]);
        instruction_two.setText(instruction_array_text[instruction_key+1]);

        return v;

    }
}

package com.example.inclass_sankara_narayanan_002787959.InClass03;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inclass_sankara_narayanan_002787959.InClass02.InClass02;
import com.example.inclass_sankara_narayanan_002787959.InClass02.display_activity;
import com.example.inclass_sankara_narayanan_002787959.InClass02.userDetails;
import com.example.inclass_sankara_narayanan_002787959.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public final static String TAG = "editProfile";

    private EditText nameEditBox;

    private EditText emailEditBox;

    private ImageButton avatar;

    private Button submit;

    private SeekBar status;

    private String operatingSys = "Android";

    private int imgResIdval = 0;

    private int statusint = 3;

    final static String statusText[] = {"Angry", "Sad", "Happy", "Awesome"};

    public EditProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment editProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditProfileFragment newInstance(String param1, String param2) {
        EditProfileFragment fragment = new EditProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        nameEditBox = view.findViewById(R.id.nameId);
        emailEditBox = view.findViewById(R.id.emailId);
        avatar = view.findViewById(R.id.imageButtonId);
        submit = view.findViewById(R.id.button);
        status = view.findViewById(R.id.seekBar2);
        status.setProgress(3);
        status.setMax(4);
        status.setMin(1);

        ImageView img = view.findViewById(R.id.imageViewfragId);
        TextView statusView = view.findViewById(R.id.moodTextFragId);
        statusView.setText(statusText[2]);

        RadioButton defaultOption = view.findViewById(R.id.radioButton);
        defaultOption.setChecked(true);

        RadioGroup radioGroup = view.findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton optionSelected = view.findViewById(i);
                operatingSys = String.valueOf(optionSelected.getText());
            }
        });

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .hide((EditProfileFragment) getActivity()
                                .getSupportFragmentManager()
                                .findFragmentByTag(EditProfileFragment.TAG))
                        .add(R.id.constraintId, new SelectAvatarFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (String.valueOf(emailEditBox.getText()).equals("") || !android.util.Patterns.EMAIL_ADDRESS.matcher(String.valueOf(emailEditBox.getText())).matches()) {
                    emailEditBox.invalidate();
                    Toast.makeText(getContext(), "Invalid email address!!", Toast.LENGTH_SHORT).show();
                } else if (String.valueOf(nameEditBox.getText()).equals("")) {
                    Toast.makeText(getContext(), "Invalid Name!!", Toast.LENGTH_SHORT).show();
                } else if (imgResIdval == 0) {
                    Toast.makeText(getContext(), "No avatar Selected", Toast.LENGTH_SHORT).show();
                } else {
                    String emailVal = String.valueOf(emailEditBox.getText());
                    String nameVal = String.valueOf(nameEditBox.getText());
                    userDetails newUser = new userDetails(emailVal, nameVal, operatingSys, imgResIdval, statusint);
                    sendData.fromEditprofile(newUser);
                }

            }
        });


        status.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                statusint = i;
                statusView.setText(statusText[i - 1]);
                img.setImageResource(imageToset(i));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // do nothing
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // do nothing
            }
        });

        return view;
    }

    public void updateAvatar(int imgRes) {
        imgResIdval = imgRes;
        this.avatar.setImageResource(imgRes);
    }


    static int imageToset(int idImg) {
        if (idImg == 1) {
            return R.drawable.angry;
        } else if (idImg == 2) {
            return R.drawable.sad;
        } else if (idImg == 3) {
            return R.drawable.happy;
        } else {
            return R.drawable.awesome;
        }

    }

    public interface editProfileInterface {
        void fromEditprofile(userDetails user);
    }

    editProfileInterface sendData;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof InClass03) {
            sendData = (InClass03) context;
        }

    }
}
package com.example.inclass_sankara_narayanan_002787959.InClass03;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.inclass_sankara_narayanan_002787959.InClass02.InClass02;
import com.example.inclass_sankara_narayanan_002787959.InClass02.userDetails;
import com.example.inclass_sankara_narayanan_002787959.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DisplayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DisplayFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String title = "Display Acivity";

    private String moodText = "I am %s!";

    private String opSysText = "I use %s!";

    private String tittle = "Edit Profile Activity";

    private ImageView bioDp ;
    private ImageView statusIcon ;
    private TextView name ;
    private TextView email ;
    private TextView operatingSys;
    private TextView mood ;

    public final static String TAG = "Display Profile";

    public DisplayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment dislayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DisplayFragment newInstance(String param1, String param2) {
        DisplayFragment fragment = new DisplayFragment();
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
        View  view = inflater.inflate(R.layout.fragment_dislay, container, false);
         getActivity().setTitle(tittle);
         bioDp = view.findViewById(R.id.fragAvtId);
         statusIcon = view.findViewById(R.id.fragMoodIcon);
         name = view.findViewById(R.id.textView12);
         email = view.findViewById(R.id.textView13);
         operatingSys = view.findViewById(R.id.opsSysId);
         mood = view.findViewById(R.id.fragMood);


        return view;
    }

    public void update(userDetails newUser)
    {
        bioDp.setImageResource(newUser.getAvatarId());
        statusIcon.setImageResource(EditProfileFragment.imageToset(newUser.getMood()));
        name.setText(newUser.getName());
        email.setText(newUser.getEmailAddress());
        mood.setText(String.format(moodText,EditProfileFragment.statusText[newUser.getMood()-1]));
        operatingSys.setText(String.format(opSysText,newUser.getOpSys()));
    }
}
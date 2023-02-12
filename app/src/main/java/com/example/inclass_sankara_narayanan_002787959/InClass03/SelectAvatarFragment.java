package com.example.inclass_sankara_narayanan_002787959.InClass03;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.inclass_sankara_narayanan_002787959.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectAvatarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectAvatarFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ImageButton avatar1;
    ImageButton avatar2;
    ImageButton avatar3;
    ImageButton avatar4;
    ImageButton avatar5;
    ImageButton avatar6;

    public SelectAvatarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment selectAvatarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectAvatarFragment newInstance(String param1, String param2) {
        SelectAvatarFragment fragment = new SelectAvatarFragment();
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
        View view = inflater.inflate(R.layout.fragment_select_avatar, container, false);

        avatar1 = view.findViewById(R.id.imageButton2);
        avatar1.setOnClickListener(this);
        avatar2 = view.findViewById(R.id.imageButton3);
        avatar2.setOnClickListener(this);
        avatar3 = view.findViewById(R.id.imageButton4);
        avatar3.setOnClickListener(this);
        avatar4 = view.findViewById(R.id.imageButton5);
        avatar4.setOnClickListener(this);
        avatar5 = view.findViewById(R.id.imageButton6);
        avatar5.setOnClickListener(this);
        avatar6 = view.findViewById(R.id.imageButton7);
        avatar6.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View view) {
        Log.d("selectAvt", "onClick: here ");
        int id = view.getId();
        int imgId = 0;
        if(id == R.id.imageButton2)
        {
            imgId = R.drawable.avatar_f_1;
        }
        else if(id == R.id.imageButton3)
        {
            imgId = R.drawable.avatar_f_2;
        }
        else if(id == R.id.imageButton4)
        {
            imgId = R.drawable.avatar_f_3;
        }
        else if(id == R.id.imageButton5)
        {
            imgId = R.drawable.avatar_m_1;
        }
        else if(id == R.id.imageButton6)
        {
            imgId = R.drawable.avatar_m_2;
        }
        else
        {
            imgId = R.drawable.avatar_m_3;
        }
        sendData.fromAvatar(imgId);

    }

    public interface selectAvatarinterface
    {
        void fromAvatar(int imgRes);
    }

    selectAvatarinterface sendData;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof InClass03) {
            sendData = (InClass03) context;
        }
    }





}
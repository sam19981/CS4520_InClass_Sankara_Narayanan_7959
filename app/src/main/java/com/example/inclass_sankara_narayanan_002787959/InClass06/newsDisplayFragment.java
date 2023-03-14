package com.example.inclass_sankara_narayanan_002787959.InClass06;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.inclass_sankara_narayanan_002787959.InClass06.DataModels.News;
import com.example.inclass_sankara_narayanan_002787959.R;

import java.security.PrivateKey;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link newsDisplayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class newsDisplayFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ImageView imgview;
    private TextView tittle;
    private TextView author;
    private TextView description;
    private TextView dateTime;

    private News newsToDisplay;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public newsDisplayFragment(News news) {
        // Required empty public constructor
        newsToDisplay = news;
    }

    public newsDisplayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment newsDisplayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static newsDisplayFragment newInstance(String param1, String param2) {
        newsDisplayFragment fragment = new newsDisplayFragment();
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
        View view =  inflater.inflate(R.layout.fragment_news_display, container, false);
        imgview = view.findViewById(R.id.newDispImg);
        tittle = view.findViewById(R.id.tittleId);
        author = view.findViewById(R.id.newsAuthor);
        description = view.findViewById(R.id.newsDescription);
        dateTime = view.findViewById(R.id.dateTimeid);


        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if(getActivity().getSupportFragmentManager().getBackStackEntryCount()!=0)
                {
                    getActivity().getSupportFragmentManager().popBackStackImmediate();
                }
            }
        });

        Glide.with(getContext()).load(newsToDisplay.getUrlToImage()).into(imgview);
        tittle.setText(newsToDisplay.getTittle());
        author.setText(newsToDisplay.getAuthor());
        description.setText(newsToDisplay.getDescription());
        dateTime.setText(newsToDisplay.getPubLishedAt());

        return view;
    }



}
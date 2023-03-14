package com.example.inclass_sankara_narayanan_002787959.InClass06;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.inclass_sankara_narayanan_002787959.InClass06.DataModels.NetworkResponseListner;
import com.example.inclass_sankara_narayanan_002787959.InClass06.DataModels.News;
import com.example.inclass_sankara_narayanan_002787959.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link newsList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class newsList extends Fragment implements NetworkResponseListner {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "country";
    private static final String ARG_PARAM2 = "category";

    // TODO: Rename and change types of parameters

    private RecyclerView mRecyclerView;
    private NewsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String country ="ae";
    private String category ="business";

    private Spinner countrySpinner;
    private Spinner categorySpinner;
    //    private NewsCollection allNews;
    private final String[] countryArray = {"ae","ar","at","bg","br","in","it","lt","ro","rs","ru","sa","ua","us","ve","za"};
    private final String[] categoryArray = {"business","entertainment","general","health","science","sports","technology"};

    public newsList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param category Parameter 1.
     * @param country Parameter 2.
     * @return A new instance of fragment newsList.
     */
    // TODO: Rename and change types and number of parameters
    public static newsList newInstance(String category, String country) {
        newsList fragment = new newsList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, category);
        args.putString(ARG_PARAM2, country);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getString(ARG_PARAM1);
            country = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);
        countrySpinner = view.findViewById(R.id.countryId);
        ArrayAdapter<CharSequence> countryAdapter = new ArrayAdapter<CharSequence>(getContext(),android.R.layout.simple_spinner_item,countryArray);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(countryAdapter);
        mRecyclerView = view.findViewById(R.id.recyclerViewId);
        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                country=countryArray[position];
                LoadData load = new LoadData(country,category,newsList.this);
                load.execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        categorySpinner = view.findViewById(R.id.categoryId);
        ArrayAdapter<CharSequence> categoryAdapter = new ArrayAdapter<CharSequence>(getContext(),android.R.layout.simple_spinner_item,categoryArray);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category=categoryArray[position];
                LoadData load = new LoadData(country,category,newsList.this);
                load.execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return view;
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
//    {   super.onViewCreated(view, savedInstanceState);
//
//
//
//    }

    @Override
    public void SuccessData(ArrayList<News> news) {
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new NewsAdapter(news,getParentFragmentManager());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void FailedData() {
        Toast.makeText(getContext(), "Could not load Data", Toast.LENGTH_SHORT).show();
    }
}
package com.example.inclass_sankara_narayanan_002787959.InClass08;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.inclass_sankara_narayanan_002787959.InClass08.dataModels.UserData;
import com.example.inclass_sankara_narayanan_002787959.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private UserData mParam1;

    FirebaseAuth mAuth;

    FirebaseFirestore db;

    FirebaseUser mUser;

    ImageView profilePic;
    TextView userName;
    RecyclerView recyclerView;
    private ChatAdapter chatAdapter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;
    List<UserData> users;

    Button logOut;
    UserData currentUser;



    public ChatFragment() {
        // Required empty public constructor
        users = new ArrayList<>();
    }



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param user Parameter 1.
     * @return A new instance of fragment chatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatFragment newInstance(UserData user) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        mUser = mAuth.getCurrentUser();
        loadData();

    }

    public void updateRecyclerView(List<UserData> allUsers){
        chatAdapter.setUserList(allUsers);
        chatAdapter.notifyDataSetChanged();
    }

    private void loadData() {
        List<UserData> allUsers = new ArrayList<>();
        CollectionReference collRef = db.collection("users");
        collRef.get()
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("UserList", "Could Not load User list ");
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if(task.getResult().size()>1) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d("UserList", document.getId() + " => " + document.getData());
                                    UserData user = document.toObject(UserData.class);
                                    if (!user.getEmail().equals(mUser.getEmail())) {
                                        allUsers.add(user);
                                    }
                                }
                                updateRecyclerView(allUsers);
                            }
                        } else {
                            Log.w("UserList", "Error getting documents.", task.getException());
                        }
                    }
                });
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_chat, container, false);

        profilePic = view.findViewById(R.id.userProfilepicId);
        userName = view.findViewById(R.id.userNameId);
        recyclerView = view.findViewById(R.id.recyclerId);
        recyclerViewLayoutManager = new LinearLayoutManager(getContext());
        chatAdapter = new ChatAdapter(users,getContext());
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerView.setAdapter(chatAdapter);


        db.collection("users").document(mUser.getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful())
                        {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            currentUser = documentSnapshot.toObject(UserData.class);
                            userName.setText(currentUser.getName());
                            Glide.with(getContext()).load(currentUser.getProfilepicture()).into(profilePic);
                        }
                    }
                });



        logOut = view.findViewById(R.id.logOut);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.rootLayoutId,new LoginFragment(),"chatToLogin").addToBackStack(null).commit();
            }
        });

        return view;
    }
}
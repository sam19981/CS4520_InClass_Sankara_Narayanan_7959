package com.example.inclass_sankara_narayanan_002787959.InClass08;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inclass_sankara_narayanan_002787959.InClass08.Interfaces.SendImage;
import com.example.inclass_sankara_narayanan_002787959.InClass08.dataModels.MessageData;
import com.example.inclass_sankara_narayanan_002787959.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DisplayChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DisplayChatFragment extends Fragment implements InClass08.DisplayTakenPhoto {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String ARG_PARAM3 = "convo";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String convo;
    private ImageView toImg;
    private TextView toUser;
    private ImageButton back;
    private FirebaseUser currentUser;
    private RecyclerView recyclerViewchats;
    private LinearLayout linearLayout;
    private DisplayChatAdapter displayChatAdapter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;

    private TextInputEditText sendMessage;
    private Uri imgSend;
    private ImageView sendImage;

    FirebaseFirestore db;
    FirebaseAuth mAuth;
    private Button sendBtn;

    String  currentConversation="";

    List<MessageData> conversationBtwUsers;

    private static CollectionReference collRef;

    public DisplayChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment displayChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DisplayChatFragment newInstance(String param1, String param2,String convo) {
        DisplayChatFragment fragment = new DisplayChatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2,param2);
        args.putString(ARG_PARAM3,convo);
        fragment.setArguments(args);
        return fragment;
    }

    public void updateSendImage(Uri URI)
    {
        imgSend = URI;
        DocumentReference doc = db.collection("conversations").document(convo);
        currentConversation=convo;
        MessageData messageTopost = new MessageData();
        messageTopost.setMessage(imgSend.toString());
        messageTopost.setUserId(currentUser.getEmail());
        messageTopost.setMessageType("Image");
        messageTopost.setTimestamp(String.valueOf(Calendar.getInstance().getTimeInMillis()));
        doc.collection("allmessages").document().set(messageTopost).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("Sending Message", "onSuccess: Posted successfully ");
                sendMessage.setText("");
                loadData();
            }
        });

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            convo = getArguments().getString(ARG_PARAM3);
        }
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser =mAuth.getCurrentUser();
        conversationBtwUsers = new ArrayList<>();
        List<String> userIds = Arrays.asList(mParam1,currentUser.getEmail());

        collRef =  db.collection("conversations");

        Collections.sort(userIds);

        CollectionReference collRef = db.collection("conversations");

        Query query = collRef.whereEqualTo("userIds", userIds);

        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null)
                {
                    Log.d("Conversation", "Error");
                    return;

                }
                if(value.isEmpty())
                {
                    Log.d("Conversation", "Value Empty");
                     DocumentReference documentReference = collRef.document();
                     documentReference.set(new Conversation(userIds)).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d("Conversation", "created Conversation");
                            currentConversation = documentReference.getId();
                            addEventListner();
                        }
                    });
                }
                else
                {
                    Log.d("Conversation", "onEvent: Convo exists");
                    for(QueryDocumentSnapshot snapshot: value)
                    {
                        currentConversation = snapshot.getId();
                        DocumentReference document = collRef.document(currentConversation);
                        document.collection("allmessages").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                for(QueryDocumentSnapshot snapshot: task.getResult())
                                {
                                    conversationBtwUsers.add(snapshot.toObject(MessageData.class));
                                    Log.d("chatMessages", "onComplete: "+snapshot.getId()+"-"+snapshot.getData());
                                }

                                updateRecyclerView(conversationBtwUsers);
                                addEventListner();
                            }
                        });
                    }
                }
            }
        });

    }

    void addEventListner() {
        collRef.document(currentConversation).collection("allmessages").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    for (DocumentSnapshot snapshot : value.getDocuments()) {
                        conversationBtwUsers.add(snapshot.toObject(MessageData.class));
                        Log.d("chatMessages", "onComplete: " + snapshot.getId() + "-" + snapshot.getData());
                    }
                    conversationBtwUsers.sort(Comparator.comparing(MessageData::getTimestamp));
                    loadData();
                }
            });
    }


    private void updateRecyclerView(List<MessageData>convo)
    {
        displayChatAdapter.setConversation(convo);
        displayChatAdapter.notifyDataSetChanged();
    }

    private void loadData()
    {   CollectionReference collRef = db.collection("conversations");
        DocumentReference document = collRef.document(currentConversation);
        conversationBtwUsers = new ArrayList<>();
        document.collection("allmessages").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                for(QueryDocumentSnapshot snapshot: task.getResult())
                {
                    conversationBtwUsers.add(snapshot.toObject(MessageData.class));
                    Log.d("chatMessages", "onComplete: "+snapshot.getId()+"-"+snapshot.getData());
                }
                conversationBtwUsers.sort(Comparator.comparing(MessageData::getTimestamp));
                updateRecyclerView(conversationBtwUsers);

            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_display_chat, container, false);
       toImg = view.findViewById(R.id.toImg);
       toUser = view.findViewById(R.id.touserName);
       back = view.findViewById(R.id.backButtonId);
       recyclerViewchats = view.findViewById(R.id.displayChatId);
        recyclerViewLayoutManager = new LinearLayoutManager(getContext());
        displayChatAdapter = new DisplayChatAdapter(mParam1,mParam2);
        recyclerViewchats.setLayoutManager(recyclerViewLayoutManager);
        recyclerViewchats.setAdapter(displayChatAdapter);
       toUser.setText(mParam2);
       sendBtn = view.findViewById(R.id.sendBtn);
       sendMessage = view.findViewById(R.id.sendMsgId);
       sendImage = view.findViewById(R.id.sendImage);


       sendImage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.rootLayoutId,new CameraFragment(DisplayChatFragment.this)).commit();
           }
       });

        sendBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String message = String.valueOf(sendMessage.getText());
               if (message.equals("")) {
                   Toast.makeText(getContext(), "No message to send", Toast.LENGTH_SHORT).show();
               } else {
                   DocumentReference doc = db.collection("conversations").document(currentConversation);
                   MessageData messageTopost = new MessageData();
                   messageTopost.setMessage(message);
                   messageTopost.setUserId(currentUser.getEmail());
                   messageTopost.setMessageType("message");
                   messageTopost.setTimestamp(String.valueOf(Calendar.getInstance().getTimeInMillis()));
                   doc.collection("allmessages").document().set(messageTopost).addOnSuccessListener(new OnSuccessListener<Void>() {
                       @Override
                       public void onSuccess(Void unused) {
                           Log.d("Sending Message", "onSuccess: Posted successfully ");
                           sendMessage.setText("");
                           loadData();
                       }
                   });

               }
           }
       });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction().replace(R.id.rootLayoutId,new ChatFragment(),"loginToChat" )
                        .addToBackStack(null).commit();
            }
        });

        return view;
    }

    @Override
    public void displayphoto(Uri URI) {
        SendImage sendImgins = (SendImage)getContext();
        sendImgins.send(URI,mParam2,mParam1,currentConversation);
    }

    @Override
    public void displayphotoFromgallery(Uri URI) {
        SendImage sendImgins = (SendImage)getContext();
        sendImgins.send(URI,mParam2,mParam1,currentConversation);
    }


    public class Conversation {
            private List<String> userIds;

            public Conversation() {
                // Required empty constructor for Firestore
            }

            public Conversation(List<String> userIds) {
                this.userIds = userIds;
            }

            public List<String> getUserIds() {
                return userIds;
            }

            public void setUserIds(List<String> userIds) {
                this.userIds = userIds;
            }
        }



}
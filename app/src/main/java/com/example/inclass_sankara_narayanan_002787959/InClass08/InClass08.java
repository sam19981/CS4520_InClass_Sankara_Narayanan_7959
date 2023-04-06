package com.example.inclass_sankara_narayanan_002787959.InClass08;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.inclass_sankara_narayanan_002787959.InClass08.Interfaces.SendImage;
import com.example.inclass_sankara_narayanan_002787959.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class InClass08 extends AppCompatActivity implements SendImage {

    FirebaseUser currentUser;
    FirebaseAuth mAuth;

    private FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class08);
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = mAuth.getCurrentUser();
        System.out.println(currentUser);
        goToNextScreen();
    }

    public void goToNextScreen()
    {
        if(currentUser!= null)
        {
            // go to the chatScreen
            getSupportFragmentManager().beginTransaction().replace(R.id.rootLayoutId,new ChatFragment(),"loginToChat" ).addToBackStack(null).commit();
            Log.d("userDtata" ,"goToNextScreen: "+mAuth.getCurrentUser().getEmail());
        }
        else {
            getSupportFragmentManager().beginTransaction().replace(R.id.rootLayoutId,new LoginFragment(),"ActivityToLogin").addToBackStack(null).commit();
        }
    }

    public void setUser(FirebaseUser user)
    {
        currentUser = mAuth.getCurrentUser();
        goToNextScreen();
    }

    @Override
    public void send(Uri URI,String tempToUser,String tempTouserEmail,String convo) {

        StorageReference storageReference = storage.getReference().child("images/"+URI.getLastPathSegment());
        UploadTask uploadImage  = storageReference.putFile(URI);

        uploadImage.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(InClass08.this, "Upload Failed! Try again!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    getSupportFragmentManager().beginTransaction().replace(R.id.rootLayoutId,DisplayChatFragment.newInstance(tempTouserEmail,tempToUser,convo),"sendImage").addToBackStack("sendImage").commit();
                                    getSupportFragmentManager().executePendingTransactions();
                                    DisplayChatFragment chatDisplayfrag = (DisplayChatFragment)getSupportFragmentManager().findFragmentByTag("sendImage");
                                    chatDisplayfrag.updateSendImage(uri);
                                }
                            });
                    }
                });

    }

    public interface DisplayTakenPhoto
    {
        public void displayphoto(Uri URI);

        public  void displayphotoFromgallery(Uri URI);
    }



}
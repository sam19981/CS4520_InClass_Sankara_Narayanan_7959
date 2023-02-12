package com.example.inclass_sankara_narayanan_002787959.InClass03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.inclass_sankara_narayanan_002787959.InClass02.userDetails;
import com.example.inclass_sankara_narayanan_002787959.R;

public class InClass03 extends AppCompatActivity implements SelectAvatarFragment.selectAvatarinterface,EditProfileFragment.editProfileInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class03);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.constraintId,new EditProfileFragment(), EditProfileFragment.TAG)
                .addToBackStack(null).
                commit();
    }


    @Override
    public void fromAvatar(int imgRes) {
        EditProfileFragment frag = (EditProfileFragment)getSupportFragmentManager()
                .findFragmentByTag(EditProfileFragment.TAG);
        frag.updateAvatar(imgRes);
        getSupportFragmentManager().popBackStack();

    }

    @Override
    public void fromEditprofile(userDetails user) {

        getSupportFragmentManager().popBackStack();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.constraintId,new DisplayFragment())
                .commit();
        getSupportFragmentManager().executePendingTransactions();
        DisplayFragment fragmentByTag = (DisplayFragment) getSupportFragmentManager().findFragmentById(R.id.constraintId);
        fragmentByTag.update(user);

    }
}
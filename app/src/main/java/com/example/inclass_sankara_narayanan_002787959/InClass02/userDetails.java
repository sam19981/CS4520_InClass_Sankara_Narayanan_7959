package com.example.inclass_sankara_narayanan_002787959.InClass02;

// Sankara Narayanan Rajagopal
//InClass02

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class userDetails implements Parcelable {

    private String emailAddress;
    private String name;
    private int avatarId;
    private int mood;
    private String opSys;


    public userDetails(String email, String name, String opSys, int avatarId, int mood)
    {
        this.emailAddress =email;
        this.name = name;
        this.avatarId = avatarId;
        this.mood = mood;
        this.opSys = opSys;
    }

    protected userDetails(Parcel in) {
        emailAddress = in.readString();
        name = in.readString();
        avatarId = in.readInt();
        mood = in.readInt();
        opSys = in.readString();
    }

    public int getMood() {
        return mood;
    }

    public String getName() {
        return name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public int getAvatarId() {
        return avatarId;
    }

    public String getOpSys() {
        return opSys;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
            parcel.writeString(emailAddress);
            parcel.writeString(name);
            parcel.writeInt(avatarId);
            parcel.writeInt(mood);
            parcel.writeString(opSys);
    }

    public static final Creator<userDetails> CREATOR = new Creator<userDetails>() {
        @Override
        public userDetails createFromParcel(Parcel in) {
            return new userDetails(in);
        }

        @Override
        public userDetails[] newArray(int size) {
            return new userDetails[size];
        }
    };

    @Override
    public String toString() {
        return "userDetails{" +
                "emailAddress='" + emailAddress + '\'' +
                ", name='" + name + '\'' +
                ", avatarId=" + avatarId +
                ", mood=" + mood +
                ", opSys='" + opSys + '\'' +
                '}';
    }
}

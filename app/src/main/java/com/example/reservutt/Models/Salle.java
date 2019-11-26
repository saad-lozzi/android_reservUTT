package com.example.reservutt.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Salle implements Parcelable {
    private String name;
    private String id;

    protected Salle(Parcel in) {
        name = in.readString();
        id = in.readString();
    }

    public static final Creator<Salle> CREATOR = new Creator<Salle>() {
        @Override
        public Salle createFromParcel(Parcel in) {
            return new Salle(in);
        }

        @Override
        public Salle[] newArray(int size) {
            return new Salle[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Salle(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public Salle(){
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(id);
    }
}

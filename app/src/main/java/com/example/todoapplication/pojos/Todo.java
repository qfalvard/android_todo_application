package com.example.todoapplication.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Todo implements Parcelable {

    private long id;

    private String name;

    private String urgency;

    public Todo() {
    }

    public Todo(String name, String urgency) {
        this.name = name;
        this.urgency = urgency;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrgency() {
        return urgency;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", urgency='" + urgency + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(name);
        parcel.writeString(urgency);
    }

    public Todo(Parcel parcel) {
        this.id = parcel.readLong();
        this.name = parcel.readString();
        this.urgency = parcel.readString();
    }

    public static final Parcelable.Creator<Todo> CREATOR = new Parcelable.Creator<Todo>() {

        @Override
        public Todo createFromParcel(Parcel parcel) {
            return new Todo(parcel);
        }

        @Override
        public Todo[] newArray(int size) {
            return new Todo[size];
        }
    };
}

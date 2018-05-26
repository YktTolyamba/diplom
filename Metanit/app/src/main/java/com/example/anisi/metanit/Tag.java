package com.example.anisi.metanit;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Tag implements Parcelable {
    public int id;
    public String name;

    public Tag(int _id, String _url, String _name) {
        id = _id;
        name = _name;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(id);
        parcel.writeString(name);
    }

    public static final Parcelable.Creator<Tag> CREATOR = new Parcelable.Creator<Tag>() {
        // распаковываем объект из Parcel
        public Tag createFromParcel(Parcel in) {
            return new Tag(in);
        }

        public Tag[] newArray(int size) {
            return new Tag[size];
        }
    };

    private Tag(Parcel parcel) {
        id = parcel.readInt();
        name = parcel.readString();
    }
}

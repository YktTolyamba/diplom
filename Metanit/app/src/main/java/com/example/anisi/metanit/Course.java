package com.example.anisi.metanit;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by Толян on 10.05.2018.
 */

public class Course implements Parcelable {
    public int id;
    public String url;
    public String name;

    public Course(int _id, String _url, String _name) {
        id = _id;
        url = _url;
        name = _name;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(id);
        parcel.writeString(url);
        parcel.writeString(name);
    }

    public static final Parcelable.Creator<Course> CREATOR = new Parcelable.Creator<Course>() {
        // распаковываем объект из Parcel
        public Course createFromParcel(Parcel in) {
            return new Course(in);
        }

        public Course[] newArray(int size) {
            return new Course[size];
        }
    };

    private Course(Parcel parcel) {
        id = parcel.readInt();
        url = parcel.readString();
        name = parcel.readString();
    }

//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getUrl() {
//        return url;
//    }
//
//    public void setUrl(String url) {
//        this.url = url;
//    }
}

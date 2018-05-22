package com.example.anisi.metanit;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class CourseTopic implements Parcelable{
    public int id;
    public String url;
    public String course;
    public String code;
    public String name;
    public String text;
    public String modified;
    public List<String> tag;

    public CourseTopic(int _id, String _url, String _course, String _code, String _name, String _text, String _modified, List<String> _tag) {
        id = _id;
        url = _url;
        course = _course;
        code = _code;
        name = _name;
        text = _text;
        modified = _modified;
        tag = _tag;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(id);
        parcel.writeString(url);
        parcel.writeString(course);
        parcel.writeString(code);
        parcel.writeString(name);
        parcel.writeString(text);
        parcel.writeString(modified);
        parcel.writeStringList(tag);
    }

    public static final Parcelable.Creator<CourseTopic> CREATOR = new Parcelable.Creator<CourseTopic>() {
        // распаковываем объект из Parcel
        public CourseTopic createFromParcel(Parcel in) {
            return new CourseTopic(in);
        }

        public CourseTopic[] newArray(int size) {
            return new CourseTopic[size];
        }
    };

    private CourseTopic(Parcel parcel) {
        id = parcel.readInt();
        url = parcel.readString();
        course = parcel.readString();
        code = parcel.readString();
        name = parcel.readString();
        text = parcel.readString();
        modified = parcel.readString();
        tag = parcel.createStringArrayList();
    }
}

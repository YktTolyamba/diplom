package com.example.anisi.metanit;

import android.os.Parcel;
import android.os.Parcelable;

public class CourseTopic implements Parcelable{
    public int id;
    public String url;
    public String course;
    public String code;
    public String name;
    public String text;
    public String modified;

    public CourseTopic(int _id, String _url, String _course, String _code, String _name, String _text, String _modified) {
        id = _id;
        url = _url;
        course = _course;
        code = _code;
        name = _name;
        text = _text;
        modified = _modified;
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
    }

//    public int getId() {
//        return id;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }
//
//    public String getCode() {
//        return code;
//    }
//
//    public void setCourse(String course) {
//        this.course = course;
//    }
//
//    public String getCourse() {
//        return course;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getModified() {
//        return modified;
//    }
//
//    public void setModified(String modified) {
//        this.modified = modified;
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
//    public String getText() {
//        return text;
//    }
//
//    public void setText(String text) {
//        this.text = text;
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

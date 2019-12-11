package com.example.bixapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class Photo implements Parcelable {

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };
    private int id;
    @SerializedName("album_id")
    private String albumId;
    private String title;
    private String url;
    private String thumbnail;
    @SerializedName("_links")
    private Map<String, Link> links;

    protected Photo(Parcel in) {
        id = in.readInt();
        albumId = in.readString();
        title = in.readString();
        url = in.readString();
        thumbnail = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(albumId);
        parcel.writeString(title);
        parcel.writeString(url);
        parcel.writeString(thumbnail);
    }
}

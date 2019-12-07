package com.example.bixapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class User implements Parcelable {
    private int id;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    private String gender;
    private String dob;
    private String email;
    private String phone;
    private String website;
    private String address;
    private String Status;
    @SerializedName("_links")
    private Map<String, Link> links;

    private boolean photoLoad = false;

    public User(int id, String firstName, String lastName, String gender, String email, String phone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
    }

    protected User(Parcel in) {
        id = in.readInt();
        firstName = in.readString();
        lastName = in.readString();
        gender = in.readString();
        dob = in.readString();
        email = in.readString();
        phone = in.readString();
        website = in.readString();
        address = in.readString();
        Status = in.readString();
        photoLoad = in.readByte() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(gender);
        parcel.writeString(dob);
        parcel.writeString(email);
        parcel.writeString(phone);
        parcel.writeString(website);
        parcel.writeString(address);
        parcel.writeString(Status);
        parcel.writeByte((byte) (photoLoad ? 1 : 0));
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public Map<String, Link> getLinks() {
        return links;
    }

    public void setLinks(Map<String, Link> links) {
        this.links = links;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public boolean isPhotoLoad() {
        return photoLoad;
    }

    public void setPhotoLoad(boolean photoLoad) {
        this.photoLoad = photoLoad;
    }


}

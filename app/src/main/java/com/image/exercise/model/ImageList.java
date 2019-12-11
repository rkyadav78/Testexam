package com.image.exercise.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImageList implements Parcelable {
    @SerializedName("title")
    private String title;
    @SerializedName("rows")
    private List<Rows> rows;


    protected ImageList(Parcel in) {
        title = in.readString();
        rows = in.createTypedArrayList(Rows.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeTypedList(rows);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ImageList> CREATOR = new Creator<ImageList>() {
        @Override
        public ImageList createFromParcel(Parcel in) {
            return new ImageList(in);
        }

        @Override
        public ImageList[] newArray(int size) {
            return new ImageList[size];
        }
    };

    public String getStatus() {
        return title;
    }

    public void setStatus(String status) {
        this.title = status;
    }

    public List<Rows> getRows() {
        return rows;
    }

    public void setRows(List<Rows> rows) {
        this.rows = rows;
    }
}

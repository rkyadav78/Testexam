package com.image.exercise.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.google.gson.annotations.SerializedName;


public class Rows implements Parcelable {

    private static int increment = 0;

    private long id;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("imageHref")
    private String url;



    protected Rows(Parcel in) {
                title = in.readString();
        description = in.readString();
        url = in.readString();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Rows> CREATOR = new Creator<Rows>() {
        @Override
        public Rows createFromParcel(Parcel in) {
            return new Rows(in);
        }

        @Override
        public Rows[] newArray(int size) {
            return new Rows[size];
        }
    };

    public long getId() {
        return id;
    }

    protected void setId() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }



    public static DiffUtil.ItemCallback<Rows> DIFF_CALLBACK = new DiffUtil.ItemCallback<Rows>() {
        @Override
        public boolean areItemsTheSame(@NonNull Rows oldItem, @NonNull Rows newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Rows oldItem, @NonNull Rows newItem) {
            return oldItem.equals(newItem);
        }
    };

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        Rows rows = (Rows) obj;
        return rows.id == this.id;
    }
}

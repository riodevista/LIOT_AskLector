package ru.mipt.asklector.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Dmitry Bochkov on 20.10.2015.
 */
public class LectureID implements Parcelable, AskLectorObject{

    public static final String OBJECT = "LectureID";

    private String id;

    public LectureID(String id){
        this.id = id;
    }

    protected LectureID(Parcel in) {
        id = in.readString();
    }

    public static final Creator<LectureID> CREATOR = new Creator<LectureID>() {
        @Override
        public LectureID createFromParcel(Parcel in) {
            return new LectureID(in);
        }

        @Override
        public LectureID[] newArray(int size) {
            return new LectureID[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
    }
}

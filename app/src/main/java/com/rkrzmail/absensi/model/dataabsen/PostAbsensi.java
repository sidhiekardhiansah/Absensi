
package com.rkrzmail.absensi.model.dataabsen;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PostAbsensi implements Serializable, Parcelable
{

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    public final static Creator<PostAbsensi> CREATOR = new Creator<PostAbsensi>() {


        @SuppressWarnings({
            "unchecked"
        })
        public PostAbsensi createFromParcel(Parcel in) {
            return new PostAbsensi(in);
        }

        public PostAbsensi[] newArray(int size) {
            return (new PostAbsensi[size]);
        }

    }
    ;
    private final static long serialVersionUID = 3880527025328382646L;

    protected PostAbsensi(Parcel in) {
        this.status = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
    }

    public PostAbsensi() {
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeValue(message);
    }

    public int describeContents() {
        return  0;
    }

}

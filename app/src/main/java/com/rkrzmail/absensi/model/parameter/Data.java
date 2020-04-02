
package com.rkrzmail.absensi.model.parameter;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Data implements Serializable, Parcelable
{

    @SerializedName("ref_parameter_shift")
    @Expose
    private List<DataParameter> refParameterShift = null;
    public final static Creator<Data> CREATOR = new Creator<Data>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        public Data[] newArray(int size) {
            return (new Data[size]);
        }

    }
    ;
    private final static long serialVersionUID = -993485903447947985L;

    protected Data(Parcel in) {
        in.readList(this.refParameterShift, (DataParameter.class.getClassLoader()));
    }

    public Data() {
    }

    public List<DataParameter> getRefParameterShift() {
        return refParameterShift;
    }

    public void setRefParameterShift(List<DataParameter> refParameterShift) {
        this.refParameterShift = refParameterShift;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(refParameterShift);
    }

    public int describeContents() {
        return  0;
    }

}

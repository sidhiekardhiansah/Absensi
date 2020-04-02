
package com.rkrzmail.absensi.model.parameter;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DataParameter implements Serializable, Parcelable
{

    @SerializedName("ID")
    @Expose
    private String iD;
    @SerializedName("Unit")
    @Expose
    private String unit;
    @SerializedName("Shift_Name")
    @Expose
    private String shiftName;
    @SerializedName("Jam_Mulai")
    @Expose
    private String jamMulai;
    @SerializedName("Jam_Selesai")
    @Expose
    private String jamSelesai;
    public final static Creator<DataParameter> CREATOR = new Creator<DataParameter>() {


        @SuppressWarnings({
            "unchecked"
        })
        public DataParameter createFromParcel(Parcel in) {
            return new DataParameter(in);
        }

        public DataParameter[] newArray(int size) {
            return (new DataParameter[size]);
        }

    }
    ;
    private final static long serialVersionUID = -7381043585298811301L;

    protected DataParameter(Parcel in) {
        this.iD = ((String) in.readValue((String.class.getClassLoader())));
        this.unit = ((String) in.readValue((String.class.getClassLoader())));
        this.shiftName = ((String) in.readValue((String.class.getClassLoader())));
        this.jamMulai = ((String) in.readValue((String.class.getClassLoader())));
        this.jamSelesai = ((String) in.readValue((String.class.getClassLoader())));
    }

    public DataParameter() {
    }

    public String getID() {
        return iD;
    }

    public void setID(String iD) {
        this.iD = iD;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getShiftName() {
        return shiftName;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }

    public String getJamMulai() {
        return jamMulai;
    }

    public void setJamMulai(String jamMulai) {
        this.jamMulai = jamMulai;
    }

    public String getJamSelesai() {
        return jamSelesai;
    }

    public void setJamSelesai(String jamSelesai) {
        this.jamSelesai = jamSelesai;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(iD);
        dest.writeValue(unit);
        dest.writeValue(shiftName);
        dest.writeValue(jamMulai);
        dest.writeValue(jamSelesai);
    }

    public int describeContents() {
        return  0;
    }

}

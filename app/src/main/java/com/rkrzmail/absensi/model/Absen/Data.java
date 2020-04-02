
package com.rkrzmail.absensi.model.Absen;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Serializable, Parcelable
{

    @SerializedName("sales_code")
    @Expose
    private String salesCode;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("branch")
    @Expose
    private String branch;
    @SerializedName("position")
    @Expose
    private String position;
    @SerializedName("foto")
    @Expose
    private String foto;
    @SerializedName("created_date_foto")
    @Expose
    private String createdDateFoto;
    @SerializedName("kategori")
    @Expose
    private String kategori;
    @SerializedName("geo_info")
    @Expose
    private String geoInfo;
    @SerializedName("shift_name")
    @Expose
    private String shiftName;
    @SerializedName("longshift_status")
    @Expose
    private String longshiftStatus;
    @SerializedName("approved_status")
    @Expose
    private String approvedStatus;
    @SerializedName("approved_by")
    @Expose
    private String approvedBy;
    @SerializedName("approved_date")
    @Expose
    private String approvedDate;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("keterangan")
    @Expose
    private String keterangan;
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
    private final static long serialVersionUID = -8343797269544330378L;

    protected Data(Parcel in) {
        this.salesCode = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.unit = ((String) in.readValue((String.class.getClassLoader())));
        this.branch = ((String) in.readValue((String.class.getClassLoader())));
        this.position = ((String) in.readValue((String.class.getClassLoader())));
        this.foto = ((String) in.readValue((String.class.getClassLoader())));
        this.createdDateFoto = ((String) in.readValue((String.class.getClassLoader())));
        this.kategori = ((String) in.readValue((String.class.getClassLoader())));
        this.geoInfo = ((String) in.readValue((String.class.getClassLoader())));
        this.shiftName = ((String) in.readValue((String.class.getClassLoader())));
        this.longshiftStatus = ((String) in.readValue((String.class.getClassLoader())));
        this.approvedStatus = ((String) in.readValue((String.class.getClassLoader())));
        this.approvedBy = ((String) in.readValue((String.class.getClassLoader())));
        this.approvedDate = ((String) in.readValue((String.class.getClassLoader())));
        this.createdDate = ((String) in.readValue((String.class.getClassLoader())));
        this.keterangan = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Data() {
    }

    public String getSalesCode() {
        return salesCode;
    }

    public void setSalesCode(String salesCode) {
        this.salesCode = salesCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getCreatedDateFoto() {
        return createdDateFoto;
    }

    public void setCreatedDateFoto(String createdDateFoto) {
        this.createdDateFoto = createdDateFoto;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getGeoInfo() {
        return geoInfo;
    }

    public void setGeoInfo(String geoInfo) {
        this.geoInfo = geoInfo;
    }

    public String getShiftName() {
        return shiftName;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }

    public String getLongshiftStatus() {
        return longshiftStatus;
    }

    public void setLongshiftStatus(String longshiftStatus) {
        this.longshiftStatus = longshiftStatus;
    }

    public String getApprovedStatus() {
        return approvedStatus;
    }

    public void setApprovedStatus(String approvedStatus) {
        this.approvedStatus = approvedStatus;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(String approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(salesCode);
        dest.writeValue(name);
        dest.writeValue(unit);
        dest.writeValue(branch);
        dest.writeValue(position);
        dest.writeValue(foto);
        dest.writeValue(createdDateFoto);
        dest.writeValue(kategori);
        dest.writeValue(geoInfo);
        dest.writeValue(shiftName);
        dest.writeValue(longshiftStatus);
        dest.writeValue(approvedStatus);
        dest.writeValue(approvedBy);
        dest.writeValue(approvedDate);
        dest.writeValue(createdDate);
        dest.writeValue(keterangan);
    }

    public int describeContents() {
        return  0;
    }

}

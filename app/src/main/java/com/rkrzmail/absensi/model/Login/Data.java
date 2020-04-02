
package com.rkrzmail.absensi.model.Login;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Data implements Serializable, Parcelable
{

    @SerializedName("Employee_ID")
    @Expose
    private String employeeID;
    @SerializedName("NIK")
    @Expose
    private String nIK;
    @SerializedName("DSR_Code")
    @Expose
    private String dSRCode;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Date_Of_Birth")
    @Expose
    private String dateOfBirth;
    @SerializedName("Image_Photo")
    @Expose
    private String imagePhoto;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Employee_Type")
    @Expose
    private String employeeType;
    @SerializedName("Employee_Type_Group")
    @Expose
    private String employeeTypeGroup;
    @SerializedName("Branch")
    @Expose
    private String branch;
    @SerializedName("Division")
    @Expose
    private String division;
    @SerializedName("Departement")
    @Expose
    private String departement;
    @SerializedName("Unit")
    @Expose
    private String unit;
    @SerializedName("Position")
    @Expose
    private String position;
    @SerializedName("Channel")
    @Expose
    private String channel;
    @SerializedName("Group_Type")
    @Expose
    private String groupType;
    @SerializedName("Level")
    @Expose
    private String level;
    @SerializedName("Product")
    @Expose
    private String product;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("SM_Code")
    @Expose
    private String sMCode;
    @SerializedName("SM_Name")
    @Expose
    private String sMName;
    @SerializedName("Password")
    @Expose
    private String password;
    @SerializedName("Password_Change")
    @Expose
    private String passwordChange;
    @SerializedName("Last_Login")
    @Expose
    private Object lastLogin;
    @SerializedName("Created_Date")
    @Expose
    private String createdDate;
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
    private final static long serialVersionUID = -3911762780233955622L;

    protected Data(Parcel in) {
        this.employeeID = ((String) in.readValue((String.class.getClassLoader())));
        this.nIK = ((String) in.readValue((String.class.getClassLoader())));
        this.dSRCode = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.dateOfBirth = ((String) in.readValue((String.class.getClassLoader())));
        this.imagePhoto = ((String) in.readValue((String.class.getClassLoader())));
        this.email = ((String) in.readValue((String.class.getClassLoader())));
        this.employeeType = ((String) in.readValue((String.class.getClassLoader())));
        this.employeeTypeGroup = ((String) in.readValue((String.class.getClassLoader())));
        this.branch = ((String) in.readValue((String.class.getClassLoader())));
        this.division = ((String) in.readValue((String.class.getClassLoader())));
        this.departement = ((String) in.readValue((String.class.getClassLoader())));
        this.unit = ((String) in.readValue((String.class.getClassLoader())));
        this.position = ((String) in.readValue((String.class.getClassLoader())));
        this.channel = ((String) in.readValue((String.class.getClassLoader())));
        this.groupType = ((String) in.readValue((String.class.getClassLoader())));
        this.level = ((String) in.readValue((String.class.getClassLoader())));
        this.product = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.sMCode = ((String) in.readValue((String.class.getClassLoader())));
        this.sMName = ((String) in.readValue((String.class.getClassLoader())));
        this.password = ((String) in.readValue((String.class.getClassLoader())));
        this.passwordChange = ((String) in.readValue((String.class.getClassLoader())));
        this.lastLogin = ((Object) in.readValue((Object.class.getClassLoader())));
        this.createdDate = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data() {
    }

    /**
     * 
     * @param lastLogin
     * @param groupType
     * @param departement
     * @param channel
     * @param employeeID
     * @param branch
     * @param division
     * @param nIK
     * @param password
     * @param passwordChange
     * @param sMCode
     * @param email
     * @param product
     * @param dSRCode
     * @param level
     * @param employeeTypeGroup
     * @param dateOfBirth
     * @param unit
     * @param employeeType
     * @param createdDate
     * @param name
     * @param sMName
     * @param position
     * @param imagePhoto
     * @param status
     */
    public Data(String employeeID, String nIK, String dSRCode, String name, String dateOfBirth, String imagePhoto, String email, String employeeType, String employeeTypeGroup, String branch, String division, String departement, String unit, String position, String channel, String groupType, String level, String product, String status, String sMCode, String sMName, String password, String passwordChange, Object lastLogin, String createdDate) {
        super();
        this.employeeID = employeeID;
        this.nIK = nIK;
        this.dSRCode = dSRCode;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.imagePhoto = imagePhoto;
        this.email = email;
        this.employeeType = employeeType;
        this.employeeTypeGroup = employeeTypeGroup;
        this.branch = branch;
        this.division = division;
        this.departement = departement;
        this.unit = unit;
        this.position = position;
        this.channel = channel;
        this.groupType = groupType;
        this.level = level;
        this.product = product;
        this.status = status;
        this.sMCode = sMCode;
        this.sMName = sMName;
        this.password = password;
        this.passwordChange = passwordChange;
        this.lastLogin = lastLogin;
        this.createdDate = createdDate;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getNIK() {
        return nIK;
    }

    public void setNIK(String nIK) {
        this.nIK = nIK;
    }

    public String getDSRCode() {
        return dSRCode;
    }

    public void setDSRCode(String dSRCode) {
        this.dSRCode = dSRCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getImagePhoto() {
        return imagePhoto;
    }

    public void setImagePhoto(String imagePhoto) {
        this.imagePhoto = imagePhoto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    public String getEmployeeTypeGroup() {
        return employeeTypeGroup;
    }

    public void setEmployeeTypeGroup(String employeeTypeGroup) {
        this.employeeTypeGroup = employeeTypeGroup;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSMCode() {
        return sMCode;
    }

    public void setSMCode(String sMCode) {
        this.sMCode = sMCode;
    }

    public String getSMName() {
        return sMName;
    }

    public void setSMName(String sMName) {
        this.sMName = sMName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordChange() {
        return passwordChange;
    }

    public void setPasswordChange(String passwordChange) {
        this.passwordChange = passwordChange;
    }

    public Object getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Object lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(employeeID);
        dest.writeValue(nIK);
        dest.writeValue(dSRCode);
        dest.writeValue(name);
        dest.writeValue(dateOfBirth);
        dest.writeValue(imagePhoto);
        dest.writeValue(email);
        dest.writeValue(employeeType);
        dest.writeValue(employeeTypeGroup);
        dest.writeValue(branch);
        dest.writeValue(division);
        dest.writeValue(departement);
        dest.writeValue(unit);
        dest.writeValue(position);
        dest.writeValue(channel);
        dest.writeValue(groupType);
        dest.writeValue(level);
        dest.writeValue(product);
        dest.writeValue(status);
        dest.writeValue(sMCode);
        dest.writeValue(sMName);
        dest.writeValue(password);
        dest.writeValue(passwordChange);
        dest.writeValue(lastLogin);
        dest.writeValue(createdDate);
    }

    public int describeContents() {
        return  0;
    }

}

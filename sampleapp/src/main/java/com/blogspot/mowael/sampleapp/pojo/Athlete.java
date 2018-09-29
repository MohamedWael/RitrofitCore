
package com.blogspot.mowael.sampleapp.pojo;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Athlete {

    @SerializedName("brief")
    private String mBrief;
    @SerializedName("image")
    private String mImage;
    @SerializedName("name")
    private String mName;

    public String getBrief() {
        return mBrief;
    }

    public void setBrief(String brief) {
        mBrief = brief;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

}

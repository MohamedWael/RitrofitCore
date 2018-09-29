
package com.blogspot.mowael.sampleapp.pojo;

import com.blogspot.mowael.sampleapp.pojo.Athlete;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class Athletes {

    @SerializedName("athletes")
    private List<Athlete> mAthletes;

    public List<Athlete> getAthletes() {
        return mAthletes;
    }

    public void setAthletes(List<Athlete> athletes) {
        mAthletes = athletes;
    }

}

package com.manics.food.ListView;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Yash on 9/28/2015.
 */
public class CuisineList implements Parcelable{

    private ArrayList<ListItem> Highlights;
    private ArrayList<ListItem> Lunch;
    private ArrayList<ListItem> Dinner;

    public CuisineList(){;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel pWrite, int flags) {

        pWrite.writeTypedList(Highlights);
        pWrite.writeTypedList(Lunch);
        pWrite.writeTypedList(Dinner);
    }

    public CuisineList(Parcel pRead){
        this.Highlights=pRead.createTypedArrayList(CREATOR);
        this.Lunch=pRead.createTypedArrayList(CREATOR);
        this.Dinner=pRead.createTypedArrayList(CREATOR);
    }

    /**
     * This field is needed for Android to be able to create new objects, individually or as arrays */
    public static final Parcelable.Creator CREATOR=new Parcelable.Creator(){
        @Override
        public Object createFromParcel(Parcel in) {
            return new CuisineList(in);
        }

        @Override
        public Object[] newArray(int size) {
            return new Object[size];
        }
    };

    public ArrayList<ListItem> getHighlights() {
        return Highlights;
    }

    public void setHighlights(ArrayList<ListItem> highlights) {
        Highlights = highlights;
    }

    public ArrayList<ListItem> getLunch() {
        return Lunch;
    }

    public void setLunch(ArrayList<ListItem> lunch) {
        Lunch = lunch;
    }

    public ArrayList<ListItem> getDinner() {
        return Dinner;
    }

    public void setDinner(ArrayList<ListItem> dinner) {
        Dinner = dinner;
    }

}



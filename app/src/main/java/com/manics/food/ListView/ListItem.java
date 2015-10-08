package com.manics.food.ListView;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by Yash on 8/29/2015.
 */
public class ListItem implements Parcelable {

    private String itemFlag;
    private String itemTitleValue;
    private String itemDescriptionValue;
    private String itemPriceValue;
    private String itemImageResourceValue;
    private String itemRating;
    private byte[] imageBitmap;


    /**
     * Standard basic constructor for non-parcel object creation
     */
    public ListItem(){;}


    /** Constructor to use when re-constructing object from a parcel
     *  This constructor is invoked by the method createFromParcel(Parcel source) of the object creator.
     * @param in a parcel from which to read this object
     */

    public ListItem(Parcel in){
        this.itemFlag=in.readString();
        this.itemTitleValue=in.readString();
        this.itemDescriptionValue=in.readString();
        this.itemPriceValue=in.readString();
        this.itemImageResourceValue=in.readString();
        this.itemRating=in.readString();
        Log.d("HomePage_masterListVal","inParcelRead");
        //this.imageBitmap=in.readParcelable(Bitmap.class.getClassLoader());
        //this.imageBitmap=(byte[])in.readArray(Byte.class.getClassLoader());
        this.imageBitmap=new byte[in.readInt()];
        //in.readInt(this.imageBitmap.length);
        in.readByteArray(this.imageBitmap);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(itemFlag);
        dest.writeString(itemTitleValue);
        dest.writeString(itemDescriptionValue);
        dest.writeString(itemPriceValue);
        dest.writeString(itemImageResourceValue);
        dest.writeString(itemRating);
        Log.d("HomePage_masterListVal", "inParcelWrite");
        dest.writeInt(imageBitmap.length);
        dest.writeByteArray(imageBitmap);
        //dest.writeParcelable(imageBitmap, flags);
        //imageBitmap.writeToParcel(dest,flags);
    }

    /**
     * This field is needed for Android to be able to create new objects, individually or as arrays.
     */
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public Object createFromParcel(Parcel in) {
            return new ListItem(in);
        }

        @Override
        public Object[] newArray(int size) {
            return new ListItem[size];
        }
    };

    public String getItemTitleValue() {
        return itemTitleValue;
    }

    public void setItemTitleValue(String itemTitleValue) {
        this.itemTitleValue = itemTitleValue;
    }

    public String getItemDescriptionValue() {
        return itemDescriptionValue;
    }

    public void setItemDescriptionValue(String itemDescriptionValue) {
        this.itemDescriptionValue = itemDescriptionValue;
    }

    public String getItemPriceValue() {
        return itemPriceValue;
    }

    public void setItemPriceValue(String itemPriceValue) {
        this.itemPriceValue = itemPriceValue;
    }

    public String getItemImageResourceValue() {
        return itemImageResourceValue;
    }

    public void setItemImageResourceValue(String itemImageResourceValue) {
        this.itemImageResourceValue = itemImageResourceValue;
    }

    public String getItemRating() {
        return itemRating;
    }

    public void setItemRating(String itemRating) {
        this.itemRating = itemRating;
    }

    public String getItemFlag() {
        return itemFlag;
    }

    public void setItemFlag(String itemFlag) {
        this.itemFlag = itemFlag;
    }

    public byte[] getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(byte[] imageBitmap) {
        this.imageBitmap = imageBitmap;
    }
}

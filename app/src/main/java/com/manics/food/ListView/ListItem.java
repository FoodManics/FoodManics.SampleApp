package com.manics.food.ListView;

import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

/**
 * Created by Yash on 8/29/2015.
 */
public class ListItem implements Serializable {

    private String itemTitleValue;
    private String itemDescriptionValue;
    private String itemPriceValue;
    private int itemImageResourceValue;

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

    public int getItemImageResourceValue() {
        return itemImageResourceValue;
    }

    public void setItemImageResourceValue(int itemImageResourceValue) {
        this.itemImageResourceValue = itemImageResourceValue;
    }
}

package com.manics.food;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.manics.food.Common.MenuCommon;
import com.manics.food.ListView.ListItem;
import com.manics.food.PaymentCartIntegration.PaymentPage;
import com.manics.food.foodmanics.R;


public class ItemSelection extends MenuCommon {

    //private View orderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_item_selection);

        View orderButton = (Button) findViewById(R.id.orderButton);
        orderButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent homePageIntent = new Intent(ItemSelection.this, PaymentPage.class);
                        startActivity(homePageIntent);
                    }
                }
        );

        ListItem listSelectedItem =  intent.getParcelableExtra("listItemObject");

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        ImageView imageDisplay = (ImageView) findViewById(R.id.imageViewExpanded);
        TextView title = (TextView) findViewById(R.id.title);
        TextView price = (TextView) findViewById(R.id.price);
        TextView description = (TextView) findViewById(R.id.description);
        RatingBar ratingBar=(RatingBar)findViewById(R.id.itemRratingBar);

        byte [] byteArr=listSelectedItem.getImageBitmap();
        Bitmap imageBmp= BitmapFactory.decodeByteArray(byteArr, 0, byteArr.length);

        //Bitmap imageBmp = listSelectedItem.getImageBitmap();
        String titleValue = listSelectedItem.getItemTitleValue();
        String priceValue = "$"+listSelectedItem.getItemPriceValue();
        String descriptionValue = listSelectedItem.getItemDescriptionValue();
        float ratingValue=(Float.parseFloat(listSelectedItem.getItemRating()));

        imageDisplay.setImageBitmap(imageBmp);
        title.setText(titleValue);
        price.setText(priceValue);
        description.setText(descriptionValue);
        ratingBar.setRating(ratingValue);

    }
}

package com.manics.food.ListView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.manics.food.Common.MenuCommon;
import com.manics.food.ItemSelection;
import com.manics.food.foodmanics.R;

import java.util.ArrayList;
import java.util.List;

public class ListItemExample extends MenuCommon {

    private ListItem listItems = new ListItem();
    private final List<ListItem> listItemsList = new ArrayList<>();
    public MenuCommon menuObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item_example);

        listItems.setItemImageResourceValue("samosa");
        listItems.setItemTitleValue("Item1");
        listItems.setItemDescriptionValue("Item1 Description..." + "\n" + "...goes here");
        listItems.setItemPriceValue("$4.00");
        listItemsList.add(listItems);

        listItems = new ListItem();
        listItems.setItemImageResourceValue("pizza");
        listItems.setItemTitleValue("Item2");
        listItems.setItemDescriptionValue("Item2 Description..." + "\n" + "...goes here");
        listItems.setItemPriceValue("$10.00");
        listItemsList.add(listItems);

        listItems = new ListItem();
        listItems.setItemImageResourceValue("");
        listItems.setItemTitleValue("Item3");
        listItems.setItemDescriptionValue("Item3 Description..." + "\n" + "...goes here");
        listItems.setItemPriceValue("$18.00");
        listItemsList.add(listItems);

        listItems = new ListItem();
        listItems.setItemImageResourceValue("");
        listItems.setItemTitleValue("Item4");
        listItems.setItemDescriptionValue("Item4 Description..." + "\n" + "...goes here");
        listItems.setItemPriceValue("$8.00");
        listItemsList.add(listItems);

        listItems = new ListItem();
        listItems.setItemImageResourceValue("");
        listItems.setItemTitleValue("Item5");
        listItems.setItemDescriptionValue("Item5 Description..." + "\n" + "...goes here");
        listItems.setItemPriceValue("$6.00");
        listItemsList.add(listItems);

        listItems = new ListItem();
        listItems.setItemImageResourceValue("");
        listItems.setItemTitleValue("Item6");
        listItems.setItemDescriptionValue("Item6 Description..." + "\n" + "...goes here");
        listItems.setItemPriceValue("$10.00");
        listItemsList.add(listItems);

        listItems = new ListItem();
        listItems.setItemImageResourceValue("");
        listItems.setItemTitleValue("Item7");
        listItems.setItemDescriptionValue("Item7 Description..." + "\n" + "...goes here");
        listItems.setItemPriceValue("$12.00");
        listItemsList.add(listItems);

        listItems = new ListItem();
        listItems.setItemImageResourceValue("");
        listItems.setItemTitleValue("Item8");
        listItems.setItemDescriptionValue("Item8 Description..." + "\n" + "...goes here");
        listItems.setItemPriceValue("$6.00");
        listItemsList.add(listItems);

        listItems = new ListItem();
        listItems.setItemImageResourceValue("");
        listItems.setItemTitleValue("Item9");
        listItems.setItemDescriptionValue("Item9 Description..." + "\n" + "...goes here");
        listItems.setItemPriceValue("$7.00");
        listItemsList.add(listItems);

        ListView listview = (ListView) findViewById(R.id.listMenu);

        //final ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, menuText);

        final MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this, android.R.layout.simple_list_item_single_choice, listItemsList);
        listview.setAdapter(adapter);
        //getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        listview.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view,int position, long id)
                    {
                        listItemSelectListener(parent, view, position, id);
                    }
                }
        );
}

    public void listItemSelectListener(AdapterView<?> parent, final View view,int position, long id)
    {
        //final String item = (String) parent.getItemAtPosition(position);
        ListItem listItems=new ListItem();
        listItems=listItemsList.get(position);
        Log.d("Item:",listItems.getItemPriceValue());

        Intent selectionDetailsIntent = new Intent(ListItemExample.this, ItemSelection.class);
        selectionDetailsIntent.putExtra("listItemObject", listItems);
        startActivity(selectionDetailsIntent);
    }
}

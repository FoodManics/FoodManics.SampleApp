package com.manics.food.ListView;

import com.manics.food.ListView.ListItem;
import com.manics.food.foodmanics.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yash on 8/29/2015.
 */
public class MySimpleArrayAdapter extends ArrayAdapter<ListItem> {
    private final Context context;
    public final List<ListItem> listItemsListLocal;
    List<ListItem> listItems=new ArrayList<>();

    static class ViewHolder {
        ImageView icon;
        TextView title;
        TextView description;
        TextView price;
        int position;
    }
    public MySimpleArrayAdapter(Context context, int a, List<ListItem> listItemsList) {
        super(context, -1, listItemsList);
        this.context = context;
        this.listItemsListLocal = listItemsList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //If row is yet visible then inflates xml layout.
        View rowView=convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.activity_listview_menu, parent, false);

            // configure view holder
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.icon = (ImageView) rowView.findViewById(R.id.imageViewExp1);
            viewHolder.title = (TextView) rowView.findViewById(R.id.firstLine);
            viewHolder.description=(TextView) rowView.findViewById(R.id.secondLine);
            viewHolder.price=(TextView) rowView.findViewById(R.id.thirdLine);
            rowView.setTag(viewHolder);
        }

        // If a row is not visible anymore, reusing associated view by convertView property, passed by android system to getView methods, this helps to avoid inflating and increase performance.
        // fill data in convertView/rowView

        ViewHolder holder = (ViewHolder) rowView.getTag();
        String title = listItemsListLocal.get(position).getItemTitleValue();
        holder.title.setText(title);
        String description = listItemsListLocal.get(position).getItemDescriptionValue();
        holder.description.setText(description);
        String price = listItemsListLocal.get(position).getItemPriceValue();
        holder.price.setText(price);
        int imageId = listItemsListLocal.get(position).getItemImageResourceValue();
        holder.icon.setImageResource(imageId);

        // change the icon for Windows and iPhone
        /*String s = values[position];

        if (s.startsWith("iPhone")) {
            imageView.setImageResource(R.drawable.values[position]);
        } else {
            imageView.setImageResource(R.drawable.ic_play_light);
        }
*/
        return rowView;
    }
}


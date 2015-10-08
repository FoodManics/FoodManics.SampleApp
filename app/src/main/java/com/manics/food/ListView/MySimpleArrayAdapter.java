package com.manics.food.ListView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.manics.food.Home_Page;
import com.manics.food.ListView.ListItem;
import com.manics.food.foodmanics.R;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yash on 8/29/2015.
 */
public class MySimpleArrayAdapter extends ArrayAdapter<ListItem> {

    private final Context context;
    public final List<ListItem> listItemsListLocal;
    List<ListItem> listItems=new ArrayList<>();
    private LruCache<String, byte[]>  imageCache;

    static class ViewHolder {
        ImageView icon;
        TextView title;
        TextView description;
        TextView price;
        //int position;
    }

    public MySimpleArrayAdapter(Context context, int a, List<ListItem> listItemsList) {
        super(context, -1, listItemsList);
        this.context = context;
        this.listItemsListLocal = listItemsList;


        final int maxMemory=(int)(Runtime.getRuntime().maxMemory()/1024);
        Log.d("Debug_ListArrayAdaptr_1","Maxmemory size"+maxMemory);
        final int cacheSize=maxMemory/8;
        imageCache=new LruCache<>(cacheSize);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //If row is yet visible then inflates xml layout.
        View rowView = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.activity_listview_menu, parent, false);

            // configure view holder
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.icon = (ImageView) rowView.findViewById(R.id.imageViewExp1);
            viewHolder.title = (TextView) rowView.findViewById(R.id.firstLine);
            viewHolder.description = (TextView) rowView.findViewById(R.id.secondLine);
            viewHolder.price = (TextView) rowView.findViewById(R.id.thirdLine);
            rowView.setTag(viewHolder);
        }

        // If a row is not visible anymore, reusing associated view by convertView property, passed by android system to getView methods, this helps to avoid inflating and increase performance.
        // fill data in convertView/rowView

        ViewHolder holder = (ViewHolder) rowView.getTag();
        String title = listItemsListLocal.get(position).getItemTitleValue();
        holder.title.setText(title);
        String description = listItemsListLocal.get(position).getItemDescriptionValue();
        holder.description.setText(description);
        String price = "$" + listItemsListLocal.get(position).getItemPriceValue();
        holder.price.setText(price);
        //int imageId = listItemsListLocal.get(position).getItemImageResourceValue();    Used for static/local images loading
        //holder.icon.setImageResource(imageId);

        byte[] imageBitmapCache;

        //First if to use Cache memory, second if is to get direct from memory, else call http url to load from web
        if (imageCache.get(listItemsListLocal.get(position).getItemTitleValue()) != null)
        {
            imageBitmapCache=imageCache.get(listItemsListLocal.get(position).getItemTitleValue());
            Bitmap imageBitmap = BitmapFactory.decodeByteArray(imageBitmapCache, 0, imageBitmapCache.length);
            holder.icon.setImageBitmap(imageBitmap);//Setting image to imageview
            Log.d("Debug_ListArrayAdaptr_2", "From Cache, not calling http async task to Image loading");
        }
        else if ((listItemsListLocal.get(position).getImageBitmap() != null)){
            imageBitmapCache=listItemsListLocal.get(position).getImageBitmap();
            Bitmap imageBitmap = BitmapFactory.decodeByteArray(imageBitmapCache, 0, imageBitmapCache.length);
            holder.icon.setImageBitmap(imageBitmap);//Setting image to imageview
            Log.d("Debug_ListArrayAdaptr_2", "From Memory, not calling http async task to Image loading");
        }else{
            ListAndView listandview=new ListAndView();
            listandview.listItem=listItemsListLocal.get(position);
            listandview.viewHolder=holder;
            ImageLoader imageLoader=new ImageLoader();
            imageLoader.execute(listandview);
        }

        //Log.d("BitMap check, List Adap", ""+imageBitmap.getByteCount());
        // change the icon for Windows and iPhone
        /*String s = values[position];*/

        return rowView;
    }

    class ListAndView{
        private ListItem listItem;
        private ViewHolder viewHolder;
        private byte[] bitmapArray;
    }

    private class ImageLoader extends AsyncTask<ListAndView,Void,ListAndView>{

        @Override
        protected ListAndView doInBackground(ListAndView... params) {

            ListAndView container=params[0];
            ListItem listItem=container.listItem;
            InputStream in_stream=null;
            //ByteArrayOutputStream out_stream=null;
            byte[] bytes1;

            try {
                String finalURL = Home_Page.BaseURL + listItem.getItemImageResourceValue();
                Log.d("ImageURLs:", finalURL);
                in_stream = (InputStream) new URL(finalURL).getContent();

                //1st Approach :: Compressing and converting to Byte Array saves space, image byte arrays have half the size of the other method used, below
                        /*Bitmap bitmap;
                        bitmap= BitmapFactory.decodeStream(in_stream);
                        Log.d("Debug_HomePage_20", "CHeckOutputByteArrayExceptionBefore");
                        out_stream = new ByteArrayOutputStream();
                        Log.d("Debug_HomePage_20", "CHeckOutputByteArrayExceptionAfter");
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out_stream);
                        byte[] bytes = out_stream.toByteArray();
                        //item.setImageBitmap(bitmap);*/


                //2nd approach:
                bytes1= IOUtils.toByteArray(in_stream);
                Log.d("Debug_OStreamBitSize:", "" + bytes1.length);
                //Log.d("Debug_CompressBitSize:", "" + bytes1.length);


                in_stream.close();
                container.bitmapArray=bytes1;
                listItem.setImageBitmap(bytes1);  //saving image to list in memory, for reload on request/scrolling.
                return container;
                //out_stream.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
            finally{
                try {
                    if(in_stream != null) {
                        in_stream.close();
                    }
                    /*if(out_stream!=null){
                          out_stream.close();
                      }*/
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(ListAndView result)
        {
            byte [] byteArr=result.bitmapArray;
            Bitmap imageBitmap= BitmapFactory.decodeByteArray(byteArr,0,byteArr.length);
            result.viewHolder.icon.setImageBitmap(imageBitmap);
            imageCache.put(result.listItem.getItemTitleValue(),result.bitmapArray); // saving image in cache, to fast reload from cache, as it's faster than memory
        }
    }
}



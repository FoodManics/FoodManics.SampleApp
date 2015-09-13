package com.manics.food.NavigationDrawer;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.manics.food.foodmanics.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by Yash on 9/3/2015.
 */
public class NavigationBarRecyclerViewAdapter extends RecyclerView.Adapter<NavigationBarRecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private final LayoutInflater inflater;
    private List<NavigationBarRawData> navigationBarDataList= Collections.emptyList();
    private final View.OnClickListener mOnClickListener=new MyOnClickListener();

    NavigationBarRecyclerViewAdapter(Context context, List<NavigationBarRawData> navigationBarDataList){
        inflater = LayoutInflater.from(context);
        this.navigationBarDataList=navigationBarDataList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.recyclerview_rowitem,parent,false);
        //view.setOnClickListener(mOnClickListener);
        MyViewHolder myviewHolder=new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                mOnClickListener.onClick(v);
            }
        });

        return myviewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        NavigationBarRawData obj=navigationBarDataList.get(position);
        holder.rowItemText_recyclerView.setText(obj.getNavTitle());
        holder.rowItemImage_recyclerView.setImageResource(obj.getImageID());

       /* holder.rowItemImage_recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Clicked on Image#"+position,Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return navigationBarDataList.size();
    }

    class MyOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            Log.d("RecycleOnCLick", "RecycleViewOnclickListener");
        }

    }
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView rowItemText_recyclerView;
        ImageView rowItemImage_recyclerView;
        //View itemView1;

        public MyViewHolder(View itemView){
            super(itemView);

            rowItemText_recyclerView=(TextView)itemView.findViewById(R.id.rowItemText_recyclerView);
            rowItemImage_recyclerView=(ImageView)itemView.findViewById(R.id.rowItemImage_recyclerView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("RecycleOnCLick","RecycleViewOnclickListener");
                    Toast.makeText(context,"Clicked on Image#"+getAdapterPosition(),Toast.LENGTH_SHORT).show();

                }
            });
            rowItemImage_recyclerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("RecycleOnCLick","RecycleViewOnclickListener");
                    Toast.makeText(context,"Clicked on Image#"+getAdapterPosition(),Toast.LENGTH_SHORT).show();

                }
            });


            //itemView1=itemView;
        }
        @Override
        public void onClick(View view) {
            Log.d("RecycleOnCLick", "RecycleViewOnclickListener");
        }

       /* @Override
        public void onClick(View itemView) {
            Log.d("RecycleOnCLick","RecycleViewOnclickListener");
            Toast.makeText(context,"Clicked on Image#"+getAdapterPosition(),Toast.LENGTH_SHORT).show();
        }*/


    }
}

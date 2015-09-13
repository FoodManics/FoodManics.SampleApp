package com.manics.food.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.manics.food.ItemSelection;
import com.manics.food.ListView.ListItem;
import com.manics.food.ListView.MySimpleArrayAdapter;
import com.manics.food.foodmanics.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuickManuPreview_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuickManuPreview_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuickManuPreview_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<ListItem> listItemsList=new ArrayList<>();
    private ListItem listItems=new ListItem();

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuickManuPreview_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuickManuPreview_Fragment newInstance(String param1, String param2) {
        QuickManuPreview_Fragment fragment = new QuickManuPreview_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public QuickManuPreview_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        if(container==null){
            return null;
        }
        // Inflate the layout for this fragment

        View rootView= inflater.inflate(R.layout.fragment_quick_manu_preview_, container, false);

        int imageId;
        imageId = R.drawable.samosa;
        listItems.setItemImageResourceValue(imageId);
        listItems.setItemTitleValue("Item1");
        listItems.setItemDescriptionValue("Item1 Description..." + "\n" + "...goes here");
        listItems.setItemPriceValue("$4.00");
        listItemsList.add(listItems);

        listItems = new ListItem();
        imageId = R.drawable.burger;
        listItems.setItemImageResourceValue(imageId);
        listItems.setItemTitleValue("Item2");
        listItems.setItemDescriptionValue("Item2 Description..." + "\n" + "...goes here");
        listItems.setItemPriceValue("$10.00");
        listItemsList.add(listItems);

        listItems = new ListItem();
        imageId = R.drawable.pizza;
        listItems.setItemImageResourceValue(imageId);
        listItems.setItemTitleValue("Item3");
        listItems.setItemDescriptionValue("Item3 Description..." + "\n" + "...goes here");
        listItems.setItemPriceValue("$18.00");
        listItemsList.add(listItems);

        listItems = new ListItem();
        imageId = R.drawable.chickenwings;
        listItems.setItemImageResourceValue(imageId);
        listItems.setItemTitleValue("Item4");
        listItems.setItemDescriptionValue("Item4 Description..." + "\n" + "...goes here");
        listItems.setItemPriceValue("$8.00");
        listItemsList.add(listItems);

        listItems = new ListItem();
        imageId = R.drawable.pancake;
        listItems.setItemImageResourceValue(imageId);
        listItems.setItemTitleValue("Item5");
        listItems.setItemDescriptionValue("Item5 Description..." + "\n" + "...goes here");
        listItems.setItemPriceValue("$6.00");
        listItemsList.add(listItems);

        listItems = new ListItem();
        imageId = R.drawable.springrolls;
        listItems.setItemImageResourceValue(imageId);
        listItems.setItemTitleValue("Item6");
        listItems.setItemDescriptionValue("Item6 Description..." + "\n" + "...goes here");
        listItems.setItemPriceValue("$10.00");
        listItemsList.add(listItems);

        listItems = new ListItem();
        imageId = R.drawable.wraps;
        listItems.setItemImageResourceValue(imageId);
        listItems.setItemTitleValue("Item7");
        listItems.setItemDescriptionValue("Item7 Description..." + "\n" + "...goes here");
        listItems.setItemPriceValue("$12.00");
        listItemsList.add(listItems);

        listItems = new ListItem();
        imageId = R.drawable.chickenwings;
        listItems.setItemImageResourceValue(imageId);
        listItems.setItemTitleValue("Item8");
        listItems.setItemDescriptionValue("Item8 Description..." + "\n" + "...goes here");
        listItems.setItemPriceValue("$6.00");
        listItemsList.add(listItems);

        listItems = new ListItem();
        imageId = R.drawable.burger;
        listItems.setItemImageResourceValue(imageId);
        listItems.setItemTitleValue("Item9");
        listItems.setItemDescriptionValue("Item9 Description..." + "\n" + "...goes here");
        listItems.setItemPriceValue("$7.00");
        listItemsList.add(listItems);

        ListView listview = (ListView)rootView.findViewById(R.id.listMenu);

        final MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this.getActivity(), android.R.layout.simple_list_item_single_choice, listItemsList);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                        listItemSelectListener(parent, view, position, id);
                    }
                }
        );
        return rootView;

    }

    public void listItemSelectListener(AdapterView<?> parent, final View view,int position, long id)
    {
        //final String item = (String) parent.getItemAtPosition(position);
        ListItem listItems=new ListItem();
        listItems=listItemsList.get(position);
        Log.d("Item:", listItems.getItemPriceValue());

        Intent selectionDetailsIntent = new Intent(this.getActivity().getBaseContext(), ItemSelection.class);
        selectionDetailsIntent.putExtra("listItemObject", listItems);
        startActivity(selectionDetailsIntent);
    }





    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}

package com.manics.food.foodmanics.maps;

import android.content.Intent;
import android.content.res.Configuration;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.manics.food.foodmanics.R;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

public class Location extends AppCompatActivity {

    private GoogleMap mMap;
    View searchTextView;
    LatLngBounds truckLocations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        Toolbar toolbar=(Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);


        setUpMapIfNeeded();
    }


 @Override
    protected void onResume() {
     super.onResume();
    }


    public void onMapSearch(View view) {
        EditText searchText = (EditText) findViewById(R.id.Text_Map_Search);
        String searchString = "";
        searchString = searchText.getText().toString();
        List<Address> addressList = new Vector<>();
        if (searchString != null && (!searchString.equals(""))) {
            Geocoder geocoder = new Geocoder(this);
            try {
                Log.d("LocationMap", "Inside MapTry");
                addressList = geocoder.getFromLocationName(searchString, 1);
            } catch (IOException e) {

                e.printStackTrace();
            }
            if(addressList.size() > 0){
                Address address = addressList.get(0);
                LatLng latlng = new LatLng(address.getLatitude(), address.getLongitude());
                mMap.addMarker(new MarkerOptions().position(latlng).title("Foodmanics HQ"));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 10));
            }
           }

    }

    public void onClickZoomIn(View view) {
        if (view.getId() == R.id.map_zoomin_button){
            mMap.animateCamera(CameraUpdateFactory.zoomIn());

        }else if(view.getId() == R.id.map_zoomout_button){
            mMap.animateCamera(CameraUpdateFactory.zoomOut());
        }

    }

    public void onMapTypeClick(){

        if(mMap.getMapType()==GoogleMap.MAP_TYPE_NORMAL){
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        }
        else{
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }

    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    public void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.gmaplocation))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        //mMap.setMyLocationEnabled(true);
        //mMap.animateCamera(CameraUpdateFactory.newLatLng(latlng1));

        mMap.setPadding(0,0,300,0);
        int orientation = getResources().getConfiguration().orientation;

        Log.d("MapScreenOrientation",""+orientation);
        switch (orientation){
            case Configuration.ORIENTATION_LANDSCAPE: {
                truckLocations = new LatLngBounds(
                        new LatLng(38.908465, -77.335967), new LatLng(38.972924, -77.250033));
                break;
            }
            case Configuration.ORIENTATION_PORTRAIT:
                truckLocations = new LatLngBounds(
                        new LatLng(38.908465, -77.3305967), new LatLng(38.972924, -77.220033));
                break;
        }


        // Set the camera to the greatest possible zoom level that includes the bounds
           mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
               @Override
               public void onMapLoaded() {

                   mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(truckLocations, 5));
                   Log.d("TruckPositionsBlock", "" + truckLocations.getCenter());
                   //mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(truckLocations, 200, 400, 25));
               }
           });
        LatLng latlng1_home=new LatLng(38.922660, -77.238160);
        LatLng latlng2_ofc=new LatLng(38.949287, -77.325100);

        final Marker marker1=mMap.addMarker(new MarkerOptions().position(latlng1_home).title("FoodManics Tysons"));
        final Marker marker2=mMap.addMarker(new MarkerOptions().position(latlng2_ofc).title("FoodManics Reston"));


        /*CameraPosition cameraPosition=new CameraPosition.Builder()
                .target(latlng1_home)
                .zoom(10)
                .build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
   */     //mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                String mapURI = "";
                Uri gmmIntentUri = Uri.parse("");
                if (marker.getId().equals(marker1.getId())) {
                    mapURI = "geo:0,0?q=1717 Gosnell Road, Tysons, Virginia&z=10";
                } else if (marker.getId().equals(marker2.getId())) {
                    mapURI = "geo:0,0?q=1771 Business Center Drive, Reston, Virginia&z=10";
                }

                Log.d("Marker1 ID", "" + marker1.getId());
                Log.d("Marker1 ID", "" + marker2.getId());
                Log.d("Local Marker ID", "" + marker.getId());

                gmmIntentUri = Uri.parse(mapURI);
                /*Turn by Turn Navigation
                Uri gmmIntentUri = Uri.parse("google.navigation:q=1717+Gosnell+Road,+Tysons+Virginia");
                */
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");

                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_location, menu);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intent;

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.SearchMap:{
                EditText editText=(EditText)findViewById(R.id.Text_Map_Search);
                Button mapSearchButton=(Button)findViewById(R.id.search_map_button);
                editText.setVisibility(View.VISIBLE);
                mapSearchButton.setVisibility(View.VISIBLE);

                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,//Width
                        ViewGroup.LayoutParams.WRAP_CONTENT);//Height

                param.weight=1.0f;
                editText.setLayoutParams(param);

                param.weight=4.0f;
                mapSearchButton.setLayoutParams(param);
                return true;
            }
            case R.id.ChangeMapView:{
                onMapTypeClick();
                return true;
            }
            case R.id.contctus:{
                Toast.makeText(this, "Please call us at 911911911 or visit our website www.khamosh.com", Toast.LENGTH_LONG).show();
                return true;
            }
            case R.id.feedback:{
                return true;
            }
            case R.id.action_settings:
            {
                return true;
            }
            //Represents the back button ID on each tool bar, default provided by android framework
            case R.id.home:{
                NavUtils.navigateUpFromSameTask(this);
            }
        }
        return super.onOptionsItemSelected(item);
    }
}

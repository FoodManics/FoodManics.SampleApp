package com.manics.food.Common;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import com.manics.food.foodmanics.R;
import android.net.Uri;

/**
 * Created by Yash on 8/30/2015.
 */
public class MenuCommon extends AppCompatActivity{

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_common, menu);
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
        Intent intent,chooser=null;


        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_settings:
            {
                return true;
            }
            case R.id.contctus:{
                Toast.makeText(this, "Please call us at 911911911 or visit our website www.khamosh.com", Toast.LENGTH_LONG).show();
                intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:38.9197153,-77.2386953"));
                chooser=Intent.createChooser(intent,"Launch Maps app");
                startActivity(chooser);
                return true;
            }
            case R.id.aboutus:{
                AlertDialog.Builder a_builder = new AlertDialog.Builder(this);
                a_builder.setMessage("We are a bunch of geeks!!!")
                        .setCancelable(true).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = a_builder.create();
                //alert.create();
                alert.setTitle("AboutUs");
                alert.show();
                return true;
            }
            case R.id.feedback:{
                return true;
            }
            //Represents the back button ID on each tool bar, default provided by android framework
            case R.id.home:{
                NavUtils.navigateUpFromSameTask(this);
            }
            case R.id.nextPageIcon:{
                if(this.getLocalClassName().equals("com.manics.food.Home_Page")){
                    //startActivity(new Intent(this, com.manics.food.ItemSelection.class));
                }else if(this.getLocalClassName().equals("com.manics.food.ItemSelection")){
                    startActivity(new Intent(this,com.manics.food.PaymentCartIntegration.PaymentPage.class));
                }else if(this.getLocalClassName().equals("com.manics.food.PaymentPage")){
                    //startActivity(new Intent());
                }
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        Log.d("ContextMenu Debug", "In Context Menu");
        inflater.inflate(R.menu.menu_contextual, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.rating_contextual:
                //editNote(info.id);
                return true;
            case R.id.comment_contextual:
                //deleteNote(info.id);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

}

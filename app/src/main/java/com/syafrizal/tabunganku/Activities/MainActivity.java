package com.syafrizal.tabunganku.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.syafrizal.tabunganku.Constant;
import com.syafrizal.tabunganku.Fragments.TabunganAdapter;
import com.syafrizal.tabunganku.Fragments.TabunganFragment;
import com.syafrizal.tabunganku.Models.Tabungan;
import com.syafrizal.tabunganku.R;

public class MainActivity extends AppCompatActivity {
    Fragment fragment;
    SharedPreferences preferences;


    private void addFragment(String tujuan) {

        if (tujuan.equalsIgnoreCase("tabungan")) {
            fragment = new TabunganFragment();

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        preferences = getSharedPreferences(Constant.USER_SP,MODE_PRIVATE);
        boolean firstrun = preferences.getBoolean(Constant.FIRST_RUN,true);

        addFragment("tabungan");
        //cek pertama kali masuk
        if (firstrun){
            Intent intent = new Intent(this,EmptyActivity.class);
            startActivity(intent);
            preferences.edit().putBoolean(Constant.FIRST_RUN,false).apply();
        }



    }

    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        //Refresh your stuff here

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        preferences = getSharedPreferences(Constant.USER_SP,MODE_PRIVATE);
        boolean firstrun = preferences.getBoolean(Constant.FIRST_RUN,true);
        addFragment("tabungan");
        //cek pertama kali masuk
        if (firstrun){
            Intent intent = new Intent(this,EmptyActivity.class);
            startActivity(intent);
            preferences.edit().putBoolean(Constant.FIRST_RUN,false).apply();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_tambah) {
            Intent intent = new Intent(this,TambahActivity.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }



}

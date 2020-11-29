package com.example.erase;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Main extends AppCompatActivity {

    Fragment repair = new SellFragment();
    int PERMISSION_ID = 44;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i = getIntent();
        final String user_id = i.getStringExtra("EMail");
        BottomNavigationView btw = findViewById(R.id.bottom_navigation);
        btw.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {

                int id = item.getItemId();
                if(id==R.id.store){
                        if(checkPermissions())
                        {
                            if(isLocationEnabled())
                            startActivity(new Intent(getApplicationContext(), MapActivity.class));
                            else
                                Toast.makeText(getApplicationContext(), "Turn On Location", Toast.LENGTH_LONG).show();
                        }
                        else
                            requestPermissions();
                }
                else if(id==R.id.repair){
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    Bundle b = new Bundle();
                    b.putString("ID",user_id);
                    repair.setArguments(b);
                    ft.replace(R.id.container, repair);
                    ft.commit();
                    /* if(savedInstanceState!=null)
                     {
                         repair =  fm.getFragment(savedInstanceState,"repair");
                     }
                     else {
                         repair = new SellFragment();
                         FragmentTransaction ft = fm.beginTransaction();
                         ft.replace(R.id.container, repair);
                         ft.commit();
                     }*/
                }
            }
        });
    }

    /*@Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "repair",repair);
    }*/

    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID);
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        assert locationManager != null;
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.action_bar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.log_out) {
            Toast.makeText(this, FirebaseAuth.getInstance().getCurrentUser().getEmail(),Toast.LENGTH_LONG).show();
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(Main.this, Login.class));
            return true;
        }
        else
            return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed()
    {
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setTitle("Erase");
        builder.setMessage("Do you want to exit the app?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent a = new Intent(Intent.ACTION_MAIN);
                a.addCategory(Intent.CATEGORY_HOME);
                a.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(a);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }});

        AlertDialog dialog= builder.create();
        dialog.show();

    }
}

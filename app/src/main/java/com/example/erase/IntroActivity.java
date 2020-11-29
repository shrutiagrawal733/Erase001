package com.example.erase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class IntroActivity extends AppCompatActivity {

    private ViewPager screenPager;
    IntroViewPagerAdapter introViewPagerAdapter;
    TabLayout tabIndicator;
    Button Next;
    int position=0;
    Button btn_GetStarted;
    Animation btn_anim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //make the activity on full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_intro);

        //when this activity is about to launch we need to check if its opened before or not
        if(restorePrefData())
        {
            Intent i = new Intent(getApplicationContext(),Login.class);
            startActivity(i);
            finish();
        }


        //ini views
        Next = findViewById(R.id.btn_next);
        btn_GetStarted = findViewById(R.id.btn_get_started);
        tabIndicator =findViewById(R.id.tab_indicator);
        btn_anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_animation);


        //hide the action bar
        Objects.requireNonNull(getSupportActionBar()).hide();

        //fill list screen
        final List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("Recycle"," Find nearest e-waste recycling collection centers ",R.drawable.img1));
        mList.add(new ScreenItem("Sell and Buy"," Sell and purchase second hand electronic products",R.drawable.img2));
        mList.add(new ScreenItem("Repair","Easily find contact details of nearest repair shops",R.drawable.img3));


        //setup Viewpager
        screenPager = findViewById(R.id.screen_viewpager);
        introViewPagerAdapter = new IntroViewPagerAdapter(this,mList);
        screenPager.setAdapter(introViewPagerAdapter);

        //setup tablayout with viewpager
        tabIndicator.setupWithViewPager(screenPager);


        //next button click listener
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position=screenPager.getCurrentItem();
                if(position<mList.size())
                {
                    position++;
                    screenPager.setCurrentItem(position);
                }

                if(position==mList.size()-1)                //when we reach the last screeen
                {
                    loadLastScreen();

                }
            }
        });

        // tablayout add change listener
        tabIndicator.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==mList.size()-1)
                    loadLastScreen();
                else
                {
                    Next.setVisibility(View.VISIBLE);
                    btn_GetStarted.setVisibility(View.INVISIBLE);
                    tabIndicator.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //Get Started button click listener
        btn_GetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open Login Activity
                Intent i = new Intent(getApplicationContext(),Login.class);
                startActivity(i);
                //also we need to save a boolean value to storage so when the user runs the app we could know that he is already
                //checked the launch activity.

                savePrefsData();
                finish();
            }
        });

    }

    private boolean restorePrefData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        return pref.getBoolean("isLaunchOpened",false);
    }

    private void savePrefsData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor= pref.edit();
        editor.putBoolean("isLaunchOpened",true);
        editor.commit();

    }

    // show the GETSTARTED button and hide the indicator and the next button
    private void loadLastScreen()
    {
        Next.setVisibility(View.INVISIBLE);
        btn_GetStarted.setVisibility(View.VISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);

        //setup Animation
        btn_GetStarted.setAnimation(btn_anim);
    }
}

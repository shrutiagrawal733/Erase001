package com.example.erase;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

public class SellFragment extends Fragment {

    String id;
    DBManager dbManager;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View  view = inflater.inflate(R.layout.sell_fragment, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);

        dbManager = new DBManager(getContext());
        dbManager.open();
        Bundle bundle = getArguments();

        assert bundle != null;
        id = bundle.getString("ID");

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        SharedPreferences pref = Objects.requireNonNull(getContext()).getSharedPreferences("myPrefs",MODE_PRIVATE);
        String dropbox = pref.getString("dropbox",null);
        String gadget= pref.getString("gadget",null);
        boolean t = pref.getBoolean("Activity",false);

        //Toast.makeText(getContext(), dropbox+gadget+t, Toast.LENGTH_LONG).show();
        if(t)
        {
            int temp = (new Random()).nextInt();
            dbManager.insert(id, dropbox ,gadget,temp);
            SharedPreferences.Editor editor= pref.edit();
            editor.putBoolean("Activity",false);
            editor.apply();
        }

        ArrayList<DataModel> data = new ArrayList<>();

        Cursor cursor = dbManager.fetch(id);
        int a = cursor.getColumnIndex("Column_date");
        int b = cursor.getColumnIndex("DropBox");
        int c = cursor.getColumnIndex("Gadget");
        int d = cursor.getColumnIndex("Points");

        if(cursor.getCount()>0)
        {
            do {
                data.add(new DataModel(cursor.getString(a), cursor.getString(b), cursor.getString(c), Integer.parseInt(cursor.getString(d))));
            } while (cursor.moveToPrevious());
        }


        RecyclerView.Adapter adapter = new CustomAdapter(data);
        recyclerView.setAdapter(adapter);

        Button button = view.findViewById(R.id.sellButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Form.class));
            }

        });

        return view;
    }

   /* @Override
    public void onResume() {
        super.onResume();

        SharedPreferences pref = Objects.requireNonNull(getContext()).getSharedPreferences("myPrefs",MODE_PRIVATE);
        String dropbox = pref.getString("dropbox",null);
        String gadget= pref.getString("gadget",null);
        boolean t = pref.getBoolean("Activity",false);

        if(t)
        {
            int temp = (new Random()).nextInt();
            dbManager.insert(id, dropbox ,gadget,temp);
            SharedPreferences.Editor editor= pref.edit();
            editor.putBoolean("Activity",false);
        }

        ArrayList<DataModel> data = new ArrayList<>();

        Cursor cursor = dbManager.fetch(id);
        int a = cursor.getColumnIndex("Column_date");
        int b = cursor.getColumnIndex("DropBox");
        int c = cursor.getColumnIndex("Gadget");
        int d = cursor.getColumnIndex("Points");


        /* do {
        //   data.add(new DataModel(cursor.getString(a), cursor.getString(b), cursor.getString(c), Integer.parseInt(cursor.getString(d))));
        } while (cursor.moveToPrevious());

        RecyclerView.Adapter adapter = new CustomAdapter(data);
        recyclerView.setAdapter(adapter);

    }*/
}

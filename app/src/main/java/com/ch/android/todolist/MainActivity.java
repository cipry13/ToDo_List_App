package com.ch.android.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Item> items = new ArrayList<Item>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        ListView listView = findViewById(R.id.item_list);
        items.add(new Item("Ana"));
        items.add(new Item("are"));
        items.add(new Item("mere"));

        ItemAdapter adapter = new ItemAdapter(this, items, R.layout.list_item);


        listView.setAdapter(adapter);

        Log.v("Main Activityyyyyyyy: ",items.get(0).getText());

        Button addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDialogFragment mdf = new MyDialogFragment();
                mdf.show(getSupportFragmentManager(), "My Dialog Fragment");
//                items.add(new Item(mdf.getUserInput()));

            }
        });

    }

    public void addItem(String x){
        items.add(new Item(x));
    }

}
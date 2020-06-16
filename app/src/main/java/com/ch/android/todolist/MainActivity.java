package com.ch.android.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    ItemAdapter adapter;
    ArrayList<Item> items = new ArrayList<Item>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        ListView listView = findViewById(R.id.item_list);


 /*       for(int i = 0; i < 5; i++) {
            items.add(new Item("Task " + i));
        }*/

        loadData();


        adapter = new ItemAdapter(this, R.layout.list_item, items);
        listView.setAdapter(adapter);



        FloatingActionButton addButton = findViewById(R.id.fab);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDialogFragment mdf = new MyDialogFragment();
                mdf.show(getSupportFragmentManager(), "My Dialog Fragment");
                adapter.notifyDataSetChanged();
            }
        });

    }

    public void addItem(String x){
        items.add(new Item(x));
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(items);
        editor.putString("task list", json);
        editor.apply();
    }
    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<Item>>() {}.getType();
        items = gson.fromJson(json, type);
        if (items == null) {
            items = new ArrayList<>();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.more_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.select_all:
                Toast.makeText(this, "All tasks selected", Toast.LENGTH_SHORT).show();
                selectAll();
                return true;
            case R.id.deselect_all:
                Toast.makeText(this, "All tasks deselected", Toast.LENGTH_SHORT).show();
                deselectAll();
                return true;
            case R.id.delete_all_selected:
                Toast.makeText(this, "Selected tasks deleted", Toast.LENGTH_SHORT).show();
                deleteAllSelected();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void selectAll(){
        for(Item i : items){
            i.setIsSelected(true);
        }
        adapter.notifyDataSetChanged();
    }

    private void deselectAll(){
        for(Item i : items){
            i.setIsSelected(false);
        }
        adapter.notifyDataSetChanged();
    }

    private void deleteAllSelected(){
       for(Iterator<Item> itemIterator = items.iterator(); itemIterator.hasNext();){
           Item item = itemIterator.next();
           if(item.getIsSelected()){
               itemIterator.remove();
           }
       }
       adapter.notifyDataSetChanged();
    }
}
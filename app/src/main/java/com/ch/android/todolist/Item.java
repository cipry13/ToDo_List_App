package com.ch.android.todolist;

public class Item {
    String name;
    boolean isSelected = false;

    public Item(){

    }

    public Item(String name){
        this.name = name;
    }

    public String getItemName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setIsSelected(boolean isSelected){
        this.isSelected = isSelected;
    }

    public boolean getIsSelected(){
        return isSelected;
    }

}

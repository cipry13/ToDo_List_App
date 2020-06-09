package com.ch.android.todolist;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;

public class ItemAdapter extends ArrayAdapter<Item> {

    Context mContext;
    LayoutInflater inflater;
    ArrayList<Item> items;

    public ItemAdapter(Context context,  int resource, ArrayList<Item> items){
        super(context, resource);

        this.mContext = context;
        this.items = items;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.tv = convertView.findViewById(R.id.item_name_text_view);
            holder.cb = convertView.findViewById(R.id.checkbox);
            holder.editIV = convertView.findViewById(R.id.iv_edit);
            holder.deleteIV = convertView.findViewById(R.id.iv_delete);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Item item = items.get(position);
        holder.cb.setChecked(item.getIsSelected());
        holder.tv.setText(item.getItemName());



        holder.cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.cb.isChecked()) {
                    items.get(position).setIsSelected(true);
                    holder.tv.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    items.get(position).setIsSelected(false);
                    holder.tv.setPaintFlags(holder.tv.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                }
            }
        });
/*
        holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.cb.isChecked()) {
                    items.get(position).setIsSelected(true);
                    holder.tv.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    items.get(position).setIsSelected(false);
                    holder.tv.setPaintFlags(holder.tv.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));

                }

            }

        });

*/
        holder.editIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
                builder1.setMessage("Modify task");
                builder1.setCancelable(true);
                final EditText input = new EditText(mContext);
                input.setText(items.get(position).getItemName());
                builder1.setView(input);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                items.get(position).setName(input.getText().toString());
                                notifyDataSetChanged();
                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });



        holder.deleteIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
                builder1.setMessage("Are you sure you want to delete this task? \n" + items.get(position).getItemName());
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                items.remove(position);
                                holder.tv.setPaintFlags(holder.tv.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
//                                Log.i("ItemAdapter: ",items.get(position).getItemName() + " + position:" + position + " + isChecked: " + items.get(position).getIsSelected());
                                notifyDataSetChanged();
                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });


        return convertView;
    }
    @Override
    public int getCount() {
        return items.size();
    }

    private class ViewHolder {
        TextView tv;
        CheckBox cb;
        ImageView editIV, deleteIV;
    }
}

package com.ch.android.todolist;

import android.content.Context;
import android.graphics.Paint;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

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

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Item item = items.get(position);
        holder.cb.setChecked(item.isSelected());
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

        return convertView;
    }
    @Override
    public int getCount() {
        return items.size();

    }

    private class ViewHolder {
        TextView tv;
        CheckBox cb;
    }
}

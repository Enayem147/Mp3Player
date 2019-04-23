package com.example.windows10.mp3player.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.windows10.mp3player.R;
import com.example.windows10.mp3player.model.TheLoai;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends ArrayAdapter<TheLoai> {
    Context context;
    public CategoryAdapter(@NonNull Context context, int resource, @NonNull List<TheLoai> objects) {
        super(context, resource, objects);
    }

    class ViewHolder{
        TextView categoryName;
        ImageView categoryPicture;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(viewHolder == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.layout_category,null);
            viewHolder = new ViewHolder();
            viewHolder.categoryName = convertView.findViewById(R.id.txtNameCategory);
            viewHolder.categoryPicture = convertView.findViewById(R.id.imageBackgroundCategory);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        TheLoai theLoai = getItem(position);

        Picasso.get()
                .load(theLoai.getHinhTheLoai())
                .into(viewHolder.categoryPicture);

        viewHolder.categoryName.setText(theLoai.getTenTheLoai());


        return convertView;
    }
}

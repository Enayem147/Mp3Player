package com.example.windows10.mp3player.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.windows10.mp3player.R;
import com.example.windows10.mp3player.activity.MusicPlayerActivity;
import com.example.windows10.mp3player.model.BaiHat;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {
    Context context;
    ArrayList<BaiHat> listBaiHat;

    public SongAdapter(Context context, ArrayList<BaiHat> listBaiHat) {
        this.context = context;
        this.listBaiHat = listBaiHat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_song,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BaiHat baiHat = listBaiHat.get(position);
        holder.txtTen.setText(baiHat.getTenBaiHat());
        holder.txtCaSi.setText(baiHat.getCaSi());
        Picasso.get()
                .load(baiHat.getHinhBaiHat())
                .into(holder.imgSongList);
//        holder.txtIndex.setText(""+(position+1));
    }

    @Override
    public int getItemCount() {
        return listBaiHat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtTen,txtCaSi;
        ImageView imgThich,imgSongList;
        public ViewHolder(View itemView) {
            super(itemView);
            txtTen = itemView.findViewById(R.id.txtSongListName);
            txtCaSi = itemView.findViewById(R.id.txtSongListSinger);
            imgThich = itemView.findViewById(R.id.imageViewSongListLove);
            imgSongList = itemView.findViewById(R.id.imageViewSongList);

            imgThich.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   // imgThich.setImageResource(R.drawable.iconloved);
                    Drawable lovedIcon = ContextCompat.getDrawable(context,R.drawable.iconloved);
                    Drawable imgIcon = imgThich.getDrawable();

                    if(lovedIcon == imgIcon){
                        imgThich.setImageResource(R.drawable.iconlove);
                    }else{
                        imgThich.setImageResource(R.drawable.iconloved);
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,MusicPlayerActivity.class);
                    intent.putExtra("baihat",listBaiHat.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}

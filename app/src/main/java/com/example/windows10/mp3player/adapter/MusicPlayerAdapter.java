package com.example.windows10.mp3player.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.windows10.mp3player.R;
import com.example.windows10.mp3player.model.BaiHat;

import java.util.ArrayList;

public class MusicPlayerAdapter extends RecyclerView.Adapter<MusicPlayerAdapter.ViewHolder> {
    Context context;
    ArrayList<BaiHat> listBaiHat;

    public MusicPlayerAdapter(Context context, ArrayList<BaiHat> listBaiHat) {
        this.context = context;
        this.listBaiHat = listBaiHat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_music_player, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BaiHat baiHat = listBaiHat.get(position);

        holder.txtIndex.setText((position + 1)+"");
        holder.txtNameSong.setText(baiHat.getTenBaiHat());
        holder.txtSinger.setText(baiHat.getCaSi());
    }

    @Override
    public int getItemCount() {
        return listBaiHat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtIndex, txtNameSong, txtSinger;

        public ViewHolder(View itemView) {
            super(itemView);
            txtIndex = itemView.findViewById(R.id.textViewMusicPlayerIndex);
            txtNameSong = itemView.findViewById(R.id.textViewMusicPlayerNameSong);
            txtSinger = itemView.findViewById(R.id.textViewMusicPlayerSinger);
        }
    }
}

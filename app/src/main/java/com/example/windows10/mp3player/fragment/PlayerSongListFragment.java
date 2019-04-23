package com.example.windows10.mp3player.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.windows10.mp3player.R;
import com.example.windows10.mp3player.activity.MusicPlayerActivity;
import com.example.windows10.mp3player.adapter.MusicPlayerAdapter;

public class PlayerSongListFragment extends Fragment {
    View view;
    RecyclerView recyclerViewSongList;
    MusicPlayerAdapter musicPlayerAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_player_song_list,container,false);
        recyclerViewSongList = view.findViewById(R.id.recyclerViewSongList);
        if(MusicPlayerActivity.listBaiHat.size() > 0 ){
            musicPlayerAdapter = new MusicPlayerAdapter(getActivity(),MusicPlayerActivity.listBaiHat);
            recyclerViewSongList.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerViewSongList.setAdapter(musicPlayerAdapter);
        }
        return view;
    }
}

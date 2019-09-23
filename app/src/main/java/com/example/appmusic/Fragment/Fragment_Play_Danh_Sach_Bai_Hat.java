package com.example.appmusic.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.appmusic.Activity.PlayMusicActivity;
import com.example.appmusic.Adapter.PlaynhacAdpter;
import com.example.appmusic.R;

public class Fragment_Play_Danh_Sach_Bai_Hat extends Fragment {

    View view;
    RecyclerView recyclerViewplaynhac;
    PlaynhacAdpter playnhacAdpter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_play_danh_sach_bai_hat,container,false);
        recyclerViewplaynhac=view.findViewById(R.id.recyclerviewPlaybaihat);
        if(PlayMusicActivity.mangmusic.size()>0){
            playnhacAdpter=new PlaynhacAdpter(getActivity(), PlayMusicActivity.mangmusic);
            recyclerViewplaynhac.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerViewplaynhac.setAdapter(playnhacAdpter);
        }

        return view;
    }
}

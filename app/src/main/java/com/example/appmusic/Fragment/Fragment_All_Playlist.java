package com.example.appmusic.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appmusic.Activity.DanhsachbaihatActivity;
import com.example.appmusic.Adapter.PlaylistAdapter;
import com.example.appmusic.Model.Playlist;
import com.example.appmusic.R;
import com.example.appmusic.Serviec.APIService;
import com.example.appmusic.Serviec.Dataservice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_All_Playlist extends Fragment {
    View view;
    ListView lvallplaylist;
    TextView txttitleallplaylist;
    PlaylistAdapter playlistAdapter;
    ArrayList<Playlist> mangplaylist;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_all_playlist,container,false);
        lvallplaylist =view.findViewById(R.id.listviewallplaylist);
        txttitleallplaylist=view.findViewById(R.id.textviewtitleallplaylist);
        GetData();
        return view;
    }

    private void GetData() {
        Dataservice dataservice = APIService.getService();
        Call<List<Playlist>> callback = dataservice.GetAllPlaylist();
        callback.enqueue((new Callback<List<Playlist>>() {
            @Override
            public void onResponse(Call<List<Playlist>> call, Response<List<Playlist>> response) {
                mangplaylist=(ArrayList<Playlist>) response.body();
                playlistAdapter =new PlaylistAdapter(getActivity(),android.R.layout.simple_list_item_1,mangplaylist);
                lvallplaylist.setAdapter(playlistAdapter);
                //setListViewHeightBasedOnChildren(lvallplaylist);
                lvallplaylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent=new Intent(getActivity(), DanhsachbaihatActivity.class);
                        intent.putExtra("playlist",mangplaylist.get(position));
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Playlist>> call, Throwable t) {

            }
        }));
    }
}

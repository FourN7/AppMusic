package com.example.appmusic.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmusic.Adapter.DanhsachcacplaylistAdapter;
import com.example.appmusic.Model.Playlist;
import com.example.appmusic.R;
import com.example.appmusic.Serviec.APIService;
import com.example.appmusic.Serviec.Dataservice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhsachcacplaylistActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerViewdanhsachcacplaylist;
    DanhsachcacplaylistAdapter danhsachcacplaylistAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachcacplaylist);
        init();
        GetData();

    }

    private void GetData() {
        Dataservice dataservice= APIService.getService();
        Call<List<Playlist>> callback= dataservice.GetDanhsachcacPlaylist();
        callback.enqueue(new Callback<List<Playlist>>() {
            @Override
            public void onResponse(Call<List<Playlist>> call, Response<List<Playlist>> response) {
                ArrayList<Playlist> mangplaylist = (ArrayList<Playlist>) response.body();
                danhsachcacplaylistAdapter=new DanhsachcacplaylistAdapter(DanhsachcacplaylistActivity.this,mangplaylist);
                recyclerViewdanhsachcacplaylist.setLayoutManager(new GridLayoutManager(DanhsachcacplaylistActivity.this,2));
                recyclerViewdanhsachcacplaylist.setAdapter(danhsachcacplaylistAdapter);
            }

            @Override
            public void onFailure(Call<List<Playlist>> call, Throwable t) {

            }
        });
    }

    private void init() {
        toolbar=findViewById(R.id.toolbardanhsachcacplaylist);
        recyclerViewdanhsachcacplaylist=findViewById(R.id.recyclerviewdanhsachcacplaylist);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Play Lists");
        toolbar.setTitleTextColor(getResources().getColor(R.color.maudo));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}

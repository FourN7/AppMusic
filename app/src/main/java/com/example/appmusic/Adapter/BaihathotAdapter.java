package com.example.appmusic.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmusic.Activity.PlayMusicActivity;
import com.example.appmusic.Model.Baihat;
import com.example.appmusic.R;
import com.example.appmusic.Serviec.APIService;
import com.example.appmusic.Serviec.Dataservice;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaihathotAdapter extends RecyclerView.Adapter<BaihathotAdapter.ViewHolder> {

    Context context;
    ArrayList<Baihat>baihatArrayList;

    public BaihathotAdapter(Context context, ArrayList<Baihat> baihatArrayList) {
        this.context = context;
        this.baihatArrayList = baihatArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.dong_bai_hat_hot,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Baihat baihat=baihatArrayList.get(position);
        holder.txtcasi.setText(baihat.getCasi());
        holder.txtten.setText(baihat.getTenbaihat());
        Picasso.with(context).load(baihat.getHinhbaihat()).into(holder.imghinh);
    }

    @Override
    public int getItemCount() {
        return baihatArrayList.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder{
       TextView txtten,txtcasi;
       ImageView imghinh ,imgluottich;
       public ViewHolder(@NonNull View itemView) {
           super(itemView);
           txtten=itemView.findViewById(R.id.textviewtenbaihathot);
           txtcasi=itemView.findViewById(R.id.textviewcasibaihathot);
           imghinh=itemView.findViewById(R.id.imageviewbaihathot);
           imgluottich=itemView.findViewById(R.id.imageviewluotthich);
           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent= new Intent(context, PlayMusicActivity.class);
                   intent.putExtra("cakhuc",baihatArrayList.get(getPosition()));
                   context.startActivity(intent);
               }
           });
           imgluottich.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                    imgluottich.setImageResource(R.drawable.iconloved);
                   Dataservice dataservice= APIService.getService();
                   Call<String> callback=dataservice.UpdateLuotThich("1",baihatArrayList.get(getPosition()).getIdbaihat());
                   callback.enqueue(new Callback<String>() {
                       @Override
                       public void onResponse(Call<String> call, Response<String> response) {
                           String ketqua=response.body();
                           if(ketqua.equals("Success")){
                               Toast.makeText(context,"Đã thích",Toast.LENGTH_SHORT).show();
                           }else {
                               Toast.makeText(context,"Loi!!",Toast.LENGTH_SHORT).show();
                           }
                       }

                       @Override
                       public void onFailure(Call<String> call, Throwable t) {

                       }
                   });
                   imgluottich.setEnabled(false);
               }
           });
       }


   }
}

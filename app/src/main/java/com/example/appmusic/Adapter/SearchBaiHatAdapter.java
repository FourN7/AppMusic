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

public class SearchBaiHatAdapter extends RecyclerView.Adapter<SearchBaiHatAdapter.ViewHolder> {
    Context context;
    ArrayList<Baihat> mangbaihat;

    public SearchBaiHatAdapter(Context context, ArrayList<Baihat> mangbaihat) {
        this.context = context;
        this.mangbaihat = mangbaihat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.dong_search_bai_hat,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Baihat baihat =mangbaihat.get(position);
        holder.txtTenbaihat.setText(baihat.getTenbaihat());
        holder.txtCasi.setText(baihat.getCasi());
        Picasso.with(context).load(baihat.getHinhbaihat()).into(holder.imagebaihat);
    }

    @Override
    public int getItemCount() {
        return mangbaihat.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenbaihat,txtCasi;
        ImageView imagebaihat,imageluotthich;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenbaihat=itemView.findViewById(R.id.textviewsearchtenbaihat);
            txtCasi=itemView.findViewById(R.id.textviewsearchcasi);
            imagebaihat=itemView.findViewById(R.id.imageviewSearchbaihat);
            imageluotthich=itemView.findViewById(R.id.imgviewseachluotthich);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, PlayMusicActivity.class);
                    intent.putExtra("cakhuc",mangbaihat.get(getPosition()));
                    context.startActivity(intent);
                }
            });
            imageluotthich.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageluotthich.setImageResource(R.drawable.iconloved);
                    Dataservice dataservice= APIService.getService();
                    Call<String>callback=dataservice.UpdateLuotThich("1",mangbaihat.get(getPosition()).getIdbaihat());
                    callback.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String ketqua =response.body();
                            if(ketqua.equals("Success")){
                                Toast.makeText(context,"Đã thích",Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(context,"Lỗi",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                    imageluotthich.setEnabled(false);
                }
            });
        }
    }
}

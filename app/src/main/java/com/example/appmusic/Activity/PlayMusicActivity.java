package com.example.appmusic.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.appmusic.Adapter.ViewPagerPlaylistnhacAdapter;
import com.example.appmusic.Fragment.Fragment_Dia_Nhac;
import com.example.appmusic.Fragment.Fragment_Play_Danh_Sach_Bai_Hat;
import com.example.appmusic.Model.Baihat;
import com.example.appmusic.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class PlayMusicActivity extends AppCompatActivity {


    Toolbar toolbarplaynhac;
    TextView txtTimesong,txtTotaltimesong;
    SeekBar sktime;
    ImageButton imgplay,imgrepeat,imgnext,imgpre,imgdom;
    ViewPager viewPagerplaynhac;
    public static ArrayList<Baihat>mangmusic =new ArrayList<>() ;
    public static ViewPagerPlaylistnhacAdapter adapternhac;
    Fragment_Dia_Nhac fragment_dia_nhac;
    Fragment_Play_Danh_Sach_Bai_Hat fragment_play_danh_sach_bai_hat;
    MediaPlayer mediaPlayer;
    int position =0;
    boolean repeat=false;
    boolean checkradom=false;
    boolean next =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        GetDataFromIntent();
        init();
        eventClick();


    }

    private void eventClick() {
        final Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
           @Override
            public void run() {
               if(adapternhac.getItem(1)!=null){
                   if(mangmusic.size()>0){
                       fragment_dia_nhac.PlayNhac(mangmusic.get(0).getHinhbaihat());
                        handler.removeCallbacks(this);
                    }else {
                        handler.postDelayed(this,300);
                    }
                }
            }
       },500);
       imgplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()){
                   mediaPlayer.pause();
                   imgplay.setImageResource(R.drawable.iconplay);
                }else {
                    mediaPlayer.start();
                    imgplay.setImageResource(R.drawable.iconpause);
                }
            }
        });
       imgrepeat.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (repeat==false){
                   if (checkradom==true);{
                        checkradom=false;
                        imgrepeat.setImageResource(R.drawable.iconsyned);
                        imgdom.setImageResource(R.drawable.iconsuffle);
                   }
                   imgrepeat.setImageResource(R.drawable.iconsyned);
                   repeat=true;
               }else {
                   imgrepeat.setImageResource(R.drawable.iconrepeat);
                   repeat=false;
               }
           }
       });
       imgdom.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (checkradom==false){
                   if (repeat==true);{
                       repeat=false;
                       imgdom.setImageResource(R.drawable.iconshuffled);
                       imgrepeat.setImageResource(R.drawable.iconrepeat);
                   }
                   imgdom.setImageResource(R.drawable.iconshuffled);
                   checkradom=true;
               }else {
                   imgdom.setImageResource(R.drawable.iconsuffle);
                   checkradom=false;
               }
           }
       });
       sktime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
           @Override
           public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

           }

           @Override
           public void onStartTrackingTouch(SeekBar seekBar) {

           }

           @Override
           public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
           }
       });
       imgnext.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(mangmusic.size()>0){
                     if(mediaPlayer.isPlaying()|| mediaPlayer!=null){
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer=null;
                     }
                     if (position<(mangmusic.size())){
                         imgplay.setImageResource(R.drawable.iconpause);
                         position++;
                         if(repeat==true){
                             if(position==0){
                                 position=mangmusic.size();
                             }
                             position -= 1;
                         }
                         if(checkradom == true){
                             Random random =new Random();
                             int index =random.nextInt(mangmusic.size());
                             if (index == position){
                                 position = index -1;
                             }
                             position = index;
                         }
                         if(position > (mangmusic.size() -1)){
                             position=0;
                         }
                         new PlayMp3().execute(mangmusic.get(position).getLinkbaihat());
                         fragment_dia_nhac.PlayNhac(mangmusic.get(position).getHinhbaihat());
                         getSupportActionBar().setTitle(mangmusic.get(position).getTenbaihat());
                         UpdateTime();
                     }
                }
               imgpre.setClickable(false);
               imgnext.setClickable(false);
               Handler handler1=new Handler();
               handler1.postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       imgpre.setClickable(true);
                       imgnext.setClickable(true);
                   }
               },5000);
           }
       });
       imgpre.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(mangmusic.size()>0){
                   if(mediaPlayer.isPlaying()||mediaPlayer != null){
                       mediaPlayer.stop();
                       mediaPlayer.release();
                       mediaPlayer=null;
                   }
                   if (position < (mangmusic.size())){
                       imgplay.setImageResource(R.drawable.iconpause);
                       position--;
                       if(position<0){
                           position= mangmusic.size() -1;
                       }
                       if(repeat==true){
                           position +=1;
                       }
                       if(checkradom==true){
                           Random random=new Random();
                           int index =random.nextInt(mangmusic.size());
                           if (index==position){
                               position = index -1;
                           }
                           position = index;
                       }
                       new PlayMp3().execute(mangmusic.get(position).getLinkbaihat());
                       fragment_dia_nhac.PlayNhac(mangmusic.get(position).getHinhbaihat());
                       getSupportActionBar().setTitle(mangmusic.get(position).getTenbaihat());
                        UpdateTime();
                   }
               }
               imgpre.setClickable(false);
               imgnext.setClickable(false);
               Handler handler1=new Handler();
               handler1.postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       imgpre.setClickable(true);
                       imgnext.setClickable(true);
                   }
               },5000);
           }
       });
    }

    private void GetDataFromIntent() {
        Intent intent =getIntent();
        mangmusic.clear();
        if(intent!=null){
            if(intent.hasExtra("cakhuc")){
                Baihat baihat=intent.getParcelableExtra("cakhuc");
                mangmusic.add(baihat);

            }
            if(intent.hasExtra("cacbaihat")){
                ArrayList<Baihat> mangbaihat= intent.getParcelableArrayListExtra("cacbaihat");
                mangmusic = mangbaihat;
            }
        }

    }

    private void init() {
        toolbarplaynhac=findViewById(R.id.toolbarplaynhac);
        txtTimesong=findViewById(R.id.textviewtimesong);
        txtTotaltimesong=findViewById(R.id.textviewtotaltimesong);
        sktime=findViewById(R.id.seekbarsong);
        imgplay=findViewById(R.id.imagebuttonplay);
        imgrepeat=findViewById(R.id.imagebuttonrepaet);
        imgnext=findViewById(R.id.imagebuttonnext);
        imgdom=findViewById(R.id.imagebuttonsuffle);
        imgpre=findViewById(R.id.imagebuttonpre);
        viewPagerplaynhac=findViewById(R.id.viewpagerplaynhac);
        setSupportActionBar(toolbarplaynhac);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarplaynhac.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                mediaPlayer.stop();
                mangmusic.clear();
            }
        });
        toolbarplaynhac.setTitleTextColor(Color.WHITE);
        fragment_dia_nhac=new Fragment_Dia_Nhac();
        fragment_play_danh_sach_bai_hat=new Fragment_Play_Danh_Sach_Bai_Hat();
        adapternhac=new ViewPagerPlaylistnhacAdapter(getSupportFragmentManager());
        adapternhac.AddFragmet(fragment_play_danh_sach_bai_hat);
        adapternhac.AddFragmet(fragment_dia_nhac);
        viewPagerplaynhac.setAdapter(adapternhac);
        fragment_dia_nhac= (Fragment_Dia_Nhac) adapternhac.getItem(1);
        if(mangmusic.size()>0){
            getSupportActionBar().setTitle(mangmusic.get(0).getTenbaihat());
            new PlayMp3().execute(mangmusic.get(0).getLinkbaihat());
            imgplay.setImageResource(R.drawable.iconpause);
        }

    }
        class PlayMp3 extends AsyncTask<String,Void,String>{
            @Override
            protected String doInBackground(String... strings) {
                return strings[0];
            }

            @Override
            protected void onPostExecute(String baihat) {
                super.onPostExecute(baihat);
                try {
                mediaPlayer=new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                });

                mediaPlayer.setDataSource(baihat);
                mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaPlayer.start();
                TimeSong();
                UpdateTime();
            }
        }

    private void TimeSong() {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("mm:ss");
        txtTotaltimesong.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
        sktime.setMax(mediaPlayer.getDuration());
    }
    private  void UpdateTime(){
        final Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer != null){
                    sktime.setProgress(mediaPlayer.getCurrentPosition());
                    SimpleDateFormat simpleDateFormat =new SimpleDateFormat("mm:ss");
                    txtTimesong.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                    handler.postDelayed(this,300);
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            next=true;
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        },300);
        final Handler handler1=new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(next==true){
                    if (position<(mangmusic.size())){
                        imgplay.setImageResource(R.drawable.iconpause);
                        position++;
                        if(repeat==true){
                            if(position==0){
                                position=mangmusic.size();
                            }
                            position -= 1;
                        }
                        if(checkradom == true){
                            Random random =new Random();
                            int index =random.nextInt(mangmusic.size());
                            if (index == position){
                                position = index -1;
                            }
                            position = index;
                        }
                        if(position > (mangmusic.size() -1)){
                            position=0;
                        }
                        new PlayMp3().execute(mangmusic.get(position).getLinkbaihat());
                        fragment_dia_nhac.PlayNhac(mangmusic.get(position).getHinhbaihat());
                        getSupportActionBar().setTitle(mangmusic.get(position).getTenbaihat());
                    }
                    imgpre.setClickable(false);
                    imgnext.setClickable(false);
                    Handler handler1=new Handler();
                    handler1.postDelayed(new Runnable() {
                        @Override
                         public void run() {
                            imgpre.setClickable(true);
                            imgnext.setClickable(true);
                         }
                    },5000);
                    next=false;
                    handler1.removeCallbacks(this);
                }else {
                    handler1.postDelayed(this,1000);
                }
            }
        },1000);
    }
}

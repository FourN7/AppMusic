package com.example.appmusic.Serviec;

import com.example.appmusic.Model.Album;
import com.example.appmusic.Model.Baihat;
import com.example.appmusic.Model.ChuDe;
import com.example.appmusic.Model.Playlist;
import com.example.appmusic.Model.Quangcao;
import com.example.appmusic.Model.TheLoai;
import com.example.appmusic.Model.Theloaitrongngay;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Dataservice {
    @GET("songbanner.php")
    Call<List<Quangcao>> GetDataBanner();

    @GET("playlistforcurrentday.php")
    Call<List<Playlist>> GetPlaylistCurrentDay();

    @GET("allplaylist.php")
    Call<List<Playlist>>GetAllPlaylist();

    @GET("chudevatheloaitrongngay.php")
    Call<Theloaitrongngay>GetCategoryMusic();

    @GET("albumhot.php")
    Call<List<Album>>GetAlbumHot();

    @GET("baihatduocthich.php")
    Call<List<Baihat>>GetBaiHatHot();

    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<Baihat>>GetDanhsachbaihattheoquangcao(@Field("idquangcao") String idquangcao);

    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<Baihat>>GetDanhsachbaihattheoplaylist(@Field("idplaylist") String idplaylist);

    @GET("danhsachcacplaylist.php")
    Call<List<Playlist>>GetDanhsachcacPlaylist();

    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<Baihat>>GetDanhsachbaihattheotheloai(@Field("idtheloai") String idtheloai);

    @GET("tatcachude.php")
    Call<List<ChuDe>>GetAllChuDe();

    @GET("tatcaalbum.php")
    Call<List<Album>>GetAllAlbum();

    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<Baihat>>GetDanhsachbaihattheoalbum(@Field("idalbum") String idalbum);


    @FormUrlEncoded
    @POST("updateluotthich.php")
    Call<String>UpdateLuotThich(@Field("luotthich") String luotthich,@Field("idbaihat") String idbaihat);

    @FormUrlEncoded
    @POST("searchbaihat.php")
    Call<List<Baihat>>GetSearchBaiHat(@Field("tukhoa") String tukhoa);

    @FormUrlEncoded
    @POST("theloaichude.php")
    Call<List<TheLoai>>GetTheloaitheochude(@Field("idchude") String idchude);

}

package com.example.windows10.mp3player.service;

import com.example.windows10.mp3player.model.BaiHat;
import com.example.windows10.mp3player.model.TheLoai;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DataService {
    @GET("getTheLoai.php")
    Call<List<TheLoai>> getTheLoai();

    @FormUrlEncoded
    @POST("getBaiHat.php")
    Call<List<BaiHat>> getBaiHatFromTheLoai(@Field("idTheLoai") String idTheLoai);
}

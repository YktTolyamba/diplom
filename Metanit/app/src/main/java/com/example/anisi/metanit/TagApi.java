package com.example.anisi.metanit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TagApi {
    @GET("tag/")
    Call<List<Tag>> tag();
}

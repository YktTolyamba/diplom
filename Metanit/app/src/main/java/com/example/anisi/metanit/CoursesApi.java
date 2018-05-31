package com.example.anisi.metanit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CoursesApi {
    @GET("course/")
    Call<List<Course>> course();
}

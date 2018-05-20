package com.example.anisi.metanit;

/**
 * Created by Толян on 10.05.2018.
 */
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CoursesApi {
    @GET("course/")
    Call<List<Course>> course();
}

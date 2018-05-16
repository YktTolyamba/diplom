package com.example.anisi.metanit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CourseTopicsApi {
    @GET("course_topic/")
    Call<List<CourseTopics>> courseTopics(@Query("course") int courseId);
}
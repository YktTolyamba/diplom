package com.example.anisi.metanit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CourseTopicApi {
    @GET("course_topic/")
    Call<List<CourseTopic>> courseTopics();

    @GET("course_topic/")
    Call<List<CourseTopic>> courseTopic(@Query("course") int courseId);
}
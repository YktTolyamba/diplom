package com.example.anisi.metanit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class CoursesList {
    public ArrayList<Courses> coursesAR = new ArrayList<>();
    public void PushCourse(Response<List<Courses>> response){
        for (int i = 0; i < response.body().size(); i++) {
            coursesAR.add(response.body().get(i));
        }
    }
    public Courses GetCourse(int id){
        return coursesAR.get(id);
    }
}

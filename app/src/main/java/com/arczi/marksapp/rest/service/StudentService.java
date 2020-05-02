package com.arczi.marksapp.rest.service;

import com.arczi.marksapp.rest.model.Student;
import com.arczi.marksapp.rest.model.StudentDetalist;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface StudentService {

    @GET("/api/students")
    Call<List<Student>> getAllStudentOnList();

    @GET("/api/students/{index}")
    Call<StudentDetalist> getStudent(@Path(value = "index") Integer index);
}

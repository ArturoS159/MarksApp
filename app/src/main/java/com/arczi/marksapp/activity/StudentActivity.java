package com.arczi.marksapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.arczi.marksapp.R;
import com.arczi.marksapp.adapter.StudentDetailAdapter;
import com.arczi.marksapp.rest.RetrofitInstance;
import com.arczi.marksapp.rest.model.Lab;
import com.arczi.marksapp.rest.model.StudentDetalist;
import com.arczi.marksapp.rest.service.StudentService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentActivity extends AppCompatActivity {
    private final static StudentService STUDENT_SERVICE = RetrofitInstance.getRetrofitInstance().create(StudentService.class);
    private String indeks;
    private StudentDetalist studentDetalist;
    private RecyclerView recyclerView;
    private StudentDetailAdapter mAdapter;
    private List<Lab> labList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studentactivity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView indeksDetailText = findViewById(R.id.indeksDetail);
        indeks=getIntent().getStringExtra("indeks");
        indeksDetailText.setText(indeks);
        setRecyclerView();
    }

    private void setRecyclerView() {
        getStudentDelalist();
        StudentDetailAdapter studentDetailAdapter = new StudentDetailAdapter(R.layout.student_detail_row, labList);
        recyclerView = findViewById(R.id.student_detail);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = studentDetailAdapter;
        recyclerView.setAdapter(studentDetailAdapter);
    }

    private void getStudentDelalist() {
        STUDENT_SERVICE.getStudent(Integer.parseInt(indeks));
        Call<StudentDetalist> studentOnList = STUDENT_SERVICE.getStudent(Integer.valueOf(indeks));
        studentOnList.enqueue(new Callback<StudentDetalist>() {
            @Override
            public void onResponse(Call<StudentDetalist> call, Response<StudentDetalist> response) {
                studentDetalist = response.body();
                mAdapter.refreshRecycleView(response.body());
            }

            @Override
            public void onFailure(Call<StudentDetalist> call, Throwable t) {
                Toast.makeText(StudentActivity.this, "Brak internetu!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

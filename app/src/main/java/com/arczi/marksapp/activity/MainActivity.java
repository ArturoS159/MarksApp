package com.arczi.marksapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.arczi.marksapp.R;
import com.arczi.marksapp.adapter.StudentListAdapter;
import com.arczi.marksapp.rest.RetrofitInstance;
import com.arczi.marksapp.rest.model.Student;
import com.arczi.marksapp.rest.service.StudentService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private final static StudentService STUDENT_SERVICE = RetrofitInstance.getRetrofitInstance().create(StudentService.class);
    private static Context mContext;
    private StudentListAdapter madapter;
    private List<Student> studentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getApplicationContext();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setRecyclerView();
        setRefresh();
        setSearchText();
    }

    private void setSearchText() {
        EditText searchText = findViewById(R.id.search);

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable text) {
                filter(text.toString());
            }
        });
    }

    private void setRefresh() {
        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.refresh);
        pullToRefresh.setOnRefreshListener(() -> {
            studentList.clear();
            studentList = getAllStudents();
            setRecyclerView();
            pullToRefresh.setRefreshing(false);
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    private void setRecyclerView() {
        studentList = getAllStudents();
        StudentListAdapter studentListAdapter = new StudentListAdapter(R.layout.student_row, studentList);
        recyclerView = findViewById(R.id.item_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        madapter = studentListAdapter;
        recyclerView.setAdapter(studentListAdapter);
    }

    private void filter(final String text) {
        List<Student> filterList = studentList.stream()
                .filter(student -> student.getIndex().toLowerCase().contains(text.toLowerCase()) ||
                        student.getGroup().toLowerCase().contains(text.toLowerCase())
                )
                .collect(Collectors.toList());
        madapter.refreshRecycleView(filterList);
    }

    public static Context getAppContext(){
        return mContext;
    }

    private List<Student> getAllStudents() {
        final List<Student> studentList = new ArrayList<>();
        Call<List<Student>> studentOnList = STUDENT_SERVICE.getAllStudentOnList();
        studentOnList.enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                studentList.addAll(response.body());
                madapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Brak internetu!", Toast.LENGTH_SHORT).show();
            }
        });
        return studentList;
    }

    public static void setNewIntent(String index){
        Intent intent = new Intent(getAppContext(), StudentActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("indeks", index);
        getAppContext().startActivity(intent);
    }
}

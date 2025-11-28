package com.example.todolist;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton fab;
    DatabaseHelper myDb;
    ArrayList<TaskModel> taskList;
    TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.fabAdd);
        myDb = new DatabaseHelper(this);
        taskList = new ArrayList<>();

        // عندما يتم الضغط على زر + لإضافة مهمة جديدة
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivity(intent);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadData();  // تحميل البيانات من قاعدة البيانات
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();  // عندما يعود المستخدم إلى MainActivity نعيد تحميل البيانات
    }

    private void loadData() {
        taskList.clear();
        Cursor cursor = myDb.getAllData();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Tasks Found", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                taskList.add(new TaskModel(
                        cursor.getString(0), // ID
                        cursor.getString(1), // TITLE
                        cursor.getString(2), // DESCRIPTION
                        cursor.getString(3)  // DATE
                ));
            }
        }
        // تعيين المحول لعرض البيانات في RecyclerView
        adapter = new TaskAdapter(this, taskList);
        recyclerView.setAdapter(adapter);
    }
}

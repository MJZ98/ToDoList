package com.example.todolist;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddTaskActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText etTitle, etDesc, etDate;
    Button btnSave;
    boolean isEdit = false;
    String taskId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        myDb = new DatabaseHelper(this);
        etTitle = findViewById(R.id.etTitle);
        etDesc = findViewById(R.id.etDesc);
        etDate = findViewById(R.id.etDate);
        btnSave = findViewById(R.id.btnSave);
        // نعرف إذا جينا للشاشة بوضع تعديل
        if (getIntent() != null && getIntent().getBooleanExtra("isEdit", false)) {
            isEdit = true;
            taskId = getIntent().getStringExtra("id");
            String title = getIntent().getStringExtra("title");
            String desc = getIntent().getStringExtra("desc");
            String date = getIntent().getStringExtra("date");

            // نعبي الحقول بالقيم القديمة
            etTitle.setText(title);
            etDesc.setText(desc);
            etDate.setText(date);

            // نغير نص الزر عشان يكون واضح
            btnSave.setText("Update Task");
        }


        // عند الضغط على زر حفظ
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                String desc = etDesc.getText().toString();
                String date = etDate.getText().toString();

                if (title.isEmpty() || desc.isEmpty()) {
                    Toast.makeText(AddTaskActivity.this, "Please enter details", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (isEdit) {
                    // تعديل
                    if (title.length() > 50) {
                        Toast.makeText(AddTaskActivity.this, "Update Error", Toast.LENGTH_SHORT).show();
                    } else {
                        boolean isUpdated = myDb.updateData(taskId, title, desc, date);
                        if (isUpdated) {
                            Toast.makeText(AddTaskActivity.this, "Task Updated", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(AddTaskActivity.this, "Update Error", Toast.LENGTH_SHORT).show();
                        }
                    }

                } else {
                    // إضافة جديدة
                    boolean isInserted = myDb.insertData(title, desc, date);

                    if (isInserted) {
                        Toast.makeText(AddTaskActivity.this, "Task Saved", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(AddTaskActivity.this, "Database Error", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}

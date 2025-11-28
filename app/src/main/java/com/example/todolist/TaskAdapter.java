package com.example.todolist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import android.content.Intent;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    Context context;
    ArrayList<TaskModel> list;
    DatabaseHelper myDb;

    public TaskAdapter(Context context, ArrayList<TaskModel> list) {
        this.context = context;
        this.list = list;
        myDb = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TaskModel task = list.get(position);
        holder.tvTitle.setText(task.getTitle());
        holder.tvDesc.setText(task.getDescription());
        holder.tvDate.setText(task.getDate());
        // ضغط عادي لفتح شاشة التعديل
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddTaskActivity.class);
                intent.putExtra("isEdit", true);
                intent.putExtra("id", task.getId());
                intent.putExtra("title", task.getTitle());
                intent.putExtra("desc", task.getDescription());
                intent.putExtra("date", task.getDate());
                context.startActivity(intent);
            }
        });


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showDeleteDialog(task.getId(), position);
                return true;
            }
        });
    }

    private void showDeleteDialog(String id, int position) {
        new AlertDialog.Builder(context)
                .setTitle("Delete Task")
                .setMessage("Are you sure you want to delete this task?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Integer deletedRows = myDb.deleteData(id);
                        if(deletedRows > 0) {
                            list.remove(position);
                            notifyItemRemoved(position);
                            Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDesc, tvDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}

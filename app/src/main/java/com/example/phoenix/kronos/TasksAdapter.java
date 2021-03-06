package com.example.phoenix.kronos;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.MyViewHolder> {

    private List<Task> TasksList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView description,locationname;
        Button delete;
        public MyViewHolder(View view) {
            super(view);
            description = (TextView) view.findViewById(R.id.description);
            locationname = (TextView) view.findViewById(R.id.location_name);
            delete = (Button) view.findViewById(R.id.delete);
        }
    }


    public TasksAdapter(List<Task> TasksList) {
        this.TasksList = TasksList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_event, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Task task = TasksList.get(position);
        holder.description.setText(task.description);
        holder.locationname.setText(task.locationname);
    }

    @Override
    public int getItemCount() {
        return TasksList.size();
    }}
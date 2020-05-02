package com.arczi.marksapp.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.arczi.marksapp.R;
import com.arczi.marksapp.activity.MainActivity;
import com.arczi.marksapp.rest.model.Student;

import java.util.List;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.ViewHolder> {


    private int listItemLayout;
    private List<Student> studentList;

    public StudentListAdapter(int layoutId, List<Student> studentList) {
        listItemLayout = layoutId;
        this.studentList = studentList;
    }

    @Override
    public int getItemCount() {
        return studentList == null ? 0 : studentList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(listItemLayout, parent, false);
        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int listPosition) {
        TextView intexText = holder.indexText;
        TextView markText = holder.markText;
        TextView groupText = holder.groupText;
        TextView presentText = holder.presentText;
        TextView absentText = holder.absentText;
        TextView lectureText = holder.lectureText;
        TextView homeworkText = holder.homeworkText;
        TextView allText = holder.allText;

        intexText.setText(studentList.get(listPosition).getIndex());
        markText.setText(studentList.get(listPosition).getMark().toString());
        groupText.setText(studentList.get(listPosition).getGroup());
        presentText.setText(studentList.get(listPosition).getPresenceCounter().toString());
        absentText.setText(studentList.get(listPosition).getAbsenceCounter().toString());
        lectureText.setText(studentList.get(listPosition).getLecturePoints().toString());
        homeworkText.setText(studentList.get(listPosition).getHomeworkPoints().toString());
        allText.setText(studentList.get(listPosition).getAllPoints().toString());
    }



    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView indexText;
        public TextView markText;
        public TextView groupText;
        public TextView presentText;
        public TextView absentText;
        public TextView lectureText;
        public TextView homeworkText;
        public TextView allText;
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            indexText = itemView.findViewById(R.id.index);
            markText = itemView.findViewById(R.id.mark);
            groupText = itemView.findViewById(R.id.group);
            presentText = itemView.findViewById(R.id.present);
            absentText = itemView.findViewById(R.id.absent);
            lectureText = itemView.findViewById(R.id.lecture);
            homeworkText = itemView.findViewById(R.id.homework);
            allText = itemView.findViewById(R.id.all);
        }
        @Override
        public void onClick(View view) {
            Log.d("onclick", "onClick " + getLayoutPosition() + " " + indexText.getText());
            MainActivity.setNewIntent(indexText.getText().toString());
        }
    }

    public void refreshRecycleView(List<Student> studentList){
        this.studentList=studentList;
        notifyDataSetChanged();
    }
}
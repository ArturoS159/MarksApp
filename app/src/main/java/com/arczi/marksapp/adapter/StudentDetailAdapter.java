package com.arczi.marksapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.arczi.marksapp.R;
import com.arczi.marksapp.rest.model.Lab;
import com.arczi.marksapp.rest.model.StudentDetalist;

import java.util.List;

public class StudentDetailAdapter extends RecyclerView.Adapter<StudentDetailAdapter.ViewHolder> {


    private int listItemLayout;
    private List<Lab> labList;

    public StudentDetailAdapter(int layoutId, List<Lab> labList) {
        listItemLayout = layoutId;
        this.labList = labList;
    }

    @Override
    public int getItemCount() {
        return labList == null ? 0 : labList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(listItemLayout, parent, false);
        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int listPosition) {
        TextView intexText = holder.dateOfLab;
        TextView pktText = holder.pkt;
        intexText.setText(labList.get(listPosition).getDateOfLab());
        pktText.setText(labList.get(listPosition).getPoints().toString());
    }



    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView dateOfLab;
        public TextView pkt;
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            dateOfLab = itemView.findViewById(R.id.dateOfLab);
            pkt = itemView.findViewById(R.id.pkt);
        }
        @Override
        public void onClick(View view) {

        }
    }

    public void refreshRecycleView(StudentDetalist studentDetalistList){
        this.labList = studentDetalistList.getLabs();
        notifyDataSetChanged();
    }
}
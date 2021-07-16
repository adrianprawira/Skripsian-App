package com.upnvj.skripsian.ui.lecturer.detail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.upnvj.skripsian.R;
import com.upnvj.skripsian.data.model.LecturerSchedule;
import com.upnvj.skripsian.databinding.ItemLecturerScheduleBinding;
import com.upnvj.skripsian.util.callback.OnItemClickListener;

public class DetailLecturerAdapter extends ListAdapter<LecturerSchedule, DetailLecturerAdapter.ViewHolder> {

    private OnItemClickListener<LecturerSchedule> listener;
    private String[] timeStudies;

    DetailLecturerAdapter(OnItemClickListener<LecturerSchedule> listener, String[] timeStudies) {
        super(LecturerSchedule.DIFF_CALLBACK);
        this.listener = listener;
        this.timeStudies = timeStudies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemLecturerScheduleBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_lecturer_schedule, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final LecturerSchedule schedule = getItem(position);
        String timeStart = timeStudies[schedule.getTimeOfStudyStart()].substring(0, 5);
        String timeEnd = timeStudies[schedule.getTimeOfStudyEnd()].substring(6, 11);
        String timeStudy = timeStart + "-" + timeEnd;
        String timeStudyIndex = (schedule.getTimeOfStudyStart() + 1) + " - " + (schedule.getTimeOfStudyEnd() + 1);

        holder.binding.setSchedule(schedule);
        holder.binding.setTimeStudy(timeStudy);
        holder.binding.setTimeStudyIndex(timeStudyIndex);
        holder.binding.setListener(listener);
        holder.binding.cardSchedule.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onLongClick(schedule);
                return true;
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ItemLecturerScheduleBinding binding;

        ViewHolder(ItemLecturerScheduleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

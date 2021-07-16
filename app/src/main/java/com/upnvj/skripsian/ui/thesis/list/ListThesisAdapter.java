package com.upnvj.skripsian.ui.thesis.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.upnvj.skripsian.R;
import com.upnvj.skripsian.data.model.ThesisScheduleWithLecturers;
import com.upnvj.skripsian.databinding.ItemThesisScheduleBinding;
import com.upnvj.skripsian.util.callback.OnItemClickListener;

public class ListThesisAdapter extends ListAdapter<ThesisScheduleWithLecturers, ListThesisAdapter.ViewHolder> {

    private OnItemClickListener<ThesisScheduleWithLecturers> listener;

    ListThesisAdapter(OnItemClickListener<ThesisScheduleWithLecturers> listener) {
        super(ThesisScheduleWithLecturers.DIFF_CALLBACK);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemThesisScheduleBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_thesis_schedule, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ThesisScheduleWithLecturers data = getItem(position);
        holder.binding.setData(data);
        holder.binding.setListener(listener);
        holder.binding.cardSchedule.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onLongClick(data);
                return true;
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ItemThesisScheduleBinding binding;

        ViewHolder(ItemThesisScheduleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}

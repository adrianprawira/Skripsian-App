package com.upnvj.skripsian.ui.home;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.upnvj.skripsian.R;
import com.upnvj.skripsian.data.model.ThesisScheduleWithLecturers;
import com.upnvj.skripsian.databinding.ItemHomeTodayScheduleBinding;

public class HomeAdapter extends ListAdapter<ThesisScheduleWithLecturers, HomeAdapter.ViewHolder> {

    HomeAdapter() {
        super(ThesisScheduleWithLecturers.DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemHomeTodayScheduleBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_home_today_schedule, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setData(getItem(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ItemHomeTodayScheduleBinding binding;

        ViewHolder(ItemHomeTodayScheduleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
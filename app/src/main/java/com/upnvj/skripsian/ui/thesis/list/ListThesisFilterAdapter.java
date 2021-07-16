package com.upnvj.skripsian.ui.thesis.list;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.upnvj.skripsian.R;
import com.upnvj.skripsian.databinding.ItemScheduleFilterBinding;
import com.upnvj.skripsian.util.callback.OnItemClickListener;

public class ListThesisFilterAdapter extends ListAdapter<ListThesisFilter, ListThesisFilterAdapter.ViewHolder> {

    private OnItemClickListener<ListThesisFilter> listener;

    ListThesisFilterAdapter(OnItemClickListener<ListThesisFilter> listener) {
        super(ListThesisFilter.DIFF_CALLBACK);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemScheduleFilterBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_schedule_filter, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setFilter(getItem(position));
        holder.binding.setListener(listener);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ItemScheduleFilterBinding binding;

        ViewHolder(ItemScheduleFilterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
package com.upnvj.skripsian.ui.thesis;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.upnvj.skripsian.R;
import com.upnvj.skripsian.databinding.ItemThesisScheduleGroupBinding;
import com.upnvj.skripsian.util.callback.OnItemClickListener;

public class ThesisAdapter extends ListAdapter<ThesisScheduleGroup, ThesisAdapter.ViewHolder> {

    private OnItemClickListener<ThesisScheduleGroup> listener;

    ThesisAdapter(OnItemClickListener<ThesisScheduleGroup> listener) {
        super(ThesisScheduleGroup.DIFF_CALLBACK);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemThesisScheduleGroupBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_thesis_schedule_group, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setGroup(getItem(position));
        holder.binding.setListener(listener);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ItemThesisScheduleGroupBinding binding;

        ViewHolder(ItemThesisScheduleGroupBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}

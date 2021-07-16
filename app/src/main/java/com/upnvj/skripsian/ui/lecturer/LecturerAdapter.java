package com.upnvj.skripsian.ui.lecturer;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.upnvj.skripsian.R;
import com.upnvj.skripsian.data.model.Lecturer;
import com.upnvj.skripsian.databinding.ItemLecturerBinding;
import com.upnvj.skripsian.util.callback.OnItemClickListener;

public class LecturerAdapter extends ListAdapter<Lecturer, LecturerAdapter.ViewHolder> {

    private OnItemClickListener<Lecturer> listener;

    LecturerAdapter(OnItemClickListener<Lecturer> listener) {
        super(Lecturer.DIFF_CALLBACK);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemLecturerBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_lecturer, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setLecturer(getItem(position));
        holder.binding.setListener(listener);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ItemLecturerBinding binding;

        ViewHolder(ItemLecturerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}

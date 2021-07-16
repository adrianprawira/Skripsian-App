package com.upnvj.skripsian.ui.thesis;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class ThesisScheduleGroup {

    static DiffUtil.ItemCallback<ThesisScheduleGroup> DIFF_CALLBACK = new DiffUtil.ItemCallback<ThesisScheduleGroup>() {
        @Override
        public boolean areItemsTheSame(@NonNull ThesisScheduleGroup oldItem, @NonNull ThesisScheduleGroup newItem) {
            return oldItem.getDateString().equals(newItem.getDateString());
        }

        @Override
        public boolean areContentsTheSame(@NonNull ThesisScheduleGroup oldItem, @NonNull ThesisScheduleGroup newItem) {
            return oldItem.getDateString().equals(newItem.getDateString());
        }
    };

    private String dateString;
    private int schedulesCount;

    public ThesisScheduleGroup() {
    }

    ThesisScheduleGroup(String dateString, int schedulesCount) {
        this.dateString = dateString;
        this.schedulesCount = schedulesCount;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public int getSchedulesCount() {
        return schedulesCount;
    }

    public void setSchedulesCount(int schedulesCount) {
        this.schedulesCount = schedulesCount;
    }
}

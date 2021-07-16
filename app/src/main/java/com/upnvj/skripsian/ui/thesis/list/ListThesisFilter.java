package com.upnvj.skripsian.ui.thesis.list;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.upnvj.skripsian.data.model.ThesisScheduleWithLecturers;

import java.util.List;

public class ListThesisFilter {

    static DiffUtil.ItemCallback<ListThesisFilter> DIFF_CALLBACK = new DiffUtil.ItemCallback<ListThesisFilter>() {
        @Override
        public boolean areItemsTheSame(@NonNull ListThesisFilter oldItem, @NonNull ListThesisFilter newItem) {
            return oldItem.getIndexTime() == newItem.getIndexTime() && oldItem.isChecked() == newItem.isChecked();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ListThesisFilter oldItem, @NonNull ListThesisFilter newItem) {
            return false;
        }
    };

    private int indexTime;
    private String timeStudyTitle;
    private String timeStudyDetail;
    private boolean isChecked;
    private List<ThesisScheduleWithLecturers> schedules;

    public ListThesisFilter() {
    }

    ListThesisFilter(int indexTime, String timeStudyTitle, String timeStudyDetail, boolean isChecked, List<ThesisScheduleWithLecturers> schedules) {
        this.indexTime = indexTime;
        this.timeStudyTitle = timeStudyTitle;
        this.timeStudyDetail = timeStudyDetail;
        this.isChecked = isChecked;
        this.schedules = schedules;
    }

    int getIndexTime() {
        return indexTime;
    }

    public void setIndexTime(int indexTime) {
        this.indexTime = indexTime;
    }

    public String getTimeStudyTitle() {
        return timeStudyTitle;
    }

    public void setTimeStudyTitle(String timeStudyTitle) {
        this.timeStudyTitle = timeStudyTitle;
    }

    public String getTimeStudyDetail() {
        return timeStudyDetail;
    }

    public void setTimeStudyDetail(String timeStudyDetail) {
        this.timeStudyDetail = timeStudyDetail;
    }

    public boolean isChecked() {
        return isChecked;
    }

    void setChecked(boolean checked) {
        isChecked = checked;
    }

    public List<ThesisScheduleWithLecturers> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<ThesisScheduleWithLecturers> schedules) {
        this.schedules = schedules;
    }
}

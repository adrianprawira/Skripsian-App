package com.upnvj.skripsian.ui.thesis.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.upnvj.skripsian.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailThesisFragment extends Fragment {

    public DetailThesisFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_thesis, container, false);
    }
}

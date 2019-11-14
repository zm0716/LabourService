package com.agilefinger.labourservice.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agilefinger.labourservice.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageMRFragment extends Fragment {


    public ImageMRFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_mr, container, false);
    }

}

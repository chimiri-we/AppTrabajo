package com.example.apptrabajo.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.apptrabajo.R;
import com.example.apptrabajo.ui.slideshow.SlideshowFragment;


/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {



    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_vistas, container, false);


        return root;
    }
}
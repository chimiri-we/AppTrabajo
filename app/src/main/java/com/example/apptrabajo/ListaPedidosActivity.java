package com.example.apptrabajo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptrabajo.adaptadores.DetalleVentaAdapter;
import com.example.apptrabajo.entidades.DetalleVenta;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ListaPedidosActivity  extends AppCompatActivity {

    private static final String TAG = ListaPedidosActivity.class.getSimpleName();


    private RecyclerView mList;
    private DetalleVentaAdapter mListAdapter;
    private ProgressBar mProgress;
    private View mEmptyView;


    private Date mDateSchedulePicked;
    private String mMedicalCenterId;
    private String mTimeSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        mProgress = (ProgressBar) findViewById(R.id.progress);
        mEmptyView = findViewById(R.id.doctors_schedules_empty);


    }


    private void showEmptyView() {
        mEmptyView.setVisibility(View.VISIBLE);
        mProgress.setVisibility(View.GONE);
        mList.setVisibility(View.GONE);
    }

    private void showLoadingIndicator(boolean show) {
        mProgress.setVisibility(show ? View.VISIBLE : View.GONE);
        mList.setVisibility(show ? View.GONE : View.VISIBLE);
    }
}
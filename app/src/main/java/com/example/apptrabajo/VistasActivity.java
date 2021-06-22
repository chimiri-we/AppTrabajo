package com.example.apptrabajo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.apptrabajo.main.VisitasFragment;
import com.example.apptrabajo.main.PlaceholderFragment;
import com.example.apptrabajo.ui.home.HomeFragment;

public class VistasActivity extends Fragment {

    private Fragment fragmentR, fragmentS;
    private Fragment fragmentL;

    TextView tvLista, tvListF, tvListV;
    int fragmentTransaction;
FragmentManager fragmentManager;
    private Object View;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_vistas, container, false);

        tvLista = view.findViewById(R.id.lista_clientes);
        tvListF = view.findViewById(R.id.client_faltante);
        tvListV = view.findViewById(R.id.cliente_visitados);
       fragmentS = new HomeFragment();
         fragmentL = new PlaceholderFragment();
         fragmentR = new VisitasFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.contenedorFragment, fragmentS).commit();

        tvLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

                transaction.replace(R.id.contenedorFragment, fragmentS).commit();
                transaction.addToBackStack(null);
            }
        });
        tvListV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

                transaction.replace(R.id.contenedorFragment, fragmentR).commit();
                transaction.addToBackStack(null);
            }
        });
        tvListF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

                transaction.replace(R.id.contenedorFragment, fragmentL).commit();
                transaction.addToBackStack(null);
            }
        });
        return view;
    }



    public void onClick(ViewGroup view) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        switch (view.getId())
        {
            case R.id.client_faltante: transaction.replace(R.id.contenedorFragment, fragmentL);
                transaction.addToBackStack(null);
                break;
            case R.id.cliente_visitados: transaction.replace(R.id.contenedorFragment, fragmentR);
                transaction.addToBackStack(null);
                break;
            case R.id.lista_clientes: transaction.replace(R.id.contenedorFragment, fragmentS);
                transaction.addToBackStack(null);
                break;
        }
        transaction.commit();
    }

}
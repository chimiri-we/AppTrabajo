package com.example.apptrabajo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.Volley;


public class ComunicadosFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragmen_comunicados, container, false);
        TextView btnComentario = view.findViewById(R.id.txt_comentario);
        btnComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inflarFormatoEscribirComentario();
            }
        });

   return view;
    }

    private void inflarFormatoEscribirComentario() {
        LayoutInflater inflador;
        View v;
        inflador = LayoutInflater.from(getContext());
        v = inflador.inflate(R.layout.item_comentario,null, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        builder.setTitle("Comunicados");
        builder.setView(v);
        builder.create();
        builder.setPositiveButton("Actualizar", (dialog, which) -> {


            // finish();
            //  startActivity(getContext());

        });

        builder.show();
    }
}
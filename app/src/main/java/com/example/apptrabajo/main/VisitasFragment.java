package com.example.apptrabajo.main;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.apptrabajo.R;
import com.example.apptrabajo.adaptadores.VisitasAdapter;
import com.example.apptrabajo.api.Consultas;
import com.example.apptrabajo.datos.BaseDatosApp;
import com.example.apptrabajo.entidades.Visitas;

import java.util.ArrayList;

public class VisitasFragment extends Fragment {

    ListView listaVisitas;
VisitasAdapter adapter;
    ArrayList<Visitas> visitasArrayList;
   BaseDatosApp bdLocal;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_visitas, container, false);

        listaVisitas = root.findViewById(R.id.nuevalistaCliente);

        generatelistaVisitas();

        if (visitasArrayList.size() > 0) {

            adapter.setDropDownViewResource(View.VISIBLE);
            listaVisitas.setAdapter(adapter);
        }else {
            adapter.setDropDownViewResource(View.GONE);
        }


        return root;
    }

    private void generatelistaVisitas() {
        bdLocal = new BaseDatosApp(getContext().getApplicationContext());
        SQLiteDatabase db = bdLocal.getWritableDatabase();
        Visitas visitas;
        visitasArrayList = new ArrayList<>();
        Cursor cursor;
        cursor = db.rawQuery("select * from Visitas", null);
        while (cursor.moveToNext()) {

            visitas = new Visitas();
            visitas.setEstado(cursor.getInt(4));
            visitas.setFecha_visita(cursor.getString(3));
            visitas.setId_cliente(cursor.getInt(2));
            visitas.setId_visitas(cursor.getInt(0));
            visitasArrayList.add(visitas);
        }
        adapter = new VisitasAdapter(getContext().getApplicationContext(), R.layout.item_visitas, visitasArrayList);

        listaVisitas.setAdapter(adapter);
        cursor.close();

    }
}



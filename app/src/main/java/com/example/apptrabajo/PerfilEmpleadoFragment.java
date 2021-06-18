package com.example.apptrabajo;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.apptrabajo.datos.BaseDatosApp;

public class PerfilEmpleadoFragment extends Fragment {

    ImageView btnActuPro, btnActuClie;

    CardView verListaVenta, verVisitas, verVistasPendientes;
    private static final String TABLE_CLIENTE = "Cliente";

    BaseDatosApp bdLocal;

    private static final String TABLE_PRODUCTO = "Producto";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.perfil_empleado, container, false);


        btnActuClie = v.findViewById(R.id.btnActualizarCliente);
        btnActuClie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarCliente();
            }
        });
        btnActuPro = v.findViewById(R.id.btn_actuali_pro);
        btnActuPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarPro();
            }
        });
        verListaVenta = v.findViewById(R.id.verListaVentas);
        verListaVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ListaPedidosActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }

    private void actualizarCliente() {
        bdLocal = new BaseDatosApp(requireContext().getApplicationContext());
        SQLiteDatabase db = bdLocal.getReadableDatabase();
        db.execSQL("delete from " + TABLE_CLIENTE);
        Toast.makeText(getContext(), "Se ha eliminado, actualiza la vista", Toast.LENGTH_LONG).show();
        onStart();
    }

    private void actualizarPro() {
        bdLocal = new BaseDatosApp(requireContext().getApplicationContext());
        SQLiteDatabase db = bdLocal.getReadableDatabase();
        db.execSQL("delete from " + TABLE_PRODUCTO);
        Toast.makeText(getContext(), "Se ha eliminado, actualiza la vista", Toast.LENGTH_LONG).show();
        onStart();
    }
}

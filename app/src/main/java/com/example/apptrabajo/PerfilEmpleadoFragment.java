package com.example.apptrabajo;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.apptrabajo.datos.BaseDatosApp;

public class PerfilEmpleadoFragment extends Fragment {

    ImageView btnActuPro, btnActuClie;

    BaseDatosApp bdLocal;

    private static final String TABLE_PRODUCTO = "Producto";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.perfil_empleado, container, false);


        btnActuClie = v.findViewById(R.id.btnActualizarCliente);
        btnActuPro = v.findViewById(R.id.btn_actuali_pro);
        btnActuPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarPro();
            }
        });

        return v;
    }

    private void actualizarPro() {
        bdLocal = new BaseDatosApp(requireContext().getApplicationContext());
        SQLiteDatabase db = bdLocal.getReadableDatabase();
        db.execSQL("delete from " + TABLE_PRODUCTO);
        Toast.makeText(getContext(), "Se ha eliminado, actualiza la vista", Toast.LENGTH_LONG).show();
        onStart();
    }
}

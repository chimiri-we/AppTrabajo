package com.example.apptrabajo;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.apptrabajo.api.Consultas;
import com.example.apptrabajo.datos.BaseDatosApp;
import com.example.apptrabajo.entidades.DetalleVenta;
import com.example.apptrabajo.entidades.Venta;
import com.google.android.material.chip.Chip;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class PerfilEmpleadoFragment extends Fragment {

    ImageView btnActuPro, btnActuClie;

    CardView verListaVenta, verVisitas, verVistasPendientes;
    private static final String TABLE_CLIENTE = "Cliente";

    BaseDatosApp bdLocal;
    Consultas con;

    Chip chip;
  Venta venta;
    String fecha;
    TextView totalV, txtFecha;
    private static final String TABLE_PRODUCTO = "Producto";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.perfil_empleado, container, false);

        chip = v.findViewById(R.id.actualizar_productos);
        totalV = v.findViewById(R.id.tv_ventas_dia);
        txtFecha = v.findViewById(R.id.dvFecha);
        Date fechaActual= Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        fecha = df.format(fechaActual);
        System.out.println("Current time => " + fecha);
        txtFecha.setText(fecha);
        con = new Consultas(requireContext().getApplicationContext());

        String f = txtFecha.getText().toString().trim();
        venta = con.obtenerVentasDia(fecha);
        if (venta != null) {
            //venta = new DetalleVenta();

            totalV.setText(String.valueOf(venta.getTota_venta()));
        }



/*        btnActuClie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarCliente();
            }
        });
      */
        chip.setOnClickListener(new View.OnClickListener() {
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

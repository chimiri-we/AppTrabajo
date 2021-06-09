package com.example.apptrabajo.modelo;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.apptrabajo.R;

import java.text.BreakIterator;

public class VentaModelo extends RecyclerView.ViewHolder {

    public  TextView tvNameProducto, tvPrecioProducto, tvTotal, tvCantidad;

   public VentaModelo(View itemView) {
            super(itemView);
        tvCantidad = itemView.findViewById(R.id.cantidad);
        tvTotal = itemView.findViewById(R.id.total);
        tvNameProducto = itemView.findViewById(R.id.nombreProducto);
        tvPrecioProducto = itemView.findViewById(R.id.precioProducto);

   }




}

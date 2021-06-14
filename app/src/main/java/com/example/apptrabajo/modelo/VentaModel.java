package com.example.apptrabajo.modelo;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.apptrabajo.R;

public class VentaModel extends RecyclerView.ViewHolder {

    public TextView nombreCliente, fechaVenta, totalV, detalle;

    public VentaModel(View itemView) {
        super(itemView);
        nombreCliente = itemView.findViewById(R.id.name_cliente);
        fechaVenta = itemView.findViewById(R.id.fecha_text);
        totalV = itemView.findViewById(R.id.tv_total_venta);
        detalle = itemView.findViewById(R.id.tv_detalle_venta);

    }

}

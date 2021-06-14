package com.example.apptrabajo.modelo;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.apptrabajo.R;

public class VentaModel extends RecyclerView.ViewHolder {

    public TextView nombreCliente, fechaVenta, tVenta, tIdVenta, detalle;

    public Button btnVerId;
    public VentaModel(View itemView) {
        super(itemView);
        tIdVenta = itemView.findViewById(R.id.tv_idVenta);
        btnVerId = itemView.findViewById(R.id.verId_button);
        nombreCliente = itemView.findViewById(R.id.name_cliente);
        fechaVenta = itemView.findViewById(R.id.fecha_text);
        tVenta = itemView.findViewById(R.id.tv_total_venta);
        detalle = itemView.findViewById(R.id.tv_detalle_venta);

    }

}

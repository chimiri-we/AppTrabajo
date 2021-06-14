package com.example.apptrabajo.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptrabajo.R;
import com.example.apptrabajo.datos.BaseDatosApp;
import com.example.apptrabajo.entidades.DetalleVenta;
import com.example.apptrabajo.entidades.Venta;
import com.example.apptrabajo.modelo.VentaDetalleModel;
import com.example.apptrabajo.modelo.VentaModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class VentaAdapter extends RecyclerView.Adapter<VentaModel>
        implements Filterable {

    private final Context context;
    private ArrayList listVenta;
    private final ArrayList<Venta> vArrayList;
    private final BaseDatosApp mDatabase;
   Venta venta;

    public VentaAdapter(Context context, ArrayList<Venta> listVenta) {
        this.context = context;
        this.listVenta = listVenta;
        this.vArrayList = listVenta;
        mDatabase = new BaseDatosApp(context);

    }

    @Override
    public Filter getFilter() {
        return null;
    }

    @NonNull
    @NotNull
    @Override
    public VentaModel onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_productos_mostrar, parent, false);
        return new VentaModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull VentaModel holder, int position) {

        final Venta newventa = (Venta) listVenta.get(position);
        holder.nombreCliente.setText(newventa.getNombre_cliente());

        holder.detalle.setText(newventa.getDetalle_venta());
        holder.fechaVenta.setText(newventa.getFecha());
        holder.tVenta.setText(String.valueOf(newventa.getTota_venta()));
        holder.tIdVenta.setText(String.valueOf(newventa.getId_venta()));



    }



    @Override
    public int getItemCount() {
        return listVenta.size();
    }
}

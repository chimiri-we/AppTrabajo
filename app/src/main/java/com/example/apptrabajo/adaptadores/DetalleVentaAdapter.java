package com.example.apptrabajo.adaptadores;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptrabajo.MainActivity;
import com.example.apptrabajo.R;
import com.example.apptrabajo.datos.BaseDatosApp;
import com.example.apptrabajo.entidades.DetalleVenta;
import com.example.apptrabajo.entidades.Productos;
import com.example.apptrabajo.entidades.Venta;
import com.example.apptrabajo.modelo.VentaDetalleModel;
import com.example.apptrabajo.ui.home.DetalleCliente;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class DetalleVentaAdapter extends RecyclerView.Adapter<VentaDetalleModel>
        implements Filterable {

    private final Context context;
    private ArrayList listProductos;
    private final ArrayList<DetalleVenta> mArrayList;
    private final BaseDatosApp mDatabase;

    public DetalleVentaAdapter(Context context, ArrayList<DetalleVenta> listProductos) {
        this.context = context;
        this.listProductos = listProductos;
        this.mArrayList = listProductos;
        mDatabase = new BaseDatosApp(context);
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    @NonNull
    @NotNull
    @Override
    public VentaDetalleModel onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_compras, parent, false);
        return new VentaDetalleModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull VentaDetalleModel holder, int position) {
        final DetalleVenta dtVenta = (DetalleVenta) listProductos.get(position);
        holder.tvCantidad.setText(dtVenta.getCantidad());

        holder.tvNameProducto.setText(dtVenta.getNombre_producto());
        holder.tvPrecioProducto.setText(dtVenta.getPrecio());
        holder.tvTotal.setText(dtVenta.getTotal());



        holder.tvNameProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id_detalle = ((DetalleVenta) listProductos.get(position)).getId_detalle();
            actualizarPedio(id_detalle);
            }
        });
      //  holder.editContact.setOnClickListener(view -> editTaskDialog(productos));

    }
    private void actualizarPedio(int id_detalle) {
        BaseDatosApp bdLocal = new BaseDatosApp(context.getApplicationContext());
        SQLiteDatabase db = bdLocal.getReadableDatabase();

        db.delete("DetalleVenta", "id_detalle" + " = ?", new String[]{String.valueOf(id_detalle)});  // Toast.makeText(this, "Se ha eliminado, actualiza la vista", Toast.LENGTH_LONG).show();
        ((Activity) context).finish();
        context.startActivity(((Activity)
                context).getIntent());
    }



    @Override
    public int getItemCount() {
        return listProductos.size();
    }
}

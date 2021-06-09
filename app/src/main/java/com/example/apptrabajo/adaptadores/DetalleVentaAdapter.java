package com.example.apptrabajo.adaptadores;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptrabajo.R;
import com.example.apptrabajo.datos.BaseDatosApp;
import com.example.apptrabajo.entidades.DetalleVenta;
import com.example.apptrabajo.entidades.Productos;
import com.example.apptrabajo.modelo.VentaModelo;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class DetalleVentaAdapter extends RecyclerView.Adapter<VentaModelo>
        implements Filterable {

    private final Context context;
    private ArrayList listProductos;
    private final ArrayList<DetalleVenta> mArrayList;
    private final BaseDatosApp mDatabase;
    DetalleVenta detalleVenta;

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
    public VentaModelo onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list_layout, parent, false);
        return new VentaModelo(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull VentaModelo holder, int position) {
        final DetalleVenta dtVenta = (DetalleVenta) listProductos.get(position);
        holder.tvCantidad.setText(dtVenta.getCantidad());

        holder.tvNameProducto.setText(dtVenta.getNombre_producto());
        holder.tvPrecioProducto.setText(dtVenta.getPrecio());
        holder.tvTotal.setText(dtVenta.getTotal());



      //  holder.editContact.setOnClickListener(view -> editTaskDialog(productos));

    }

    private void editTaskDialog(Productos productos) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.add_productos, null);
        final TextView editName = subView.findViewById(R.id.tvNombrePro);
        final TextView editPrecio = subView.findViewById(R.id.tvPrecio);
        final EditText editCantidad = subView.findViewById(R.id.tvtotal);
        // final  TextView tvCosto = subView.findViewById(R.id.costo);
        if (productos != null) {
            editName.setText(productos.getNombre());


        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Editar Nota");
        builder.setView(subView);
        builder.create();
        builder.setPositiveButton("EDITAR NOTA", (dialog, which) -> {
            final String name = editName.getText().toString();
            final String precio = editPrecio.getText().toString();
            final String total = editCantidad.getText().toString();

            if (TextUtils.isEmpty(name)) {
                Toast.makeText(context, "Algo salió mal. Verifique sus valores de entrada", Toast.LENGTH_LONG).show();
            } else {
                int sumaCant = Integer.parseInt(editPrecio.getText().toString());
                int sumaCost = Integer.parseInt(editCantidad.getText().toString());
                int costo = sumaCant * sumaCost;
                // tvCosto.setText(costo);

                mDatabase.updateProducto(new
                        DetalleVenta(detalleVenta.getId_producto(), detalleVenta.getNombre_producto(), precio));
                ((Activity) context).finish();
                context.startActivity(((Activity)
                        context).getIntent());
            }
        }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Tarea Cancelada", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }


    @Override
    public int getItemCount() {
        return listProductos.size();
    }
}

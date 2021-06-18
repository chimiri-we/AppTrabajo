package com.example.apptrabajo.adaptadores;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptrabajo.R;
import com.example.apptrabajo.datos.BaseDatosApp;
import com.example.apptrabajo.entidades.Clientes;
import com.example.apptrabajo.entidades.DetalleVenta;
import com.example.apptrabajo.entidades.Venta;
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
    DetalleVentaAdapter detalleVentaAdapter;
   Clientes clientes;
    int idCliente;
    int idVenta;
    TextView clienteNomb, clienteDire, clienteTel, ventaTotla, ventaId;
    RecyclerView listView;

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

        holder.dtIdCliente.setText(String.valueOf(newventa.getId_cliente()));
        holder.fechaVenta.setText(newventa.getFecha());
        holder.tVenta.setText(String.valueOf(newventa.getTota_venta()));
        holder.tIdVenta.setText(String.valueOf(newventa.getId_venta()));
        holder.btnVerId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   int idCliente = newventa.getId_cliente();

                int idVenta = newventa.getId_venta();
                Bundle bundle=new Bundle();

                Intent intent = new Intent(String.valueOf(idVenta));
                bundle.putInt("id_venta", newventa.getId_venta());


                intent.putExtras(bundle);
                verDetalleVenta(intent.putExtras(intent));
            }
        });



    }

    private void verDetalleVenta(Intent intent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.mostrar_detalle, null);
        listView = subView.findViewById(R.id.lista_venta);
         clienteNomb = subView.findViewById(R.id.dtNombre);
      clienteDire = subView.findViewById(R.id.dtDireccion);
     clienteTel = subView.findViewById(R.id.dtTelefono);
     ventaTotla = subView.findViewById(R.id.dtTotalVenta);
        ventaId = subView.findViewById(R.id.IdVenta);


     Bundle extras = intent.getExtras();
   //  idCliente = extras.getInt("id_cliente");
   // idCliente = (int) extras.getSerializable("id_cliente");
        idVenta = extras.getInt("id_venta");
        idVenta = (int) extras.getSerializable("id_venta");

    Toast.makeText(context, "el id es" + idVenta, Toast.LENGTH_LONG).show();

  /*   clientes = mDatabase.verCliente(idCliente);
        if (clientes != null) {

            clienteNomb.setText(clientes.getNombre());
            clienteDire.setText(clientes.getDireccion());
            clienteTel.setText(clientes.getTelefono());
        }*/
        venta = mDatabase.verVentaPorIdCliente(idVenta);
       if (venta != null) {

           idCliente = venta.getId_cliente();

            ventaTotla.setText(String.valueOf(venta.getTota_venta()));
           LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
           listView.setLayoutManager(linearLayoutManager);
           listView.setHasFixedSize(true);
           ArrayList<DetalleVenta> dtventa = mDatabase.listDetalleVenta(idVenta);
          detalleVentaAdapter = new DetalleVentaAdapter(context, dtventa);
          listView.setAdapter(detalleVentaAdapter);

          clientes = mDatabase.verCliente(idCliente);
           if (clientes != null) {

               clienteNomb.setText(clientes.getNombre());
               clienteDire.setText(clientes.getDireccion());
               clienteTel.setText(clientes.getTelefono());
           }

        }


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Detalle de venta");
        builder.setView(subView);
        builder.create();
        builder.setPositiveButton("EDITAR", (dialog, which) -> {
            Toast.makeText(context, "Algo salió mal. Verifique sus valores de entrada", Toast.LENGTH_LONG).show();

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
        return listVenta.size();
    }
}

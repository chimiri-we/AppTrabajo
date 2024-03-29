package com.example.apptrabajo;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apptrabajo.adaptadores.DetalleVentaAdapter;
import com.example.apptrabajo.datos.BaseDatosApp;
import com.example.apptrabajo.entidades.DetalleVenta;
import com.example.apptrabajo.entidades.Productos;

import java.util.ArrayList;

public class DetalleClienteFragment extends Fragment {

    BaseDatosApp bdLocal;
        Spinner spinnerPro;
    ArrayList<Productos> listaProducto;
    ArrayList<String> datosProducto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detalle_cliente, container, false);


        RecyclerView detalleVentaView = view.findViewById(R.id.lista_venta);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        detalleVentaView.setLayoutManager(linearLayoutManager);
        detalleVentaView.setHasFixedSize(true);
        bdLocal = new BaseDatosApp(getContext());
        ArrayList<DetalleVenta> detalleVenta = bdLocal.listDetalleVenta();
        if (detalleVenta.size() > 0) {
            detalleVentaView.setVisibility(View.VISIBLE);
           DetalleVentaAdapter detalleVentaAdapter = new DetalleVentaAdapter(getContext(), detalleVenta);
            detalleVentaView.setAdapter(detalleVentaAdapter);
        }
        else {
            detalleVentaView.setVisibility(View.GONE);
            Toast.makeText(getContext(), "No hay ningún contacto en la base de datos. Empiece a agregar ahora", Toast.LENGTH_LONG).show();
        }
    /*    Button btnAdd = view.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nuevointent = new Intent(getContext(), SeguimientoActivity.class);
                startActivity(nuevointent);
            }
        });*/
        return view;

    }





    private void addTaskDialog() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View subView = inflater.inflate(R.layout.add_productos, null);
        final TextView nameProducto = subView.findViewById(R.id.tvNombrePro);
        final TextView precio = subView.findViewById(R.id.tvPrecio);
        //final TextView tvCosto = subView.findViewById(R.id.costo);
        final EditText edTotal = subView.findViewById(R.id.tvtotal);
        spinnerPro = subView.findViewById(R.id.spinnerPro);
        bdLocal = new BaseDatosApp(getContext());

      //  cargarProductos();
        ArrayAdapter<CharSequence> adapterPro = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, datosProducto);
        spinnerPro.setAdapter(adapterPro);


      /*  seleccionarItem();
        spinnerPro.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contacts ListViewClickProduc = (Contacts) parent.getItemAtPosition(position);
                //    final String name = ListViewClickProduc.getName();
                // final String precio = ListViewClickProduc.getPrecio();
                Contacts contacts = null;
                assert contacts != null;
                ListViewClickProduc.setName(contacts.getName().toLowerCase());
                // ListViewClickProduc.setPrecio(contacts.getPhno().toLowerCase());
            }
        });

   /*   spinnerPro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Contacts ListViewClickProduc = (Contacts) parent.getItemAtPosition(position);
                //    final String name = ListViewClickProduc.getName();
                 // final String precio = ListViewClickProduc.getPrecio();
                Contacts contacts = null;
                assert contacts != null;
                ListViewClickProduc.setName(contacts.getName().toLowerCase());
               // ListViewClickProduc.setPrecio(contacts.getPhno().toLowerCase());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                return;
            }

        });*/
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Agragar nueva Nota");
        builder.setView(subView);
        builder.create();
        builder.setPositiveButton("Agregar Nota", (dialog, which) -> {


            final String name = nameProducto.getText().toString();
            final String ph_no = precio.getText().toString();
        /*  int dato1 = Integer.parseInt(precio.getText().toString());
          int dato2 = Integer.parseInt(edTotal.getText().toString());
          int suma = dato1 * dato2;
          String resultado = String.valueOf(suma);
        //  TextView tvCosto = findViewById(R.id.costo);

          tvCosto.setText(resultado);*/
//         galleryViewModel.sumarPrecio();


            if (TextUtils.isEmpty(name)) {
                Toast.makeText(getContext(), "Algo salió mal. Verifique sus valores de entrada", Toast.LENGTH_LONG).show();
            }
            else {
                Productos newProducto = new Productos(name, ph_no);
                bdLocal.addProductos(newProducto);


               // finish();
               // startActivity(getIntent());
                // onStart();
                // notifyDataSetChanged();
                //  getDelegate().onStart();
                //    obtenerCosto();
            }

        });
        //  finish();


        builder.setNegativeButton("CANCELAR", (dialog, which) -> Toast.makeText(getContext(), "Tarea Cancelada", Toast.LENGTH_LONG).show());
        builder.show();
        onStart();


    }


    private void seleccionarItem() {



    }

 /*   private void cargarProductos() {
        SQLiteDatabase db=bdLocal.getReadableDatabase();

        Productos producto=null;
        listaProducto = new ArrayList<Productos>();
        Cursor cursor=db.rawQuery( "select * from Productos ", null);
        while (cursor.moveToNext()){
            producto=new Productos();
            producto.setId(cursor.getInt(0));
            producto.setNombre(cursor.getString(1));
            producto.setPrecio(cursor.getString(2));
            listaProducto.add(producto);
        }
        obtenerLista();

    }*/

    private void obtenerLista() {
        datosProducto = new ArrayList<String>();
        datosProducto.add("selecciona");

        for (int i=0;i<listaProducto.size();i++) {
            datosProducto.add(listaProducto.get(i).getNombre()+" $ "+listaProducto.get(i).getPrecio());
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (bdLocal != null) {
            bdLocal.close();
        }



    }

    public void agregarProducto() {
        LayoutInflater inflater = LayoutInflater.from(requireActivity());
        View subView = inflater.inflate(R.layout.add_productos, null);
        final TextView nameField = subView.findViewById(R.id.tvNombrePro);
        final TextView noField = subView.findViewById(R.id.tvPrecio);
        final EditText edTotal = subView.findViewById(R.id.tvtotal);
        spinnerPro = subView.findViewById(R.id.spinnerPro);

        AlertDialog.Builder builder = new AlertDialog.Builder(this.requireContext());
        builder.setTitle("Agragar nueva Nota");
        builder.setView(subView);
        builder.create();
        builder.setPositiveButton("Agregar Nota", (dialog, which) -> {




    });
        builder.setNegativeButton("CANCELAR", (dialog, which) -> Toast.makeText(getContext(), "Tarea Cancelada", Toast.LENGTH_LONG).show());
        builder.show();
        onStart();
    }
}
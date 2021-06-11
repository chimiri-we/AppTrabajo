package com.example.apptrabajo.ui.home;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteAccessPermException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.apptrabajo.DetalleClienteFragment;
import com.example.apptrabajo.R;
import com.example.apptrabajo.datos.BaseDatosApp;
import com.example.apptrabajo.entidades.Clientes;
import com.example.apptrabajo.entidades.DetalleVenta;
import com.example.apptrabajo.entidades.Productos;
import com.example.apptrabajo.entidades.Venta;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.LinkedTransferQueue;

public class DetalleCliente extends AppCompatActivity {

    private static final String TABLE_DETALLE_VENTA = "DetalleVenta";

    private static final String COLUMN_ID_VENTA = "id_venta";
    private static final String COLUMN_ID_PRODUCTO = "id_producto";
    private static final String TABLE_VENTA = "Venta";
    ArrayList<Productos> arrayList = new ArrayList<Productos>();
    ArrayList<String> strinsProducto;
    private Context context;
    Productos productos;
    DetalleVenta dtv;
    Clientes clientes;
    Venta venta;
    int id = 0;
    BaseDatosApp bdLocal;
    TextView t1,t2,t3,t4,t5,t6;
    Spinner spinnerPro;
    FloatingActionButton btnAgregar;
    public DetalleCliente() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        t1 = (TextView) findViewById(R.id.text11);
        t3 = (TextView) findViewById(R.id.text3);
        t2 = (TextView) findViewById(R.id.text22);
        t4 = (TextView) findViewById(R.id.textView12);
        btnAgregar = findViewById(R.id.fab_Agregar_producto);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                agregarProducto();

            }
        });

        Fragment fragmentL = new DetalleClienteFragment();
       // Fragment fragmentR = new SlideshowFragment();
        //  fragmentP = new RecuperarPasswordFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.contenedorFragment, fragmentL).commit();
        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null){
                id = Integer.parseInt(null);
            } else {
                id = extras.getInt("id");
            }
        } else {
            id = (int) savedInstanceState.getSerializable("id");
        }

        final BaseDatosApp bdLocal = new BaseDatosApp(DetalleCliente.this);
        clientes = bdLocal.verCliente(id);

        if(clientes != null){
        //int ID = getIntent().getIntExtra("id", ID)
      /*  String cliente = getIntent().getStringExtra("id");
        Bundle extras = getIntent().getExtras();
        id = extras.getInt(cliente);

        bdLocal = new BaseDatosApp(DetalleCliente.this);
        clientes = bdLocal.verCliente(id);

      */
            t1.setText(clientes.getNombre());
        //    t2.setText(clientes.getTelefono());
            t3.setText(clientes.getDireccion());
          //
            generarVenta();
        }
    }

    private void generarVenta() {
        Venta venta = new Venta();
        ContentValues values = new ContentValues();
        int idCliente = clientes.getId();
        String nombreCliente = clientes.getNombre();
        String dtVenta = t1.getText().toString();
        {
        //values.put(COLUMN_ID_CLI, cliente.getId());
        values.put("id_cliente", idCliente);
        values.put("nombre_cliente", nombreCliente);
//        values.put("fecha", venta.getFecha());

        values.put("id_detalle", dtVenta);
        bdLocal = new BaseDatosApp(this.getApplicationContext());
        SQLiteDatabase db = bdLocal.getReadableDatabase();
            Toast.makeText(this, "Datos guardados"+ SQLiteAccessPermException.class, Toast.LENGTH_SHORT).show();

            db.insert(TABLE_VENTA, null, values);
        }
    }

    private void agregarProducto() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View subView = inflater.inflate(R.layout.add_productos, null);
        final TextView nameField = subView.findViewById(R.id.tvNombrePro);
        final TextView noField = subView.findViewById(R.id.tvPrecio);
        final EditText edCantidad = subView.findViewById(R.id.tvCantidad);
 //       final TextView tvTotal = subView.findViewById(R.id.total);


        spinnerPro = subView.findViewById(R.id.spinnerPro);
        bdLocal = new BaseDatosApp(this.getApplicationContext());
        spinerProducto();
        ArrayAdapter<CharSequence> adapterPro = new ArrayAdapter(this, android.R.layout.simple_spinner_item, strinsProducto);
        spinnerPro.setAdapter(adapterPro);

        spinnerPro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 if (position!=0) {
                    nameField.setText(arrayList.get(position-1).getNombre());
                    noField.setText(arrayList.get(position-1).getPrecio());

                }else {
                    nameField.setText("");
                    noField.setText("");
                      }

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;

            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Agragar un Producto");
        builder.setView(subView);
        builder.create();
        builder.setPositiveButton("Agregar", (dialog, which) -> {

            final String nombrePro = nameField.getText().toString();
            final String precio = noField.getText().toString();
            final String cantidad = edCantidad.getText().toString();
            int dato1 = Integer.parseInt(noField.getText().toString());
            int dato2 = Integer.parseInt(edCantidad.getText().toString());
            int suma = dato1 * dato2;
            String resultado = String.valueOf(suma);
//            tv.setText(resultado);

            Toast.makeText(this, "El total es" + suma, Toast.LENGTH_LONG).show();
           // final String COLUMN_ID_VENTA = "id_venta";
           // final String COLUMN_ID_PRODUCTO = "id_producto";

            if (TextUtils.isEmpty(nombrePro)) {
                Toast.makeText(this, "Algo salió mal. Verifique sus valores de entrada", Toast.LENGTH_LONG).show();
            }
            else {
                DetalleVenta newVenta = new DetalleVenta(nombrePro, precio, cantidad, resultado);
                bdLocal.agregaProductos(newVenta);


                finish();
                startActivity(getIntent());
            }
        });
        builder.setNegativeButton("CANCELAR", (dialog, which) -> Toast.makeText(this, "Tarea Cancelada", Toast.LENGTH_LONG).show());
        builder.show();
        onStart();
    }



    private void spinerProducto() {
            //   SQLiteOpenHelper sql = bdLocal.getReadableDatabase();
            SQLiteDatabase db = bdLocal.getWritableDatabase();
            Productos producto=null;
            arrayList = new ArrayList<Productos>();
            Cursor cursor=db.rawQuery( "select * from Producto", null);
            while (cursor.moveToNext()){
                producto=new Productos();
                producto.setId(cursor.getInt(0));
                producto.setNombre(cursor.getString(1));
                producto.setPrecio(cursor.getString(2));
                arrayList.add(producto);
            }
            obtenerLista();


        }

        public void obtenerLista() {

            strinsProducto = new ArrayList<String>();
            strinsProducto.add("selecciona");

            for (int i=0;i<arrayList.size();i++) {
                strinsProducto.add(arrayList.get(i).getNombre()+" $ "+arrayList.get(i).getPrecio());
            }
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detalle, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                showEditScreen();
                break;
            case R.id.action_delete:

                bdLocal.deleteContact(id);
                Toast.makeText(this, "Se ha eliminado, actualiza la vista", Toast.LENGTH_LONG).show();


        break;
        }
        return super.onOptionsItemSelected(item);
    }



    private void showEditScreen() {

        int vt = 0;
        SQLiteDatabase db = bdLocal.getWritableDatabase();
        DetalleVenta dtVenta=null;
        Cursor cursor=db.rawQuery( "select SUM(total) from DetalleVenta", null);
        if (cursor.moveToNext()) {
            vt = cursor.getInt(0);
        }
        db.close();


        Toast.makeText(this, "Total es "+cursor.getInt(0), Toast.LENGTH_LONG).show();
        System.out.println();
        Log.d("Respuesta: ", cursor.toString());

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }



}


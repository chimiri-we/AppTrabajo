package com.example.apptrabajo.ui.home;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.example.apptrabajo.MainActivity;
import com.example.apptrabajo.R;
import com.example.apptrabajo.datos.BaseDatosApp;
import com.example.apptrabajo.entidades.Clientes;
import com.example.apptrabajo.entidades.DetalleVenta;
import com.example.apptrabajo.entidades.Productos;
import com.example.apptrabajo.entidades.Venta;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

public class DetalleCliente extends AppCompatActivity {



    private static final String TABLE_VENTA = "Venta";
    ArrayList<Productos> arrayList = new ArrayList<Productos>();
    ArrayList<String> strinsProducto;

    Clientes clientes;

    int totalV;
    int id = 0;
    String nombreCli;
    BaseDatosApp bdLocal;
    TextView  fecha,direccion, telefono, diaVisita, codDetalle;
    TextView tvTotal;
    String formattedDate;
    Spinner spinnerPro;

    FloatingActionButton btnAgregar;
    CollapsingToolbarLayout collapser;
    public DetalleCliente() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.getBackground().setAlpha(0);
     collapser = findViewById(R.id.toolbar_layout);

        //obtenemos fecha y hora

        fecha = (TextView) findViewById(R.id.tv_fecha);
        Date fechaActual= Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        String formattedDate = df.format(fechaActual);
        System.out.println("Current time => " +formattedDate);

        fecha.setText(formattedDate);


      codDetalle = findViewById(R.id.tv_numeroPedido);

      tvTotal = findViewById(R.id.mostrarTotal);
        direccion = findViewById(R.id.tv_direccion);
        telefono = findViewById(R.id.tv_numeroTel);
        diaVisita = findViewById(R.id.tvDia_visita);
        tvTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerTotalVenta();
            }
        });





        btnAgregar = findViewById(R.id.fab_Agregar_producto);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                agregarProducto();
             //   generateNumeroPedido();

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
//             cod = extras.getString("cod");
            }

        } else {
            id = (int) savedInstanceState.getSerializable("id");
           //cod = savedInstanceState.getString("cod");
          //  codDetalle.setText(String.valueOf(id));
        }
       // codDetalle.setText(id);
        final BaseDatosApp bdLocal = new BaseDatosApp(DetalleCliente.this);
        clientes = bdLocal.verCliente(id);

        if(clientes != null){

           codDetalle.setText(String.valueOf(id));
            diaVisita.setText(clientes.getDiaVisita());
           telefono.setText(clientes.getTelefono());
            direccion.setText(clientes.getDireccion());
            collapser.setTitle(clientes.getNombre());

            nombreCli = collapser.getTitle().toString().trim();
         //   obtenerTotalVenta();
            Venta idVen = bdLocal.ultimaVenta();
            //   String idVenta = String.valueOf(idVen.getId_venta());
            int idventa = 1+(idVen.getId_venta());
            TotalVenta(idventa);
          //

        }
    }

    private void TotalVenta(int idventa) {
        bdLocal = new BaseDatosApp(this.getApplicationContext());
        // SQLiteDatabase db = bdLocal.getWritableDatabase();
        DetalleVenta dtVenta = bdLocal.sumarItems(idventa);

        tvTotal.setText(dtVenta.getTotal());
    }


    public void agregarProducto() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View subView = inflater.inflate(R.layout.add_productos, null);
        final TextView nameField = subView.findViewById(R.id.tvNombrePro);
        final TextView precio = subView.findViewById(R.id.tvPrecio);
        final EditText edCantidad = subView.findViewById(R.id.tvCantidad);
        final TextView Total = findViewById(R.id.tvTotal);

//inflamos spiner con lista de productos
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
                    precio.setText(arrayList.get(position-1).getPrecio());

                }else {
                    nameField.setText("");
                    precio.setText("");
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

            Venta idVen = bdLocal.ultimaVenta();
         //   String idVenta = String.valueOf(idVen.getId_venta());
            int idventa = 1+(idVen.getId_venta());
            final String nombrePro = nameField.getText().toString();
            final String precioProd = precio.getText().toString();
            final String cantidad = edCantidad.getText().toString();

            int dato1 = Integer.parseInt(precio.getText().toString());
            int dato2 = Integer.parseInt(edCantidad.getText().toString());
            int suma = dato1 * dato2;
            String resultado = String.valueOf(suma);



            if (TextUtils.isEmpty(nombrePro)) {
                Toast.makeText(this, "Algo salió mal. Verifique sus valores de entrada", Toast.LENGTH_LONG).show();
            }
            else {
                DetalleVenta newDetalleVenta = new DetalleVenta(idventa, nombrePro, precioProd, cantidad, resultado);
                bdLocal.agregaProductos(newDetalleVenta);

                Toast.makeText(this, "El id de venta es" +idventa, Toast.LENGTH_LONG).show();

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
                producto.setNombre(cursor.getString(2));
                producto.setPrecio(cursor.getString(3));
                arrayList.add(producto);
            }
            obtenerLista();


        }

        public void obtenerLista() {

            strinsProducto = new ArrayList<String>();
            strinsProducto.add("selecciona");

            for (int i=0;i<arrayList.size();i++) {
                strinsProducto.add(arrayList.get(i).getNombre());
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
                ventaNueva();

                break;
            case R.id.action_delete:

                actualizarPedio();

        break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void ventaNueva() {

        Date fechaActual= Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        formattedDate = df.format(fechaActual);
       // nombreCli = clientes.getNombre();
        totalV = Integer.parseInt(tvTotal.getText().toString().trim());

        Venta venta = new Venta(nombreCli, id, formattedDate, totalV);
        bdLocal.generarVenta(venta);
     //   actualizarPedio();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        Toast.makeText(this, "el total es" + totalV, Toast.LENGTH_SHORT).show();


    }

    private void actualizarPedio() {
        bdLocal = new BaseDatosApp(this.getApplicationContext());
        SQLiteDatabase db = bdLocal.getReadableDatabase();

        db.delete("DetalleVenta", "id_producto" + " = ?", new String[]{String.valueOf(0)});
       // Toast.makeText(this, "Se ha eliminado, actualiza la vista", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public void obtenerTotalVenta() {

        int vt = 0;
      tvTotal = findViewById(R.id.mostrarTotal);
        bdLocal = new BaseDatosApp(this.getApplicationContext());
        SQLiteDatabase db = bdLocal.getWritableDatabase();
        DetalleVenta dtVenta=null;
        Cursor cursor=db.rawQuery( "select SUM(total) from DetalleVenta", null);
        if (cursor.moveToNext()) {
            DetalleVenta detalleVenta = new DetalleVenta();
            vt = cursor.getInt(0);
            ContentValues values = new ContentValues();
            values.put("total", cursor.getInt(0));

            tvTotal.setText(String.valueOf(+vt).toString());



        }
        db.close();

       // Toast.makeText(this, "Total es "+cursor.getInt(0), Toast.LENGTH_LONG).show();
       // System.out.println();
       // Log.d("Respuesta: ", cursor.toString());

    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }



}


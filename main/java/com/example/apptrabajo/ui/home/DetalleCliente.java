package com.example.apptrabajo.ui.home;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

public class DetalleCliente extends AppCompatActivity {

    ArrayList<Productos> arrayList = new ArrayList<Productos>();
    ArrayList<String> strinsProducto;
    private Context context;
    Clientes clientes;
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
        //setSupportActionBar(toolbar);
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
          //  t4.setText(clientes.getDiaVisita());
        }
    }

    private void agregarProducto() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View subView = inflater.inflate(R.layout.add_productos, null);
        final TextView nameField = subView.findViewById(R.id.tvNombrePro);
        final TextView noField = subView.findViewById(R.id.tvPrecio);
        final EditText edCantidad = subView.findViewById(R.id.tvCantidad);

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
        builder.setTitle("Agragar nueva Nota");
        builder.setView(subView);
        builder.create();
        builder.setPositiveButton("Agregar Nota", (dialog, which) -> {
            final String nombrePro = nameField.getText().toString();
            final String precio = noField.getText().toString();
            final String cantidad = edCantidad.getText().toString();

            if (TextUtils.isEmpty(nombrePro)) {
                Toast.makeText(this, "Algo salió mal. Verifique sus valores de entrada", Toast.LENGTH_LONG).show();
            }
            else {
                DetalleVenta newVenta = new DetalleVenta(nombrePro, precio, cantidad);
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
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }



}


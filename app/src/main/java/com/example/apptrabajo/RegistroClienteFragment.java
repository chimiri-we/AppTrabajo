package com.example.apptrabajo;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.apptrabajo.datos.BaseDatosApp;
import com.example.apptrabajo.entidades.Clientes;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistroClienteFragment extends Fragment {

    public static final String REGISTER_URL = "https://servicioparanegocio.es/superClean/registro_cliente.php";

    public static final String KEY_NOMBRE = "nombre";
    public static final String KEY_DIA_VISITA = "dia_visita";
    public static final String KEY_DIRECCION = "direccion";
    public static final String KEY_NUMEROTELEFONO = "telefono";


    private static final String TABLE_CLIENTE = "Cliente";
    private EditText edtNombreCliente, edtNumeroCliente, edtDireccion, edtColonia;

    private ProgressDialog progressDialog;
    Spinner spinner;
    private RequestQueue requestQueue;

    BaseDatosApp bdLocal;
    Clientes cliente;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     View v = inflater.inflate(R.layout.fragment_registro_cliente, container, false);

        edtNombreCliente = v.findViewById(R.id.edt_nombre);
        edtNumeroCliente = v.findViewById(R.id.edt_telefono);
        edtDireccion = v.findViewById(R.id.edt_direccion);
        edtColonia = v.findViewById(R.id.edt_colonia);
        spinner = v.findViewById(R.id.spinner);

        Button btnRegistroCliente = v.findViewById(R.id.btn_registro);

        btnRegistroCliente.setOnClickListener(v1 -> registroCliente());


        return v;
    }


    private void registroCliente()  {
        final String nombre = edtNombreCliente.getText().toString().trim();
        final String direccion = edtDireccion.getText().toString().trim();
        final String colonia = edtColonia.getText().toString().trim();
        final String telefono = edtNumeroCliente.getText().toString().trim();
        final String dia_visita = edtNumeroCliente.getText().toString().trim();

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Cargando..");
        progressDialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                response -> {
                    Toast.makeText(getContext(), "Registro exitoso", Toast.LENGTH_SHORT).show();

                    cliente=new Clientes();
                  //  cliente.setId_Remoto(jsonObject.optInt("Id"));
                    cliente.setNombre(nombre);
                    cliente.setTelefono(telefono);
                    cliente.setDireccion(direccion);
                    cliente.setColonia(colonia);
                    cliente.setDiaVisita(dia_visita);

                    ContentValues values = new ContentValues();
                   // values.put("id_Remoto", cliente.getId_Remoto());
                    values.put("nombre", cliente.getNombre());
                    values.put("telefono", cliente.getTelefono());
                    values.put("direccion", cliente.getDireccion());
                    values.put("colonia", cliente.getColonia());
                    values.put("dia_visita", cliente.getDiaVisita());

                    bdLocal = new BaseDatosApp(requireContext().getApplicationContext());
                    SQLiteDatabase db = bdLocal.getReadableDatabase();
                    if(db!= null) {
                        Toast.makeText(getContext(), "Datos guardados", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getContext(), "Tienes un problema" +
                                " "+response, Toast.LENGTH_SHORT).show();
                    }
                    db.insert(TABLE_CLIENTE, null, values);




                    progressDialog.hide();

//                    edtPasswordCliente.setText("");
                    edtColonia.setText("");
                    edtNumeroCliente.setText("");
                    edtNombreCliente.setText("");
                    edtDireccion.setText("");
                },
                error -> {
                    progressDialog.hide();
                    Toast.makeText(getContext(), "Nose puede Registrar"+error.toString(), Toast.LENGTH_LONG).show();
                    Log.i("ERROR", error.toString());
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put(KEY_NOMBRE, nombre);
                params.put(KEY_DIA_VISITA, dia_visita);
                params.put(KEY_DIRECCION, direccion);
                params.put(KEY_NUMEROTELEFONO, telefono);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}

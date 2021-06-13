package com.example.apptrabajo;

import android.app.ProgressDialog;
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

import java.util.HashMap;
import java.util.Map;

public class RegistroClienteFragment extends Fragment {

    public static final String REGISTER_URL = "https://servicioparanegocio.es/bdApp/registroUseruno.php";

    public static final String KEY_NOMBRE = "nombre";
    public static final String KEY_DIA_VISITA = "password";
    public static final String KEY_DIRECCION = "email";
    public static final String KEY_NUMEROTELEFONO = "telefono";

    private EditText edtNombreCliente, edtNumeroCliente, edtDireccion, edtPasswordCliente;

    private ProgressDialog progressDialog;
    Spinner spinner;
    private RequestQueue requestQueue;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     View v = inflater.inflate(R.layout.fragment_registro_cliente, container, false);

        edtNombreCliente = v.findViewById(R.id.edt_nombre);
        edtNumeroCliente = v.findViewById(R.id.edt_telefono);
        edtDireccion = v.findViewById(R.id.edt_direccion);
        spinner = v.findViewById(R.id.spinner);

        Button btnRegistroCliente = v.findViewById(R.id.btn_registro);

        btnRegistroCliente.setOnClickListener(v1 -> registroCliente());


        return v;
    }


    private void registroCliente()  {
        final String nombre = edtNombreCliente.getText().toString().trim();
        final String direccion = edtDireccion.getText().toString().trim();
        final String telefono = edtNumeroCliente.getText().toString().trim();
        final String dia_visita = edtNumeroCliente.getText().toString().trim();

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Cargando..");
        progressDialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                response -> {
                    Toast.makeText(getContext(), "Registro exitoso", Toast.LENGTH_SHORT).show();
                    progressDialog.hide();
//                    edtPasswordCliente.setText("");
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

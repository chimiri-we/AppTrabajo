package com.example.apptrabajo.ui.home;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteAccessPermException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.apptrabajo.api.Coneccion;
import com.example.apptrabajo.databinding.FragmentHomeBinding;
import com.example.apptrabajo.datos.BaseDatosApp;
import com.example.apptrabajo.entidades.Clientes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class HomeFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {



    private FragmentHomeBinding binding;
    ArrayList<String> listaCliente;
    ArrayList<Clientes> nuevaListaCliente;
    BaseDatosApp bdLocal;
    ListView listViewOriginal;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    private static final String TABLE_CLIENTE = "Cliente";
    private static final String COLUMN_NOMBRE = "nombre";
    private static final String COLUMN_DIRECCION = "direccion";
    private static final String COLUMN_TELEFONO = "telefono";
    private static final String COLUMN_DIA_VISITA = "dia_visita";

    Coneccion conn;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        listViewOriginal = binding.produclis;



        bdLocal = new BaseDatosApp(getContext());
        consultarClientes();
        ArrayAdapter<CharSequence> adapterCliente = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, listaCliente);
        listViewOriginal.setAdapter(adapterCliente);

        if (nuevaListaCliente.size() > 0) {

           adapterCliente.setDropDownViewResource(View.VISIBLE);
            listViewOriginal.setAdapter(adapterCliente);
       }
        else {
            adapterCliente.setDropDownViewResource(View.GONE);
            Toast.makeText(getContext(),"no hay datos", Toast.LENGTH_SHORT).show();


            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

            builder.setTitle("Editar Nota");
            builder.create();
            builder.setPositiveButton("Actualizar", (dialog, which) -> {
               cargarWebService();
               // finish();
              //  startActivity(getContext());

            });
            request= Volley.newRequestQueue(requireContext());
            builder.show();
            onStart();

        }

        listViewOriginal.setHasTransientState(true);
        listViewOriginal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
       // Clientes ListViewClickUser = (Clientes) parent.getItemAtPosition(position);
       String cliente =""+nuevaListaCliente.get(position).getId();

     //  int ID = nuevaListaCliente.get(position).getId();
        // printing clicked item on screen using Toast message.
        Toast.makeText(getContext(), cliente, Toast.LENGTH_SHORT).show();


      Bundle bundle=new Bundle();
        bundle.putInt("id", Integer.parseInt(cliente));
        Intent intent = new Intent(getContext(), DetalleCliente.class);
        intent.putExtras(bundle);
        startActivity(intent);

/*
        Intent intent = new Intent(getContext(), DetalleCliente.class);
        // Pass all data rank
        intent.putExtra("nombre",ListViewClickUser.getNombre());

        intent.putExtra("id",ListViewClickUser.getId());
        // Pass all data country
        intent.putExtra("telefono",ListViewClickUser.getTelefono());
        intent.putExtra("direccion",ListViewClickUser.getDireccion());
        intent.putExtra("dia_visita",ListViewClickUser.getDiaVisita());
        startActivity(intent);*/
    }
});


        return root;
    }

    private void cargarWebService() {
        String url="https://servicioparanegocio.es/superClean/consultarUsuario.php?";
        //String url="https://servicioparanegocio.es/superClean/consultarUsuario.php?dia_visita=jueves";
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null, this, this);
        request.add(jsonObjectRequest);
        //  VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
    }


    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), "No se puede conectar "+error.toString(), Toast.LENGTH_LONG).show();
        System.out.println();
        Log.d("ERROR: ", error.toString());

    }


    public void onResponse(JSONObject response) {
        Clientes cliente=null;

        JSONArray json=response.optJSONArray("usuario");

        try {

            for (int i = 0; i< Objects.requireNonNull(json).length(); i++){
                cliente=new Clientes();
                JSONObject jsonObject=null;
                jsonObject=json.getJSONObject(i);
                cliente.setId(jsonObject.optInt("Id"));
                cliente.setNombre(jsonObject.optString("nombre"));
                cliente.setTelefono(jsonObject.optString("telefono"));
                cliente.setDireccion(jsonObject.optString("direccion"));
                cliente.setDiaVisita(jsonObject.getString("dia_visita"));

                ContentValues values = new ContentValues();
                //values.put(COLUMN_ID_CLI, cliente.getId());
                values.put(COLUMN_NOMBRE, cliente.getNombre());
                values.put(COLUMN_TELEFONO, cliente.getTelefono());
                values.put(COLUMN_DIRECCION, cliente.getDireccion());
                values.put(COLUMN_DIA_VISITA, cliente.getDiaVisita());
                bdLocal = new BaseDatosApp(requireContext().getApplicationContext());
                SQLiteDatabase db = bdLocal.getReadableDatabase();
                if(db!= null) {
                    Toast.makeText(getContext(), "Datos guardados"+ SQLiteAccessPermException.class, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getContext(), "Tienes un problema" +
                            " "+response, Toast.LENGTH_SHORT).show();
                }
                db.insert(TABLE_CLIENTE, null, values);
            }




        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "No se ha podido establecer conexión con el servidor" +
                    " "+response, Toast.LENGTH_LONG).show();

        }

        consultarClientes();
    }



    private void consultarClientes() {
        SQLiteDatabase db = bdLocal.getReadableDatabase();
        Clientes clientes=null;
        nuevaListaCliente = new ArrayList<Clientes>();
        Cursor cursor = db.rawQuery("select * from Cliente", null);
        while (cursor.moveToNext()) {
            clientes=new Clientes();
            clientes.setId(cursor.getInt(0));
            clientes.setNombre(cursor.getString(1));
            clientes.setTelefono(cursor.getString(2));
            clientes.setDireccion(cursor.getString(3));
            clientes.setDiaVisita(cursor.getString(4));
            nuevaListaCliente.add(clientes);
        }
        obtenerlista();
    }

    private void obtenerlista() {
        listaCliente = new ArrayList<String>();
        for (int i=0;i<nuevaListaCliente.size();i++) {
            listaCliente.add(nuevaListaCliente.get(i).getNombre()+" - "+nuevaListaCliente.get(i).getDiaVisita());
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
package com.example.apptrabajo.ui.home;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
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
import com.example.apptrabajo.R;
import com.example.apptrabajo.adaptadores.ClientesAdapter;
import com.example.apptrabajo.databinding.FragmentHomeBinding;
import com.example.apptrabajo.datos.BaseDatosApp;
import com.example.apptrabajo.entidades.Clientes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class HomeFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {



    ProgressDialog progress;
    private FragmentHomeBinding binding;
    ArrayList<String> listaCliente;
    ArrayList<Clientes> nuevaListaCliente;
    BaseDatosApp bdLocal;
    ListView listViewOriginal;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    private static final String TABLE_CLIENTE = "Cliente";
ImageView btn;
    
    ClientesAdapter clientesAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        listViewOriginal = binding.produclis;
      //  btn = binding.button2;



        bdLocal = new BaseDatosApp(getContext());
        consultarClientes();
     //   ArrayAdapter<CharSequence> adapterCliente = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, listaCliente);
     //   listViewOriginal.setAdapter(adapterCliente);

        if (nuevaListaCliente.size() > 0) {

            clientesAdapter.setDropDownViewResource(View.VISIBLE);
            listViewOriginal.setAdapter(clientesAdapter);
         //  adapterCliente.setDropDownViewResource(View.VISIBLE);
         //   listViewOriginal.setAdapter(adapterCliente);
       }
        else {
            clientesAdapter.setDropDownViewResource(View.GONE);
         //   Toast.makeText(getContext(),"no hay datos", Toast.LENGTH_SHORT).show();

            View subView = inflater.inflate(R.layout.dialogo_actualizar_datos, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

            builder.setTitle("Actualiza tu base de Datos");
            builder.setMessage("No tienes tu lista actualizada, porfavor actualiza");
            builder.setView(subView);
            builder.create();
            builder.setPositiveButton("Actualizar", (dialog, which) -> {
               cargarWebService();

               // finish();
              //  startActivity(getContext());

            });
            request= Volley.newRequestQueue(requireContext());
            builder.show();
          //  Intent intent = new Intent(getContext(), MainActivity.class);
         //   startActivity(intent);

        }

        listViewOriginal.setHasTransientState(true);
        listViewOriginal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

       String cliente =""+nuevaListaCliente.get(position).getId_cliente();
    //   String codDetalle = "cod_"+(nuevaListaCliente.get(position).getId_cliente()+1);

    //    Toast.makeText(getContext(), "id del cliente es " + codDetalle, Toast.LENGTH_SHORT).show();


      Bundle bundle=new Bundle();

        bundle.putInt("id", Integer.parseInt(cliente));
      //  bundle.putString("cod", codDetalle);
        Intent intent = new Intent(getContext(), DetalleCliente.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }
});

      //  btn.setOnClickListener(v -> actualizarDatos());

        return root;
    }

    public void actualizarDatos() {

        bdLocal = new BaseDatosApp(requireContext().getApplicationContext());
        SQLiteDatabase db = bdLocal.getReadableDatabase();
        db.execSQL("delete from " + TABLE_CLIENTE);
        Toast.makeText(getContext(), "Se ha eliminado, actualiza la vista", Toast.LENGTH_LONG).show();
        onStart();
    }

    public void cargarWebService() {
        progress=new ProgressDialog(getContext());
        progress.setMessage("Consultando...");
        progress.show();
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
        progress.hide();

    }


    public void onResponse(JSONObject response) {
        Clientes cliente=null;

        JSONArray json=response.optJSONArray("usuario");

        try {

            for (int i = 0; i< Objects.requireNonNull(json).length(); i++){
                cliente=new Clientes();
                JSONObject jsonObject=null;
                jsonObject=json.getJSONObject(i);
                cliente.setId_Remoto(jsonObject.optInt("Id"));
                cliente.setNombre(jsonObject.optString("nombre"));
                cliente.setTelefono(jsonObject.optString("telefono"));
                cliente.setDireccion(jsonObject.optString("direccion"));
                cliente.setColonia(jsonObject.optString("colonia"));
                cliente.setDiaVisita(jsonObject.getString("dia_visita"));

                ContentValues values = new ContentValues();
                values.put("id_Remoto", cliente.getId_Remoto());
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
            }

            progress.hide();



        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Verifica tu coneccion", Toast.LENGTH_LONG).show();

        }

        consultarClientes();

    }



    public void consultarClientes() {
        SQLiteDatabase db = bdLocal.getReadableDatabase();
        Clientes clientes=null;
        nuevaListaCliente = new ArrayList<Clientes>();
        Cursor cursor = db.rawQuery("select * from Cliente", null);
        while (cursor.moveToNext()) {
            clientes=new Clientes();
            clientes.setId_cliente(cursor.getInt(0));
            clientes.setNombre(cursor.getString(2));
            clientes.setTelefono(cursor.getString(5));
            clientes.setDireccion(cursor.getString(3));
            clientes.setColonia(cursor.getString(4));
            clientes.setDiaVisita(cursor.getString(6));
            nuevaListaCliente.add(clientes);
        }
        clientesAdapter = new ClientesAdapter(getContext(), R.layout.usuarios_list, nuevaListaCliente);
        listViewOriginal.setAdapter(clientesAdapter);
      //  obtenerlista();
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
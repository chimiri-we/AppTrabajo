package com.example.apptrabajo.api;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteAccessPermException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.apptrabajo.datos.BaseDatosApp;
import com.example.apptrabajo.entidades.Clientes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class Coneccion extends JSONObject implements Response.Listener<JSONObject>, Response.ErrorListener {

    private static final String DATABASE_NAME = "Superclean";
    private static final String TABLE_CLIENTE = "Cliente";
    private static final String COLUMN_ID_CLI = "id";
    private static final String COLUMN_NAME_CLI = "nombre";
    private static final String COLUMN_DIRECCION = "direccion";
    private static final String COLUMN_TELEFONO = "telefono";
    private static final String COLUMN_DIA_VISITA = "dia_visita";
    BaseDatosApp bdDatos;

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    ProgressDialog progress;

    Context context;
    public  Coneccion() {

    }
    public void cargarWebService() {

    //    progress=new ProgressDialog(context);
    //    progress.setMessage("Consultando...");
     //   progress.show();

        // String ip=getString(R.string.ip);

        String url="https://servicioparanegocio.es/superClean/consultarUsuario.php";

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
        //  VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(context, "No se puede conectar "+error.toString(), Toast.LENGTH_LONG).show();
        System.out.println();
        Log.d("ERROR: ", error.toString());
        progress.hide();
    }

    @Override
    public void onResponse(JSONObject response) {
        Clientes cliente=null;

        JSONArray json=response.optJSONArray("usuario");

        try {

            for (int i = 0; i< Objects.requireNonNull(json).length(); i++){
                cliente=new Clientes();
                JSONObject jsonObject=null;
                jsonObject=json.getJSONObject(i);
                cliente.setId_cliente(jsonObject.optInt("Id_cli"));
                cliente.setNombre(jsonObject.optString("nombre_cli"));
                cliente.setTelefono(jsonObject.optString("telefono_cli"));
                cliente.setDireccion(jsonObject.optString("direccion_cli"));
                cliente.setDiaVisita(jsonObject.getString("dia_visita"));

                ContentValues values = new ContentValues();
                values.put(COLUMN_ID_CLI, cliente.getId_cliente());
                values.put(COLUMN_NAME_CLI, cliente.getNombre());
                values.put(COLUMN_TELEFONO, cliente.getTelefono());
                values.put(COLUMN_DIRECCION, cliente.getDireccion());
                values.put(COLUMN_DIA_VISITA, cliente.getDiaVisita());
                bdDatos = new BaseDatosApp(context.getApplicationContext());
                SQLiteDatabase db = bdDatos.getReadableDatabase();
                if(db!= null) {
                    Toast.makeText(context, "Datos guardados"+ SQLiteAccessPermException.class, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(context, "Tienes un problema" +
                            " "+response, Toast.LENGTH_SHORT).show();
                }
                db.insert(TABLE_CLIENTE, null, values);
            }

            progress.hide();


        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context, "No se ha podido establecer conexión con el servidor" +
                    " "+response, Toast.LENGTH_LONG).show();
            progress.hide();
        }

    }


}

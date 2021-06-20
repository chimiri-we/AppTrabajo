package com.example.apptrabajo.api;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.apptrabajo.datos.BaseDatosApp;
import com.example.apptrabajo.entidades.DetalleVenta;
import com.example.apptrabajo.entidades.Productos;
import com.example.apptrabajo.entidades.Venta;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Consultas extends BaseDatosApp implements Response.Listener<JSONObject>, Response.ErrorListener {


    ProgressDialog progress;

    Cursor cursor;
    Productos productos;
    Venta venta;
    private static final String TABLE_PRODUCTO = "Producto";

    private static final String nombre_producto = "nombre_producto";

    private static final String precioProducto = "precioProducto";
    private static final String id_remoto = "id_remoto";

    ArrayList<Productos> arrayList = new ArrayList<Productos>();
    ArrayList<String> strinsProducto;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    BaseDatosApp bdLocal;
    Context context;
    public Consultas(Context context) {
        super(context);
        this.context = context;

    }

    public Venta obtenerVentasDia(String fecha) {
        bdLocal = new BaseDatosApp(context.getApplicationContext());
        SQLiteDatabase db = bdLocal.getWritableDatabase();

        Venta venta=null;
        Cursor cursor;

        cursor = db.rawQuery("select SUM(total_venta) from Venta WHERE fecha = fecha ", null);
        if (cursor.moveToNext()) {
            venta = new Venta();
          //  int totalVen = Integer.parseInt(cursor.getString(4));
            venta.setTota_venta(cursor.getInt(0));

        }
        cursor.close();
        return venta;
    }


    public void actualizarProducto(RequestQueue request) {


        progress=new ProgressDialog(context);
        progress.setMessage("Consultando...");
        progress.show();

        // String ip=getString(R.string.ip);

        String url="https://servicioparanegocio.es/superClean/consultarProducto.php";

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this::onResponse,this);
        request.add(jsonObjectRequest);

          //VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(context, "No se puede conectar "+error.toString(), Toast.LENGTH_SHORT).show();
        System.out.println();
        Log.d("ERROR: ", error.toString());
        progress.hide();
    }

    @Override
    public void onResponse(JSONObject response) {
        progress.hide();

        Productos producto=null;

        JSONArray json=response.optJSONArray("producto");

        try {

            for (int i = 0; i< Objects.requireNonNull(json).length(); i++){
                producto=new Productos();
                JSONObject jsonObject;
                jsonObject=json.getJSONObject(i);

                producto.setId_remoto(jsonObject.optInt("id"));
                producto.setNombre(jsonObject.optString("name"));
                producto.setPrecio(jsonObject.optString("precio"));

                ContentValues values = new ContentValues();
                values.put(id_remoto, producto.getId_remoto());
                values.put(nombre_producto, producto.getNombre());
                values.put(precioProducto, producto.getPrecio());
                bdLocal = new BaseDatosApp(context.getApplicationContext());
                SQLiteDatabase db = bdLocal.getReadableDatabase();
                if(db!= null) {
                   // Toast.makeText(context, "Datos guardados", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(context, "Tienes un problema" +
                            " "+response, Toast.LENGTH_SHORT).show();
                }
                db.insert(TABLE_PRODUCTO, null, values);
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




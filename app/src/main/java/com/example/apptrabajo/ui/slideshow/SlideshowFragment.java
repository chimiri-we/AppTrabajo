package com.example.apptrabajo.ui.slideshow;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteAccessPermException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.apptrabajo.R;
import com.example.apptrabajo.adaptadores.ProductoAdapter;
import com.example.apptrabajo.databinding.FragmentSlideshowBinding;
import com.example.apptrabajo.datos.BaseDatosApp;
import com.example.apptrabajo.entidades.Productos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Objects;

public class SlideshowFragment extends Fragment  implements Response.Listener<JSONObject>, Response.ErrorListener {

    private static final String TABLE_PRODUCTO = "Producto";

    private static final String nombre_producto = "nombre_producto";

    private static final String precioProducto = "precioProducto";
    private static final String id_remoto = "id_remoto";

    ArrayList<Productos> arrayList = new ArrayList<Productos>();
    ArrayList<String> strinsProducto;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    ProgressDialog progress;
    BaseDatosApp bdLocal;
    Connection con;
    //  RecyclerView listView;
    ListView listView;
    ProductoAdapter productoAdapter;

    private SlideshowViewModel slideshowViewModel;
    private FragmentSlideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View v = binding.getRoot();
        listView = binding.productoList;


        bdLocal = new BaseDatosApp(getContext());
        consultarProducto();


      //  ArrayAdapter<CharSequence> adapterProducto = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, strinsProducto);
    //    listView.setAdapter(adapterProducto);

        if (arrayList.size() > 0) {

            productoAdapter.setDropDownViewResource(View.VISIBLE);
            listView.setAdapter(productoAdapter);
        }else {
            productoAdapter.setDropDownViewResource(View.GONE);
            Toast.makeText(getContext(),"no hay datos", Toast.LENGTH_SHORT).show();
          //  LayoutInflater inflater = LayoutInflater.from(getContext());
            View subView = inflater.inflate(R.layout.dialogo_actualizar_datos, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

            builder.setTitle("Actualiza tu base de Datos");
            builder.setMessage("No tienes tu lista actualizada, porfavor actualiza");
            builder.setView(subView);

            builder.create();
            builder.setPositiveButton("Actualizar", (dialog, which) -> {
                actualizarProducto();
                // finish();
                //  startActivity(getContext());

            });

            builder.show();
            onStart();
        }




        request= Volley.newRequestQueue(requireContext());
      // actualizarProducto();

        return v;

    }

    private void consultarProducto() {
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
   //     obtenerLista();
        productoAdapter= new ProductoAdapter(getContext(), R.layout.productos_list, arrayList);
        listView.setAdapter(productoAdapter);

    }

    public void obtenerLista() {

        strinsProducto = new ArrayList<String>();
       // strinsProducto.add("selecciona");

        for (int i=0;i<arrayList.size();i++) {
            strinsProducto.add(arrayList.get(i).getNombre()+" $ "+arrayList.get(i).getPrecio());
        }
    }



    private void actualizarProducto() {
       /* LayoutInflater inflater = LayoutInflater.from(getContext());
        View subView = inflater.inflate(R.layout.dialogo_actualizar_datos, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Actualiza tu base de Datos");
        builder.setMessage("No tienes tu lista actualizada, porfavor actualiza");
        builder.setView(subView);
        builder.create();
        builder.setPositiveButton("ACTUALIZAR", (dialog, which) -> {
          */  progress=new ProgressDialog(getContext());
            progress.setMessage("Consultando...");
            progress.show();

            // String ip=getString(R.string.ip);

            String url="https://servicioparanegocio.es/superClean/consultarProducto.php";

            jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
            request.add(jsonObjectRequest);

            //  VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);




    }


    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), "No se puede conectar "+error.toString(), Toast.LENGTH_SHORT).show();
        System.out.println();
        Log.d("ERROR: ", error.toString());
        progress.hide();
    }

    @Override
    public void onResponse(JSONObject response) {
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
                //  producto = new Producto(name, precio);
                arrayList.add(producto);
                ContentValues values = new ContentValues();
                values.put(id_remoto, producto.getId_remoto());
                values.put(nombre_producto, producto.getNombre());
                values.put(precioProducto, producto.getPrecio());
                bdLocal = new BaseDatosApp(requireContext().getApplicationContext());
                SQLiteDatabase db = bdLocal.getReadableDatabase();
                if(db!= null) {
                    Toast.makeText(getContext(), "Datos guardados", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getContext(), "Tienes un problema" +
                            " "+response, Toast.LENGTH_SHORT).show();
                }
                db.insert(TABLE_PRODUCTO, null, values);
            }

            progress.hide();
            consultarProducto();

           // productoAdapter= new ProductoAdapter(getContext(), R.layout.productos_list, adapterlistaProducto);
           // listView.setAdapter(productoAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "No se ha podido establecer conexión con el servidor" +
                    " "+response, Toast.LENGTH_LONG).show();
            progress.hide();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
package com.example.apptrabajo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.apptrabajo.api.Consultas;
import com.example.apptrabajo.datos.BaseDatosApp;
import com.example.apptrabajo.entidades.Venta;

import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class PerfilEmpleadoFragment extends Fragment  {



    CardView verListaVenta, verVisitas, verVistasPendientes;
    private static final String TABLE_CLIENTE = "Cliente";

    BaseDatosApp bdLocal;
    Consultas con;

    ImageView imFoto;

     final int COD_FOTO = 120;
    final String CARPETA_RAIZ = "MisFotosApp";
    final String CARPETA_IMAGENES = "imagenes";
    final String RUTA_IMAGEN = CARPETA_RAIZ + CARPETA_IMAGENES;
    String path;
    Button btnActPro, btnActClien;
    CamaraActivity camaraActivity;
  Venta venta;
    String fecha;
    TextView totalV, txtFecha;
    private static final String TABLE_PRODUCTO = "Producto";
    private int requestCode;
    private int resultCode;
    @Nullable
    private Intent data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.perfil_empleado, container, false);

        imFoto = v.findViewById(R.id.imFotoPerfil);
        imFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ocTomaFoto(v);
            }
        });
        btnActPro = v.findViewById(R.id.actualizar_productos);
        btnActClien = v.findViewById(R.id.actualizar_cliente);
        totalV = v.findViewById(R.id.tv_ventas_dia);
     //   txtFecha = v.findViewById(R.id.dvFecha);

        Date fechaActual= Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        fecha = df.format(fechaActual);
        System.out.println("Current time => " + fecha);
      //  txtFecha.setText(fecha);
        con = new Consultas(requireContext().getApplicationContext());

       // String f = txtFecha.getText().toString().trim();
        venta = con.obtenerVentasDia(fecha);
        if (venta != null) {
            //venta = new DetalleVenta();

            totalV.setText(String.valueOf(venta.getTota_venta()));
        }



        btnActClien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarCliente();
            }
        });

        btnActPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarPro();
            }
        });
        verListaVenta = v.findViewById(R.id.verListaVentas);
        verListaVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ListaPedidosActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }



    private void ocTomaFoto(View v) {
   /*     String nombreImagen = "";

        File fileImagen = new File(Environment.getExternalStorageDirectory(), RUTA_IMAGEN);
        boolean isCreada = fileImagen.exists();

        if(isCreada == false) {
            isCreada = fileImagen.mkdirs();
        }

        if(isCreada == true) {
            //nombreImagen = (System.currentTimeMillis() / 1000) + ".jpg";
            nombreImagen = "mifoto.jpg";
        }

        path = Environment.getExternalStorageDirectory()+File.separator+RUTA_IMAGEN+File.separator+nombreImagen;
        File imagen = new File(path);

      */
     //   intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        @SuppressLint("IntentReset") Intent intent1 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent1.setType("image/");
        startActivityForResult(Intent.createChooser(intent1, "selecciona"), 10);

      /*  if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String authorities = v.getContext().getPackageName()+".provider";
            Uri imageUri = FileProvider.getUriForFile(v.getContext(), authorities, imagen);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            requireContext().startActivity(intent.putExtra("COD_FOTO", COD_FOTO));
        }*/
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
           Uri path= data.getData();
           imFoto.setImageURI(path);
        }
    }



    private void actualizarCliente() {
        bdLocal = new BaseDatosApp(requireContext().getApplicationContext());
        SQLiteDatabase db = bdLocal.getReadableDatabase();
        db.execSQL("delete from " + TABLE_CLIENTE);
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        builder.setTitle("Actualiza tu base de Datos");
        builder.setMessage("No tienes tu lista actualizada, porfavor actualiza");

        builder.create();
        builder.setPositiveButton("Actualizar", (dialog, which) -> {
            Consultas consultas = new Consultas(getContext());
            RequestQueue nuevaRequest = Volley.newRequestQueue(requireContext());
            consultas.actualizarCliente(nuevaRequest);

        });
       // Toast.makeText(getContext(), "Datos Guardados", Toast.LENGTH_LONG).show();

        bdLocal.close();
        builder.show();
        onStart();
    }

    private void actualizarPro() {
        bdLocal = new BaseDatosApp(requireContext().getApplicationContext());
        SQLiteDatabase db = bdLocal.getReadableDatabase();
        db.execSQL("delete from " + TABLE_PRODUCTO);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        builder.setTitle("Actualiza tu base de Datos");
        builder.setMessage("No tienes tu lista actualizada, porfavor actualiza");

        builder.create();
        builder.setPositiveButton("Actualizar", (dialog, which) -> {
            Consultas consultas = new Consultas(getContext());
            RequestQueue request= Volley.newRequestQueue(requireContext());
            consultas.actualizarProducto(request);

        });
      //  Toast.makeText(getContext(), "Datos Guardados", Toast.LENGTH_LONG).show();

        bdLocal.close();
        builder.show();
        onStart();
    }
}

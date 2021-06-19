package com.example.apptrabajo.api;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.apptrabajo.datos.BaseDatosApp;
import com.example.apptrabajo.entidades.DetalleVenta;
import com.example.apptrabajo.entidades.Venta;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Consultas extends BaseDatosApp {

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
}
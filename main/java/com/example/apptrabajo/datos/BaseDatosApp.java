package com.example.apptrabajo.datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.apptrabajo.entidades.Clientes;
import com.example.apptrabajo.entidades.DetalleVenta;
import com.example.apptrabajo.entidades.Productos;

import java.util.ArrayList;

public class BaseDatosApp extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Trabajo";
    private static final String TABLE_CLIENTE = "Cliente";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_ID_CLIENTE = "id_cliente";
    private static final String COLUMN_NOMBRE = "nombre";
    private static final String COLUMN_NOMBRE_CLIENTE = "nombre_cliente";
    private static final String COLUMN_DIRECCION = "direccion";
    private static final String COLUMN_TELEFONO = "telefono";
    private static final String COLUMN_DIA_VISITA = "dia_visita";

    private static final String TABLE_PRODUCTO = "Producto";
    private static final String COLUMN_PRECIO = "precioProducto";

    private static final String TABLE_VENTA = "Venta";
    private static final String COLUMN_TOTAL = "total";
    private static final String COLUMN_DETALLE = "detalle_venta";
    private static final String COLUMN_FECHA = "fecha";

    private static final String TABLE_DETALLE_VENTA = "DetalleVenta";
    private static final String COLUMN_ID_VENTA = "id_venta";
    private static final String COLUMN_ID_PRODUCTO = "id_producto";
    private static final String COLUMN_NOMBRE_PRODUCTO = "nombre_producto";
    private static final String COLUMN_PRECIO_PRODUCTO = "precio_producto";
    private static final String COLUMN_CANTIDAD = "cantidad";





    public BaseDatosApp(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CLIENTE_TABLE = "CREATE TABLE "
                + TABLE_CLIENTE + "(" + COLUMN_ID
                + " INTEGER PRIMARY KEY,"
                + COLUMN_NOMBRE + " TEXT,"
                + COLUMN_DIRECCION + " TEXT,"
                + COLUMN_TELEFONO + " TEXT,"
                + COLUMN_DIA_VISITA + " TEXT" + ")";
        db.execSQL(CREATE_CLIENTE_TABLE);

        String CREATE_PRODUCTO_TABLE = "CREATE TABLE "
                + TABLE_PRODUCTO + "(" + COLUMN_ID
                + " INTEGER PRIMARY KEY,"
                + COLUMN_NOMBRE + " TEXT,"
                + COLUMN_PRECIO + " INTEGER" + ")";
        db.execSQL(CREATE_PRODUCTO_TABLE);

        String CREATE_VENTA_TABLE = "CREATE TABLE "
                + TABLE_VENTA + "(" + COLUMN_ID
                + " INTEGER PRIMARY KEY,"
                + COLUMN_ID_CLIENTE + " TEXT,"
                + COLUMN_NOMBRE_CLIENTE + " TEXT,"
                + COLUMN_DETALLE + " TEXT,"
                + COLUMN_FECHA + " TEXT" + ")";
        db.execSQL(CREATE_VENTA_TABLE);

        String CREATE_DETALLE_VENTA_TABLE = "CREATE TABLE "
                + TABLE_DETALLE_VENTA + "(" + COLUMN_ID
                + " INTEGER PRIMARY KEY,"
                + COLUMN_ID_VENTA + " TEXT,"
                + COLUMN_ID_PRODUCTO + " TEXT,"
                + COLUMN_NOMBRE_PRODUCTO + " TEXT,"
                + COLUMN_PRECIO_PRODUCTO + " TEXT,"
                + COLUMN_CANTIDAD + " TEXT,"
                + COLUMN_TOTAL + " TEXT" + ")";
        db.execSQL(CREATE_DETALLE_VENTA_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENTE);
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTO);
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VENTA);
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DETALLE_VENTA);
        onCreate(db);
    }

    public ArrayList<DetalleVenta> listDetalleVenta() {
        String sql = "select * from  DetalleVenta";
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<DetalleVenta> storeContacts = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String nombre_producto = cursor.getString(3);
                String precio = cursor.getString(4);
                String cantidad = cursor.getString(5);
                storeContacts.add(new DetalleVenta(id, nombre_producto, precio, cantidad));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return storeContacts;
    }


    public void addProductos(Productos newContact) {

    }

    public void updateProducto(DetalleVenta detalleVenta) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE_PRODUCTO, detalleVenta.getNombre_producto());
        values.put(COLUMN_PRECIO, detalleVenta.getPrecio());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_PRODUCTO, values, COLUMN_ID + " = ?", new String[]{String.valueOf(detalleVenta.getId())});
    }

    public Clientes verCliente(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
       Clientes clientes = null;
        Cursor cursor;

       cursor = db.rawQuery("select * from Cliente WHERE id = " + id + " LIMIT 1", null);
        if (cursor.moveToFirst()) {

                clientes = new Clientes();
                clientes.setNombre(cursor.getString(1));
                clientes.setDireccion(cursor.getString(2));



        }
        cursor.close();
        return clientes;
    }

    public ArrayList<Productos> spinerProducto() {
        String sql = "select * from Productos";
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Productos> storeProduct = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()){

                int id = Integer.parseInt(cursor.getString(0));
                String nombre = cursor.getString(1);
                String precio = cursor.getString(2);
                storeProduct.add(new Productos(id, nombre, precio));
            }


        cursor.close();
        return storeProduct;


    }

    public void agregaProductos(DetalleVenta newVenta) {
        ContentValues values = new ContentValues();
      values.put(COLUMN_NOMBRE_PRODUCTO, newVenta.getNombre_producto());
      values.put(COLUMN_PRECIO_PRODUCTO, newVenta.getPrecio());
      values.put(COLUMN_CANTIDAD, newVenta.getCantidad());
      values.put(COLUMN_ID_VENTA, newVenta.getId_venta());
    SQLiteDatabase db = this.getWritableDatabase();
      db.insert(TABLE_DETALLE_VENTA, null, values);
}
}


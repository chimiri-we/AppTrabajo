package com.example.apptrabajo.datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.apptrabajo.entidades.Clientes;
import com.example.apptrabajo.entidades.DetalleVenta;
import com.example.apptrabajo.entidades.Productos;
import com.example.apptrabajo.entidades.Venta;

import java.util.ArrayList;

public class BaseDatosApp extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 8;
    private static final String DATABASE_NAME = "DatosTrabajo";
    private static final String TABLE_CLIENTE = "Cliente";
    private static final String COLUMN_ID = "id";
    private static final String id_cliente = "id_cliente";
    private static final String COLUMN_NOMBRE_CLIENTE = "nombre_cliente";
    private static final String COLUMN_TELEFONO = "telefono";
    private static final String COLUMN_DIA_VISITA = "dia_visita";

    private static final String TABLE_PRODUCTO = "Producto";
    private static final String precioProducto = "precioProducto";

    private static final String TABLE_VENTA = "Venta";
    private static final String COLUMN_TOTAL = "total";
    private static final String COLUMN_FECHA = "fecha";

    private static final String TABLE_DETALLE_VENTA = "DetalleVenta";
    private static final String COLUMN_ID_VENTA = "id_venta";
    private static final String COLUMN_ID_PRODUCTO = "id_producto";
    private static final String nombre_producto = "nombre_producto";
    private static final String COLUMN_PRECIO_PRODUCTO = "precio_producto";
    private static final String COLUMN_CANTIDAD = "cantidad";





    public BaseDatosApp(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

  String dbCliente="create table Cliente("+
                "id_cliente INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "nombre TEXT NOT NULL,"+
                "direccion TEXT,"+
                "telefono TEXT,"+
                "dia_visita TEXT)";
        db.execSQL(dbCliente);
        
        String dbProducto="create table Producto("+
                "id_producto INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "nombre_producto TEXT NOT NULL,"+
                "precioProducto INTEGER)";
        db.execSQL(dbProducto);

        String dbVenta="create table Venta("+
                "id_venta INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "id_cliente INTEGER NOT NULL,"+
                "nombre_cliente TEXT NOT NULL,"+
                "cod_detalle INTEGER,"+
                "fecha TEXT,"+
                "total_venta integer,"+
                "FOREIGN KEY(id_cliente) REFERENCES Cliente(id_cliente))";
        db.execSQL(dbVenta);

        String dbDetalleVenta="create table DetalleVenta("+
                "id_detalle INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "id_venta INTEGER,"+
                "id_producto integer,"+
                "nombre_producto TEXT,"+
                "precio_producto TEXT,"+
                "cantidad integer,"+
                "total integer,"+
                "cod_detalle INTEGER,"+
                "FOREIGN KEY(id_producto) REFERENCES Producto(id_producto),"+
                "FOREIGN KEY(id_venta) REFERENCES Venta(id_venta))";
        db.execSQL(dbDetalleVenta);

      /*  String CREATE_VENTA_TABLE = "CREATE TABLE "
                + TABLE_VENTA + "(" + COLUMN_ID
                + " INTEGER PRIMARY KEY,"
                + COLUMN_ID_CLIENTE + " INTEGER,"
                + COLUMN_NOMBRE_CLIENTE + " TEXT,"
                + COLUMN_DETALLE + " TEXT,"
                + COLUMN_FECHA + " TEXT,"
                +"FOREIGN KEY(COLUMN_ID_CLIENTE) REFERENCES Ciente(COLUMN_ID))";
        db.execSQL(CREATE_VENTA_TABLE);

        String CREATE_DETALLE_VENTA_TABLE = "CREATE TABLE "
                + TABLE_DETALLE_VENTA + "(" + COLUMN_ID
                + " INTEGER PRIMARY KEY,"
                + COLUMN_ID_VENTA + " INTEGER,"
                + COLUMN_ID_PRODUCTO + " INTEGER,"
                + COLUMN_NOMBRE_PRODUCTO + " TEXT,"
                + COLUMN_PRECIO_PRODUCTO + " TEXT,"
                + COLUMN_CANTIDAD + " TEXT,"
                +"FOREIGN KEY(COLUMN_ID_VENTA) REFERENCES Venta(COLUMN_ID),"+
                "FOREIGN KEY(COLUMN_ID_PRODUCTO) REFERENCES Producto(COLUMN_ID))";
        db.execSQL(CREATE_DETALLE_VENTA_TABLE);

*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VENTA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DETALLE_VENTA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENTE);
        onCreate(db);

    }

    public void agregaCliente(Clientes clientes) {
    ContentValues values = new ContentValues();
    //values.put(COLUMN_ID_CLI, cliente.getId());
                values.put("nombre", clientes.getNombre());
                values.put("telefono", clientes.getTelefono());
                values.put("direccion", clientes.getDireccion());
                values.put("dia_visita", clientes.getDiaVisita());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_CLIENTE, null, values);
    }
    public void generarVenta(Venta venta) {
        ContentValues values = new ContentValues();
        values.put("id_cliente", venta.getId_cliente());
        values.put("nombre_cliente", venta.getNombre_cliente());
        values.put("cod_detalle", venta.getDetalle_venta());
        values.put("fecha", venta.getFecha());
        values.put("total_venta", venta.getTota_venta());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_VENTA, null, values);

    }
    public void agregaProductos(DetalleVenta newVenta) {
        ContentValues values = new ContentValues();
        values.put("nombre_producto", newVenta.getNombre_producto());
        values.put("precio_producto", newVenta.getPrecio());
        values.put("cantidad", newVenta.getCantidad());
        values.put("id_venta", newVenta.getId_venta());
        values.put("id_producto", newVenta.getId_producto());
        values.put("total", newVenta.getTotal());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_DETALLE_VENTA, null, values);
    }


    public ArrayList<DetalleVenta> listDetalleVenta() {
        String sql = "select * from  DetalleVenta";
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<DetalleVenta> storeContacts = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                int id_detalle = Integer.parseInt(cursor.getString(0));
                String nombre_producto = cursor.getString(3);
                String precio = cursor.getString(4);
                String cantidad = cursor.getString(5);
                String total = cursor.getString(6);
                storeContacts.add(new DetalleVenta(id_detalle, nombre_producto, precio, cantidad, total));
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
        values.put(nombre_producto, detalleVenta.getNombre_producto());
        values.put(precioProducto, detalleVenta.getPrecio());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_PRODUCTO, values, COLUMN_ID + " = ?", new String[]{String.valueOf(detalleVenta.getId())});
    }

    public Clientes verCliente(int id_cliente) {
        SQLiteDatabase db = this.getWritableDatabase();
       Clientes clientes = null;
        Cursor cursor;

       cursor = db.rawQuery("select * from Cliente WHERE id_cliente = " + id_cliente + " LIMIT 1", null);
        if (cursor.moveToFirst()) {

                clientes = new Clientes();
                clientes.setId_cliente(cursor.getInt(0));
                clientes.setNombre(cursor.getString(1));
                clientes.setDireccion(cursor.getString(2));
                clientes.setTelefono(cursor.getString(3));
            clientes.setDiaVisita(cursor.getString(4));


        }
       cursor.close();
        return clientes;
    }


    public ArrayList<Venta> listVenta() {
        SQLiteDatabase db = this.getWritableDatabase();
        Venta venta= null;
        ArrayList<Venta> storeVenta = new ArrayList<>();
        Cursor cursor;
        cursor = db.rawQuery("select * from Venta", null);
        if (cursor.moveToFirst()) {
            do {

            int idVenta = Integer.parseInt(cursor.getString(0));
            String nombreClient = cursor.getString(2);
            String dtVenta = cursor.getString(3);
            String fechaVenta = cursor.getString(4);
           int totalVenta = Integer.parseInt(cursor.getString(5));

            storeVenta.add(new Venta(idVenta, nombreClient, dtVenta, fechaVenta, totalVenta));
        }
            while (cursor.moveToNext());
        }
        cursor.close();
        return storeVenta;
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
   public void deleteContact(int id_detalle) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (id_detalle>0){
            db.delete("DetalleVenta", "id_producto" + " = ?", new String[]{String.valueOf(0)});

        }

    }



}
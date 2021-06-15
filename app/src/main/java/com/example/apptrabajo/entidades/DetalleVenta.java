package com.example.apptrabajo.entidades;

public class DetalleVenta {
    private int id_detalle;
    private int id_producto;
    private int id_venta;
    private String nombre_producto;
    private String cantidad;
    private String precio;
    private String total;
    private String cod_detalle;

    public DetalleVenta(int id_detalle, int id_producto, int id_venta, String nombre_producto, String cantidad, String precio, String total) {
        this.id_detalle = id_detalle;
        this.id_producto = id_producto;
        this.id_venta = id_venta;
        this.nombre_producto = nombre_producto;
        this.cantidad = cantidad;
        this.precio = precio;
        this.total = total;
    }

    public DetalleVenta() {

    }

    public DetalleVenta(String nombrePro, String precio, String cantidad) {

        this.nombre_producto = nombrePro;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public DetalleVenta(int id_detalle, String nombre_producto, String precio, String cantidad, String total) {
        this.id_detalle = id_detalle;
        this.nombre_producto = nombre_producto;
        this.cantidad = cantidad;
        this.precio = precio;
        this.total = total;
    }

    public DetalleVenta(String nombrePro, String precio, String cantidad, int idVenta, int idProducto) {

        this.nombre_producto = nombrePro;
        this.cantidad = cantidad;
        this.precio = precio;
        this.id_producto = idProducto;
        this.id_venta = idVenta;

    }

    public DetalleVenta(int id_producto, String nombre_producto, String precio) {
        this.nombre_producto = nombre_producto;

        this.precio = precio;
        this.id_producto = id_producto;
    }

    public DetalleVenta(String nombrePro, String precio, String cantidad, String resultado) {
        this.nombre_producto = nombrePro;
        this.cantidad = cantidad;
        this.precio = precio;
        this.total = resultado;
    }

    public DetalleVenta(String nombrePro, String precioProd, String cantidad, String resultado, String s) {
        this.nombre_producto = nombrePro;
        this.cantidad = cantidad;
        this.precio = precioProd;
        this.total = resultado;
        this.cod_detalle = s;
    }


    public int getId() {
        return id_detalle;
    }

    public void setId(int id) {
        this.id_detalle = id;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public int getId_detalle() {
        return id_detalle;
    }

    public void setId_detalle(int id_detalle) {
        this.id_detalle = id_detalle;
    }

    public String getCod_detalle() {
        return cod_detalle;
    }

    public void setCod_detalle(String cod_detalle) {
        this.cod_detalle = cod_detalle;
    }
}

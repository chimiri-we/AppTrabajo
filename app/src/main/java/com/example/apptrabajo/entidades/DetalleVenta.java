package com.example.apptrabajo.entidades;

public class DetalleVenta {
    private int id;
    private String id_producto;
    private String id_venta;
    private String nombre_producto;
    private String cantidad;
    private String precio;
    private String total;

    public DetalleVenta(int id, String id_producto, String id_venta, String nombre_producto, String cantidad, String precio, String total) {
        this.id = id;
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

    public DetalleVenta(int id, String nombre_producto, String precio, String cantidad) {
        this.id = id;
        this.nombre_producto = nombre_producto;
        this.cantidad = cantidad;
        this.precio = precio;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getId_producto() {
        return id_producto;
    }

    public void setId_producto(String id_producto) {
        this.id_producto = id_producto;
    }

    public String getId_venta() {
        return id_venta;
    }

    public void setId_venta(String id_venta) {
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
}

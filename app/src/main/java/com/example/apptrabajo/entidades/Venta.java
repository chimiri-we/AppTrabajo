package com.example.apptrabajo.entidades;

public class Venta {
    private int id_venta;
    private int id_cliente;
    private String nombre_cliente;
    private String fecha;
    private String detalle_venta;
    private int tota_venta;

    public Venta(int id_venta, int id_cliente, String nombre_cliente, String fecha, String detalle_venta, int tv) {
        this.id_venta = id_venta;
        this.id_cliente = id_cliente;
        this.nombre_cliente = nombre_cliente;
        this.fecha = fecha;
        this.detalle_venta = detalle_venta;
        this.tota_venta = tv;
    }

    public Venta() {

    }

    public Venta(int id, String nombre, String formattedDate, int totaVenta) {
        this.id_cliente = id;
        this.nombre_cliente = nombre;
        this.fecha = formattedDate;
        this.tota_venta = totaVenta;
    }

    public Venta(int idVenta, String nombreClient, String dtVenta, String fechaVenta, int totalVenta) {
        this.id_venta = idVenta;
        this.nombre_cliente = nombreClient;
        this.fecha = fechaVenta;
        this.detalle_venta = dtVenta;
        this.tota_venta = totalVenta;
    }


    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDetalle_venta() {
        return detalle_venta;
    }

    public void setDetalle_venta(String detalle_venta) {
        this.detalle_venta = detalle_venta;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public int getTota_venta() {
        return tota_venta;
    }

    public void setTota_venta(int tota_venta) {
        this.tota_venta = tota_venta;
    }

    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }
}

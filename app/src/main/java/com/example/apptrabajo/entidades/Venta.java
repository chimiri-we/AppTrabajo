package com.example.apptrabajo.entidades;

public class Venta {
    private int id;
    private int id_cliente;
    private String nombre_cliente;
    private String fecha;
    private String detalle_venta;

    public Venta(int id, int id_cliente, String nombre_cliente, String fecha, String detalle_venta) {
        this.id = id;
        this.id_cliente = id_cliente;
        this.nombre_cliente = nombre_cliente;
        this.fecha = fecha;
        this.detalle_venta = detalle_venta;
    }

    public Venta() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_cliente(int id) {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
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

}

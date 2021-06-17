package com.example.apptrabajo.entidades;

public class
Venta {
    private int id_venta;

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    private int id_cliente;
    private String nombre_cliente;
    private String fecha;
    private int tota_venta;

    public Venta(int id_venta, int id_cliente, String nombre_cliente, String fecha, String detalle_venta, int tv) {
        this.id_venta = id_venta;
        this.id_cliente = id_cliente;
        this.nombre_cliente = nombre_cliente;
        this.fecha = fecha;
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


    public Venta(int idVenta, int idCliente, String nombreClient, String fechaVenta, int totalVenta) {
        this.id_venta = idVenta;

        this.id_cliente = idCliente;
        this.nombre_cliente = nombreClient;
        this.fecha = fechaVenta;
        this.tota_venta = totalVenta;
    }

    public Venta(String nombreCli, int id, String formattedDate, int totalV) {
        this.nombre_cliente = nombreCli;
        this.id_cliente = id;
        this.fecha = formattedDate;
        this.tota_venta = totalV;


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

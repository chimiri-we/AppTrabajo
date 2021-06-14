package com.example.apptrabajo.entidades;

public class Clientes {
    private int id;
    private int id_venta;
    private String nombre;
    private String telefono;
    private String direccion;
    private String diaVisita;
    public Clientes(int id, String nombre, String telefono, String direccion, String dia_visita){
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.diaVisita = dia_visita;
    }

    public Clientes(int id_venta) {
        this.id_venta = id_venta;

    }
    public Clientes() {

    }


    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDiaVisita() {
        return diaVisita;
    }

    public void setDiaVisita(String diaVisita) {
        this.diaVisita = diaVisita;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }
}

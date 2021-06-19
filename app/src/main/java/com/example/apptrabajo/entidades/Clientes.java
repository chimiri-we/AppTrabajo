package com.example.apptrabajo.entidades;

public class Clientes {
    private int id_cliente;
    private int id_Remoto;
    private String nombre;
    private String telefono;
    private String direccion;
    private String diaVisita;
    private String colonia;

    public int getId_Remoto() {
        return id_Remoto;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public void setId_Remoto(int id_Remoto) {
        this.id_Remoto = id_Remoto;
    }

    public Clientes(int id_cliente, String nombre, String telefono, String direccion, String dia_visita){
        this.id_cliente = id_cliente;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.diaVisita = dia_visita;
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




    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
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


}

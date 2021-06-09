package com.example.apptrabajo.entidades;

public class Productos {
    private int id;
    private String nombre;
    private String precio;


    public Productos(String precio, String name) {
        this.nombre = name;
        this.precio = precio;
    }

    public Productos() {

    }

    public Productos(int id, String nombre, String precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
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

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }





}

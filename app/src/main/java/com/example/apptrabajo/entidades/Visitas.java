package com.example.apptrabajo.entidades;

public class Visitas {

    private  int id_visitas;
    private int id_empleado;
    private int id_cliente;
    private int estado;
    private String fecha_visita;

    public Visitas(int id_visitas, int id_empleado, int id_cliente, int estado, String fecha_visita) {
        this.id_visitas = id_visitas;
        this.id_empleado = id_empleado;
        this.id_cliente = id_cliente;
        this.estado = estado;
        this.fecha_visita = fecha_visita;
    }

    public Visitas(int id, String formattedDate, int stado) {
        this.id_cliente = id;
        this.estado = stado;
        this.fecha_visita = formattedDate;
    }

    public Visitas() {

    }

    public int getId_visitas() {
        return id_visitas;
    }

    public void setId_visitas(int id_visitas) {
        this.id_visitas = id_visitas;
    }

    public int getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(int id_empleado) {
        this.id_empleado = id_empleado;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getFecha_visita() {
        return fecha_visita;
    }

    public void setFecha_visita(String fecha) {
        this.fecha_visita = fecha;
    }




}

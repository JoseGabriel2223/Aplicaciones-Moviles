package com.example.securityhome;

public class administrar {
    private String nombre;
    private String Descripcion;



    public administrar(String nombre, String Descripcion){

        this.nombre= nombre;
        this.Descripcion= Descripcion;

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }
}

package com.example.securityhome;

public class Datos {

    private String value;
    private String fecha;

    public Datos(String fecha, String value){
        this.fecha= fecha;
        this.value= value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}

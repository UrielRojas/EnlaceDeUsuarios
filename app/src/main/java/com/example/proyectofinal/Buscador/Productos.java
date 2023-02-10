package com.example.proyectofinal.Buscador;

public class Productos {
    private String Empresa ,Nombre ,Presentacion ,Tipo;

    public Productos() {

    }

    public Productos(String empresa,String nombre ,String presentacion,String Tipo) {
        this.Empresa = empresa;
        this.Nombre = nombre;
        this.Presentacion = presentacion;
        this.Tipo = Tipo;
    }

    public String getEmpresa() {
        return Empresa;
    }

    public void setEmpresa(String empresa) {
        this.Empresa = empresa;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        this.Nombre = nombre;
    }

    public String getPresentacion() {
        return Presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.Presentacion = presentacion;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }
}

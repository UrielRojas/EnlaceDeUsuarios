package com.example.proyectofinal.Buscador;

public class Comerciantes {
    private String Usuario,Comercio,Colonia,Calle,Estado,Municipio,ID;

    public Comerciantes() {

    }

    public Comerciantes(String usuario, String comercio, String colonia, String calle, String estado, String municipio,String ID) {
        this.Usuario = usuario;
        this.Comercio = comercio;
        this.Colonia = colonia;
        this.Calle = calle;
        this.Estado = estado;
        this.Municipio = municipio;
        this.ID = ID;

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        this.Usuario = usuario;
    }

    public String getComercio() {
        return Comercio;
    }

    public void setComercio(String comercio) {
        this.Comercio = comercio;
    }

    public String getColonia() {
        return Colonia;
    }

    public void setColonia(String colonia) {
        this.Colonia = colonia;
    }

    public String getCalle() {
        return Calle;
    }

    public void setCalle(String calle) {
        this.Calle = calle;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        this.Estado = estado;
    }

    public String getMunicipio() {
        return Municipio;
    }

    public void setMunicipio(String municipio) {
        this.Municipio = municipio;
    }
}

package com.example.controlesbasicos;

public class producto {
    String _id;
    String _rev;
    String idProducto;
    String codigo;
    String descripcion;
    String marca;
    String presentacion;
    String precio;
    String costo;
    String stock;
    String foto;
    String porcentaje;


    public producto(String _id, String _rev, String idProducto, String codigo, String descripcion, String marca, String presentacion, String precio, String costo, String stock, String foto, String estado,String porcentaje) {
        this.idProducto = idProducto;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.marca = marca;
        this.presentacion = presentacion;
        this.precio = precio;
        this.costo=costo;
        this.stock=stock;
        this.foto=foto;
        this.porcentaje=porcentaje;
    }

    public String getIdProducto() {
        return idProducto;
    }
    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }
    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getMarca() {
        return marca;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }
    public String getPresentacion() {
        return presentacion;
    }
    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }
    public String getPrecio() {
        return precio;
    }
    public void setPrecio(String precio) {
        this.precio = precio;
    }
    public String getFoto() {
        return foto;
    }
    public void setFoto(String foto) {
        this.foto = foto;
    }
    public String get_id() {
        return _id;
    }
    public void set_id(String _id) {
        this._id = _id;
    }
    public String get_rev() {
        return _rev;
    }
    public void set_rev(String _rev) {
        this._rev = _rev;
    }
    public String getCosto() {
        return costo;
    }
    public void setCosto(String costo) {
        this.costo = costo;
    }
    public String getStock() {
        return stock;
    }
    public void setStock(String stock) {this.stock = stock;}
    public String getPorcentaje() {
        return porcentaje;
    }
    public void setPorcentaje(String porcentaje) {this.porcentaje = porcentaje;}
}



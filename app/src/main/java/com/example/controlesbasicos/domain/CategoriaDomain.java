package com.example.controlesbasicos.domain;

public class CategoriaDomain {
    public String nombreCategoria;
    public String DescripcionCategoria;
    public String ImagenCategoria;
    public String ButtonAgregarCarrito;
    public String ButtonVerDetalles;


    public CategoriaDomain(String nombreCategoria, String descripcionCategoria, String imagenCategoria, String ButtonAgregarCarrito, String ButtonVerdetalles) {

        this.nombreCategoria = nombreCategoria;
        this.DescripcionCategoria = descripcionCategoria;
        this.ImagenCategoria = imagenCategoria;
        this.ButtonAgregarCarrito = ButtonAgregarCarrito;
        this.ButtonVerDetalles = ButtonVerdetalles;
    }

    public String getNombreCategoria() {return nombreCategoria;}
    public void setNombreCategoria(String nombreCategoria) {this.nombreCategoria = nombreCategoria;}

    public String getDescripcionCategoria() {
        return DescripcionCategoria;
    }
    public void setDescripcionCategoria(String descripcionCategoria) {DescripcionCategoria = descripcionCategoria;}

    public String getImagenCategoria() {
        return ImagenCategoria;
    }
    public void setImagenCategoria(String imagenCategoria) {ImagenCategoria = imagenCategoria;}

    public String getButtonAgregarCarrito() {return ButtonAgregarCarrito;}
    public void setButtonAgregarCarrito(String buttonAgregarCarrito) {buttonAgregarCarrito = buttonAgregarCarrito;}

    public String getButtonVerDetalles() {return ButtonVerDetalles;}
    public void setButtonVerDetalles(String buttonVerDetalles) {buttonVerDetalles = buttonVerDetalles;}

    }


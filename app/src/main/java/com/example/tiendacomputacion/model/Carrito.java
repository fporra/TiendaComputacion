package com.example.tiendacomputacion.model;

public class Carrito {
    private int id;
    private int usuarioId;  // ID del usuario (puedes obtenerlo de Firebase Auth si es necesario)
    private int productoId;  // ID del producto en el carrito
    private int cantidad;  // Cantidad del producto
    private double precioTotal;  // Precio total para este item (opcional, calculado)

    // Constructor vacío (obligatorio para Gson)
    public Carrito() {}

    // Constructor con parámetros
    public Carrito(int id, int usuarioId, int productoId, int cantidad, double precioTotal) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.productoId = productoId;
        this.cantidad = cantidad;
        this.precioTotal = precioTotal;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }
}
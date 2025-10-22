package com.example.tiendacomputacion;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tiendacomputacion.model.Producto;

public class ProductoDetalleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_detalle);

        Producto producto = (Producto) getIntent().getSerializableExtra("producto");

        TextView nombre = findViewById(R.id.nombre_detalle);
        TextView precio = findViewById(R.id.precio_detalle);
        TextView descripcion = findViewById(R.id.descripcion_detalle);
        Button agregarCarrito = findViewById(R.id.agregar_carrito_detalle);

        nombre.setText(producto.getNombre());
        precio.setText(String.valueOf(producto.getPrecio()) + " CLP");
        descripcion.setText(producto.getDescripcion());

        agregarCarrito.setOnClickListener(v -> {
            Carrito.getInstance().agregarProducto(producto);
            Toast.makeText(v.getContext(), producto.getNombre() + " agregado al carrito", Toast.LENGTH_SHORT).show();
        });
    }
}
package com.example.tiendacomputacion;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tiendacomputacion.model.Producto;

public class EditProductActivity extends AppCompatActivity {

    private AppDatabase db;
    private Producto producto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        db = AppDatabase.getInstance(this);
        producto = (Producto) getIntent().getSerializableExtra("producto");

        EditText nombre = findViewById(R.id.nombre_producto_edit);
        EditText descripcion = findViewById(R.id.descripcion_producto_edit);
        EditText precio = findViewById(R.id.precio_producto_edit);
        EditText stock = findViewById(R.id.stock_producto_edit);
        Button guardar = findViewById(R.id.guardar_producto_edit);

        nombre.setText(producto.getNombre());
        descripcion.setText(producto.getDescripcion());
        precio.setText(String.valueOf(producto.getPrecio()));
        stock.setText(String.valueOf(producto.getStock()));

        guardar.setOnClickListener(v -> {
            String nombreString = nombre.getText().toString();
            String descripcionString = descripcion.getText().toString();
            double precioDouble = Double.parseDouble(precio.getText().toString());
            int stockInt = Integer.parseInt(stock.getText().toString());

            producto.setNombre(nombreString);
            producto.setDescripcion(descripcionString);
            producto.setPrecio(precioDouble);
            producto.setStock(stockInt);

            new Thread(() -> {
                db.productoDao().update(producto);
                runOnUiThread(() -> {
                    Toast.makeText(this, "Producto actualizado con éxito", Toast.LENGTH_SHORT).show();
                    finish();
                });
            }).start();
        });
    }
}
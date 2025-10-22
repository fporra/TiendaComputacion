package com.example.tiendacomputacion;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tiendacomputacion.adapter.CarritoAdapter;
import com.example.tiendacomputacion.model.Producto;

import java.util.List;

public class CarritoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_carrito);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Producto> productos = Carrito.getInstance().getProductos();
        CarritoAdapter adapter = new CarritoAdapter(productos);
        recyclerView.setAdapter(adapter);

        TextView total = findViewById(R.id.total);
        total.setText("Total: " + Carrito.getInstance().getTotal() + " CLP");

        Button comprar = findViewById(R.id.comprar);
        comprar.setOnClickListener(v -> {
            Toast.makeText(this, "Compra realizada con éxito", Toast.LENGTH_SHORT).show();
        });
    }
}
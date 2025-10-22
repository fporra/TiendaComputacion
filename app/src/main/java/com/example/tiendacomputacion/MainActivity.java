package com.example.tiendacomputacion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tiendacomputacion.adapter.ProductoAdapter;
import com.example.tiendacomputacion.model.Producto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private AppDatabase db;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        db = AppDatabase.getInstance(this);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
            return;
        }

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        FloatingActionButton fab = findViewById(R.id.fab_carrito);
        fab.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, CarritoActivity.class));
        });

        Button adminButton = findViewById(R.id.admin_button);
        if (currentUser.getEmail().equals("fporramaraboli@gmail.com")) {
            adminButton.setVisibility(View.VISIBLE);
            adminButton.setOnClickListener(v -> {
                startActivity(new Intent(MainActivity.this, CreateProductActivity.class));
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProducts();
    }

    private void loadProducts() {
        new Thread(() -> {
            List<Producto> productos = db.productoDao().getAll();
            if (productos.isEmpty()) {
                // Add some dummy products if the database is empty
                db.productoDao().insertAll(
                        new Producto("Laptop", "Laptop de última generación", 1000.0, "", 10),
                        new Producto("Mouse", "Mouse inalámbrico", 25.0, "", 20),
                        new Producto("Teclado", "Teclado mecánico", 75.0, "", 15)
                );
                productos = db.productoDao().getAll();
            }
            List<Producto> finalProductos = productos;
            runOnUiThread(() -> {
                ProductoAdapter adapter = new ProductoAdapter(finalProductos);
                recyclerView.setAdapter(adapter);
            });
        }).start();
    }
}

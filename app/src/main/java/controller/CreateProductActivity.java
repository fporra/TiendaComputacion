package controller;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tiendacomputacion.R;
import com.example.tiendacomputacion.model.AppDatabase;
import com.example.tiendacomputacion.model.Producto;

public class CreateProductActivity extends AppCompatActivity {

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);

        db = AppDatabase.getInstance(this);

        EditText nombre = findViewById(R.id.nombre_producto);
        EditText descripcion = findViewById(R.id.descripcion_producto);
        EditText precio = findViewById(R.id.precio_producto);
        EditText stock = findViewById(R.id.stock_producto);
        Button crear = findViewById(R.id.crear_producto);

        crear.setOnClickListener(v -> {
            String nombreString = nombre.getText().toString();
            String descripcionString = descripcion.getText().toString();
            double precioDouble = Double.parseDouble(precio.getText().toString());
            int stockInt = Integer.parseInt(stock.getText().toString());

            new Thread(() -> {
                db.productoDao().insertAll(new Producto(nombreString, descripcionString, precioDouble, "", stockInt));
                runOnUiThread(() -> {
                    Toast.makeText(this, "Producto creado con éxito", Toast.LENGTH_SHORT).show();
                    finish();
                });
            }).start();
        });
    }
}
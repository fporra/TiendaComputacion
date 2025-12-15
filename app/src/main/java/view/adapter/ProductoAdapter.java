package view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tiendacomputacion.model.AppDatabase;
import com.example.tiendacomputacion.model.Carrito;
import controller.EditProductActivity;
import controller.ProductoDetalleActivity;
import com.example.tiendacomputacion.R;
import com.example.tiendacomputacion.model.Producto;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ViewHolder> {
    private List<Producto> productos;

    public ProductoAdapter(List<Producto> productos) {
        this.productos = productos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Producto producto = productos.get(position);
        holder.nombre.setText(producto.getNombre());
        holder.precio.setText(String.valueOf(producto.getPrecio()) + " CLP");

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null && currentUser.getEmail().equals("fporramaraboli@gmail.com")) {
            holder.adminActions.setVisibility(View.VISIBLE);
        } else {
            holder.adminActions.setVisibility(View.GONE);
        }

        holder.agregarCarrito.setOnClickListener(v -> {
            Carrito.getInstance().agregarProducto(producto);
            Toast.makeText(v.getContext(), producto.getNombre() + " agregado al carrito", Toast.LENGTH_SHORT).show();
        });

        holder.editButton.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, EditProductActivity.class);
            intent.putExtra("producto", producto);
            context.startActivity(intent);
        });

        holder.deleteButton.setOnClickListener(v -> {
            new Thread(() -> {
                AppDatabase.getInstance(v.getContext()).productoDao().delete(producto);
            }).start();
            productos.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, productos.size());
            Toast.makeText(v.getContext(), "Producto eliminado", Toast.LENGTH_SHORT).show();
        });

        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, ProductoDetalleActivity.class);
            intent.putExtra("producto", producto);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre;
        TextView precio;
        Button agregarCarrito;
        LinearLayout adminActions;
        Button editButton;
        Button deleteButton;

        public ViewHolder(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre);
            precio = itemView.findViewById(R.id.precio);
            agregarCarrito = itemView.findViewById(R.id.agregar_carrito);
            adminActions = itemView.findViewById(R.id.admin_actions);
            editButton = itemView.findViewById(R.id.edit_button);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }
}

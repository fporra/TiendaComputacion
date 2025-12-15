package view.adapter;

import com.example.tiendacomputacion.model.*;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    // Leer todos los productos (GET /posts)
    @GET("posts")
    Call<List<Producto>> getProductos();

    // Leer un producto por ID (GET /posts/{id})
    @GET("posts/{id}")
    Call<Producto> getProducto(@Path("id") int id);

    // Crear producto (POST /posts)
    @POST("posts")
    Call<Producto> createProducto(@Body Producto producto);

    // Actualizar producto (PUT /posts/{id})
    @PUT("posts/{id}")
    Call<Producto> updateProducto(@Path("id") int id, @Body Producto producto);

    // Eliminar producto (DELETE /posts/{id})
    @DELETE("posts/{id}")
    Call<Void> deleteProducto(@Path("id") int id);
}
<?php
header("Content-Type: application/json; charset=UTF-8");
include 'db_connect.php';

//POST
$data = json_decode(file_get_contents("php://input"));

// Verificar que los datos necesarios no esten vacios
if (
    !empty($data->id) &&
    !empty($data->nombre) &&
    isset($data->descripcion) &&
    !empty($data->precio) &&!empty($data->stock) &&
    isset($data->imagen)
) {
    // Escapar los datos para prevenir inyeccion SQL
    $id = mysqli_real_escape_string($conn, $data->id);
    $nombre = mysqli_real_escape_string($conn, $data->nombre);
    $descripcion = mysqli_real_escape_string($conn, $data->descripcion);
    $precio = mysqli_real_escape_string($conn, $data->precio);
    $stock = mysqli_real_escape_string($conn, $data->stock);
    $imagen = mysqli_real_escape_string($conn, $data->imagen);

    // Crear la consulta SQL para actualizar el producto
    $sql = "UPDATE producto SET             nombre = '$nombre',descripcion = '$descripcion', 
            precio = '$precio', 
            stock = '$stock',
            imagen = '$imagen'
          WHERE id = $id";

    if (mysqli_query($conn, $sql)) {
        if (mysqli_affected_rows($conn) > 0) {
            // Si la actualizacion fue exitosa y afecto a alguna fila
            http_response_code(200); // 200 OK
            echo json_encode(array("message" => "Producto actualizado con éxito."));        } else {
            // Si no se encontró el producto o no hubo cambios
            http_response_code(404); // 404 Not Found
            echo json_encode(array("message" => "No se encontró el producto o no hubo cambios que guardar."));
        }
    } else {
        // Si hubo un error en la consulta
        http_response_code(503); // 503 Service Unavailable
        echo json_encode(array("message" => "Error al actualizar el producto: " . mysqli_error($conn)));
    }
} else {
    // Si faltan datos
    http_response_code(400); // 400 Bad Request
    echo json_encode(array("message" => "Datos incompletos."));
}

mysqli_close($conn);
?>
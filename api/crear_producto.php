<?php
header("Content-Type: application/json; charset=UTF-8");include 'db_connect.php';


$data = json_decode(file_get_contents("php://input"));


if (
    !empty($data->nombre) &&
    isset($data->descripcion) && 
    !empty($data->precio) &&
    !empty($data->stock) &&
    isset($data->imagen) 
) {
   
    $nombre = mysqli_real_escape_string($conn, $data->nombre);
    $descripcion = mysqli_real_escape_string($conn, $data->descripcion);
    $precio = mysqli_real_escape_string($conn, $data->precio);
    $stock = mysqli_real_escape_string($conn, $data->stock);
    $imagen = mysqli_real_escape_string($conn, $data->imagen);

    
    $sql = "INSERT INTO producto (nombre, descripcion, precio, imagen, stock) VALUES ('$nombre', '$descripcion', '$precio', '$imagen', '$stock')";

    if (mysqli_query($conn, $sql)) {
        // Si la inserción fue exitosa
        http_response_code(201); // 201 Created
        echo json_encode(array("message" => "Producto creado con éxito."));
    } else {
        // Si hubo un error en la inserción
        http_response_code(503); // 503 Service Unavailable
        echo json_encode(array("message" => "Error al crear el producto: " . mysqli_error($conn)));
    }
} else {
    // Si faltan datos
    http_response_code(400); // 400 Bad Request
    echo json_encode(array("message" => "Datos incompletos."));
}

mysqli_close($conn);
?>
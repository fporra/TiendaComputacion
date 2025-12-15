<?php
header("Content-Type: application/json; charset=UTF-8");include 'db_connect.php';

$data = json_decode(file_get_contents("php://input"));

if (!empty($data->id)) {
    $id = mysqli_real_escape_string($conn, $data->id);

    $sql = "DELETE FROM producto WHERE id = $id";

    if (mysqli_query($conn, $sql)) {
        if (mysqli_affected_rows($conn) > 0) {
            http_response_code(200); // OK
            echo json_encode(array("message" => "Producto eliminado con Ã©xito."));
        } else {
            http_response_code(404); // Not Found
            echo json_encode(array("message" => "No se encontrÃ³ el producto."));
        }
    } else {
        http_response_code(503); // Service Unavailable
        echo json_encode(array("message" => "Error al eliminar el producto: " . mysqli_error($conn)));
    }
} else {
    http_response_code(400); // Bad Request
    echo json_encode(array("message" => "ID de producto no proporcionado."));
}

mysqli_close($conn);
?>
<?php
header("Content-Type: application/json; charset=UTF-8");
include 'db_connect.php';

$data = json_decode(file_get_contents("php://input"));

if (
    !empty($data->total) &&
    !empty($data->detalles)
) {
    $total = mysqli_real_escape_string($conn, $data->total);
    // El detalle ya es un string JSON, no necesita escape adicional
    $detalles = $data->detalles; 

    $sql = "INSERT INTO historial_compras (total, detalles) VALUES ('$total', '$detalles')";

    if (mysqli_query($conn, $sql)) {
        http_response_code(201); // Created
        echo json_encode(array("message" => "Compra registrada con éxito."));
    } else {
        http_response_code(503); // Service Unavailable
        echo json_encode(array("message" => "Error al registrar la compra: " . mysqli_error($conn)));
    }
} else {
    http_response_code(400); // Bad Request
    echo json_encode(array("message" => "Datos de la compra incompletos."));
}

mysqli_close($conn);
?>
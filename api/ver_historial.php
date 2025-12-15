<?php
header("Content-Type: application/json; charset=UTF-8");
include 'db_connect.php';

// Consulta para obtener todos los registros del historial, ordenados por fecha más reciente
$sql = "SELECT id, fecha, total FROM historial_compras ORDER BY fecha DESC";
$result = mysqli_query($conn, $sql);

if ($result) {
    $historial = array();
    while ($row = mysqli_fetch_assoc($result)) {
        // Añadir cada fila al array de historial
        $historial[] = $row;
    }
    // Devolver el historial como un JSON
    echo json_encode($historial);
} else {
    // Si hubo un error en la consulta
    http_response_code(500); // Internal Server Error
    echo json_encode(array("message" => "Error al obtener el historial: " . mysqli_error($conn)));
}

mysqli_close($conn);
?>
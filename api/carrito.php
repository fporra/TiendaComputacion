<?php
include 'db_connect.php';
header('Content-Type: application/json');
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: GET, POST, DELETE');

$method = $_SERVER['REQUEST_METHOD'];
switch ($method) {
    case 'GET':
        $user_id = $_GET['user_id'];  
        $stmt = $conn->prepare("SELECT * FROM carrito WHERE user_id=?");
        $stmt->bind_param("s", $user_id);
        $stmt->execute();
        $result = $stmt->get_result();
        $carrito = [];
        while ($row = $result->fetch_assoc()) {
            $carrito[] = $row;
        }
        echo json_encode($carrito);
        break;
    case 'POST':
        $data = json_decode(file_get_contents('php://input'), true);
        $stmt = $conn->prepare("INSERT INTO carrito (user_id, producto_id, cantidad, precio_total) VALUES (?, ?, ?, ?)");
        $stmt->bind_param("siid", $data['user_id'], $data['producto_id'], $data['cantidad'], $data['precio_total']);
        $stmt->execute();
        echo json_encode(['id' => $conn->insert_id]);
        break;
    case 'DELETE':
        $id = $_GET['id'];  // ID del item en carrito
        $stmt = $conn->prepare("DELETE FROM carrito WHERE id=?");
        $stmt->bind_param("i", $id);
        $stmt->execute();
        echo json_encode(['success' => true]);
        break;
}
$conn->close();
?>
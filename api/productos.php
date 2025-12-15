<?php
include 'db_connect.php';
header('Content-Type: application/json');
header('Access-Control-Allow-Origin: *');  
header('Access-Control-Allow-Methods: GET, POST, PUT, DELETE');
header('Access-Control-Allow-Headers: Content-Type');

$method = $_SERVER['REQUEST_METHOD'];
switch ($method) {
    case 'GET':
        $result = $conn->query("SELECT * FROM producto");
        $productos = [];
        while ($row = $result->fetch_assoc()) {
            $productos[] = $row;
        }
        echo json_encode($productos);
        break;
    case 'POST':
        $data = json_decode(file_get_contents('php://input'), true);
        $stmt = $conn->prepare("INSERT INTO producto (nombre, descripcion, precio, imagen, stock) VALUES (?, ?, ?, ?, ?)");
        $stmt->bind_param("ssdsi", $data['nombre'], $data['descripcion'], $data['precio'], $data['imagen'], $data['stock']);
        $stmt->execute();
        echo json_encode(['id' => $conn->insert_id]);
        break;
    case 'PUT':
        $data = json_decode(file_get_contents('php://input'), true);
        $id = $_GET['id'];  // Asume que pasas ID en URL, ej. productos.php?id=1
        $stmt = $conn->prepare("UPDATE producto SET nombre=?, descripcion=?, precio=?, imagen=?, stock=? WHERE id=?");
        $stmt->bind_param("ssdsi", $data['nombre'], $data['descripcion'], $data['precio'], $data['imagen'], $data['stock'], $id);
        $stmt->execute();
        echo json_encode(['success' => true]);
        break;
    case 'DELETE':
        $id = $_GET['id'];
        $stmt = $conn->prepare("DELETE FROM producto WHERE id=?");
        $stmt->bind_param("i", $id);
        $stmt->execute();
        echo json_encode(['success' => true]);
        break;
}
$conn->close();
?>
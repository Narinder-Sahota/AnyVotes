<?php

if($_SERVER['REQUEST_METHOD']== 'POST'){
    $name = $_POST['name'];
    $email = $_POST['email'];
    $password = $_POST['password'];

    $password = password_hash($password, PASSWORD_DEFAULT);

    require_once 'connect.php';

    $sql = "INSET INTO users_table (name, email,password) VALUES ('$name','$email','$password')";

    if( mysqli_query($conn, $sql)){
        $result["success"] = "1";
        $result["message"] = "success";

        echo json_encode($result);
        mysqli_close($conn);
    }
}

?>
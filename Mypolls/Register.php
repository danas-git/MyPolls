<?php
$con=mysqli_connect("localhost","my_user","my_password","my_db");

$username=$_POST["username"];
$password=$_POST["password"];
$age=$_POST["age"];

$statement=mysqli_prepare($con, "Insert into user_login (username,password,age) values (?,?,?) ");
mysqli_stmt_bind_param($statement, "ssi", $username, $password, $age);
mysqli_stmt_execute($statement);

mysqli_stmt_close($statement);

mysqli_close($con);

?>

<?php
$username=$_POST['username'];
$password=$_POST['password'];
$con=mysqli_connect('mysql17.000webhost.com','a9095771_poll','almostfame5','a9095771_poll');
if(mysqli_connect_errno()){
echo 'Error : Not connected';
exit;
}
$statement=mysqli_prepare($con," select * from user_login where username=? and password=?");
mysqli_stmt_bind_param($statement,"ss",$username, $password);
mysqli_stmt_execute($statement);
mysqli_stmt_store_result($statement);
mysqli_stmt_bind_result($statement, $userid, $username,$password,$age,$email)
$user=array();
while(mysqli_stmt_fetch($statement)){
$user[username]=$username;
$user[password]=$password;
$user[age]=$age;
}

echo json_encode($user);
mysqli_stmt_close($statement);
mysqli_close($con);
?>

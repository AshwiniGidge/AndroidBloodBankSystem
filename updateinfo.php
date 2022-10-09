<?php 
$con=mysqli_connect("localhost","id16428845_bbmsdb","Password@3344","id16428845_bbms");
$uid=$_POST["uid"];	
$fname = $_POST["fname"];
$lname = $_POST["lname"];
$phno=$_POST["phno"];
$email=$_POST["email"];
$password=$_POST["password"];
$bg=$_POST["bg"];
$gender=$_POST["gender"];
$state=$_POST["state"];
$district=$_POST["district"];
$city=$_POST["city"];
$pincode=$_POST["pincode"];
$sql = "UPDATE users SET fname = '$fname',lname = '$lname',phno ='$phno',emailid ='$email',password ='$password',bloodgroup ='$bg',gender ='$gender',state ='$state',district ='$district',city ='$city',pincode ='$pincode' WHERE uid = $uid; ";
if ($con->query($sql) === TRUE) 
{	
	echo "user updated sucessfully"; 
} 
else 
{
	echo "Error: " . $sql . "<br>" . $con->error;
}
?>
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
$sql = "INSERT INTO users VALUES('$uid','$fname','$lname','$phno','$email','$password','$bg','$gender','$state','$district','$city','$pincode');";
$sql1="SELECT * FROM users WHERE uid = '$uid' ";
$result = mysqli_query($con,$sql1);
if($result->num_rows > 0){
	echo "user with this uid already exists" ;
}else{
	if ($con->query($sql) === TRUE) 
	{
		echo "User Registered Successfully" ;
	} 
	else 
	{
		echo "Error: " . $sql . "<br>" . $con->error;
	}	
}	
?>
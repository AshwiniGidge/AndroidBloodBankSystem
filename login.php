<?php 
		$con=mysqli_connect("localhost","id16428845_bbmsdb","Password@3344","id16428845_bbms");
	
		$username = $_POST["username"];
		$password = $_POST["password"];

		$sql = "SELECT * FROM users WHERE uid = '$username' AND password = '$password'";
		$result = mysqli_query($con,$sql);
		
		if($result->num_rows > 0){
			echo "logged in successfully" ;
		}else{
  			 echo "user not found";
}
?>
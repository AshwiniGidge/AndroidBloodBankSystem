<?php 
		$con=mysqli_connect("localhost","id16428845_bbmsdb","Password@3344","id16428845_bbms");
	
		$email_id = $_POST["email_id"];

		$sql = "SELECT * FROM users WHERE emailid = '$email_id'";
		$result = mysqli_query($con,$sql);
		
		if($result->num_rows > 0){
			echo "logged in successfully" ;
		}else{
  			 echo "user not found";
}
?>
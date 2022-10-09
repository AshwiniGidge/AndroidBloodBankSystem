<?php 
$con=mysqli_connect("localhost","id16428845_bbmsdb","Password@3344","id16428845_bbms");
$ruid = $_POST["ruid"];
$rname = $_POST["rname"];
$date=$_POST["date"];
$rbg=$_POST["rbg"];
$status=$_POST["status"];
$sql2="SELECT * FROM tasks WHERE ruid = '$ruid' AND status = 'pending'";
$result = mysqli_query($con,$sql2);
if($result->num_rows > 0){
	echo "We Have Alredy Submitted Your Blood request" ;
}else{
	$sql = "INSERT INTO tasks (ruid,rname,date,rbg,status) VALUES('$ruid','$rname','$date','$rbg','$status') ";
	//$result = mysqli_query($con,$sql);	
	if ($con->query($sql) === TRUE) 
	{
		echo "Blood Requested Successfully" ;
	} 
	else 
	{
		echo "Error: " . $sql . "<br>" . $con->error;
	}
}
?>
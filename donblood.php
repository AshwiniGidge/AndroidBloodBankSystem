<?php 
$con=mysqli_connect("localhost","id16428845_bbmsdb","Password@3344","id16428845_bbms");
$ruid = $_POST["ruid"];
$srno1 =(int)$_POST["srno"];
$duid = $_POST["duid"];
$dname = $_POST["dname"];
$dbg=$_POST["dbg"];
function get_data($con,$ruid)  
{  
	$query = "SELECT * FROM users WHERE uid = '$ruid' ";
	$result = mysqli_query($con, $query); 
	$my_data = array();  
	while($row = mysqli_fetch_array($result))  
	{  
		$my_data[] = array(  
		'fname'          =>     $row["fname"], 
		'lname'          =>     $row["lname"], 
		'phno'           =>     $row["phno"],
		'emailid'        =>     $row["emailid"], 
		'bloodgroup'     =>     $row["bloodgroup"],
		'state'          =>     $row["state"], 
		'district'       =>     $row["district"], 
		'city'           =>     $row["city"],  
		'pincode'        =>     $row["pincode"],   
		);  
	}  
	return json_encode($my_data);  
}  
$sql = "UPDATE tasks SET duid = '$duid',dname = '$dname',dbg ='$dbg',status='completed' WHERE srno = $srno1; ";
if ($con->query($sql) === TRUE) 
{	
	$contents = get_data($con,$ruid);
	echo $contents; 
} 
else 
{
	echo "Error: " . $sql . "<br>" . $con->error;
}
?>	
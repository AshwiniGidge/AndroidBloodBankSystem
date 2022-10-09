 <?php  
//  
function get_data()  
{  
	$connect = mysqli_connect("localhost","id16428845_bbmsdb","Password@3344","id16428845_bbms");  
	$myuid = $_POST["myuid"];
	$query="SELECT date,dbg,status,ruid FROM tasks WHERE ruid='$myuid' OR duid='$myuid'";
	$result = mysqli_query($connect, $query); 
	$task_data = array();  
	while($row = mysqli_fetch_array($result))  
	{  
		$task_data[] = array(  
		'date'          =>     $row["date"], 
		'ruid'          =>     $row["ruid"], 
		'status'        =>     $row["status"],  
		'dbg'           =>     $row["dbg"],  
		);  
	}  
	return json_encode($task_data);  
}  
$contents = get_data();  
echo $contents;  
?>
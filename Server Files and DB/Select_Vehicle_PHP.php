<?php
$vehicle_num = $_POST['vehicle_num'];
$servername = "localhost";
$username = "gridmepc_admin";
$password = "vivekadmin123";
$dbname = "gridmepc_vehicledetails";


// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

$sql = "select * from VehicleData where vehicle_num= ".$vehicle_num;

$result = $conn->query($sql);

if ($result->num_rows > 0) {


   while ($row=$result->fetch_assoc())
   {	
	echo json_encode($row);
	}
   }
    else {
		 echo "{'id':'null'}";
//echo "Error: " . $sql . "<br>" . $conn->error;
   
}

$conn->close();

?>
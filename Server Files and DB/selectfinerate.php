<?php


$vehicle_type = $_POST['vehicle_type'];
$fine_type = $_POST['fine_type'];

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

$sql = "select * from FinerateData where vehicle_type=$vehicle_type AND fine_type=$fine_type";

$result = $conn->query($sql);

if ($result->num_rows > 0) {


   while ($row=$result->fetch_assoc())
   {	
	echo json_encode($row);
	}
   }
    else {
//echo "Error: " . $sql . "<br>" . $conn->error;
    echo "{'Charge':''}";
}

$conn->close();

?>
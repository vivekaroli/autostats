<?php
$vehicle_num = $_POST['vehicle_num'];

$servername = "localhost";
$username = "gridmepc_admin";
$password = "vivekadmin123";

//$username = "root";
//$password = "";
$dbname = "gridmepc_vehicledetails";
$resArray = array();
$res = array();

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

$sql = "select * from FineData where vehicle_num=$vehicle_num";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    while ($row=$result->fetch_assoc()){
	array_push($resArray, $row);
	}
	$res['status'] = 'success';
	$res['array'] = $resArray;
	echo json_encode($res);
   }
else {
   //echo "Error: " . $sql . "<br>" . $conn->error;
   $res['status'] = 'failed';
   $res['message'] = 'Not found';
   echo json_encode($res);
}

$conn->close();

?>
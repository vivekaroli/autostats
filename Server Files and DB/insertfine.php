<?php


$vehicle_type = $_POST['vehicle_type'];
$fine_type = $_POST['fine_type'];
$charge = $_POST['charge'];

$id = intval($_POST['id']);
$action = $_POST['action'];

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

if(strcmp($action,"'update'")==0)
{

	$sql = "UPDATE FinerateData SET charge=$charge WHERE id=$id";
}
else
{
$sql = "INSERT INTO FinerateData (vehicle_type,fine_type,charge)
VALUES ($vehicle_type,$fine_type,$charge)";
}
if ($conn->query($sql) === TRUE) {
    echo "{'success':true,'message':'Fine Charge Updated'}";
} else {
   // echo "Error: " . $sql . "<br>" . $conn->error;
  
	  echo "Error: " . $sql . "<br>" . $conn->error; 
   }



$conn->close();

?>
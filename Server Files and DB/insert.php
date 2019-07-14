<?php

$vehicle_type = $_POST['vehicle_type'];
$vehicle_num = $_POST['vehicle_num'];

$rc_name = $_POST['rc_name'];
$address_1 = $_POST['address_1'];
$address_2 = $_POST['address_2'];
$district = $_POST['district'];
$state = $_POST['state'];
$country = $_POST['country'];
$pin_code = $_POST['pin_code'];
$phone_num = $_POST['phone_num'];
$email_id = $_POST['email_id'];

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


$sql = "INSERT INTO VehicleData (vehicle_type,vehicle_num,rc_name, address_1,address_2,district,state,country,pin_code,phone_num,email_id)
VALUES ($vehicle_type,$vehicle_num,$rc_name,$address_1,$address_2,$district,$state,$country,$pin_code,$phone_num,$email_id)";

if ($conn->query($sql) === TRUE) {
    echo "{'success':true,'message':'New record created successfully'}";
} else {
   // echo "Error: " . $sql . "<br>" . $conn->error;
   if(strpos($conn->error, 'vehicle_num') !== false)
   {
    echo "{'success':false,'message':'Vehicle Number already registered'}";
   }
   else
   {
	  echo "Error: " . $sql . "<br>" . $conn->error; 
   }

}

$conn->close();

?>
<?php
$fine_type = $_POST['fine_type'];
$date_time = $_POST['dateTime'];
$personid = intval($_POST['vehicle_id']);
$personid2 = floor($personid);
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
$sql = "select * from VehicleData where id=$personid2";

$result = $conn->query($sql);

if ($result->num_rows > 0) {


   while ($row=$result->fetch_assoc())
   {	
	$vehicle_num = $row['vehicle_num'];
	$vehicle_type = $row['vehicle_type'];
	$sql2 = "select * from FinerateData where vehicle_type='$vehicle_type' AND fine_type=$fine_type";
	$result1 = $conn->query($sql2);

if ($result1->num_rows > 0) {
   while ($row1=$result1->fetch_assoc())
   {	
   $charge = $row1['Charge'];

   	$sql3 = "INSERT INTO FineData (vehicle_num,vehicle_type,fine_type,fine_charge,datetime)
VALUES ('$vehicle_num','$vehicle_type',$fine_type,'$charge',$date_time)";

if ($conn->query($sql3) === TRUE) {
    echo "{'success':true,'message':'Fine added successfully'}";
} else {
   // echo "Error: " . $sql . "<br>" . $conn->error;
  
	  echo "{'success':false,'message':'Error in Adding Fine'}"; 
   }
   }
}
else {
echo "{'success':false,'message':'Charge for selected fine type is not specified'}";
   // echo "{'ids':'null'}";
}

	}
   }
    else {
//echo "Error: " . $sql . "<br>" . $conn->error;
echo "{'success':false,'message':'Error in getting Vehicle Number'}";
  //  echo "{'id':'null'}";
}




$conn->close();

?>
<?php

/*
Our "config.inc.php" file connects to database every time we include or require
it within a php script.  Since we want this script to add a new user to our db,
we will be talking with our database, and therefore,
let's require the connection to happen:
*/
require("config.inc.php");
//get our variables from url

if((isset($_GET['shipname'])) && (isset($_GET['roomnum'])))
	{

        $ship = $_GET['shipname'];
        $room = $_GET['roomnum'];
	}


//initial query
$query = "Select Comment,Username FROM comments WHERE Ship_Name = '$ship' AND Room_Num = '$room'";
$query_params = null;
//execute query
try {
    $stmt   = $db->prepare($query);
    $result = $stmt->execute($query_params);
}
catch (PDOException $ex) {
    $response["success"] = 0;
    $response["message"] = "Database Error!";
    die(json_encode($response));
}

// Finally, we can retrieve all of the found rows into an array using fetchAll 
$rows = $stmt->fetchAll();


if ($rows) {
    $response["success"] = 1;
    $response["message"] = "INCOMING COMMENTS!";
    $response["posts"]   = array();
    
    foreach ($rows as $row) {
        $post             = array();
        $post["Comment"] = $row["Comment"];
        $post["Username"]    = $row["Username"];
        
        
        //update our repsonse JSON data
        array_push($response["posts"], $post);
    }
    
    // echoing JSON response
    echo json_encode($response);
    
    
} else {
    $response["success"] = 0;
    $response["message"] = "No Post Available!";
    die(json_encode($response));
}

?>

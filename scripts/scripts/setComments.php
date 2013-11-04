<?php

require("config.inc.php");

	/*if (isset($_GET['username'])) {
$Username = $_GET['username'];
}
if (isset($_GET['password'])) {
$Password = $_GET['password'];
}

echo $Username;
echo $Password;*/
    //If we have made it here without dying, then we are in the clear to 
    //create a new user.  Let's setup our new query to create a user.  
    //Again, to protect against sql injects, user tokens such as :user and :pass
    $query = "INSERT INTO comments ( Ship_Name, Room_Num, Comment, Username ) VALUES ( :ship, :room, :msg, :user ) ";
    
    //Again, we need to update our tokens with the actual data:
    //Again, we need to update our tokens with the actual data:
    if((isset($_GET['shipname'])) && (isset($_GET['roomnum']))&&(isset($_GET['message'])) && (isset($_GET['username'])))
	{
	$query_params = array(

		':ship' => $_GET['shipname'],
        ':room' => $_GET['roomnum'],
        ':msg' => $_GET['message'],
        ':user' => $_GET['username']
    );
	}
    
    //time to run our query, and create the user
    try {
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
    }
    catch (PDOException $ex) {
        // For testing, you could use a die and message. 
        die("Failed to run query: " . $ex->getMessage());
        
        //or just use this use this one:
       $response["success"] = 0;
       $response["message"] = "Database Error1. Please Try Again!";
		die(json_encode($response));
    }
    
    //If we have made it this far without dying, we have successfully added
    //a new user to our database.  We could do a few things here, such as 
    //redirect to the login page.  Instead we are going to echo out some
    //json data that will be read by the Android application, which will login
    //the user (or redirect to a different activity, I'm not sure yet..)
    $response["success"] = 1;
    $response["message"] = "Comment Successfully Added!";
    echo json_encode($response);
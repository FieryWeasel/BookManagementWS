<?php
include_once '../conectdb.php';
include_once '../debug.php';
include_once '../keys.php';

$name = explode('auth.php', $_SERVER['REQUEST_URI']);
if(isset($name[1]) ){
	$array = explode('/', $name[1]);

	$name_function = "";
	if(isset($array[2]) ){
		$name_function = $array[2];
	}

	if( isset($array[1]) && $array[1] == "user"){
	switch ($name_function){
		case "isExist":
			isExist($_POST);
			break;
		case "canConnect":
			canConnect($_POST);
			break;
		default:
			?>No function to launch<br/><?php
			break;
		}
	}
}

function isExist($connectInfos) {
	$keys = new keys();
	
	// Verify informations about the request
	if(!isset($connectInfos['id'])){
		displayDebugMsg("Error - Need a 'user_id'");
		$arr_response[$keys->RES_key] = $keys->RES_Result_No;
		$arr_response[$keys->ERR_key] = $keys->ERR_Bad_Arguments;
		echo(json_encode($arr_response));
		return;
	}
		
	// Try to know if the user exist in the database
	$jsonObj = getResultFromDataBase('SELECT * FROM account WHERE user_id='. $connectInfos['id'] .'');
	
	if($jsonObj == ""){
		$arr_response[$keys->RES_key] = $keys->RES_Result_No;
		$arr_response[$keys->ERR_key] = $keys->ERR_No_Result_Found;
	} else {
		displayDebugMsg("Result here for user_id : ".$user_id);
		$arr_response[$keys->RES_key] = $keys->RES_Result_Yes;
	}
	
	echo(json_encode($arr_response));
}

function canConnect($connectInfos){
	$keys = new keys();
	
	// Verify informations about the request
	if(!isset($connectInfos["crypted_key"])){
		displayDebugMsg("Error - Need a 'cryted_key'");
		$arr_response[$keys->RES_key] = $keys->RES_Result_No;
		$arr_response[$keys->ERR_key] = $keys->ERR_Bad_Arguments;
		$arr_response["crypted_key"] = $connectInfos["crypted_key"];
		echo(json_encode($arr_response));
		return;
	}
	
	// Try to know if the user's information of connection is correct
	$jsonObj = getResultFromDataBase('SELECT * FROM account WHERE crypted_key LIKE \''. $connectInfos["crypted_key"] .'\'');
		
	if($jsonObj == ""){
		$arr_response[$keys->RES_key] = $keys->RES_Result_No;
		$arr_response["crypted_key"] = $connectInfos["crypted_key"];
	} else {
		$arr_response[$keys->RES_key] = $keys->RES_Result_Yes;
		$arr_response["id"] = $jsonObj[0]["user_id"];
	}
	
	echo(json_encode($arr_response));
}


/**
 * Encrypting password
 * @param password
 * returns encrypted password
 */
function hashSSHA($salt, $password) {

	$encrypted = base64_encode(sha1($password . $salt, true) . $salt);
	
	return $encrypted;
}

?>
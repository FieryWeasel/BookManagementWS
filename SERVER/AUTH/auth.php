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
		case "add":
			add($_POST);
			break;
		case "modify":
			modify($_POST);
			break;
		case "delete":
			delete($_POST);
			break;
		default:
			?>No function to launch<br/><?php
			break;
		}
	}
}

function isExist($connectInfos) {
	$keys = new keys();
	
	if(!isset($connectInfos['id'])){
		displayDebugMsg("Error - Need a 'user_id'");
		$arr_response[$keys->RES_key] = $keys->RES_Result_No;
		$arr_response[$keys->ERR_key] = $keys->ERR_Bad_Arguments;
		echo(json_encode($arr_response));
		return;
	}
		
	// Request into the BDD
	$jsonObj = getResultFromDataBase('SELECT * FROM user WHERE id='. $connectInfos['id'] .'');
	
	if($jsonObj == ""){
		$arr_response[$keys->RES_key] = $keys->RES_Result_No;
		$arr_response[$keys->ERR_key] = $keys->ERR_No_Result_Found;
	} else {
		$arr_response[$keys->RES_key] = $keys->RES_Result_Yes;
		displayDebugMsg("Result here for user_id : ".$user_id);
	}
	
	echo(json_encode($arr_response));
}

function canConnect($userInfos){
	$keys = new keys();
	
	if(!isset($userInfos["crypted_key"])){
		displayDebugMsg("Error - Need a 'cryted_key'");
		$arr_response[$keys->RES_key] = $keys->RES_Result_No;
		$arr_response[$keys->ERR_key] = $keys->ERR_Bad_Arguments;
		$arr_response["crypted_key"] = $userInfos["crypted_key"];
		echo(json_encode($arr_response));
		return;
	}
	
	$jsonObj = getResultFromDataBase('SELECT * FROM account WHERE crypted_key LIKE \''. $userInfos["crypted_key"] .'\'');
		
	if($jsonObj == ""){
		$arr_response[$keys->RES_key] = $keys->RES_Result_No;
		$arr_response["crypted_key"] = $userInfos["crypted_key"];
	} else {
		$arr_response[$keys->RES_key] = $keys->RES_Result_Yes;
		$arr_response["id"] = $jsonObj[0]["user_id"];
	}
	
	echo(json_encode($arr_response));
}

function add($userInfos){
	$keys = new keys();
	
	if(		!isset($userInfos['id'])
		||	!isset($userInfos['crypted_key']))
	{
		displayDebugMsg("Error - Need a 'id' / 'crypted_key' argments");
		$arr_response[$keys->RES_key] = $keys->RES_Result_No;
		$arr_response[$keys->ERR_key] = $keys->ERR_Bad_Arguments;
		echo(json_encode($arr_response));
		return;
	}
	
	$jsonObj = getResultFromDataBase('SELECT * FROM user WHERE id = '.$userInfos['id']);
	
	if($jsonObj != ""){
		$arr_response[$keys->RES_key] = $keys->RES_Result_No;
		$arr_response[$keys->ERR_key] = $keys->ERR_Add_Key_Already_Exist;
		echo(json_encode($arr_response));
		return;
	}
	
	$res = getResultFromDataBase("INSERT INTO account (crypted_key, user_id) VALUES ('".$userInfos['id']."', '".$userInfos['crypted_key']."')");
	
	if($res == 0){
		$arr_response[$keys->RES_key] = $keys->RES_Result_Yes;
	} else if($res == -1){
		$arr_response[$keys->RES_key] = $keys->RES_Result_No;
	}
	
	echo(json_encode($arr_response));
}

function delete($userInfos){
	$keys = new keys();

	if(!isset($userInfos['id']))
	{
		displayDebugMsg("Error - Need a 'id' argment");
		$arr_response[$keys->RES_key] = $keys->RES_Result_No;
		$arr_response[$keys->ERR_key] = $keys->ERR_Bad_Arguments;
		echo(json_encode($arr_response));
		return;
	}
	
	$jsonObj = getResultFromDataBase('SELECT * FROM user WHERE id = '.$userInfos['id']);
	
	if($jsonObj == ""){
		$arr_response[$keys->RES_key] = $keys->RES_Result_No;
		$arr_response[$keys->ERR_key] = $keys->ERR_No_Result_Found;
		echo(json_encode($arr_response));
		return;
	}

	$res = getResultFromDataBase("DELETE FROM user WHERE id = ".$userInfos['id']."", 'account');

	if($res == 0){
		$arr_response[$keys->RES_key] = $keys->RES_Result_Yes;
	} else if($res == -1){
		$arr_response[$keys->RES_key] = $keys->RES_Result_No;
	}
	
	echo(json_encode($arr_response));
}

function modify($userInfos){
	$keys = new keys();
	var_dump($userInfos);

	if(		!isset($userInfos['id'])
		||	!isset($userInfos['crypted_key']))
	{
		displayDebugMsg("Error - Need a 'id' / 'crypted_key' argments");
		$arr_response[$keys->RES_key] = $keys->RES_Result_No;
		$arr_response[$keys->ERR_key] = $keys->ERR_Bad_Arguments;
		echo(json_encode($arr_response));
		return;
	}

	$jsonObj = getResultFromDataBase('SELECT * FROM user WHERE id = '.$userInfos['id'], 'account');

	if($jsonObj == ""){
		$arr_response[$keys->RES_key] = $keys->RES_Result_No;
		$arr_response[$keys->ERR_key] = $keys->ERR_No_Result_Found;
		echo(json_encode($arr_response));
		return;
	}

	$res = getResultFromDataBase('UPDATE account SET crypted_key=\''. $userInfos['crypted_key'] .'\' WHERE user_id='. $userInfos['id'] .'');

	if($res == 0){
		$arr_response[$keys->RES_key] = $keys->RES_Result_Yes;
	} else if($res == -1){
		$arr_response[$keys->RES_key] = $keys->RES_Result_No;
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
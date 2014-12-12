<?php
include_once 'conectdb.php';
include_once '../debug.php';
include_once '../keys.php';

$array = explode('/', $_SERVER['REQUEST_URI']);

$name_function = "";
if(isset($array[2]) ){
	$name_function = $array[2];
}

$isFunctionLaunched = false;

switch ($name_function){
	case "isExist":
		isExist($array);
		break;
	case "verifyPassword":
		verifyPassword($_POST);
		break;
	case "createUser":
		createUser($_POST);
		break;
	case "deleteUser":
		deleteUser($_POST);
		break;
	default:
		?>No function to launch<br/><?php
		break;
}


function isExist($array) {
	$keys = new keys();
	$user_name = "No_name";
	
	if(isset($array[3]) && $array[3] != 'user' || !isset($array[4])){
		displayDebugMsg("Error - Header of function "+$array[2]+" not correct (/isExist/user/XXXX)");
		$arr_response[$keys->ERR_key] = $keys->ERR_Bad_Arguments;
		echo(json_encode($arr_response));
		return;
	}
	
	$user_name = $array[4];
	
	// Request into the BDD
	$jsonObj = getResultFromDataBase('SELECT * FROM user WHERE UPPER(nickname) LIKE UPPER("'.$user_name.'")', 'auth');
	
	if($jsonObj == ""){
		$arr_response[$keys->ERR_key] = $keys->ERR_No_Result_Found;
		echo(json_encode($arr_response));
	} else {
		displayDebugMsg("Result here for ".$user_name);
		echo(json_encode($jsonObj));
	}
	
}

function verifyPassword($connectInfos){
	$keys = new keys();
	
	if(!isset($connectInfos['user_id']) || !isset($connectInfos['crypted_key'])){
		displayDebugMsg("Error - Need a 'user_id' and a 'cryted_key'");
		$arr_response[$keys->$ERR_key] = $keys->ERR_Bad_Arguents;
		echo(json_encode($arr_response));
		return;
	}
	
	$user_id = $connectInfos['user_id'];
	$crypted_key = $connectInfos['crypted_key'];
	
	$jsonObj = getResultFromDataBase('SELECT * FROM account WHERE user_id = '.$user_id.'', 'auth');
	
	if($jsonObj == ""){
		$arr_response[$keys->RES_key] = $keys->RES_Result_No;
		echo(json_encode($arr_response));
	} else {
		if(strcmp($jsonObj[0]['crypted_key'], $crypted_key) == 0){
			$arr_response[$keys->RES_key] = $keys->RES_Result_Yes;
			echo(json_encode($arr_response));
		} else {
			$arr_response[$keys->RES_key] = $keys->RES_Result_No;
			echo(json_encode($arr_response));
		}
	}
}

function createUser($userInfos){
	$keys = new keys();
	var_dump($userInfos);
	
	if(		!isset($userInfos['first_name'])
		||	!isset($userInfos['last_name'])
		||	!isset($userInfos['nickname'])
		|| 	!isset($userInfos['birth_date'])
		||	!isset($userInfos['email']))
	{
		displayDebugMsg("Error - Need a 'first_name' / 'last_name' / 'nickname' / 'birth_date' / 'email' argments");
		$arr_response[$keys->ERR_key] = $keys->ERR_Bad_Arguments;
		echo(json_encode($arr_response));
		return;
	}
	
	$res = getResultFromDataBase("INSERT INTO user (first_name, last_name, nickname, birth_date, email) VALUES ('".$userInfos['first_name']."', '".$userInfos['last_name']."', '".$userInfos['nickname']."', '".$userInfos['birth_date']."', '".$userInfos['email']."')", 'auth');
	
	if($res == 0){
		$arr_response[$keys->RES_key] = $keys->RES_Result_Yes;
		echo(json_encode($arr_response));
	} else if($res == -1){
		$arr_response[$keys->RES_key] = $keys->RES_Result_No;
		echo(json_encode($arr_response));
	}
}

function deleteUser($userInfos){
	$keys = new keys();

	if(!isset($userInfos['id']))
	{
		displayDebugMsg("Error - Need a 'id' argment");
		$arr_response[$keys->ERR_key] = $keys->ERR_Bad_Arguments;
		echo(json_encode($arr_response));
		return;
	}
	
	$jsonObj = getResultFromDataBase('SELECT * FROM user WHERE id = '.$userInfos['id'], 'auth');
	
	if($jsonObj == ""){
		$arr_response[$keys->ERR_key] = $keys->ERR_No_Result_Found;
		echo(json_encode($arr_response));
		return;
	}

	$res = getResultFromDataBase("DELETE FROM user WHERE id = ".$userInfos['id']."", 'auth');

	if($res == 0){
		$arr_response[$keys->RES_key] = $keys->RES_Result_Yes;
		echo(json_encode($arr_response));
	} else if($res == -1){
		$arr_response[$keys->RES_key] = $keys->RES_Result_No;
		echo(json_encode($arr_response));
	}
}

function modifyUser($userInfos){
	$keys = new keys();
	var_dump($userInfos);

	if(!isset($userInfos['id']))
	{
		displayDebugMsg("Error - Need a 'id' argment");
		$arr_response[$keys->ERR_key] = $keys->ERR_Bad_Arguments;
		echo(json_encode($arr_response));
		return;
	}

	$jsonObj = getResultFromDataBase('SELECT * FROM user WHERE id = '.$userInfos['id'], 'auth');

	if($jsonObj == ""){
		$arr_response[$keys->ERR_key] = $keys->ERR_No_Result_Found;
		echo(json_encode($arr_response));
		return;
	}
	
	$strChamp = "";
	$strValue = "";
	$fisrt = true;
	foreach ($userInfos as $key => $value){
		if(		strcmp($key, 'first_name') == 0
			||	strcmp($key, 'first_name') == 0
			||	strcmp($key, 'first_name') == 0
			|| 	strcmp($key, 'first_name') == 0
			||	strcmp($key, 'first_name') == 0)
		{
			if($fisrt == true){
				$strChamp .= $key.", ";
				$strValue .= "'".$value."'";
			} else {
				$strChamp .= ", ".$key;
				$strValue .= ", '".$value."'";
			}
		}
	}

	$res = getResultFromDataBase("", 'auth');

	if($res == 0){
		$arr_response[$keys->RES_key] = $keys->RES_Result_Yes;
		echo(json_encode($arr_response));
	} else if($res == -1){
		$arr_response[$keys->RES_key] = $keys->RES_Result_No;
		echo(json_encode($arr_response));
	}
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
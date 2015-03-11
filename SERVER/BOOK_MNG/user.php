<?php
include_once '../conectdb.php';
include_once '../debug.php';
include_once '../keys.php';

$name = explode('user.php', $_SERVER['REQUEST_URI']);
if(isset($name[1]) ){
	$array = explode('/', $name[1]);

	$name_function = "";
	if(isset($array[2]) ){
		$name_function = $array[2];
	}

	if( isset($array[1]) && $array[1] == "user"){
	switch ($name_function){
		case "get":
			get($_POST);
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

function get($connectInfos) {
	$keys = new keys();
	
	// Todo : Change the parameter to be optional (Get all User)
	
	// Verify informations about the request
	if(!isset($connectInfos['id'])){
		displayDebugMsg("Error - Need a 'user_id'");
		$arr_response[$keys->RES_key] = $keys->RES_Result_No;
		$arr_response[$keys->ERR_key] = $keys->ERR_Bad_Arguments;
		echo(json_encode($arr_response));
		return;
	}
	
	// Try to get the informations about user
	$jsonObj = getResultFromDataBase('SELECT * FROM user WHERE id='. $connectInfos['id'] .'');
	
	if($jsonObj == ""){
		$arr_response[$keys->RES_key] = $keys->RES_Result_No;
		$arr_response[$keys->ERR_key] = $keys->ERR_No_Result_Found;
	} else {
		$arr_response[$keys->RES_key] = $keys->RES_Result_Yes;
		$arr_response['users'] = $jsonObj;
	}
	
	echo(json_encode($arr_response));
}

function add($connectInfos){
	$keys = new keys();
		
	// Verify informations about the request
	if(		!isset($connectInfos['first_name'])
		||	!isset($connectInfos['last_name'])
		||	!isset($connectInfos['nickname'])
		|| 	!isset($connectInfos['birth_date'])
		||	!isset($connectInfos['email'])
		||	!isset($connectInfos['crypted_key']))
	{
		displayDebugMsg("Error - Need a 'first_name' / 'last_name' / 'nickname' / 'birth_date' / 'email' / 'crypted_key' arguments");
		$arr_response[$keys->ERR_key] = $keys->ERR_Bad_Arguments;
		echo(json_encode($arr_response));
		return;
	}
	
	// Try to insert user in the table user
	$res = getResultFromDataBase("INSERT INTO user (first_name, last_name, nickname, birth_date, email) VALUES ('".$connectInfos['first_name']."', '".$connectInfos['last_name']."', '".$connectInfos['nickname']."', '".$connectInfos['birth_date']."', '".$connectInfos['email']."')");

	$jsonObj = getResultFromDataBase('SELECT * FROM user ORDER BY id DESC LIMIT 1');
		
	// Try to insert user in the table account
	if(		isset($jsonObj[0])
		&&	strcmp($jsonObj[0]['first_name'], $connectInfos['first_name']) == 0 
		&& 	strcmp($jsonObj[0]['last_name'], $connectInfos['last_name']) == 0
		&&	strcmp($jsonObj[0]['nickname'], $connectInfos['nickname']) == 0
		&&	strcmp($jsonObj[0]['birth_date'], $connectInfos['birth_date']) == 0
		&&	strcmp($jsonObj[0]['email'], $connectInfos['email']) == 0){
		$res = getResultFromDataBase("INSERT INTO account (crypted_key, user_id) VALUES ('".$connectInfos['crypted_key']."', ".$jsonObj[0]['id'].")");
		
		$jsonObj = getResultFromDataBase('SELECT * FROM account WHERE user_id='. $jsonObj[0]['id'] .'');
		if($jsonObj != ""){
			$arr_response[$keys->RES_key] = $keys->RES_Result_Yes;
		} else {
			$arr_response[$keys->RES_key] = $keys->RES_Result_No;
			$arr_response[$keys->ERR_key] = $keys->ERR_Value_Cannot_Be_Modify;
		}
	} else {
		$arr_response[$keys->RES_key] = $keys->RES_Result_No;
		$arr_response[$keys->ERR_key] = $keys->ERR_Value_Cannot_Be_Modify;
	}
	
	echo(json_encode($arr_response));
}

function delete($connectInfos){
	$keys = new keys();

	// Verify informations about the request
	if(!isset($connectInfos['id']))
	{
		displayDebugMsg("Error - Need a 'id' argument");
		$arr_response[$keys->ERR_key] = $keys->ERR_Bad_Arguments;
		echo(json_encode($arr_response));
		return;
	}
	
	$jsonObj = getResultFromDataBase('SELECT * FROM user WHERE id = '.$connectInfos['id']);
	
	if($jsonObj == ""){
		$arr_response[$keys->ERR_key] = $keys->ERR_No_Result_Found;
		echo(json_encode($arr_response));
		return;
	}

	// Try to delete the user on the table user
	$res = getResultFromDataBase("DELETE FROM user WHERE id = ".$connectInfos['id']."");
	
	$jsonObj = getResultFromDataBase('SELECT * FROM user WHERE id='. $connectInfos['id'] .'');

	if($jsonObj == ""){
		// Try to delete the user on the table account
		$res = getResultFromDataBase("DELETE FROM account WHERE user_id = ".$connectInfos['id']."");
		
		$jsonObj = getResultFromDataBase('SELECT * FROM account WHERE user_id='. $connectInfos['id'] .'');
		
		if($jsonObj == ""){
			$arr_response[$keys->RES_key] = $keys->RES_Result_Yes;
		} else {
			$arr_response[$keys->RES_key] = $keys->RES_Result_No;
			$arr_response[$keys->ERR_key] = $keys->ERR_Value_Cannot_Be_Modify;
		}
	} else {
		$arr_response[$keys->RES_key] = $keys->RES_Result_No;
		$arr_response[$keys->ERR_key] = $keys->ERR_Value_Cannot_Be_Modify;
	}
	
	echo(json_encode($arr_response));
}

function modify($connectInfos){
	$keys = new keys();

	// Verify informations about the request
	if(	!isset($connectInfos['id']))
	{
		displayDebugMsg("Error - Need a 'id' argument");
		$arr_response[$keys->RES_key] = $keys->RES_Result_No;
		$arr_response[$keys->ERR_key] = $keys->ERR_Bad_Arguments;
		echo(json_encode($arr_response));
		return;
	}

	$jsonObj = getResultFromDataBase('SELECT * FROM user WHERE id='.$connectInfos['id']);
	
	if($jsonObj == ""){
		displayDebugMsg("Error - No user with this 'id' found in user");
		$arr_response[$keys->RES_key] = $keys->RES_Result_No;
		$arr_response[$keys->ERR_key] = $keys->ERR_No_Result_Found;
		echo(json_encode($arr_response));
		return;
	}
	
	// Try to build the UPDATE request arguments
	$strFinal = "";
	$isFirstElement = true;
	$needUpdateCryptedKey = false;
	foreach ($connectInfos as $key => $value){
		if(strcmp($key, 'first_name') == 0 || strcmp($key, 'last_name') == 0 || strcmp($key, 'nickname') == 0 || strcmp($key, 'birth_date') == 0 || strcmp($key, 'email') == 0){
			if(!$isFirstElement){
					$strFinal .= ',';
			}
			$strFinal .= $key . '=\'' . $value . '\'';
			$isFirstElement = false;
		}
		if(strcmp($key, 'crypted_key') == 0){
			$needUpdateCryptedKey = true;
		}
	}
	
	// Try to modify the value in user table and account table if needed
	if(strcmp($strFinal, "") != 0 || $needUpdateCryptedKey){
		// Try to update field in table user
		$res = 0;
		if(strcmp($strFinal, "") != 0){
			$res = getResultFromDataBase('UPDATE user SET '. $strFinal .' WHERE id='. $connectInfos['id']);
		}
		
		// Try to update field in table account
		$resCryptedKey = 0;
		if($needUpdateCryptedKey){
			$jsonObj = getResultFromDataBase('SELECT * FROM account WHERE user_id = '.$connectInfos['id']);
			
			if($jsonObj == ""){
				displayDebugMsg("Error - No user with this 'id' found in account");
				$arr_response[$keys->RES_key] = $keys->RES_Result_No;
				$arr_response[$keys->ERR_key] = $keys->ERR_No_Result_Found;
				echo(json_encode($arr_response));
				return;
			}

			$resCryptedKey = getResultFromDataBase('UPDATE account SET crypted_key=\''. $connectInfos['crypted_key'] .'\' WHERE user_id='. $connectInfos['id'] .'');
		}

		if($res == 0 && $resCryptedKey == 0){
			$arr_response[$keys->RES_key] = $keys->RES_Result_Yes;
		} else {
			$arr_response[$keys->RES_key] = $keys->RES_Result_No;
			$arr_response[$keys->ERR_key] = $keys->ERR_Value_Cannot_Be_Modify;
		}
	} else {
		displayDebugMsg("Error - No arguments to update");
		$arr_response[$keys->RES_key] = $keys->RES_Result_No;
		$arr_response[$keys->ERR_key] = $keys->ERR_Bad_Arguments;
	}

	echo(json_encode($arr_response));
}

?>
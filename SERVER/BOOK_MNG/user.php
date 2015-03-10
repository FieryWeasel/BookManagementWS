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
	
	if(!isset($connectInfos['id'])){
		displayDebugMsg("Error - Need a 'user_id'");
		$arr_response[$keys->RES_key] = $keys->RES_Result_No;
		$arr_response[$keys->ERR_key] = $keys->ERR_Bad_Arguments;
		echo(json_encode($arr_response));
		return;
	}
		
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

function add($userInfos){
	$keys = new keys();
		
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
	
	$res = getResultFromDataBase("INSERT INTO user (first_name, last_name, nickname, birth_date, email) VALUES ('".$userInfos['first_name']."', '".$userInfos['last_name']."', '".$userInfos['nickname']."', '".$userInfos['birth_date']."', '".$userInfos['email']."')");
	
	$jsonObj = getResultFromDataBase('SELECT * FROM user WHERE id='. $connectInfos['id'] .'');
	
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

function modify($userInfos){
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

?>
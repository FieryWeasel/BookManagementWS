<?php
include_once 'conectdb.php';
include_once '../debug.php';

$array = explode('/', $_SERVER['REQUEST_URI']);

$name_function = "";
if(isset($array[2]) ){
	$name_function = $array[2];
}

/*
$var = file_get_contents($_SERVER['URL']);
json_decode($var);
dump_var($var);
*/

$isFunctionLaunched = false;

if((isset($_GET) && needRest()) || !needRest(){
	switch ($name_function){
		case "listBooks":
			listBooks($array);
			$isFunctionLaunched = true;
			break;
		case "book":
			book($array);
			$isFunctionLaunched = true;
			break;
	}
}

if(!$isFunctionLaunched){
	?>No function to launch<br/><?php
}


function listBooks($array) {
	// Request into the BDD
	$jsonObj = getResultFromDataBase('SELECT * FROM book', 'books');

	print_r(json_encode($jsonObj));
}

/*
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
*/

function book($array) {
	$book_id = "";
	if(isset($array[3]) && $array[3] == 'isbn' && isset($array[4])){
		$book_id = $array[4];
	
		// Request into the BDD
		$jsonObj = getResultFromDataBase('SELECT * FROM book WHERE UPPER(isbn) LIKE UPPER("'.$book_id.'")', 'books');

		if($jsonObj == ""){
			// Display an error
			displayDebugMsg("No result for "+$book_id);
			print(json_encode($jsonObj));
		} else {
			displayDebugMsg("Result here for "+$book_id);
			print(json_encode($jsonObj));
		}
	} else {
		displayDebugMsg("Error - Header of function "+$array[2]+" not correct (/book/isbn/X-XXX-XXXXX-X)");
	}
}

function add($userInfos){
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
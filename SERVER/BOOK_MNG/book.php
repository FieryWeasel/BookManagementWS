<?php
include_once '../conectdb.php';
include_once '../debug.php';
include_once '../keys.php';

$name = explode('book.php', $_SERVER['REQUEST_URI']);
if(isset($name[1]) ){
	$array = explode('/', $name[1]);

	$name_function = "";
	if(isset($array[2]) ){
		$name_function = $array[2];
	}

	if( isset($array[1]) && $array[1] == "book"){
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
	
	// Todo : Change the parameter to be optional (Get all Books)
	
	// Verify informations about the request
	if(!isset($connectInfos['isbn']))
	{
		displayDebugMsg("Error - Need a 'isbn' argment");
		$arr_response[$keys->ERR_key] = $keys->ERR_Bad_Arguments;
		echo(json_encode($arr_response));
		return;
	}
	
	// Try to get the informations about book
	$jsonObj = getResultFromDataBase('SELECT * FROM book WHERE UPPER(isbn) LIKE UPPER("'.$connectInfos['isbn'].'")');

	if($jsonObj == ""){
		$arr_response[$keys->RES_key] = $keys->RES_Result_No;
	} else {
		$arr_response[$keys->RES_key] = $keys->RES_Result_Yes;
		$arr_response["book"] = $jsonObj[0];
	}
	
	echo(json_encode($arr_response));
}

function add($connectInfos){
	$keys = new keys();
	
	// Verify informations about the request
	if(		!isset($connectInfos['isbn'])
		||	!isset($connectInfos['title'])
		||	!isset($connectInfos['type_id'])
		|| 	!isset($connectInfos['author_id'])
		||	!isset($connectInfos['summary']))
	{
		displayDebugMsg("Error - Need a 'isbn' / 'title' / 'type_id' / 'author_id' / 'summary' argments");
		$arr_response[$keys->ERR_key] = $keys->ERR_Bad_Arguments;
		echo(json_encode($arr_response));
		return;
	}
	
	$jsonObj = getResultFromDataBase('SELECT * FROM book WHERE UPPER(isbn) LIKE UPPER("'.$connectInfos['isbn'].'")');
	
	if($jsonObj != ""){
		$arr_response[$keys->RES_key] = $keys->RES_Result_No;
		$arr_response[$keys->ERR_key] = $keys->ERR_Add_Key_Already_Exist;
		echo(json_encode($arr_response));
		return;
	}
	
	// Try to add a book on the table book
	$res = getResultFromDataBase('INSERT INTO book (isbn, title, type_id, author_id, summary) VALUES ("'.$connectInfos['isbn'].'", "'.$connectInfos['title'].'", "'.$connectInfos['type_id'].'", "'.$connectInfos['author_id'].'", "'.$connectInfos['summary'].'")');
	
	$jsonObj = getResultFromDataBase('SELECT * FROM book WHERE UPPER(isbn) LIKE UPPER("'.$connectInfos['isbn'].'")');
	
	if($jsonObj == ""){
		$arr_response[$keys->RES_key] = $keys->RES_Result_No;
		$arr_response[$keys->ERR_key] = $keys->ERR_Value_Cannot_Be_Modify;
	} else {
		$arr_response[$keys->RES_key] = $keys->RES_Result_Yes;
	}
	
	echo(json_encode($arr_response));
}

function delete($connectInfos){
	$keys = new keys();

	// Verify informations about the request
	if(!isset($connectInfos['isbn']))
	{
		displayDebugMsg("Error - Need a 'isbn' argment");
		$arr_response[$keys->RES_key] = $keys->RES_Result_No;
		$arr_response[$keys->ERR_key] = $keys->ERR_Bad_Arguments;
		echo(json_encode($arr_response));
		return;
	}
	
	$jsonObj = getResultFromDataBase('SELECT * FROM book WHERE UPPER(isbn) LIKE UPPER("'.$connectInfos['isbn'].'")');
	if($jsonObj == ""){
		$arr_response[$keys->RES_key] = $keys->RES_Result_No;
		$arr_response[$keys->ERR_key] = $keys->ERR_No_Result_Found;
		echo(json_encode($arr_response));
		return;
	}

	// Try to delete a book on the table book
	$res = getResultFromDataBase('DELETE FROM book WHERE UPPER(isbn) LIKE UPPER("'.$connectInfos['isbn'].'")');

	$jsonObj = getResultFromDataBase('SELECT * FROM book WHERE UPPER(isbn) LIKE UPPER("'.$connectInfos['isbn'].'")');
	if($jsonObj != ""){
		$arr_response[$keys->RES_key] = $keys->RES_Result_No;
		$arr_response[$keys->ERR_key] = $keys->ERR_Value_Cannot_Be_Modify;
	} else {
		$arr_response[$keys->RES_key] = $keys->RES_Result_Yes;
	}
	
	echo(json_encode($arr_response));
}

function modify($connectInfos){
	$keys = new keys();

	// Verify informations about the request
	if(	!isset($connectInfos['isbn']))
	{
		displayDebugMsg("Error - Need a 'isbn' argment");
		$arr_response[$keys->RES_key] = $keys->RES_Result_No;
		$arr_response[$keys->ERR_key] = $keys->ERR_Bad_Arguments;
		echo(json_encode($arr_response));
		return;
	}

	$jsonObj = getResultFromDataBase('SELECT * FROM book WHERE UPPER(isbn) LIKE UPPER("'.$connectInfos['isbn'].'")');

	if($jsonObj == ""){
		displayDebugMsg("Error - No book with this 'isbn' found");
		$arr_response[$keys->RES_key] = $keys->RES_Result_No;
		$arr_response[$keys->ERR_key] = $keys->ERR_No_Result_Found;
		echo(json_encode($arr_response));
		return;
	}
	
	// Try to build the UPDATE request arguments
	$strFinal = "";
	$isFirstElement = true;
	foreach ($connectInfos as $key => $value){
		if(strcmp($key, 'title') == 0 || strcmp($key, 'summary') == 0){
			if(!$isFirstElement){
					$strFinal .= ',';
			}
			$strFinal .= $key . '=\'' . $value . '\'';
			$isFirstElement = false;
		} else if(strcmp($key, 'type_id') == 0 || (strcmp($key, 'author_id') == 0)){
			if(!$isFirstElement){
					$strFinal .= ',';
			}
			$strFinal .= $key . '=' . $value . '';
			$isFirstElement = false;
		}
	}
	
	// Try to modify the value in user table and account table if needed
	if(strcmp($strFinal, "") != 0){
		$res = getResultFromDataBase('UPDATE book SET '. $strFinal .' WHERE UPPER(isbn) LIKE UPPER("'. $connectInfos['isbn'] .'")');

		if($res == 0){
			$arr_response[$keys->RES_key] = $keys->RES_Result_Yes;
		} else if($res == -1){
			$arr_response[$keys->RES_key] = $keys->RES_Result_No;
		}
	} else {
		displayDebugMsg("Error - No arguments to update");
		$arr_response[$keys->RES_key] = $keys->RES_Result_No;
		$arr_response[$keys->ERR_key] = $keys->ERR_No_Result_Found;
	}

	echo(json_encode($arr_response));
}

?>
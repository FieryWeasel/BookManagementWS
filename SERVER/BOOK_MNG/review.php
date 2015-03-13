<?php
include_once '../conectdb.php';
include_once '../debug.php';
include_once '../keys.php';

function custumFunction($name_function){
	switch ($name_function){
		case "delete":
			delete($_POST);
			break;
		default:
			?>No function to launch<br/><?php
			break;
	}
}

function get($connectInfos) {
	$keys = new keys();
	
	// Try to build the SELECT request arguments
	$strFinal = "";
	$isFirstElement = true;
	foreach ($connectInfos as $key => $value){
		if(strcmp($key, 'book_id') == 0 || strcmp($key, 'comment') == 0 || strcmp($key, 'title') == 0){
			if($isFirstElement){
					$strFinal .= 'WHERE ';
			}
			if(!$isFirstElement){
					$strFinal .= ' AND ';
			}
			$strFinal .= 'UPPER('.$key . ')=UPPER(\'' . $value . '\')';
			$isFirstElement = false;
		}
		if(strcmp($key, 'id') == 0 || strcmp($key, 'user_id') == 0 || strcmp($key, 'mark') == 0){
			if($isFirstElement){
					$strFinal .= 'WHERE ';
			}
			if(!$isFirstElement){
					$strFinal .= ' AND ';
			}
			$strFinal .= $key . '=' . $value . '';
			$isFirstElement = false;
		}
	}
		
	// Try to get the informations about book
	$jsonObj = getResultFromDataBase('SELECT * FROM review '. $strFinal);

	if($jsonObj == ""){
		$arr_response[$keys->RES_key] = $keys->RES_Result_No;
		$arr_response[$keys->ERR_key] = $keys->ERR_No_Result_Found;
	} else {
		$arr_response[$keys->RES_key] = $keys->RES_Result_Yes;
		$arr_response['reviews'] = $jsonObj;
	}
	
	echo(json_encode($arr_response));
}

function add($connectInfos){
	$keys = new keys();
	
	// Verify informations about the request
	if(		!isset($connectInfos['book_id'])
		||	!isset($connectInfos['user_id'])
		||	!isset($connectInfos['mark'])
		|| 	!isset($connectInfos['comment'])
		|| 	!isset($connectInfos['title']))
	{
		displayDebugMsg("Error - Need a 'book_id' / 'user_id' / 'mark' / 'comment' / 'title' argments");
		$arr_response[$keys->RES_key] = $keys->RES_Result_No;
		$arr_response[$keys->ERR_key] = $keys->ERR_Bad_Arguments;
		echo(json_encode($arr_response));
		return;
	}
	
	$jsonObj = getResultFromDataBase('SELECT * FROM book WHERE LOWER(isbn)=LOWER(\''. $connectInfos['book_id'] .'\')');
	
	if($jsonObj == ""){
		displayDebugMsg("Error - No book correpond to the 'book_id' argument");
		$arr_response[$keys->RES_key] = $keys->RES_Result_No;
		$arr_response[$keys->ERR_key] = $keys->ERR_No_Result_Found;
		echo(json_encode($arr_response));
		return;
	}
	
	$jsonObj = getResultFromDataBase('SELECT * FROM user WHERE id='. $connectInfos['user_id'] .'');
	
	if($jsonObj == ""){
		displayDebugMsg("Error - No book correpond to the 'user_id' argument");
		$arr_response[$keys->RES_key] = $keys->RES_Result_No;
		$arr_response[$keys->ERR_key] = $keys->ERR_No_Result_Found;
		echo(json_encode($arr_response));
		return;
	}
	
	// Try to add a book on the table book
	$res = getResultFromDataBase('INSERT INTO review (book_id, user_id, mark, comment, title) VALUES ("'. $connectInfos['book_id'] .'", '. $connectInfos['user_id'] .', '. $connectInfos['mark'] .', "'. $connectInfos['comment'] .'", "'. $connectInfos['title'] .'")');
	
	$jsonObj = getResultFromDataBase('SELECT * FROM review ORDER BY id DESC LIMIT 1');
	
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
	if(!isset($connectInfos['id']))
	{
		displayDebugMsg("Error - Need a 'id' argment");
		$arr_response[$keys->RES_key] = $keys->RES_Result_No;
		$arr_response[$keys->ERR_key] = $keys->ERR_Bad_Arguments;
		echo(json_encode($arr_response));
		return;
	}
	
	$jsonObj = getResultFromDataBase('SELECT * FROM review WHERE id='. $connectInfos['id']);
	if($jsonObj == ""){
		$arr_response[$keys->RES_key] = $keys->RES_Result_No;
		$arr_response[$keys->ERR_key] = $keys->ERR_No_Result_Found;
		echo(json_encode($arr_response));
		return;
	}

	// Try to delete a book on the table book
	$res = getResultFromDataBase('DELETE FROM review WHERE id='. $connectInfos['id']);

	$jsonObj = getResultFromDataBase('SELECT * FROM review WHERE id='. $connectInfos['id']);
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
	if(	!isset($connectInfos['id']))
	{
		displayDebugMsg("Error - Need a 'id' argment");
		$arr_response[$keys->RES_key] = $keys->RES_Result_No;
		$arr_response[$keys->ERR_key] = $keys->ERR_Bad_Arguments;
		echo(json_encode($arr_response));
		return;
	}

	$jsonObj = getResultFromDataBase('SELECT * FROM review WHERE id='. $connectInfos['id']);

	if($jsonObj == ""){
		displayDebugMsg("Error - No review with this 'id' found");
		$arr_response[$keys->RES_key] = $keys->RES_Result_No;
		$arr_response[$keys->ERR_key] = $keys->ERR_No_Result_Found;
		echo(json_encode($arr_response));
		return;
	}
	
	// Try to build the UPDATE request arguments
	$strFinal = "";
	$isFirstElement = true;
	foreach ($connectInfos as $key => $value){
		if(strcmp($key, 'book_id') == 0 || strcmp($key, 'comment') == 0 || strcmp($key, 'title') == 0){
			if(!$isFirstElement){
					$strFinal .= ',';
			}
			$strFinal .= $key . '=\'' . $value . '\'';
			$isFirstElement = false;
		} else if((strcmp($key, 'user_id') == 0 || strcmp($key, 'mark') == 0)){
			if(!$isFirstElement){
					$strFinal .= ',';
			}
			$strFinal .= $key . '=' . $value . '';
			$isFirstElement = false;
		}
	}
	
	// Try to modify the value in review table if needed
	if(strcmp($strFinal, "") != 0){
		$res = getResultFromDataBase('UPDATE review SET '. $strFinal .' WHERE id='. $connectInfos['id']);

		if($res == 0){
			$arr_response[$keys->RES_key] = $keys->RES_Result_Yes;
		} else if($res == -1){
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
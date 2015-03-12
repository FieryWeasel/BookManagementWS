<?php
include_once '../conectdb.php';
include_once '../debug.php';
include_once '../keys.php';

function get($connectInfos) {
	$keys = new keys();
	
	// Try to build the SELECT request arguments
	$strFinal = "";
	$isFirstElement = true;
	foreach ($connectInfos as $key => $value){
		if(strcmp($key, 'isbn') == 0 || strcmp($key, 'title') == 0 || strcmp($key, 'summary') == 0){
			if($isFirstElement){
					$strFinal .= 'WHERE ';
			}
			if(!$isFirstElement){
					$strFinal .= ' AND ';
			}
			$strFinal .= 'UPPER('.$key . ')=UPPER(\'' . $value . '\')';
			$isFirstElement = false;
		}
		if(strcmp($key, 'type_id') == 0 || strcmp($key, 'author_id') == 0){
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
	$jsonObj = getResultFromDataBase('SELECT * FROM book '. $strFinal);

	if($jsonObj == ""){
		$arr_response[$keys->RES_key] = $keys->RES_Result_No;
		$arr_response[$keys->ERR_key] = $keys->ERR_No_Result_Found;
	} else {
		$arr_response[$keys->RES_key] = $keys->RES_Result_Yes;
		$arr_response['books'] = $jsonObj;
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
		$arr_response[$keys->RES_key] = $keys->RES_Result_No;
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

	$jsonObj = getResultFromDataBase('SELECT * FROM book WHERE UPPER(isbn) = UPPER("'.$connectInfos['isbn'].'")');

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
		$res = getResultFromDataBase('UPDATE book SET '. $strFinal .' WHERE UPPER(isbn) = UPPER("'. $connectInfos['isbn'] .'")');

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

//getGoogleBook();

function getGoogleBook(){
	$ch = curl_init("https://www.googleapis.com/books/v1/volumes?q=isbn:1408855658&country=FR");
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
	curl_setopt($ch, CURLOPT_HEADER, 0);
	$data = curl_exec($ch);
	curl_close($ch);
	echo(json_encode($data));
}

?>
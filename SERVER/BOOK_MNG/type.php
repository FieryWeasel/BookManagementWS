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
		if(strcmp($key, 'name') == 0 ){
			if($isFirstElement){
					$strFinal .= 'WHERE ';
			}
			if(!$isFirstElement){
					$strFinal .= ' AND ';
			}
			$strFinal .= 'UPPER('.$key . ')=UPPER(\'' . $value . '\')';
			$isFirstElement = false;
		}
		if(strcmp($key, 'id') == 0 ){
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
	$jsonObj = getResultFromDataBase('SELECT * FROM type '. $strFinal);

	if($jsonObj == ""){
		$arr_response[$keys->RES_key] = $keys->RES_Result_No;
		$arr_response[$keys->ERR_key] = $keys->ERR_No_Result_Found;
	} else {
		$arr_response[$keys->RES_key] = $keys->RES_Result_Yes;
		$arr_response['types'] = $jsonObj;
	}
	
	echo(json_encode($arr_response));
}

function add($connectInfos){
	$keys = new keys();
	
	// Verify informations about the request
	if(		!isset($connectInfos['name']))
	{
		displayDebugMsg("Error - Need a 'name' argment");
		$arr_response[$keys->RES_key] = $keys->RES_Result_No;
		$arr_response[$keys->ERR_key] = $keys->ERR_Bad_Arguments;
		echo(json_encode($arr_response));
		return;
	}
	
	// Try to insert user in the table user
	$res = getResultFromDataBase("INSERT INTO type (name) VALUES ('". $connectInfos['name'] ."')");

	$jsonObj = getResultFromDataBase('SELECT * FROM type ORDER BY id DESC LIMIT 1');
		
	// Try to insert user in the table account
	if(		isset($jsonObj[0])
		&&	strcmp($jsonObj[0]['name'], $connectInfos['name']) == 0 ){
		$arr_response[$keys->RES_key] = $keys->RES_Result_Yes;
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
		displayDebugMsg("Error - Need a 'id' argment");
		$arr_response[$keys->RES_key] = $keys->RES_Result_No;
		$arr_response[$keys->ERR_key] = $keys->ERR_Bad_Arguments;
		echo(json_encode($arr_response));
		return;
	}

	$jsonObj = getResultFromDataBase('SELECT * FROM type WHERE id = '. $connectInfos['id']);

	if($jsonObj == ""){
		displayDebugMsg("Error - No type with this 'id' found");
		$arr_response[$keys->RES_key] = $keys->RES_Result_No;
		$arr_response[$keys->ERR_key] = $keys->ERR_No_Result_Found;
		echo(json_encode($arr_response));
		return;
	}
	
	// Try to build the UPDATE request arguments
	$strFinal = "";
	$isFirstElement = true;
	foreach ($connectInfos as $key => $value){
		if(strcmp($key, 'name') == 0 ){
			if(!$isFirstElement){
					$strFinal .= ',';
			}
			$strFinal .= $key . '=\'' . $value . '\'';
			$isFirstElement = false;
		}
	}
	
	// Try to modify the value in type table if needed
	if(strcmp($strFinal, "") != 0){
		$res = getResultFromDataBase('UPDATE type SET '. $strFinal .' WHERE id = '. $connectInfos['id']);

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
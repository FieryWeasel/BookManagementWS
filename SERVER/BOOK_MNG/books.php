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

if((isset($_GET) && needRest() == true) || needRest() == false){
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
?>
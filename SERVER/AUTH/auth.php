<?php
include_once 'conectdb.php';

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

switch ($name_function){
	case "isExist":
		isExist($array);
		break;
	default:
		?>No function to launch<br/><?php
		break;
}

function isExist($array) {
	$user_name = "No_name";
	if(isset($array[3]) && $array[3] == 'user' && isset($array[4])){
		$user_name = $array[4];
		// Request into the BDD
		
		$jsonObj = getResultFromDataBase('SELECT * FROM user WHERE UPPER(nickname) LIKE UPPER("'.$user_name.'")');
		
		if($jsonObj == null){
			// Display an error
		} else {
			print(json_encode($jsonObj));
		}
		
		?>Result here for <?php echo ($user_name);
	} else {
		?>Error - Header of function <?php echo ($array[2]) ?> not correct (/isExist/user/XXXX)<?php
	}
}

/**
 * Encrypting password
 * @param password
 * returns salt and encrypted password
 */
function hashSSHA($password) {

	$salt = sha1(rand());
	$salt = substr($salt, 0, 10);
	$encrypted = base64_encode(sha1($password . $salt, true) . $salt);
	$hash = array("salt" => $salt, "encrypted" => $encrypted);
	return $hash;
}

/**
 * Decrypting password
 * @param salt, password
 * returns hash string
 */
function checkhashSSHA($salt, $password) {

	$hash = base64_encode(sha1($password . $salt, true) . $salt);

	return $hash;
}

?>
<?php
function connect(){
	$hostname = "mysql.hostinger.fr";
	$username = "u446795347_root";
	$password = "rootlp";
	$db_name = "u446795347_wsbm";
	
	$db = null;
	
	try {
		$db = new PDO('mysql:host='.$hostname.';dbname='.$db_name, $username, $password);
	} catch (PDOException $e) {
		echo 'Connexion échouée : ' . $e->getMessage();
	}
	
	return $db;
}


function getResultFromDataBase($sql_request){

	$db = connect();
	
	$sth = $db->prepare($sql_request);
	$sth->execute();
	
	$output = "";
	
	$result = $sth->fetchAll(PDO::FETCH_ASSOC);
	foreach ( $result as $row){
		$output[] = $row;
	}

	return $output;
}
?>
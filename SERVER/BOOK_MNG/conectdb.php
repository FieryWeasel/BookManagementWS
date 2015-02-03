<?php

$conf = parse_ini_file("../config.ini", true);

function connect($dbname){
	
	$db_name = "books_mng";
	
	$db = null;
	
	try {
		$db = new PDO($conf['local']['type'].':host='.$conf['local']['host'].';dbname='.$conf['local'][$dbname], $conf['local']['root'], $conf['local']['mdp']);
	} catch (PDOException $e) {
		echo 'Connexion échouée : ' . $e->getMessage();
	}
	
	return $db;
}


function getResultFromDataBase($sql_request, $dbname){

	$db = connect($dbname);
	
	$sth = $db->prepare($sql_request);
	$sth->execute();
	
	$output = "";
	
	$result = $sth->fetchAll(PDO::FETCH_ASSOC);
	foreach ( $result as $row){
		$output[] = $row;
	}

	return $output;
}

function inertIntoDataBase($sql_request, $dbname){

	$db = connect($dbname);

	try {
		$conn->exec($sql_request);
		return 0;
	} catch(PDOException $e) {
		return -1;
	}
}
?>
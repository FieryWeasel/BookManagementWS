<?php
include_once '../conectdb.php';
include_once '../debug.php';
include_once '../keys.php';

$name = explode('mng.php', $_SERVER['REQUEST_URI']);
if( isset($name[1]) ){
	$array = explode('/', $name[1]);

	$name_function = "";
	if( isset($array[2]) ){
		$name_function = $array[2];
	}
	
	if( isset($array[1]) ){
	switch ($array[1]){
		case "user":
			include_once 'user.php';
			break;
		case "book":
			include_once 'book.php';
			break;
		case "review":
			include_once 'review.php';
			break;
		case "type":
			include_once 'type.php';
			break;
		case "author":
			include_once 'author.php';
			break;
		default:
			?>No directory to include<br/><?php
			break;
		}
		
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
			if( $array[1] != "type" && $array[1] != "author")
				delete($_POST);
			break;
		default:
			?>No function to launch<br/><?php
			break;
		}
		
	}

	
}
?>
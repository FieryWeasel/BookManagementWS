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
	
	$directoryFind = false;
	if( isset($array[1]) ){
		switch ($array[1]){
			case "user":
				include_once 'user.php';
				$directoryFind = true;
				break;
			case "book":
				include_once 'book.php';
				$directoryFind = true;
				break;
			case "review":
				include_once 'review.php';
				$directoryFind = true;
				break;
			case "type":
				include_once 'type.php';
				$directoryFind = true;
				break;
			case "author":
				include_once 'author.php';
				$directoryFind = true;
				break;
			default:
				?>No directory to include<br/><?php
				break;
			}
		
		if($directoryFind){
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
				default:
					custumFunction($name_function);
					break;
			}
		}
	}
}
?>
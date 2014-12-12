<?php

function needRest(){
	return false;
}

function needDebugMsg(){
	return false;
}

function displayDebugMsg($msg){
	if(needDebugMsg()){
		echo($msg);
	}
}
?>
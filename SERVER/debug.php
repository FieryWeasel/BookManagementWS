<?php
function needDebugMsg(){
	return false;
}

function displayDebugMsg($msg){
	if(needDebugMsg()){
		echo($msg);
	}
}
?>
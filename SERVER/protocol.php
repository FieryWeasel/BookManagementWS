AUTH :
[user] contain all infos of one user
Data :
[id]

Request : 
AUTH/auth.php/user/isExist : Verify if user exist
Params required :
[id] id of the user
Response :
[response] [NO|YES]
if NO [error] the code of the error 

Request : 
AUTH/auth.php/user/canConnect : Verify if user can connect
Params required :
[id] id of the user
[crypted_key] key for the connection of the user
Response :
[response] [NO|YES]

Request : 
AUTH/auth.php/user/add : Add user's data if exist
Params required :
[id] id of the user
[crypted_key] key for the connection of the user
Response :
[response] [NO|YES] NO if the user ID is already used
if NO [error] the code of the error 

Request : 
AUTH/auth.php/user/modify : Modify user's data if exist
Params required :
[id] id of the user
[crypted_key] new key for the connection of the user
Response :
[response] [NO|YES]
if NO [error] the code of the error 

Request : 
AUTH/auth.php/user/delete : Delete user's data if exist
Params required :
[id] id of the user
Response :
[response] [NO|YES]
if NO [error] the code of the error 

BOOK_MNG :
[user] contain all infos of one user
Data :
[id]
[first_name]
[last_name]
[nickname]
[birth_date]
[email]

Request : 
BOOK_MNG/books.php/user/get : Get user's data if exist
Params optional :
[nickname] nickname of the user
[id] id of the user
Response :
[response] number of user found
[users_found] array of [user]
TP-link

general files structure:
	- db_script: table structure & trigger function
	- server: rest api based on spark
	- web: web app based on angular js
	
Config:
web/phone_book/app/common/controller/run.js: set ip & server port number to access rest api if server side is different from web app;otherwise, localhost would be fine
server/phonebook/src/main/java/phonebook/app.java: customize your server CROS if concerned using static function 'enableCORS'
server/phonebook/src/main/java/phonebook/TPDB/TPDBAccess.java: set db connection config if concerned and different from default ('localhost', 'tplink', 'tplink' , '123456')
server/phonebook/lib: please add gson & jdbc jar into project
db_script: please run tables.sql then db_triggers.sql; if table User not inserted correctly, please based on table Group to manually set table User _groupId

db structure:
table group to table user is 1 to n relationship
table not do real delete; based on each table attribute _status to instruct data is available or not; this design is for purpose, future data backup / restore from admin
because there is no real delete transaction, create trigger in stead of db table cascade delete

server structure: (take an example of User role, Group role is similar way)
TPDB:
	TPDBAccess.java: db connection config, initial & close
	User.java: data model (invisible outside package)
	UserSimple.java: convertion between object and json string (invisible outside package)
	UserDao.java: DAO function / transaction (invisible outside package)
	UserFunciton.java: based on outside function request to provide DAO related data transaction(select, update, 'delete', insert) -- (visiable to outside package)
App.js
	all rest api public function is here
	beware of server config for CROS using function enableCORS (can be customized based on requirement)
	port should be 4567
	
web app structure: (take an example of contact-based file, then similar to group)
	public: for over all project
	app
		common: web app config and global var & function 
		app:
			controller: function controller based on different model
			model: function model for different view usage 
			view: ui part

Known issues fixed:
1. (Fix) when all entites empty, start over, update contact information which created before, phone number existing validation will be triggered incorrectly, something related to var deep copy or id not updated from UI
2. (Fix) not too much data input validation
3. (Fix) after modal close, not clean cache / history data

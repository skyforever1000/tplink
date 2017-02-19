"use strict";
app.factory('Contact', function($http, $rootScope){
    var Obj = {};

    Obj.getUserList = function(){
    	return $http({
            method: 'GET',
            url: $rootScope.ServerDomain + 'GetUserList',
            responseText: 'JSON'
        });
    }
	
	Obj.httpUpdate = function(user, status){
		var remoteUser = {};
		remoteUser._userId = user.id;
		remoteUser._firstName = user.firstName;
		remoteUser._lastName = user.lastName;
		remoteUser._address = user.address;
		remoteUser._phoneNo = user.phone;
		remoteUser._status = status;
		remoteUser._groupId = user.groupId;
		
		return $http({
            method: 'POST',
            url: $rootScope.ServerDomain + 'updateUser',
			data: remoteUser,
            responseText: 'JSON'
        });
	}
	
	Obj.httpAddUser = function(user, groupId){
		var remoteUser = {};
		remoteUser._firstName = user.firstName;
		remoteUser._lastName = user.lastName;
		remoteUser._address = user.address;
		remoteUser._phoneNo = user.phone;
		remoteUser._groupId = groupId;
		
		return $http({
            method: 'POST',
            url: $rootScope.ServerDomain + 'addUser',
			data: remoteUser,
            responseText: 'JSON'
        });
	}
	
	Obj.isUserExist = function(new_user){
		for(var i = 0; i< contacts.length ; i++){
			if(contacts[i].phone == new_user.phone){
				return true;
			}
		}
		return false;
	}

    var contacts =[];
    var page = 1;
	
	Obj.cleanContacts = function(){
		contacts =[];
	}
	
    Obj.get = function(id) {
        for(var i = 0; i < contacts.length; i++) {
            if (contacts[i].id == id) {
                return contacts[i];
            }
        }
        return {};
    }
    Obj.getAll = function(){
        return contacts;
    }
    Obj.getPage = function() {
        return page;
    }
 
    Obj.search = function(keyword) {
        let tempList = [];
        contacts.forEach(function(contact){
            if (contact.id.toString().toLowerCase().indexOf(keyword.toLowerCase()) >= 0 
                || contact.firstName.toLowerCase().indexOf(keyword.toLowerCase()) >= 0
                || contact.lastName.toLowerCase().indexOf(keyword.toLowerCase()) >= 0) {
                tempList.push(contact);
            }
        });
        return tempList;
    }
    
    Obj.add = function(obj, scope) {
        Obj.httpAddUser(obj, scope.tempGrupId).then(function(res){
        	if(res.status == 200){
        		obj.id = res.data[0];
				obj.groupId = scope.tempGrupId;
        		contacts.push(obj);
        		scope.tempUserObj = obj;
        	}
        });       
    }
	
    Obj.remove = function(id){
        var i = contacts.length - 1;
		var groupId = -1;
		var user = {};
        console.log(contacts[i].id, id);
        while(i >= 0) {
            if (contacts[i].id == id) {
			   groupId = contacts[i].groupId;
			   user = contacts[i];
               contacts.splice(i, 1);
			   break;
            }
            i--;
        }
		
		Obj.httpUpdate(user, 0).then(function(response){
			if(response.data.status = 200){					
				console.log("john groupId", groupId);
				return groupId;
			}
		});
		return groupId;
    }
	
	Obj.removeByGroup = function(groupId){
		for(var i = contacts.length -1; i >= 0; i--){
			if(contacts[i].groupId == groupId){
				contacts.splice(i, 1);
			}
		}
	}
	
    Obj.update = function(obj,scope){
		var old_user = {};
		var condition = false;
		
		for(var i =0; i<contacts.length; i++){
			if (contacts[i].id == obj.id) {
				old_user = contacts[i];
				if(old_user.phone == obj.phone)
					condition = true;
				else if(!Obj.isUserExist(obj))
					condition = true;
				else
					condition = false;
				
				if(condition){
					Obj.httpUpdate(obj, 1).then(function(response){
						if(response.status = 200){
							contacts[i] = angular.copy(obj);	
							scope.contact = obj;
						}
					});
				}else{
					scope.contact = old_user;
				}
				return condition;
            }
		}
    }
    
    Obj.pushUsers = function(user){    
    	var contact = {};
    	contact.id = user._userId;
    	contact.firstName = user._firstName;
    	contact.lastName = user._lastName;
    	contact.address = user._address;
    	contact.phone = user._phoneNo;
		contact.groupId = user._groupId;
    	contacts.push(contact);
    }
    
    return Obj;
});
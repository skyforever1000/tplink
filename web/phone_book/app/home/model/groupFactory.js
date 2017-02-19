"use strict";
app.factory('Group', function($http, $rootScope, Contact){
    var Obj = {};
	var groups = [];
	
	Obj.getGroupList = function(){
    	return $http({
            method: 'GET',
            url: $rootScope.ServerDomain + 'GetUserList',
            responseText: 'JSON'
        });
    }
	
	Obj.httpUpdate = function(group, status){
		var remoteGroup = {};
		remoteGroup._groupId = group.id;
		remoteGroup._groupName = group.title;
		remoteGroup._status = status;
		
		return $http({
            method: 'POST',
            url: $rootScope.ServerDomain + 'updateGroup',
			data: remoteGroup,
            responseText: 'JSON'
        });
	}
	
	Obj.httpAddGroup = function(group){
		var remoteGroup = {};
		remoteGroup._groupName = group.title;
		
		return $http({
            method: 'POST',
            url: $rootScope.ServerDomain + 'addGroup',
			data: remoteGroup,
            responseText: 'JSON'
        });
	}
    
	Obj.loadInfo = function (){
		Contact.cleanContacts();
		groups =[];
		Obj.getGroupList().then(function(response){
			for(var i = 0; i< response.data.length; i++){
				var group = {};
				group.id = response.data[i]._groupId;
				group.title = response.data[i]._groupName;
				group.users = [];
				group.contactList = [];
				group.users = response.data[i].users;
				for(var j = 0; j < group.users.length; j++){
					if(typeof group.users == "undefined" || group.users == null)
						break;
					if(typeof group.users[j] == "undefined" || group.users[j] == null)
						continue;
	 
					Contact.pushUsers(group.users[j]);
					group.contactList.push(group.users[j]._userId);
				}
				groups.push(group);
			}
			console.log("john groups:", groups);
		});
	}
	
	Obj.loadInfo();
	
	Obj.isGroupExist = function(new_group){
		for(var i = 0; i< groups.length ; i++){
			if(groups[i].title == new_group.title){
				return true;
			}
		}
		return false;
	}
	
	Obj.getGroupByGroupId = function(groupId){
		for(var i = 0; i< groups.length; i++){
			if(groups[i].id = groupId)
				return groups[i];
		}
	}
    
    Obj.insertUser = function(user){
    	for(var i = 0; i < groups.length; i++){
    		if(groups[i].id == user.groupId){
				if(typeof groups[i].contactList == "undefined"){
					groups[i].contactList = [];
					groups[i].users = [];
				}
				
				var index =groups[i].contactList.length;
    			groups[i].contactList[index] = user.id;
				groups[i].users[index] = user;
    			return groups[i];
    		}
    	}
    }
        
    Obj.get = function(id) {
        groups.forEach(function(obj){
            return obj;
        });
    }
    
    Obj.getAll = function(){
        return groups;
    }
    Obj.search = function(keyword) {
        let tempList = [];
        groups.forEach(function(item){
            if (item.id.toString().toLowerCase().indexOf(keyword.toLowerCase()) >= 0 
                || item.title.toLowerCase().indexOf(keyword.toLowerCase()) >= 0) {
                tempList.push(item);
            }
        });
        return tempList;
    }
	
    Obj.add = function(obj, scope) {
    
    	Obj.httpAddGroup(obj).then(function(res){
    		if(res.status == 200){
    			obj.id = res.data[0];
    			groups.push(obj);
    			scope.groups = groups;
    		}
    	});
    	
    }
	
    Obj.remove = function(id){
        var i = groups.length - 1;
		var group = {};
        while(i >= 0) {
            if (groups[i].id == id) {
				group = groups[i];
				groups.splice(i, 1);
				break;
            }
            i--;
        }
		
		Obj.httpUpdate(group, 0).then(function(response){
			if(response.data.status == 200){
				
			}
			
		});
    }
	
	Obj.reomveUserFromGroup =  function(groupId, userId){
		for(var i = 0; i< groups.length; i++){
			if(groups[i].id == groupId){
				var index = groups[i].contactList.indexOf(userId); 
				if(index > -1){
					groups[i].contactList.splice(index, 1);
					return groups[i];
				}
			}
		}
	}
	
    Obj.removeByField = function(fieldName, groupId, itemId) {
		var tempGroup ={};
        switch(fieldName) {
            case 'contact':
                groups.forEach(function(group, index){
                    if (group.id == groupId) {
                        group.contactList.forEach(function(id, idIndex){
                            if (id == itemId) {
                                groups[index].contactList.splice(idIndex, 1);
								tempGroup = groups[index];
								Contact.remove(id);
								return tempGroup;
                            }
                        });
                    }
                });
                break;
            default: 
                console.error('Group removeByField error: ', fieldName, groupId, itemId);
        }
		return tempGroup;
    }
	
    Obj.update = function(obj,scope){
		for(var i = 0; i < groups.length; i++){
			var old_group = {};
			var condition = false;
			
            if (groups[i].id == obj.id) {
				old_group = groups[i];
				if(!Obj.isGroupExist(obj)){
					condition = true;
				}else{
					condition = false;
				}
				
				if(condition){
					Obj.httpUpdate(obj, 1).then(function(response){
						if(response.status == 200){
							groups[i] = obj;
							scope.group = obj;
							scope.selectedItem = obj;
						}
					});
				}else{
					scope.group = old_group;
					scope.selectedItem = old_group;
				}
				return condition;
            }
		}
    }
    return Obj;
});
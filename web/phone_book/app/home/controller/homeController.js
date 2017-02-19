app.controller('homeController', function($scope, $rootScope, Contact, Group, CommonFunction){
    $scope.contacts = Contact.getAll() || [];
    $scope.groups = Group.getAll() || [];
    $scope.selectedItem = null;
    $scope.keywords = '';
    $scope.tempGrupId  = -1;
    $scope.tempUserObj = null;
    $scope.groupInsertMode = false;
	$scope.contactInsertMode = false;

    $scope.filterResult = search;
    $scope.select = select;
    $scope.remove = remove;
    $scope.update = update;
    $scope.add = add;
    $scope.modal = modal;
    $scope.removeFromGroup = removeFromGroup;
    
    $scope.tempContactId = -1;
    $scope.tempGroupId = -1;

    function removeFromGroup(groupId, contactId){
		//$scope.$apply(function(){
			$scope.selectedItem = Group.removeByField('contact', groupId, contactId);
		//});
		clean();
    }
    function modal(id, modelName) {
        switch(modelName) {
            case 'Contact':
                $scope.contact = angular.copy(id);
                break;
            case 'Group':
                $scope.group = angular.copy($scope.selectedItem);
                break;
            default:
                console.error('please enter correct modal name');
        }
    }
	
    function update(id_1, modelName) {
    	var id = angular.copy(id_1);
        switch(modelName) {
            case 'Contact':
            	if(!userInfoCheck(id))
					return;
            	id.id = $scope.contact.id;
            	id.groupId = $scope.contact.groupId;
				if(!Contact.update(id, $scope))
					alert("Phone Already Exists!");
				
				//$scope.selectedItem =Group.getGroupByGroupId($scope.contact.groupId);
                clean();
                break;
            case 'Group':
            	if(!groupInfoCheck(id))
					return;
            	id.id = $scope.group.id;
                if(!Group.update(id, $scope))
					alert("Group Name has been occupied!");
                clean();
                break;
            default:
                console.error('please enter correct modal name');
        }
    }

    function add(obj_1, modelName) {
		var obj = angular.copy(obj_1);
        switch(modelName) {
            case 'Contact':
				if(!userInfoCheck(obj))
					return;
            	if(Contact.isUserExist(obj)){
            		alert("User Number Already Exists!");
            	}else{
                	Contact.add(obj, $scope);
                	setTimeout(function(){
						$scope.$apply(function(){
							$scope.selectedItem = Group.insertUser($scope.tempUserObj);
						});
                	}, 1000);
                }
				clean();
                break;
            case 'Group':
				if(!groupInfoCheck(obj))
					return;
            	if(Group.isGroupExist(obj)){
            		alert("there already has this group name!");
            	}else{
					Group.add(obj, $scope);
				}
				clean();
                break;
            default:
                console.error('please enter correct modal name');
        }
    }
    
    $scope.addUser = function(id, modelName){
    	$scope.tempGrupId  = id;
    	$scope.contactInsertMode = true;
    	$scope.contact;	
    	cleanModalData("contactModal");       
    }
	
	$scope.updateUser = function(obj, modelName){
		console.log("john update user status: ");
		$scope.contactInsertMode = false;
		initialField(obj, modelName);
		switch(modelName){
			case 'Contact':
				$scope.tempContactId = obj.id;
				break;
			case 'Group':
				$scope.tempGroupId = obj.id;
				break;
			default:
				;
		}
	}
    
    $scope.groupInsertModeChange = function(status, obj){
    	console.log("john status", status);
    	$scope.groupInsertMode = status;
    	if(status)
    		cleanModalData("groupModal");
    	else
    		initialField(obj, "Group");
    }
	
	function cleanModalData(modalId){
		  $("#" + modalId).find("input,textarea,select").val('').end();
	}

    function remove(id, modelName) {
		
        switch(modelName) {
            case 'Contact':
                var groupId = Contact.remove(id);
				$scope.selectedItem = Group.reomveUserFromGroup(groupId, id);
				clean(id);
                break;
            case 'Group':
            	Contact.removeByGroup(id);
                Group.remove(id);
                $scope.selectedItem = null;
				clean(id);
                break;
            default:
                console.error('please enter correct modal name');
        }
    }
    
    function initialField(obj, modalName){
    	switch(modalName){
    		case 'Contact':
    			$('#firstName').val(angular.copy($scope.contact.firstName));
    			$('#lastName').val(angular.copy($scope.contact.lastName));
    			$('#address').val(angular.copy($scope.contact.address));
    			$('#phone').val(angular.copy($scope.contact.phone));
    			break;
    		case 'Group':
    			$('#title').val(angular.copy($scope.selectedItem.title));
    			break;
    		default:
    			;
    	}
    }
	
    function clean(id) {
    	setTimeout(function(){
    		$scope.$apply(function(){
				$scope.contacts = Contact.getAll() || [];
				$scope.groups = Group.getAll() || [];
			});

			if ((typeof id != 'undefined' || id != null) && ($scope.selectedItem == null || $scope.selectedItem.id == id)) {
				$scope.selectedItem = null;
			}
			if (typeof $scope.selectedItem != "undefined" && $scope.selectedItem != null
				&& typeof $scope.selectedItem.contactList != 'undefined' && $scope.selectedItem.contactList != null) {
				for (var i = 0; i < $scope.groups.length; i++) {
					if ($scope.groups[i].id == $scope.selectedItem.id) {
						$scope.selectedItem = angular.copy($scope.groups[i]);
						console.log('$scope.selectedItem', $scope.selectedItem);
					}
				}
			}    	
    	}, 1000);
    }
	
	function groupInfoCheck(group){
		if(group == null || typeof group.title == "undefined" || group.title == null || group.title ==""){
			alert("Group Name is required");
			return false;
		}
		return true;
	}
	
	function userInfoCheck(contact){
		var condition = true;
		var phoneno = /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/;
		if(contact == null ||
			typeof contact.firstName == "undefined" || contact.firstName == null ||
		   typeof contact.lastName == "undefined" || contact.lastName ==null ||
			typeof contact.phone == "undefined" || contact.phone == null){
				alert("first, last, and phone is required");
				condition = false;
			}else if(!contact.phone.match(phoneno)){
				alert("phone number format is incorrect");
				condition = false;
			}
			
			return condition;
	}
	
	
	function buildContactObj(){
		var obj = {};
		obj.firstName = $('#firstName').val();
		obj.lastName = $('#lastName').val();
		obj.address = $('#address').val();
		obj.phone = $('#phone').val();
		return obj;
	}
	
	function buildGroupObj(){
		var obj = {};
		obj.title = $('#title').val();
		return obj;
	}
	
    function search(keyword) {
        console.log('function search', keyword);
        if (typeof keyword != 'undefined' && keyword != '') {
            $scope.contacts = Contact.search(keyword);
            $scope.groups = Group.search(keyword);
        } else {
            $scope.contacts = Contact.getAll();
            $scope.groups = Group.getAll();
        }
    }

    function select(obj, modelName){
        $scope.selectedItem = obj;
        switch(modelName){
            case 'contact':
                $scope.contact = angular.copy(obj);
                break;
            case 'Group':
            	$scope.group = angular.copy(obj);
            	break;
            default:
        }
    }
});
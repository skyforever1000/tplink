"use strict";
app.factory('CommonFunction', function(){
    var Obj = {};
	
	Obj.generateMsgBox = function(msg, desc){
		$("<div>" + msg + "</div>").dialog({
			modal: true,
			title: desc,
			buttons: [{
				class: 'btn btn-sm btn-success width-100',
				text: translations.OK,
				click: function () {
					$(this).dialog('close');
					$(this).dialog('destroy').remove();
				}
			}]
		});
	}
	
	return Obj;
});
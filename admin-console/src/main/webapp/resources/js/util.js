function checkAll() {
	var all=document.getElementsByName('all');
	if (all.length > 0) {
		all = all[0];
	}
	var checklist=document.getElementsByName("item");
	for (var j=0; j<checklist.length; j++) {
		if (all.checked) {
			checklist[j].checked=true;
		} else {
			checklist[j].checked=false;
		}
	}
}

function onsubmitform(param) {
	var cForm = document.getElementById('form-list');
	if (cForm == null || cForm== undefined) {
		cForm = document.getElementById('form-item');
	}
	if(param == 'New' || document.pressed == 'New') {
		cForm.method = "get";
		cForm.action = "new";
		cForm.submit();
		
	} else if(param == 'Filter' || document.pressed == 'Filter') {
		var filter=document.getElementById("filter");
		if (filter != null && filter.value!="") {
			cForm.method = "get";
			cForm.action = "search";
			cForm.submit();
	    }
	} else if(param == 'Delete' || param == 'DeleteProp' || document.pressed == 'Delete' || document.pressed == 'DeleteProp') {
		var checklist=document.getElementsByName("item");
		var selectedItems = false;
		for (var j=0; j<checklist.length; j++) {
			if (checklist[j].checked==true) {
				selectedItems = true;
				break;		
			} 
		}
		if (selectedItems==true && (document.pressed == 'Delete' || param == 'Delete' )) {
			cForm.method = "post";
			cForm.action = "delete";
			cForm.submit();
		} else if (selectedItems==true && (document.pressed == 'DeleteProp' || param == 'DeleteProp')) {
			cForm.method = "post";
			cForm.action = "deleteprop";
			cForm.submit();
		} 
	} else if(param == 'Logout' || document.pressed == 'Logout') {
		cForm.method = "get";
		cForm.action = "logout";
		cForm.submit();
	} else if(param == 'Previous' || document.pressed == 'Previous') {
		cForm.method = "get";
		cForm.action = "prev";
		cForm.submit();
	} else if(param == 'Next' || document.pressed == 'Next') {
		cForm.method = "get";
		cForm.action = "next";
		cForm.submit();
	} else if(param == 'Create' || document.pressed == 'Create') {
		cForm.method = "post";
		cForm.action = "new";
		cForm.submit();
	} else if(param == 'CreateProp' || document.pressed == 'CreateProp') {
		cForm.method = "post";
		cForm.action = "newprop";
		cForm.submit();
	} else if(param == 'NewProp' || document.pressed == 'NewProp') {
		cForm.method = "get";
		cForm.action = "newprop";
		cForm.submit();
		
	} else if(param == 'CancelProp' || document.pressed == 'CancelProp') {
		cForm.method = "get";
		cForm.action = "getprops";
		cForm.submit();
	} else if(param == 'Cancel' || document.pressed == 'Cancel') {
		cForm.method = "get";
		cForm.action = "get";
		cForm.submit();
	} else if(param == 'Update' || document.pressed == 'Update') {
		cForm.method = "post";
		cForm.action = "update";
		cForm.submit();
	} else if(param == 'UpdateProp' || document.pressed == 'UpdateProp') {
		cForm.method = "post";
		cForm.action = "updateprop";
		cForm.submit();
	} else if(param == 'UserProperties' || document.pressed == 'UserProperties') {
		cForm.method = "get";
		cForm.action = "getprops";
		cForm.submit();
	} else if(param == 'Accounts' || document.pressed == 'Accounts') {
		cForm.method = "get";
		cForm.action = "get";
		cForm.submit();
	} else if(param == 'CreateTenant' || document.pressed == 'CreateTenant') {
		cForm.method = "post";
		cForm.action = "newtenant";
		cForm.submit();
	} else if(param == 'CancelTenant' || document.pressed == 'CancelTenant') {
		cForm.method = "get";
		cForm.action = "gettenants";
		cForm.submit();
	}else if(param == 'NewTenant' || document.pressed == 'NewTenant') {
		cForm.method = "get";
		cForm.action = "newtenant";
		cForm.submit();
	}else if(param == 'ManageAttributes' || document.pressed == 'ManageAttributes') {
		cForm.method = "get";
		cForm.action = "getprops";
		cForm.submit();
	}else if(param == 'PreviousTenant' || document.pressed == 'PreviousTenant') {
     		cForm.method = "get";
     		cForm.action = "prevtenants";
     		cForm.submit();
    } else if(param == 'NextTenant' || document.pressed == 'NextTenant') {
            cForm.method = "get";
            cForm.action = "nexttenants";
            cForm.submit();
    } else if(param == 'GetTenants' || document.pressed == 'GetTenants') {
            cForm.method = "get";
            cForm.action = "gettenants";
            cForm.submit();
    } else if(param == 'FilterTenant' || document.pressed == 'FilterTenant') {
        var filter=document.getElementById("filter");
        if (filter != null && filter.value !="" && filter.value.trim() !=" ") {
            cForm.method = "get";
            cForm.action = "searchtenants";
            cForm.submit();
        }
    } else if(param == 'UpdateTenant' || document.pressed == 'UpdateTenant') {
            cForm.method = "post";
            cForm.action = "updatetenant";
            cForm.submit();
    }
}

 $(document).ready(function(){

     var appName = 'admin-console';

     var i = $('.field').size();

     $('#add').click(function() {
         $('<input type="text" class="field" name="domains" value="" "style=width:270" />').fadeIn('slow').appendTo('#domainList');
         i++;
     });

     $('#remove').click(function() {
     if(i > 1) {
         $('.field:last').remove();
         i--;
     }
     });

     $('#reset').click(function() {
     while(i > 1) {
         $('.field:last').remove();
         i--;
     }
     });

     $('#backTenant').click(function(){
         window.close();
     });

     $('#tenantName').change(function() {
        var data = {tenantName : $(this).val()};
        var url = '/'+appName+'/tenant_attributes_and_roles';

        $.ajax({
            url: url,
			data: data,
            dataType : 'html',
            contentTypeString:"text/html;charset=UTF-8",
            cache: false,
            type: 'GET',
            success: function(aData, textStatus, jqXHR){
                $('#schemaAttributes').children().remove();
                $('#schemaAttributes').append(aData);
            },
            error: function (xhr, ajaxOptions, thrownError) {
                alert(xhr.status);
            }
        });
     });

     $('#selectedTenantName').change(function() {
        this.form.submit();
     });
 });
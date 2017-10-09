var menuUrl=new RegExp("menu/getMainmenu.logic$");

(function($){
	$(document).ajaxSend(function(event, jqXHR, ajaxOptions){
		if (!menuUrl.test(ajaxOptions.url)){
			$("#preparing-file-modal").show();
			$("#preparing-file-modal-img").show();
		}
	});

	// <!-- Defining: On Ajax Complete Event. -->
	$(document).ajaxComplete(function(event, XMLHttpRequest, ajaxOptions){
		if (!menuUrl.test(ajaxOptions.url)){
			$("#preparing-file-modal").hide();
			$("#preparing-file-modal-img").hide();
		}
	});
	

	// <!-- Defining: On Ajax Send Event. -->
	/*$(document).ajaxSend(function(event, jqXHR, ajaxOptions){
		if (!menuUrl.test(ajaxOptions.url))
			$("#preparing-file-modal").dialog({modal: true, closeOnEscape: false});
	});

	// <!-- Defining: On Ajax Error Event. -->
	$(document).ajaxError(function(event, jqXHR, ajaxSettings, thrownError){
		if( jqXHR.status === 403 ) {
			window.location = '/TKI';
		}
		if( jqXHR.status === 406 ) {
			message.setErrors( eval(jqXHR.responseText) );
		}
	});

	// <!-- Defining: On Ajax Complete Event. -->
	$(document).ajaxComplete(function(event, XMLHttpRequest, ajaxOptions){
		if (!menuUrl.test(ajaxOptions.url))
			$("#preparing-file-modal").dialog("close");
	});

	// <!-- Defining: On Ajax Success Event. -->
	$(document).ajaxSuccess(function(event, XMLHttpRequest, ajaxOptions){
		// common.Notify.messages( "Successfully" ).show();
	});*/

	// <!-- Defining: AJAX Serialization for Spring MVC. -->
	var jQueryParam = $.param;
	$.param = function( obj ) {

		if( $.type(obj) === "string" )
			return obj;

		var returnSt = jQueryParam.apply( this, arguments );

		return returnSt
		.replace(/[%]5[Bb]((?!\d+)\w+)[%]5[Dd]/g, ".$1")
		.replace(/[%]5[Bb]/g, "[")
		.replace(/[%]5[Dd]/g, "]")
		.replace(/([^&=]+\=(null)?[&]|[^&=]+\=(null)?$)/g,"")
		.replace(/(^[&]*|[&]*$)/g, "");
	};

	/*$.postJSON = function(url, data, callback) {
	    return jQuery.ajax({
	        'type': 'POST',
	        'url': url,
	        'contentType': 'application/json',
	        'data': JSON.stringify(data),
	        'dataType': 'json',
	        'success': callback
	    });
	};*/
})(jQuery);
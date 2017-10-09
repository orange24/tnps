$( function($) {
	
	$("[id=processSearchIcon]").click( function() {
		common.SlickGrid.setSearchBtn(processGrid.getGrid());
	});

});
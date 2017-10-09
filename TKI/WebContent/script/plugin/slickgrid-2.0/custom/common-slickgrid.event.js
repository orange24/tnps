jQuery( function($) {
	bindClickSearchIcon();
});

function bindClickSearchIcon() {
	/**
	 *  Handle Event that id eq 'searchIcon' to toggle search
	 */
	$("[id='searchIcon']").click( function() {
		var grid = eval($(this).parent("div").next().find("div.grid-detail").attr("id"));
		common.SlickGrid.setSearchBtn(grid.getGrid());
	});
}
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Untitled Document</title>
<%@ include file="importResources.jsp" %>
<script language="javascript">
	var menu;
	var show;
	var hide;
	var plus;
	var minus;
	var submenu;
	var txtPMenu;
	  	
	$(document).ready(function() {
		menu = $("li[id=menu]");
		show = $("#show");
		hide = $("#hide");
		submenu = $("li[id=submenu]");
		plus = $("img[id=plus]");
		minus = $("img[id=minus]");
		txtPMenu = $("b[id=txtPMenu]");
		
		menu.click(function(){
  			$(this).children().toggle();
  			txtPMenu.css("display","inline");
  		});
		
		submenu.click(function(e){
			e.stopPropagation();
  		});
		
		show.click(function(){
  			menu.children().show();
  			plus.css("display","none");
  		});
		
		hide.click(function(){
  			menu.children().css("display","none");
  			plus.css("display","inline");
  			txtPMenu.css("display","inline");
  		});
	});
</script>
<style type="text/css">
<!--
body {
	background-image: url(image/frame/left_bg.jpg);
	margin-left: 25px;
}

-->
</style>

</head>

<body>
<img id="show" src="image/menu/sidemenu_open_bt.jpg" width="60" height="18" />
<img src="image/menu/sidemenu_refresh.jpg" width="18" height="18" />
	<ol style="list-style-type:none;padding:0px;margin:0px;">
		<c:forEach items="${menuList}" var="menuParent">
			<li id="menu">
			<img id="plus" style="display:none" src="image/menu/sidemenu_plus.jpg" width="10" height="10" />
			<img id="minus" src="image/menu/sidemenu_minus.jpg" width="10" height="10" />
			<b id="txtPMenu">${menuParent.menuParentName}</b>
				<ul style="list-style-type:none;padding:6px;margin:3px;">
				<c:forEach items="${menuParent.menuList}" var="menu">
					<li id="submenu" style="padding:1px;margin:1px;">
						<img src="image/menu/sidemenu_circle.jpg" width="10" height="10" border="0" />
						<a href="${menu.menuUrl}" target="mainFrame">${menu.menuName}</a>
					</li>
				</c:forEach>
				</ul>
			</li>
		</c:forEach>
	</ol>
<img id="hide" src="image/menu/side_menu_close_bt.jpg" width="60" height="18" />
</body>
</html>

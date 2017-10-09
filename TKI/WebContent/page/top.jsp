<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Untitled Document</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
<link href="css/main.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
function updateClock ()
{
  var currentTime = new Date ();
  var currentDay = currentTime.getDay ();
  
  //Convert the day component to day abbreviation
  currentDay = ( currentDay == 0 ) ? "Sun" : currentDay;
  currentDay = ( currentDay == 1 ) ? "Mon" : currentDay;
  currentDay = ( currentDay == 2 ) ? "Tue" : currentDay;
  currentDay = ( currentDay == 3 ) ? "Wed" : currentDay;
  currentDay = ( currentDay == 4 ) ? "Thu" : currentDay;
  currentDay = ( currentDay == 5 ) ? "Fri" : currentDay;
  currentDay = ( currentDay == 6 ) ? "Sat" : currentDay;
  
  var currentMonth = currentTime.getMonth( ); 
  
  //Convert the month component to text month
  currentMonth = ( currentMonth == 0 ) ? "January" : currentMonth;
  currentMonth = ( currentMonth == 1 ) ? "February" : currentMonth;
  currentMonth = ( currentMonth == 2 ) ? "March" : currentMonth;
  currentMonth = ( currentMonth == 3 ) ? "April" : currentMonth;
  currentMonth = ( currentMonth == 4 ) ? "May" : currentMonth;
  currentMonth = ( currentMonth == 5 ) ? "June" : currentMonth;
  currentMonth = ( currentMonth == 6 ) ? "July" : currentMonth;
  currentMonth = ( currentMonth == 7 ) ? "August" : currentMonth;
  currentMonth = ( currentMonth == 8 ) ? "September" : currentMonth;
  currentMonth = ( currentMonth == 9 ) ? "October" : currentMonth;
  currentMonth = ( currentMonth == 10) ? "November" : currentMonth;
  currentMonth = ( currentMonth == 11) ? "December" : currentMonth;
  
  var currentDate = currentTime.getDate( );
  
  // Add suffix to the date
  currentDate = ( currentDate == 1 || currentDate == 21 || currentDate == 31 ) ? currentDate : currentDate;
  currentDate = ( currentDate == 2 || currentDate == 22 ) ? currentDate : currentDate;
  currentDate = ( currentDate == 3 ) || currentDate == 23 ? currentDate : currentDate;
  currentDate = ( currentDate > 3 || currentDate < 21 || currentDate > 23 || currentDate < 31 ) ? currentDate : currentDate;

  var currentHours = currentTime.getHours ();
  var currentMinutes = currentTime.getMinutes ();
  var currentSeconds = currentTime.getSeconds ();

  // Pad the minutes and seconds with leading zeros, if required
  currentMinutes = ( currentMinutes < 10 ? "0" : "" ) + currentMinutes;
  currentSeconds = ( currentSeconds < 10 ? "0" : "" ) + currentSeconds;

  // Choose either "AM" or "PM" as appropriate
  var timeOfDay = ( currentHours < 12 ) ? "AM" : "PM";

  // Convert the hours component to 12-hour format if needed
  currentHours = ( currentHours > 12 ) ? currentHours - 12 : currentHours;

  // Convert an hours component of "0" to "12"
  currentHours = ( currentHours == 0 ) ? 12 : currentHours;

  // Compose the string for display
  var currentTimeString = currentDay + " " + currentMonth +  " " + currentDate + " - " +
  							currentHours + ":" + currentMinutes + ":" + currentSeconds + " " + timeOfDay;

  // Update the time display
  document.getElementById("clock").firstChild.nodeValue = currentTimeString;
}
</script>



<script language="JavaScript" type="text/javascript">
var origCols;
function toggleFrame() {
    if (origCols) {
        showFrame();
    } else {
        hideFrame();
    }
}
function hideFrame() {
    var frameset =  window.top.document.getElementById("masterFrameset");
    document.getElementById("img_header1").src="image/frame/header1_B.jpg";
    origCols = frameset.cols;
    frameset.cols = "0, *, 18";
}

function showFrame() {
    window.top.document.getElementById("masterFrameset").cols = origCols;
    document.getElementById("img_header1").src="image/frame/header1.jpg";
    origCols = null;
}

// set frame visibility based on previous cookie setting
function setFrameVis() {
    if (document.getElementById) {
        if (getCookie("frameHidden") == "true") {
            hideFrame()
        }  
    }
}
</script>

</head>

<body onload="updateClock(); setInterval('updateClock()', 1000 )">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td width="243" align="left"><img id="img_header1" src="image/frame/header1.jpg" width="243" height="114" /></td>
			<td style="background:url('image/frame/header_bg.jpg');babackground-repeat:repeat-x">&nbsp;</td>
			<td valign="top" style="background:url('image/frame/header2.jpg');background-repeat:no-repeat; background-position:right; padding-top: 20px; padding-right: 45px;" width="615">
				<div align="right">
				<a href="javascript:toggleFrame()"><img src="image/trans.gif" width="125" height="35" border="0"/></a>				
				<a href="CMM_S02.html" target="mainFrame"><img src="image/trans.gif" width="125" height="35" border="0"/></a>
				<a href="main.html" target="mainFrame"><img src="image/trans.gif" width="80" height="35" border="0"/></a>
				</div>
				<div align="right">
					<security:authentication property="principal.username"/> <a href="j_spring_security_logout" target="_parent"><img src="image/frame/logout.jpg" width="85" height="25" border="0" /></a>
					<br/><span id="clock">&nbsp;</span>
				</div>
			</td>
		</tr>
	</table>
</body>
</html>

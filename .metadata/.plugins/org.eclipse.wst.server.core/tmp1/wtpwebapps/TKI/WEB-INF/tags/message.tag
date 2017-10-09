<%@ tag body-content="empty" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="item" required="true" rtexprvalue="true" type="th.co.nttdata.tki.bean.AbstractBaseBean" %>
<%
	String cssStyle = " style=\"display:none;\"";

	if( item == null ) {
		// <!-- Nothing to do when 'item' is unavailable. -->
	} else if( item.getInfos() != null && item.getInfos().size() > 0 ) {
		cssStyle = "";
	} else if( item.getErrors() != null && item.getErrors().size() > 0 ) {
		cssStyle = "";
	}
%>
<div<%= cssStyle %>>
	<ul id="infMessages" class="textblue"><c:forEach items="${item.infos}" var="info"><li><spring:message code="${info.code}" arguments="${info.arguments}"/></li></c:forEach></ul>
	<ul id="errMessages" class="textred"><c:forEach items="${item.errors}" var="err"><li><spring:message code="${err.code}" arguments="${err.arguments}"/></li></c:forEach></ul>
</div>
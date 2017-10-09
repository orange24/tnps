<%@ tag body-content="empty" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ attribute name="item" required="true" rtexprvalue="true" type="th.co.nttdata.tki.bean.AbstractBaseBean" %>
<%@ attribute name="index" required="true" rtexprvalue="true" type="java.lang.Integer" %>
<%
	// <!-- Processing. -->
	out.println( item.getPageCount() * (item.getPageNumber() - 1) + index + 1 );
%>
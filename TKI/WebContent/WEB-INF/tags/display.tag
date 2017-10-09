<%@ tag body-content="empty" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ attribute name="item" required="true" rtexprvalue="true" type="th.co.nttdata.tki.bean.AbstractBaseBean" %>
<%
	// <!-- Initial Data. -->
	Integer[] pageDisplay = item.getPageDisplay();
	Integer     pageCount = item.getPageCount();
	StringBuilder buffer = new StringBuilder();

	// <!-- Processing. -->
	buffer.append("Display <select name='pageCount' id='pageCount'>");
	for( Integer count : pageDisplay )
		buffer
			.append("<option value='")
			.append(count)
			.append("'")
			.append(pageCount == count ? " selected='selected'" : "")
			.append(">")
			.append(count)
			.append("</option>");

	out.println(buffer.append("</select> rows/page"));
%>
<%@ tag body-content="empty" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ attribute name="item" required="true" rtexprvalue="true" type="th.co.nttdata.tki.bean.AbstractBaseBean" %>
<%
	// <!-- Initial Data. -->
	Integer pageNumber = item.getPageNumber();
	Integer  pageTotal = item.getPageTotal();
	StringBuilder buffer = new StringBuilder();
	StringBuilder select = new StringBuilder();

	// <!-- Processing. -->
	select.append("<select name='pageNumber' id='pageNumber'>");
	for( Integer index = 1; index <= pageTotal; index++ )
		select
			.append("<option value='")
			.append(index)
			.append("'")
			.append(pageNumber == index ? " selected='selected'" : "")
			.append(">")
			.append(index)
			.append("</option>");
	select.append("</select>");

	// <!-- Generating. -->
	boolean isFirst = pageNumber == 1;
	boolean  isLast = pageNumber == pageTotal; 
	buffer.append("Page ").append( select ).append("/").append( pageTotal ).append(" ");

	if( !isFirst )
		buffer.append("<a id='firstPage' href='javascript:void(0);'>&lt;&lt;</a> &nbsp;<a id='prevPage' href='javascript:void(0);'>Prev</a>");
	if( !isFirst && !isLast )
		buffer.append(" | ");
	if( !isLast )
		buffer.append("<a id='nextPage' href='javascript:void(0);'>Next</a>&nbsp; <a id='lastPage' href='javascript:void(0);'>&gt;&gt;</a>");

	out.println(buffer);
%>
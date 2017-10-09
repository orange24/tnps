package org.apache.jsp.tag.web;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class number_tag
    extends javax.servlet.jsp.tagext.SimpleTagSupport
    implements org.apache.jasper.runtime.JspSourceDependent {


  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  private JspContext jspContext;
  private java.io.Writer _jspx_sout;
  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public void setJspContext(JspContext ctx) {
    super.setJspContext(ctx);
    java.util.ArrayList _jspx_nested = null;
    java.util.ArrayList _jspx_at_begin = null;
    java.util.ArrayList _jspx_at_end = null;
    this.jspContext = new org.apache.jasper.runtime.JspContextWrapper(ctx, _jspx_nested, _jspx_at_begin, _jspx_at_end, null);
  }

  public JspContext getJspContext() {
    return this.jspContext;
  }
  private th.co.nttdata.tki.bean.AbstractBaseBean item;

  public th.co.nttdata.tki.bean.AbstractBaseBean getItem() {
    return this.item;
  }

  public void setItem(th.co.nttdata.tki.bean.AbstractBaseBean item) {
    this.item = item;
    jspContext.setAttribute("item", item);
  }

  public Object getDependants() {
    return _jspx_dependants;
  }

  private void _jspInit(ServletConfig config) {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(config.getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) config.getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
  }

  public void doTag() throws JspException, java.io.IOException {
    PageContext _jspx_page_context = (PageContext)jspContext;
    HttpServletRequest request = (HttpServletRequest) _jspx_page_context.getRequest();
    HttpServletResponse response = (HttpServletResponse) _jspx_page_context.getResponse();
    HttpSession session = _jspx_page_context.getSession();
    ServletContext application = _jspx_page_context.getServletContext();
    ServletConfig config = _jspx_page_context.getServletConfig();
    JspWriter out = jspContext.getOut();
    _jspInit(config);
    jspContext.getELContext().putContext(JspContext.class,jspContext);
    if( getItem() != null ) 
      _jspx_page_context.setAttribute("item", getItem());

    try {
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");

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

    } catch( Throwable t ) {
      if( t instanceof SkipPageException )
          throw (SkipPageException) t;
      if( t instanceof java.io.IOException )
          throw (java.io.IOException) t;
      if( t instanceof IllegalStateException )
          throw (IllegalStateException) t;
      if( t instanceof JspException )
          throw (JspException) t;
      throw new JspException(t);
    } finally {
      jspContext.getELContext().putContext(JspContext.class,super.getJspContext());
      ((org.apache.jasper.runtime.JspContextWrapper) jspContext).syncEndTagFile();
    }
  }
}

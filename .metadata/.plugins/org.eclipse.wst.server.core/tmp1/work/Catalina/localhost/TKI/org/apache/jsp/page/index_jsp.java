package org.apache.jsp.page;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContext;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Frameset//EN\" \"http://www.w3.org/TR/html4/frameset.dtd\">\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\" />\r\n");
      out.write("<title>Thai Kikuwa Induntry Co., Ltd.</title>\r\n");
      out.write("<script type=\"text/javascript\">\r\n");

	String bundleName = "th.co.nttdata.errorcodes";
	RequestContext rq = new RequestContext(request);

	ResourceBundle resourceBundle = ResourceBundle.getBundle(bundleName);
	StringBuilder buffer = new StringBuilder();
	for( String key : resourceBundle.keySet() ) {
		buffer.append(",\n\t\t\"").append(key).append("\" : \"").append(rq.getMessage(key)).append("\"");
	}

	bundleName = "th.co.nttdata.messages";
	resourceBundle = ResourceBundle.getBundle(bundleName);
	for( String key : resourceBundle.keySet() ) {
		buffer.append(",\n\t\t\"").append(key).append("\" : \"").append(rq.getMessage(key)).append("\"");
	}

      out.write("\r\n");
      out.write("\tvar messageCodes = { ");
      out.print( buffer.substring(1) );
      out.write("\r\n");
      out.write("\t};\r\n");
      out.write("\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("\r\n");
      out.write("<frameset rows=\"114,*\" cols=\"*\" frameborder=\"NO\" framespacing=\"0\">\r\n");
      out.write("\t<frame src=\"top.html\" name=\"topFrame\" scrolling=\"no\" noresize=\"noresize\" id=\"topFrame\" title=\"topFrame\" />\r\n");
      out.write("\t<frameset id=\"masterFrameset\" rows=\"*\" cols=\"211,*,18\" frameborder=\"NO\" framespacing=\"0\">\r\n");
      out.write("\t\t<frame src=\"menu.html\" name=\"leftFrame\" scrolling=\"auto\" noresize=\"noresize\" id=\"leftFrame\" title=\"leftFrame\" />\r\n");
      out.write("\t\t<frame src=\"main.html\" name=\"mainFrame\" id=\"mainFrame\" title=\"mainFrame\" frameborder=\"0\" />\r\n");
      out.write("\t\t<frame src=\"right.html\" name=\"rightBG\" scrolling=\"no\" noresize=\"noresize\" id=\"rightBG\" title=\"rightBG\" />\r\n");
      out.write("\t</frameset>\r\n");
      out.write("</frameset>\r\n");
      out.write("</html>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else log(t.getMessage(), t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}

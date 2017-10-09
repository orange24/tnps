package org.apache.jsp.page;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class top_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fsecurity_005fauthentication_0026_005fproperty_005fnobody;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fsecurity_005fauthentication_0026_005fproperty_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fsecurity_005fauthentication_0026_005fproperty_005fnobody.release();
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
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n");
      out.write("<html >\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\" />\r\n");
      out.write("<title>Untitled Document</title>\r\n");
      out.write("<style type=\"text/css\">\r\n");
      out.write("<!--\r\n");
      out.write("body {\r\n");
      out.write("\tmargin-left: 0px;\r\n");
      out.write("\tmargin-top: 0px;\r\n");
      out.write("\tmargin-right: 0px;\r\n");
      out.write("\tmargin-bottom: 0px;\r\n");
      out.write("}\r\n");
      out.write("-->\r\n");
      out.write("</style>\r\n");
      out.write("<link href=\"css/main.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("function updateClock ()\r\n");
      out.write("{\r\n");
      out.write("  var currentTime = new Date ();\r\n");
      out.write("  var currentDay = currentTime.getDay ();\r\n");
      out.write("  \r\n");
      out.write("  //Convert the day component to day abbreviation\r\n");
      out.write("  currentDay = ( currentDay == 0 ) ? \"Sun\" : currentDay;\r\n");
      out.write("  currentDay = ( currentDay == 1 ) ? \"Mon\" : currentDay;\r\n");
      out.write("  currentDay = ( currentDay == 2 ) ? \"Tue\" : currentDay;\r\n");
      out.write("  currentDay = ( currentDay == 3 ) ? \"Wed\" : currentDay;\r\n");
      out.write("  currentDay = ( currentDay == 4 ) ? \"Thu\" : currentDay;\r\n");
      out.write("  currentDay = ( currentDay == 5 ) ? \"Fri\" : currentDay;\r\n");
      out.write("  currentDay = ( currentDay == 6 ) ? \"Sat\" : currentDay;\r\n");
      out.write("  \r\n");
      out.write("  var currentMonth = currentTime.getMonth( ); \r\n");
      out.write("  \r\n");
      out.write("  //Convert the month component to text month\r\n");
      out.write("  currentMonth = ( currentMonth == 0 ) ? \"January\" : currentMonth;\r\n");
      out.write("  currentMonth = ( currentMonth == 1 ) ? \"February\" : currentMonth;\r\n");
      out.write("  currentMonth = ( currentMonth == 2 ) ? \"March\" : currentMonth;\r\n");
      out.write("  currentMonth = ( currentMonth == 3 ) ? \"April\" : currentMonth;\r\n");
      out.write("  currentMonth = ( currentMonth == 4 ) ? \"May\" : currentMonth;\r\n");
      out.write("  currentMonth = ( currentMonth == 5 ) ? \"June\" : currentMonth;\r\n");
      out.write("  currentMonth = ( currentMonth == 6 ) ? \"July\" : currentMonth;\r\n");
      out.write("  currentMonth = ( currentMonth == 7 ) ? \"August\" : currentMonth;\r\n");
      out.write("  currentMonth = ( currentMonth == 8 ) ? \"September\" : currentMonth;\r\n");
      out.write("  currentMonth = ( currentMonth == 9 ) ? \"October\" : currentMonth;\r\n");
      out.write("  currentMonth = ( currentMonth == 10) ? \"November\" : currentMonth;\r\n");
      out.write("  currentMonth = ( currentMonth == 11) ? \"December\" : currentMonth;\r\n");
      out.write("  \r\n");
      out.write("  var currentDate = currentTime.getDate( );\r\n");
      out.write("  \r\n");
      out.write("  // Add suffix to the date\r\n");
      out.write("  currentDate = ( currentDate == 1 || currentDate == 21 || currentDate == 31 ) ? currentDate : currentDate;\r\n");
      out.write("  currentDate = ( currentDate == 2 || currentDate == 22 ) ? currentDate : currentDate;\r\n");
      out.write("  currentDate = ( currentDate == 3 ) || currentDate == 23 ? currentDate : currentDate;\r\n");
      out.write("  currentDate = ( currentDate > 3 || currentDate < 21 || currentDate > 23 || currentDate < 31 ) ? currentDate : currentDate;\r\n");
      out.write("\r\n");
      out.write("  var currentHours = currentTime.getHours ();\r\n");
      out.write("  var currentMinutes = currentTime.getMinutes ();\r\n");
      out.write("  var currentSeconds = currentTime.getSeconds ();\r\n");
      out.write("\r\n");
      out.write("  // Pad the minutes and seconds with leading zeros, if required\r\n");
      out.write("  currentMinutes = ( currentMinutes < 10 ? \"0\" : \"\" ) + currentMinutes;\r\n");
      out.write("  currentSeconds = ( currentSeconds < 10 ? \"0\" : \"\" ) + currentSeconds;\r\n");
      out.write("\r\n");
      out.write("  // Choose either \"AM\" or \"PM\" as appropriate\r\n");
      out.write("  var timeOfDay = ( currentHours < 12 ) ? \"AM\" : \"PM\";\r\n");
      out.write("\r\n");
      out.write("  // Convert the hours component to 12-hour format if needed\r\n");
      out.write("  currentHours = ( currentHours > 12 ) ? currentHours - 12 : currentHours;\r\n");
      out.write("\r\n");
      out.write("  // Convert an hours component of \"0\" to \"12\"\r\n");
      out.write("  currentHours = ( currentHours == 0 ) ? 12 : currentHours;\r\n");
      out.write("\r\n");
      out.write("  // Compose the string for display\r\n");
      out.write("  var currentTimeString = currentDay + \" \" + currentMonth +  \" \" + currentDate + \" - \" +\r\n");
      out.write("  \t\t\t\t\t\t\tcurrentHours + \":\" + currentMinutes + \":\" + currentSeconds + \" \" + timeOfDay;\r\n");
      out.write("\r\n");
      out.write("  // Update the time display\r\n");
      out.write("  document.getElementById(\"clock\").firstChild.nodeValue = currentTimeString;\r\n");
      out.write("}\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<script language=\"JavaScript\" type=\"text/javascript\">\r\n");
      out.write("var origCols;\r\n");
      out.write("function toggleFrame() {\r\n");
      out.write("    if (origCols) {\r\n");
      out.write("        showFrame();\r\n");
      out.write("    } else {\r\n");
      out.write("        hideFrame();\r\n");
      out.write("    }\r\n");
      out.write("}\r\n");
      out.write("function hideFrame() {\r\n");
      out.write("    var frameset =  window.top.document.getElementById(\"masterFrameset\");\r\n");
      out.write("    document.getElementById(\"img_header1\").src=\"image/frame/header1_B.jpg\";\r\n");
      out.write("    origCols = frameset.cols;\r\n");
      out.write("    frameset.cols = \"0, *, 18\";\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function showFrame() {\r\n");
      out.write("    window.top.document.getElementById(\"masterFrameset\").cols = origCols;\r\n");
      out.write("    document.getElementById(\"img_header1\").src=\"image/frame/header1.jpg\";\r\n");
      out.write("    origCols = null;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("// set frame visibility based on previous cookie setting\r\n");
      out.write("function setFrameVis() {\r\n");
      out.write("    if (document.getElementById) {\r\n");
      out.write("        if (getCookie(\"frameHidden\") == \"true\") {\r\n");
      out.write("            hideFrame()\r\n");
      out.write("        }  \r\n");
      out.write("    }\r\n");
      out.write("}\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write("</head>\r\n");
      out.write("\r\n");
      out.write("<body onload=\"updateClock(); setInterval('updateClock()', 1000 )\">\r\n");
      out.write("\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td width=\"243\" align=\"left\"><img id=\"img_header1\" src=\"image/frame/header1.jpg\" width=\"243\" height=\"114\" /></td>\r\n");
      out.write("\t\t\t<td style=\"background:url('image/frame/header_bg.jpg');babackground-repeat:repeat-x\">&nbsp;</td>\r\n");
      out.write("\t\t\t<td valign=\"top\" style=\"background:url('image/frame/header2.jpg');background-repeat:no-repeat; background-position:right; padding-top: 20px; padding-right: 45px;\" width=\"615\">\r\n");
      out.write("\t\t\t\t<div align=\"right\">\r\n");
      out.write("\t\t\t\t<a href=\"javascript:toggleFrame()\"><img src=\"image/trans.gif\" width=\"125\" height=\"35\" border=\"0\"/></a>\t\t\t\t\r\n");
      out.write("\t\t\t\t<a href=\"CMM_S02.html\" target=\"mainFrame\"><img src=\"image/trans.gif\" width=\"125\" height=\"35\" border=\"0\"/></a>\r\n");
      out.write("\t\t\t\t<a href=\"main.html\" target=\"mainFrame\"><img src=\"image/trans.gif\" width=\"80\" height=\"35\" border=\"0\"/></a>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div align=\"right\">\r\n");
      out.write("\t\t\t\t\t");
      if (_jspx_meth_security_005fauthentication_005f0(_jspx_page_context))
        return;
      out.write(" <a href=\"j_spring_security_logout\" target=\"_parent\"><img src=\"image/frame/logout.jpg\" width=\"85\" height=\"25\" border=\"0\" /></a>\r\n");
      out.write("\t\t\t\t\t<br/><span id=\"clock\">&nbsp;</span>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t</table>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
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

  private boolean _jspx_meth_security_005fauthentication_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  security:authentication
    org.springframework.security.taglibs.authz.AuthenticationTag _jspx_th_security_005fauthentication_005f0 = (org.springframework.security.taglibs.authz.AuthenticationTag) _005fjspx_005ftagPool_005fsecurity_005fauthentication_0026_005fproperty_005fnobody.get(org.springframework.security.taglibs.authz.AuthenticationTag.class);
    boolean _jspx_th_security_005fauthentication_005f0_reused = false;
    try {
      _jspx_th_security_005fauthentication_005f0.setPageContext(_jspx_page_context);
      _jspx_th_security_005fauthentication_005f0.setParent(null);
      // /page/top.jsp(134,5) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_security_005fauthentication_005f0.setProperty("principal.username");
      int _jspx_eval_security_005fauthentication_005f0 = _jspx_th_security_005fauthentication_005f0.doStartTag();
      if (_jspx_th_security_005fauthentication_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fsecurity_005fauthentication_0026_005fproperty_005fnobody.reuse(_jspx_th_security_005fauthentication_005f0);
      _jspx_th_security_005fauthentication_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_security_005fauthentication_005f0, _jsp_annotationprocessor, _jspx_th_security_005fauthentication_005f0_reused);
    }
    return false;
  }
}

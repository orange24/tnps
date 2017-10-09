package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class login_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(3);
    _jspx_dependants.add("/page/importResources.jsp");
    _jspx_dependants.add("/page/loadingResource.jsp");
    _jspx_dependants.add("/WEB-INF/tags/message.tag");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fspring_005fmessage_0026_005fcode_005fnobody;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fspring_005fmessage_0026_005fcode_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fspring_005fmessage_0026_005fcode_005fnobody.release();
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

      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\" />\r\n");
      out.write("<title>Thai Kikuwa Induntry Co., Ltd.</title>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\r\n");
      out.write("\r\n");
      out.write("<div id=\"preparing-file-modal\" class=\"ui-widget-overlay\" style=\"width: 100%; height: 100%; z-index: 1001; position: fixed;display: none;\"></div>\r\n");
      out.write("<div id=\"preparing-file-modal-img\" style=\"position: fixed; z-index: 9999; top:30%; left: 45%; display: none;\">\r\n");
      out.write("\t<img src=\"image/loading.gif\"/>\r\n");
      out.write("</div>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<div id=\"preparing-file-modal-page\" class=\"ui-widget-overlay\" style=\"width: 100%; height: 100%; z-index: 1001; position: fixed;\"></div>\r\n");
      out.write("<div id=\"preparing-file-modal-page-img\" style=\"position: fixed; z-index: 9999; top:30%; left: 45%;\">\r\n");
      out.write("\t<img src=\"image/loading.gif\"/>\r\n");
      out.write("</div>");
      out.write("\r\n");
      out.write("<link href=\"css/main.css\" type=\"text/css\" rel=\"stylesheet\" />\r\n");
      out.write("<link href=\"css/tab.css\" rel=\"stylesheet\" type=\"text/css\" />\t\r\n");
      out.write("<link href=\"css/jquery-ui-1.8.6.custom.css\" type=\"text/css\" rel=\"stylesheet\" />\t\r\n");
      out.write("<script src=\"script/plugin/standard_script.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script src=\"script/plugin/jquery-1.4.2.min.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script src=\"script/plugin/jquery-ui-1.8.6.custom.min.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src='script/jslib/common.js'></script>\r\n");
      out.write("\r\n");
      out.write("<script language=\"javascript\">\r\n");
      out.write("//JQuery\r\n");
      out.write("var username;\r\n");
      out.write("var password;\r\n");
      out.write("var btnLogin;\r\n");
      out.write("var btnCancel;\r\n");
      out.write("\r\n");
      out.write("$(document).ready(function(){\r\n");
      out.write("\tvar secMsg = '");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${SPRING_SECURITY_LAST_EXCEPTION.message}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("';\r\n");
      out.write("\tif (secMsg) {\r\n");
      out.write("\t\tmessage.errMessages.append( $(\"<li/>\").html(secMsg) );\r\n");
      out.write("\t\tmessage.elmMessages.css(\"display\", \"\");\r\n");
      out.write("\t}\r\n");
      out.write("\t//message.setError();\r\n");
      out.write("\tusername = $(\"#username\");\r\n");
      out.write("\tpassword = $(\"#password\");\r\n");
      out.write("\tbtnLogin = $(\"#btnLogin\");\r\n");
      out.write("\tbtnCancel = $(\"#btnCancel\");\r\n");
      out.write("\t\r\n");
      out.write("\tbtnLogin.click(function(){\r\n");
      out.write("\t\tvar errors = [];\r\n");
      out.write("\t\tif( !username.val() )\r\n");
      out.write("\t\t\terrors.push({\"code\": \"err.cmm.001\", \"arguments\": [\"Username\"]});\r\n");
      out.write("\t\tif( !password.val() )\r\n");
      out.write("\t\t\terrors.push({\"code\": \"err.cmm.001\", \"arguments\": [\"Password\"]});\r\n");
      out.write("\t\tif( errors.length > 0 ) {\r\n");
      out.write("\t\t\tmessage.setErrors(errors);\r\n");
      out.write("\t\t\treturn false;\r\n");
      out.write("\t\t}\r\n");
      out.write("\r\n");
      out.write("\t\tmessage.clear();\r\n");
      out.write("\t\treturn true;\r\n");
      out.write("\t});\r\n");
      out.write("\t\r\n");
      out.write("\t$(\"form\").submit(function(){\r\n");
      out.write("\t\thideLoading();\r\n");
      out.write("\t\treturn message.isNoError();\r\n");
      out.write("\t});\r\n");
      out.write("\t\r\n");
      out.write("\tbtnCancel.click(function(){\r\n");
      out.write("\t\tmessage.clear();\r\n");
      out.write("\t});\r\n");
      out.write("\t\t\t\r\n");
      out.write("});\r\n");
      out.write("</script>\r\n");
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
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\r\n");
      out.write("\t<div style=\"border: #000 solid 1px; position: absolute; top: 0; right: 0; bottom: 0; left: 0; background:url('image/login/bg.png'); background-repeat: no-repeat;\">\r\n");
      out.write("\r\n");
      out.write("\t<form id=\"loginForm\" name=\"loginForm\" action=\"j_spring_security_check\" method=\"post\">\r\n");
      out.write("\r\n");
      out.write("\t\t<table width=\"300\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\" style=\"margin: 180px auto;\">\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td colspan=\"2\">\r\n");
      out.write("\t\t\t\t<img src=\"image/login/logo.gif\" alt=\"\" width=\"246\" height=\"33\"/>\r\n");
      out.write("\t\t\t\t<br/>\r\n");
      out.write("\t\t\t\t<br/>\r\n");
      out.write("\t\t\t\t");
      if (_jspx_meth_page_005fmessage_005f0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t</td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td width=\"80\" align=\"right\"><strong>Username :</strong></td>\r\n");
      out.write("\t\t\t<td align=\"left\">\r\n");
      out.write("\t\t\t\t<input name=\"j_username\" id=\"username\" type=\"text\" size=\"25\"/>\r\n");
      out.write("\t\t\t</td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td align=\"right\"><strong>Password : </strong></td>\r\n");
      out.write("\t\t\t<td align=\"left\">\r\n");
      out.write("\t\t\t\t<input name=\"j_password\" id=\"password\" type=\"password\" size=\"25\"/>\r\n");
      out.write("\t\t\t</td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td colspan=\"2\" align=\"center\">\r\n");
      out.write("\t\t\t\t<br/>\r\n");
      out.write("\t\t\t\t<br/>\r\n");
      out.write("\t\t\t\t<input id=\"btnLogin\" type=\"submit\" value=\"Login\"/>\r\n");
      out.write("\t\t\t\t&nbsp;\r\n");
      out.write("\t\t\t\t<input id=\"btnCancel\" type=\"reset\" value=\"Cancel\"/>\r\n");
      out.write("\t\t\t</td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t</table>\r\n");
      out.write("\r\n");
      out.write("\t</form>\r\n");
      out.write("\r\n");
      out.write("\t<div align=\"right\" style=\"position: absolute; right: 0; bottom: 0;\">\r\n");
      out.write("\t\t&copy; 2010 Thai Kikuwa Industry Co., Ltd.<br/>\r\n");
      out.write("\t\tPower By NTTDATA (Thailand) Co., Ltd.<br/>\r\n");
      out.write("\t\t<strong>");
      if (_jspx_meth_spring_005fmessage_005f0(_jspx_page_context))
        return;
      out.write("</strong>\r\n");
      out.write("\t</div>\r\n");
      out.write("\r\n");
      out.write("\t</div>\r\n");
      out.write("\r\n");
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

  private boolean _jspx_meth_page_005fmessage_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  page:message
    org.apache.jsp.tag.web.message_tag _jspx_th_page_005fmessage_005f0 = new org.apache.jsp.tag.web.message_tag();
    org.apache.jasper.runtime.AnnotationHelper.postConstruct(_jsp_annotationprocessor, _jspx_th_page_005fmessage_005f0);
    _jspx_th_page_005fmessage_005f0.setJspContext(_jspx_page_context);
    // /login.jsp(78,4) name = item type = th.co.nttdata.tki.bean.AbstractBaseBean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = java.lang.String deferredMethod = false methodSignature = null
    _jspx_th_page_005fmessage_005f0.setItem((th.co.nttdata.tki.bean.AbstractBaseBean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${beanInput}", th.co.nttdata.tki.bean.AbstractBaseBean.class, (PageContext)_jspx_page_context, null, false));
    _jspx_th_page_005fmessage_005f0.doTag();
    org.apache.jasper.runtime.AnnotationHelper.preDestroy(_jsp_annotationprocessor, _jspx_th_page_005fmessage_005f0);
    return false;
  }

  private boolean _jspx_meth_spring_005fmessage_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  spring:message
    org.springframework.web.servlet.tags.MessageTag _jspx_th_spring_005fmessage_005f0 = (org.springframework.web.servlet.tags.MessageTag) _005fjspx_005ftagPool_005fspring_005fmessage_0026_005fcode_005fnobody.get(org.springframework.web.servlet.tags.MessageTag.class);
    boolean _jspx_th_spring_005fmessage_005f0_reused = false;
    try {
      _jspx_th_spring_005fmessage_005f0.setPageContext(_jspx_page_context);
      _jspx_th_spring_005fmessage_005f0.setParent(null);
      // /login.jsp(109,10) name = code type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_spring_005fmessage_005f0.setCode("softwareVersion");
      int[] _jspx_push_body_count_spring_005fmessage_005f0 = new int[] { 0 };
      try {
        int _jspx_eval_spring_005fmessage_005f0 = _jspx_th_spring_005fmessage_005f0.doStartTag();
        if (_jspx_th_spring_005fmessage_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
          return true;
        }
      } catch (Throwable _jspx_exception) {
        while (_jspx_push_body_count_spring_005fmessage_005f0[0]-- > 0)
          out = _jspx_page_context.popBody();
        _jspx_th_spring_005fmessage_005f0.doCatch(_jspx_exception);
      } finally {
        _jspx_th_spring_005fmessage_005f0.doFinally();
      }
      _005fjspx_005ftagPool_005fspring_005fmessage_0026_005fcode_005fnobody.reuse(_jspx_th_spring_005fmessage_005f0);
      _jspx_th_spring_005fmessage_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_spring_005fmessage_005f0, _jsp_annotationprocessor, _jspx_th_spring_005fmessage_005f0_reused);
    }
    return false;
  }
}

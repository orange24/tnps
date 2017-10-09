package org.apache.jsp.page.FNG;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class FNG_005fS01_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(3);
    _jspx_dependants.add("/page/FNG/../importResources.jsp");
    _jspx_dependants.add("/page/FNG/../loadingResource.jsp");
    _jspx_dependants.add("/WEB-INF/tags/message.tag");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fspring_005fmessage_0026_005fcode_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fform_005finput_0026_005ftabindex_005fsize_005fpath_005fid_005fcssClass_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fform_005fradiobutton_0026_005fvalue_005ftabindex_005fpath_005fid_005fdisabled_005fchecked_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fform_005fradiobutton_0026_005fvalue_005fpath_005fid_005fdisabled_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fspring_005fmessage_0026_005fcode_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fform_005finput_0026_005ftabindex_005fsize_005fpath_005fid_005fcssClass_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fform_005fradiobutton_0026_005fvalue_005ftabindex_005fpath_005fid_005fdisabled_005fchecked_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fform_005fradiobutton_0026_005fvalue_005fpath_005fid_005fdisabled_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fspring_005fmessage_0026_005fcode_005fnobody.release();
    _005fjspx_005ftagPool_005fform_005finput_0026_005ftabindex_005fsize_005fpath_005fid_005fcssClass_005fnobody.release();
    _005fjspx_005ftagPool_005fform_005fradiobutton_0026_005fvalue_005ftabindex_005fpath_005fid_005fdisabled_005fchecked_005fnobody.release();
    _005fjspx_005ftagPool_005fform_005fradiobutton_0026_005fvalue_005fpath_005fid_005fdisabled_005fnobody.release();
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
    _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.release();
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

      out.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");

String contextPath = request.getContextPath();

      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<title></title>\r\n");
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
      out.write("<script src=\"page/FNG/FNG_S01.js\" language=\"javascript\"></script>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<h1>");
      if (_jspx_meth_spring_005fmessage_005f0(_jspx_page_context))
        return;
      out.write("</h1>\r\n");
      out.write("  <ul id=\"navlist\">\r\n");
      out.write("    <li><a href=\"FNG_S01.html\" id=\"current\">FG Stock In/Out</a></li>\r\n");
      out.write("    <li><a href=\"FNG_S02.html\" >FG Stock In/Out History</a></li>\r\n");
      out.write("  </ul>\r\n");
      out.write("  \r\n");
      out.write("  ");
      if (_jspx_meth_page_005fmessage_005f0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\r\n");
      out.write("<form id=\"fngForm\" method=\"post\">\r\n");
      out.write("  <table width=\"100%\" border=\"0\" cellpadding=\"3\" cellspacing=\"1\">\r\n");
      out.write("    <tr>\r\n");
      out.write("      <td>\r\n");
      out.write("        <table class=\"ui-widget ui-widget-content \" cellpadding=\"3\" cellspacing=\"1\" width=\"100%\" border=\"0\">\r\n");
      out.write("          <tr>\r\n");
      out.write("         \t<th width=\"13%\">Report Date&nbsp;<span class=\"textred\">*</span></th>\r\n");
      out.write("            <td width=\"29%\">\r\n");
      out.write("            \t");
      if (_jspx_meth_form_005finput_005f0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("            </td> \r\n");
      out.write("            <th width=\"13%\">FG Stock Type&nbsp;<span class=\"textred\">*</span></th>\r\n");
      out.write("            <td width=\"32%\">\r\n");
      out.write("            \t");
      if (_jspx_meth_form_005fradiobutton_005f0(_jspx_page_context))
        return;
      out.write(" IN\r\n");
      out.write("            \t");
      if (_jspx_meth_form_005fradiobutton_005f1(_jspx_page_context))
        return;
      out.write(" OUT\r\n");
      out.write("            </td>\r\n");
      out.write("            <td align=\"right\">\r\n");
      out.write("              <input type=\"submit\" value=\"OK\" id=\"btnOk\" class=\"submit_button\" tabindex=\"4\"\r\n");
      out.write("              \t");
      if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("              />\r\n");
      out.write("            </td>       \r\n");
      out.write("          </tr>\r\n");
      out.write("        </table>\r\n");
      out.write("      </td>\r\n");
      out.write("    </tr>\r\n");
      out.write("  </table>\r\n");
      out.write("  <br />\r\n");
      out.write("  ");
      if (_jspx_meth_c_005fif_005f1(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("</form>\r\n");
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
      // /page/FNG/FNG_S01.jsp(20,4) name = code type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_spring_005fmessage_005f0.setCode("menu.FGStockIn/Out");
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

  private boolean _jspx_meth_page_005fmessage_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  page:message
    org.apache.jsp.tag.web.message_tag _jspx_th_page_005fmessage_005f0 = new org.apache.jsp.tag.web.message_tag();
    org.apache.jasper.runtime.AnnotationHelper.postConstruct(_jsp_annotationprocessor, _jspx_th_page_005fmessage_005f0);
    _jspx_th_page_005fmessage_005f0.setJspContext(_jspx_page_context);
    // /page/FNG/FNG_S01.jsp(26,2) name = item type = th.co.nttdata.tki.bean.AbstractBaseBean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = java.lang.String deferredMethod = false methodSignature = null
    _jspx_th_page_005fmessage_005f0.setItem((th.co.nttdata.tki.bean.AbstractBaseBean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${tfg}", th.co.nttdata.tki.bean.AbstractBaseBean.class, (PageContext)_jspx_page_context, null, false));
    _jspx_th_page_005fmessage_005f0.doTag();
    org.apache.jasper.runtime.AnnotationHelper.preDestroy(_jsp_annotationprocessor, _jspx_th_page_005fmessage_005f0);
    return false;
  }

  private boolean _jspx_meth_form_005finput_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  form:input
    org.springframework.web.servlet.tags.form.InputTag _jspx_th_form_005finput_005f0 = (org.springframework.web.servlet.tags.form.InputTag) _005fjspx_005ftagPool_005fform_005finput_0026_005ftabindex_005fsize_005fpath_005fid_005fcssClass_005fnobody.get(org.springframework.web.servlet.tags.form.InputTag.class);
    boolean _jspx_th_form_005finput_005f0_reused = false;
    try {
      _jspx_th_form_005finput_005f0.setPageContext(_jspx_page_context);
      _jspx_th_form_005finput_005f0.setParent(null);
      // /page/FNG/FNG_S01.jsp(36,13) name = path type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_form_005finput_005f0.setPath("tfg.reportDate");
      // /page/FNG/FNG_S01.jsp(36,13) name = id type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_form_005finput_005f0.setId("reportDate");
      // /page/FNG/FNG_S01.jsp(36,13) name = tabindex type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_form_005finput_005f0.setTabindex("2");
      // /page/FNG/FNG_S01.jsp(36,13) name = size type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_form_005finput_005f0.setSize("10");
      // /page/FNG/FNG_S01.jsp(36,13) name = cssClass type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_form_005finput_005f0.setCssClass("date");
      int[] _jspx_push_body_count_form_005finput_005f0 = new int[] { 0 };
      try {
        int _jspx_eval_form_005finput_005f0 = _jspx_th_form_005finput_005f0.doStartTag();
        if (_jspx_th_form_005finput_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
          return true;
        }
      } catch (Throwable _jspx_exception) {
        while (_jspx_push_body_count_form_005finput_005f0[0]-- > 0)
          out = _jspx_page_context.popBody();
        _jspx_th_form_005finput_005f0.doCatch(_jspx_exception);
      } finally {
        _jspx_th_form_005finput_005f0.doFinally();
      }
      _005fjspx_005ftagPool_005fform_005finput_0026_005ftabindex_005fsize_005fpath_005fid_005fcssClass_005fnobody.reuse(_jspx_th_form_005finput_005f0);
      _jspx_th_form_005finput_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_form_005finput_005f0, _jsp_annotationprocessor, _jspx_th_form_005finput_005f0_reused);
    }
    return false;
  }

  private boolean _jspx_meth_form_005fradiobutton_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  form:radiobutton
    org.springframework.web.servlet.tags.form.RadioButtonTag _jspx_th_form_005fradiobutton_005f0 = (org.springframework.web.servlet.tags.form.RadioButtonTag) _005fjspx_005ftagPool_005fform_005fradiobutton_0026_005fvalue_005ftabindex_005fpath_005fid_005fdisabled_005fchecked_005fnobody.get(org.springframework.web.servlet.tags.form.RadioButtonTag.class);
    boolean _jspx_th_form_005fradiobutton_005f0_reused = false;
    try {
      _jspx_th_form_005fradiobutton_005f0.setPageContext(_jspx_page_context);
      _jspx_th_form_005fradiobutton_005f0.setParent(null);
      // /page/FNG/FNG_S01.jsp(40,13) name = path type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_form_005fradiobutton_005f0.setPath("tfg.fgStockType");
      // /page/FNG/FNG_S01.jsp(40,13) name = disabled type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_form_005fradiobutton_005f0.setDisabled((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${tfg.fgStockType != null}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      // /page/FNG/FNG_S01.jsp(40,13) name = id type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_form_005fradiobutton_005f0.setId("rdiIn");
      // /page/FNG/FNG_S01.jsp(40,13) name = value type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_form_005fradiobutton_005f0.setValue(new String("fgin"));
      // /page/FNG/FNG_S01.jsp(40,13) name = tabindex type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_form_005fradiobutton_005f0.setTabindex("1");
      // /page/FNG/FNG_S01.jsp(40,13) null
      _jspx_th_form_005fradiobutton_005f0.setDynamicAttribute(null, "checked", new String("checked"));
      int[] _jspx_push_body_count_form_005fradiobutton_005f0 = new int[] { 0 };
      try {
        int _jspx_eval_form_005fradiobutton_005f0 = _jspx_th_form_005fradiobutton_005f0.doStartTag();
        if (_jspx_th_form_005fradiobutton_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
          return true;
        }
      } catch (Throwable _jspx_exception) {
        while (_jspx_push_body_count_form_005fradiobutton_005f0[0]-- > 0)
          out = _jspx_page_context.popBody();
        _jspx_th_form_005fradiobutton_005f0.doCatch(_jspx_exception);
      } finally {
        _jspx_th_form_005fradiobutton_005f0.doFinally();
      }
      _005fjspx_005ftagPool_005fform_005fradiobutton_0026_005fvalue_005ftabindex_005fpath_005fid_005fdisabled_005fchecked_005fnobody.reuse(_jspx_th_form_005fradiobutton_005f0);
      _jspx_th_form_005fradiobutton_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_form_005fradiobutton_005f0, _jsp_annotationprocessor, _jspx_th_form_005fradiobutton_005f0_reused);
    }
    return false;
  }

  private boolean _jspx_meth_form_005fradiobutton_005f1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  form:radiobutton
    org.springframework.web.servlet.tags.form.RadioButtonTag _jspx_th_form_005fradiobutton_005f1 = (org.springframework.web.servlet.tags.form.RadioButtonTag) _005fjspx_005ftagPool_005fform_005fradiobutton_0026_005fvalue_005fpath_005fid_005fdisabled_005fnobody.get(org.springframework.web.servlet.tags.form.RadioButtonTag.class);
    boolean _jspx_th_form_005fradiobutton_005f1_reused = false;
    try {
      _jspx_th_form_005fradiobutton_005f1.setPageContext(_jspx_page_context);
      _jspx_th_form_005fradiobutton_005f1.setParent(null);
      // /page/FNG/FNG_S01.jsp(41,13) name = path type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_form_005fradiobutton_005f1.setPath("tfg.fgStockType");
      // /page/FNG/FNG_S01.jsp(41,13) name = disabled type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_form_005fradiobutton_005f1.setDisabled((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${tfg.fgStockType != null}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      // /page/FNG/FNG_S01.jsp(41,13) name = id type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_form_005fradiobutton_005f1.setId("rdiIn");
      // /page/FNG/FNG_S01.jsp(41,13) name = value type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_form_005fradiobutton_005f1.setValue(new String("fgout"));
      int[] _jspx_push_body_count_form_005fradiobutton_005f1 = new int[] { 0 };
      try {
        int _jspx_eval_form_005fradiobutton_005f1 = _jspx_th_form_005fradiobutton_005f1.doStartTag();
        if (_jspx_th_form_005fradiobutton_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
          return true;
        }
      } catch (Throwable _jspx_exception) {
        while (_jspx_push_body_count_form_005fradiobutton_005f1[0]-- > 0)
          out = _jspx_page_context.popBody();
        _jspx_th_form_005fradiobutton_005f1.doCatch(_jspx_exception);
      } finally {
        _jspx_th_form_005fradiobutton_005f1.doFinally();
      }
      _005fjspx_005ftagPool_005fform_005fradiobutton_0026_005fvalue_005fpath_005fid_005fdisabled_005fnobody.reuse(_jspx_th_form_005fradiobutton_005f1);
      _jspx_th_form_005fradiobutton_005f1_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_form_005fradiobutton_005f1, _jsp_annotationprocessor, _jspx_th_form_005fradiobutton_005f1_reused);
    }
    return false;
  }

  private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f0 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    boolean _jspx_th_c_005fif_005f0_reused = false;
    try {
      _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
      _jspx_th_c_005fif_005f0.setParent(null);
      // /page/FNG/FNG_S01.jsp(45,15) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_c_005fif_005f0.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${not empty tfg.fgStockType}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
      int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
      if (_jspx_eval_c_005fif_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n");
          out.write("              \t\tdisabled=\"true\"\r\n");
          out.write("              \t");
          int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_c_005fif_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
      _jspx_th_c_005fif_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_c_005fif_005f0, _jsp_annotationprocessor, _jspx_th_c_005fif_005f0_reused);
    }
    return false;
  }

  private boolean _jspx_meth_c_005fif_005f1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f1 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    boolean _jspx_th_c_005fif_005f1_reused = false;
    try {
      _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
      _jspx_th_c_005fif_005f1.setParent(null);
      // /page/FNG/FNG_S01.jsp(56,2) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_c_005fif_005f1.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${not empty tfg.fgStockType}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
      int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
      if (_jspx_eval_c_005fif_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n");
          out.write("  <div>\r\n");
          out.write("    \t<span class=\"textred\">* Required Field</span>\r\n");
          out.write("        <table width=\"100%\" border=\"1\" align=\"center\" cellpadding=\"3\" cellspacing=\"0\" class=\"ui-widget ui-widget-content\"  id=\"dynamictbl\">\r\n");
          out.write("    \t  <tr>\r\n");
          out.write("    \t    <th rowspan=\"2\" width=\"4%\">\r\n");
          out.write("    \t      No.\r\n");
          out.write("    \t    </th>\r\n");
          out.write("    \t    <th rowspan=\"2\" width=\"12%\">\r\n");
          out.write("    \t      Lot Seq.  <span class=\"textred\">*</span><br/>(Lot 18 Digits)\r\n");
          out.write("   \t        </th>\r\n");
          out.write("    \t    <th rowspan=\"2\" width=\"20%\">\r\n");
          out.write("    \t      Customer <span class=\"textred\">*</span>\r\n");
          out.write("    \t    </th>\r\n");
          out.write("    \t    <th rowspan=\"2\" width=\"30%\">\r\n");
          out.write("    \t      FG No : FG Name <span class=\"textred\">*</span>\r\n");
          out.write("    \t    </th>\r\n");
          out.write("    \t    <th rowspan=\"2\" width=\"10%\">\r\n");
          out.write("    \t      Type <span class=\"textred\">*</span>\r\n");
          out.write("    \t    </th>\r\n");
          out.write("    \t    <th colspan=\"2\" width=\"16%\">\r\n");
          out.write("    \t      Stock FG <span class=\"textred\">*</span>\r\n");
          out.write("    \t    </th>\r\n");
          out.write("    \t    <th rowspan=\"2\" width=\"4%\">\r\n");
          out.write("    \t      Action\r\n");
          out.write("    \t    </th>\r\n");
          out.write("  \t      </tr>\r\n");
          out.write("          <tr>\r\n");
          out.write("            <th>\r\n");
          out.write("              In\r\n");
          out.write("            </th>\r\n");
          out.write("            <th>\r\n");
          out.write("              Out\r\n");
          out.write("            </th>\r\n");
          out.write("          </tr>\r\n");
          out.write("          <tr>\r\n");
          out.write("            <td align=\"center\" id=\"idx\">1.</td>\r\n");
          out.write("            <td>\r\n");
          out.write("              \t<input name=\"lotSeqNo\" type=\"text\" id=\"lotSeqNo\" onkeyup=\"checkscanlot(this)\" tabindex=\"18\" size=\"22\" title=\"Lot Sequence No.\" />\r\n");
          out.write("            \t<input name=\"tagId\" type=\"hidden\" id=\"tagId\" />\r\n");
          out.write("            </td>\r\n");
          out.write("            <td>\r\n");
          out.write("            \t<div align=\"left\" id=\"customerCode\">&nbsp;</div>\r\n");
          out.write("            \t<input type=\"hidden\" id=\"hideCustId\" name=\"hideCustId\"/>\r\n");
          out.write("            \t<input type=\"hidden\" id=\"hideCustCode\" name=\"hideCustCode\"/>\r\n");
          out.write("            </td>\r\n");
          out.write("            <td>\r\n");
          out.write("             \t<div align=\"left\" id=\"fgId\">&nbsp;</div>\r\n");
          out.write("             \t<input type=\"hidden\" id=\"hideFgId\" name=\"hideFgId\"/>\r\n");
          out.write("             \t<input type=\"hidden\" id=\"hideFgNo\" name=\"hideFgNo\"/>\r\n");
          out.write("            </td>\r\n");
          out.write("            <td>\r\n");
          out.write("             \t<select name=\"reportType\" id=\"reportType\" title=\"Type\">\r\n");
          out.write("\t                ");
          if (_jspx_meth_c_005fforEach_005f0(_jspx_th_c_005fif_005f1, _jspx_page_context))
            return true;
          out.write("\r\n");
          out.write("           \t   \t</select>  \r\n");
          out.write("            </td>\r\n");
          out.write("            <td align=\"center\">\r\n");
          out.write("              <input name=\"fgIn\" type=\"text\" id=\"fgIn\" tabindex=\"17\" maxlength=\"10\" size=\"5\" title=\"FG In/Out\" onkeypress=\"return fngIntegerFilter(event)\" onchange=\"getTotalFgIn()\" />\r\n");
          out.write("            </td>\r\n");
          out.write("            <td align=\"center\">\r\n");
          out.write("              <input name=\"fgOut\" type=\"text\" id=\"fgOut\" tabindex=\"17\" maxlength=\"10\" size=\"5\" title=\"FG In/Out\" onkeypress=\"return fngIntegerFilter(event)\" onchange=\"getTotalFgOut()\"/>\r\n");
          out.write("            </td>\r\n");
          out.write("            <td align=\"center\">\r\n");
          out.write("              <img id=\"delRow\" name=\"delRow\" src=\"image/icon/delete.gif\" width=\"16\" height=\"16\" class=\"delete\"/>\r\n");
          out.write("            </td>\r\n");
          out.write("          </tr>                    \r\n");
          out.write("        </table>\r\n");
          out.write("        <table width=\"100%\" border=\"1\" align=\"center\" cellpadding=\"3\" cellspacing=\"0\" class=\"ui-widget ui-widget-content\"  id=\"totaltbl\">\r\n");
          out.write("        \t<tr>\r\n");
          out.write("\t            <td align=\"right\" width=\"76%\">\r\n");
          out.write("\t             \t<b>Total</b>\r\n");
          out.write("\t            </td>\r\n");
          out.write("\t            <td align=\"center\"  width=\"8%\">\r\n");
          out.write("\t              <div id=\"sumFgIn\"  class=\"text-bold\">0</div>\r\n");
          out.write("\t            </td>\r\n");
          out.write("\t            <td align=\"center\"  width=\"8%\">\r\n");
          out.write("\t              <div id=\"sumFgOut\" class=\"text-bold\">0</div>\r\n");
          out.write("\t            </td>\r\n");
          out.write("\t            <td align=\"center\" width=\"4%\">\r\n");
          out.write("\t            </td>\r\n");
          out.write("        \t\r\n");
          out.write("        \t</tr>\r\n");
          out.write("        </table>\r\n");
          out.write("\t</div>\r\n");
          out.write("\t<br/>\r\n");
          out.write("\t<div align=\"right\">\r\n");
          out.write("\t\t<input name=\"btnAdd\" type=\"button\" class=\"submit_button\" id=\"btnAdd\" value=\"Add New Row\"/>\r\n");
          out.write("    \t<input name=\"btnSave\" type=\"button\" class=\"submit_button\" id=\"btnSave\" value=\"Save\"/>\r\n");
          out.write("\t</div>\r\n");
          out.write("  ");
          int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_c_005fif_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
      _jspx_th_c_005fif_005f1_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_c_005fif_005f1, _jsp_annotationprocessor, _jspx_th_c_005fif_005f1_reused);
    }
    return false;
  }

  private boolean _jspx_meth_c_005fforEach_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:forEach
    org.apache.taglibs.standard.tag.rt.core.ForEachTag _jspx_th_c_005fforEach_005f0 = (org.apache.taglibs.standard.tag.rt.core.ForEachTag) _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(org.apache.taglibs.standard.tag.rt.core.ForEachTag.class);
    boolean _jspx_th_c_005fforEach_005f0_reused = false;
    try {
      _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
      _jspx_th_c_005fforEach_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fif_005f1);
      // /page/FNG/FNG_S01.jsp(109,17) name = items type = javax.el.ValueExpression reqTime = true required = false fragment = false deferredValue = true expectedTypeName = java.lang.Object deferredMethod = false methodSignature = null
      _jspx_th_c_005fforEach_005f0.setItems(new org.apache.jasper.el.JspValueExpression("/page/FNG/FNG_S01.jsp(109,17) '${reportTypeList}'",_el_expressionfactory.createValueExpression(_jspx_page_context.getELContext(),"${reportTypeList}",java.lang.Object.class)).getValue(_jspx_page_context.getELContext()));
      // /page/FNG/FNG_S01.jsp(109,17) name = var type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_c_005fforEach_005f0.setVar("item");
      int[] _jspx_push_body_count_c_005fforEach_005f0 = new int[] { 0 };
      try {
        int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
        if (_jspx_eval_c_005fforEach_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
          do {
            out.write("\r\n");
            out.write("\t                  \t<option value=\"");
            out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.typeCode}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
            out.write("\" class=\"");
            out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.report}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
            out.write('"');
            out.write('>');
            out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.typeName}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
            out.write("</option>\r\n");
            out.write("\t                ");
            int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
            if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
              break;
          } while (true);
        }
        if (_jspx_th_c_005fforEach_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
          return true;
        }
      } catch (Throwable _jspx_exception) {
        while (_jspx_push_body_count_c_005fforEach_005f0[0]-- > 0)
          out = _jspx_page_context.popBody();
        _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
      } finally {
        _jspx_th_c_005fforEach_005f0.doFinally();
      }
      _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
      _jspx_th_c_005fforEach_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_c_005fforEach_005f0, _jsp_annotationprocessor, _jspx_th_c_005fforEach_005f0_reused);
    }
    return false;
  }
}

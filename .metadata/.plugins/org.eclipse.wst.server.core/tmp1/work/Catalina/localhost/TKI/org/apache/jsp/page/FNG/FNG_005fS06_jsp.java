package org.apache.jsp.page.FNG;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class FNG_005fS06_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(3);
    _jspx_dependants.add("/page/FNG/../importResourcesSlickgrid.jsp");
    _jspx_dependants.add("/page/FNG/../loadingResource.jsp");
    _jspx_dependants.add("/WEB-INF/tags/message.tag");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fspring_005fmessage_0026_005fcode_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fform_005fform_0026_005fmethod_005fid_005fcommandName_005faction;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fform_005fhidden_0026_005fpath_005fid_005fnobody;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fspring_005fmessage_0026_005fcode_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fform_005fform_0026_005fmethod_005fid_005fcommandName_005faction = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fform_005fhidden_0026_005fpath_005fid_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fspring_005fmessage_0026_005fcode_005fnobody.release();
    _005fjspx_005ftagPool_005fform_005fform_0026_005fmethod_005fid_005fcommandName_005faction.release();
    _005fjspx_005ftagPool_005fform_005fhidden_0026_005fpath_005fid_005fnobody.release();
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

      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \r\n");
      out.write("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<title></title>\r\n");
      out.write("\r\n");
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
      out.write("\t\r\n");
      out.write("<link href=\"css/jquery-ui-1.8.6.custom.css\" type=\"text/css\" rel=\"stylesheet\" />\t\r\n");
      out.write("<script src=\"script/plugin/standard_script.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script src=\"script/plugin/jquery-1.7.2.min.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script src=\"script/plugin/jquery-ui-1.8.6.custom.min.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href='script/plugin/slickgrid-2.0/slick.grid.css' media=\"screen\" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href='script/plugin/slickgrid-2.0/custom/common-slickgrid.css' media=\"screen\" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href='script/plugin/slickgrid-2.0/custom/epc-slickgrid.css' media=\"screen\" />\t\r\n");
      out.write("<script type=\"text/javascript\" src='script/plugin/slickgrid-2.0/lib/jquery.event.drag-2.0.min.js'></script>\r\n");
      out.write("<script type=\"text/javascript\" src='script/plugin/slickgrid-2.0/slick.core.js'></script>\r\n");
      out.write("<script type=\"text/javascript\" src='script/plugin/slickgrid-2.0/slick.editors.js'></script>\r\n");
      out.write("<script type=\"text/javascript\" src='script/plugin/slickgrid-2.0/slick.grid.js'></script>\r\n");
      out.write("<script type=\"text/javascript\" src='script/plugin/slickgrid-2.0/slick.dataview.js'></script>\r\n");
      out.write("<script type=\"text/javascript\" src='script/plugin/slickgrid-2.0/slick.formatters.js'></script>\r\n");
      out.write("<script type=\"text/javascript\" src='script/plugin/slickgrid-2.0/plugins/slick.checkboxselectcolumn.js'></script>\r\n");
      out.write("<script type=\"text/javascript\" src='script/plugin/slickgrid-2.0/plugins/slick.cellrangedecorator.js'></script>\r\n");
      out.write("<script type=\"text/javascript\" src='script/plugin/slickgrid-2.0/plugins/slick.cellrangeselector.js'></script>\r\n");
      out.write("<script type=\"text/javascript\" src='script/plugin/slickgrid-2.0/plugins/slick.cellselectionmodel.js'></script>\r\n");
      out.write("<script type=\"text/javascript\" src='script/plugin/slickgrid-2.0/plugins/slick.rowselectionmodel.js'></script>\r\n");
      out.write("<script type=\"text/javascript\" src='script/plugin/slickgrid-2.0/controls/slick.pager.js'></script>\r\n");
      out.write("<script type=\"text/javascript\" src='script/plugin/slickgrid-2.0/controls/slick.columnpicker.js'></script>\r\n");
      out.write("<script type=\"text/javascript\" src='script/plugin/slickgrid-2.0/custom/common-converter.js'></script>\r\n");
      out.write("<script type=\"text/javascript\" src='script/plugin/slickgrid-2.0/custom/common-validator.js'></script>\r\n");
      out.write("<script type=\"text/javascript\" src='script/plugin/slickgrid-2.0/custom/common-slickgrid.js'></script>\r\n");
      out.write("<script type=\"text/javascript\" src='script/plugin/slickgrid-2.0/custom/common-slickgrid.event.js'></script>\r\n");
      out.write("<script type=\"text/javascript\" src='script/plugin/slickgrid-2.0/custom/common-slickgrid.formatter.js'></script>\r\n");
      out.write("<script type=\"text/javascript\" src='script/plugin/slickgrid-2.0/custom/common-slickgrid.editor.js'></script>\r\n");
      out.write("<script type=\"text/javascript\" src='script/plugin/slickgrid-2.0/custom/common-slickgrid.validator.js'></script>\r\n");
      out.write("<script type=\"text/javascript\" src='script/jslib/common.js'></script>");
      out.write("\r\n");
      out.write("<script src=\"page/FNG/FNG_S06.js\" language=\"javascript\"></script>\r\n");
      out.write("<style>\r\n");
      out.write(".print-column span.slick-column-name:after {\r\n");
      out.write("\tcontent: \"Print\";\r\n");
      out.write("\tposition: absolute;\r\n");
      out.write("}\r\n");
      out.write("</style>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<h1 class=\"header\">");
      if (_jspx_meth_spring_005fmessage_005f0(_jspx_page_context))
        return;
      out.write("</h1>\r\n");
      out.write("\t");
      if (_jspx_meth_form_005fform_005f0(_jspx_page_context))
        return;
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
      // /page/FNG/FNG_S06.jsp(23,19) name = code type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_spring_005fmessage_005f0.setCode("menu.PrintTagLabel");
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

  private boolean _jspx_meth_form_005fform_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  form:form
    org.springframework.web.servlet.tags.form.FormTag _jspx_th_form_005fform_005f0 = (org.springframework.web.servlet.tags.form.FormTag) _005fjspx_005ftagPool_005fform_005fform_0026_005fmethod_005fid_005fcommandName_005faction.get(org.springframework.web.servlet.tags.form.FormTag.class);
    boolean _jspx_th_form_005fform_005f0_reused = false;
    try {
      _jspx_th_form_005fform_005f0.setPageContext(_jspx_page_context);
      _jspx_th_form_005fform_005f0.setParent(null);
      // /page/FNG/FNG_S06.jsp(24,1) name = id type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_form_005fform_005f0.setId("fngForm");
      // /page/FNG/FNG_S06.jsp(24,1) name = action type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_form_005fform_005f0.setAction("FNG_S06_preview.html");
      // /page/FNG/FNG_S06.jsp(24,1) name = commandName type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_form_005fform_005f0.setCommandName("searchCriteria");
      // /page/FNG/FNG_S06.jsp(24,1) name = method type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_form_005fform_005f0.setMethod("POST");
      int[] _jspx_push_body_count_form_005fform_005f0 = new int[] { 0 };
      try {
        int _jspx_eval_form_005fform_005f0 = _jspx_th_form_005fform_005f0.doStartTag();
        if (_jspx_eval_form_005fform_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
          do {
            out.write("\r\n");
            out.write("\t\r\n");
            out.write("\t\t<ul id=\"navlist\">\r\n");
            out.write("\t\t\t<li><a href=\"FNG_S06.html\" id=\"current\">Print Tag Label</a></li>\r\n");
            out.write("\t\t\t<li><a href=\"FNG_S07.html\">Print Tag Changed History</a></li>\r\n");
            out.write("\t\t</ul>\r\n");
            out.write("\t\r\n");
            out.write("\t\t");
            if (_jspx_meth_page_005fmessage_005f0(_jspx_th_form_005fform_005f0, _jspx_page_context, _jspx_push_body_count_form_005fform_005f0))
              return true;
            out.write("\r\n");
            out.write("\r\n");
            out.write("\t\t<input type=\"hidden\" id=\"tagId\" />\r\n");
            out.write("\t\t<input type=\"hidden\" id=\"hideLotNo\" />\r\n");
            out.write("\t\t<input type=\"hidden\" id=\"hideCustomerId\" />\r\n");
            out.write("\t\t<input type=\"hidden\" id=\"hideFgId\" />\r\n");
            out.write("\t\t<input type=\"hidden\" id=\"hideSnp\" />\r\n");
            out.write("\t\t<input type=\"hidden\" id=\"hideQty\" />\r\n");
            out.write("\t\t<input type=\"hidden\" id=\"hideLabelType\" />\r\n");
            out.write("\t\t<input type=\"hidden\" id=\"hideVendorCode\" />\r\n");
            out.write("\t\t<input type=\"hidden\" id=\"hideVendorFgNo\" />\r\n");
            out.write("\r\n");
            out.write("\t\t");
            if (_jspx_meth_form_005fhidden_005f0(_jspx_th_form_005fform_005f0, _jspx_page_context, _jspx_push_body_count_form_005fform_005f0))
              return true;
            out.write("\r\n");
            out.write("\r\n");
            out.write("\t\t<table width=\"100%\" border=\"0\" cellpadding=\"3\" cellspacing=\"1\">\r\n");
            out.write("\t\t\t<tr>\r\n");
            out.write("\t\t\t\t<td colspan=\"2\">\r\n");
            out.write("\t\t\t\t\t<table class=\"ui-widget ui-widget-content \" cellpadding=\"3\"\r\n");
            out.write("\t\t\t\t\t\tcellspacing=\"1\" width=\"100%\" border=\"0\">\r\n");
            out.write("\t\t\t\t\t\t<tr>\r\n");
            out.write("\t\t\t\t\t\t\t<th width=\"17%\"><span class=\"label\">Lot No.<span\r\n");
            out.write("\t\t\t\t\t\t\t\t\tclass=\"textred\">*</span></span></th>\r\n");
            out.write("\t\t\t\t\t\t\t<td width=\"35%\"><input type=\"text\" id=\"lotNo\"\r\n");
            out.write("\t\t\t\t\t\t\t\tclass=\"ui-autocomplete-input\" tabindex=\"12\" autocomplete=\"off\"\r\n");
            out.write("\t\t\t\t\t\t\t\trole=\"textbox\" aria-autocomplete=\"list\" aria-haspopup=\"true\" maxlength=\"14\" />\r\n");
            out.write("\t\t\t\t\t\t\t</td>\r\n");
            out.write("\t\t\t\t\t\t\t<th width=\"17%\"><span class=\"label\">Process</span></th>\r\n");
            out.write("\t\t\t\t\t\t\t<td><input type=\"text\" id=\"wip\" readonly=\"readonly\"\r\n");
            out.write("\t\t\t\t\t\t\t\tdisabled=\"disabled\" class=\"styleFontBold\" /></td>\r\n");
            out.write("\t\t\t\t\t\t</tr>\r\n");
            out.write("\t\t\t\t\t\t<tr>\r\n");
            out.write("\t\t\t\t\t\t\t<th width=\"17%\"><span class=\"label\">Customer</span><span\r\n");
            out.write("\t\t\t\t\t\t\t\tclass=\"textred\">*</span></th>\r\n");
            out.write("\t\t\t\t\t\t\t<td><select name=\"customerId\" id=\"customerId\">\r\n");
            out.write("\t\t\t\t\t\t\t</select></td>\r\n");
            out.write("\t\t\t\t\t\t\t<th width=\"17%\"><span class=\"label\">FG No : FG Name</span><span\r\n");
            out.write("\t\t\t\t\t\t\t\tclass=\"textred\">*</span></th>\r\n");
            out.write("\t\t\t\t\t\t\t<td><select name=\"fgNo\" id=\"fgId\">\r\n");
            out.write("\t\t\t\t\t\t\t</select></td>\r\n");
            out.write("\t\t\t\t\t\t\t\r\n");
            out.write("\t\t\t\t\t\t</tr>\r\n");
            out.write("\t\t\t\t\t\t\r\n");
            out.write("\t\t\t\t\t\t<tr>\r\n");
            out.write("\t\t\t\t\t\t\t<th width=\"17%\"><span class=\"label\">Mold Name</span></th>\r\n");
            out.write("\t\t\t\t\t\t\t<td><div id=\"mold\"></div>\r\n");
            out.write("\t\t\t\t\t\t\t</td>\r\n");
            out.write("\t\t\t\t\t\t\t<th width=\"17%\"><span class=\"label\">Mold No</span> </th>\r\n");
            out.write("\t\t\t\t\t\t\t<td><div id=\"moldNo\"></div> </td>\r\n");
            out.write("\t\t\t\t\t\t\t\r\n");
            out.write("\t\t\t\t\t\t</tr>\r\n");
            out.write("\t\t\t\t\t\t\r\n");
            out.write("\t\t\t\t\t\t<tr>\r\n");
            out.write("\t\t\t\t\t\t\t<th width=\"17%\"><span class=\"label\">SNP</span><span\r\n");
            out.write("\t\t\t\t\t\t\t\tclass=\"textred\">*</span></th>\r\n");
            out.write("\t\t\t\t\t\t\t<td><input type=\"text\" id=\"snp_wip\"\r\n");
            out.write("\t\t\t\t\t\t\t\tclass=\"styleFontBold posInt\" /></td>\r\n");
            out.write("\t\t\t\t\t\t\t<th width=\"17%\"><span class=\"label\">Qty</span><span\r\n");
            out.write("\t\t\t\t\t\t\t\tclass=\"textred\">*</span></th>\r\n");
            out.write("\t\t\t\t\t\t\t<td><input type=\"text\" id=\"qty\" class=\"styleFontBold posInt\" />\r\n");
            out.write("\t\t\t\t\t\t\t</td>\r\n");
            out.write("\t\t\t\t\t\t</tr>\r\n");
            out.write("\t\t\t\t\t\t<tr>\r\n");
            out.write("\t\t\t\t\t\t\t<th width=\"17%\"><span class=\"label\">Label Type</span><span\r\n");
            out.write("\t\t\t\t\t\t\t\tclass=\"textred\">*</span></th>\r\n");
            out.write("\t\t\t\t\t\t\t<td><input type=\"radio\"\r\n");
            out.write("\t\t\t\t\t\t\t\tname=\"labelType\" checked=\"checked\" value=\"N\"\r\n");
            out.write("\t\t\t\t\t\t\t\tid=\"NormalLabelType\" />Normal &nbsp; <input type=\"radio\"\r\n");
            out.write("\t\t\t\t\t\t\t\tname=\"labelType\" value=\"S\" id=\"SpecialLabelType\" />Special\r\n");
            out.write("\t\t\t\t\t\t\t</td>\r\n");
            out.write("\t\t\t\t\t\t\t<th width=\"17%\"><span class=\"label\">Print QTY remain</span></th>\r\n");
            out.write("\t\t\t\t\t\t\t<td><input type=\"text\" id=\"printQtyRemain\" class=\"styleFontBold\" disabled=\"disabled\" />\r\n");
            out.write("\t\t\t\t\t\t\t</td>\r\n");
            out.write("\t\t\t\t\t\t</tr>\r\n");
            out.write("\t\t\t\t\t\t<tr>\r\n");
            out.write("\t\t\t\t\t\t\t<th width=\"17%\"><span class=\"label\">Vendor Code</span><span\r\n");
            out.write("\t\t\t\t\t\t\t\tclass=\"textred\">*</span></th>\r\n");
            out.write("\t\t\t\t\t\t\t<td><input type=\"text\" id=\"vendorcode\" class=\"styleFontBold\" />\r\n");
            out.write("\t\t\t\t\t\t\t</td>\r\n");
            out.write("\t\t\t\t\t\t\t<th width=\"17%\"><span class=\"label\">Vendor FG No.</span><span\r\n");
            out.write("\t\t\t\t\t\t\t\tclass=\"textred\">*</span></th>\r\n");
            out.write("\t\t\t\t\t\t\t<td><input type=\"text\" id=\"vendorFgNo\"\r\n");
            out.write("\t\t\t\t\t\t\t\tclass=\"styleFontBold\" /></td>\r\n");
            out.write("\t\t\t\t\t\t</tr>\r\n");
            out.write("\t\t\t\t\t</table>\r\n");
            out.write("\t\t\t\t</td>\r\n");
            out.write("\t\t\t</tr>\r\n");
            out.write("\t\t\t<tr>\r\n");
            out.write("\t\t\t\t<td>\r\n");
            out.write("\t\t\t\t\t<div align=\"left\">For Vendor code and Vendor Partno is\r\n");
            out.write("\t\t\t\t\t\tmandatory only Label type = \"Special\"</div>\r\n");
            out.write("\t\t\t\t</td>\r\n");
            out.write("\t\t\t\t<td>\r\n");
            out.write("\t\t\t\t\t<div align=\"right\">\r\n");
            out.write("\t\t\t\t\t\t<input name=\"btnClear\" type=\"button\" \r\n");
            out.write("\t\t\t\t\t\t\tid=\"btnClear\" value=\"Clear\" />\r\n");
            out.write("\t\t\t\t\t\t<input name=\"btnPreview\" type=\"button\" class=\"submit_button\"\r\n");
            out.write("\t\t\t\t\t\t\tid=\"searchBtn\" value=\"Preview\" />\r\n");
            out.write("\t\t\t\t\t</div>\r\n");
            out.write("\t\t\t\t</td>\r\n");
            out.write("\t\t\t</tr>\r\n");
            out.write("\t\t</table>\r\n");
            out.write("\t\t<br />\r\n");
            out.write("\t\t<div id=\"itemInformationGrid\" class=\"grid-detail\"\r\n");
            out.write("\t\t\tstyle=\"width: 99.8%; height: 180px; overflow: hidden; outline: 0px none; position: relative;\"></div>\r\n");
            out.write("\t\t<br />\r\n");
            out.write("\t\t<div align=\"right\">\r\n");
            out.write("\t\t\t<input name=\"btnSave\" type=\"button\" class=\"submit_button\"\r\n");
            out.write("\t\t\t\tid=\"btnSavePrint\" value=\"Save And Print\" />\r\n");
            out.write("\t\t</div>\r\n");
            out.write("\t\t<br />\r\n");
            out.write("\t\t\r\n");
            out.write("\t\t<div id=\"printDialog\" title=\"Printer\" >\r\n");
            out.write("\t\t\t<div class=\"grid-header\">\r\n");
            out.write("\t\t\t\t<label>Select Printer</label> \r\n");
            out.write("\t\t\t</div>\r\n");
            out.write("\t\t\t<div>\r\n");
            out.write("\t\t\t\t<div align=\"center\" class=\"grid-detail\">\r\n");
            out.write("\t\t\t\t\t<table width=\"100%\">\r\n");
            out.write("\t\t\t\t<tr>\r\n");
            out.write("\t\t\t\t\t<td align=\"right\" width=\"30%\"><label>Printer</label> &nbsp;&nbsp;&nbsp; </td>\r\n");
            out.write("\t\t\t\t\t<td align=\"left\" width=\"70%\">\r\n");
            out.write("\t\t\t\t\t\t<select id=\"cboPrinter\"></select>\r\n");
            out.write("\t\t\t\t\t</td>\r\n");
            out.write("\t\t\t\t</tr>\r\n");
            out.write("\t\t\t\t</table>\r\n");
            out.write("\t\t\t\t</div>\r\n");
            out.write("\t\t\t\t<br/>\r\n");
            out.write("\t\t\t\t<div align=\"right\">\r\n");
            out.write("\t\t\t\t\t\t<input name=\"btnPrintDialog\" type=\"button\" class=\"submit_button\"\r\n");
            out.write("\t\t\t\t\t\t\tid=\"btnPrintDialog\" value=\"Print\" />\r\n");
            out.write("\t\t\t\t\t\t<input name=\"btnCancelDialog\" type=\"button\" class=\"submit_button\"\r\n");
            out.write("\t\t\t\t\t\t\tid=\"btnCancelDialog\" value=\"Cancel\" />\r\n");
            out.write("\t\t\t\t</div>\r\n");
            out.write("\t\t\t</div>\r\n");
            out.write("\t\t</div>\r\n");
            out.write("\t");
            int evalDoAfterBody = _jspx_th_form_005fform_005f0.doAfterBody();
            if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
              break;
          } while (true);
        }
        if (_jspx_th_form_005fform_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
          return true;
        }
      } catch (Throwable _jspx_exception) {
        while (_jspx_push_body_count_form_005fform_005f0[0]-- > 0)
          out = _jspx_page_context.popBody();
        _jspx_th_form_005fform_005f0.doCatch(_jspx_exception);
      } finally {
        _jspx_th_form_005fform_005f0.doFinally();
      }
      _005fjspx_005ftagPool_005fform_005fform_0026_005fmethod_005fid_005fcommandName_005faction.reuse(_jspx_th_form_005fform_005f0);
      _jspx_th_form_005fform_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_form_005fform_005f0, _jsp_annotationprocessor, _jspx_th_form_005fform_005f0_reused);
    }
    return false;
  }

  private boolean _jspx_meth_page_005fmessage_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_form_005fform_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_form_005fform_005f0)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  page:message
    org.apache.jsp.tag.web.message_tag _jspx_th_page_005fmessage_005f0 = new org.apache.jsp.tag.web.message_tag();
    org.apache.jasper.runtime.AnnotationHelper.postConstruct(_jsp_annotationprocessor, _jspx_th_page_005fmessage_005f0);
    _jspx_th_page_005fmessage_005f0.setJspContext(_jspx_page_context);
    _jspx_th_page_005fmessage_005f0.setParent(_jspx_th_form_005fform_005f0);
    // /page/FNG/FNG_S06.jsp(32,2) name = item type = th.co.nttdata.tki.bean.AbstractBaseBean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = java.lang.String deferredMethod = false methodSignature = null
    _jspx_th_page_005fmessage_005f0.setItem((th.co.nttdata.tki.bean.AbstractBaseBean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${bean}", th.co.nttdata.tki.bean.AbstractBaseBean.class, (PageContext)_jspx_page_context, null, false));
    _jspx_th_page_005fmessage_005f0.doTag();
    org.apache.jasper.runtime.AnnotationHelper.preDestroy(_jsp_annotationprocessor, _jspx_th_page_005fmessage_005f0);
    return false;
  }

  private boolean _jspx_meth_form_005fhidden_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_form_005fform_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_form_005fform_005f0)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  form:hidden
    org.springframework.web.servlet.tags.form.HiddenInputTag _jspx_th_form_005fhidden_005f0 = (org.springframework.web.servlet.tags.form.HiddenInputTag) _005fjspx_005ftagPool_005fform_005fhidden_0026_005fpath_005fid_005fnobody.get(org.springframework.web.servlet.tags.form.HiddenInputTag.class);
    boolean _jspx_th_form_005fhidden_005f0_reused = false;
    try {
      _jspx_th_form_005fhidden_005f0.setPageContext(_jspx_page_context);
      _jspx_th_form_005fhidden_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_form_005fform_005f0);
      // /page/FNG/FNG_S06.jsp(44,2) name = path type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_form_005fhidden_005f0.setPath("printerName");
      // /page/FNG/FNG_S06.jsp(44,2) name = id type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_form_005fhidden_005f0.setId("printerName");
      int[] _jspx_push_body_count_form_005fhidden_005f0 = new int[] { 0 };
      try {
        int _jspx_eval_form_005fhidden_005f0 = _jspx_th_form_005fhidden_005f0.doStartTag();
        if (_jspx_th_form_005fhidden_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
          return true;
        }
      } catch (Throwable _jspx_exception) {
        while (_jspx_push_body_count_form_005fhidden_005f0[0]-- > 0)
          out = _jspx_page_context.popBody();
        _jspx_th_form_005fhidden_005f0.doCatch(_jspx_exception);
      } finally {
        _jspx_th_form_005fhidden_005f0.doFinally();
      }
      _005fjspx_005ftagPool_005fform_005fhidden_0026_005fpath_005fid_005fnobody.reuse(_jspx_th_form_005fhidden_005f0);
      _jspx_th_form_005fhidden_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_form_005fhidden_005f0, _jsp_annotationprocessor, _jspx_th_form_005fhidden_005f0_reused);
    }
    return false;
  }
}

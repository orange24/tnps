package org.apache.jsp.page.ADM;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class BAT_005fS01_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(3);
    _jspx_dependants.add("/page/ADM/../importResources.jsp");
    _jspx_dependants.add("/page/ADM/../loadingResource.jsp");
    _jspx_dependants.add("/WEB-INF/tags/message.tag");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fform_005fform_0026_005fmethod_005fid_005fcommandName_005faction;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fspring_005fmessage_0026_005fcode_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fform_005fselect_0026_005fpath_005fitems_005fid_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fform_005finput_0026_005ftitle_005ftabindex_005fpath_005fid_005fcssClass_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fform_005fform_0026_005fmethod_005fid_005fcommandName_005faction = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fspring_005fmessage_0026_005fcode_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fform_005fselect_0026_005fpath_005fitems_005fid_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fform_005finput_0026_005ftitle_005ftabindex_005fpath_005fid_005fcssClass_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fc_005fchoose = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fform_005fform_0026_005fmethod_005fid_005fcommandName_005faction.release();
    _005fjspx_005ftagPool_005fspring_005fmessage_0026_005fcode_005fnobody.release();
    _005fjspx_005ftagPool_005fform_005fselect_0026_005fpath_005fitems_005fid_005fnobody.release();
    _005fjspx_005ftagPool_005fform_005finput_0026_005ftitle_005ftabindex_005fpath_005fid_005fcssClass_005fnobody.release();
    _005fjspx_005ftagPool_005fc_005fchoose.release();
    _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
    _005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.release();
    _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
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
      out.write("\r\n");
      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<title></title>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\r\n");
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
      out.write("\tvar executeDate;\r\n");
      out.write("\tvar batS01Form;\r\n");
      out.write("\tvar selectBatch ;\r\n");
      out.write("\tvar finishDateTime;\r\n");
      out.write("\tvar runBy;\r\n");
      out.write("\tvar status;\r\n");
      out.write("\tvar btnRun;\r\n");
      out.write("\t\r\n");
      out.write("\t$(document).ready(function(){\r\n");
      out.write("\t\texecuteDate\t\t= $(\"#executeDate\");\r\n");
      out.write("\t\tbatS01Form\t\t= $(\"#batS01Form\");\r\n");
      out.write("\t\tselectBatch \t= $(\"select#selectBatch\");\r\n");
      out.write("\t\tfinishDateTime \t= $(\"#finishDateTime\");\r\n");
      out.write("\t\trunBy\t\t\t= $(\"#runBy\");\r\n");
      out.write("\t\tstatus\t\t\t= $(\"#status\");\r\n");
      out.write("\t\tbtnRun\t\t\t= $(\"#btnRun\");\t\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tgetBatchStatus();\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tselectBatch.change(function(){\r\n");
      out.write("\t\t\tpostJSON(\"BAT_S01_Batch\", { \"batchId\": selectBatch.val() }, function( result ){\t\r\n");
      out.write("\t\t\t\tif(result.finishDate){\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\tvar date        = new Date(result.finishDate);\r\n");
      out.write("\t\t\t\t\tvar dateMonth   = date.getMonth() + 1;\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\tvar month\t\t= ((dateMonth+'').length === 1 ? \"0\" : \"\") + (dateMonth);\r\n");
      out.write("\t\t\t\t\tvar day\t\t\t= ((date.getDate()+'').length === 1 ? \"0\" : \"\")+ date.getDate();\r\n");
      out.write("\t\t\t\t\tvar hour\t\t= ((date.getHours()+'').length === 1 ? \"0\" : \"\")+ date.getHours();\r\n");
      out.write("\t\t\t\t\tvar min\t\t\t= ((date.getMinutes()+'').length === 1 ? \"0\" : \"\")+ date.getMinutes();\r\n");
      out.write("\t\t\t\t\tvar sec\t\t\t= ((date.getSeconds()+'').length === 1 ? \"0\" : \"\")+ date.getSeconds();\r\n");
      out.write("\t\t\t\t\tvar finishDate  = day +\"/\"+ month +\"/\"+ date.getFullYear() +\" \"+ hour +\":\"+ min +\":\"+ sec;\r\n");
      out.write("\t\t\t\t\tfinishDateTime.html(finishDate);\r\n");
      out.write("\t\t\t\t}else{\r\n");
      out.write("\t\t\t\t\tfinishDateTime.html(\"\");\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t\tvar batchStatus = result.batchStatus;\t\t\t\t\r\n");
      out.write("\t\t\t\tif(batchStatus === 0){\r\n");
      out.write("\t\t\t\t\tstatus.html(\"<strong class='textgreen'>Success</strong>\");\r\n");
      out.write("\t\t\t\t\tbtnRun.attr(\"disabled\",false);\r\n");
      out.write("\t\t\t\t}else if(batchStatus === 1){\r\n");
      out.write("\t\t\t\t\tstatus.html(\"<strong class='textblue'>Running...</strong>\");\r\n");
      out.write("\t\t\t\t\tbtnRun.attr(\"disabled\",true);\r\n");
      out.write("\t\t\t\t}else if(batchStatus === 2){\r\n");
      out.write("\t\t\t\t\tstatus.html(\"<strong class='textred'>Fail</strong>\");\r\n");
      out.write("\t\t\t\t\tbtnRun.attr(\"disabled\",false);\r\n");
      out.write("\t\t\t\t}else if(batchStatus === 3){\r\n");
      out.write("\t\t\t\t\tstatus.html(\"<strong class='textorange'>Success with error.</strong>\");\r\n");
      out.write("\t\t\t\t\tbtnRun.attr(\"disabled\",false);\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t\tvar resultRunBy = result.runBy;\r\n");
      out.write("\t\t\t\trunBy.html(resultRunBy);\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t});\r\n");
      out.write("\t\tbtnRun.click(function(){\r\n");
      out.write("\t\t\tif($(\"#executeDate\").datepicker(\"getDate\") == null){\r\n");
      out.write("\t\t\t\talert('Please select date to run batch');\r\n");
      out.write("\t\t\t\treturn;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\tif(confirm(\"Confirm to Run Batch\")){\r\n");
      out.write("\t\t\t\tbatS01Form.attr(\"action\",\"BAT_S01_run.html\");\r\n");
      out.write("\t\t\t\tbatS01Form.submit();\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t});\t\r\n");
      out.write("\t\tsetInterval(function(){\r\n");
      out.write("\t\t\tif( $(\"#status:contains('Running')\").length > 0 ){\r\n");
      out.write("\t\t\t\tpostJSON(\"BAT_S01_Batch\", { \"batchId\": selectBatch.val() }, function( result ){\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\tvar date        = new Date(result.finishDate);\r\n");
      out.write("\t\t\t\t\tvar dateMonth   = date.getMonth() + 1;\r\n");
      out.write("\t\t\t\t\tvar batchStatus = result.batchStatus;\r\n");
      out.write("\t\t\t\t\tvar resultRunBy = result.runBy;\t\t\t\t\r\n");
      out.write("\t\t\t\t\tvar month\t\t= ((dateMonth+'').length === 1 ? \"0\" : \"\") + (dateMonth);\r\n");
      out.write("\t\t\t\t\tvar day\t\t\t= ((date.getDate()+'').length === 1 ? \"0\" : \"\")+ date.getDate();\r\n");
      out.write("\t\t\t\t\tvar hour\t\t= ((date.getHours()+'').length === 1 ? \"0\" : \"\")+ date.getHours();\r\n");
      out.write("\t\t\t\t\tvar min\t\t\t= ((date.getMinutes()+'').length === 1 ? \"0\" : \"\")+ date.getMinutes();\r\n");
      out.write("\t\t\t\t\tvar sec\t\t\t= ((date.getSeconds()+'').length === 1 ? \"0\" : \"\")+ date.getSeconds();\r\n");
      out.write("\t\t\t\t\tvar finishDate  = day +\"/\"+ month +\"/\"+ date.getFullYear() +\" \"+ hour +\":\"+ min +\":\"+ sec;\t\t\t\t\r\n");
      out.write("\t\t\t\t\tif(batchStatus === 0){\r\n");
      out.write("\t\t\t\t\t\tstatus.html(\"<strong class='textgreen'>Success</strong>\");\r\n");
      out.write("\t\t\t\t\t\tbtnRun.attr(\"disabled\",false);\r\n");
      out.write("\t\t\t\t\t}else if(batchStatus === 1){\r\n");
      out.write("\t\t\t\t\t\tstatus.html(\"<strong class='textblue'>Running...</strong>\");\r\n");
      out.write("\t\t\t\t\t\tbtnRun.attr(\"disabled\",true);\r\n");
      out.write("\t\t\t\t\t}else if(batchStatus === 2){\r\n");
      out.write("\t\t\t\t\t\tstatus.html(\"<strong class='textred'>Fail</strong>\");\r\n");
      out.write("\t\t\t\t\t\tbtnRun.attr(\"disabled\",false);\r\n");
      out.write("\t\t\t\t\t}else if(batchStatus === 3){\r\n");
      out.write("\t\t\t\t\t\tstatus.html(\"<strong class='textorange'>Success with error.</strong>\");\r\n");
      out.write("\t\t\t\t\t\tbtnRun.attr(\"disabled\",false);\r\n");
      out.write("\t\t\t\t\t}\t\t\t\t\r\n");
      out.write("\t\t\t\t\tfinishDateTime.html(finishDate);\r\n");
      out.write("\t\t\t\t\trunBy.html(resultRunBy);\t\t\t\t\r\n");
      out.write("\t\t\t\t});\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\tgetBatchStatus();\r\n");
      out.write("\t\t}, 30000);\t\r\n");
      out.write("\r\n");
      out.write("\t\tif(!$(\"#executeDate\").val()){\r\n");
      out.write("\t\t\t$(\"#executeDate\").datepicker( \"setDate\" , new Date() );\r\n");
      out.write("\t\t}\r\n");
      out.write("\t});\t\r\n");
      out.write("\r\n");
      out.write("\tfunction getBatchStatus() {\r\n");
      out.write("\t\t// clear table status\r\n");
      out.write("\t\t$(\"#tableStatus tr:not(#rowHeader)\").remove();\r\n");
      out.write("\t\t//Get status of all batch to display status\r\n");
      out.write("\t\tpostJSON(\"BAT_S01_getBatchStatus\", function(result) {\r\n");
      out.write("\t\t\tif (result.length > 0) {\r\n");
      out.write("\t\t\t\t$.each(result, function(rowIndex, data) {\r\n");
      out.write("\t\t\t\t\t$('#tableStatus tr:last')\r\n");
      out.write("\t\t\t\t\t\t\t.after(\r\n");
      out.write("\t\t\t\t\t\t\t\t\t'<tr><td>'\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t+ data.batchName\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t+ '</td><td>'\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t+ getStatusLabel(data.batchStatus)\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t+ '</td></tr>');\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\tif (data.batchId == selectBatch.val()) {\r\n");
      out.write("\t\t\t\t\t\tif (data.batchStatus === 0) {\r\n");
      out.write("\t\t\t\t\t\t\tstatus.html(\"<strong class='textgreen'>Success</strong>\");\r\n");
      out.write("\t\t\t\t\t\t\tbtnRun.attr(\"disabled\", false);\r\n");
      out.write("\t\t\t\t\t\t} else if (data.batchStatus === 1) {\r\n");
      out.write("\t\t\t\t\t\t\tstatus.html(\"<strong class='textblue'>Running...</strong>\");\r\n");
      out.write("\t\t\t\t\t\t\tbtnRun.attr(\"disabled\", true);\r\n");
      out.write("\t\t\t\t\t\t} else if (data.batchStatus === 2) {\r\n");
      out.write("\t\t\t\t\t\t\tstatus.html(\"<strong class='textred'>Fail</strong>\");\r\n");
      out.write("\t\t\t\t\t\t\tbtnRun.attr(\"disabled\", false);\r\n");
      out.write("\t\t\t\t\t\t} else if (data.batchStatus === 3) {\r\n");
      out.write("\t\t\t\t\t\t\tstatus.html(\"<strong class='textorange'>Success with error.</strong>\");\r\n");
      out.write("\t\t\t\t\t\t\tbtnRun.attr(\"disabled\", false);\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t});\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t$('#tableStatus').show();\r\n");
      out.write("\r\n");
      out.write("\t\t\t} else {\r\n");
      out.write("\t\t\t\t$('#tableStatus').hide();\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t});\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction getStatusLabel(batchStatus) {\r\n");
      out.write("\t\tvar result = \"\";\r\n");
      out.write("\t\tif (batchStatus === 0) {\r\n");
      out.write("\t\t\tresult = \"<strong class='textgreen'>Success</strong>\";\r\n");
      out.write("\t\t} else if (batchStatus === 1) {\r\n");
      out.write("\t\t\tresult = \"<strong class='textblue'>Running...</strong>\";\r\n");
      out.write("\t\t} else if (batchStatus === 2) {\r\n");
      out.write("\t\t\tresult = \"<strong class='textred'>Fail</strong>\";\r\n");
      out.write("\t\t} else if (batchStatus === 3) {\r\n");
      out.write("\t\t\tresult = \"<strong class='textorange'>Success with error.</strong>\";\r\n");
      out.write("\t\t}\r\n");
      out.write("\r\n");
      out.write("\t\treturn result;\r\n");
      out.write("\t}\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\t\r\n");
      out.write("\t");
      if (_jspx_meth_form_005fform_005f0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("</body>\r\n");
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
      // /page/ADM/BAT_S01.jsp(176,1) name = method type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_form_005fform_005f0.setMethod("post");
      // /page/ADM/BAT_S01.jsp(176,1) name = id type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_form_005fform_005f0.setId("batS01Form");
      // /page/ADM/BAT_S01.jsp(176,1) name = commandName type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_form_005fform_005f0.setCommandName("batch");
      // /page/ADM/BAT_S01.jsp(176,1) name = action type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_form_005fform_005f0.setAction("BAT_S01.html");
      int[] _jspx_push_body_count_form_005fform_005f0 = new int[] { 0 };
      try {
        int _jspx_eval_form_005fform_005f0 = _jspx_th_form_005fform_005f0.doStartTag();
        if (_jspx_eval_form_005fform_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
          do {
            out.write("\r\n");
            out.write("\t<h1 class=\"header\">");
            if (_jspx_meth_spring_005fmessage_005f0(_jspx_th_form_005fform_005f0, _jspx_page_context, _jspx_push_body_count_form_005fform_005f0))
              return true;
            out.write("</h1>\r\n");
            out.write("\t\t");
            if (_jspx_meth_page_005fmessage_005f0(_jspx_th_form_005fform_005f0, _jspx_page_context, _jspx_push_body_count_form_005fform_005f0))
              return true;
            out.write("\r\n");
            out.write("\t\t<table cellpadding=\"3\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"ui-widget ui-widget-content\">\r\n");
            out.write("\t\t\t<tr>\t\t      \t\r\n");
            out.write("\t\t\t\t<th width=\"12%\" class=\"label\">Batch Name</th>\r\n");
            out.write("\t\t        <td width=\"35%\" >");
            if (_jspx_meth_form_005fselect_005f0(_jspx_th_form_005fform_005f0, _jspx_page_context, _jspx_push_body_count_form_005fform_005f0))
              return true;
            out.write("</td>\r\n");
            out.write("\t\t\t</tr>\r\n");
            out.write("\t\t\t<tr>\t\t      \t\r\n");
            out.write("\t\t\t\t<th width=\"12%\" class=\"label\">Execute date</th>\r\n");
            out.write("\t\t        <td width=\"35%\">");
            if (_jspx_meth_form_005finput_005f0(_jspx_th_form_005fform_005f0, _jspx_page_context, _jspx_push_body_count_form_005fform_005f0))
              return true;
            out.write("</td>\r\n");
            out.write("\t\t\t</tr>\r\n");
            out.write("\t\t\t<tr>\t\t      \t\r\n");
            out.write("\t\t\t\t<th width=\"12%\" class=\"label\">Batch Status</th>\r\n");
            out.write("\t\t        <td width=\"35%\" id=\"status\">\r\n");
            out.write("\t\t\t        ");
            if (_jspx_meth_c_005fchoose_005f0(_jspx_th_form_005fform_005f0, _jspx_page_context, _jspx_push_body_count_form_005fform_005f0))
              return true;
            out.write("\t\t\t    \r\n");
            out.write("\t\t\t\t</td>\r\n");
            out.write("\t\t\t</tr>\r\n");
            out.write("\t\t\t<tr>\t\t      \t\r\n");
            out.write("\t\t\t\t<th width=\"12%\" class=\"label\">Last Run Time</th>\r\n");
            out.write("\t\t        <td width=\"35%\"><div id=\"finishDateTime\">");
            if (_jspx_meth_fmt_005fformatDate_005f0(_jspx_th_form_005fform_005f0, _jspx_page_context, _jspx_push_body_count_form_005fform_005f0))
              return true;
            out.write("</div></td>\r\n");
            out.write("\t\t\t</tr>\r\n");
            out.write("\t\t\t<tr>\t\t      \t\r\n");
            out.write("\t\t\t\t<th width=\"12%\" class=\"label\">Last Run By</th>\r\n");
            out.write("\t\t        <td width=\"35%\" id=\"runBy\">");
            if (_jspx_meth_c_005fout_005f0(_jspx_th_form_005fform_005f0, _jspx_page_context, _jspx_push_body_count_form_005fform_005f0))
              return true;
            out.write("</td>\r\n");
            out.write("\t\t\t</tr>\r\n");
            out.write("\t\t</table>\r\n");
            out.write("\t\t<table width=\"100%\">\r\n");
            out.write("\t\t\t<tr>\r\n");
            out.write("\t\t\t\t<td align=\"right\">\r\n");
            out.write("\t\t\t\t\t");
            if (_jspx_meth_c_005fif_005f0(_jspx_th_form_005fform_005f0, _jspx_page_context, _jspx_push_body_count_form_005fform_005f0))
              return true;
            out.write("\r\n");
            out.write("\t\t\t\t\t");
            if (_jspx_meth_c_005fif_005f1(_jspx_th_form_005fform_005f0, _jspx_page_context, _jspx_push_body_count_form_005fform_005f0))
              return true;
            out.write("\r\n");
            out.write("  \t\t\t\t</td>\r\n");
            out.write("\t\t\t</tr>\r\n");
            out.write("\t\t</table>\r\n");
            out.write("\r\n");
            out.write("\t\t<table id=\"tableStatus\" width=\"40%\" border=\"1\" cellpadding=\"3\" cellspacing=\"1\" class=\"ui-widget ui-widget-content\"\r\n");
            out.write("\t\t\tstyle=\"display: none\">\r\n");
            out.write("\t\t\t<tr id=\"rowHeader\">\r\n");
            out.write("\t\t\t\t<th>Batch Name</th>\r\n");
            out.write("\t\t\t\t<th>Batch Status</th>\r\n");
            out.write("\t\t\t</tr>\r\n");
            out.write("\t\t</table>\r\n");
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

  private boolean _jspx_meth_spring_005fmessage_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_form_005fform_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_form_005fform_005f0)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  spring:message
    org.springframework.web.servlet.tags.MessageTag _jspx_th_spring_005fmessage_005f0 = (org.springframework.web.servlet.tags.MessageTag) _005fjspx_005ftagPool_005fspring_005fmessage_0026_005fcode_005fnobody.get(org.springframework.web.servlet.tags.MessageTag.class);
    boolean _jspx_th_spring_005fmessage_005f0_reused = false;
    try {
      _jspx_th_spring_005fmessage_005f0.setPageContext(_jspx_page_context);
      _jspx_th_spring_005fmessage_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_form_005fform_005f0);
      // /page/ADM/BAT_S01.jsp(177,20) name = code type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_spring_005fmessage_005f0.setCode("menu.Batch");
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

  private boolean _jspx_meth_page_005fmessage_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_form_005fform_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_form_005fform_005f0)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  page:message
    org.apache.jsp.tag.web.message_tag _jspx_th_page_005fmessage_005f0 = new org.apache.jsp.tag.web.message_tag();
    org.apache.jasper.runtime.AnnotationHelper.postConstruct(_jsp_annotationprocessor, _jspx_th_page_005fmessage_005f0);
    _jspx_th_page_005fmessage_005f0.setJspContext(_jspx_page_context);
    _jspx_th_page_005fmessage_005f0.setParent(_jspx_th_form_005fform_005f0);
    // /page/ADM/BAT_S01.jsp(178,2) name = item type = th.co.nttdata.tki.bean.AbstractBaseBean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = java.lang.String deferredMethod = false methodSignature = null
    _jspx_th_page_005fmessage_005f0.setItem((th.co.nttdata.tki.bean.AbstractBaseBean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${batch}", th.co.nttdata.tki.bean.AbstractBaseBean.class, (PageContext)_jspx_page_context, null, false));
    _jspx_th_page_005fmessage_005f0.doTag();
    org.apache.jasper.runtime.AnnotationHelper.preDestroy(_jsp_annotationprocessor, _jspx_th_page_005fmessage_005f0);
    return false;
  }

  private boolean _jspx_meth_form_005fselect_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_form_005fform_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_form_005fform_005f0)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  form:select
    org.springframework.web.servlet.tags.form.SelectTag _jspx_th_form_005fselect_005f0 = (org.springframework.web.servlet.tags.form.SelectTag) _005fjspx_005ftagPool_005fform_005fselect_0026_005fpath_005fitems_005fid_005fnobody.get(org.springframework.web.servlet.tags.form.SelectTag.class);
    boolean _jspx_th_form_005fselect_005f0_reused = false;
    try {
      _jspx_th_form_005fselect_005f0.setPageContext(_jspx_page_context);
      _jspx_th_form_005fselect_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_form_005fform_005f0);
      // /page/ADM/BAT_S01.jsp(182,27) name = id type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_form_005fselect_005f0.setId("selectBatch");
      // /page/ADM/BAT_S01.jsp(182,27) name = path type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_form_005fselect_005f0.setPath("batchId");
      // /page/ADM/BAT_S01.jsp(182,27) name = items type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_form_005fselect_005f0.setItems((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${bacthMap}", java.lang.Object.class, (PageContext)_jspx_page_context, null, false));
      int[] _jspx_push_body_count_form_005fselect_005f0 = new int[] { 0 };
      try {
        int _jspx_eval_form_005fselect_005f0 = _jspx_th_form_005fselect_005f0.doStartTag();
        if (_jspx_th_form_005fselect_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
          return true;
        }
      } catch (Throwable _jspx_exception) {
        while (_jspx_push_body_count_form_005fselect_005f0[0]-- > 0)
          out = _jspx_page_context.popBody();
        _jspx_th_form_005fselect_005f0.doCatch(_jspx_exception);
      } finally {
        _jspx_th_form_005fselect_005f0.doFinally();
      }
      _005fjspx_005ftagPool_005fform_005fselect_0026_005fpath_005fitems_005fid_005fnobody.reuse(_jspx_th_form_005fselect_005f0);
      _jspx_th_form_005fselect_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_form_005fselect_005f0, _jsp_annotationprocessor, _jspx_th_form_005fselect_005f0_reused);
    }
    return false;
  }

  private boolean _jspx_meth_form_005finput_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_form_005fform_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_form_005fform_005f0)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  form:input
    org.springframework.web.servlet.tags.form.InputTag _jspx_th_form_005finput_005f0 = (org.springframework.web.servlet.tags.form.InputTag) _005fjspx_005ftagPool_005fform_005finput_0026_005ftitle_005ftabindex_005fpath_005fid_005fcssClass_005fnobody.get(org.springframework.web.servlet.tags.form.InputTag.class);
    boolean _jspx_th_form_005finput_005f0_reused = false;
    try {
      _jspx_th_form_005finput_005f0.setPageContext(_jspx_page_context);
      _jspx_th_form_005finput_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_form_005fform_005f0);
      // /page/ADM/BAT_S01.jsp(186,26) name = title type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_form_005finput_005f0.setTitle("Execute date");
      // /page/ADM/BAT_S01.jsp(186,26) name = path type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_form_005finput_005f0.setPath("executeDate");
      // /page/ADM/BAT_S01.jsp(186,26) name = id type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_form_005finput_005f0.setId("executeDate");
      // /page/ADM/BAT_S01.jsp(186,26) name = cssClass type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_form_005finput_005f0.setCssClass("date");
      // /page/ADM/BAT_S01.jsp(186,26) name = tabindex type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_form_005finput_005f0.setTabindex("3");
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
      _005fjspx_005ftagPool_005fform_005finput_0026_005ftitle_005ftabindex_005fpath_005fid_005fcssClass_005fnobody.reuse(_jspx_th_form_005finput_005f0);
      _jspx_th_form_005finput_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_form_005finput_005f0, _jsp_annotationprocessor, _jspx_th_form_005finput_005f0_reused);
    }
    return false;
  }

  private boolean _jspx_meth_c_005fchoose_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_form_005fform_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_form_005fform_005f0)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:choose
    org.apache.taglibs.standard.tag.common.core.ChooseTag _jspx_th_c_005fchoose_005f0 = (org.apache.taglibs.standard.tag.common.core.ChooseTag) _005fjspx_005ftagPool_005fc_005fchoose.get(org.apache.taglibs.standard.tag.common.core.ChooseTag.class);
    boolean _jspx_th_c_005fchoose_005f0_reused = false;
    try {
      _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
      _jspx_th_c_005fchoose_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_form_005fform_005f0);
      int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
      if (_jspx_eval_c_005fchoose_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n");
          out.write("\t\t\t        \t");
          if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context, _jspx_push_body_count_form_005fform_005f0))
            return true;
          out.write("\r\n");
          out.write("\t\t\t\t\t    ");
          if (_jspx_meth_c_005fwhen_005f1(_jspx_th_c_005fchoose_005f0, _jspx_page_context, _jspx_push_body_count_form_005fform_005f0))
            return true;
          out.write("\r\n");
          out.write("\t\t\t\t\t    ");
          if (_jspx_meth_c_005fwhen_005f2(_jspx_th_c_005fchoose_005f0, _jspx_page_context, _jspx_push_body_count_form_005fform_005f0))
            return true;
          out.write("\t\t\t\t    \r\n");
          out.write("\t\t\t\t\t    ");
          if (_jspx_meth_c_005fwhen_005f3(_jspx_th_c_005fchoose_005f0, _jspx_page_context, _jspx_push_body_count_form_005fform_005f0))
            return true;
          out.write("\r\n");
          out.write("\t\t\t\t\t");
          int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_c_005fchoose_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
      _jspx_th_c_005fchoose_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_c_005fchoose_005f0, _jsp_annotationprocessor, _jspx_th_c_005fchoose_005f0_reused);
    }
    return false;
  }

  private boolean _jspx_meth_c_005fwhen_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_form_005fform_005f0)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:when
    org.apache.taglibs.standard.tag.rt.core.WhenTag _jspx_th_c_005fwhen_005f0 = (org.apache.taglibs.standard.tag.rt.core.WhenTag) _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.WhenTag.class);
    boolean _jspx_th_c_005fwhen_005f0_reused = false;
    try {
      _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
      _jspx_th_c_005fwhen_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fchoose_005f0);
      // /page/ADM/BAT_S01.jsp(192,12) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_c_005fwhen_005f0.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${batch.batchStatus == 0}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
      int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
      if (_jspx_eval_c_005fwhen_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n");
          out.write("\t\t\t\t\t        <strong class=\"textgreen\">Success</strong> \r\n");
          out.write("\t\t\t\t\t    ");
          int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_c_005fwhen_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
      _jspx_th_c_005fwhen_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_c_005fwhen_005f0, _jsp_annotationprocessor, _jspx_th_c_005fwhen_005f0_reused);
    }
    return false;
  }

  private boolean _jspx_meth_c_005fwhen_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_form_005fform_005f0)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:when
    org.apache.taglibs.standard.tag.rt.core.WhenTag _jspx_th_c_005fwhen_005f1 = (org.apache.taglibs.standard.tag.rt.core.WhenTag) _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.WhenTag.class);
    boolean _jspx_th_c_005fwhen_005f1_reused = false;
    try {
      _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
      _jspx_th_c_005fwhen_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fchoose_005f0);
      // /page/ADM/BAT_S01.jsp(195,9) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_c_005fwhen_005f1.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${batch.batchStatus == 1}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
      int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
      if (_jspx_eval_c_005fwhen_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n");
          out.write("\t\t\t\t\t\t\t<strong class=\"textblue\">Running...</strong>\r\n");
          out.write("\t\t\t\t\t\t");
          int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_c_005fwhen_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
      _jspx_th_c_005fwhen_005f1_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_c_005fwhen_005f1, _jsp_annotationprocessor, _jspx_th_c_005fwhen_005f1_reused);
    }
    return false;
  }

  private boolean _jspx_meth_c_005fwhen_005f2(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_form_005fform_005f0)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:when
    org.apache.taglibs.standard.tag.rt.core.WhenTag _jspx_th_c_005fwhen_005f2 = (org.apache.taglibs.standard.tag.rt.core.WhenTag) _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.WhenTag.class);
    boolean _jspx_th_c_005fwhen_005f2_reused = false;
    try {
      _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
      _jspx_th_c_005fwhen_005f2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fchoose_005f0);
      // /page/ADM/BAT_S01.jsp(198,9) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_c_005fwhen_005f2.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${batch.batchStatus == 2}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
      int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
      if (_jspx_eval_c_005fwhen_005f2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n");
          out.write("\t\t\t\t\t        <strong class=\"textred\">Fail</strong>\r\n");
          out.write("\t\t\t\t\t    ");
          int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_c_005fwhen_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
      _jspx_th_c_005fwhen_005f2_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_c_005fwhen_005f2, _jsp_annotationprocessor, _jspx_th_c_005fwhen_005f2_reused);
    }
    return false;
  }

  private boolean _jspx_meth_c_005fwhen_005f3(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_form_005fform_005f0)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:when
    org.apache.taglibs.standard.tag.rt.core.WhenTag _jspx_th_c_005fwhen_005f3 = (org.apache.taglibs.standard.tag.rt.core.WhenTag) _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.WhenTag.class);
    boolean _jspx_th_c_005fwhen_005f3_reused = false;
    try {
      _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
      _jspx_th_c_005fwhen_005f3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fchoose_005f0);
      // /page/ADM/BAT_S01.jsp(201,9) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_c_005fwhen_005f3.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${batch.batchStatus == 3}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
      int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
      if (_jspx_eval_c_005fwhen_005f3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n");
          out.write("\t\t\t\t\t        <strong class=\"textorange\">Success with error.</strong>\r\n");
          out.write("\t\t\t\t\t    ");
          int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_c_005fwhen_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
      _jspx_th_c_005fwhen_005f3_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_c_005fwhen_005f3, _jsp_annotationprocessor, _jspx_th_c_005fwhen_005f3_reused);
    }
    return false;
  }

  private boolean _jspx_meth_fmt_005fformatDate_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_form_005fform_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_form_005fform_005f0)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:formatDate
    org.apache.taglibs.standard.tag.rt.fmt.FormatDateTag _jspx_th_fmt_005fformatDate_005f0 = (org.apache.taglibs.standard.tag.rt.fmt.FormatDateTag) _005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.get(org.apache.taglibs.standard.tag.rt.fmt.FormatDateTag.class);
    boolean _jspx_th_fmt_005fformatDate_005f0_reused = false;
    try {
      _jspx_th_fmt_005fformatDate_005f0.setPageContext(_jspx_page_context);
      _jspx_th_fmt_005fformatDate_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_form_005fform_005f0);
      // /page/ADM/BAT_S01.jsp(209,51) name = value type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_fmt_005fformatDate_005f0.setValue((java.util.Date) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${batch.finishDate}", java.util.Date.class, (PageContext)_jspx_page_context, null, false));
      // /page/ADM/BAT_S01.jsp(209,51) name = pattern type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_fmt_005fformatDate_005f0.setPattern("dd/MM/yyyy HH:mm:ss");
      int _jspx_eval_fmt_005fformatDate_005f0 = _jspx_th_fmt_005fformatDate_005f0.doStartTag();
      if (_jspx_th_fmt_005fformatDate_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f0);
      _jspx_th_fmt_005fformatDate_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_fmt_005fformatDate_005f0, _jsp_annotationprocessor, _jspx_th_fmt_005fformatDate_005f0_reused);
    }
    return false;
  }

  private boolean _jspx_meth_c_005fout_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_form_005fform_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_form_005fform_005f0)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:out
    org.apache.taglibs.standard.tag.rt.core.OutTag _jspx_th_c_005fout_005f0 = (org.apache.taglibs.standard.tag.rt.core.OutTag) _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(org.apache.taglibs.standard.tag.rt.core.OutTag.class);
    boolean _jspx_th_c_005fout_005f0_reused = false;
    try {
      _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
      _jspx_th_c_005fout_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_form_005fform_005f0);
      // /page/ADM/BAT_S01.jsp(213,37) name = value type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_c_005fout_005f0.setValue((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${batch.runBy}", java.lang.Object.class, (PageContext)_jspx_page_context, null, false));
      int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
      if (_jspx_th_c_005fout_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
      _jspx_th_c_005fout_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_c_005fout_005f0, _jsp_annotationprocessor, _jspx_th_c_005fout_005f0_reused);
    }
    return false;
  }

  private boolean _jspx_meth_c_005fif_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_form_005fform_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_form_005fform_005f0)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f0 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    boolean _jspx_th_c_005fif_005f0_reused = false;
    try {
      _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
      _jspx_th_c_005fif_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_form_005fform_005f0);
      // /page/ADM/BAT_S01.jsp(219,5) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_c_005fif_005f0.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${batch.batchStatus == 1}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
      int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
      if (_jspx_eval_c_005fif_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n");
          out.write("    \t\t\t\t\t<input name=\"btnRun\" type=\"button\" id=\"btnRun\" value=\"Run Batch\" disabled=\"disabled\"/>\r\n");
          out.write("    \t\t\t\t");
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

  private boolean _jspx_meth_c_005fif_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_form_005fform_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_form_005fform_005f0)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f1 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    boolean _jspx_th_c_005fif_005f1_reused = false;
    try {
      _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
      _jspx_th_c_005fif_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_form_005fform_005f0);
      // /page/ADM/BAT_S01.jsp(222,5) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_c_005fif_005f1.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${batch.batchStatus != 1}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
      int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
      if (_jspx_eval_c_005fif_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n");
          out.write("    \t\t\t\t\t<input name=\"btnRun\" type=\"button\" id=\"btnRun\" value=\"Run Batch\" />\r\n");
          out.write("    \t\t\t\t");
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
}

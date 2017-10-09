<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<%@page import="javax.print.attribute.standard.PrinterName"%>
<%@page import="javax.print.attribute.HashPrintRequestAttributeSet"%>
<%@page import="javax.print.attribute.PrintRequestAttributeSet"%>
<%@page import="java.awt.print.PrinterJob"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="javax.print.*" %>
<%
/*
// prepare for print
DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
PrintService[] printService = PrintServiceLookup
		.lookupPrintServices(flavor, null);

// check printService
if (printService != null) {
	for (int i = 0; i < printService.length; i++) {
		out.println( printService[i].getName()+"<BR/>");
	}
}
*/

Class[] classes = PrintServiceLookup.class.getDeclaredClasses();
for (int i = 0; i < classes.length; i++) {
    if ("javax.print.PrintServiceLookup$Services".equals(classes[i].getName())) {
                sun.awt.AppContext.getAppContext().remove(classes[i]);
        //System.out.println("Refreshed Printer List");
        break;
    }
}

// PrintService service = null;
// PrintService[] services = PrinterJob.lookupPrintServices();

// Retrieve specified print service from the array
// for (int index = 0; service == null && index < services.length; index++) {
//     out.println( services[index].getName()+"<BR/>");
// }

// out.println("<HR/>");
DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();

// aset.add(new PrinterName("ipp:\\\\172.16.12.14\\ZDesigner GK420t", null));
// PrintServiceAttributeSet aset = HashPrintAttributeSet();
// PrintService[] pservices = PrintServiceLookup.lookupPrintServices(flavor, aset);


PrintService[] prnSvcs;
			PrintService prnSvc = PrintServiceLookup.lookupDefaultPrintService();

// 			if (prnSvc != null)
// 			{
			out.println("Default Printer found: ["+prnSvc.getName()+"]<BR/>");
// 			}
// 			else
// 			{
			prnSvcs = PrintServiceLookup.lookupPrintServices(flavor, aset);

			if (prnSvcs.length > 0)
			{
			int ii = 0;
			while ( ii < prnSvcs.length )
			{
				out.println("Printer found: ["+prnSvcs[ii].getName()+"]<BR/>");
			ii++;
			}
			//choose first one for now, eh?
			prnSvc = prnSvcs[0];
			}
// 			}

			// Print out flavors for this selected printer
// 			DocFlavor[] flavors = prnSvc.getSupportedDocFlavors();
// 			for (int i = 0; i < flavors.length; i++)
// 			{
// 				out.println("<BR/>[ " + flavors[i].getMimeType() + " | " +
// 			flavors[i].getRepresentationClassName() +" | "+
// 			flavors[i].toString()+" ]");
// 			}

%>
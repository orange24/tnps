package th.co.nttdata.tki.controller.prd;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.MCustomer;
import th.co.nttdata.tki.bean.MMachine;
import th.co.nttdata.tki.bean.TDCPlan;
import th.co.nttdata.tki.blogic.prd.PRD_S03Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;

@Controller
public class PRD_S03Controller extends AbstractBaseController {

	/* A variable for indicating the next screen */
	private static final String PATH_URI = "PRD/PRD_S03";

	/* A variable for accessing business layer */
	@Autowired
	private PRD_S03Logic prd_S03Logic;

	@Autowired
	private ServletContext servletContext;

	@RequestMapping("/PRD_S03")
	public ModelAndView init() {
		TDCPlan mProduction = new TDCPlan();
		mProduction.setWipList(prd_S03Logic.getWipByWipType());
		mProduction.setMachineList(prd_S03Logic.getMachineByWip(mProduction
				.getWipList().get(0).getWip()));
		mProduction.setMachineToList(prd_S03Logic.getMachineByWip(mProduction
				.getWipList().get(0).getWip()));
		mProduction.setCustomerList(prd_S03Logic.getAllCustomer());
		return new ModelAndView(PATH_URI).addObject("mProduction", mProduction);
	}

	@RequestMapping("/PRD_S03_getMachine")
	public @ResponseBody
	List<MMachine> getMachine(HttpServletRequest request) {
		return prd_S03Logic.getMachineByWip(request.getParameter("param"));
	}

	@RequestMapping("/PRD_S03_getCustomer")
	public @ResponseBody
	List<MCustomer> getCustomerMaster(HttpServletRequest request) {
		return prd_S03Logic.getCustomer(request.getParameter("machineId"));
	}

	@RequestMapping("/PRD_S03_search")
	public @ResponseBody
	List<TDCPlan> search(@RequestBody TDCPlan param) {
		return prd_S03Logic.search(param);
	}
	

	@RequestMapping("/PRD_S03_get_wip")
	public @ResponseBody
	List<TDCPlan> getWip(@RequestBody TDCPlan param) {
		return prd_S03Logic.getWip(param);
	}

	@RequestMapping("/PRD_S03_save")
	public @ResponseBody
	TDCPlan save(@RequestBody List<Map<String, Object>> param) {
		TDCPlan object = new TDCPlan();
		try {
			object = prd_S03Logic.save(param);
			setSaveInfo(object);
		} catch (Exception e) {
			setSystemError(object, e);
		}
		return object;
	}

	@RequestMapping("/PRD_S03_print")
	public @ResponseBody
	TDCPlan print(@RequestBody List<Map<String, Object>> param)
			throws Exception {
		TDCPlan object = new TDCPlan();
		try {
			object = prd_S03Logic.print(param, object);
		} catch (Exception e) {
			setSystemError(object, e);
		}
		return object;
	}
	

	@RequestMapping("/PRD_S03_print_pdf")
	public @ResponseBody
	ResponseEntity<String> printPDF(@RequestBody List<Map<String, Object>> param)
			throws Exception {
		TDCPlan object = new TDCPlan();
		ResponseEntity<String> response = null;
		try {
			
			String filename = prd_S03Logic.printPDF(param, object);
			response = new ResponseEntity<String>(filename, null, HttpStatus.OK);
/*
			byte[] contents = prd_S03Logic.printPDF(param, object);
			
			    HttpHeaders headers = new HttpHeaders();
			    headers.setContentType(MediaType.parseMediaType("application/pdf"));
			    String filename = "output.pdf";
			    headers.setContentDispositionFormData(filename, filename);
			    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
			    response = new ResponseEntity<byte[]>(contents, headers, HttpStatus.OK);
//			    response.setContentType("application/vnd.ms-excel");
//			    response.setHeader("Content-Disposition", "attachment;filename="+ filename);
//				response.addCookie( new Cookie("downloadToken", String.valueOf(System.currentTimeMillis())) );
//				response.flushBuffer();
			    
		    */
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return response;
	}
	
	@RequestMapping(value = "/PRD_S03_PDF.pdf", method = RequestMethod.GET)
	public void getFile(HttpServletRequest request,
            HttpServletResponse response){
	    try {
	    	String fileName = request.getParameter("file");
	    	System.out.println("PRD_S03_PDF fileName = "+fileName);
	      // get your file as InputStream
	      String filePath = servletContext.getRealPath("/WEB-INF/pdf/")+"/"+fileName;
	      File file = new File(filePath);
	      InputStream is = new FileInputStream(file);
	      
	      response.setContentType("application/pdf");
	      // copy it to response's OutputStream
	      org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
	      response.flushBuffer();
	      
	      if(file.delete()){
  			System.out.println(file.getName() + " is deleted!");
	  		}else{
	  			System.out.println("Delete operation is failed.");
	  		}
	    } catch (IOException ex) {
	    	ex.printStackTrace();
	      throw new RuntimeException("IOError writing file to output stream");
	    }

	}
}

package th.co.nttdata.tki.controller.nir;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import th.co.nttdata.tki.bean.FgStockNirvana;
import th.co.nttdata.tki.blogic.nir.NIR_S02Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;

@Controller
public class NIR_S02Controller extends AbstractBaseController {
	@Autowired
	private NIR_S02Logic nirS02Logic;

	/**
	 * Generate FG stock report. If data more than 5000, Split data 5000/excel file then zip all file If data less than
	 * or equals 5000, export to excel file
	 * 
	 * @param criteria - criteria to filter data.
	 * @param request - HttpServletRequest
	 * @param response - HttpServletResponse
	 * @throws IOException if error occur
	 */
	@RequestMapping(value = "/NIR_R02_export", method = RequestMethod.POST)
	public void exportNirR01(FgStockNirvana criteria, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Calendar current = Calendar.getInstance();
		criteria.setTransDate(current.getTime());
		// for control dialog loading on screen.
		response.addCookie(new Cookie("downloadToken", String.valueOf(System.currentTimeMillis())));

		StringBuffer fileName = new StringBuffer();
		if (nirS02Logic.isDataMoreThanMaxRow(criteria)) {
			fileName.append(nirS02Logic.generateZipFileExport(criteria));
			File zipFile = new File(fileName.toString());
			FileInputStream inputStream = new FileInputStream(zipFile);
			response.setContentType("application/zip");
			response.setHeader("Content-Disposition", "attachment;filename=" + zipFile.getName());
			IOUtils.copy(inputStream, response.getOutputStream());
			inputStream.close();
		} else {
			fileName.append("FG_").append(NIR_S02Logic.fileFormatter.format(criteria.getTransDate())).append(".xls");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
			Workbook workbook = nirS02Logic.generateExcelFile(criteria);
			workbook.write(response.getOutputStream());
		}
		response.flushBuffer();
	}
}

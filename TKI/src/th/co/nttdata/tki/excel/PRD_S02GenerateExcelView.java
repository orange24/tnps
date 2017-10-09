package th.co.nttdata.tki.excel;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import th.co.nttdata.tki.bean.TDCPlan;

public class PRD_S02GenerateExcelView extends AbstractExcelView {
	@Override
	@SuppressWarnings("unchecked")
	protected void build(Map<String, Object> model, HSSFWorkbook workbook)
			throws Exception {
		List<TDCPlan> list = (List<TDCPlan>) model.get("data");
		HSSFSheet sheet = workbook.getSheetAt(0);
		int rowNumber = 3;
		HSSFRow row = null;
		for (TDCPlan obj : list) {
			row = sheet.createRow(rowNumber);
			row.createCell(0).setCellValue(obj.getDcPlanDate());
			row.createCell(1).setCellValue(obj.getWip());
			row.createCell(2).setCellValue(obj.getMachineNo());
			row.createCell(3).setCellValue(obj.getSeq());
			row.createCell(4).setCellValue(obj.getShift());
			row.createCell(5).setCellValue(obj.getCustomerCode());
			row.createCell(6).setCellValue(obj.getPartNo());
			row.createCell(7).setCellValue(obj.getPartName());
			row.createCell(8).setCellValue(obj.getSt());
			row.createCell(9).setCellValue(obj.getQuantity());
			row.createCell(10).setCellValue(obj.getSnpWip());
			row.createCell(11).setCellValue(obj.getReasonCode());
			if (!obj.getGenStatus()) {
				/*
				 * row.createCell(12).setCellValue(obj.getWorkOrderNo());
				 * row.createCell(13).setCellValue(obj.getStartLotNo());
				 */
				row.createCell(12).setCellValue("Generated work order number ");
				row.createCell(13).setCellValue("Generated lot number ");
			}
			row.createCell(14).setCellValue(obj.getEndLotNo());
			if (obj.getGenStatus()) {
				row.createCell(15).setCellValue("Yes");
			} else {
				row.createCell(15).setCellValue("No");
			}
			row.createCell(16).setCellValue(obj.getGenDate());
			rowNumber++;
		}
	}
}
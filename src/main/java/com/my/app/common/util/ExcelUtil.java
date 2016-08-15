package com.my.app.common.util;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil {

	public static List<List<String>> readExcel() throws Exception {
		String excel = "C:/dev/local/workspace/servlet-web/src/main/webapp/upload/test.xlsx";
		Workbook workbook = WorkbookFactory.create(new File(excel), null, true);
		workbook.setMissingCellPolicy(MissingCellPolicy.CREATE_NULL_AS_BLANK);
		FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		
		Sheet sheet = workbook.getSheetAt(1);
		
		List<List<String>> rowList = new ArrayList<List<String>>();
		List<String> colList = null;
		
		for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			
			colList = new ArrayList<String>();
			
			for (short j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
				Cell cell = row.getCell(j);
				String value = null;
				
				int cellType = (cell.getCellType() == Cell.CELL_TYPE_FORMULA)
						? evaluator.evaluateFormulaCell(cell) : cell.getCellType();
				
				switch (cellType) {
				case Cell.CELL_TYPE_BOOLEAN:
					value = Boolean.toString(cell.getBooleanCellValue());
					break;
				case Cell.CELL_TYPE_ERROR:
					value = Byte.toString(cell.getErrorCellValue());
					break;
				case Cell.CELL_TYPE_FORMULA:
					System.out.println("[" + cell.getCellFormula() + "]");
					break;
				case Cell.CELL_TYPE_NUMERIC:
					if (HSSFDateUtil.isCellDateFormatted(cell)) {
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						value = format.format(cell.getDateCellValue());
					} else {
						DecimalFormat df = new DecimalFormat();
						df.setGroupingUsed(true);
						value = df.format(cell.getNumericCellValue());
					}
					break;
				default:
					value = cell.getStringCellValue();
					break;
				}
				
				colList.add(value);
			}
			
			rowList.add(colList);
		}
		
		workbook.close();
		
		return rowList;
	}
	
}

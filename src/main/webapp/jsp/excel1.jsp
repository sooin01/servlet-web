<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="org.apache.poi.hssf.usermodel.HSSFDateUtil" %>
<%@ page import="org.apache.poi.ss.usermodel.Cell" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.io.File" %>
<%@ page import="org.apache.poi.ss.usermodel.Sheet" %>
<%@ page import="org.apache.poi.ss.usermodel.Row" %>
<%@ page import="java.util.List" %>
<%@ page import="org.apache.poi.ss.usermodel.Workbook" %>
<%@ page import="org.apache.poi.ss.usermodel.WorkbookFactory" %>
<%@ page import="org.apache.poi.ss.usermodel.Row.MissingCellPolicy" %>
<%@ page import="org.apache.poi.ss.usermodel.FormulaEvaluator" %>
<%
	String excel = "C:/dev/local/workspace/servlet-web/src/main/webapp/upload/test.xlsx";
	Workbook workbook = WorkbookFactory.create(new File(excel), null, true);
	workbook.setMissingCellPolicy(MissingCellPolicy.CREATE_NULL_AS_BLANK);
	FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
	
	Sheet sheet = workbook.getSheetAt(1);
	
	List<List<String>> rowList = new ArrayList<List<String>>();
	List<String> colList = null;
	
	System.out.println("엑셀 파싱 시작");
	
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
	
	System.out.println("엑셀 파싱 끝");
	System.out.println("엑셀 출력 시작");
%>
<!DOCTYPE html>
<html>
<head>
</head>
<body>

<table border="1">
	<%
		for (int i = 0; i < 10; i++) {
	%>
			<tr>
	<%
			for (int j = 0; j < rowList.get(i).size(); j++) {
	%>
				<td><%=rowList.get(i).get(j)%></td>
	<%
			}
	%>
			</tr>
	<%
		}
		System.out.println("엑셀 출력 끝");
	%>
</table>

<p>
	<a href="/sample/excel2">클릭1</a>
</p>
<p>
	<a href="/jsp/excel2.jsp">클릭2</a>
</p>

</body>
</html>
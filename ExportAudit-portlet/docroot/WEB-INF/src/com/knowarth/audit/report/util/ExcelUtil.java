package com.knowarth.audit.report.util;

import com.liferay.portal.audit.model.AuditEvent;
import com.liferay.portal.kernel.util.StringUtil;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.portlet.ResourceResponse;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

public class ExcelUtil {
	
	
	public static void generateReport(ResourceResponse resourceResponse,List<AuditEvent> auditData) throws IOException{
	
	HSSFWorkbook workbook = new HSSFWorkbook();
	HSSFSheet sheet = workbook.createSheet("Audit Report");
	
	sheet.setDefaultColumnWidth(20);

 // cell style for header
    CellStyle style = workbook.createCellStyle();
    Font font = workbook.createFont();    
    font.setFontName("Arial");
    style.setAlignment(CellStyle.ALIGN_JUSTIFY);
    style.setFillForegroundColor(HSSFColor.BLACK.index);
    style.setFillPattern(CellStyle.SOLID_FOREGROUND);
    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    font.setColor(HSSFColor.WHITE.index);
    style.setFont(font);
    style.getAlignment();

    // create header row
    HSSFRow header = sheet.createRow(0);
     
    header.createCell(0).setCellValue("Sr No");
    header.getCell(0).setCellStyle(style);
     
    header.createCell(1).setCellValue("USER ID");
    header.getCell(1).setCellStyle(style);
     
    header.createCell(2).setCellValue("USER NAME");
    header.getCell(2).setCellStyle(style);
     
    header.createCell(3).setCellValue("RESOURCE ID");
    header.getCell(3).setCellStyle(style);
     
    header.createCell(4).setCellValue("EVENT TYPE");
    header.getCell(4).setCellStyle(style);
    
    header.createCell(5).setCellValue("RESOURCE NAME");
    header.getCell(5).setCellStyle(style);
    
    
    header.createCell(6).setCellValue("CLIENT IP");
    header.getCell(6).setCellStyle(style);
    
    header.createCell(7).setCellValue("CREATE DATE");
    header.getCell(7).setCellStyle(style);
    
   
    
    
    	int rowCount = 1;
    	int rowNember=1;
    	
    	//row Data
    
    for (AuditEvent auditEvent : auditData) {
        HSSFRow auditRow = sheet.createRow(rowCount++); 
        auditRow.createCell(0).setCellValue(rowNember++);
        auditRow.createCell(1).setCellValue(auditEvent.getUserId());
        auditRow.createCell(2).setCellValue(auditEvent.getUserName());
        auditRow.createCell(3).setCellValue(auditEvent.getAuditEventId());
        auditRow.createCell(4).setCellValue(auditEvent.getEventType());
        auditRow.createCell(5).setCellValue(StringUtil.extractLast(auditEvent.getClassName(), ".").toUpperCase());
        auditRow.createCell(6).setCellValue(auditEvent.getClientIP());
     
        DateFormat dateFormat=SimpleDateFormat.getDateTimeInstance();
        auditRow.createCell(7).setCellValue(dateFormat.format(auditEvent.getCreateDate()));
        
    }
    
	OutputStream outputStream = resourceResponse.getPortletOutputStream();
	workbook.write(outputStream);
	outputStream.flush();
	outputStream.close();

}
		
}



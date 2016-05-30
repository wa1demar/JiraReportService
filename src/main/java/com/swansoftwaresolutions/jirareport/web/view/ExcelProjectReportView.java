package com.swansoftwaresolutions.jirareport.web.view;

import com.swansoftwaresolutions.jirareport.core.dto.jira_users.FullProjectUserDto;
import com.swansoftwaresolutions.jirareport.core.dto.projects.FullProjectDto;
import com.swansoftwaresolutions.jirareport.core.dto.projects.FullProjectDtos;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author Vladimir Martynyuk
 */
public class ExcelProjectReportView extends AbstractExcelView {

    private static final int FIRST_ROW = 1;

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {

        FullProjectDtos revenueData = (FullProjectDtos) model.get("projectData");

        HSSFSheet sheet = workbook.createSheet("Project Assignments");

        HSSFRow header = sheet.createRow(FIRST_ROW);

        CellStyle headerStyle = getHeaderStyle(workbook);

        CellStyle style = workbook.createCellStyle();

        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 11);
        style.setFont(font);

        int colNum = 0;
        int maxRowNum = 0;
        int maxColNum = 0;
        for ( FullProjectDto project : revenueData.getProjects()) {
            HSSFCell headerCell = header.createCell(colNum);
            headerCell.setCellStyle(headerStyle);
            headerCell.setCellValue(project.getTitle() + " (" + project.getUsers().size() + ")");

            if (project.getUsers() != null && project.getUsers().size() > 0) {
                int rowNum = FIRST_ROW + 1;
                for (FullProjectUserDto user : project.getUsers()) {
                    HSSFRow row = null;
                    if (sheet.getRow(rowNum) == null) {
                        row = sheet.createRow(rowNum);
                    } else {
                        row = sheet.getRow(rowNum);
                    }

                    HSSFCell cell = row.createCell(colNum);
                    cell.setCellValue(user.getName() + (user.getPosition() != null ? " - " + user.getPosition().getName() : ""));
                    rowNum++;

                }

                maxRowNum = maxRowNum <= rowNum ? rowNum : maxRowNum;
            }

            sheet.autoSizeColumn(colNum);

            maxColNum = maxColNum <= colNum ? colNum : maxColNum;
            colNum++;
        }

        for (int i = FIRST_ROW + 1; i < maxRowNum + 1; i++) {
            for (int j = 0; j < maxColNum + 1; j++) {
                HSSFRow row = sheet.getRow(i) != null ? sheet.getRow(i) : sheet.createRow(i);
                HSSFCell cell = row.getCell(j) != null ? row.getCell(j) : row.createCell(j);

                HSSFCellStyle cellStyle = workbook.createCellStyle();

                cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
                cellStyle.setBorderRight(CellStyle.BORDER_THIN);


                if (i == maxRowNum) {
                    cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
                }

                cell.setCellStyle(cellStyle);
            }
        }



    }

    private CellStyle getHeaderStyle(HSSFWorkbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

        style.setFont(font);

        style.setFillBackgroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setFillPattern(CellStyle.BIG_SPOTS);

        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);

        return style;
    }

}

package com.swansoftwaresolutions.jirareport.web.view;

import com.swansoftwaresolutions.jirareport.core.dto.jira_users.FullProjectUserDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_users.JiraUserDto;
import com.swansoftwaresolutions.jirareport.core.dto.projects.ExportProjectsDtos;
import com.swansoftwaresolutions.jirareport.core.dto.projects.FullProjectDto;
import com.swansoftwaresolutions.jirareport.core.dto.resourceboard.ResourceColumnDto;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Vladimir Martynyuk
 */
public class ExcelProjectReportView extends AbstractExcelView {

    private int maxRowNum = 0;
    private int maxColNum = 0;

    private int firstRowNum = 1;

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {

        ExportProjectsDtos revenueData = (ExportProjectsDtos) model.get("projectData");

        HSSFSheet sheet = workbook.createSheet("Project Assignments");
        sheet.setMargin(Sheet.RightMargin, 0.5);
        sheet.setMargin(Sheet.LeftMargin, 0.5);

        Row header = sheet.createRow(firstRowNum + 2);
        header.setHeight((short) 400);

        CellStyle projectHeaderStyle = getProjectHeaderStyle(workbook);
        CellStyle headerStyle = getHeaderStyle(workbook);

        int colNum = 0;
        int rowNum = firstRowNum + 2;
        for (FullProjectDto project : revenueData.getProjects()) {
            Cell projectCell = header.createCell(colNum);
            projectCell.setCellStyle(projectHeaderStyle);
            projectCell.setCellValue(project.getTitle());
            sheet.addMergedRegion(new CellRangeAddress(firstRowNum + 2, firstRowNum + 2, colNum, colNum + 1));

            Row headerRow = null;
            if (sheet.getRow(firstRowNum + 3) == null) {
                headerRow = sheet.createRow(firstRowNum + 3);
                headerRow.setHeight((short) 400);
            } else {
                headerRow = sheet.getRow(firstRowNum + 3);
            }

            Cell headerCell1 = headerRow.createCell(colNum);
            headerCell1.setCellStyle(headerStyle);
            headerCell1.setCellValue("Participants");
            sheet.autoSizeColumn(colNum);

            Cell headerCell2 = headerRow.createCell(colNum + 1);
            headerCell2.setCellStyle(headerStyle);
            headerCell2.setCellValue("EL / Position");
            sheet.autoSizeColumn(colNum + 1);


            if (project.getUsers() != null && project.getUsers().size() > 0) {
                rowNum = firstRowNum + 4;
                for (FullProjectUserDto user : project.getUsers()) {
                    Row row = null;
                    if (sheet.getRow(rowNum) == null) {
                        row = sheet.createRow(rowNum);
                        row.setHeight((short) 350);
                    } else {
                        row = sheet.getRow(rowNum);
                    }
//
                    Cell userCell = row.createCell(colNum);
                    userCell.setCellStyle(getUserCellStyle(workbook, user.getAssignmentTypes()));
                    userCell.setCellValue(user.getName());
                    sheet.autoSizeColumn(colNum);

                    Cell positionCell = row.createCell(colNum + 1);
                    positionCell.setCellStyle(getPositionCellStyle(workbook));
                    positionCell.setCellValue((user.getPosition() != null ? user.getPosition().getName() : ""));
                    sheet.autoSizeColumn(colNum + 1);

                    rowNum++;

                }

                maxRowNum = maxRowNum <= rowNum ? rowNum : maxRowNum;
            }

            maxColNum = maxColNum <= colNum ? colNum : maxColNum;
            colNum += 3;
        }

        maxRowNum += 2;

        createMainHeader(sheet, "Projects");

        firstRowNum = maxRowNum;

        Map<String, Set<JiraUserDto>> technologies = revenueData.getTechnologies();

        HSSFRow benchRow = sheet.createRow(firstRowNum + 2);
        benchRow.setHeight((short) 400);
        colNum = 0;
        maxColNum = 0;
        for (Map.Entry<String, Set<JiraUserDto>> entry : technologies.entrySet()) {
            Cell projectCell = benchRow.createCell(colNum);
            projectCell.setCellStyle(projectHeaderStyle);
            projectCell.setCellValue(entry.getKey() + " Bench");
            sheet.addMergedRegion(new CellRangeAddress(firstRowNum + 2, firstRowNum + 2, colNum, colNum + 2));

            Row headerRow = null;
            if (sheet.getRow(firstRowNum + 3) == null) {
                headerRow = sheet.createRow(firstRowNum + 3);
                headerRow.setHeight((short) 400);
            } else {
                headerRow = sheet.getRow(firstRowNum + 3);
            }

            Cell headerCell1 = headerRow.createCell(colNum);
            headerCell1.setCellStyle(headerStyle);
            headerCell1.setCellValue("Participants");
            sheet.autoSizeColumn(colNum);

            Cell headerCell2 = headerRow.createCell(colNum + 1);
            headerCell2.setCellStyle(headerStyle);
            headerCell2.setCellValue("EL / Position");
            sheet.autoSizeColumn(colNum + 1);


            if (entry.getValue() != null && entry.getValue().size() > 0) {
                rowNum = firstRowNum + 4;
                for (JiraUserDto user : entry.getValue()) {
                    Row row = null;
                    if (sheet.getRow(rowNum) == null) {
                        row = sheet.createRow(rowNum);
                        row.setHeight((short) 400);
                    } else {
                        row = sheet.getRow(rowNum);
                    }

                    Cell userCell = row.createCell(colNum);
                    userCell.setCellStyle(getUserCellStyle(workbook, new ArrayList<ResourceColumnDto>() {{ add(user.getAssignmentType()); }} ));
                    userCell.setCellValue(user.getName());
                    sheet.autoSizeColumn(colNum);

                    Cell positionCell = row.createCell(colNum + 1);
                    positionCell.setCellStyle(getPositionCellStyle(workbook));
                    positionCell.setCellValue((user.getPosition() != null ? user.getPosition().getName() : ""));
                    sheet.autoSizeColumn(colNum + 1);

                    rowNum++;

                }

                maxRowNum = maxRowNum <= rowNum ? rowNum : maxRowNum;
            }


            maxColNum = maxColNum <= colNum ? colNum : maxColNum;
            colNum += 3;

        }

        createMainHeader(sheet, "Bench");

//        HSSFRow tempRow = sheet.createRow(maxRowNum + 2);
//        for (int i = 0; i < 70; i++) {
//            Cell headerCell2 = tempRow.createCell(i);
//
//            CellStyle style = workbook.createCellStyle();
//            style.setFillForegroundColor((short)i);
//            style.setFillPattern(CellStyle.SOLID_FOREGROUND);
//
//            headerCell2.setCellStyle(style);
//            headerCell2.setCellValue(i);
//        }
    }

    private void createMainHeader(HSSFSheet sheet, String text) {
        Row row = sheet.createRow(firstRowNum);
        row.setHeight((short) 500);
        HSSFWorkbook workbook = sheet.getWorkbook();


        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setWrapText(true);
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        Font font = workbook.createFont();
        font.setColor(IndexedColors.BLACK.getIndex());
        font.setFontHeightInPoints((short) 18);
        font.setFontName("Arial");

        style.setFont(font);


        Cell cell = (Cell) row.createCell((short) 0);
        cell.setCellValue(text);
        cell.setCellStyle(style);

        sheet.addMergedRegion(new CellRangeAddress(
                firstRowNum, //first row (0-based)
                firstRowNum, //last row (0-based)
                0, //first column (0-based)
                maxColNum + 1 //last column (0-based)
        ));

    }

    private CellStyle getProjectHeaderStyle(HSSFWorkbook workbook) {

        CellStyle style = workbook.createCellStyle();
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 14);
        font.setFontName("Trebuchet MS");

        style.setFont(font);

        return style;
    }

    private CellStyle getHeaderStyle(HSSFWorkbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        style.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);

        style.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);

        style.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);

        style.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setBorderRight(CellStyle.BORDER_THIN);

        Font font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeightInPoints((short) 11);
        font.setColor(IndexedColors.WHITE.getIndex());
        font.setFontName("Arial");

        style.setFont(font);

        return style;
    }

    private CellStyle getUserCellStyle(HSSFWorkbook workbook, List<ResourceColumnDto> assignmentTypes) {
        CellStyle style = workbook.createCellStyle();
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        style.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);

        style.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);

        style.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);

        style.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setBorderRight(CellStyle.BORDER_THIN);

        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 11);
        font.setFontName("Trebuchet MS");
        if (assignmentTypes != null && assignmentTypes.size() > 0) {
            XSSFColor myColor = new XSSFColor(hex2Rgb(assignmentTypes.get(0).getColor()));
            HSSFPalette palette = workbook.getCustomPalette();
            font.setColor(palette.findSimilarColor(myColor.getRGB()[0], myColor.getRGB()[1], myColor.getRGB()[2]).getIndex());
        } else {
            font.setColor(IndexedColors.BLACK.getIndex());
        }

        style.setFont(font);

        return style;
    }

    private CellStyle getPositionCellStyle(HSSFWorkbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);

        style.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);

        style.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);

        style.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);

        style.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setBorderRight(CellStyle.BORDER_THIN);

        style.setAlignment(CellStyle.ALIGN_CENTER);

        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 11);
        font.setFontName("Arial");
        font.setColor(IndexedColors.BLACK.getIndex());


        style.setFont(font);

        return style;
    }

    public static Color hex2Rgb(String colorStr) {
        return new Color(
                Integer.valueOf(colorStr.substring(1, 3), 16),
                Integer.valueOf(colorStr.substring(3, 5), 16),
                Integer.valueOf(colorStr.substring(5, 7), 16));
    }

}

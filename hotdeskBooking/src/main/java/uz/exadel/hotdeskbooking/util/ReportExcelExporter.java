package uz.exadel.hotdeskbooking.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import uz.exadel.hotdeskbooking.dto.response.BookingReportResTO;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
public class ReportExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<BookingReportResTO> reportDataList;

    public ReportExcelExporter(List<BookingReportResTO> reportDataList) {
        this.reportDataList = reportDataList;
        workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("BookingReportResTOs");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(14);
        style.setFont(font);
        createCell(row, 0, "Booking ID", style);
        createCell(row, 1, "User ID", style);
        createCell(row, 2, "User Firstname", style);
        createCell(row, 3, "User Lastname", style);
        createCell(row, 4, "User Role", style);
        createCell(row, 5, "Workplace ID", style);
        createCell(row, 6, "Map ID", style);
        createCell(row, 7, "Workplace Number", style);
        createCell(row, 8, "Workplace Type", style);
        createCell(row, 9, "Workplace Next To Window", style);
        createCell(row, 10, "Workplace Has PC", style);
        createCell(row, 11, "Workplace Has Monitor", style);
        createCell(row, 12, "Workplace Has Keyboard", style);
        createCell(row, 13, "Workplace Has Mouse", style);
        createCell(row, 14, "Workplace Has Headset", style);
        createCell(row, 15, "Map Floor", style);
        createCell(row, 16, "Map Kitchen", style);
        createCell(row, 17, "Map Conf Rooms", style);
        createCell(row, 18, "Start Date", style);
        createCell(row, 19, "End Date", style);
        createCell(row, 20, "Recurring", style);

    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            if (value == Boolean.TRUE) {
                cell.setCellValue("Yes");
            } else {
                cell.setCellValue("No");
            }
        } else if (value instanceof Date) {
            Format formatter = new SimpleDateFormat("dd-MM-yyyy");
            cell.setCellValue(formatter.format(value));
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (BookingReportResTO BookingReportResTO : reportDataList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, BookingReportResTO.getId(), style);
            createCell(row, columnCount++, BookingReportResTO.getUser().getId(), style);
            createCell(row, columnCount++, BookingReportResTO.getUser().getFirstName(), style);
            createCell(row, columnCount++, BookingReportResTO.getUser().getLastName(), style);
            createCell(row, columnCount++, BookingReportResTO.getUser().getRole(), style);
            createCell(row, columnCount++, BookingReportResTO.getWorkplace().getId(), style);
            createCell(row, columnCount++, BookingReportResTO.getWorkplace().getMapId(), style);
            createCell(row, columnCount++, BookingReportResTO.getWorkplace().getNumber(), style);
            createCell(row, columnCount++, BookingReportResTO.getWorkplace().getType().getName(), style);
            createCell(row, columnCount++, BookingReportResTO.getWorkplace().getNextToWindow(), style);
            createCell(row, columnCount++, BookingReportResTO.getWorkplace().getHasPC(), style);
            createCell(row, columnCount++, BookingReportResTO.getWorkplace().getHasMonitor(), style);
            createCell(row, columnCount++, BookingReportResTO.getWorkplace().getHasKeyboard(), style);
            createCell(row, columnCount++, BookingReportResTO.getWorkplace().getHasMouse(), style);
            createCell(row, columnCount++, BookingReportResTO.getWorkplace().getHasHeadset(), style);
            createCell(row, columnCount++, BookingReportResTO.getWorkplace().getFloor(), style);
            createCell(row, columnCount++, BookingReportResTO.getWorkplace().getKitchen(), style);
            createCell(row, columnCount++, BookingReportResTO.getWorkplace().getConfRoom(), style);
            createCell(row, columnCount++, BookingReportResTO.getStartDate(), style);
            createCell(row, columnCount++, BookingReportResTO.getEndDate(), style);
            createCell(row, columnCount++, BookingReportResTO.isRecurring(), style);

        }
    }

    public void export(HttpServletResponse response) {
        try {
            writeHeaderLine();
            writeDataLines();

            ServletOutputStream outputStream = response.getOutputStream();

            workbook.write(outputStream);
            workbook.close();

            outputStream.close();
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}

package com.onedsol.tools.util;

import com.onedsol.tools.jpdfmake.*;
import com.onedsol.tools.jpdfmake.enums.Alignment;
import com.onedsol.tools.jpdfmake.enums.PageBreak;
import com.onedsol.tools.jpdfmake.table.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hermeslm on 4/27/17.
 */
public class PdfUtil {

    public static Pdf initPdfWithHeader(String reportName, String rightText, String base64Logo) {

        List<Object> content = new ArrayList<>();

        /**
         * Header
         */
        String companyInfo = rightText + "\n";
        Text companyColumn = new Text(companyInfo);
        companyColumn.width(100)
                     .fontSize(8)
                     .alignment(Alignment.justify);

        List<String> headerStyles = new ArrayList<>();
        headerStyles.add("header");

        Text reportTitleColumn = new Text(reportName);
        reportTitleColumn.fontSize(22)
                         .bold(true)
                         .alignment(Alignment.center)
                         .color("#2F5496");

        Image reportLogo = new Image(base64Logo, 100, 50);

        Column column = new Column();
        column.addColumn(companyColumn);
        column.addColumn(reportTitleColumn);
        column.addColumn(reportLogo);

        content.add(column);
        content.add(PdfUtil.println());
//        content.add(ln);

        Pdf pdf = new Pdf();
        pdf.setContent(content);
        return pdf;
    }

    public static Pdf initPdfWithHeaderFitted(String reportName, String rightText, String base64Logo) {

        List<Object> content = new ArrayList<>();

        /**
         * Header
         */
        String companyInfo = rightText + "\n";
        Text companyColumn = new Text(companyInfo);
        companyColumn.width(100)
                     .fontSize(8)
                     .alignment(Alignment.justify);

        List<String> headerStyles = new ArrayList<>();
        headerStyles.add("header");

        Text reportTitleColumn = new Text(reportName);
        reportTitleColumn.fontSize(22)
                         .bold(true)
                         .alignment(Alignment.center)
                         .color("#2F5496");

        Image reportLogo = new Image(base64Logo, 125, 50);

        Column columnItem = new Column();
        columnItem.addColumn(companyColumn);
        columnItem.addColumn(reportTitleColumn);
        columnItem.addColumn(reportLogo);

        content.add(columnItem);

        Pdf pdf = new Pdf();
        pdf.setContent(content);
        return pdf;
    }

//    static public Pdf finishInvoicePdf(Pdf pdf, BigDecimal total) {
//        List<ContentElement> content = pdf.getContent();
//
//        content.add(new Text("\n"));
//        content.add(new ExtendedTextItem("Total: $" + total.toString(), "50%", 9, Alignment.right));
//        content.add(new Text("\n"));
//
//        return pdf;
//    }

    public static Table tableNoData() {

        List<Object> columnWidths = new ArrayList<>();
        columnWidths.add(Width.WIDTH_SPACE_EQUALLY);

        Table tableElement = new Table();
        tableElement.setLayout(new TableLayout("#8EAADB", "#8EAADB"));

        TableBody tableBody = new TableBody();
        tableBody.setWidths(columnWidths);
        tableElement.setTable(tableBody);

        List<Item> tableHeader = new ArrayList<>();
        tableHeader.add(new TableCell("INFO").fillColor("#8EAADB").color("#FFFFFF").alignment(Alignment.center));

        List<Item> tableData = new ArrayList<>();
        tableData.add(new TableCell("There are no results for the search criteria.")
                .alignment(Alignment.center));

        List<List<Item>> body = new ArrayList<>();
        body.add(tableHeader);
        body.add(tableData);
        tableBody.setBody(body);

        tableElement.setTable(tableBody);

        return tableElement;
    }

    public static Table tableNoData(String title, String description) {

        List<Object> columnWidths = new ArrayList<>();
        columnWidths.add(Width.WIDTH_SPACE_EQUALLY);

        Table tableElement = new Table();
        tableElement.setLayout(new TableLayout("#8EAADB", "#8EAADB"));

        TableBody tableBody = new TableBody();
        tableBody.setWidths(columnWidths);
        tableElement.setTable(tableBody);

        List<Item> tableHeader = new ArrayList<>();
        tableHeader.add(new TableCell("INFO:" + title).fillColor("#8EAADB")
                                                      .color("#FFFFFF")
                                                      .alignment(Alignment.center));

        List<Item> tableData = new ArrayList<>();
        tableData.add(new TableCell(description).alignment(Alignment.center));

        List<List<Item>> body = new ArrayList<>();
        body.add(tableHeader);
        body.add(tableData);
        tableBody.setBody(body);

        tableElement.setTable(tableBody);

        return tableElement;
    }

    public static Table tableNoData(String title) {

        List<Object> columnWidths = new ArrayList<>();
        columnWidths.add(Width.WIDTH_SPACE_EQUALLY);

        Table tableElement = new Table();
        tableElement.setLayout(new TableLayout("#8EAADB", "#8EAADB"));

        TableBody tableBody = new TableBody();
        tableBody.setWidths(columnWidths);
        tableElement.setTable(tableBody);

        List<Item> tableHeader = new ArrayList<>();
        tableHeader.add(new TableCell("INFO:" + title).fillColor("#8EAADB")
                                                      .color("#FFFFFF")
                                                      .alignment(Alignment.center));

        List<Item> tableData = new ArrayList<>();
        tableData.add(new TableCell("There are no results for the search criteria.")
                .alignment(Alignment.center));

        List<List<Item>> body = new ArrayList<>();
        body.add(tableHeader);
        body.add(tableData);
        tableBody.setBody(body);

        tableElement.setTable(tableBody);

        return tableElement;
    }

    public static Text println() {
        return new Text("\n");
    }

    public static Text pageBreak(PageBreak pageBreak) {
        return new Text("\n").pageBreak(pageBreak);
    }

}

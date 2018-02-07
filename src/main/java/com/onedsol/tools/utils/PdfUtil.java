package com.onedsol.tools.utils;

import com.onedsol.tools.jpdfmake.*;
import com.onedsol.tools.jpdfmake.table.TableBody;
import com.onedsol.tools.jpdfmake.table.TableElement;
import com.onedsol.tools.jpdfmake.table.TableItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hermeslm on 4/27/17.
 */
@Component
public class PdfUtil {

    static public Pdf initPdfWithHeader(String reportName, String rightText, String base64Logo) {

        TextItem ln = new TextItem("\n");
        List<ContentElement> content = new ArrayList<>();

        /**
         * Header
         */
        String companyInfo = rightText + "\n";
        ExtendedTextItem companyColumn = new ExtendedTextItem(companyInfo, 100,
                8, Alignment.justify);

        List<String> headerStyles = new ArrayList<>();
        headerStyles.add("header");
        ExtendedTextItem reportTitleColumn = new ExtendedTextItem(reportName, 22, true,
                Alignment.center, "#2F5496");

        ImageItem reportLogo = new ImageItem(base64Logo, 100, 50);

        ColumnElement columnItem = new ColumnElement();
        columnItem.getColumns().add(companyColumn);
        columnItem.getColumns().add(reportTitleColumn);
        columnItem.getColumns().add(reportLogo);

        content.add(columnItem);
        content.add(ln);
//        content.add(ln);

        Pdf pdf = new Pdf();
        pdf.setContent(content);
        return pdf;
    }

    static public Pdf initPdfWithHeaderFitted(String reportName, String rightText, String base64Logo) {

        List<ContentElement> content = new ArrayList<>();

        /**
         * Header
         */
        String companyInfo = rightText + "\n";
        ExtendedTextItem companyColumn = new ExtendedTextItem(companyInfo, 100,
                8, Alignment.justify);

        List<String> headerStyles = new ArrayList<>();
        headerStyles.add("header");
        ExtendedTextItem reportTitleColumn = new ExtendedTextItem(reportName, 22, true,
                Alignment.center, "#2F5496");

        ImageItem reportLogo = new ImageItem(base64Logo, 125, 50);

        ColumnElement columnItem = new ColumnElement();
        columnItem.getColumns().add(companyColumn);
        columnItem.getColumns().add(reportTitleColumn);
        columnItem.getColumns().add(reportLogo);

        content.add(columnItem);

        Pdf pdf = new Pdf();
        pdf.setContent(content);
        return pdf;
    }

    static public Pdf finishInvoicePdf(Pdf pdf, BigDecimal total) {
        List<ContentElement> content = pdf.getContent();

        content.add(new TextItem("\n"));
        content.add(new ExtendedTextItem("Total: $" + total.toString(), "50%", 9, Alignment.right));
        content.add(new TextItem("\n"));

        return pdf;
    }

    static public ContentElement getNotDataTable() {

        List<Object> columnWidths = new ArrayList<>();
        columnWidths.add(Width.WIDTH_SPACE_EQUALLY);

        TableElement tableElement = new TableElement();
        tableElement.setLayout(new LayoutElement("#8EAADB", "#8EAADB"));

        TableBody tableBody = new TableBody();
        tableBody.setWidths(columnWidths);
        tableElement.setTable(tableBody);

        List<Element> tableHeader = new ArrayList<>();
        tableHeader.add(new TableItem("INFO", "#8EAADB", "#FFFFFF", Alignment.center));

        List<Element> tableData = new ArrayList<>();
        tableData.add(new TableItem("There are no results for the search criteria in this report.",
                Alignment.center));

        List<List<Element>> body = new ArrayList<List<Element>>();
        body.add(tableHeader);
        body.add(tableData);
        tableBody.setBody(body);

        tableElement.setTable(tableBody);

        return tableElement;
    }

    static public ContentElement getNotDataTable(String title, String description) {

        List<Object> columnWidths = new ArrayList<>();
        columnWidths.add(Width.WIDTH_SPACE_EQUALLY);

        TableElement tableElement = new TableElement();
        tableElement.setLayout(new LayoutElement("#8EAADB", "#8EAADB"));

        TableBody tableBody = new TableBody();
        tableBody.setWidths(columnWidths);
        tableElement.setTable(tableBody);

        List<Element> tableHeader = new ArrayList<>();
        tableHeader.add(new TableItem(title, "#8EAADB", "#FFFFFF", Alignment.center));

        List<Element> tableData = new ArrayList<>();
        tableData.add(new TableItem(description,
                Alignment.center));

        List<List<Element>> body = new ArrayList<List<Element>>();
        body.add(tableHeader);
        body.add(tableData);
        tableBody.setBody(body);

        tableElement.setTable(tableBody);

        return tableElement;
    }

    static public ContentElement getNotDataTable(String title) {

        List<Object> columnWidths = new ArrayList<>();
        columnWidths.add(Width.WIDTH_SPACE_EQUALLY);

        TableElement tableElement = new TableElement();
        tableElement.setLayout(new LayoutElement("#8EAADB", "#8EAADB"));

        TableBody tableBody = new TableBody();
        tableBody.setWidths(columnWidths);
        tableElement.setTable(tableBody);

        List<Element> tableHeader = new ArrayList<>();
        tableHeader.add(new TableItem(title, "#8EAADB", "#FFFFFF", Alignment.center));

        List<Element> tableData = new ArrayList<>();
        tableData.add(new TableItem("There are no results for the search criteria in this report.",
                Alignment.center));

        List<List<Element>> body = new ArrayList<List<Element>>();
        body.add(tableHeader);
        body.add(tableData);
        tableBody.setBody(body);

        tableElement.setTable(tableBody);

        return tableElement;
    }

    static public TextItem println() {
        return new TextItem("\n");
    }

    static public TextItem pageBreak(PageBreak pageBreak) {
        return new TextItem("\n", pageBreak);
    }

}

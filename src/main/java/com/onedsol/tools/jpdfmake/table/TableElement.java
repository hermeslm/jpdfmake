package com.onedsol.tools.jpdfmake.table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.onedsol.tools.jpdfmake.Element;
import com.onedsol.tools.jpdfmake.LayoutElement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hermeslm on 4/14/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TableElement extends Element {

    private String style;

    private TableBody table;

    private String color;

    private LayoutElement layout;

    /**
     * Build a Table Element from table data, using tableheader and columns widths.
     *
     * @param columnWidths List of columns widths
     * @param tableHeader  List with tha table header texts.
     * @param tableData    List of table rows
     * @param fontSize     Table fontsize.
     * @param zebraStyle   Table zebra style.
     * @return TableElement from table data, using tableheader and columns widths.
     */
    static public TableElement buildTableElement(List<Object> columnWidths, List<Element> tableHeader,
                                                 List<List<String>> tableData, Integer fontSize, boolean zebraStyle) {

        TableElement tableElement = new TableElement();
        tableElement.setLayout(new LayoutElement("#8EAADB", "#8EAADB"));

        TableBody tableBody = new TableBody();

        tableBody.setWidths(columnWidths);
        tableElement.setTable(tableBody);

        List<List<Element>> body = new ArrayList<List<Element>>();

        body.add(tableHeader);

        int i = 0;
        for (List<String> row : tableData) {
            List<Element> line = new ArrayList<>();
            for (int j = 0; j < row.size(); j++) {
                if (zebraStyle) {
                    if (i % 2 == 0) {//Make zebra rows style
                        line.add(new TableItem(printValue(row.get(j)), fontSize));
                    } else {
                        line.add(new TableItem(printValue(row.get(j)), "#D9E2F3"/*"#edf1f9"*/, fontSize));
                    }
                }else {
                    line.add(new TableItem(printValue(row.get(j)), fontSize));
                }
            }
            body.add(line);
            i += 1;
        }
        tableBody.setBody(body);
        return tableElement;
    }

    /**
     * Build a Table Element from table data, using tableheader and columns widths.
     *
     * @param columnWidths List of columns widths
     * @param tableHeader  List with tha table header texts.
     * @param tableData    List of table rows
     * @return TableElement from table data, using tableheader and columns widths.
     */
    static public TableElement buildTableElement(List<Object> columnWidths, List<Element> tableHeader,
                                                 List<List<String>> tableData) {

        TableElement tableElement = new TableElement();
        tableElement.setLayout(new LayoutElement("#8EAADB", "#8EAADB"));

        TableBody tableBody = new TableBody();

        tableBody.setWidths(columnWidths);
        tableElement.setTable(tableBody);

        List<List<Element>> body = new ArrayList<List<Element>>();

        body.add(tableHeader);

        int i = 0;
        for (List<String> row : tableData) {
            List<Element> line = new ArrayList<>();
            for (int j = 0; j < row.size(); j++) {
                if (i % 2 == 0) {//Make zebra rows style
                    line.add(new TableItem(printValue(row.get(j))));
                } else {
                    line.add(new TableItem(printValue(row.get(j)), "#D9E2F3"));
                }
            }
            body.add(line);
            i += 1;
        }
        tableBody.setBody(body);
        return tableElement;
    }

    /**
     * Build a Table Element from table items, using tableheader and columns widths.
     *
     * @param columnWidths List of columns widths
     * @param tableHeader  List with tha table header texts.
     * @param tableData    List of table rows
     * @param hLineColor   Horizontal line color
     * @param vLineColor   Vertical line color
     * @return TableElement from table data, using tableheader and columns widths.
     */
    static public TableElement buildTableElement(List<Object> columnWidths, List<Element> tableHeader,
                                                 List<List<Element>> tableData,
                                                 String hLineColor, String vLineColor) {

        TableElement tableElement = new TableElement();
        tableElement.setLayout(new LayoutElement(hLineColor, vLineColor));

        TableBody tableBody = new TableBody();

        tableBody.setWidths(columnWidths);
        tableElement.setTable(tableBody);

        List<List<Element>> body = new ArrayList<List<Element>>();

        if (tableHeader != null && tableHeader.size() > 0) {
            body.add(tableHeader);
        }

        for (List<Element> row : tableData) {
            List<Element> line = new ArrayList<>();
            for (int j = 0; j < row.size(); j++) {
                line.add(row.get(j));
            }
            body.add(line);
        }
        tableBody.setBody(body);
        return tableElement;
    }

    /**
     * Build a Table Element from table items, using tableheader and columns widths.
     *
     * @param columnWidths List of columns widths
     * @param tableData    List of table rows
     * @param hLineColor   Horizontal line color
     * @param vLineColor   Vertical line color
     * @return TableElement from table data, using tableheader and columns widths.
     */
    static public TableElement buildTableElement(List<Object> columnWidths, List<List<Element>> tableData,
                                                 String hLineColor, String vLineColor) {

        TableElement tableElement = new TableElement();
        tableElement.setLayout(new LayoutElement(hLineColor, vLineColor));

        TableBody tableBody = new TableBody();

        tableBody.setWidths(columnWidths);
        tableElement.setTable(tableBody);

        List<List<Element>> body = new ArrayList<List<Element>>();

        for (List<Element> row : tableData) {
            List<Element> line = new ArrayList<>();
            for (int j = 0; j < row.size(); j++) {
                line.add(row.get(j));
            }
            body.add(line);
        }
        tableBody.setBody(body);
        return tableElement;
    }

    static private String printValue(Object item) {

        if (item instanceof Date) {
            return new SimpleDateFormat("MM/dd/yyyy").format(item);
        } else {
            return item.toString();
        }
    }

    public LayoutElement getLayout() {
        return layout;
    }

    public void setLayout(LayoutElement layout) {
        this.layout = layout;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public TableBody getTable() {
        return table;
    }

    public void setTable(TableBody table) {
        this.table = table;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "TableElement{" +
            "style='" + style + '\'' +
            ", table=" + table +
            ", color='" + color + '\'' +
            ", layout=" + layout +
            '}';
    }

}

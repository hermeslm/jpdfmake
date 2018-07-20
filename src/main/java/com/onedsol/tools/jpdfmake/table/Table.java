package com.onedsol.tools.jpdfmake.table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.onedsol.tools.jpdfmake.Item;
import com.onedsol.tools.jpdfmake.enums.Layout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Table extends Item {

    private String color;
    private String style;
    private TableBody table;
    private Object layout;

    public Table color(String color) {

        this.color = color;
        return this;
    }

    public Table style(String style) {

        this.style = style;
        return this;
    }

    public Table table(TableBody table) {

        this.table = table;
        return this;
    }

    public Table layout(Object layout) {

        this.layout = layout;
        return this;
    }

    /**
     * Build a Table Element from table data, using tableheader and columns widths.
     *
     * @param columnWidths List of columns widths
     * @param tableHeader  List with the table header texts.
     * @param tableData    List of table rows
     * @param fontSize     Table fontSize.
     * @param hLineColor   String of Horizontal Lines Color.
     * @param vLineColor   String of Vertical Lines Color.
     * @param zebraStyle   Table zebra style.
     * @param fillColor    String of Row fill Color.
     * @return TableElement from table data, using tableheader and columns widths.
     */
    public static Table buildTable(List<Object> columnWidths, List<Item> tableHeader, List<List<String>> tableData,
                                   Integer fontSize, String hLineColor, String vLineColor, boolean zebraStyle,
                                   String fillColor) {

        if (hLineColor == null) {
            hLineColor = "#8EAADB";
        }

        if (vLineColor == null) {
            vLineColor = "#8EAADB";
        }

        if (fillColor == null) {
            fillColor = "#D9E2F3";
        }

        Table table = new Table();
        table.setLayout(new TableLayout(hLineColor, vLineColor));

        TableBody tableBody = new TableBody();
        tableBody.widths(columnWidths);

        List<List<Item>> body = new ArrayList<>();
        body.add(tableHeader);

        int i = 0;
        for (List<String> row : tableData) {
            List<Item> line = new ArrayList<>();
            for (String aRow : row) {
                if (zebraStyle) {
                    if (i % 2 == 0) {//Make zebra rows style
                        line.add(new TableCell(printValue(aRow), fontSize));
                    } else {
                        line.add(new TableCell(printValue(aRow), fillColor/*"#edf1f9"*/, fontSize));
                    }
                } else {
                    line.add(new TableCell(printValue(aRow), fontSize));
                }
            }
            body.add(line);
            i += 1;
        }

        tableBody.body(body);
        table.table(tableBody);

        return table;

    }

    /**
     * Build a Table Element from table data, using tableheader and columns widths.
     *
     * @param columnWidths List of columns widths
     * @param tableHeader  List with the table header texts.
     * @param tableData    List of table rows
     * @param hLineColor   String of Horizontal Lines Color.
     * @param vLineColor   String of Vertical Lines Color.
     * @return TableElement from table data, using tableheader and columns widths.
     */
    public static Table buildTable(List<Object> columnWidths, List<Item> tableHeader, List<List<Item>> tableData,
                                   String hLineColor, String vLineColor) {

        if (hLineColor == null) {
            hLineColor = "#8EAADB";
        }

        if (vLineColor == null) {
            vLineColor = "#8EAADB";
        }

        Table table = new Table();
        table.setLayout(new TableLayout(hLineColor, vLineColor));

        TableBody tableBody = new TableBody();
        tableBody.widths(columnWidths);

        List<List<Item>> body = new ArrayList<>();
        body.add(tableHeader);

        body.addAll(tableData);

        tableBody.body(body);
        table.table(tableBody);

        return table;

    }

    /**
     * Build a Table Element from table data, using tableheader and columns widths.
     *
     * @param columnWidths List of columns widths
     * @param tableHeader  List with the table header texts.
     * @param tableData    List of table rows
     * @param layout       Table Layout.
     * @return TableElement from table data, using tableheader and columns widths.
     */
    public static Table buildTable(List<Object> columnWidths, List<Item> tableHeader, List<List<Item>> tableData,
                                   Layout layout) {

        Table table = new Table();
        table.setLayout(layout);

        TableBody tableBody = new TableBody();
        tableBody.widths(columnWidths);

        List<List<Item>> body = new ArrayList<>();
        body.add(tableHeader);

        body.addAll(tableData);

        tableBody.body(body);
        table.table(tableBody);

        return table;

    }

    static private String printValue(Object item) {

        if (item instanceof Date) {
            return new SimpleDateFormat("MM/dd/yyyy").format(item);
        } else {
            return item.toString();
        }
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public TableBody getTable() {
        return table;
    }

    public void setTable(TableBody table) {
        this.table = table;
    }

    public Object getLayout() {
        return layout;
    }

    public void setLayout(Object layout) {
        this.layout = layout;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }
}

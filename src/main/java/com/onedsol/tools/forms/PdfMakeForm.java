package com.onedsol.tools.forms;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.onedsol.tools.forms.enumeration.DateTimeFormat;
import com.onedsol.tools.forms.enumeration.DateTimeFormatType;
import com.onedsol.tools.forms.enumeration.FieldType;
import com.onedsol.tools.jpdfmake.*;
import com.onedsol.tools.jpdfmake.enums.Alignment;
import com.onedsol.tools.jpdfmake.enums.Layout;
import com.onedsol.tools.jpdfmake.table.Table;
import com.onedsol.tools.jpdfmake.table.TableBody;
import com.onedsol.tools.jpdfmake.table.Width;
import com.onedsol.tools.util.PdfUtil;
import com.onedsol.tools.util.Substitutor;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PdfMakeForm {

    private static String checkedIcon = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAETSURBVFhH7ZbLDcIwEETTBCfgTpfQDh9xQ1AH0AaITwGwL4pRCP6s4z2hPGkEMZuZsbm4GhgIMBVtRA/Rq0ALUTaEX0Q+wxx1w0eNkrBzDHaiMQsGEHxulCzxFFFgUj+V48LxVBVwR2hBdjhYFegVDhYFeodDaYGicCgpkAqfN59RQgUwi+1GE67amK+AMz8137ukwsHn6yVU4ChivVtCEw4+Xy+hQYwJ5zdXQhsOId8fYoPtEgRqwyHm+0VqsF0CacIh5ftBM0igOwFNOJgWAIK14WBeIJehgNrX+kICXPPwvNdPCbYihvciXiwFj4MIzyULKWaiq4gXLMVFV32qtF6LODKfWY5uopXI8i8d+Buq6g2YPcB8AuJqbwAAAABJRU5ErkJggg==";
    private static String uncheckedIcon = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAB3SURBVFhH7ZZBCoAwEAP35KP0fSr0n/Ubmgi9FbZCxIMZyKGwhKGnhDEdJqQgFTlFYdeOsDuFh70SRdidciA8nu+XhgVhJ38ipdmqGe61gAUsYAELWMACFvhcoE0yzigVjybZm6N0RVI4nTdEPcvZOTTLzZ+IuABEAafJ6YhtqAAAAABJRU5ErkJggg==";

    static public List<Item> section(JsonObject section, int sectionFontSize, int textFontSize,
                                     HashMap<String, String> tokenHashMap) {

        List<Item> result = new ArrayList<>();

        String sectionTitle = section.get("title").getAsString();

        boolean sectionHideLabel = false;
        if (section.get("hideLabel") != null) {
            sectionHideLabel = section.get("hideLabel").getAsBoolean();
        }

        JsonArray sectionRows = section.get("rows").getAsJsonArray();

        //We add section title
        if (!sectionHideLabel) {
            Integer[] margin = {0, 0, 0, 10};
            result.add(new Text(sectionTitle).fontSize(sectionFontSize).bold(true).margin(margin));
        }

        //We add section rows
        for (int j = 0; j < sectionRows.size(); j++) {
            JsonObject row = sectionRows.get(j).getAsJsonObject();
            JsonArray cols = row.get("cols").getAsJsonArray();

            Column rowColumn = column(cols, textFontSize, tokenHashMap);

            result.add(rowColumn);
            result.add(PdfUtil.println());
        }

        return result;
    }

    static public Column column(JsonArray cols, int fontSize, HashMap<String, String> tokenHashMap) {

        Column column = new Column();
        column.columnGap(10);

        for (int k = 0; k <= cols.size() - 1; k++) {
            JsonObject col = cols.get(k).getAsJsonObject();
            JsonArray fields = col.get("fields").getAsJsonArray();

            if (fields.size() > 0) {

                JsonObject field = fields.get(0).getAsJsonObject();
                List<Item> columnContent = new ArrayList();

                columnContent.addAll(handleField(field, true, fontSize, tokenHashMap));

                if (columnContent.size() > 0) {
                    column.addColumn(columnContent);
                } else {
                    column.addColumn(new Text(" "));
                }
            }
        }
        return column;

    }

    /**
     * This method draw a label for a field with a font size.
     *
     * @param field    Field to draw a label.
     * @param fontSize Font size to draw.
     * @return Item with a label.
     */
    static private Item drawLabel(JsonObject field, int fontSize) {

        String fieldLabel = field.get("label").getAsString();
        boolean fieldHideLabel = false;
        if (field.get("hideLabel") != null) {
            fieldHideLabel = field.get("hideLabel").getAsBoolean();
        }

        Text text = new Text(fieldLabel + "\n\n").fontSize(fontSize).alignment(Alignment.left).bold(true);
        if (fieldHideLabel) {
            text = new Text("");
        }
        return text;
    }

    /**
     * This method handle a field with a type label.
     *
     * @param field    Field.
     * @param fontSize Font size to draw the field value.
     * @return Item with the field value.
     */
    static public Item label(JsonObject field, int fontSize) {

        String fieldLabel = field.get("label").getAsString();
        return new Text(fieldLabel + "\n\n").fontSize(fontSize).alignment(Alignment.left).bold(true);
    }

    /**
     * This method handle a field with a type Text.
     *
     * @param field     Field.
     * @param showLabel Show or hide the field label.
     * @param fontSize  Font size to draw the field value.
     * @return Item with the field value.
     */
    static public List<Item> text(JsonObject field, boolean showLabel, int fontSize) {

        JsonElement fieldValue = field.get("value");
        List<Item> result = new ArrayList<>();

        if (showLabel) {
            result.add(drawLabel(field, fontSize));
        }
        String valueText = "";
        if (fieldValue != null) {
            valueText = !fieldValue.isJsonNull() ? fieldValue.getAsString() : "";
        }
        result.add(new Text(valueText).alignment(Alignment.left).fontSize(fontSize));

        return result;
    }

    /**
     * This method handle a field with a type Select.
     *
     * @param field     Field.
     * @param showLabel Show or hide the field label.
     * @param fontSize  Font size to draw the field value.
     * @return Item with the field value.
     */
    static public List<Item> select(JsonObject field, boolean showLabel, int fontSize) {


        List<Item> result = new ArrayList<>();
        if (showLabel) {
            result.add(drawLabel(field, fontSize));
        }
        JsonElement fieldValue = field.get("value");
        //We get in options the value field.
        String titleField = field.get("titleField").getAsString();
        String value = fieldValue.isJsonObject() ?
                fieldValue.getAsJsonObject().get(titleField).getAsString() : "";

        if (value != null && !value.contentEquals("")) {
            Table table = new Table().layout(Layout.noBorders);
            TableBody tableBody = new TableBody();
            List<List<Item>> body = new ArrayList<>();
            List<Item> row = new ArrayList<>();
            row.add(new Image(checkedIcon, 15, 15));
            row.add(new Text(value).alignment(Alignment.left).fontSize(fontSize));
            body.add(row);
            tableBody.body(body);
            table.table(tableBody);
            result.add(table);
        } else {
            result.add(new Text(""));
        }
        return result;
    }

    /**
     * This method handle a field with a type MultiSelect.
     *
     * @param field     Field.
     * @param showLabel Show or hide the field label.
     * @param fontSize  Font size to draw the field value.
     * @return Item with the field value.
     */
    static public List<Item> multiSelect(JsonObject field, boolean showLabel, int fontSize) {

        JsonArray fieldValue = field.get("value").getAsJsonArray();
        //We get in options the value field.
        String titleField = field.get("titleField").getAsString();
        List<Item> result = new ArrayList<>();

        if (showLabel) {
            result.add(drawLabel(field, fontSize));
        }

        for (int i = 0; i < fieldValue.size(); i++) {

            String value = fieldValue.get(i).getAsJsonObject().get(titleField).getAsString();
            Table table = new Table().layout(Layout.noBorders);
            TableBody tableBody = new TableBody();
            List<List<Item>> body = new ArrayList<>();
            List<Item> row = new ArrayList<>();
            row.add(new Image(checkedIcon, 15, 15));
            row.add(new Text(value).alignment(Alignment.left).fontSize(fontSize));
            body.add(row);
            tableBody.body(body);
            table.table(tableBody);
            result.add(table);
        }

        return result;
    }

    /**
     * This method handle a field with a type RadioList.
     *
     * @param field     Field.
     * @param showLabel Show or hide the field label.
     * @param fontSize  Font size to draw the field value.
     * @return Item with the field value.
     */
    static public List<Item> radio(JsonObject field, boolean showLabel, int fontSize) {

        JsonElement fieldValue = field.get("value");
        JsonArray fieldOptions = field.get("options").getAsJsonArray();
        String value = "";
        List<Item> result = new ArrayList<>();

        if (showLabel) {
            result.add(drawLabel(field, fontSize));
        }

        String valueText = "";
        if (fieldValue != null) {
            valueText = !fieldValue.isJsonNull() ? fieldValue.getAsString() : "";
        }

        for (int i = 0; i < fieldOptions.size(); i++) {
            JsonObject option = fieldOptions.get(i).getAsJsonObject();
            if (option.get("id").getAsString().contentEquals(valueText)) {
                value = option.get("name").getAsString();
            }
        }

        Table table = new Table().layout(Layout.noBorders);
        TableBody tableBody = new TableBody();
        List<List<Item>> body = new ArrayList<>();
        List<Item> row = new ArrayList<>();
        row.add(new Image(checkedIcon, 15, 15));
        row.add(new Text(value).alignment(Alignment.left).fontSize(fontSize));
        body.add(row);
        tableBody.body(body);
        table.table(tableBody);
        result.add(table);

        return result;
    }

    /**
     * This method handle a field with a type DateTime.
     *
     * @param field     Field.
     * @param showLabel Show or hide the field label.
     * @param fontSize  Font size to draw the field value.
     * @return Item with the field value.
     */
    static public List<Item> dateTime(JsonObject field, boolean showLabel, int fontSize) {

        String fieldValue = field.get("value").getAsString();
        String fieldFormat = field.get("format").getAsString();
        List<Item> result = new ArrayList<>();

        if (showLabel) {
            result.add(drawLabel(field, fontSize));
        }

        String format = DateTimeFormat.SHORT_DATE_LONG_YEAR;

        switch (fieldFormat) {

            case DateTimeFormatType.FULL_DATE:
                format = DateTimeFormat.FULL_DATE;
                break;

            case DateTimeFormatType.LONG_DATE:
                format = DateTimeFormat.LONG_DATE;
                break;

            case DateTimeFormatType.MEDIUM:
                format = DateTimeFormat.MEDIUM;
                break;

            case DateTimeFormatType.MEDIUM_DATE:
                format = DateTimeFormat.MEDIUM_DATE;
                break;

            case DateTimeFormatType.MEDIUM_TIME:
                format = DateTimeFormat.MEDIUM_TIME;
                break;

            case DateTimeFormatType.SHORT:
                format = DateTimeFormat.SHORT;
                break;

            case DateTimeFormatType.SHORT_DATE:
                format = DateTimeFormat.SHORT_DATE;
                break;

            case DateTimeFormatType.SHORT_TIME:
                format = DateTimeFormat.SHORT_TIME;
                break;
        }

        if (fieldValue != null) {
            String value = formatDateTime(fieldValue, format);
            result.add(new Text(value).fontSize(fontSize).alignment(Alignment.left));
        } else {
            result.add(new Text(""));
        }

        return result;
    }

    /**
     * This method handle a field with a type CKEditor.
     *
     * @param field        Field.
     * @param showLabel    Show or hide the field label.
     * @param fontSize     Font size to draw the field value.
     * @param tokenHashMap A toke HashMap with values to replace.
     * @return Item with the field value.
     */
    static public List<Item> editor(JsonObject field, boolean showLabel, int fontSize,
                                    HashMap<String, String> tokenHashMap) {

        String fieldValue = field.get("value").toString();
        List<Item> result = new ArrayList<>();

        if (showLabel) {
            result.add(drawLabel(field, fontSize));
        }

        if (fieldValue != null) {
            String cleanText = clearText(fieldValue);
            String tokenApplied = Substitutor.textSubstitutor(tokenHashMap, cleanText);
            result.add(new Text(tokenApplied)
                    .fontSize(10)
                    .alignment(Alignment.justify)
                    .bold(false));
        } else {
            result.add(new Text(""));
        }

        return result;
    }

    /**
     * This method handle a field with a type IfYes.
     *
     * @param field     Field.
     * @param showLabel Show or hide the field label.
     * @param fontSize  Font size to draw the field value.
     * @return Item with the field value.
     */
    static public List<Item> ifYes(JsonObject field, boolean showLabel, int fontSize) {

        String fieldLabel = field.get("label").getAsString();
        JsonElement fieldValue = field.get("value");
        JsonObject toggleObject = fieldValue.getAsJsonObject();
        boolean toggle = toggleObject.get("toggle").getAsBoolean();
        String textarea = toggleObject.get("textarea").getAsString();
        List<Item> result = new ArrayList<>();

        Text label = new Text(fieldLabel + " ").fontSize(fontSize).alignment(Alignment.left).bold(true);
        Text value = new Text(toggle ? "Yes\n\n" : "No\n\n").fontSize(fontSize).alignment(Alignment.left);
        NestedText nestedText = new NestedText();
        nestedText.getText().add(label);
        nestedText.getText().add(value);

        if (showLabel) {
            result.add(nestedText);
        } else {
            boolean fieldHideLabel = false;
            if (field.get("hideLabel") != null) {
                fieldHideLabel = field.get("hideLabel").getAsBoolean();
            }
            if (!fieldHideLabel) {
                result.add(value);
            } else {
                result.add(nestedText);
            }
        }

        if (textarea != null && toggle) {
            result.add(
                    new Text(textarea)
                            .fontSize(fontSize)
                            .alignment(Alignment.left));
        }

        return result;
    }

    /**
     * This method handle a field with a type Checkbox.
     *
     * @param field     Field.
     * @param showLabel Show or hide the field label.
     * @param fontSize  Font size to draw the field value.
     * @return Item with the field value.
     */
    static public List<Item> checkbox(JsonObject field, boolean showLabel, int fontSize) {

        String fieldLabel = field.get("label").getAsString();
        boolean fieldValue = field.get("value").getAsBoolean();
        List<Item> result = new ArrayList<>();

        if (showLabel) {
            result.add(drawLabel(field, fontSize));
        }

        Table table = new Table().layout(Layout.noBorders);
        TableBody tableBody = new TableBody();
        List<List<Item>> body = new ArrayList<>();
        List<Item> row = new ArrayList<>();
        row.add(new Image(fieldValue ? checkedIcon : uncheckedIcon, 15, 15));
        row.add(new Text(fieldLabel).alignment(Alignment.left).fontSize(fontSize));
        body.add(row);
        tableBody.body(body);
        table.table(tableBody);
        result.add(table);

        return result;
    }

    /**
     * This method handle a field with a type Checkbox.
     *
     * @param field     Field.
     * @param showLabel Show or hide the field label.
     * @param fontSize  Font size to draw the field value.
     * @return Item with the field value.
     */
    static public List<Item> checkboxList(JsonObject field, boolean showLabel, int fontSize) {

        JsonElement fieldValue = field.get("value");
        boolean fieldInline = false;
        if (field.get("inline") != null) {
            fieldInline = field.get("inline").getAsBoolean();
        }

        List<Item> result = new ArrayList<>();

        if (showLabel) {
            result.add(drawLabel(field, fontSize));
        }

        Table table = new Table().layout(Layout.noBorders);
        TableBody tableBody = new TableBody();
        List<List<Item>> body = new ArrayList<>();

        if (field.get("options") != null) {
            JsonArray options = field.get("options").getAsJsonArray();
            if (fieldInline) {
                //if field is inline we add one row
                List<Item> row = new ArrayList<>();
                for (JsonElement option : options) {
                    String id = option.getAsJsonObject().get("id").getAsString();
                    buildRow(fontSize, fieldValue, row, option, id);
                }
                if (!row.isEmpty()) {
                    body.add(row);
                }
            } else {
                //if field is inline we add n rows
                for (JsonElement option : options) {
                    List<Item> row = new ArrayList<>();
                    String id = option.getAsJsonObject().get("id").getAsString();
                    buildRow(fontSize, fieldValue, row, option, id);
                    if (!row.isEmpty()) {
                        body.add(row);
                    }
                }
            }
        }

        if (!body.isEmpty()) {
            tableBody.body(body);
            table.table(tableBody);
            result.add(table);
        }

        return result;
    }

    /**
     * Utils method used in Checkbox list row creation.
     *
     * @param fontSize   Font size.
     * @param fieldValue Field value.
     * @param row        Row to create.
     * @param option     Checkbox current option object.
     * @param id         Option id field.
     */
    private static void buildRow(int fontSize, JsonElement fieldValue, List<Item> row,
                                 JsonElement option, String id) {

        if (fieldValue.getAsJsonObject().get(id) != null) {
            boolean value = fieldValue.getAsJsonObject().get(id).getAsBoolean();
            if (value) {
                row.add(new Image(value ? checkedIcon : uncheckedIcon, 15, 15));
                row.add(new Text(option.getAsJsonObject().get("name").getAsString())
                        .alignment(Alignment.left)
                        .fontSize(fontSize));
            }
        }
    }

    /**
     * This method handle a field with a type Toggle.
     *
     * @param field    Field.
     * @param fontSize Font size to draw the field value.
     * @return Item with the field value.
     */
    static public List<Item> toggle(JsonObject field, int fontSize) {

        String fieldLabel = field.get("label").getAsString();
        boolean fieldValue = field.get("value").getAsBoolean();
        List<Item> result = new ArrayList<>();

        Text label = new Text(fieldLabel + " ").fontSize(fontSize).alignment(Alignment.left).bold(true);
        Text value = new Text(fieldValue ? "Yes\n\n" : "No\n\n").fontSize(fontSize).alignment(Alignment.left);
        NestedText nestedText = new NestedText();
        nestedText.getText().add(label);
        nestedText.getText().add(value);

        result.add(nestedText);

        return result;
    }

    /**
     * This method handle a field with a type Table.
     *
     * @param field     Field.
     * @param showLabel Show or hide the field label.
     * @param fontSize  Font size to draw the field value.
     * @return Item with the field value.
     */
    static public List<Item> table(JsonObject field, boolean showLabel, int fontSize,
                                   HashMap<String, String> tokenHashMap) {

        List<Item> result = new ArrayList<>();
        JsonElement matrix = field.get("matrix");
        JsonArray rows = matrix.getAsJsonArray();

        if (showLabel) {
            result.add(drawLabel(field, fontSize));
        }

        if (rows.size() > 0) {
            Table table = new Table();
            TableBody tableBody = new TableBody();
            List<Object> widths = new ArrayList<>();
            List<List<Item>> body = new ArrayList<>();
            for (int i = 0; i < rows.size(); i++) {
                List<Item> resultRows = new ArrayList<>();
                JsonArray row = rows.get(i).getAsJsonArray();
                for (int j = 0; j < row.size(); j++) {
                    JsonObject col = row.get(j).getAsJsonObject();
                    //We get widths only from first row.
                    if (i == 0) {
                        if (col.get("width") != null) {
                            String tmp = col.get("width").getAsString() != null ?
                                    col.get("width").getAsString() : "";
                            String width = tmp.contentEquals("") ?
                                    Width.WIDTH_SPACE_EQUALLY : Width.WIDTH_AUTO;
                            widths.add(width);
                        } else {
                            widths.add(Width.WIDTH_SPACE_EQUALLY);
                        }
                    }
                    JsonArray fields = col.get("fields").getAsJsonArray();
                    if (fields.size() > 0) {
                        JsonObject colField = fields.get(0).getAsJsonObject();
                        resultRows.addAll(handleField(colField, false, fontSize, tokenHashMap));
                    } else {
                        resultRows.add(new Text("").fontSize(10).alignment(Alignment.left));
                    }
                }
                body.add(resultRows);
            }
            tableBody.setWidths(widths);
            tableBody.setBody(body);
            table.setTable(tableBody);
            result.add(table);
        }

        return result;

    }

    /**
     * This method handle a fields.
     *
     * @param field        Field.
     * @param showLabel    Show or hide the field label.
     * @param fontSize     Font size to draw the field value.
     * @param tokenHashMap A toke HashMap with values to replace.
     * @return Item with the handled field.
     */
    private static List<Item> handleField(JsonObject field, boolean showLabel, int fontSize,
                                          HashMap<String, String> tokenHashMap) {

        List<Item> result = new ArrayList<>();
        String type;
        type = field.get("type").getAsString();

        switch (type) {
            case FieldType.LABEL:
                result.add(label(field, fontSize));
                break;
            case FieldType.TEXTAREA:
                result.addAll(text(field, showLabel, fontSize));
                break;
            case FieldType.TEXT:
                result.addAll(text(field, showLabel, fontSize));
                break;
            case FieldType.NUMBER:
                result.addAll(text(field, showLabel, fontSize));
                break;
            case FieldType.SELECT:
                result.addAll(select(field, showLabel, fontSize));
                break;
            case FieldType.SELECT2:
                result.addAll(select(field, showLabel, fontSize));
                break;
            case FieldType.MULTI_SELECT:
                result.addAll(multiSelect(field, showLabel, fontSize));
                break;
            case FieldType.DATETIME:
                result.addAll(dateTime(field, showLabel, fontSize));
                break;
            case FieldType.RADIO:
                result.addAll(radio(field, showLabel, fontSize));
                break;
            case FieldType.TOGGLE:
                result.addAll(toggle(field, fontSize));
                break;
            case FieldType.CHECKBOX:
                result.addAll(checkbox(field, showLabel, fontSize));
                break;
            case FieldType.CHECKBOX_LIST:
                result.addAll(checkboxList(field, showLabel, fontSize));
                break;
            case FieldType.TABLE:
                result.addAll(table(field, showLabel, fontSize, tokenHashMap));
                break;
            case FieldType.IF_YES:
                result.addAll(ifYes(field, showLabel, fontSize));
                break;
            case FieldType.CKEDITOR:
                result.addAll(editor(field, showLabel, fontSize, tokenHashMap));
                break;
        }

        return result;
    }

    /**
     * Util method for DateTime handle.
     *
     * @param dateAsString DateTime as String.
     * @param pattern      DateTime pattern to show.
     * @return DateTime formatted with the passed pattern.
     */
    private static String formatDateTime(String dateAsString, String pattern) {

        ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateAsString);
        Date date = zonedDateTimeToDateConverter(zonedDateTime);
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }

    /**
     * Util method to convert ZonedDateTime to Date.
     *
     * @param source ZoneDateTime source.
     * @return Date converted.
     */
    private static Date zonedDateTimeToDateConverter(ZonedDateTime source) {

        return source == null ? null : Date.from(source.toInstant());
    }

    /**
     * Util method useful for text clearing in Editor fields.
     *
     * @param text Text to clear.
     * @return Text cleared.
     */
    private static String clearText(String text) {

        Pattern removeTags = Pattern.compile("<.+?>");
        if (text == null || text.length() == 0) {
            return text;
        }
        Matcher m = removeTags.matcher(text);
        String tmp = m.replaceAll("")
                .replace("&nbsp;", " ")
                .replace("&bull;", " ")
                .replace("\\n", "")
                .replace("\\r", "");

        return tmp.substring(1, tmp.length() - 1);
    }

}

package com.onedsol.tools.util.report;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A LogoDTO.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Param implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private String type;

    private List<String> value;

    public List<String> getValue() {
        return value;
    }

    public void setValue(List<String> value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getSmartValue() {

        if (value != null) {
            switch (type) {
                case ParamType.DATE:
                    return LocalDate.parse(value.get(0));
                case ParamType.TEXT:
                    return String.valueOf(value.get(0));
                case ParamType.NUMBER:
                    return new Long(value.get(0));
                case ParamType.LIST:
                    return new Long(value.get(0));
                case ParamType.SINGLE_SELECT:
                    return new String(value.get(0));
                case ParamType.MULTI_SELECT:
                    return new ParamList(value);
                case ParamType.TABLE_SELECT:
                    return new ParamList(value);
                case ParamType.DRAG_AND_DROP:
                    return new ParamList(value);
                default:
                    return value;
            }
        } else {
            return null;
        }
    }

    /**
     * Get param value as long, throws exception if value can be casted to Long,
     * or null if values are list.
     *
     * @return Long value
     * @throws ExceptionParamType Exception with wrong param type
     */
    public Long getAsLong() throws ExceptionParamType {

        if (value != null) {
            switch (type) {
                case ParamType.DATE:
                    throw new ExceptionParamType("LocalDate can not be converted to Long");
                case ParamType.TEXT:
                    throw new ExceptionParamType("Text can not be converted to Long");
                case ParamType.NUMBER:
                    return value.get(0) == null ? null : new Long(value.get(0));
                case ParamType.LIST:
                    return new Long(value.get(0));
                case ParamType.SINGLE_SELECT:
                    return new Long(value.get(0));
                case ParamType.MULTI_SELECT:
                    throw new ExceptionParamType("MultiSelect can not be converted to Long");
                case ParamType.TABLE_SELECT:
                    throw new ExceptionParamType("Table can not be converted to Long");
                case ParamType.DRAG_AND_DROP:
                    throw new ExceptionParamType("Drag and drop can not be converted to Long");
                default:
                    return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Get param value as list of long, throws exception if value can be casted to Long,
     * or null if values are list.
     *
     * @return List Long values
     * @throws ExceptionParamType Exception with wrong param type
     */
    public List<Long> getAsLongList() throws ExceptionParamType {

        List<Long> tmpValue;

        if (value != null) {
            switch (type) {
                case ParamType.DATE:
                    throw new ExceptionParamType("LocalDate can not be converted to List<Long>");
                case ParamType.TEXT:
                    throw new ExceptionParamType("Text can not be converted to List<Long>");
                case ParamType.NUMBER:
                    return Arrays.asList(new Long(value.get(0)));
                case ParamType.LIST:
                    return Arrays.asList(new Long(value.get(0)));
                case ParamType.SINGLE_SELECT:
                    return Arrays.asList(new Long(value.get(0)));
                case ParamType.MULTI_SELECT:
                    return getLongList();
                case ParamType.TABLE_SELECT:
                    return getLongList();
                case ParamType.DRAG_AND_DROP:
                    return getLongList();
                default:
                    return null;
            }
        } else {
            return null;
        }
    }

    private List<Long> getLongList() {

        List<Long> tmpValue = new ArrayList<>();
        for (String itemValue : value) {
            tmpValue.add(Long.valueOf(itemValue));
        }
        return tmpValue;
    }

    /**
     * Get param value as list of String, throws exception if value can be casted to String,
     * or null if values are list.
     *
     * @return List of String values
     * @throws ExceptionParamType Exception with wrong param type
     */
    public List<String> getAsStringList() throws ExceptionParamType {

        if (value != null) {
            switch (type) {
                case ParamType.DATE:
                    return value;
                case ParamType.TEXT:
                    return value;
                case ParamType.NUMBER:
                    return value;
                case ParamType.LIST:
                    return value;
                case ParamType.SINGLE_SELECT:
                    return value;
                case ParamType.MULTI_SELECT:
                    return value;
                case ParamType.TABLE_SELECT:
                    return value;
                case ParamType.DRAG_AND_DROP:
                    return value;
                default:
                    return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Get param value as LocalDate, throws exception if value can be casted to Long,
     * or null if values are list.
     *
     * @return LocalDate value
     * @throws ExceptionParamType Exception with wrong param type
     */
    public LocalDate getAsLocalDate() throws ExceptionParamType {

        if (value != null) {
            switch (type) {
                case ParamType.DATE:
                    return LocalDate.parse(value.get(0));
                case ParamType.TEXT:
                    return LocalDate.parse(value.get(0));
                case ParamType.NUMBER:
                    throw new ExceptionParamType("Number can not be converted to LocalDate");
                case ParamType.LIST:
                    return LocalDate.parse(value.get(0));
                case ParamType.SINGLE_SELECT:
                    return LocalDate.parse(value.get(0));
                case ParamType.MULTI_SELECT:
                    throw new ExceptionParamType("List can not be converted to LocalDate");
                case ParamType.TABLE_SELECT:
                    throw new ExceptionParamType("Table can not be converted to LocalDate");
                case ParamType.DRAG_AND_DROP:
                    throw new ExceptionParamType("Drag and drop can not be converted to LocalDate");
                default:
                    return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Get param value as Boolean, throws exception if value can be casted to Long,
     * or null if values are list.
     *
     * @return Boolean value
     * @throws ExceptionParamType Exception with wrong param type
     */
    public Boolean getAsBoolean() throws ExceptionParamType {

        if (value != null) {
            switch (type) {
                case ParamType.DATE:
                    throw new ExceptionParamType("LocalDate can not be converted to Boolean");
                case ParamType.TEXT:
                    return Boolean.valueOf(value.get(0));
                case ParamType.NUMBER:
                    return Boolean.valueOf(value.get(0));
                case ParamType.LIST:
                    return Boolean.valueOf(value.get(0));
//                    throw new ExceptionParamType("List can not be converted to Boolean");
                case ParamType.SINGLE_SELECT:
                    return Boolean.valueOf(value.get(0));
                case ParamType.MULTI_SELECT:
                    throw new ExceptionParamType("MultiSelect can not be converted to Boolean");
                case ParamType.TABLE_SELECT:
                    throw new ExceptionParamType("Table can not be converted to Boolean");
                case ParamType.DRAG_AND_DROP:
                    throw new ExceptionParamType("Drag and drop can not be converted to Boolean");
                default:
                    return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Get param value as Array of int, throws exception if value can be casted to,
     * or null error occurs.
     *
     * @return int[] value
     * @throws ExceptionParamType Exception with wrong param type
     */
    public int[] getAsIntArray() throws ExceptionParamType {

        if (value != null) {
            switch (type) {
                case ParamType.DATE:
                    throw new ExceptionParamType("LocalDate can not be converted to int[]");
                case ParamType.TEXT:
                    return buildIntArray(value);
                case ParamType.NUMBER:
                    return buildIntArray(value);
                case ParamType.LIST:
                    return buildIntArray(value);
                case ParamType.SINGLE_SELECT:
                    return buildIntArray(value);
                case ParamType.MULTI_SELECT:
                    return buildIntArray(value);
                case ParamType.TABLE_SELECT:
                    return buildIntArray(value);
                case ParamType.DRAG_AND_DROP:
                    return buildIntArray(value);
                default:
                    return null;
            }
        } else {
            return null;
        }
    }

    private int[] buildIntArray(List<String> value) {
        int[] result = new int[value.size()];
        for (int i = 0; i < value.size(); i++) {
            result[i] = Integer.valueOf(value.get(i));
        }
        return result;
    }

}

package com.onedsol.tools.utils.report;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A LogoDTO.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ParamValue implements Serializable {

    private static final long serialVersionUID = 1L;

    private Object value;

    public ParamValue() {
    }

    public ParamValue(Long value) {

        this.value = value;
    }

    public ParamValue(String value) {

        this.value = value;
    }

    public ParamValue(LocalDate value) {

        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}

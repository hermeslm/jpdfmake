package com.onedsol.tools.utils.report;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A LogoDTO.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ParamList implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<String> value;

    public ParamList(List<String> value) {
        this.value = value;
    }

    public List<String> getValue() {
        return value;
    }

    public void setValue(List<String> value) {
        this.value = value;
    }

    /**
     * Return values as Long
     *
     * @return List long values.
     * @throws Exception Exception on cast operation
     */
    public List<Long> getValuesAsLong() throws Exception {

        List<Long> result = new ArrayList<>();
        for (String item : value) {
            result.add(Long.valueOf(item));
        }

        return result;
    }

    /**
     * Return values as Integer
     *
     * @return List Integer values.
     * @throws Exception Exception on cast operation
     */
    public List<Integer> getValuesAsInteger() throws Exception {

        List<Integer> result = new ArrayList<>();
        for (String item : value) {
            result.add(Integer.valueOf(item));
        }

        return result;
    }

    /**
     * Return values as Integer
     *
     * @return Int array values.
     * @throws Exception Exception on cast operation
     */
    public int[] getValuesAsInt() throws Exception {

        int[] result = new int[value.size()];
        for (int i = 0; i < value.size(); i++) {
            result[i] = Integer.valueOf(value.get(i));
        }

        return result;
    }

}

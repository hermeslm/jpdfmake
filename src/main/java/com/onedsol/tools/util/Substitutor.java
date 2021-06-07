package com.onedsol.tools.util;

import org.apache.commons.lang3.text.StrSubstitutor;

import java.util.Map;

public class Substitutor {

    public static String textSubstitutor(Map valuesMap, String toReplaceString) {

        StrSubstitutor sub = new StrSubstitutor(valuesMap);
        return sub.replace(toReplaceString);
    }
}

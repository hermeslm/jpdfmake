package com.onedsol.tools.jpdfmake;

import org.junit.Test;

/**
 * Created by hermeslm on 4/13/17.
 */
public class StyleElementTest {

    @Test
    public void toStringTest() {

        StyleElement styleElement = new StyleElement();
        styleElement.setName("header");
        styleElement.setFontSize(18);
        styleElement.setBold(true);

        System.out.println(styleElement);
    }

}

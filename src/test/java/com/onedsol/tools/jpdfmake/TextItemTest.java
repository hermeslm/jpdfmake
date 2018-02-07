package com.onedsol.tools.jpdfmake;

import org.junit.Test;

/**
 * Created by hermeslm on 4/13/17.
 */
public class TextItemTest {
    @Test
    public void toStringTest() {

        TextItem textItem = new TextItem();
        textItem.setText("First paragraph");

        System.out.println(textItem);
    }

}

package com.onedsol.tools.jpdfmake;

import org.junit.Test;

/**
 * Created by hermeslm on 4/13/17.
 */
public class TextTest {
    @Test
    public void toStringTest() {

        Text text = new Text();
        text.setText("First paragraph");

        System.out.println(text);
    }

}

package com.onedsol.tools.jpdfmake;

import com.onedsol.tools.jpdfmake.StyleElement;
import com.onedsol.tools.jpdfmake.TextItem;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hermeslm on 4/13/17.
 */
public class ElementTest {
    @Test
    public void toStringTest() {

        StyleElement styleElement = new StyleElement();
        styleElement.setName("header");
        styleElement.setFontSize(18);
        styleElement.setBold(true);

        List<String> styles = new ArrayList<>();
        styles.add(styleElement.getName());

        TextItem textItem = new TextItem();
        textItem.setText("First paragraph");

        System.out.println(textItem);
    }

}

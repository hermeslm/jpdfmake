package com.onedsol.tools.jpdfmake;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onedsol.tools.jpdfmake.table.TableBody;
import com.onedsol.tools.jpdfmake.table.TableElement;
import com.onedsol.tools.jpdfmake.table.TableItem;
import com.onedsol.tools.utils.PdfUtil;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * Created by hermeslm on 4/13/17.
 */
public class PdfTest {

    @Test
    public void getNotDataReportTest() {

        Pdf pdf = new Pdf(new ArrayList<>());

        pdf.getContent().add(PdfUtil.getNotDataTable());

        ObjectMapper mapper = new ObjectMapper();

        String jsonInString = null;
        try {
            jsonInString = mapper.writeValueAsString(pdf);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println(jsonInString);
    }

    @Test
    public void imageTest() {

        try {
            BufferedImage img = null;
            img = ImageIO.read(new File("/tmp/linear_gauge.png"));
            Graphics2D rectangle = img.createGraphics();
            rectangle.setColor(Color.BLACK);
            rectangle.fill(new Rectangle(100, 0, 4, img.getHeight()));
            rectangle.dispose();

            Graphics2D circle = img.createGraphics();
            circle.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            circle.setPaint(Color.BLACK);
            circle.fillOval(100 + (4 / 2) - 12, img.getHeight() / 2 - 12, 24, 24);
            circle.dispose();

            File outputFile = new File("/tmp/linear_gauge_saved.png");
            ImageIO.write(img, "png", outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void tableTest() {

        List<ContentElement> content = new ArrayList<>();


        Pdf pdf = new Pdf();
        pdf.setContent(content);

        /**
         * Table 4 columns and width property
         */

        TableElement tableElement = new TableElement();
        tableElement.setLayout(new LayoutElement("#8EAADB", "#8EAADB"));
//        tableElement.setColor("#876543");

        TableBody tableBody = new TableBody();
        List<Object> widths = new ArrayList<>();
        widths.add(new Integer(200));
        widths.add(new String(Width.WIDTH_SPACE_EQUALLY));
        widths.add(new String(Width.WIDTH_SPACE_EQUALLY));
        widths.add(new String(Width.WIDTH_AUTO));
        tableBody.setWidths(widths);
        tableElement.setTable(tableBody);

        List<List<Element>> body = new ArrayList<List<Element>>();
        List<Element> header = new ArrayList<>();
        header.add(new TableItem("Header Col1", "#8EAADB", "#FFFFFF"));
        header.add(new TableItem("Header Col2", "#8EAADB", "#FFFFFF"));
        header.add(new TableItem("Header Col3", "#8EAADB", "#FFFFFF"));
        header.add(new TableItem("Header Col4", "#8EAADB", "#FFFFFF"));
        body.add(header);

        List<Element> row1 = new ArrayList<>();

        row1.add(new TableItem("Value Col1 Row1"));
        row1.add(new TableItem("Value Col2 Row1"));
        row1.add(new TableItem("Value Col3 Row1"));
        row1.add(new TableItem("Value Col4 Row1"));
        body.add(row1);

        List<Element> row2 = new ArrayList<>();
        row2.add(new TableItem("Value Col1 Row2", "#D9E2F3"));
        row2.add(new TableItem("Value Col2 Row2", "#D9E2F3"));
        row2.add(new TableItem("Value Col3 Row2", "#D9E2F3"));
        row2.add(new TableItem("Value Col4 Row2", "#D9E2F3"));
        body.add(row2);

        List<Element> row3 = new ArrayList<>();
        row3.add(new TableItem("Value Col1 Row3"));
        row3.add(new TableItem("Value Col2 Row3"));
        row3.add(new TableItem("Value Col3 Row3"));
        row3.add(new TableItem("Value Col4 Row3"));
        body.add(row3);

        tableBody.setBody(body);
        content.add(tableElement);

        ObjectMapper mapper = new ObjectMapper();

        String jsonInString = null;
        try {
            jsonInString = mapper.writeValueAsString(pdf);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println(jsonInString);
    }

    @Test
    public void headerTest() {

        TextItem ln = new TextItem("\n");
        List<ContentElement> content = new ArrayList<>();

        /**
         * Header
         */
        String companyInfo = "United Clinical Laboratory\n" +
                "2257 Vista Parkway Suite 2\n" +
                "West Palm Beach, FL 33411\n" +
                "Phone: (844) 291-4292\n";
        ExtendedTextItem companyColumn = new ExtendedTextItem(companyInfo, 100,
                8, Alignment.center);

        String reportName = "Diabetes Management";
        List<String> headerStyles = new ArrayList<>();
        headerStyles.add("header");
        ExtendedTextItem reportTitleColumn = new ExtendedTextItem(reportName, 22, true,
                Alignment.center, "#2F5496");

        String logo = null;
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("lab/logo.png").getFile());
            byte[] encoded;
            encoded = Base64.getEncoder().encode(FileUtils.readFileToByteArray(file));
            //TODO read content type for this image.
            logo = "data:image/png;base64," + new String(encoded);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ImageItem reportLogo = new ImageItem(logo, 100, 50);

        ColumnElement columnItem = new ColumnElement();
        columnItem.getColumns().add(companyColumn);
        columnItem.getColumns().add(reportTitleColumn);
        columnItem.getColumns().add(reportLogo);

        content.add(columnItem);
        content.add(ln);

        Pdf pdf = new Pdf();
        pdf.setContent(content);

        ObjectMapper mapper = new ObjectMapper();

        String jsonInString = null;
        try {
            jsonInString = mapper.writeValueAsString(pdf);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println(jsonInString);
    }

    @Test
    public void toStringTest() {

        TextItem ln = new TextItem("\n");

        StyleElement styleElement = new StyleElement();
        styleElement.setName("header");
        styleElement.setFontSize(18);
        styleElement.setBold(true);

        /**
         * Paragraph sample 1 y 2
         */

        TextItem textItem = new TextItem("First paragraph");

        TextItem textItem1 = new TextItem();
        textItem1.setText("Another paragraph, this time a little bit longer to make sure, this line will be divided into at least two lines");

        List<ContentElement> content = new ArrayList<>();
        content.add(textItem);
        content.add(ln);
        content.add(textItem1);
        content.add(ln);

        /**
         * Paragraph sample 3 with nested text
         */

        TextItem secondParagraph = new TextItem("Second paragraph");

        List<Element> textElements = new ArrayList<>();
        textElements.add(secondParagraph);

        NestedTextItem extendedTextItem = new NestedTextItem();
        extendedTextItem.setText(textElements);

        content.add(extendedTextItem);
        content.add(ln);

        /**
         * Columns sample 1 with 2 columns justified
         */

        TextItem firstColumn = new TextItem("First Column Lorem ipsum dolor sit amet, consectetur adipisicing elit. Malit profecta versatur nomine ocurreret multavit, officiis viveremus aeternum superstitio suspicor alia nostram, quando nostros congressus susceperant concederetur leguntur iam, vigiliae democritea tantopere causae, atilii plerumque ipsas potitur pertineant multis rem quaeri pro, legendum didicisse credere ex maluisset per videtis. Cur discordans praetereat aliae ruinae dirigentur orestem eodem, praetermittenda divinum. Collegisti, deteriora malint loquuntur officii cotidie finitas referri doleamus ambigua acute. Adhaesiones ratione beate arbitraretur detractis perdiscere, constituant hostis polyaeno. Diu concederetur.");
        TextItem secondColumn = new TextItem("Second Column Lorem ipsum dolor sit amet, consectetur adipisicing elit. Malit profecta versatur nomine ocurreret multavit, officiis viveremus aeternum superstitio suspicor alia nostram, quando nostros congressus susceperant concederetur leguntur iam, vigiliae democritea tantopere causae, atilii plerumque ipsas potitur pertineant multis rem quaeri pro, legendum didicisse credere ex maluisset per videtis. Cur discordans praetereat aliae ruinae dirigentur orestem eodem, praetermittenda divinum. Collegisti, deteriora malint loquuntur officii cotidie finitas referri doleamus ambigua acute. Adhaesiones ratione beate arbitraretur detractis perdiscere, constituant hostis polyaeno. Diu concederetur.");

        List<Element> columnsTextElements = new ArrayList<>();
        columnsTextElements.add(firstColumn);
        columnsTextElements.add(secondColumn);

        NestedTextItem columnNestedTextItem1 = new NestedTextItem();
        columnNestedTextItem1.setText(columnsTextElements);

        ColumnElement columnItem = new ColumnElement();
        columnItem.setColumns(columnsTextElements);
        columnItem.setAlignment(Alignment.justify.name());

        content.add(columnItem);
        content.add(ln);

        /**
         * Columns sample 2 with 4 columns and width property
         */
        ExtendedTextItem columnExtendedTextItem1 = new ExtendedTextItem("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Malit profecta versatur nomine ocurreret multavit.");
        columnExtendedTextItem1.setWidth(90);
        columnExtendedTextItem1.setBold(true);

        ExtendedTextItem columnExtendedTextItem2 = new ExtendedTextItem("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Malit profecta versatur nomine ocurreret multavit, officiis viveremus aeternum superstitio suspicor alia nostram, quando nostros congressus susceperant concederetur leguntur iam, vigiliae democritea tantopere causae, atilii plerumque ipsas potitur pertineant multis rem quaeri pro, legendum didicisse credere ex maluisset per videtis. Cur discordans praetereat aliae ruinae dirigentur orestem eodem, praetermittenda divinum. Collegisti, deteriora malint loquuntur officii cotidie finitas referri doleamus ambigua acute. Adhaesiones ratione beate arbitraretur detractis perdiscere, constituant hostis polyaeno. Diu concederetur.");
        columnExtendedTextItem2.setWidth(Width.WIDTH_SPACE_EQUALLY);

        ExtendedTextItem columnExtendedTextItem3 = new ExtendedTextItem("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Malit profecta versatur nomine ocurreret multavit, officiis viveremus aeternum superstitio suspicor alia nostram, quando nostros congressus susceperant concederetur leguntur iam, vigiliae democritea tantopere causae, atilii plerumque ipsas potitur pertineant multis rem quaeri pro, legendum didicisse credere ex maluisset per videtis. Cur discordans praetereat aliae ruinae dirigentur orestem eodem, praetermittenda divinum. Collegisti, deteriora malint loquuntur officii cotidie finitas referri doleamus ambigua acute. Adhaesiones ratione beate arbitraretur detractis perdiscere, constituant hostis polyaeno. Diu concederetur.");
        columnExtendedTextItem3.setWidth(Width.WIDTH_SPACE_EQUALLY);

        List<Element> columnsElements2 = new ArrayList<>();
        columnsElements2.add(columnExtendedTextItem1);
        columnsElements2.add(columnExtendedTextItem2);
        columnsElements2.add(columnExtendedTextItem3);

        ColumnElement columnItem2 = new ColumnElement();
        columnItem2.setColumns(columnsElements2);
        content.add(columnItem2);
        content.add(ln);

        Pdf pdf = new Pdf();
        pdf.setContent(content);

        DefaultStyle defaultStyle = new DefaultStyle();
        defaultStyle.setColumnGap(20);
        pdf.setDefaultStyle(defaultStyle);

        ObjectMapper mapper = new ObjectMapper();

        String jsonInString = null;
        try {
            jsonInString = mapper.writeValueAsString(pdf);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println(jsonInString);

    }

}

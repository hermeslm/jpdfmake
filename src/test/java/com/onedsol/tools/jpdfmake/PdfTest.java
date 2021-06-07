package com.onedsol.tools.jpdfmake;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onedsol.tools.jpdfmake.enums.Alignment;
import com.onedsol.tools.jpdfmake.table.*;
import com.onedsol.tools.util.PdfUtil;
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

    Pdf pdf = new Pdf();

    pdf.getContent().add(PdfUtil.tableNoData());

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

    List<Object> content = new ArrayList<>();


    Pdf pdf = new Pdf();
    pdf.setContent(content);

    /**
     * Table 4 columns and width property
     */

    Table tableElement = new Table();
    tableElement.setLayout(new TableLayout("#8EAADB", "#8EAADB"));
//        tableElement.setColor("#876543");

    TableBody tableBody = new TableBody();
    List<Object> widths = new ArrayList<>();
    widths.add(new Integer(200));
    widths.add(new String(Width.WIDTH_SPACE_EQUALLY));
    widths.add(new String(Width.WIDTH_SPACE_EQUALLY));
    widths.add(new String(Width.WIDTH_AUTO));
    tableBody.setWidths(widths);
    tableElement.setTable(tableBody);

    List<List<Item>> body = new ArrayList<>();
    List<Item> header = new ArrayList<>();
    header.add(new TableCell("Header Col1").fillColor("#8EAADB").color("#FFFFFF"));
    header.add(new TableCell("Header Col2").fillColor("#8EAADB").color("#FFFFFF"));
    header.add(new TableCell("Header Col3").fillColor("#8EAADB").color("#FFFFFF"));
    header.add(new TableCell("Header Col4").fillColor("#8EAADB").color("#FFFFFF"));
    body.add(header);

    List<Item> row1 = new ArrayList<>();

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

    row1.add(new Image().image(logo).width(200));
    row1.add(new TableCell("Value Col2 Row1"));
    row1.add(new TableCell("Value Col3 Row1"));
    row1.add(new TableCell("Value Col4 Row1"));
    body.add(row1);

    List<Item> row2 = new ArrayList<>();
    row2.add(new TableCell("Value Col1 Row2").color("#D9E2F3"));
    row2.add(new TableCell("Value Col2 Row2").color("#D9E2F3"));
    row2.add(new TableCell("Value Col3 Row2").color("#D9E2F3"));
    row2.add(new TableCell("Value Col4 Row2").color("#D9E2F3"));
    body.add(row2);

    List<Item> row3 = new ArrayList<>();
    row3.add(new TableCell("Value Col1 Row3"));
    row3.add(new TableCell("Value Col2 Row3"));
    row3.add(new TableCell("Value Col3 Row3"));
    row3.add(new TableCell("Value Col4 Row3"));
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

    List<Object> content = new ArrayList<>();

    /**
     * Header
     */
    String companyInfo = "United Clinical Laboratory\n" +
      "2257 Vista Parkway Suite 2\n" +
      "West Palm Beach, FL 33411\n" +
      "Phone: (844) 291-4292\n";
    Text companyColumn = new Text(companyInfo).width(100)
                                              .fontSize(8)
                                              .alignment(Alignment.center);

    String reportName = "Diabetes Management";
    List<String> headerStyles = new ArrayList<>();
    headerStyles.add("header");
    Text reportTitleColumn = new Text(reportName).fontSize(22)
                                                 .bold(true)
                                                 .alignment(Alignment.center)
                                                 .color("#2F5496");

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

    Image reportLogo = new Image(logo, 100, 50);

    Column columnItem = new Column();
    columnItem.addColumn(companyColumn);
    columnItem.addColumn(reportTitleColumn);
    columnItem.addColumn(reportLogo);

    content.add(columnItem);
    content.add(PdfUtil.println());

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

    Style style = new Style();
//        style.setName("header");
    style.setFontSize(18);
    style.setBold(true);

    /**
     * Paragraph sample 1 y 2
     */

    Text text = new Text("First paragraph");

    Text text1 = new Text();
    text1.setText("Another paragraph, this time a little bit longer to make sure, this line will be divided into at least two lines");

    List<Object> content = new ArrayList<>();
    content.add(text);
    content.add(PdfUtil.println());
    content.add(text1);
    content.add(PdfUtil.println());

    /**
     * Paragraph sample 3 with nested text
     */

    Text secondParagraph = new Text("Second paragraph");

    List<Item> textElements = new ArrayList<>();
    textElements.add(secondParagraph);

    NestedText extendedTextItem = new NestedText();
    extendedTextItem.setText(textElements);

    content.add(extendedTextItem);
    content.add(PdfUtil.println());

    /**
     * Columns sample 1 with 2 columns justified
     */

    Text firstColumn = new Text("First Column Lorem ipsum dolor sit amet, consectetur adipisicing elit. Malit profecta versatur nomine ocurreret multavit, officiis viveremus aeternum superstitio suspicor alia nostram, quando nostros congressus susceperant concederetur leguntur iam, vigiliae democritea tantopere causae, atilii plerumque ipsas potitur pertineant multis rem quaeri pro, legendum didicisse credere ex maluisset per videtis. Cur discordans praetereat aliae ruinae dirigentur orestem eodem, praetermittenda divinum. Collegisti, deteriora malint loquuntur officii cotidie finitas referri doleamus ambigua acute. Adhaesiones ratione beate arbitraretur detractis perdiscere, constituant hostis polyaeno. Diu concederetur.");
    Text secondColumn = new Text("Second Column Lorem ipsum dolor sit amet, consectetur adipisicing elit. Malit profecta versatur nomine ocurreret multavit, officiis viveremus aeternum superstitio suspicor alia nostram, quando nostros congressus susceperant concederetur leguntur iam, vigiliae democritea tantopere causae, atilii plerumque ipsas potitur pertineant multis rem quaeri pro, legendum didicisse credere ex maluisset per videtis. Cur discordans praetereat aliae ruinae dirigentur orestem eodem, praetermittenda divinum. Collegisti, deteriora malint loquuntur officii cotidie finitas referri doleamus ambigua acute. Adhaesiones ratione beate arbitraretur detractis perdiscere, constituant hostis polyaeno. Diu concederetur.");

    List<Item> columnsTextElements = new ArrayList<>();
    columnsTextElements.add(firstColumn);
    columnsTextElements.add(secondColumn);

    NestedText columnNestedText1 = new NestedText();
    columnNestedText1.setText(columnsTextElements);

    Column columnItem = new Column();
    columnItem.addColumn(columnsTextElements);
    columnItem.setAlignment(Alignment.justify.name());

    content.add(columnItem);
    content.add(PdfUtil.println());

    /**
     * Columns sample 2 with 4 columns and width property
     */
    Text columnExtendedTextItem1 = new Text("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Malit profecta versatur nomine ocurreret multavit.");
    columnExtendedTextItem1.setWidth(90);
    columnExtendedTextItem1.setBold(true);

    Text columnExtendedTextItem2 = new Text("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Malit profecta versatur nomine ocurreret multavit, officiis viveremus aeternum superstitio suspicor alia nostram, quando nostros congressus susceperant concederetur leguntur iam, vigiliae democritea tantopere causae, atilii plerumque ipsas potitur pertineant multis rem quaeri pro, legendum didicisse credere ex maluisset per videtis. Cur discordans praetereat aliae ruinae dirigentur orestem eodem, praetermittenda divinum. Collegisti, deteriora malint loquuntur officii cotidie finitas referri doleamus ambigua acute. Adhaesiones ratione beate arbitraretur detractis perdiscere, constituant hostis polyaeno. Diu concederetur.");
    columnExtendedTextItem2.setWidth(Width.WIDTH_SPACE_EQUALLY);

    Text columnExtendedTextItem3 = new Text("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Malit profecta versatur nomine ocurreret multavit, officiis viveremus aeternum superstitio suspicor alia nostram, quando nostros congressus susceperant concederetur leguntur iam, vigiliae democritea tantopere causae, atilii plerumque ipsas potitur pertineant multis rem quaeri pro, legendum didicisse credere ex maluisset per videtis. Cur discordans praetereat aliae ruinae dirigentur orestem eodem, praetermittenda divinum. Collegisti, deteriora malint loquuntur officii cotidie finitas referri doleamus ambigua acute. Adhaesiones ratione beate arbitraretur detractis perdiscere, constituant hostis polyaeno. Diu concederetur.");
    columnExtendedTextItem3.setWidth(Width.WIDTH_SPACE_EQUALLY);

    List<Item> columnsElements2 = new ArrayList<>();
    columnsElements2.add(columnExtendedTextItem1);
    columnsElements2.add(columnExtendedTextItem2);
    columnsElements2.add(columnExtendedTextItem3);

    Column columnItem2 = new Column();
    columnItem2.addColumn(columnsElements2);
    content.add(columnItem2);
    content.add(PdfUtil.println());

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

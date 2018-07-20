package com.onedsol.tools.utils.graph;

import com.onedsol.tools.jpdfmake.Image;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.category.*;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.Range;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.Layer;
import org.jfree.ui.TextAnchor;
import org.jfree.util.Rotation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;

/**
 * Created by hermeslm on 5/2/17.
 */
public class ChartUtil {

    public static BufferedImage draw(JFreeChart chart, int width, int height) {

        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = img.createGraphics();

        Rectangle2D rectangle = new Rectangle2D.Double(0, 0, width, height);
        chart.draw(g2, rectangle);

        g2.dispose();
        return img;
    }

//    public static BufferedImage draw2(JFreeChart chart, int width, int height){
//
//        BufferedImage bufferedImage = new BufferedImage(chart.getWidth(), chart.getHeight(), BufferedImage.TYPE_INT_RGB);
//        Graphics2D graphics2D = bufferedImage.createGraphics();
//        chart.paint(graphics2D, chart.getWidth(), chart.getHeight());
//        chart.getWidth(), chart.getHeight()
//    }

    public static void saveToFile(JFreeChart chart,
                                  String aFileName,
                                  int width,
                                  int height) throws IOException {

        BufferedImage img = draw(chart, width, height);
        File outputFile = new File(aFileName);
        ImageIO.write(img, "jpg", outputFile);
    }

    public static Image genGaugeWithBarIndicatorChart(InputStream gaugeImageIs, boolean flip,
                                                      Double value, int width, int height) {

        try {
            BufferedImage img = ImageIO.read(gaugeImageIs);
            GaugeChart gaugeChart = new GaugeChart(img, flip);

            BufferedImage tmp = gaugeChart
                    .indicator(new BarIndicator())
                    .value(value)
                    .drawAsImage();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(tmp, "png", baos);
            byte[] encoded = Base64.getEncoder().encode(baos.toByteArray());
            baos.flush();
            return new Image("data:image/png;base64," + new String(encoded), width, height);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Image genGaugeWithRectIndicatorChart(InputStream gaugeImageIs, boolean flip,
                                                       Double value, int width, int height) {

        try {
            BufferedImage img = ImageIO.read(gaugeImageIs);
            GaugeChart gaugeChart = new GaugeChart(img, flip);

            BufferedImage tmp = gaugeChart
                    .indicator(new RectIndicator())
                    .value(value)
                    .drawAsImage();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(tmp, "png", baos);
            byte[] encoded = Base64.getEncoder().encode(baos.toByteArray());
            baos.flush();
            return new Image("data:image/png;base64," + new String(encoded), width, height);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static Image genPieChart(DefaultPieDataset dataSet, String title,
                                    Color background, int width, int height) {

        JFreeChart chart = ChartFactory.createPieChart3D(
                title,  // chart title
                dataSet,                // data
                true,                   // include legend
                true,
                false
        );

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        plot.setNoDataMessage("No data to display");
        plot.setBackgroundPaint(background);
        plot.setOutlineVisible(false);

        try {

            BufferedImage img = draw(chart, 500, 200);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(img, "png", os);
            byte[] encoded = Base64.getEncoder().encode(os.toByteArray());
            os.flush();
            return new Image("data:image/png;base64," + new String(encoded), width, height);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Image genHStackedBarChart(DefaultCategoryDataset dataSet, String title,
                                            Color background, String domainAxisLabel,
                                            String rangeAxisLabel, List<Color> seriesColor,
                                            int width, int height, Marker marker) {

        StackedBarRenderer br = new StackedBarRenderer(false); //enable perc. display
        br.setBarPainter(new StandardBarPainter());

        JFreeChart chart = ChartFactory.createStackedBarChart(
                title,  // chart title
                domainAxisLabel,                  // domain axis label
                rangeAxisLabel,                     // range axis label
                dataSet,                     // data
                PlotOrientation.VERTICAL,    // the plot orientation
                false,                        // legend
                true,                        // tooltips
                false                        // urls
        );

        chart.setBackgroundPaint(background);
        chart.setTitle(new TextTitle(title, new Font("SansSerif", Font.BOLD, 30)));

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setRenderer(br);

        Font font3 = new Font("Label", Font.PLAIN, 25);
        plot.getDomainAxis().setLabelFont(font3);
        plot.getRangeAxis().setLabelFont(font3);

        Font font = new Font("TickLabel", Font.PLAIN, 20);
        plot.getDomainAxis().setTickLabelFont(font);
        Font font2 = new Font("TickLabel", Font.PLAIN, 20);
        plot.getRangeAxis().setTickLabelFont(font2);

        for (int i = 0; i < seriesColor.size(); i++) {
            plot.getRenderer().setSeriesPaint(i, seriesColor.get(i));
        }

//        plot.getRenderer().setSeriesPaint(0, new Color(75, 148, 192));//Color.blue);
//        plot.getRenderer().setSeriesPaint(1, new Color(245, 105, 84));//Color.red);
//        plot.getRenderer().setSeriesPaint(2, new Color(243, 156, 18));//Color.orange);

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        if (marker != null) {
            plot.addRangeMarker(marker, Layer.FOREGROUND);
        }

        //Set default bar render without shadow nor gradient
        BarRenderer barRenderer = (BarRenderer) plot.getRenderer();
        barRenderer.setBarPainter(new StandardBarPainter());
        barRenderer.setShadowVisible(false);

        plot.setBackgroundPaint(background);

        plot.setOutlineVisible(false);

        try {

            BufferedImage img = draw(chart, 1500, 600);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(img, "png", os);
            byte[] encoded = Base64.getEncoder().encode(os.toByteArray());
            os.flush();
            return new Image("data:image/png;base64," + new String(encoded), width, height);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static Image genTimeSeriesChart(TimeSeriesCollection dataset, String title, Color background, int width, int height) {
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                title, "Date", "Tests Value", dataset, true, true, false);
        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
        renderer.setBaseShapesVisible(true);

        chart.setBackgroundPaint(background);
        chart.setTitle(new TextTitle(title, new Font("SansSerif", Font.BOLD, 30)));

        Font font3 = new Font("Label", Font.PLAIN, 25);
        plot.getDomainAxis().setLabelFont(font3);
        plot.getRangeAxis().setLabelFont(font3);

        Font font = new Font("TickLabel", Font.PLAIN, 20);
        plot.getDomainAxis().setTickLabelFont(font);
        Font font2 = new Font("TickLabel", Font.PLAIN, 20);
        plot.getRangeAxis().setTickLabelFont(font2);

        plot.setBackgroundPaint(background);
        plot.setOutlineVisible(false);

        try {
            BufferedImage img = draw(chart, 1000, 450);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(img, "png", os);
            byte[] encoded = Base64.getEncoder().encode(os.toByteArray());
            os.flush();

            return new Image("data:image/png;base64," + new String(encoded), width, height);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Image genAreaChart(DefaultCategoryDataset dataset, String title,
                                     Color background, String domainAxisLabel,
                                     String rangeAxisLabel, int width, int height,
                                     List<Marker> markers, Color seriesColor,
                                     Font valueFont, Font categoryFont) {

        JFreeChart chart = ChartFactory.createStackedAreaChart(
                title,             // chart title
                domainAxisLabel,               // domain axis label
                rangeAxisLabel,                  // range axis label
                dataset,                  // data
                PlotOrientation.VERTICAL, // orientation
                false,                     // include legend
                true,                     // tooltips
                false                     // urls
        );

        chart.setBackgroundPaint(background);

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setForegroundAlpha(0.5f);

        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinesVisible(false);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinesVisible(false);
        plot.setRangeGridlinePaint(Color.white);

        final CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setLowerMargin(0.0);
        domainAxis.setUpperMargin(0.0);
        domainAxis.setTickLabelFont(categoryFont);

        CategoryItemRenderer renderer = plot.getRenderer();
        renderer.setSeriesPaint(0, seriesColor);
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setBaseItemLabelsVisible(true);
        ItemLabelPosition position = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12,
                TextAnchor.TOP_CENTER);
        renderer.setBasePositiveItemLabelPosition(position);
        renderer.setBaseItemLabelFont(valueFont);

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setLabelAngle(0 * Math.PI / 2.0);
        rangeAxis.setTickLabelsVisible(false);
        rangeAxis.setUpperMargin(0.4);

        ValueAxis valueAxis = plot.getRangeAxis(); // getting the value axis from plot
        Range r = plot.getDataRange(valueAxis); // getting the data range
        System.out.println(r.getUpperBound());

        for (Marker marker : markers) {
            plot.addRangeMarker(marker, Layer.FOREGROUND);
        }

        try {
            BufferedImage img = draw(chart, width, height);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(img, "png", os);
            byte[] encoded = Base64.getEncoder().encode(os.toByteArray());
            os.flush();
            return new Image("data:image/png;base64," + new String(encoded), 200, 50);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    static public Image genBarAndAreaChart(DefaultCategoryDataset barDataset, DefaultCategoryDataset areaDataset,
                                           String title, Color background, String domainAxisLabel,
                                           String rangeAxisLabel, int width, int height,
                                           List<Color> barSeriesColor, Color areaColor,
                                           Font font, boolean showLegend) {

        // create the first renderer...
        //      final CategoryLabelGenerator generator = new StandardCategoryLabelGenerator();
        CategoryItemRenderer renderer = new BarRenderer();
        //    renderer.setLabelGenerator(generator);
        renderer.setBaseItemLabelsVisible(true);

        CategoryPlot plot = new CategoryPlot();

        plot.setBackgroundPaint(background);
        plot.setDomainGridlinesVisible(false);
        plot.setDomainGridlinePaint(background);
        plot.setRangeGridlinesVisible(false);
        plot.setRangeGridlinePaint(background);

        plot.setDataset(barDataset);
        plot.setRenderer(renderer);

        //Set default bar render without shadow nor gradient
        BarRenderer barRenderer = (BarRenderer) plot.getRenderer();
        barRenderer.setBarPainter(new StandardBarPainter());
        barRenderer.setShadowVisible(false);
        barRenderer.setItemMargin(.0);

        CategoryAxis categoryAxis = new CategoryAxis(domainAxisLabel);
        categoryAxis.setLowerMargin(.0);
        categoryAxis.setCategoryMargin(.1);
        categoryAxis.setUpperMargin(.0);
        plot.setDomainAxis(categoryAxis);

        NumberAxis rangeAxis = new NumberAxis(rangeAxisLabel);
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        plot.setRangeAxis(rangeAxis);

        plot.setOrientation(PlotOrientation.VERTICAL);
        plot.setRangeGridlinesVisible(true);
        plot.setDomainGridlinesVisible(true);

        plot.getDomainAxis().setLabelFont(font);
        plot.getRangeAxis().setLabelFont(font);

        plot.getDomainAxis().setTickLabelFont(font);
        plot.getRangeAxis().setTickLabelFont(font);

        CategoryItemRenderer areaRender = new FullAreaRender();
        areaRender.setSeriesPaint(0, areaColor);

        plot.setDataset(1, areaDataset);
        plot.setRenderer(1, areaRender);

        for (int i = 0; i < barSeriesColor.size(); i++) {
            plot.getRenderer().setSeriesPaint(i, barSeriesColor.get(i));
        }
        // change the rendering order so the primary dataset appears "behind" the
        // other datasets...
        plot.setDatasetRenderingOrder(DatasetRenderingOrder.REVERSE);

        plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.STANDARD);
        final JFreeChart chart = new JFreeChart(plot);
        chart.setTitle(title);
        if (!showLegend) {
            chart.removeLegend();
        }

        return drawChartImage(chart, width, height);

    }

    static public JFreeChart genLineChart(DefaultCategoryDataset dataSet, String title, String domainAxisLabel,
                                          String rangeAxisLabel, boolean showLegend,
                                          CategoryLabelPositions categoryLabelPositions) {

        JFreeChart chart = ChartFactory.createLineChart(
                title,             // chart title
                domainAxisLabel,               // domain axis label
                rangeAxisLabel,                  // range axis label
                dataSet,                  // data
                PlotOrientation.VERTICAL, // orientation
                showLegend,                     // include legend
                true,                     // tooltips
                false                     // urls
        );

        chart.setBackgroundPaint(Color.white);

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setForegroundAlpha(0.5f);

        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinesVisible(false);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinesVisible(false);
        plot.setRangeGridlinePaint(Color.white);

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setLowerMargin(0.0);
        domainAxis.setUpperMargin(0.0);
        domainAxis.setTickLabelFont(new Font("Values", Font.PLAIN, 16));
        if (categoryLabelPositions != null)
            domainAxis.setCategoryLabelPositions(categoryLabelPositions);

        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
        renderer.setSeriesStroke(0, new BasicStroke(4.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
        renderer.setSeriesPaint(0, Color.decode("#060BB0"));//ChartUtil.randomColor());
        renderer.setBaseSeriesVisible(true);
//        renderer.setShapesVisible(true);

        renderer.setSeriesShape(0, new Rectangle(-8, -8, 16, 16));
        renderer.setSeriesShapesVisible(0, true);
        renderer.setShapesFilled(true);


        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setLabelAngle(0 * Math.PI / 2.0);
        rangeAxis.setTickLabelsVisible(true);
        rangeAxis.setUpperMargin(0.4);
        rangeAxis.setTickLabelFont(new Font("Values", Font.PLAIN, 18));

        try {
            ChartUtilities.saveChartAsPNG(new File("/tmp/line_chart.png"), chart, 450, 230);
//            ChartUtil.saveToFile(chart, "/tmp/line_chart.jpg", 1500, 800);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return chart;//drawChartImage(chart, width, height);
    }

    static public Image genLineChart(DefaultCategoryDataset dataSet,
                                     String title, /*Color background, */String domainAxisLabel,
                                     String rangeAxisLabel, int width, int height,
                                         /*List<Color> barSeriesColor, Color areaColor,
                                         Font font, */boolean showLegend,
                                     CategoryLabelPositions categoryLabelPositions) {

//        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//
//        dataset.addValue(110, "GLU", "Jan'17");
//        dataset.addValue(95, "GLU", "Feb'17");
//        dataset.addValue(105, "GLU", "Mar'17");
//        dataset.addValue(70, "GLU", "Apr'17");
//        dataset.addValue(148, "GLU", "May'17");
//        dataset.addValue(115, "GLU", "Jun'17");

        JFreeChart chart = ChartFactory.createLineChart(
                title,             // chart title
                domainAxisLabel,               // domain axis label
                rangeAxisLabel,                  // range axis label
                dataSet,                  // data
                PlotOrientation.VERTICAL, // orientation
                showLegend,                     // include legend
                true,                     // tooltips
                false                     // urls
        );

        chart.setBackgroundPaint(Color.white);

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setForegroundAlpha(0.5f);

        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinesVisible(false);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinesVisible(false);
        plot.setRangeGridlinePaint(Color.white);

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setLowerMargin(0.0);
        domainAxis.setUpperMargin(0.0);
        domainAxis.setTickLabelFont(new Font("Values", Font.PLAIN, 9));
        if (categoryLabelPositions != null)
            domainAxis.setCategoryLabelPositions(categoryLabelPositions);

        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
        renderer.setSeriesStroke(0, new BasicStroke(2.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
        renderer.setSeriesPaint(0, Color.decode("#060BB0"));//ChartUtil.randomColor());
        renderer.setBaseSeriesVisible(true);
//        renderer.setShapesVisible(true);

        renderer.setSeriesShape(0, new Rectangle(-2, -2, 4, 4));
        renderer.setSeriesShapesVisible(0, true);
//        renderer.setShapesFilled(true);


//        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
//        renderer.setBaseItemLabelsVisible(true);
//        ItemLabelPosition position = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12,
//            TextAnchor.TOP_CENTER);
//        renderer.setBasePositiveItemLabelPosition(position);
//        renderer.setBaseItemLabelFont(new Font("Values", Font.BOLD, 10));

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setLabelAngle(0 * Math.PI / 2.0);
        rangeAxis.setTickLabelsVisible(true);
        rangeAxis.setUpperMargin(0.4);
        rangeAxis.setTickLabelFont(new Font("Values", Font.PLAIN, 10));

//        Marker topMarker = new ValueMarker(150);
//        topMarker.setPaint(Color.black);
//        topMarker.setStroke(new BasicStroke(1.0f));
//        topMarker.setLabel("Max");
//        topMarker.setLabelFont(new Font("Marker Top", Font.BOLD, 20));
//        topMarker.setLabelAnchor(RectangleAnchor.BOTTOM_RIGHT);
//        topMarker.setLabelTextAnchor(TextAnchor.HALF_ASCENT_RIGHT);
//        plot.addRangeMarker(topMarker, Layer.FOREGROUND);
//
//        Marker bottomMarker = new ValueMarker(70);
//        bottomMarker.setPaint(Color.black);
//        bottomMarker.setStroke(new BasicStroke(1.0f));
//        bottomMarker.setLabel("Min");
//        bottomMarker.setLabelFont(new Font("Marker Bottom", Font.BOLD, 20));
//        bottomMarker.setLabelAnchor(RectangleAnchor.BOTTOM_RIGHT);
//        bottomMarker.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
//        plot.addRangeMarker(bottomMarker, Layer.FOREGROUND);

        try {
            ChartUtilities.saveChartAsPNG(new File("/tmp/line_chart.png"), chart, 350, 150);
//            ChartUtil.saveToFile(chart, "/tmp/line_chart.jpg", 1500, 800);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return drawChartImage(chart, width, height);
    }

    private static Image drawChartImage(JFreeChart chart, int width, int height) {

        try {

            BufferedImage img = draw(chart, width, height);
            ByteArrayOutputStream os = new ByteArrayOutputStream();

            ImageIO.write(img, "png", os);
            byte[] encoded = Base64.getEncoder().encode(os.toByteArray());
            os.flush();
            return new Image("data:image/png;base64," + new String(encoded), width, height);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    static public Color randomColor() {

        //Set series color
        Color[] colorPool = {
//      new Color(0x99, 0x00, 0x99),  //purple
//      new Color(0xcc, 0x33, 0x00),  //red
//      new Color(0xff, 0xcc, 0x66),  //beige?
//      // new Color(0x00, 0x00, 0xff),
//      // new Color(0x66, 0x00, 0x99),
//      new Color(0x99, 0x66, 0x00), //brown
//      new Color(0xcc, 0x66, 0x00), //tan
//      new Color(0xff, 0xcc, 0xff), //pink
//      new Color(0x0, 0x66, 0x00),  //dark green
//      new Color(0x66, 0x00, 0xff), //blue
//      new Color(0x99, 0x66, 0xff), //off-blue
//      new Color(0xcc, 0x66, 0x66), //another tan
//      new Color(0xff, 0xff, 0x00), //yellow
//      new Color(0x0, 0x99, 0x00),  //green
//      new Color(0x66, 0x66, 0x99), //slate
//      new Color(0x99, 0x99, 0x99), //grey
//      new Color(0xcc, 0x99, 0x00), //off-tan
//      new Color(0x00, 0x99, 0x99), //puce
//      new Color(0x66, 0xcc, 0xff), //aqua
//      new Color(0x99, 0xcc, 0x00), //peagreen
//      new Color(0xcc, 0x99, 0xcc), //washedoutpurple
//      new Color(0x00, 0xff, 0x66), //limegreen
//      new Color(0xcc, 0xff, 0x00), //yellow-green
//      new Color(0x00, 0xff, 0xff), //light blue
                // new Color(0x66, 0x00, 0x00),
                // new Color(0x00, 0x00, 0x99)
                new Color(0xac, 0xc2, 0xd9),  //  cloudy blue
                new Color(0x56, 0xae, 0x57),  //  dark pastel green
                new Color(0xb2, 0x99, 0x6e),  //  dust
                new Color(0xa8, 0xff, 0x04),  //  electric lime
                new Color(0x69, 0xd8, 0x4f),  //  fresh green
                new Color(0x89, 0x45, 0x85),  //  light eggplant
                new Color(0x70, 0xb2, 0x3f),  //  nasty green
                new Color(0xd4, 0xff, 0xff),  //  really light blue
                new Color(0x65, 0xab, 0x7c),  //  tea
                new Color(0x95, 0x2e, 0x8f),  //  warm purple
                new Color(0xfc, 0xfc, 0x81),  //  yellowish tan
                new Color(0xa5, 0xa3, 0x91),  //  cement
                new Color(0x38, 0x80, 0x04),  //  dark grass green
                new Color(0x4c, 0x90, 0x85),  //  dusty teal
                new Color(0x5e, 0x9b, 0x8a),  //  grey teal
                new Color(0xef, 0xb4, 0x35),  //  macaroni and cheese
                new Color(0xd9, 0x9b, 0x82),  //  pinkish tan
                new Color(0x0a, 0x5f, 0x38),  //  spruce
                new Color(0x0c, 0x06, 0xf7),  //  strong blue
                new Color(0x61, 0xde, 0x2a),  //  toxic green
                new Color(0x37, 0x78, 0xbf),  //  windows blue
                new Color(0x22, 0x42, 0xc7),  //  blue blue
                new Color(0x53, 0x3c, 0xc6),  //  blue with a hint of purple
                new Color(0x9b, 0xb5, 0x3c),  //  booger
                new Color(0x05, 0xff, 0xa6),  //  bright sea green
                new Color(0x1f, 0x63, 0x57),  //  dark green blue
                new Color(0x01, 0x73, 0x74),  //  deep turquoise
                new Color(0x0c, 0xb5, 0x77),  //  green teal
                new Color(0xff, 0x07, 0x89),  //  strong pink
                new Color(0xaf, 0xa8, 0x8b),  //  bland
                new Color(0x08, 0x78, 0x7f),  //  deep aqua
                new Color(0xdd, 0x85, 0xd7),  //  lavender pink
                new Color(0xa6, 0xc8, 0x75),  //  light moss green
                new Color(0xa7, 0xff, 0xb5),  //  light seafoam green
                new Color(0xc2, 0xb7, 0x09),  //  olive yellow
                new Color(0xe7, 0x8e, 0xa5),  //  pig pink
                new Color(0x96, 0x6e, 0xbd),  //  deep lilac
                new Color(0xcc, 0xad, 0x60),  //  desert
                new Color(0xac, 0x86, 0xa8),  //  dusty lavender
                new Color(0x94, 0x7e, 0x94),  //  purpley grey
                new Color(0x98, 0x3f, 0xb2),  //  purply
                new Color(0xff, 0x63, 0xe9),  //  candy pink
                new Color(0xb2, 0xfb, 0xa5),  //  light pastel green
                new Color(0x63, 0xb3, 0x65),  //  boring green
                new Color(0x8e, 0xe5, 0x3f),  //  kiwi green
                new Color(0xb7, 0xe1, 0xa1),  //  light grey green
                new Color(0xff, 0x6f, 0x52),  //  orange pink
                new Color(0xbd, 0xf8, 0xa3),  //  tea green
                new Color(0xd3, 0xb6, 0x83),  //  very light brown
                new Color(0xff, 0xfc, 0xc4),  //  egg shell
                new Color(0x43, 0x05, 0x41),  //  eggplant purple
                new Color(0xff, 0xb2, 0xd0),  //  powder pink
                new Color(0x99, 0x75, 0x70),  //  reddish grey
                new Color(0xad, 0x90, 0x0d),  //  baby shit brown
                new Color(0xc4, 0x8e, 0xfd),  //  liliac
                new Color(0x50, 0x7b, 0x9c),  //  stormy blue
                new Color(0x7d, 0x71, 0x03),  //  ugly brown
                new Color(0xff, 0xfd, 0x78),  //  custard
                new Color(0xda, 0x46, 0x7d),  //  darkish pink
                new Color(0x41, 0x02, 0x00),  //  deep brown
                new Color(0xc9, 0xd1, 0x79),  //  greenish beige
                new Color(0xff, 0xfa, 0x86),  //  manilla
                new Color(0x56, 0x84, 0xae),  //  off blue
                new Color(0x6b, 0x7c, 0x85),  //  battleship grey
                new Color(0x6f, 0x6c, 0x0a),  //  browny green
                new Color(0x7e, 0x40, 0x71),  //  bruise
                new Color(0x00, 0x93, 0x37),  //  kelley green
                new Color(0xd0, 0xe4, 0x29),  //  sickly yellow
                new Color(0xff, 0xf9, 0x17),  //  sunny yellow
                new Color(0x1d, 0x5d, 0xec),  //  azul
                new Color(0x05, 0x49, 0x07),  //  darkgreen
                new Color(0xb5, 0xce, 0x08),  //  green/yellow
                new Color(0x8f, 0xb6, 0x7b),  //  lichen
                new Color(0xc8, 0xff, 0xb0),  //  light light green
                new Color(0xfd, 0xde, 0x6c),  //  pale gold
                new Color(0xff, 0xdf, 0x22),  //  sun yellow
                new Color(0xa9, 0xbe, 0x70),  //  tan green
                new Color(0x68, 0x32, 0xe3),  //  burple
                new Color(0xfd, 0xb1, 0x47),  //  butterscotch
                new Color(0xc7, 0xac, 0x7d),  //  toupe
                new Color(0xff, 0xf3, 0x9a),  //  dark cream
                new Color(0x85, 0x0e, 0x04),  //  indian red
                new Color(0xef, 0xc0, 0xfe),  //  light lavendar
                new Color(0x40, 0xfd, 0x14),  //  poison green
                new Color(0xb6, 0xc4, 0x06),  //  baby puke green
                new Color(0x9d, 0xff, 0x00),  //  bright yellow green
                new Color(0x3c, 0x41, 0x42),  //  charcoal grey
                new Color(0xf2, 0xab, 0x15),  //  squash
                new Color(0xac, 0x4f, 0x06),  //  cinnamon
                new Color(0xc4, 0xfe, 0x82),  //  light pea green
                new Color(0x2c, 0xfa, 0x1f),  //  radioactive green
                new Color(0x9a, 0x62, 0x00),  //  raw sienna
                new Color(0xca, 0x9b, 0xf7),  //  baby purple
                new Color(0x87, 0x5f, 0x42),  //  cocoa
                new Color(0x3a, 0x2e, 0xfe),  //  light royal blue
                new Color(0xfd, 0x8d, 0x49),  //  orangeish
                new Color(0x8b, 0x31, 0x03),  //  rust brown
                new Color(0xcb, 0xa5, 0x60),  //  sand brown
                new Color(0x69, 0x83, 0x39),  //  swamp
                new Color(0x0c, 0xdc, 0x73),  //  tealish green
                new Color(0xb7, 0x52, 0x03),  //  burnt siena
                new Color(0x7f, 0x8f, 0x4e),  //  camo
                new Color(0x26, 0x53, 0x8d),  //  dusk blue
                new Color(0x63, 0xa9, 0x50),  //  fern
                new Color(0xc8, 0x7f, 0x89),  //  old rose
                new Color(0xb1, 0xfc, 0x99),  //  pale light green
                new Color(0xff, 0x9a, 0x8a),  //  peachy pink
                new Color(0xf6, 0x68, 0x8e),  //  rosy pink
                new Color(0x76, 0xfd, 0xa8),  //  light bluish green
                new Color(0x53, 0xfe, 0x5c),  //  light bright green
                new Color(0x4e, 0xfd, 0x54),  //  light neon green
                new Color(0xa0, 0xfe, 0xbf),  //  light seafoam
                new Color(0x7b, 0xf2, 0xda),  //  tiffany blue
                new Color(0xbc, 0xf5, 0xa6),  //  washed out green
                new Color(0xca, 0x6b, 0x02),  //  browny orange
                new Color(0x10, 0x7a, 0xb0),  //  nice blue
                new Color(0x21, 0x38, 0xab),  //  sapphire
                new Color(0x71, 0x9f, 0x91),  //  greyish teal
                new Color(0xfd, 0xb9, 0x15),  //  orangey yellow
                new Color(0xfe, 0xfc, 0xaf),  //  parchment
                new Color(0xfc, 0xf6, 0x79),  //  straw
                new Color(0x1d, 0x02, 0x00),  //  very dark brown
                new Color(0xcb, 0x68, 0x43),  //  terracota
                new Color(0x31, 0x66, 0x8a),  //  ugly blue
                new Color(0x24, 0x7a, 0xfd),  //  clear blue
                new Color(0xff, 0xff, 0xb6),  //  creme
                new Color(0x90, 0xfd, 0xa9),  //  foam green
                new Color(0x86, 0xa1, 0x7d),  //  grey/green
                new Color(0xfd, 0xdc, 0x5c),  //  light gold
                new Color(0x78, 0xd1, 0xb6),  //  seafoam blue
                new Color(0x13, 0xbb, 0xaf),  //  topaz
                new Color(0xfb, 0x5f, 0xfc),  //  violet pink
                new Color(0x20, 0xf9, 0x86),  //  wintergreen
                new Color(0xff, 0xe3, 0x6e),  //  yellow tan
                new Color(0x9d, 0x07, 0x59),  //  dark fuchsia
                new Color(0x3a, 0x18, 0xb1),  //  indigo blue
                new Color(0xc2, 0xff, 0x89),  //  light yellowish green
                new Color(0xd7, 0x67, 0xad),  //  pale magenta
                new Color(0x72, 0x00, 0x58),  //  rich purple
                new Color(0xff, 0xda, 0x03),  //  sunflower yellow
                new Color(0x01, 0xc0, 0x8d),  //  green/blue
                new Color(0xac, 0x74, 0x34),  //  leather
                new Color(0x01, 0x46, 0x00),  //  racing green
                new Color(0x99, 0x00, 0xfa),  //  vivid purple
                new Color(0x02, 0x06, 0x6f),  //  dark royal blue
                new Color(0x8e, 0x76, 0x18),  //  hazel
                new Color(0xd1, 0x76, 0x8f),  //  muted pink
                new Color(0x96, 0xb4, 0x03),  //  booger green
                new Color(0xfd, 0xff, 0x63),  //  canary
                new Color(0x95, 0xa3, 0xa6),  //  cool grey
                new Color(0x7f, 0x68, 0x4e),  //  dark taupe
                new Color(0x75, 0x19, 0x73),  //  darkish purple
                new Color(0x08, 0x94, 0x04),  //  true green
                new Color(0xff, 0x61, 0x63),  //  coral pink
                new Color(0x59, 0x85, 0x56),  //  dark sage
                new Color(0x21, 0x47, 0x61),  //  dark slate blue
                new Color(0x3c, 0x73, 0xa8),  //  flat blue
                new Color(0xba, 0x9e, 0x88),  //  mushroom
                new Color(0x02, 0x1b, 0xf9),  //  rich blue
                new Color(0x73, 0x4a, 0x65),  //  dirty purple
                new Color(0x23, 0xc4, 0x8b),  //  greenblue
                new Color(0x8f, 0xae, 0x22),  //  icky green
                new Color(0xe6, 0xf2, 0xa2),  //  light khaki
                new Color(0x4b, 0x57, 0xdb),  //  warm blue
                new Color(0xd9, 0x01, 0x66),  //  dark hot pink
                new Color(0x01, 0x54, 0x82),  //  deep sea blue
                new Color(0x9d, 0x02, 0x16),  //  carmine
                new Color(0x72, 0x8f, 0x02),  //  dark yellow green
                new Color(0xff, 0xe5, 0xad),  //  pale peach
                new Color(0x4e, 0x05, 0x50),  //  plum purple
                new Color(0xf9, 0xbc, 0x08),  //  golden rod
                new Color(0xff, 0x07, 0x3a),  //  neon red
                new Color(0xc7, 0x79, 0x86),  //  old pink
                new Color(0xd6, 0xff, 0xfe),  //  very pale blue
                new Color(0xfe, 0x4b, 0x03),  //  blood orange
                new Color(0xfd, 0x59, 0x56),  //  grapefruit
                new Color(0xfc, 0xe1, 0x66),  //  sand yellow
                new Color(0xb2, 0x71, 0x3d),  //  clay brown
                new Color(0x1f, 0x3b, 0x4d),  //  dark blue grey
                new Color(0x69, 0x9d, 0x4c),  //  flat green
                new Color(0x56, 0xfc, 0xa2),  //  light green blue
                new Color(0xfb, 0x55, 0x81),  //  warm pink
                new Color(0x3e, 0x82, 0xfc),  //  dodger blue
                new Color(0xa0, 0xbf, 0x16),  //  gross green
                new Color(0xd6, 0xff, 0xfa),  //  ice
                new Color(0x4f, 0x73, 0x8e),  //  metallic blue
                new Color(0xff, 0xb1, 0x9a),  //  pale salmon
                new Color(0x5c, 0x8b, 0x15),  //  sap green
                new Color(0x54, 0xac, 0x68),  //  algae
                new Color(0x89, 0xa0, 0xb0),  //  bluey grey
                new Color(0x7e, 0xa0, 0x7a),  //  greeny grey
                new Color(0x1b, 0xfc, 0x06),  //  highlighter green
                new Color(0xca, 0xff, 0xfb),  //  light light blue
                new Color(0xb6, 0xff, 0xbb),  //  light mint
                new Color(0xa7, 0x5e, 0x09),  //  raw umber
                new Color(0x15, 0x2e, 0xff),  //  vivid blue
                new Color(0x8d, 0x5e, 0xb7),  //  deep lavender
                new Color(0x5f, 0x9e, 0x8f),  //  dull teal
                new Color(0x63, 0xf7, 0xb4),  //  light greenish blue
                new Color(0x60, 0x66, 0x02),  //  mud green
                new Color(0xfc, 0x86, 0xaa),  //  pinky
                new Color(0x8c, 0x00, 0x34),  //  red wine
                new Color(0x75, 0x80, 0x00),  //  shit green
                new Color(0xab, 0x7e, 0x4c),  //  tan brown
                new Color(0x03, 0x07, 0x64),  //  darkblue
                new Color(0xfe, 0x86, 0xa4),  //  rosa
                new Color(0xd5, 0x17, 0x4e),  //  lipstick
                new Color(0xfe, 0xd0, 0xfc),  //  pale mauve
                new Color(0x68, 0x00, 0x18),  //  claret
                new Color(0xfe, 0xdf, 0x08),  //  dandelion
                new Color(0xfe, 0x42, 0x0f),  //  orangered
                new Color(0x6f, 0x7c, 0x00),  //  poop green
                new Color(0xca, 0x01, 0x47),  //  ruby
                new Color(0x1b, 0x24, 0x31),  //  dark
                new Color(0x00, 0xfb, 0xb0),  //  greenish turquoise
                new Color(0xdb, 0x58, 0x56),  //  pastel red
                new Color(0xdd, 0xd6, 0x18),  //  piss yellow
                new Color(0x41, 0xfd, 0xfe),  //  bright cyan
                new Color(0xcf, 0x52, 0x4e),  //  dark coral
                new Color(0x21, 0xc3, 0x6f),  //  algae green
                new Color(0xa9, 0x03, 0x08),  //  darkish red
                new Color(0x6e, 0x10, 0x05),  //  reddy brown
                new Color(0xfe, 0x82, 0x8c),  //  blush pink
                new Color(0x4b, 0x61, 0x13),  //  camouflage green
                new Color(0x4d, 0xa4, 0x09),  //  lawn green
                new Color(0xbe, 0xae, 0x8a),  //  putty
                new Color(0x03, 0x39, 0xf8),  //  vibrant blue
                new Color(0xa8, 0x8f, 0x59),  //  dark sand
                new Color(0x5d, 0x21, 0xd0),  //  purple/blue
                new Color(0xfe, 0xb2, 0x09),  //  saffron
                new Color(0x4e, 0x51, 0x8b),  //  twilight
                new Color(0x96, 0x4e, 0x02),  //  warm brown
                new Color(0x85, 0xa3, 0xb2),  //  bluegrey
                new Color(0xff, 0x69, 0xaf),  //  bubble gum pink
                new Color(0xc3, 0xfb, 0xf4),  //  duck egg blue
                new Color(0x2a, 0xfe, 0xb7),  //  greenish cyan
                new Color(0x00, 0x5f, 0x6a),  //  petrol
                new Color(0x0c, 0x17, 0x93),  //  royal
                new Color(0xff, 0xff, 0x81),  //  butter
                new Color(0xf0, 0x83, 0x3a),  //  dusty orange
                new Color(0xf1, 0xf3, 0x3f),  //  off yellow
                new Color(0xb1, 0xd2, 0x7b),  //  pale olive green
                new Color(0xfc, 0x82, 0x4a),  //  orangish
                new Color(0x71, 0xaa, 0x34),  //  leaf
                new Color(0xb7, 0xc9, 0xe2),  //  light blue grey
                new Color(0x4b, 0x01, 0x01),  //  dried blood
                new Color(0xa5, 0x52, 0xe6),  //  lightish purple
                new Color(0xaf, 0x2f, 0x0d),  //  rusty red
                new Color(0x8b, 0x88, 0xf8),  //  lavender blue
                new Color(0x9a, 0xf7, 0x64),  //  light grass green
                new Color(0xa6, 0xfb, 0xb2),  //  light mint green
                new Color(0xff, 0xc5, 0x12),  //  sunflower
                new Color(0x75, 0x08, 0x51),  //  velvet
                new Color(0xc1, 0x4a, 0x09),  //  brick orange
                new Color(0xfe, 0x2f, 0x4a),  //  lightish red
                new Color(0x02, 0x03, 0xe2),  //  pure blue
                new Color(0x0a, 0x43, 0x7a),  //  twilight blue
                new Color(0xa5, 0x00, 0x55),  //  violet red
                new Color(0xae, 0x8b, 0x0c),  //  yellowy brown
                new Color(0xfd, 0x79, 0x8f),  //  carnation
                new Color(0xbf, 0xac, 0x05),  //  muddy yellow
                new Color(0x3e, 0xaf, 0x76),  //  dark seafoam green
                new Color(0xc7, 0x47, 0x67),  //  deep rose
                new Color(0xb9, 0x48, 0x4e),  //  dusty red
                new Color(0x64, 0x7d, 0x8e),  //  grey/blue
                new Color(0xbf, 0xfe, 0x28),  //  lemon lime
                new Color(0xd7, 0x25, 0xde),  //  purple/pink
                new Color(0xb2, 0x97, 0x05),  //  brown yellow
                new Color(0x67, 0x3a, 0x3f),  //  purple brown
                new Color(0xa8, 0x7d, 0xc2),  //  wisteria
                new Color(0xfa, 0xfe, 0x4b),  //  banana yellow
                new Color(0xc0, 0x02, 0x2f),  //  lipstick red
                new Color(0x0e, 0x87, 0xcc),  //  water blue
                new Color(0x8d, 0x84, 0x68),  //  brown grey
                new Color(0xad, 0x03, 0xde),  //  vibrant purple
                new Color(0x8c, 0xff, 0x9e),  //  baby green
                new Color(0x94, 0xac, 0x02),  //  barf green
                new Color(0xc4, 0xff, 0xf7),  //  eggshell blue
                new Color(0xfd, 0xee, 0x73),  //  sandy yellow
                new Color(0x33, 0xb8, 0x64),  //  cool green
                new Color(0xff, 0xf9, 0xd0),  //  pale
                new Color(0x75, 0x8d, 0xa3),  //  blue/grey
                new Color(0xf5, 0x04, 0xc9),  //  hot magenta
                new Color(0x77, 0xa1, 0xb5),  //  greyblue
                new Color(0x87, 0x56, 0xe4),  //  purpley
                new Color(0x88, 0x97, 0x17),  //  baby shit green
                new Color(0xc2, 0x7e, 0x79),  //  brownish pink
                new Color(0x01, 0x73, 0x71),  //  dark aquamarine
                new Color(0x9f, 0x83, 0x03),  //  diarrhea
                new Color(0xf7, 0xd5, 0x60),  //  light mustard
                new Color(0xbd, 0xf6, 0xfe),  //  pale sky blue
                new Color(0x75, 0xb8, 0x4f),  //  turtle green
                new Color(0x9c, 0xbb, 0x04),  //  bright olive
                new Color(0x29, 0x46, 0x5b),  //  dark grey blue
                new Color(0x69, 0x60, 0x06),  //  greeny brown
                new Color(0xad, 0xf8, 0x02),  //  lemon green
                new Color(0xc1, 0xc6, 0xfc),  //  light periwinkle
                new Color(0x35, 0xad, 0x6b),  //  seaweed green
                new Color(0xff, 0xfd, 0x37),  //  sunshine yellow
                new Color(0xa4, 0x42, 0xa0),  //  ugly purple
                new Color(0xf3, 0x61, 0x96),  //  medium pink
                new Color(0x94, 0x77, 0x06),  //  puke brown
                new Color(0xff, 0xf4, 0xf2),  //  very light pink
                new Color(0x1e, 0x91, 0x67),  //  viridian
                new Color(0xb5, 0xc3, 0x06),  //  bile
                new Color(0xfe, 0xff, 0x7f),  //  faded yellow
                new Color(0xcf, 0xfd, 0xbc),  //  very pale green
                new Color(0x0a, 0xdd, 0x08),  //  vibrant green
                new Color(0x87, 0xfd, 0x05),  //  bright lime
                new Color(0x1e, 0xf8, 0x76),  //  spearmint
                new Color(0x7b, 0xfd, 0xc7),  //  light aquamarine
                new Color(0xbc, 0xec, 0xac),  //  light sage
                new Color(0xbb, 0xf9, 0x0f),  //  yellowgreen
                new Color(0xab, 0x90, 0x04),  //  baby poo
                new Color(0x1f, 0xb5, 0x7a),  //  dark seafoam
                new Color(0x00, 0x55, 0x5a),  //  deep teal
                new Color(0xa4, 0x84, 0xac),  //  heather
                new Color(0xc4, 0x55, 0x08),  //  rust orange
                new Color(0x3f, 0x82, 0x9d),  //  dirty blue
                new Color(0x54, 0x8d, 0x44),  //  fern green
                new Color(0xc9, 0x5e, 0xfb),  //  bright lilac
                new Color(0x3a, 0xe5, 0x7f),  //  weird green
                new Color(0x01, 0x67, 0x95),  //  peacock blue
                new Color(0x87, 0xa9, 0x22),  //  avocado green
                new Color(0xf0, 0x94, 0x4d),  //  faded orange
                new Color(0x5d, 0x14, 0x51),  //  grape purple
                new Color(0x25, 0xff, 0x29),  //  hot green
                new Color(0xd0, 0xfe, 0x1d),  //  lime yellow
                new Color(0xff, 0xa6, 0x2b),  //  mango
                new Color(0x01, 0xb4, 0x4c),  //  shamrock
                new Color(0xff, 0x6c, 0xb5),  //  bubblegum
                new Color(0x6b, 0x42, 0x47),  //  purplish brown
                new Color(0xc7, 0xc1, 0x0c),  //  vomit yellow
                new Color(0xb7, 0xff, 0xfa),  //  pale cyan
                new Color(0xae, 0xff, 0x6e),  //  key lime
                new Color(0xec, 0x2d, 0x01),  //  tomato red
                new Color(0x76, 0xff, 0x7b),  //  lightgreen
                new Color(0x73, 0x00, 0x39),  //  merlot
                new Color(0x04, 0x03, 0x48),  //  night blue
                new Color(0xdf, 0x4e, 0xc8),  //  purpleish pink
                new Color(0x6e, 0xcb, 0x3c),  //  apple
                new Color(0x8f, 0x98, 0x05),  //  baby poop green
                new Color(0x5e, 0xdc, 0x1f),  //  green apple
                new Color(0xd9, 0x4f, 0xf5),  //  heliotrope
                new Color(0xc8, 0xfd, 0x3d),  //  yellow/green
                new Color(0x07, 0x0d, 0x0d),  //  almost black
                new Color(0x49, 0x84, 0xb8),  //  cool blue
                new Color(0x51, 0xb7, 0x3b),  //  leafy green
                new Color(0xac, 0x7e, 0x04),  //  mustard brown
                new Color(0x4e, 0x54, 0x81),  //  dusk
                new Color(0x87, 0x6e, 0x4b),  //  dull brown
                new Color(0x58, 0xbc, 0x08),  //  frog green
                new Color(0x2f, 0xef, 0x10),  //  vivid green
                new Color(0x2d, 0xfe, 0x54),  //  bright light green
                new Color(0x0a, 0xff, 0x02),  //  fluro green
                new Color(0x9c, 0xef, 0x43),  //  kiwi
                new Color(0x18, 0xd1, 0x7b),  //  seaweed
                new Color(0x35, 0x53, 0x0a),  //  navy green
                new Color(0x18, 0x05, 0xdb),  //  ultramarine blue
                new Color(0x62, 0x58, 0xc4),  //  iris
                new Color(0xff, 0x96, 0x4f),  //  pastel orange
                new Color(0xff, 0xab, 0x0f),  //  yellowish orange
                new Color(0x8f, 0x8c, 0xe7),  //  perrywinkle
                new Color(0x24, 0xbc, 0xa8),  //  tealish
                new Color(0x3f, 0x01, 0x2c),  //  dark plum
                new Color(0xcb, 0xf8, 0x5f),  //  pear
                new Color(0xff, 0x72, 0x4c),  //  pinkish orange
                new Color(0x28, 0x01, 0x37),  //  midnight purple
                new Color(0xb3, 0x6f, 0xf6),  //  light urple
                new Color(0x48, 0xc0, 0x72),  //  dark mint
                new Color(0xbc, 0xcb, 0x7a),  //  greenish tan
                new Color(0xa8, 0x41, 0x5b),  //  light burgundy
                new Color(0x06, 0xb1, 0xc4),  //  turquoise blue
                new Color(0xcd, 0x75, 0x84),  //  ugly pink
                new Color(0xf1, 0xda, 0x7a),  //  sandy
                new Color(0xff, 0x04, 0x90),  //  electric pink
                new Color(0x80, 0x5b, 0x87),  //  muted purple
                new Color(0x50, 0xa7, 0x47),  //  mid green
                new Color(0xa8, 0xa4, 0x95),  //  greyish
                new Color(0xcf, 0xff, 0x04),  //  neon yellow
                new Color(0xff, 0xff, 0x7e),  //  banana
                new Color(0xff, 0x7f, 0xa7),  //  carnation pink
                new Color(0xef, 0x40, 0x26),  //  tomato
                new Color(0x3c, 0x99, 0x92),  //  sea
                new Color(0x88, 0x68, 0x06),  //  muddy brown
                new Color(0x04, 0xf4, 0x89),  //  turquoise green
                new Color(0xfe, 0xf6, 0x9e),  //  buff
                new Color(0xcf, 0xaf, 0x7b),  //  fawn
                new Color(0x3b, 0x71, 0x9f),  //  muted blue
                new Color(0xfd, 0xc1, 0xc5),  //  pale rose
                new Color(0x20, 0xc0, 0x73),  //  dark mint green
                new Color(0x9b, 0x5f, 0xc0),  //  amethyst
                new Color(0x0f, 0x9b, 0x8e),  //  blue/green
                new Color(0x74, 0x28, 0x02),  //  chestnut
                new Color(0x9d, 0xb9, 0x2c),  //  sick green
                new Color(0xa4, 0xbf, 0x20),  //  pea
                new Color(0xcd, 0x59, 0x09),  //  rusty orange
                new Color(0xad, 0xa5, 0x87),  //  stone
                new Color(0xbe, 0x01, 0x3c),  //  rose red
                new Color(0xb8, 0xff, 0xeb),  //  pale aqua
                new Color(0xdc, 0x4d, 0x01),  //  deep orange
                new Color(0xa2, 0x65, 0x3e),  //  earth
                new Color(0x63, 0x8b, 0x27),  //  mossy green
                new Color(0x41, 0x9c, 0x03),  //  grassy green
                new Color(0xb1, 0xff, 0x65),  //  pale lime green
                new Color(0x9d, 0xbc, 0xd4),  //  light grey blue
                new Color(0xfd, 0xfd, 0xfe),  //  pale grey
                new Color(0x77, 0xab, 0x56),  //  asparagus
                new Color(0x46, 0x41, 0x96),  //  blueberry
                new Color(0x99, 0x01, 0x47),  //  purple red
                new Color(0xbe, 0xfd, 0x73),  //  pale lime
                new Color(0x32, 0xbf, 0x84),  //  greenish teal
                new Color(0xaf, 0x6f, 0x09),  //  caramel
                new Color(0xa0, 0x02, 0x5c),  //  deep magenta
                new Color(0xff, 0xd8, 0xb1),  //  light peach
                new Color(0x7f, 0x4e, 0x1e),  //  milk chocolate
                new Color(0xbf, 0x9b, 0x0c),  //  ocher
                new Color(0x6b, 0xa3, 0x53),  //  off green
                new Color(0xf0, 0x75, 0xe6),  //  purply pink
                new Color(0x7b, 0xc8, 0xf6),  //  lightblue
                new Color(0x47, 0x5f, 0x94),  //  dusky blue
                new Color(0xf5, 0xbf, 0x03),  //  golden
                new Color(0xff, 0xfe, 0xb6),  //  light beige
                new Color(0xff, 0xfd, 0x74),  //  butter yellow
                new Color(0x89, 0x5b, 0x7b),  //  dusky purple
                new Color(0x43, 0x6b, 0xad),  //  french blue
                new Color(0xd0, 0xc1, 0x01),  //  ugly yellow
                new Color(0xc6, 0xf8, 0x08),  //  greeny yellow
                new Color(0xf4, 0x36, 0x05),  //  orangish red
                new Color(0x02, 0xc1, 0x4d),  //  shamrock green
                new Color(0xb2, 0x5f, 0x03),  //  orangish brown
                new Color(0x2a, 0x7e, 0x19),  //  tree green
                new Color(0x49, 0x06, 0x48),  //  deep violet
                new Color(0x53, 0x62, 0x67),  //  gunmetal
                new Color(0x5a, 0x06, 0xef),  //  blue/purple
                new Color(0xcf, 0x02, 0x34),  //  cherry
                new Color(0xc4, 0xa6, 0x61),  //  sandy brown
                new Color(0x97, 0x8a, 0x84),  //  warm grey
                new Color(0x1f, 0x09, 0x54),  //  dark indigo
                new Color(0x03, 0x01, 0x2d),  //  midnight
                new Color(0x2b, 0xb1, 0x79),  //  bluey green
                new Color(0xc3, 0x90, 0x9b),  //  grey pink
                new Color(0xa6, 0x6f, 0xb5),  //  soft purple
                new Color(0x77, 0x00, 0x01),  //  blood
                new Color(0x92, 0x2b, 0x05),  //  brown red
                new Color(0x7d, 0x7f, 0x7c),  //  medium grey
                new Color(0x99, 0x0f, 0x4b),  //  berry
                new Color(0x8f, 0x73, 0x03),  //  poo
                new Color(0xc8, 0x3c, 0xb9),  //  purpley pink
                new Color(0xfe, 0xa9, 0x93),  //  light salmon
                new Color(0xac, 0xbb, 0x0d),  //  snot
                new Color(0xc0, 0x71, 0xfe),  //  easter purple
                new Color(0xcc, 0xfd, 0x7f),  //  light yellow green
                new Color(0x00, 0x02, 0x2e),  //  dark navy blue
                new Color(0x82, 0x83, 0x44),  //  drab
                new Color(0xff, 0xc5, 0xcb),  //  light rose
                new Color(0xab, 0x12, 0x39),  //  rouge
                new Color(0xb0, 0x05, 0x4b),  //  purplish red
                new Color(0x99, 0xcc, 0x04),  //  slime green
                new Color(0x93, 0x7c, 0x00),  //  baby poop
                new Color(0x01, 0x95, 0x29),  //  irish green
                new Color(0xef, 0x1d, 0xe7),  //  pink/purple
                new Color(0x00, 0x04, 0x35),  //  dark navy
                new Color(0x42, 0xb3, 0x95),  //  greeny blue
                new Color(0x9d, 0x57, 0x83),  //  light plum
                new Color(0xc8, 0xac, 0xa9),  //  pinkish grey
                new Color(0xc8, 0x76, 0x06),  //  dirty orange
                new Color(0xaa, 0x27, 0x04),  //  rust red
                new Color(0xe4, 0xcb, 0xff),  //  pale lilac
                new Color(0xfa, 0x42, 0x24),  //  orangey red
                new Color(0x08, 0x04, 0xf9),  //  primary blue
                new Color(0x5c, 0xb2, 0x00),  //  kermit green
                new Color(0x76, 0x42, 0x4e),  //  brownish purple
                new Color(0x6c, 0x7a, 0x0e),  //  murky green
                new Color(0xfb, 0xdd, 0x7e),  //  wheat
                new Color(0x2a, 0x01, 0x34),  //  very dark purple
                new Color(0x04, 0x4a, 0x05),  //  bottle green
                new Color(0xfd, 0x46, 0x59),  //  watermelon
                new Color(0x0d, 0x75, 0xf8),  //  deep sky blue
                new Color(0xfe, 0x00, 0x02),  //  fire engine red
                new Color(0xcb, 0x9d, 0x06),  //  yellow ochre
                new Color(0xfb, 0x7d, 0x07),  //  pumpkin orange
                new Color(0xb9, 0xcc, 0x81),  //  pale olive
                new Color(0xed, 0xc8, 0xff),  //  light lilac
                new Color(0x61, 0xe1, 0x60),  //  lightish green
                new Color(0x8a, 0xb8, 0xfe),  //  carolina blue
                new Color(0x92, 0x0a, 0x4e),  //  mulberry
                new Color(0xfe, 0x02, 0xa2),  //  shocking pink
                new Color(0x9a, 0x30, 0x01),  //  auburn
                new Color(0x65, 0xfe, 0x08),  //  bright lime green
                new Color(0xbe, 0xfd, 0xb7),  //  celadon
                new Color(0xb1, 0x72, 0x61),  //  pinkish brown
                new Color(0x88, 0x5f, 0x01),  //  poo brown
                new Color(0x02, 0xcc, 0xfe),  //  bright sky blue
                new Color(0xc1, 0xfd, 0x95),  //  celery
                new Color(0x83, 0x65, 0x39),  //  dirt brown
                new Color(0xfb, 0x29, 0x43),  //  strawberry
                new Color(0x84, 0xb7, 0x01),  //  dark lime
                new Color(0xb6, 0x63, 0x25),  //  copper
                new Color(0x7f, 0x51, 0x12),  //  medium brown
                new Color(0x5f, 0xa0, 0x52),  //  muted green
                new Color(0x6d, 0xed, 0xfd),  //  robin's egg
                new Color(0x0b, 0xf9, 0xea),  //  bright aqua
                new Color(0xc7, 0x60, 0xff),  //  bright lavender
                new Color(0xff, 0xff, 0xcb),  //  ivory
                new Color(0xf6, 0xce, 0xfc),  //  very light purple
                new Color(0x15, 0x50, 0x84),  //  light navy
                new Color(0xf5, 0x05, 0x4f),  //  pink red
                new Color(0x64, 0x54, 0x03),  //  olive brown
                new Color(0x7a, 0x59, 0x01),  //  poop brown
                new Color(0xa8, 0xb5, 0x04),  //  mustard green
                new Color(0x3d, 0x99, 0x73),  //  ocean green
                new Color(0x00, 0x01, 0x33),  //  very dark blue
                new Color(0x76, 0xa9, 0x73),  //  dusty green
                new Color(0x2e, 0x5a, 0x88),  //  light navy blue
                new Color(0x0b, 0xf7, 0x7d),  //  minty green
                new Color(0xbd, 0x6c, 0x48),  //  adobe
                new Color(0xac, 0x1d, 0xb8),  //  barney
                new Color(0x2b, 0xaf, 0x6a),  //  jade green
                new Color(0x26, 0xf7, 0xfd),  //  bright light blue
                new Color(0xae, 0xfd, 0x6c),  //  light lime
                new Color(0x9b, 0x8f, 0x55),  //  dark khaki
                new Color(0xff, 0xad, 0x01),  //  orange yellow
                new Color(0xc6, 0x9c, 0x04),  //  ocre
                new Color(0xf4, 0xd0, 0x54),  //  maize
                new Color(0xde, 0x9d, 0xac),  //  faded pink
                new Color(0x05, 0x48, 0x0d),  //  british racing green
                new Color(0xc9, 0xae, 0x74),  //  sandstone
                new Color(0x60, 0x46, 0x0f),  //  mud brown
                new Color(0x98, 0xf6, 0xb0),  //  light sea green
                new Color(0x8a, 0xf1, 0xfe),  //  robin egg blue
                new Color(0x2e, 0xe8, 0xbb),  //  aqua marine
                new Color(0x11, 0x87, 0x5d),  //  dark sea green
                new Color(0xfd, 0xb0, 0xc0),  //  soft pink
                new Color(0xb1, 0x60, 0x02),  //  orangey brown
                new Color(0xf7, 0x02, 0x2a),  //  cherry red
                new Color(0xd5, 0xab, 0x09),  //  burnt yellow
                new Color(0x86, 0x77, 0x5f),  //  brownish grey
                new Color(0xc6, 0x9f, 0x59),  //  camel
                new Color(0x7a, 0x68, 0x7f),  //  purplish grey
                new Color(0x04, 0x2e, 0x60),  //  marine
                new Color(0xc8, 0x8d, 0x94),  //  greyish pink
                new Color(0xa5, 0xfb, 0xd5),  //  pale turquoise
                new Color(0xff, 0xfe, 0x71),  //  pastel yellow
                new Color(0x62, 0x41, 0xc7),  //  bluey purple
                new Color(0xff, 0xfe, 0x40),  //  canary yellow
                new Color(0xd3, 0x49, 0x4e),  //  faded red
                new Color(0x98, 0x5e, 0x2b),  //  sepia
                new Color(0xa6, 0x81, 0x4c),  //  coffee
                new Color(0xff, 0x08, 0xe8),  //  bright magenta
                new Color(0x9d, 0x76, 0x51),  //  mocha
                new Color(0xfe, 0xff, 0xca),  //  ecru
                new Color(0x98, 0x56, 0x8d),  //  purpleish
                new Color(0x9e, 0x00, 0x3a),  //  cranberry
                new Color(0x28, 0x7c, 0x37),  //  darkish green
                new Color(0xb9, 0x69, 0x02),  //  brown orange
                new Color(0xba, 0x68, 0x73),  //  dusky rose
                new Color(0xff, 0x78, 0x55),  //  melon
                new Color(0x94, 0xb2, 0x1c),  //  sickly green
                new Color(0xc5, 0xc9, 0xc7),  //  silver
                new Color(0x66, 0x1a, 0xee),  //  purply blue
                new Color(0x61, 0x40, 0xef),  //  purpleish blue
                new Color(0x9b, 0xe5, 0xaa),  //  hospital green
                new Color(0x7b, 0x58, 0x04),  //  shit brown
                new Color(0x27, 0x6a, 0xb3),  //  mid blue
                new Color(0xfe, 0xb3, 0x08),  //  amber
                new Color(0x8c, 0xfd, 0x7e),  //  easter green
                new Color(0x64, 0x88, 0xea),  //  soft blue
                new Color(0x05, 0x6e, 0xee),  //  cerulean blue
                new Color(0xb2, 0x7a, 0x01),  //  golden brown
                new Color(0x0f, 0xfe, 0xf9),  //  bright turquoise
                new Color(0xfa, 0x2a, 0x55),  //  red pink
                new Color(0x82, 0x07, 0x47),  //  red purple
                new Color(0x7a, 0x6a, 0x4f),  //  greyish brown
                new Color(0xf4, 0x32, 0x0c),  //  vermillion
                new Color(0xa1, 0x39, 0x05),  //  russet
                new Color(0x6f, 0x82, 0x8a),  //  steel grey
                new Color(0xa5, 0x5a, 0xf4),  //  lighter purple
                new Color(0xad, 0x0a, 0xfd),  //  bright violet
                new Color(0x00, 0x45, 0x77),  //  prussian blue
                new Color(0x65, 0x8d, 0x6d),  //  slate green
                new Color(0xca, 0x7b, 0x80),  //  dirty pink
                new Color(0x00, 0x52, 0x49),  //  dark blue green
                new Color(0x2b, 0x5d, 0x34),  //  pine
                new Color(0xbf, 0xf1, 0x28),  //  yellowy green
                new Color(0xb5, 0x94, 0x10),  //  dark gold
                new Color(0x29, 0x76, 0xbb),  //  bluish
                new Color(0x01, 0x41, 0x82),  //  darkish blue
                new Color(0xbb, 0x3f, 0x3f),  //  dull red
                new Color(0xfc, 0x26, 0x47),  //  pinky red
                new Color(0xa8, 0x79, 0x00),  //  bronze
                new Color(0x82, 0xcb, 0xb2),  //  pale teal
                new Color(0x66, 0x7c, 0x3e),  //  military green
                new Color(0xfe, 0x46, 0xa5),  //  barbie pink
                new Color(0xfe, 0x83, 0xcc),  //  bubblegum pink
                new Color(0x94, 0xa6, 0x17),  //  pea soup green
                new Color(0xa8, 0x89, 0x05),  //  dark mustard
                new Color(0x7f, 0x5f, 0x00),  //  shit
                new Color(0x9e, 0x43, 0xa2),  //  medium purple
                new Color(0x06, 0x2e, 0x03),  //  very dark green
                new Color(0x8a, 0x6e, 0x45),  //  dirt
                new Color(0xcc, 0x7a, 0x8b),  //  dusky pink
                new Color(0x9e, 0x01, 0x68),  //  red violet
                new Color(0xfd, 0xff, 0x38),  //  lemon yellow
                new Color(0xc0, 0xfa, 0x8b),  //  pistachio
                new Color(0xee, 0xdc, 0x5b),  //  dull yellow
                new Color(0x7e, 0xbd, 0x01),  //  dark lime green
                new Color(0x3b, 0x5b, 0x92),  //  denim blue
                new Color(0x01, 0x88, 0x9f),  //  teal blue
                new Color(0x3d, 0x7a, 0xfd),  //  lightish blue
                new Color(0x5f, 0x34, 0xe7),  //  purpley blue
                new Color(0x6d, 0x5a, 0xcf),  //  light indigo
                new Color(0x74, 0x85, 0x00),  //  swamp green
                new Color(0x70, 0x6c, 0x11),  //  brown green
                new Color(0x3c, 0x00, 0x08),  //  dark maroon
                new Color(0xcb, 0x00, 0xf5),  //  hot purple
                new Color(0x00, 0x2d, 0x04),  //  dark forest green
                new Color(0x65, 0x8c, 0xbb),  //  faded blue
                new Color(0x74, 0x95, 0x51),  //  drab green
                new Color(0xb9, 0xff, 0x66),  //  light lime green
                new Color(0x9d, 0xc1, 0x00),  //  snot green
                new Color(0xfa, 0xee, 0x66),  //  yellowish
                new Color(0x7e, 0xfb, 0xb3),  //  light blue green
                new Color(0x7b, 0x00, 0x2c),  //  bordeaux
                new Color(0xc2, 0x92, 0xa1),  //  light mauve
                new Color(0x01, 0x7b, 0x92),  //  ocean
                new Color(0xfc, 0xc0, 0x06),  //  marigold
                new Color(0x65, 0x74, 0x32),  //  muddy green
                new Color(0xd8, 0x86, 0x3b),  //  dull orange
                new Color(0x73, 0x85, 0x95),  //  steel
                new Color(0xaa, 0x23, 0xff),  //  electric purple
                new Color(0x08, 0xff, 0x08),  //  fluorescent green
                new Color(0x9b, 0x7a, 0x01),  //  yellowish brown
                new Color(0xf2, 0x9e, 0x8e),  //  blush
                new Color(0x6f, 0xc2, 0x76),  //  soft green
                new Color(0xff, 0x5b, 0x00),  //  bright orange
                new Color(0xfd, 0xff, 0x52),  //  lemon
                new Color(0x86, 0x6f, 0x85),  //  purple grey
                new Color(0x8f, 0xfe, 0x09),  //  acid green
                new Color(0xee, 0xcf, 0xfe),  //  pale lavender
                new Color(0x51, 0x0a, 0xc9),  //  violet blue
                new Color(0x4f, 0x91, 0x53),  //  light forest green
                new Color(0x9f, 0x23, 0x05),  //  burnt red
                new Color(0x72, 0x86, 0x39),  //  khaki green
                new Color(0xde, 0x0c, 0x62),  //  cerise
                new Color(0x91, 0x6e, 0x99),  //  faded purple
                new Color(0xff, 0xb1, 0x6d),  //  apricot
                new Color(0x3c, 0x4d, 0x03),  //  dark olive green
                new Color(0x7f, 0x70, 0x53),  //  grey brown
                new Color(0x77, 0x92, 0x6f),  //  green grey
                new Color(0x01, 0x0f, 0xcc),  //  true blue
                new Color(0xce, 0xae, 0xfa),  //  pale violet
                new Color(0x8f, 0x99, 0xfb),  //  periwinkle blue
                new Color(0xc6, 0xfc, 0xff),  //  light sky blue
                new Color(0x55, 0x39, 0xcc),  //  blurple
                new Color(0x54, 0x4e, 0x03),  //  green brown
                new Color(0x01, 0x7a, 0x79),  //  bluegreen
                new Color(0x01, 0xf9, 0xc6),  //  bright teal
                new Color(0xc9, 0xb0, 0x03),  //  brownish yellow
                new Color(0x92, 0x99, 0x01),  //  pea soup
                new Color(0x0b, 0x55, 0x09),  //  forest
                new Color(0xa0, 0x04, 0x98),  //  barney purple
                new Color(0x20, 0x00, 0xb1),  //  ultramarine
                new Color(0x94, 0x56, 0x8c),  //  purplish
                new Color(0xc2, 0xbe, 0x0e),  //  puke yellow
                new Color(0x74, 0x8b, 0x97),  //  bluish grey
                new Color(0x66, 0x5f, 0xd1),  //  dark periwinkle
                new Color(0x9c, 0x6d, 0xa5),  //  dark lilac
                new Color(0xc4, 0x42, 0x40),  //  reddish
                new Color(0xa2, 0x48, 0x57),  //  light maroon
                new Color(0x82, 0x5f, 0x87),  //  dusty purple
                new Color(0xc9, 0x64, 0x3b),  //  terra cotta
                new Color(0x90, 0xb1, 0x34),  //  avocado
                new Color(0x01, 0x38, 0x6a),  //  marine blue
                new Color(0x25, 0xa3, 0x6f),  //  teal green
                new Color(0x59, 0x65, 0x6d),  //  slate grey
                new Color(0x75, 0xfd, 0x63),  //  lighter green
                new Color(0x21, 0xfc, 0x0d),  //  electric green
                new Color(0x5a, 0x86, 0xad),  //  dusty blue
                new Color(0xfe, 0xc6, 0x15),  //  golden yellow
                new Color(0xff, 0xfd, 0x01),  //  bright yellow
                new Color(0xdf, 0xc5, 0xfe),  //  light lavender
                new Color(0xb2, 0x64, 0x00),  //  umber
                new Color(0x7f, 0x5e, 0x00),  //  poop
                new Color(0xde, 0x7e, 0x5d),  //  dark peach
                new Color(0x04, 0x82, 0x43),  //  jungle green
                new Color(0xff, 0xff, 0xd4),  //  eggshell
                new Color(0x3b, 0x63, 0x8c),  //  denim
                new Color(0xb7, 0x94, 0x00),  //  yellow brown
                new Color(0x84, 0x59, 0x7e),  //  dull purple
                new Color(0x41, 0x19, 0x00),  //  chocolate brown
                new Color(0x7b, 0x03, 0x23),  //  wine red
                new Color(0x04, 0xd9, 0xff),  //  neon blue
                new Color(0x66, 0x7e, 0x2c),  //  dirty green
                new Color(0xfb, 0xee, 0xac),  //  light tan
                new Color(0xd7, 0xff, 0xfe),  //  ice blue
                new Color(0x4e, 0x74, 0x96),  //  cadet blue
                new Color(0x87, 0x4c, 0x62),  //  dark mauve
                new Color(0xd5, 0xff, 0xff),  //  very light blue
                new Color(0x82, 0x6d, 0x8c),  //  grey purple
                new Color(0xff, 0xba, 0xcd),  //  pastel pink
                new Color(0xd1, 0xff, 0xbd),  //  very light green
                new Color(0x44, 0x8e, 0xe4),  //  dark sky blue
                new Color(0x05, 0x47, 0x2a),  //  evergreen
                new Color(0xd5, 0x86, 0x9d),  //  dull pink
                new Color(0x3d, 0x07, 0x34),  //  aubergine
                new Color(0x4a, 0x01, 0x00),  //  mahogany
                new Color(0xf8, 0x48, 0x1c),  //  reddish orange
                new Color(0x02, 0x59, 0x0f),  //  deep green
                new Color(0x89, 0xa2, 0x03),  //  vomit green
                new Color(0xe0, 0x3f, 0xd8),  //  purple pink
                new Color(0xd5, 0x8a, 0x94),  //  dusty pink
                new Color(0x7b, 0xb2, 0x74),  //  faded green
                new Color(0x52, 0x65, 0x25),  //  camo green
                new Color(0xc9, 0x4c, 0xbe),  //  pinky purple
                new Color(0xdb, 0x4b, 0xda),  //  pink purple
                new Color(0x9e, 0x36, 0x23),  //  brownish red
                new Color(0xb5, 0x48, 0x5d),  //  dark rose
                new Color(0x73, 0x5c, 0x12),  //  mud
                new Color(0x9c, 0x6d, 0x57),  //  brownish
                new Color(0x02, 0x8f, 0x1e),  //  emerald green
                new Color(0xb1, 0x91, 0x6e),  //  pale brown
                new Color(0x49, 0x75, 0x9c),  //  dull blue
                new Color(0xa0, 0x45, 0x0e),  //  burnt umber
                new Color(0x39, 0xad, 0x48),  //  medium green
                new Color(0xb6, 0x6a, 0x50),  //  clay
                new Color(0x8c, 0xff, 0xdb),  //  light aqua
                new Color(0xa4, 0xbe, 0x5c),  //  light olive green
                new Color(0xcb, 0x77, 0x23),  //  brownish orange
                new Color(0x05, 0x69, 0x6b),  //  dark aqua
                new Color(0xce, 0x5d, 0xae),  //  purplish pink
                new Color(0xc8, 0x5a, 0x53),  //  dark salmon
                new Color(0x96, 0xae, 0x8d),  //  greenish grey
                new Color(0x1f, 0xa7, 0x74),  //  jade
                new Color(0x7a, 0x97, 0x03),  //  ugly green
                new Color(0xac, 0x93, 0x62),  //  dark beige
                new Color(0x01, 0xa0, 0x49),  //  emerald
                new Color(0xd9, 0x54, 0x4d),  //  pale red
                new Color(0xfa, 0x5f, 0xf7),  //  light magenta
                new Color(0x82, 0xca, 0xfc),  //  sky
                new Color(0xac, 0xff, 0xfc),  //  light cyan
                new Color(0xfc, 0xb0, 0x01),  //  yellow orange
                new Color(0x91, 0x09, 0x51),  //  reddish purple
                new Color(0xfe, 0x2c, 0x54),  //  reddish pink
                new Color(0xc8, 0x75, 0xc4),  //  orchid
                new Color(0xcd, 0xc5, 0x0a),  //  dirty yellow
                new Color(0xfd, 0x41, 0x1e),  //  orange red
                new Color(0x9a, 0x02, 0x00),  //  deep red
                new Color(0xbe, 0x64, 0x00),  //  orange brown
                new Color(0x03, 0x0a, 0xa7),  //  cobalt blue
                new Color(0xfe, 0x01, 0x9a),  //  neon pink
                new Color(0xf7, 0x87, 0x9a),  //  rose pink
                new Color(0x88, 0x71, 0x91),  //  greyish purple
                new Color(0xb0, 0x01, 0x49),  //  raspberry
                new Color(0x12, 0xe1, 0x93),  //  aqua green
                new Color(0xfe, 0x7b, 0x7c),  //  salmon pink
                new Color(0xff, 0x94, 0x08),  //  tangerine
                new Color(0x6a, 0x6e, 0x09),  //  brownish green
                new Color(0x8b, 0x2e, 0x16),  //  red brown
                new Color(0x69, 0x61, 0x12),  //  greenish brown
                new Color(0xe1, 0x77, 0x01),  //  pumpkin
                new Color(0x0a, 0x48, 0x1e),  //  pine green
                new Color(0x34, 0x38, 0x37),  //  charcoal
                new Color(0xff, 0xb7, 0xce),  //  baby pink
                new Color(0x6a, 0x79, 0xf7),  //  cornflower
                new Color(0x5d, 0x06, 0xe9),  //  blue violet
                new Color(0x3d, 0x1c, 0x02),  //  chocolate
                new Color(0x82, 0xa6, 0x7d),  //  greyish green
                new Color(0xbe, 0x01, 0x19),  //  scarlet
                new Color(0xc9, 0xff, 0x27),  //  green yellow
                new Color(0x37, 0x3e, 0x02),  //  dark olive
                new Color(0xa9, 0x56, 0x1e),  //  sienna
                new Color(0xca, 0xa0, 0xff),  //  pastel purple
                new Color(0xca, 0x66, 0x41),  //  terracotta
                new Color(0x02, 0xd8, 0xe9),  //  aqua blue
                new Color(0x88, 0xb3, 0x78),  //  sage green
                new Color(0x98, 0x00, 0x02),  //  blood red
                new Color(0xcb, 0x01, 0x62),  //  deep pink
                new Color(0x5c, 0xac, 0x2d),  //  grass
                new Color(0x76, 0x99, 0x58),  //  moss
                new Color(0xa2, 0xbf, 0xfe),  //  pastel blue
                new Color(0x10, 0xa6, 0x74),  //  bluish green
                new Color(0x06, 0xb4, 0x8b),  //  green blue
                new Color(0xaf, 0x88, 0x4a),  //  dark tan
                new Color(0x0b, 0x8b, 0x87),  //  greenish blue
                new Color(0xff, 0xa7, 0x56),  //  pale orange
                new Color(0xa2, 0xa4, 0x15),  //  vomit
                new Color(0x15, 0x44, 0x06),  //  forrest green
                new Color(0x85, 0x67, 0x98),  //  dark lavender
                new Color(0x34, 0x01, 0x3f),  //  dark violet
                new Color(0x63, 0x2d, 0xe9),  //  purple blue
                new Color(0x0a, 0x88, 0x8a),  //  dark cyan
                new Color(0x6f, 0x76, 0x32),  //  olive drab
                new Color(0xd4, 0x6a, 0x7e),  //  pinkish
                new Color(0x1e, 0x48, 0x8f),  //  cobalt
                new Color(0xbc, 0x13, 0xfe),  //  neon purple
                new Color(0x7e, 0xf4, 0xcc),  //  light turquoise
                new Color(0x76, 0xcd, 0x26),  //  apple green
                new Color(0x74, 0xa6, 0x62),  //  dull green
                new Color(0x80, 0x01, 0x3f),  //  wine
                new Color(0xb1, 0xd1, 0xfc),  //  powder blue
                new Color(0xff, 0xff, 0xe4),  //  off white
                new Color(0x06, 0x52, 0xff),  //  electric blue
                new Color(0x04, 0x5c, 0x5a),  //  dark turquoise
                new Color(0x57, 0x29, 0xce),  //  blue purple
                new Color(0x06, 0x9a, 0xf3),  //  azure
                new Color(0xff, 0x00, 0x0d),  //bright red
                new Color(0xf1, 0x0c, 0x45),  //  pinkish red
                new Color(0x51, 0x70, 0xd7),  //  cornflower blue
                new Color(0xac, 0xbf, 0x69),  //  light olive
                new Color(0x6c, 0x34, 0x61),  //  grape
                new Color(0x5e, 0x81, 0x9d),  //  greyish blue
                new Color(0x60, 0x1e, 0xf9),  //  purplish blue
                new Color(0xb0, 0xdd, 0x16),  //  yellowish green
                new Color(0xcd, 0xfd, 0x02),  //  greenish yellow
                new Color(0x2c, 0x6f, 0xbb),  //  medium blue
                new Color(0xc0, 0x73, 0x7a),  //  dusty rose
                new Color(0xd6, 0xb4, 0xfc),  //  light violet
                new Color(0x02, 0x00, 0x35),  //  midnight blue
                new Color(0x70, 0x3b, 0xe7),  //  bluish purple
                new Color(0xfd, 0x3c, 0x06),  //  red orange
                new Color(0x96, 0x00, 0x56),  //  dark magenta
                new Color(0x40, 0xa3, 0x68),  //  greenish
                new Color(0x03, 0x71, 0x9c),  //  ocean blue
                new Color(0xfc, 0x5a, 0x50),  //  coral
                new Color(0xff, 0xff, 0xc2),  //  cream
                new Color(0x7f, 0x2b, 0x0a),  //  reddish brown
                new Color(0xb0, 0x4e, 0x0f),  //  burnt sienna
                new Color(0xa0, 0x36, 0x23),  //  brick
                new Color(0x87, 0xae, 0x73),  //  sage
                new Color(0x78, 0x9b, 0x73),  //  grey green
                new Color(0xff, 0xff, 0xff),  //  white
                new Color(0x98, 0xef, 0xf9),  //  robin's egg blue
                new Color(0x65, 0x8b, 0x38),  //  moss green
                new Color(0x5a, 0x7d, 0x9a),  //  steel blue
                new Color(0x38, 0x08, 0x35),  //  eggplant
                new Color(0xff, 0xfe, 0x7a),  //  light yellow
                new Color(0x5c, 0xa9, 0x04),  //  leaf green
                new Color(0xd8, 0xdc, 0xd6),  //  light grey
                new Color(0xa5, 0xa5, 0x02),  //  puke
                new Color(0xd6, 0x48, 0xd7),  //  pinkish purple
                new Color(0x04, 0x74, 0x95),  //  sea blue
                new Color(0xb7, 0x90, 0xd4),  //  pale purple
                new Color(0x5b, 0x7c, 0x99),  //  slate blue
                new Color(0x60, 0x7c, 0x8e),  //  blue grey
                new Color(0x0b, 0x40, 0x08),  //  hunter green
                new Color(0xed, 0x0d, 0xd9),  //  fuchsia
                new Color(0x8c, 0x00, 0x0f),  //  crimson
                new Color(0xff, 0xff, 0x84),  //  pale yellow
                new Color(0xbf, 0x90, 0x05),  //  ochre
                new Color(0xd2, 0xbd, 0x0a),  //  mustard yellow
                new Color(0xff, 0x47, 0x4c),  //  light red
                new Color(0x04, 0x85, 0xd1),  //  cerulean
                new Color(0xff, 0xcf, 0xdc),  //  pale pink
                new Color(0x04, 0x02, 0x73),  //  deep blue
                new Color(0xa8, 0x3c, 0x09),  //  rust
                new Color(0x90, 0xe4, 0xc1),  //  light teal
                new Color(0x51, 0x65, 0x72),  //  slate
                new Color(0xfa, 0xc2, 0x05),  //  goldenrod
                new Color(0xd5, 0xb6, 0x0a),  //  dark yellow
                new Color(0x36, 0x37, 0x37),  //  dark grey
                new Color(0x4b, 0x5d, 0x16),  //  army green
                new Color(0x6b, 0x8b, 0xa4),  //  grey blue
                new Color(0x80, 0xf9, 0xad),  //  seafoam
                new Color(0xa5, 0x7e, 0x52),  //  puce
                new Color(0xa9, 0xf9, 0x71),  //  spring green
                new Color(0xc6, 0x51, 0x02),  //  dark orange
                new Color(0xe2, 0xca, 0x76),  //  sand
                new Color(0xb0, 0xff, 0x9d),  //  pastel green
                new Color(0x9f, 0xfe, 0xb0),  //  mint
                new Color(0xfd, 0xaa, 0x48),  //  light orange
                new Color(0xfe, 0x01, 0xb1),  //  bright pink
                new Color(0xc1, 0xf8, 0x0a),  //  chartreuse
                new Color(0x36, 0x01, 0x3f),  //  deep purple
                new Color(0x34, 0x1c, 0x02),  //  dark brown
                new Color(0xb9, 0xa2, 0x81),  //  taupe
                new Color(0x8e, 0xab, 0x12),  //  pea green
                new Color(0x9a, 0xae, 0x07),  //  puke green
                new Color(0x02, 0xab, 0x2e),  //  kelly green
                new Color(0x7a, 0xf9, 0xab),  //  seafoam green
                new Color(0x13, 0x7e, 0x6d),  //  blue green
                new Color(0xaa, 0xa6, 0x62),  //  khaki
                new Color(0x61, 0x00, 0x23),  //  burgundy
                new Color(0x01, 0x4d, 0x4e),  //  dark teal
                new Color(0x8f, 0x14, 0x02),  //  brick red
                new Color(0x4b, 0x00, 0x6e),  //  royal purple
                new Color(0x58, 0x0f, 0x41),  //  plum
                new Color(0x8f, 0xff, 0x9f),  //  mint green
                new Color(0xdb, 0xb4, 0x0c),  //  gold
                new Color(0xa2, 0xcf, 0xfe),  //  baby blue
                new Color(0xc0, 0xfb, 0x2d),  //  yellow green
                new Color(0xbe, 0x03, 0xfd),  //  bright purple
                new Color(0x84, 0x00, 0x00),  //  dark red
                new Color(0xd0, 0xfe, 0xfe),  //  pale blue
                new Color(0x3f, 0x9b, 0x0b),  //  grass green
                new Color(0x01, 0x15, 0x3e),  //  navy
                new Color(0x04, 0xd8, 0xb2),  //  aquamarine
                new Color(0xc0, 0x4e, 0x01),  //  burnt orange
                new Color(0x0c, 0xff, 0x0c),  //  neon green
                new Color(0x01, 0x65, 0xfc),  //  bright blue
                new Color(0xcf, 0x62, 0x75),  //  rose
                new Color(0xff, 0xd1, 0xdf),  //  light pink
                new Color(0xce, 0xb3, 0x01),  //  mustard
                new Color(0x38, 0x02, 0x82),  //  indigo
                new Color(0xaa, 0xff, 0x32),  //  lime
                new Color(0x53, 0xfc, 0xa1),  //  sea green
                new Color(0x8e, 0x82, 0xfe),  //  periwinkle
                new Color(0xcb, 0x41, 0x6b),  //  dark pink
                new Color(0x67, 0x7a, 0x04),  //  olive green
                new Color(0xff, 0xb0, 0x7c),  //  peach
                new Color(0xc7, 0xfd, 0xb5),  //  pale green
                new Color(0xad, 0x81, 0x50),  //  light brown
                new Color(0xff, 0x02, 0x8d),  //  hot pink
                new Color(0x00, 0x00, 0x00),  //  black
                new Color(0xce, 0xa2, 0xfd),  //  lilac
                new Color(0x00, 0x11, 0x46),  //  navy blue
                new Color(0x05, 0x04, 0xaa),  //  royal blue
                new Color(0xe6, 0xda, 0xa6),  //  beige
                new Color(0xff, 0x79, 0x6c),  //  salmon
                new Color(0x6e, 0x75, 0x0e),  //  olive
                new Color(0x65, 0x00, 0x21),  //  maroon
                new Color(0x01, 0xff, 0x07),  //  bright green
                new Color(0x35, 0x06, 0x3e),  //  dark purple
                new Color(0xae, 0x71, 0x81),  //  mauve
                new Color(0x06, 0x47, 0x0c),  //  forest green
                new Color(0x13, 0xea, 0xc9),  //  aqua
                new Color(0x00, 0xff, 0xff),  //  cyan
                new Color(0xd1, 0xb2, 0x6f),  //  tan
                new Color(0x00, 0x03, 0x5b),  //  dark blue
                new Color(0xc7, 0x9f, 0xef),  //  lavender
                new Color(0x06, 0xc2, 0xac),  //  turquoise
                new Color(0x03, 0x35, 0x00),  //  dark green
                new Color(0x9a, 0x0e, 0xea),  //  violet
                new Color(0xbf, 0x77, 0xf6),  //  light purple
                new Color(0x89, 0xfe, 0x05),  //  lime green
                new Color(0x92, 0x95, 0x91),  //  grey
                new Color(0x75, 0xbb, 0xfd),  //  sky blue
                new Color(0xff, 0xff, 0x14),  //  yellow
                new Color(0xc2, 0x00, 0x78),  //  magenta
                new Color(0x96, 0xf9, 0x7b),  //  light green
                new Color(0xf9, 0x73, 0x06),  //  orange
                new Color(0x02, 0x93, 0x86),  //  teal
                new Color(0x95, 0xd0, 0xfc),  //  light blue
                new Color(0xe5, 0x00, 0x00),  //  red
                new Color(0x65, 0x37, 0x00),  //  brown
                new Color(0xff, 0x81, 0xc0),  //  pink
                new Color(0x03, 0x43, 0xdf),  //  blue
                new Color(0x15, 0xb0, 0x1a),  //  green
                new Color(0x7e, 0x1e, 0x9c),  //  purple
        };
//
//
//
//
//
//
//        List<Color> seriesColor = new ArrayList<>();
//        Color red = new Color(255, 0, 0);
//        Color green = new Color(0, 176, 80);
//        Color yellow = new Color(255, 255, 0);
//        Color blue = new Color(75, 148, 192);
//        seriesColor.add(red);
//        seriesColor.add(yellow);
//        seriesColor.add(green);
//        seriesColor.add(blue);

        int randomNum = 0 + (int) (Math.random() * colorPool.length - 1);

        return colorPool[randomNum];
    }

}

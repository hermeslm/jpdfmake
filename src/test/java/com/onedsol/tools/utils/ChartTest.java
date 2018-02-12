package com.onedsol.tools.utils;

import com.onedsol.tools.utils.graph.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.Layer;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.TextAnchor;
import org.jfree.util.Rotation;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChartTest {

    @Test
    public void createGaugeBarIndicatorChartTest() {

        try {
            BufferedImage img = null;
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("graph/linear_gauge.png").getFile());
            img = ImageIO.read(file);

            GaugeChart gaugeChart = new GaugeChart(img);

            BufferedImage tmp = gaugeChart
                    .indicator(new BarIndicator())
                    .value(80.0)
                    .drawAsImage();

            File outputFile = new File("/tmp/linear_gauge_saved.png");
            ImageIO.write(tmp, "png", outputFile);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void createGaugeRectIndicatorChartTest() {

        try {
            BufferedImage img = null;
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("graph/linear_gauge.png").getFile());
            img = ImageIO.read(file);

            GaugeChart gaugeChart = new GaugeChart(img);

            BufferedImage tmp = gaugeChart
                    .indicator(new RectIndicator())
                    .value(70.5)
                    .drawAsImage();

            File outputFile = new File("/tmp/linear_gauge_saved.png");
            ImageIO.write(tmp, "png", outputFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createGaugeRectIndicatorChartFlippedTest() {

        try {

            BufferedImage img = null;
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("graph/linear_gauge.png").getFile());
            img = ImageIO.read(file);

            GaugeChart gaugeChart = new GaugeChart(img, true);

            BufferedImage tmp = gaugeChart
                    .indicator(new RectIndicator())
                    .value(70.5)
                    .drawAsImage();

            File outputFile = new File("/tmp/linear_gauge_saved.png");
            ImageIO.write(tmp, "png", outputFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createPieChart() {

        DefaultPieDataset dataSet = new DefaultPieDataset();
        dataSet.setValue("< 50 CFU/mL", new Double(43.2));
        dataSet.setValue("50 - 200 CFU/mL", new Double(10.0));
        dataSet.setValue("> 200 CFU/mL", new Double(17.5));

        JFreeChart chart = ChartFactory.createPieChart3D(
                "Colony Count Water",  // chart title
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
        plot.setBackgroundPaint(Color.white);
        plot.setOutlineVisible(false);

        try {
            ChartUtil.saveToFile(chart, "/tmp/pie.jpg", 500, 200);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void createStackedBarChart() {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dataset.addValue(75.0, "8.4 < Ca < 10.2 %", "Mar'16");
        dataset.addValue(25.0, "Ca >= 10.2 %", "Mar'16");
        dataset.addValue(0.0, "Ca <= 8.4 %", "Mar'16");
        dataset.addValue(85.7, "8.4 < Ca < 10.2 %", "Apr'16");
        dataset.addValue(7.1, "Ca >= 10.2 %", "Apr'16");
        dataset.addValue(7.1, "Ca <= 8.4 %", "Apr'16");

        StackedBarRenderer br = new StackedBarRenderer(false); //enable perc. display
        br.setBarPainter(new StandardBarPainter());

//        br.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
//        br.setBaseItemLabelsVisible(true);

        br.setBaseItemLabelGenerator(
                new StandardCategoryItemLabelGenerator() {
                    @Override
                    public String generateLabel(CategoryDataset dataset, int row, int column) {
                        return "Your Text" + row + "," + column;
                    }
                }
        );

        JFreeChart chart = ChartFactory.createStackedBarChart(
                "Bone Mineral QA",  // chart title
                "Category",                  // domain axis label
                "Patient Population",                     // range axis label
                dataset,                     // data
                PlotOrientation.VERTICAL,    // the plot orientation
                false,                        // legend
                true,                        // tooltips
                false                        // urls
        );

        chart.setBackgroundPaint(Color.WHITE);
        CategoryPlot plot = chart.getCategoryPlot();
        Font font3 = new Font("Label", Font.PLAIN, 20);
        plot.getDomainAxis().setLabelFont(font3);
        plot.getRangeAxis().setLabelFont(font3);

        Font font = new Font("TickLabel", Font.PLAIN, 20);
        plot.getDomainAxis().setTickLabelFont(font);
        Font font2 = new Font("TickLabel", Font.PLAIN, 20);
        plot.getRangeAxis().setTickLabelFont(font2);

        plot.setRenderer(br);
        plot.getRenderer().setSeriesPaint(0, new Color(75, 148, 192));//Color.blue);
        plot.getRenderer().setSeriesPaint(1, new Color(245, 105, 84));//Color.red);
        plot.getRenderer().setSeriesPaint(2, new Color(243, 156, 18));//Color.orange);

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        Marker marker = new ValueMarker(85);
//        marker.setOutlinePaint(Color.black);
//        marker.setLabel("Goal");
//        marker.setLabelAnchor(RectangleAnchor.BOTTOM_RIGHT);
//        marker.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
        marker.setPaint(Color.black);
        marker.setStroke(new BasicStroke(3.0f));
        plot.addRangeMarker(marker, Layer.FOREGROUND);

//        NumberAxis rangeAxis2 = (NumberAxis) plot.getRangeAxis();
//        rangeAxis2.setNumberFormatOverride(NumberFormat.getPercentInstance(Locale.US));

        //If you want to rotate domain axis
//        CategoryAxis domainAxis = plot.getDomainAxis();
//        domainAxis.setCategoryLabelPositions(
//            CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0));

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

        // set the background color for the chart...
//        plot.setForegroundAlpha(0.5f);


        //Set default bar render without shadow nor gradient
        BarRenderer barRenderer = (BarRenderer) plot.getRenderer();
        barRenderer.setBarPainter(new StandardBarPainter());
        barRenderer.setShadowVisible(false);

        plot.setBackgroundPaint(Color.white);
        plot.setOutlineVisible(false);

        try {
            ChartUtil.saveToFile(chart, "/tmp/stacked_bar.jpg", 1500, 500);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void createBarAndAreaChart() {

        // create the first dataset...
        DefaultCategoryDataset dataset1 = new DefaultCategoryDataset();
        dataset1.addValue(1, "BSI", "Jan'17");
        dataset1.addValue(1, "BSI", "Feb'17");
        dataset1.addValue(1, "BSI", "Mar'17");
        dataset1.addValue(3, "BSI", "Apr'17");
        dataset1.addValue(1, "BSI", "May'17");
        dataset1.addValue(0, "BSI", "Jun'17");
        dataset1.addValue(0, "BSI", "Jul'17");
        dataset1.addValue(5, "BSI", "Ago'17");

        dataset1.addValue(4, "Other Renal", "Jan'17");
        dataset1.addValue(6, "Other Renal", "Feb'17");
        dataset1.addValue(2, "Other Renal", "Mar'17");
        dataset1.addValue(6, "Other Renal", "Apr'17");
        dataset1.addValue(1, "Other Renal", "May'17");
        dataset1.addValue(1, "Other Renal", "Jun'17");
        dataset1.addValue(0, "Other Renal", "Jul'17");
        dataset1.addValue(4, "Other Renal", "Ago'17");

        dataset1.addValue(4, "Other Non Renal", "Jan'17");
        dataset1.addValue(3, "Other Non Renal", "Feb'17");
        dataset1.addValue(1, "Other Non Renal", "Mar'17");
        dataset1.addValue(2, "Other Non Renal", "Apr'17");
        dataset1.addValue(2, "Other Non Renal", "May'17");
        dataset1.addValue(1, "Other Non Renal", "Jun'17");
        dataset1.addValue(1, "Other Non Renal", "Jul'17");
        dataset1.addValue(4, "Other Non Renal", "Ago'17");


        // create the first renderer...
        //      final CategoryLabelGenerator generator = new StandardCategoryLabelGenerator();
        CategoryItemRenderer renderer = new BarRenderer();
        //    renderer.setLabelGenerator(generator);
        renderer.setBaseItemLabelsVisible(true);

        CategoryPlot plot = new CategoryPlot();

//        plot.setForegroundAlpha(0.5f);
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinesVisible(false);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinesVisible(false);
        plot.setRangeGridlinePaint(Color.white);

        plot.setDataset(dataset1);
        plot.setRenderer(renderer);

        //Set default bar render without shadow nor gradient
        BarRenderer barRenderer = (BarRenderer) plot.getRenderer();
        barRenderer.setBarPainter(new StandardBarPainter());
        barRenderer.setShadowVisible(false);
        barRenderer.setItemMargin(.0);

        CategoryAxis categoryAxis = new CategoryAxis("Hospitalizations");
        categoryAxis.setLowerMargin(.0);
        categoryAxis.setCategoryMargin(.1);
        categoryAxis.setUpperMargin(.0);
        plot.setDomainAxis(categoryAxis);

        NumberAxis rangeAxis = new NumberAxis("Patient Population");
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        plot.setRangeAxis(rangeAxis);

        plot.setOrientation(PlotOrientation.VERTICAL);
        plot.setRangeGridlinesVisible(true);
        plot.setDomainGridlinesVisible(true);

        Font font3 = new Font("Label", Font.PLAIN, 20);
        plot.getDomainAxis().setLabelFont(font3);
        plot.getRangeAxis().setLabelFont(font3);

        Font font = new Font("TickLabel", Font.PLAIN, 20);
        plot.getDomainAxis().setTickLabelFont(font);
        Font font2 = new Font("TickLabel", Font.PLAIN, 20);
        plot.getRangeAxis().setTickLabelFont(font2);

        // now create the second dataset and renderer...
        DefaultCategoryDataset dataset2 = new DefaultCategoryDataset();
        dataset2.addValue(9, "Total", "Jan'17");
        dataset2.addValue(10, "Total", "Feb'17");
        dataset2.addValue(4, "Total", "Mar'17");
        dataset2.addValue(11, "Total", "Apr'17");
        dataset2.addValue(4, "Total", "May'17");
        dataset2.addValue(2, "Total", "Jun'17");
        dataset2.addValue(1, "Total", "Jul'17");
        dataset2.addValue(13, "Total", "Ago'17");

        //Set series color
        List<Color> seriesColor = new ArrayList<>();
        Color red = new Color(255, 0, 0);
        Color green = new Color(0, 176, 80);
        Color yellow = new Color(255, 255, 0);
        Color blue = new Color(75, 148, 192);
        seriesColor.add(red);
        seriesColor.add(yellow);
        seriesColor.add(green);

        CategoryItemRenderer renderer2 = new FullAreaRender();
        CategoryPlot plot2 = renderer2.getPlot();
        renderer2.setSeriesPaint(0, blue);

        plot.setDataset(1, dataset2);
        plot.setRenderer(1, renderer2);
//        renderer2.getPlot().getDomainAxis().setCategoryMargin(0.0);

        for (int i = 0; i < seriesColor.size(); i++) {
            plot.getRenderer().setSeriesPaint(i, seriesColor.get(i));
        }
        // change the rendering order so the primary dataset appears "behind" the
        // other datasets...
        plot.setDatasetRenderingOrder(DatasetRenderingOrder.REVERSE);

        plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.STANDARD);
        final JFreeChart chart = new JFreeChart(plot);
        chart.setTitle("Hospitalization Summary");
        chart.removeLegend();

        try {
            ChartUtil.saveToFile(chart, "/tmp/area_bar_chart.jpg", 1500, 600);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void createAreaChart() {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dataset.addValue(110, "GLU", "Jan'17");
        dataset.addValue(95, "GLU", "Feb'17");
        dataset.addValue(105, "GLU", "Mar'17");
        dataset.addValue(70, "GLU", "Apr'17");
        dataset.addValue(148, "GLU", "May'17");
        dataset.addValue(115, "GLU", "Jun'17");

        final JFreeChart chart = ChartFactory.createStackedAreaChart(
                "",             // chart title
                "",               // domain axis label
                "",                  // range axis label
                dataset,                  // data
                PlotOrientation.VERTICAL, // orientation
                false,                     // include legend
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

        final CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setLowerMargin(0.0);
        domainAxis.setUpperMargin(0.0);
        domainAxis.setTickLabelFont(new Font("Values", Font.BOLD, 18));

        CategoryItemRenderer renderer = plot.getRenderer();
        renderer.setSeriesPaint(0, ChartUtil.randomColor());
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setBaseItemLabelsVisible(true);
        ItemLabelPosition position = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12,
                TextAnchor.TOP_CENTER);
        renderer.setBasePositiveItemLabelPosition(position);
        renderer.setBaseItemLabelFont(new Font("Values", Font.BOLD, 20));

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setLabelAngle(0 * Math.PI / 2.0);
        rangeAxis.setTickLabelsVisible(false);
        rangeAxis.setUpperMargin(0.4);
//        rangeAxis.setTickLabelFont(new Font("Values", Font.PLAIN, 20));

        Marker topMarker = new ValueMarker(150);
        topMarker.setPaint(Color.black);
        topMarker.setStroke(new BasicStroke(1.0f));
        topMarker.setLabel("Max");
        topMarker.setLabelFont(new Font("Marker Top", Font.BOLD, 20));
        topMarker.setLabelAnchor(RectangleAnchor.BOTTOM_RIGHT);
        topMarker.setLabelTextAnchor(TextAnchor.HALF_ASCENT_RIGHT);
        plot.addRangeMarker(topMarker, Layer.FOREGROUND);

        Marker bottomMarker = new ValueMarker(70);
        bottomMarker.setPaint(Color.black);
        bottomMarker.setStroke(new BasicStroke(1.0f));
        bottomMarker.setLabel("Min");
        bottomMarker.setLabelFont(new Font("Marker Bottom", Font.BOLD, 20));
        bottomMarker.setLabelAnchor(RectangleAnchor.BOTTOM_RIGHT);
        bottomMarker.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
        plot.addRangeMarker(bottomMarker, Layer.FOREGROUND);

        try {
            ChartUtil.saveToFile(chart, "/tmp/area_chart.jpg", 1500, 400);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    @Test
//    public void createLineChart() {
//
//        // Create Chart
//        XYChart chart = new XYChartBuilder().width(550).height(200).build();
//
//        chart.getStyler().setPlotBackgroundColor(ChartColor.getAWTColor(ChartColor.WHITE));
//        chart.getStyler().setPlotGridLinesColor(new Color(255, 255, 255));
//        chart.getStyler().setChartBackgroundColor(Color.WHITE);
////        chart.getStyler().setLegendBackgroundColor(Color.PINK);
////        chart.getStyler().setChartFontColor(Color.MAGENTA);
////        chart.getStyler().setChartTitleBoxBackgroundColor(new Color(0, 222, 0));
//        chart.getStyler().setChartTitleBoxVisible(true);
//        chart.getStyler().setChartTitleBoxBorderColor(Color.BLACK);
//        chart.getStyler().setPlotGridLinesVisible(false);
//
////        chart.getStyler().setAxisTickPadding(20);
//
////        chart.getStyler().setAxisTickMarkLength(15);
//
////        chart.getStyler().setPlotMargin(20);
//
//        chart.getStyler().setChartTitleFont(new Font(Font.MONOSPACED, Font.BOLD, 24));
//        chart.getStyler().setLegendFont(new Font(Font.SERIF, Font.PLAIN, 18));
////        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideSE);
////        chart.getStyler().setLegendSeriesLineLength(12);
//        chart.getStyler().setAxisTitleFont(new Font(Font.SANS_SERIF, Font.ITALIC, 18));
//        chart.getStyler().setAxisTickLabelsFont(new Font(Font.SERIF, Font.PLAIN, 11));
//        chart.getStyler().setDatePattern("MM/dd/yyyy");
//        chart.getStyler().setDecimalPattern("#0");
//        chart.getStyler().setLocale(Locale.ENGLISH);
//
//        chart.getStyler().setXAxisLogarithmic(false);
//        chart.getStyler().setXAxisLabelRotation(45);
//        chart.getStyler().setLegendVisible(false);
//
////        // Customize Chart
////        chart.getStyler().setPlotBackgroundColor(ChartColor.getAWTColor(ChartColor.WHITE));
////        chart.getStyler().setPlotGridLinesColor(new Color(255, 255, 255));
////        chart.getStyler().setChartBackgroundColor(Color.WHITE);
//////        chart.getStyler().setLegendBackgroundColor(Color.GRAY);
////        chart.getStyler().setChartFontColor(Color.BLACK);
//////        chart.getStyler().setChartTitleBoxBackgroundColor(new Color(0, 222, 0));
//////        chart.getStyler().setChartTitleBoxVisible(true);
//////        chart.getStyler().setChartTitleBoxBorderColor(Color.BLACK);
////        chart.getStyler().setPlotGridLinesVisible(true);
////
//////        chart.getStyler().setAxisTickPadding(20);
////
//////        chart.getStyler().setAxisTickMarkLength(15);
////
//////        chart.getStyler().setPlotMargin(20);
////
////        chart.getStyler().setLegendVisible(false);
////        chart.getStyler().setYAxisLogarithmic(true);
////        chart.getStyler().setXAxisLabelRotation(45);
////
//////        chart.getStyler().setChartTitleFont(new Font(Font.MONOSPACED, Font.BOLD, 24));
//////        chart.getStyler().setLegendFont(new Font(Font.SERIF, Font.PLAIN, 18));
//////        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideSE);
////        chart.getStyler().setLegendSeriesLineLength(12);
////        chart.getStyler().setAxisTitleFont(new Font(Font.SANS_SERIF, Font.ITALIC, 18));
////        chart.getStyler().setAxisTickLabelsFont(new Font(Font.SERIF, Font.PLAIN, 11));
////        chart.getStyler().setDatePattern("MM/dd/yyyy");
////        chart.getStyler().setDecimalPattern("#0.0");
////        chart.getStyler().setLocale(Locale.ENGLISH);
//
//        // generates linear data
//        List<Date> xData = new ArrayList<>();
//        List<Double> yData = new ArrayList<>();
//
//        try {
//
//            xData.add(new SimpleDateFormat("MM/dd/yyyy").parse("01/26/2017"));
//            xData.add(new SimpleDateFormat("MM/dd/yyyy").parse("02/07/2017"));
//            xData.add(new SimpleDateFormat("MM/dd/yyyy").parse("03/09/2017"));
//            xData.add(new SimpleDateFormat("MM/dd/yyyy").parse("04/20/2017"));
//            xData.add(new SimpleDateFormat("MM/dd/yyyy").parse("05/17/2017"));
//            xData.add(new SimpleDateFormat("MM/dd/yyyy").parse("06/12/2017"));
//            xData.add(new SimpleDateFormat("MM/dd/yyyy").parse("07/17/2017"));
//        } catch (Exception e) {
//
//        }
//
//        yData.add(173.0);
//        yData.add(136.0);
//        yData.add(135.0);
//        yData.add(233.0);
//        yData.add(471.0);
//        yData.add(207.0);
//        yData.add(172.0);
//
//        // Series
//        XYSeries series = chart.addSeries("Ferritin", xData, yData);
//        series.setLineColor(XChartSeriesColors.BLUE);
//        series.setMarkerColor(XChartSeriesColors.BLUE);
//        series.setMarker(SeriesMarkers.SQUARE);
//        series.setLineStyle(SeriesLines.SOLID);
//
//        try {
//            BitmapEncoder.saveBitmap(chart, "/tmp/line_chart", BitmapEncoder.BitmapFormat.PNG);
//            BitmapEncoder.saveBitmap(chart, "/tmp/line_chart", BitmapEncoder.BitmapFormat.JPG);
//            BitmapEncoder.saveJPGWithQuality(chart, "/tmp/line_chart_quality.jpg", 0.95f);
//            BitmapEncoder.saveBitmap(chart, "/tmp/line_chart", BitmapEncoder.BitmapFormat.BMP);
//            BitmapEncoder.saveBitmap(chart, "/tmp/line_chart", BitmapEncoder.BitmapFormat.GIF);
//
//            BitmapEncoder.saveBitmapWithDPI(chart, "/tmp/line_chart_300_DPI", BitmapEncoder.BitmapFormat.PNG, 300);
//            BitmapEncoder.saveBitmapWithDPI(chart, "/tmp/line_chart_300_DPI", BitmapEncoder.BitmapFormat.JPG, 300);
//            BitmapEncoder.saveBitmapWithDPI(chart, "/tmp/line_chart_300_DPI", BitmapEncoder.BitmapFormat.GIF, 300);
//
//            VectorGraphicsEncoder.saveVectorGraphic(chart, "/tmp/line_chart", VectorGraphicsEncoder.VectorGraphicsFormat.EPS);
//            VectorGraphicsEncoder.saveVectorGraphic(chart, "/tmp/line_chart", VectorGraphicsEncoder.VectorGraphicsFormat.PDF);
//            VectorGraphicsEncoder.saveVectorGraphic(chart, "/tmp/line_chart", VectorGraphicsEncoder.VectorGraphicsFormat.SVG);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

}

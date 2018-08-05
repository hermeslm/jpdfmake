package com.onedsol.tools.util.graph;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by hermeslm on 4/28/17.
 */
public class BarIndicator extends Indicator {

    private int barWidth = 4;
    private int barHeight;
    private Color barColor = Color.BLACK;
    private Color ellipseColor = Color.BLACK;
    private int ellipseWidth = 24;
    private int ellipseHeight = 24;

    public BarIndicator() {
    }

    public BarIndicator(int barWidth, int barHeight, int ellipseWidth, int ellipseHeight, Color color) {
        super();
        this.barWidth = barWidth;
        this.barHeight = barHeight;
        this.ellipseWidth = ellipseWidth;
        this.ellipseHeight = ellipseHeight;
        this.barColor = color;
        this.ellipseColor = color;
    }

    public BarIndicator(int barWidth, Color barColor, int ellipseWidth, int ellipseHeight, Color ellipseColor) {
    }

    public BarIndicator(int value, int imageWidth, int barWidth, int barHeight) {
        this.barWidth = barWidth;
        this.barHeight = barHeight;
    }

    public BufferedImage draw(BufferedImage gaugeImage, Double value) {

        this.barHeight = gaugeImage.getHeight();
        Double pixelValue = value * gaugeImage.getWidth() / 100;

        Graphics2D gd2 = gaugeImage.createGraphics();
        gd2.setColor(this.barColor);

        Rectangle rectangle = new Rectangle(pixelValue.intValue(), 0, barWidth, barHeight);
        gd2.fill(rectangle);
        gd2.dispose();

        Graphics2D circle = gaugeImage.createGraphics();
        circle.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        circle.setPaint(this.ellipseColor);

        int x = pixelValue.intValue() + (barWidth / 2) - (ellipseWidth / 2);
        int y = gaugeImage.getHeight() / 2 - (ellipseWidth / 2);

        circle.fillOval(x, y, ellipseWidth, ellipseHeight);
        circle.dispose();

        return gaugeImage;
    }

    public int getBarWidth() {
        return barWidth;
    }

    public void setBarWidth(int barWidth) {
        this.barWidth = barWidth;
    }

    public int getBarHeight() {
        return barHeight;
    }

    public void setBarHeight(int barHeight) {
        this.barHeight = barHeight;
    }

    public int getEllipseWidth() {
        return ellipseWidth;
    }

    public void setEllipseWidth(int ellipseWidth) {
        this.ellipseWidth = ellipseWidth;
    }

    public int getEllipseHeight() {
        return ellipseHeight;
    }

    public void setEllipseHeight(int ellipseHeight) {
        this.ellipseHeight = ellipseHeight;
    }

    public Color getBarColor() {
        return barColor;
    }

    public void setBarColor(Color barColor) {
        this.barColor = barColor;
    }

    public Color getEllipseColor() {
        return ellipseColor;
    }

    public void setEllipseColor(Color ellipseColor) {
        this.ellipseColor = ellipseColor;
    }

}

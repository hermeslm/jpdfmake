package com.onedsol.tools.util.graph;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * Created by hermeslm on 4/28/17.
 */
public class RectIndicator extends Indicator {

    private int width = 100;
    private int height;
    private int arcWidth = 10;
    private int arcHeight = 10;
    private Color borderColor = Color.BLACK;
    private int borderWidth = 10;
    private Double value;

    public RectIndicator() {
    }

    public RectIndicator(int width, int height, int borderWidth) {
        this.width = width;
        this.height = height;
        this.borderWidth = borderWidth;
    }

    public RectIndicator(int width, int height, int borderWidth, Double value) {
        this.width = width;
        this.height = height;
        this.value = value;
        this.borderWidth = borderWidth;
    }

    public RectIndicator(int width, int height, Color borderColor, int borderWidth) {
        this.width = width;
        this.height = height;
        this.borderColor = borderColor;
        this.borderWidth = borderWidth;
    }

    @Override
    public BufferedImage draw(BufferedImage gaugeImage, Double value) {

        height = gaugeImage.getHeight();
        Double pixelValue = value * gaugeImage.getWidth() / 100;

        //Check if pixel value plus indicator width is greater than image width,
        // we adjust this value because indicator will render in image outside.
        if (pixelValue + width > gaugeImage.getWidth()) {
            pixelValue = new Double(gaugeImage.getWidth() - width);
        }

        Graphics2D gd2 = gaugeImage.createGraphics();
//        gd2.setColor(this.barColor);
        gd2.setPaint(borderColor);
        Stroke stroke1 = new BasicStroke(6f);
        gd2.setColor(borderColor);
        gd2.setStroke(stroke1);
        gd2.drawRoundRect(pixelValue.intValue(), 0, width, height, arcWidth, arcHeight);

        Font valueFont = new Font("ValueFont", Font.BOLD, 20);
        FontMetrics fm = gd2.getFontMetrics(valueFont);
        gd2.setFont(valueFont);
        Rectangle2D rect = fm.getStringBounds(value.toString(), gd2);

        int textHeight = (int) (rect.getHeight());
        int textWidth = (int) (rect.getWidth());

        // Center text horizontally and vertically
        int x = (width - textWidth) / 2;
        int y = (height - textHeight) / 2 + fm.getAscent();

        gd2.drawString(value.toString(), x + pixelValue.intValue(), y);  // Draw the string.
        gd2.dispose();

        return gaugeImage;
    }
}

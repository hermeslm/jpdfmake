package com.onedsol.tools.util.graph;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
 * Created by hermeslm on 4/28/17.
 */
public class GaugeChart {

    private BufferedImage gaugeImage;

    private Indicator indicator;

    private int minValue = 0;

    private int maxValue = 100;

    private int initialValue = 0;

    private Double value = 0.0;

    public GaugeChart(BufferedImage gaugeImage, Indicator indicator) {

        this.gaugeImage = gaugeImage;
        this.indicator = indicator;
    }

    public GaugeChart(BufferedImage gaugeImage, int minValue, int maxValue, int initialValue, Indicator indicator) {

        this.gaugeImage = gaugeImage;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.initialValue = initialValue;
    }

    public GaugeChart(BufferedImage gaugeImage) {

        this.gaugeImage = gaugeImage;
    }

    public GaugeChart(BufferedImage gaugeImage, boolean flip) {

        if (flip) {
            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-gaugeImage.getWidth(null), 0);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            this.gaugeImage = op.filter(gaugeImage, null);
        } else {
            this.gaugeImage = gaugeImage;
        }
    }

    public BufferedImage drawAsImage() {

        if (indicator != null) {
            return indicator.draw(gaugeImage, value);
        }
        return null;
    }

    public BufferedImage drawAsImage(Indicator indicator) {

        return indicator.draw(gaugeImage, value);
    }

    public GaugeChart indicator(Indicator indicator) {

        this.indicator = indicator;
        return this;
    }

    public BufferedImage getGaugeImage() {
        return gaugeImage;
    }

    public void setGaugeImage(BufferedImage gaugeImage) {
        this.gaugeImage = gaugeImage;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public int getInitialValue() {
        return initialValue;
    }

    public void setInitialValue(int initialValue) {
        this.initialValue = initialValue;
    }

    public GaugeChart value(Double value) {
        this.value = value;
        return this;
    }

    public Indicator getIndicator() {
        return indicator;
    }

    public void setIndicator(Indicator indicator) {
        this.indicator = indicator;
    }
}

package com.onedsol.tools.util.graph;

import java.awt.image.BufferedImage;

public abstract class Indicator {

    public abstract BufferedImage draw(BufferedImage gaugeImage, Double value);
}

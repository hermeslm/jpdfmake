package com.onedsol.tools.utils.graph;

import java.awt.image.BufferedImage;

public abstract class Indicator {

    public abstract BufferedImage draw(BufferedImage gaugeImage, Double value);
}

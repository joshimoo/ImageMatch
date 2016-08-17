package com.sleepycoders.imagematch.metric;

import java.awt.image.BufferedImage;

/**
 * @author Joshua Moody (joshimoo@hotmail.de)
 */
public interface IMetric {
    float calculate(final BufferedImage a, final BufferedImage b);
}

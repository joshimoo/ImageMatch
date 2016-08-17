package com.sleepycoders.imagematch.metric;

import com.sleepycoders.imagematch.image.Image;

/**
 * @author Joshua Moody (joshimoo@hotmail.de)
 */
public interface IMetric {
    float calculate(final Image a, final Image b);
}

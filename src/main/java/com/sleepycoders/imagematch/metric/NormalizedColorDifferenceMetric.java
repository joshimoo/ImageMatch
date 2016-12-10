package com.sleepycoders.imagematch.metric;

import com.sleepycoders.imagematch.image.Image;

/**
 * @author Joshua Moody (joshimoo@hotmail.de)
 */
public class NormalizedColorDifferenceMetric extends ColorDifferenceMetric {
    @Override
    public float calculate(Image a, Image b) {
        float sum = super.calculate(a, b);
        int maxColorDifference = 255;
        int colorChannels = 3;

        // pixel count should never be 0 since you cannot create 0 sized BufferedImages
        // but just in case that api changes in the future, I added check to protect against a div by 0
        int pixelCount = a.getHeight() * a.getWidth();
        return (sum / (maxColorDifference * colorChannels)) / (pixelCount != 0 ? pixelCount : 1);
    }
}

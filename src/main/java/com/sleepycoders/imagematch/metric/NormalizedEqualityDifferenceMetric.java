package com.sleepycoders.imagematch.metric;

import com.sleepycoders.imagematch.image.Image;

/**
 * @author Joshua Moody (joshimoo@hotmail.de)
 */
public class NormalizedEqualityDifferenceMetric implements IMetric {
    @Override
    public float calculate(Image a, Image b) {
        if(a.getWidth() != b.getWidth() || a.getHeight() != b.getHeight()) {
            throw new IllegalArgumentException("can only calculate metric on same size images");
        }

        int equalCount = 0;
        for (int y = 0; y < a.getHeight(); y++) {
            for (int x = 0; x < a.getWidth(); x++) {
                if (a.getRGB(x, y) == b.getRGB(x, y)) {
                    equalCount++;
                }
            }
        }

        // pixel count should never be 0 since you cannot create 0 sized BufferedImages
        // but just in case that api changes in the future, I added check to protect against a div by 0
        int pixelCount = a.getHeight() * a.getWidth();
        return 1.0f - (float)equalCount / (pixelCount != 0 ? pixelCount : 1);
    }
}

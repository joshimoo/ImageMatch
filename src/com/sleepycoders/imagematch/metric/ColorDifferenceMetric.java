package com.sleepycoders.imagematch.metric;

import java.awt.image.BufferedImage;

/**
 * @author Joshua Moody (joshimoo@hotmail.de)
 */
public class ColorDifferenceMetric implements IMetric {

    /**
     * calculates the absolute sum of difference for all color channels
     */
    @Override
    public float calculate(final BufferedImage a, final BufferedImage b) {
        if(a.getWidth() != b.getWidth() || a.getHeight() != b.getHeight()) {
            throw new IllegalArgumentException("can only calculate metric on same size images");
        }

        int rMask = 0xFF0000, gMask = 0xFF00, bMask = 0xFF;
        int rDiff = 0, gDiff = 0, bDiff = 0;
        for (int y = 0; y < a.getHeight(); y++) {
            for (int x = 0; x < a.getWidth(); x++) {
                // TODO: getRGB() calls ComponentColorModel.getRGBComponent() which is really slow!!!
                // TODO: best to replace the dependency on Buffered Image and instead have all Metrics work on
                // TODO: an ArraySlice wrapper of the raw values that way, we can calculate the raw values for the pictures once
                // TODO: and just pass a different view slice
                // getRGB() returns a pixel in the default color format ARGB
                int p1 = a.getRGB(x, y);
                int p2 = b.getRGB(x, y);

                rDiff += Math.abs((p1 & rMask) - (p2 & rMask)) >>> 16;
                gDiff += Math.abs((p1 & gMask) - (p2 & gMask)) >>> 8;
                bDiff += Math.abs((p1 & bMask) - (p2 & bMask));
            }
        }

        return rDiff + gDiff + bDiff;
    }
}

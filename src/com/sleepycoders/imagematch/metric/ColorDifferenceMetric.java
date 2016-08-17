package com.sleepycoders.imagematch.metric;

import com.sleepycoders.imagematch.image.Image;

/**
 * @author Joshua Moody (joshimoo@hotmail.de)
 */
public class ColorDifferenceMetric implements IMetric {

    /**
     * calculates the absolute sum of difference for all color channels
     * @param a needs to be the same size as b
     * @param b needs to be the same size as a
     */
    @Override
    public float calculate(final Image a, final Image b) {
        if(a.getWidth() != b.getWidth() || a.getHeight() != b.getHeight()) {
            throw new IllegalArgumentException("can only calculate metric on same size images");
        }

        int rDiff = 0, gDiff = 0, bDiff = 0;
        for (int y = 0; y < a.getHeight(); y++) {
            for (int x = 0; x < a.getWidth(); x++) {
                // getRGB() returns a pixel in the default color format ARGB
                int p1 = a.getRGB(x, y);
                int p2 = b.getRGB(x, y);

                rDiff += Math.abs((p1 & Image.RED_MASK) - (p2 & Image.RED_MASK)) >>> 16;
                gDiff += Math.abs((p1 & Image.GREEN_MASK) - (p2 & Image.GREEN_MASK)) >>> 8;
                bDiff += Math.abs((p1 & Image.BLUE_MASK) - (p2 & Image.BLUE_MASK));
            }
        }

        return rDiff + gDiff + bDiff;
    }
}

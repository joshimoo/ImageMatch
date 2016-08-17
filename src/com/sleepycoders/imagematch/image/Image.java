package com.sleepycoders.imagematch.image;

import java.awt.image.BufferedImage;

/**
 * Fast Image Class, that supports sub views on this images data
 * @author Joshua Moody (joshimoo@hotmail.de)
 */
public class Image {
    public static final int ALPHA_MASK = 0xFF000000, RED_MASK = 0xFF0000, GREEN_MASK = 0xFF00, BLUE_MASK = 0xFF;

    // if required you can replace this with a 1D array for faster performance
    final int[][] data;

    private final int height;
    private final int width;

    private final int xOffset;
    private final int yOffset;

    public Image(final BufferedImage img) {
        xOffset = 0;
        yOffset = 0;
        width = img.getWidth();
        height = img.getHeight();
        data = new int[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // getRGB is slow, but we only have to carry the cost once
                // when initializing our img data
                data[y][x] = img.getRGB(x, y);
            }
        }
    }

    public Image(final int width, final int height) {
        xOffset = 0;
        yOffset = 0;
        this.width = width;
        this.height = height;
        data = new int[height][width];
    }

    private Image(final int x, final int y, final int w, final int h, final int[][] data) {
        xOffset = x;
        yOffset = y;
        width = w;
        height = h;
        this.data = data;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getRGB(final int x, final int y) {
        // TODO: do bounds checking
        return data[y + yOffset][x + xOffset];
    }

    public void setRGB(final int x, final int y, int argb) {
        // TODO: do bounds checking
        this.data[y + yOffset][x + xOffset] = argb;
    }

    public Image getSubimage(final int x, final int y, final int w, final int h) {
        // TODO: do bounds checking
        return new Image(xOffset + x, yOffset + y, w, h, data);
    }
}

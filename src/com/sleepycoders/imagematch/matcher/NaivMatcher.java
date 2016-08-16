package com.sleepycoders.imagematch.matcher;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Joshua Moody (joshimoo@hotmail.de)
 */
public class NaivMatcher implements IMatcher {

    /**
     * Naiv Algorithm that performs a full search
     * The algorithm also only works if the sub image is completly contained inside of the src image
     * @param src
     * @param sub
     * @param likenessThreshold
     * @return List of matches, with the displacements and likeness factors
     */
    public List<MatchResult> match(BufferedImage src, BufferedImage sub, float likenessThreshold) {
        List<MatchResult> matches = new ArrayList<>();

        // pixels outside image are considered black
        // alternatives: wraparound, mirror, etc

        int w = src.getWidth();
        int h = src.getHeight();
        int kw = sub.getWidth();
        int kh = sub.getHeight();

        // naive algorithm, that only work if the sub image is completely contained inside of the src image
        for (int y = 0; y < h - kh; y++) {
            for (int x = 0; x < w - kw; x++) {
                // does not copy, instead just provides a sliced view on the underlying data
                BufferedImage window = src.getSubimage(x, y, kw, kh);
                if(equals(window, sub)) {
                    matches.add(new MatchResult(x, y));
                }
            }
        }

        // the same approach could be used on a greyscale image or any other color model
        return matches;
    }

    public boolean equals(BufferedImage a, BufferedImage b) {
        if (a.getWidth() != b.getWidth() || a.getHeight() != b.getHeight()) { return false; }

        // images are the same size
        // do a pixel per pixel comparison
        for (int y = 0; y < a.getHeight(); y++) {
            for (int x = 0; x < a.getWidth(); x++) {
                if(a.getRGB(x, y) != b.getRGB(x, y)) { return false; }
            }
        }

        return true;
    }
}

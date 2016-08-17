package com.sleepycoders.imagematch.matcher;

import com.sleepycoders.imagematch.metric.IMetric;
import com.sleepycoders.imagematch.metric.NormalizedColorDifferenceMetric;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Joshua Moody (joshimoo@hotmail.de)
 */
public class CorrelationMatcher implements IMatcher {

    /**
     * evaluates each window inside of the src image against a passed metric
     * uses the normalizedColorDifference metric by default
     * uses a likeness factor of 0.9 by default
     * @return List of matches, with the displacements and likeness factors
     */
    @Override
    public List<MatchResult> match(final BufferedImage src, final BufferedImage sub) {
        return match(src, sub, 0.9f);
    }

    /**
     * evaluates each window inside of the src image against a passed metric
     * uses the normalizedColorDifference metric by default
     * @return List of matches, with the displacements and likeness factors
     */
    @Override
    public List<MatchResult> match(final BufferedImage src, final BufferedImage sub, final float likenessThreshold) {
        return match(src, sub, likenessThreshold, new NormalizedColorDifferenceMetric());
    }


    /**
     * evaluates each window inside of the src image against a passed metric
     * @param metric must be normalized metric
     * @return List of matches, with the displacements and likeness factors
     */
    public List<MatchResult> match(final BufferedImage src, final BufferedImage sub, final float likenessThreshold, final IMetric metric) {

        if(src == null || sub == null) { return NO_MATCH; }
        List<MatchResult> matches = new ArrayList<>();

        int w = src.getWidth();
        int h = src.getHeight();
        int kw = sub.getWidth();
        int kh = sub.getHeight();

        // naive algorithm, that only work if the sub image is completely contained inside of the src image
        // you could also evaluate the sub image shifted outside of the current image
        // but then you have to decide for a boundary strategy
        // possible boundary strategies would be:
        // - pixels outside image are considered black/white
        // - closest valid neighbour
        // - wraparound to other side of image
        // - mirror coordinates
        System.out.println("pixel-ops: " + ((long)((h-kh) * (w-kw)) * (kw * kh)) );
        for (int y = 0; y <= h - kh; y++) {
            for (int x = 0; x <= w - kw; x++) {
                // does not copy, instead just provides a sliced view on the underlying data
                BufferedImage window = src.getSubimage(x, y, kw, kh);
                float diff = metric.calculate(window, sub);
                float likeness = 1 - diff;

                if(likeness >= likenessThreshold) {
                    matches.add(new MatchResult(x, y, likeness));
                }
            }
        }

        return matches;
    }

}

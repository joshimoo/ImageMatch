package com.sleepycoders.imagematch.matcher;

import com.sleepycoders.imagematch.image.Image;

import java.util.Collections;
import java.util.List;

/**
 * @author Joshua Moody (joshimoo@hotmail.de)
 */
public class ExactMatcher implements IMatcher {

    // Store Results once, no need for further allocations
    private final static MatchResult exactMatch = new MatchResult(0, 0, 1.0f);
    private final static List<MatchResult> MATCH = Collections.singletonList(exactMatch);

    /**
     * compares two images for equality
     * @return emptyList or list with single element match result
     */
    @Override
    public List<MatchResult> match(final Image src, final Image sub) {
        if (src.getWidth() != sub.getWidth() || src.getHeight() != sub.getHeight()) { return NO_MATCH; }

        for (int y = 0; y < src.getHeight(); y++) {
            for (int x = 0; x < src.getWidth(); x++) {
                if(src.getRGB(x, y) != sub.getRGB(x, y)) { return NO_MATCH; }
            }
        }

        return MATCH;
    }


    /**
     * compares two images for equality
     * @param likenessThreshold this matcher checks for equality so this param is ignored
     * @return emptyList or list with single element match result
     */
    @Override
    public List<MatchResult> match(final Image src, final Image sub, final float likenessThreshold) {
        return match(src, sub);
    }
}

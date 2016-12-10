package com.sleepycoders.imagematch.matcher;

import com.sleepycoders.imagematch.image.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Joshua Moody (joshimoo@hotmail.de)
 */
public class NaivMatcher implements IMatcher {

    @Override
    public List<MatchResult> match(final Image src, final Image sub) {
        return match(src, sub, 1.0f);
    }

    /**
     * Naiv Algorithm that performs a full search
     * The algorithm only works if the sub image is completely contained inside of the src image
     * @param likenessThreshold this matcher checks for equality so this param is ignored  @return List of matches, with the displacements and likeness factors
     */
    @Override
    public List<MatchResult> match(final Image src, final Image sub, final float likenessThreshold) {
        if(src == null || sub == null) { return NO_MATCH; }
        List<MatchResult> matches = new ArrayList<>();
        ExactMatcher exact = new ExactMatcher();

        int w = src.getWidth();
        int h = src.getHeight();
        int kw = sub.getWidth();
        int kh = sub.getHeight();

        // naive algorithm, that only work if the sub image is completely contained inside of the src image
        for (int y = 0; y <= h - kh; y++) {
            for (int x = 0; x <= w - kw; x++) {
                // does not copy, instead just provides a sliced view on the underlying data
                Image window = src.getSubimage(x, y, kw, kh);
                if(exact.match(window, sub) != NO_MATCH) {
                    matches.add(new MatchResult(x, y));
                }
            }
        }

        return matches;
    }

}

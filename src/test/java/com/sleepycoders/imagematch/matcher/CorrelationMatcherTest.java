package com.sleepycoders.imagematch.matcher;

import com.sleepycoders.imagematch.image.Image;
import com.sleepycoders.imagematch.metric.NormalizedEqualityDifferenceMetric;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Joshua Moody (joshimoo@hotmail.de)
 */
public class CorrelationMatcherTest extends BaseMatcherTest {

    @Override
    IMatcher createMatcher() {
        return new CorrelationMatcher();
    }

    @Test
    public void testMatchCustomMetric() throws Exception {
        CorrelationMatcher matcher = (CorrelationMatcher) createMatcher();
        Image[] images = createTestImages();
        List<MatchResult> matches = matcher.match(images[0], images[1], 0.9f, new NormalizedEqualityDifferenceMetric());

        // descendingly sort the match results based on likeness
        // TODO: add comparator to MatchResult
        matches.sort( (a,b) -> a.likeness > b.likeness ? -1 : a.likeness < b.likeness ? 1 : 0 );
        MatchResult r = matches.get(0);
        assertTrue(r.x == 100 && r.y == 100);
    }
}
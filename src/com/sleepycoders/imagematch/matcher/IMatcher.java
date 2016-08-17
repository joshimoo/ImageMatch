package com.sleepycoders.imagematch.matcher;

import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.List;

/**
 * @author Joshua Moody (joshimoo@hotmail.de)
 */
public interface IMatcher {
    List<MatchResult> NO_MATCH = Collections.emptyList();

    List<MatchResult> match(final BufferedImage src, final BufferedImage sub);
    List<MatchResult> match(final BufferedImage src, final BufferedImage sub, final float likenessThreshold);
}

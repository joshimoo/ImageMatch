package com.sleepycoders.imagematch.matcher;

import com.sleepycoders.imagematch.image.Image;

import java.util.Collections;
import java.util.List;

/**
 * @author Joshua Moody (joshimoo@hotmail.de)
 */
public interface IMatcher {
    List<MatchResult> NO_MATCH = Collections.emptyList();

    List<MatchResult> match(final Image src, final Image sub);
    List<MatchResult> match(final Image src, final Image sub, final float likenessThreshold);
}

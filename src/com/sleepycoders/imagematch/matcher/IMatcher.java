package com.sleepycoders.imagematch.matcher;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * @author Joshua Moody (joshimoo@hotmail.de)
 */
public interface IMatcher {
    public List<MatchResult> match(BufferedImage src, BufferedImage sub, float likenessThreshold);
}

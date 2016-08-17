package com.sleepycoders.imagematch.matcher;

/**
 * @author Joshua Moody (joshimoo@hotmail.de)
 */
public class MatchResult {
    public final int x;
    public final int y;
    public final float likeness;

    MatchResult(final int xDisplacement, final int yDisplacement, final float likeness) {
        this.x = xDisplacement;
        this.y = yDisplacement;
        this.likeness = likeness;
    }

    /**
     * Default Constructor that only specifies displacement values, likeness value is assumed to be 100%
     * @param xDisplacement how far to offset the sub image (starting from top left corner) to fit the src image
     * @param yDisplacement how far to offset the sub image (starting from top left corner) to fit the src image
     */
    MatchResult(final int xDisplacement, final int yDisplacement) {
        this(xDisplacement, yDisplacement, 1.0f);
    }

    @Override
    public String toString() {
        return String.format("xOffset: %d yOffset: %d likeness: %f%n", this.x, this.y, this.likeness);
    }
}

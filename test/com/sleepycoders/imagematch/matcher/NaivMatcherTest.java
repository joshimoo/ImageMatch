package com.sleepycoders.imagematch.matcher;

import org.junit.Test;
import com.sleepycoders.imagematch.image.Image;
import java.awt.image.BufferedImage;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Joshua Moody (joshimoo@hotmail.de)
 */
public class NaivMatcherTest {

    Image[] createTestImages() {

        // src, sub, mismatch
        BufferedImage[] images = new BufferedImage[3];
        BufferedImage src = new BufferedImage(200, 200, BufferedImage.TYPE_BYTE_GRAY);
        BufferedImage mismatch = new BufferedImage(50, 50, BufferedImage.TYPE_BYTE_GRAY);

        // todo: create sample images or load via file
        for (int y = 0; y < src.getHeight(); y++) {
            for (int x = 0; x < src.getWidth(); x++) {
                int color = y >= 115 && y <= 130 && x >= 115 && x <= 130 ? 200 : 100;
                src.setRGB(x, y, color);
            }
        }

        // src.getSubimage(100, 100, 50, 50)
        Image wrapper = new Image(src);
        return new Image[] { wrapper, wrapper.getSubimage(100, 100, 50, 50), new Image(mismatch)};
    }

    @Test
    public void testMatch() throws Exception {
        Image[] images = createTestImages();
        IMatcher matcher = new NaivMatcher();
        List<MatchResult> matches = matcher.match(images[0], images[1], 1);

        // descendingly sort the match results based on likeness
        matches.sort( (a,b) -> a.likeness > b.likeness ? -1 : a.likeness < b.likeness ? 1 : 0 );
        MatchResult r = matches.get(0);
        assertTrue(r.x == 100 && r.y == 100);
    }

    @Test
    public void testNoMatch() throws Exception {
        Image[] images = createTestImages();
        IMatcher matcher = new NaivMatcher();
        List<MatchResult> matches = matcher.match(images[0], images[2], 1);

        assertTrue(matches.size() == 0);
    }

    @Test
    public void testEquals() throws Exception {
        Image[] images = createTestImages();
        IMatcher matcher = new NaivMatcher();
        List<MatchResult> matches = matcher.match(images[0], images[0]);
        assertTrue(matches.size() == 1);
        MatchResult r = matches.get(0);
        assertTrue(r.x == 0 && r.y == 0 && r.likeness == 1);
    }

    /**
     * You cannot create 0 width/height BufferedImage's
     */
    @Test(expected = IllegalArgumentException.class)
    public void testEmpty() throws Exception {
        Image empty = new Image(new BufferedImage(0, 0, BufferedImage.TYPE_INT_RGB));

        // our matching would work with 0 size images, if it wasn't for BufferedImage's problem
        IMatcher matcher = new NaivMatcher();
        List<MatchResult> matches = matcher.match(empty, empty);
        assertTrue(matches.size() == 1);
        MatchResult r = matches.get(0);
        assertTrue(r.x == 0 && r.y == 0 && r.likeness == 1);
    }
}
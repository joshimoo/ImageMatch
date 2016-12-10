package com.sleepycoders.imagematch.matcher;

import org.junit.Test;
import com.sleepycoders.imagematch.image.Image;
import java.awt.image.BufferedImage;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Joshua Moody (joshimoo@hotmail.de)
 */
public class NaivMatcherTest extends BaseMatcherTest {

    @Override
    IMatcher createMatcher() {
        return new NaivMatcher();
    }
}
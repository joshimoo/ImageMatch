package com.sleepycoders.imagematch.matcher;

/**
 * @author Joshua Moody (joshimoo@hotmail.de)
 */
public class ExactMatcherTest extends BaseMatcherTest {

    @Override
    IMatcher createMatcher() {
        return new ExactMatcher();
    }

    @Override
    public void testMatch() throws Exception {
        this.testEquals();
    }
}
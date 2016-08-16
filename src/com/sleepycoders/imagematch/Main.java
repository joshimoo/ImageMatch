package com.sleepycoders.imagematch;

import com.sleepycoders.imagematch.matcher.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // verify args
        if(args.length < 2) {
            System.err.println("example usage: java imagematch src.image sub.image");
            System.exit(-1);
        }

        // load images
        BufferedImage src = loadImage(args[0]);
        BufferedImage sub = loadImage(args[1]);

        IMatcher matcher = new NaivMatcher();
        List<MatchResult> matches = matcher.match(src, sub, 1);
        for (MatchResult r : matches) {
            System.out.println(r.toString());
        }
    }

    public static BufferedImage loadImage(final String path) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("image: " + path + " could not be loaded");
        }

        return img;
    }

    public void writeImage(final BufferedImage img, final String path) {

    }
}

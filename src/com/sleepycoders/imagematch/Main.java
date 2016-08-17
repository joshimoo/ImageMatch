package com.sleepycoders.imagematch;

import com.sleepycoders.imagematch.image.Image;
import com.sleepycoders.imagematch.matcher.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(final String[] args) {

        // verify args
        if(args.length < 2) {
            System.err.println("example usage: java imagematch src.image sub.image");
            System.exit(-1);
        }

        // load images
        Image src = new Image(loadImage(args[0]));
        Image sub = new Image(loadImage(args[1]));
        BufferedImage out = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_RGB);

        IMatcher matcher = new CorrelationMatcher();
        List<MatchResult> matches = matcher.match(src, sub);

        // sort the match results based on likeness descendingly
        matches.sort( (a,b) -> a.likeness > b.likeness ? -1 : a.likeness < b.likeness ? 1 : 0 );
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

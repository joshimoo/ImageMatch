package com.sleepycoders.imagematch;

import com.sleepycoders.imagematch.effect.Effect;
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
        if(args.length < 3) {
            System.err.println("example usage: java imagematch src.image sub.image out.image");
            System.exit(-1);
        }

        // load images
        Image src = new Image(loadImage(args[0]));
        Image sub = new Image(loadImage(args[1]));

        IMatcher matcher = new CorrelationMatcher();
        List<MatchResult> matches = matcher.match(src, sub);

        // sort the match results based on likeness descendingly
        matches.sort( (a,b) -> a.likeness > b.likeness ? -1 : a.likeness < b.likeness ? 1 : 0 );
        for (MatchResult r : matches) {
            System.out.println(r.toString());
        }

        // place sub image on black background at the correct position
        if(matches.size() > 0) {
            BufferedImage out = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_RGB);
            MatchResult bestMatch = matches.get(0);
            for (int y = 0; y < sub.getHeight(); y++) {
                for (int x = 0; x < sub.getWidth(); x++) {
                    int xOut = x + bestMatch.x;
                    int yOut = y + bestMatch.y;
                    int rgb = sub.getRGB(x, y);
                    out.setRGB(xOut, yOut, rgb);
                }
            }

            writeImage(out, args[2]);

        } else {
            System.out.println("could not find any matches for image \ntry lowering likenessThreshold");
        }

        // TODO: let's try some filters & effects
        if(args.length > 3) {
            Effect.apply(src, Effect.sepiaEffect);
            writeImage(src, args[3]);
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

    public static void writeImage(final BufferedImage img, final String path) {
        try {
            // path to file needs to exist already since createNewFile does not create intermediate paths
            File f = new File(path);
            f.createNewFile();
            ImageIO.write(img, "bmp", f);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("image: " + path + " could not be written");
        }
    }

    public static void writeImage(final Image img, final String path) {
        // because of lazyness we convert to BufferedImage and use the existing ImageIO stuff
        BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                out.setRGB(x, y, img.getRGB(x, y));
            }
        }

        writeImage(out, path);
    }
}

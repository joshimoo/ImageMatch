package com.sleepycoders.imagematch;

import com.sleepycoders.imagematch.effect.Effect;
import com.sleepycoders.imagematch.image.Image;
import com.sleepycoders.imagematch.matcher.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.function.IntUnaryOperator;

public class Main {

    public static Image createMatchImaage(Image src, Image sub, MatchResult match) {
        Image out = new Image(src.getWidth(), src.getHeight());
        for (int y = 0; y < sub.getHeight(); y++) {
            for (int x = 0; x < sub.getWidth(); x++) {
                int xOut = x + match.x;
                int yOut = y + match.y;
                int rgb = sub.getRGB(x, y);
                out.setRGB(xOut, yOut, rgb);
            }
        }

        return out;
    }

    public static List<MatchResult> matchImage(IMatcher matcher, Image src, Image sub) {
        List<MatchResult> matches = matcher.match(src, sub);

        // sort the match results based on likeness descendingly
        matches.sort(Comparator.comparing((MatchResult m) -> m.likeness).reversed());
        for (MatchResult r : matches) {
            System.out.println(r.toString());
        }

        return matches;
    }

    public static void processMatcherCommand(IMatcher matcher, String srcFile, String outFile, String subFile) {

        // load images
        Image src = new Image(loadImage(srcFile));
        Image sub = new Image(loadImage(subFile));

        // process images
        List<MatchResult> matches = matchImage(matcher, src, sub);

        // place sub image on black background at the correct position
        if(matches.size() > 0) {
            Image out = createMatchImaage(src, sub, matches.get(0));
            writeImage(out, outFile);
        } else {
            System.out.println("could not find any matches for image \ntry lowering likenessThreshold");
        }
    }

    public static void processFilterCommand(IntUnaryOperator filter, String srcFile, String outFile) {
        Image src = new Image(loadImage(srcFile));
        Effect.apply(src, filter);
        writeImage(src, outFile);
    }

    public static void main(final String[] args) {

        // verify args
        if(args.length < 4) {
            System.err.println("example usage: java imagematch command option src.image out.image [sub.image]");
            System.exit(-1);
        }

        // TODO: switch to commons cli or args4j
        // this is just quick and dirty command line parsing
        switch(args[0]) {
            case "match":
                if(args.length < 5) {
                    System.err.println("missing required Command Line Parameters");
                    System.err.println("example usage: java imagematch matcher naiv src.image out.image sub.image");
                    System.exit(-1);
                }

                IMatcher matcher;
                switch (args[1]) {
                    case "exact": matcher = new ExactMatcher(); break;
                    case "naiv": matcher = new NaivMatcher(); break;
                    case "corr": matcher = new CorrelationMatcher(); break;
                    default: throw new IllegalArgumentException("unknown matcher specified");
                }
                System.out.println("using matcher: " + args[1] + "\nsrc: " + args[2] + "\nsub: " + args[4] + "\nout: " + args[3]);
                processMatcherCommand(matcher, args[2], args[3], args[4]);
            break;

            case "filter":
                IntUnaryOperator filter;
                switch (args[1]) {
                    case "sepia": filter = Effect.sepiaEffect; break;
                    case "red": filter = Effect.redEffect; break;
                    case "green": filter = Effect.greenEffect; break;
                    case "blue": filter = Effect.blueEffect; break;
                    default: throw new IllegalArgumentException("unknown filter specified");
                }
                System.out.println("applying filter: " + args[1] + "\nsrc: " + args[2] + "\nout: " + args[3]);
                processFilterCommand(filter, args[2], args[3]);
            break;
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
            ImageIO.write(img, "png", f);
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

package com.sleepycoders.imagematch.effect;

import com.sleepycoders.imagematch.image.Image;

import java.util.function.IntBinaryOperator;
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;

/**
 * @author Joshua Moody (joshimoo@hotmail.de)
 */
public class Effect {

    public static IntUnaryOperator sepiaEffect = (px) -> {

        int r = (px & Image.RED_MASK) >>> 16;
        int g = (px & Image.GREEN_MASK) >>> 8;
        int b = (px & Image.BLUE_MASK);

        int tr = (int) (0.393 * r + 0.769 * g + 0.189 * b);
        int tg = (int) (0.349 * r + 0.686 * g + 0.168 * b);
        int tb = (int) (0.272 * r + 0.534 * g + 0.131 * b);

        tr = tr > 255 ? 255 : tr < 0 ? 0 : tr;
        tg = tg > 255 ? 255 : tg < 0 ? 0 : tg;
        tb = tb > 255 ? 255 : tb < 0 ? 0 : tb;

        return (px & Image.ALPHA_MASK) | tr << 16 | tg << 8 | tb;
    };


    public static IntBinaryOperator colorEffect = (px, mask) -> px & mask;
    public static IntUnaryOperator redEffect = (px) -> colorEffect.applyAsInt(px, Image.ALPHA_MASK | Image.RED_MASK);
    public static IntUnaryOperator greenEffect = (px) -> colorEffect.applyAsInt(px, Image.ALPHA_MASK | Image.GREEN_MASK);
    public static IntUnaryOperator blueEffect = (px) -> colorEffect.applyAsInt(px, Image.ALPHA_MASK | Image.BLUE_MASK);

    /**
     * applys a passed operator to each pixel of this image
     * @return the modified image
     */
    public static Image apply(Image img, IntUnaryOperator f) {
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                img.setRGB(x, y, f.applyAsInt(img.getRGB(x,y)));
            }
        }

        return img;
    }
}

package io.github.adahra.mrkrypto.data.utils;

import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;

/**
 * Created on 12/13/17.
 */

public class ImageUtils {

    public static Bitmap setHighlightImage(Bitmap bitmapSource) {
        Bitmap bitmapOut = Bitmap.createBitmap(bitmapSource.getWidth() + 96,
                bitmapSource.getHeight() + 96, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapOut);
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        Paint paintBlur = new Paint();
        paintBlur.setMaskFilter(new BlurMaskFilter(15, BlurMaskFilter.Blur.NORMAL));
        int[] offsetXY = new int[2];
        Bitmap bitmapAlpha = bitmapSource.extractAlpha(paintBlur, offsetXY);
        Paint paintAlphaColor = new Paint();
        paintAlphaColor.setColor(0xFFFFFFFF);
        canvas.drawBitmap(bitmapAlpha, offsetXY[0], offsetXY[1], paintAlphaColor);
        bitmapAlpha.recycle();
        canvas.drawBitmap(bitmapSource, 0, 0, null);
        return bitmapOut;
    }

    public static Bitmap setInvert(Bitmap bitmapSource) {
        Bitmap bitmapOut = Bitmap.createBitmap(bitmapSource.getWidth(), bitmapSource.getHeight()
                , bitmapSource.getConfig());
        int pixelColor;
        int height = bitmapSource.getHeight();
        int width = bitmapSource.getWidth();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixelColor = bitmapSource.getPixel(x, y);
                int alpha = Color.alpha(pixelColor);
                int red = 255 - Color.red(pixelColor);
                int green = 255 - Color.green(pixelColor);
                int blue = 255 - Color.blue(pixelColor);
                bitmapOut.setPixel(x, y, Color.argb(alpha, red, green, blue));
            }
        }

        return bitmapOut;
    }

    public static Bitmap setGreyscale(Bitmap bitmapSource) {
        final double GREYSCALE_RED = 0.299;
        final double GREYSCALE_GREEN = 0.587;
        final double GREYSCALE_BLUE = 0.114;
        Bitmap bitmapOut = Bitmap.createBitmap(bitmapSource.getWidth(), bitmapSource.getHeight(),
                bitmapSource.getConfig());
        int alpha;
        int red;
        int green;
        int blue;
        int pixel;
        int width = bitmapSource.getWidth();
        int height = bitmapSource.getHeight();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixel = bitmapSource.getPixel(x, y);
                alpha = Color.alpha(pixel);
                red = Color.red(pixel);
                green = Color.green(pixel);
                blue = Color.blue(pixel);
                red = (int) (GREYSCALE_RED * red + GREYSCALE_GREEN * green + GREYSCALE_BLUE + blue);
                green = (int) (GREYSCALE_RED * red + GREYSCALE_GREEN * green + GREYSCALE_BLUE + blue);
                blue = (int) (GREYSCALE_RED * red + GREYSCALE_GREEN * green + GREYSCALE_BLUE + blue);
                bitmapOut.setPixel(x, y, Color.argb(alpha, red, green, blue));
            }
        }

        return bitmapOut;
    }

    public static Bitmap setGamma(Bitmap bitmapSource, double red, double green, double blue) {
        Bitmap bitmapOut = Bitmap.createBitmap(bitmapSource.getWidth(), bitmapSource.getHeight(),
                bitmapSource.getConfig());
        int width = bitmapSource.getWidth();
        int height = bitmapSource.getHeight();
        int alpha;
        int iRed;
        int iGreen;
        int iBlue;
        int pixel;
        final int MAX_SIZE = 256;
        final double MAX_VALUE_DOUBLE = 255.0;
        final int MAX_VALUE_INT = 255;
        final double REVERSE = 1.6;
        int[] gammaRed = new int[MAX_SIZE];
        int[] gammaGreen = new int[MAX_SIZE];
        int[] gammaBlue = new int[MAX_SIZE];
        for (int i = 0; i < MAX_SIZE; i++) {
            gammaRed[i] = (int) Math.min(MAX_VALUE_INT, (int) ((MAX_VALUE_DOUBLE *
                    Math.pow(i / MAX_VALUE_DOUBLE, REVERSE / red)) + 0.5));
            gammaGreen[i] = (int) Math.min(MAX_VALUE_INT, (int) ((MAX_VALUE_DOUBLE *
                    Math.pow(i / MAX_VALUE_DOUBLE, REVERSE / green)) + 0.5));
            gammaBlue[i] = (int) Math.min(MAX_VALUE_INT, (int) ((MAX_VALUE_DOUBLE *
                    Math.pow(i / MAX_VALUE_DOUBLE, REVERSE / blue)) + 0.5));
        }

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixel = bitmapSource.getPixel(x, y);
                alpha = Color.alpha(pixel);
                iRed = gammaRed[Color.red(pixel)];
                iGreen = gammaGreen[Color.green(pixel)];
                iBlue = gammaBlue[Color.blue(pixel)];
                bitmapOut.setPixel(x, y, Color.argb(alpha, iRed, iGreen, iBlue));
            }
        }

        return bitmapOut;
    }

    public static Bitmap setColorFilter(Bitmap bitmapSource, double red, double green, double blue) {
        int width = bitmapSource.getWidth();
        int height = bitmapSource.getHeight();
        Bitmap bitmapOut = Bitmap.createBitmap(width, height, bitmapSource.getConfig());
        int alpha;
        int iRed;
        int iGreen;
        int iBlue;
        int pixel;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixel = bitmapSource.getPixel(x, y);
                alpha = Color.alpha(pixel);
                iRed = (int) (Color.red(pixel) * red);
                iGreen = (int) (Color.green(pixel) * green);
                iBlue = (int) (Color.blue(pixel) * blue);
                bitmapOut.setPixel(x, y, Color.argb(alpha, iRed, iGreen, iBlue));
            }
        }

        return bitmapOut;
    }


}

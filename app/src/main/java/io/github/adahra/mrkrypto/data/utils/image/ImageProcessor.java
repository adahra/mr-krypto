package io.github.adahra.mrkrypto.data.utils.image;

import android.graphics.Bitmap;

/**
 * Created on 12/13/17.
 */

public class ImageProcessor {
    public static final double PI = 3.14159d;
    public static final double FULL_CIRCLE_DEGREE = 360d;
    public static final double HALF_CIRCLE_DEGREE = 180d;
    public static final double RANGE = 256d;
    private Bitmap bitmap;
    private boolean error = false;

    public ImageProcessor(final Bitmap bitmap) {
        this.bitmap = bitmap.copy(bitmap.getConfig(), bitmap.isMutable());
        if (this.bitmap == null) {
            error = true;
        }
    }

    public Bitmap getBitmap() {
        if (this.bitmap == null) {
            return null;
        }

        return bitmap.copy(bitmap.getConfig(), bitmap.isMutable());
    }

    public void setBitmap(final Bitmap bitmap) {
        this.bitmap = bitmap.copy(bitmap.getConfig(), bitmap.isMutable());
        if (this.bitmap == null) {
            error = true;
        } else {
            error = false;
        }
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public void free() {
        if (this.bitmap != null && !this.bitmap.isRecycled()) {
            this.bitmap.recycle();
            this.bitmap = null;
        }
    }

    public Bitmap replaceColor(int fromColor, int targetColor) {
        if (this.bitmap == null) {
            return null;
        }

        int width = this.bitmap.getWidth();
        int height = this.bitmap.getHeight();
        int[] pixels = new int[width * height];
        this.bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        for (int x = 0; x < pixels.length; x++) {
            if (pixels[x] == fromColor) {
                pixels[x] = targetColor;
            } else {
                pixels[x] = pixels[x];
            }
        }

        Bitmap bitmapNew = Bitmap.createBitmap(width, height, this.bitmap.getConfig());
        bitmapNew.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmapNew;
    }

    public Bitmap tintImage(Bitmap bitmapSource, int degree) {
        int width = bitmapSource.getWidth();
        int height = bitmapSource.getHeight();

        int[] pixels = new int[width * height];
        bitmapSource.getPixels(pixels, 0, width, 0, 0, width, height);

        int redYellow;
        int greenYellow;
        int blueYellow;
        int redYellowYellow;
        int greenYellowYellow;
        int blueYellowYellow;
        int red;
        int green;
        int blue;
        int yellow;
        double angle = (PI * (double) degree) / HALF_CIRCLE_DEGREE;

        int s = (int) (RANGE * Math.sin(angle));
        int c = (int) (RANGE * Math.cos(angle));

        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++) {
                int index = y * width + x;
                int r = (pixels[index] >> 16) & 0xff;
                int g = (pixels[index] >> 8) & 0xff;
                int b = pixels[index] & 0xff;
                redYellow = (70 * r - 59 * g - 11 * b) / 100;
                greenYellow = (-30 * r + 41 * g - 11 * b) / 100;
                blueYellow = (-30 * r - 59 * g + 89 * b) / 100;
                yellow = (30 * r + 59 * g + 11 * b) / 100;
                redYellowYellow = (s * blueYellow + c * redYellow) / 256;
                blueYellowYellow = (c * blueYellow - s * redYellow) / 256;
                greenYellowYellow = (-51 * redYellowYellow - 19 * blueYellowYellow) / 100;
                red = yellow + redYellowYellow;
                red = (red < 0) ? 0 : ((red > 255) ? 255 : red);
                green = yellow + greenYellowYellow;
                green = (green < 0) ? 0 : ((green > 255) ? 255 : green);
                blue = yellow + blueYellowYellow;
                blue = (blue < 0) ? 0 : ((blue > 255) ? 255 : blue);
                pixels[index] = 0xff000000 | (red << 16) | (green << 8) | blue;
            }

        Bitmap bitmapOut = Bitmap.createBitmap(width, height, bitmapSource.getConfig());
        bitmapOut.setPixels(pixels, 0, width, 0, 0, width, height);

        pixels = null;

        return bitmapOut;
    }
}

package io.github.adahra.mrkrypto.data.utils.image;

import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.Random;

/**
 * Created on 12/13/17.
 */

public class ImageUtils {
    public static final int BOOST_RED_COLOR_TYPE = 1;
    public static final int BOOST_GREEN_COLOR_TYPE = 2;
    public static final int BOOST_BLUE_COLOR_TYPE = 3;
    public static final int FLIP_VERTICAL = 1;
    public static final int FLIP_HORIZONTAL = 2;
    public static final int COLOR_MIN = 0x00;
    public static final int COLOR_MAX = 0xFF;

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

    public static Bitmap setColorFilter(Bitmap bitmapSource, double red, double green,
                                        double blue) {
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

    public static Bitmap setSepiaToningEffect(Bitmap bitmapSource, int depth, double red,
                                              double green, double blue) {
        int width = bitmapSource.getWidth();
        int height = bitmapSource.getHeight();
        Bitmap bitmapOut = Bitmap.createBitmap(width, height, bitmapSource.getConfig());
        final double GREYSCALE_RED = 0.3;
        final double GREYSCALE_GREEN = 0.59;
        final double GREYSCALE_BLUE = 0.11;
        int alpha;
        int iRed;
        int iGreen;
        int iBlue;
        int pixel;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixel = bitmapSource.getPixel(x, y);
                alpha = Color.alpha(pixel);
                iRed = Color.red(pixel);
                iGreen = Color.green(pixel);
                iBlue = Color.blue(pixel);
                iRed = (int) (GREYSCALE_RED * iRed + GREYSCALE_GREEN * iGreen + GREYSCALE_BLUE
                        * iBlue);
                iGreen = (int) (GREYSCALE_RED * iRed + GREYSCALE_GREEN * iGreen + GREYSCALE_BLUE
                        * iBlue);
                iBlue = (int) (GREYSCALE_RED * iRed + GREYSCALE_GREEN * iGreen + GREYSCALE_BLUE
                        * iBlue);
                iRed += (depth * red);
                if (iRed > 255) {
                    iRed = 255;
                }

                iGreen += (depth * green);
                if (iGreen > 255) {
                    iGreen = 255;
                }

                iBlue += (depth * blue);
                if (iBlue > 255) {
                    iBlue = 255;
                }

                bitmapOut.setPixel(x, y, Color.argb(alpha, iRed, iGreen, iBlue));
            }
        }

        return bitmapOut;
    }

    public static Bitmap setDecreaseColorDepth(Bitmap bitmapSource, int bitOffset) {
        int width = bitmapSource.getWidth();
        int height = bitmapSource.getHeight();
        Bitmap bitmapOut = Bitmap.createBitmap(width, height, bitmapSource.getConfig());
        int alpha;
        int red;
        int green;
        int blue;
        int pixel;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixel = bitmapSource.getPixel(x, y);
                alpha = Color.alpha(pixel);
                red = Color.red(pixel);
                green = Color.green(pixel);
                blue = Color.blue(pixel);
                red = ((red + (bitOffset / 2)) - ((red + (bitOffset / 2)) % bitOffset) - 1);
                if (red < 0) {
                    red = 0;
                }

                green = ((green + (bitOffset / 2)) - ((green + (bitOffset / 2)) % bitOffset) - 1);
                if (green < 0) {
                    green = 0;
                }

                blue = ((blue + (bitOffset / 2)) - ((blue + (bitOffset / 2)) % bitOffset) - 1);
                if (blue < 0) {
                    blue = 0;
                }

                bitmapOut.setPixel(x, y, Color.argb(alpha, red, green, blue));
            }
        }

        return bitmapOut;
    }

    public static Bitmap setContrast(Bitmap bitmapSource, double value) {
        int width = bitmapSource.getWidth();
        int height = bitmapSource.getHeight();
        Bitmap bitmapOut = Bitmap.createBitmap(width, height, bitmapSource.getConfig());
        int alpha;
        int red;
        int green;
        int blue;
        int pixel;
        double contrast = Math.pow((100 + value) / 100, 2);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixel = bitmapSource.getPixel(x, y);
                alpha = Color.alpha(pixel);
                red = Color.red(pixel);
                red = (int) (((((red / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if (red < 0) {
                    red = 0;
                } else if (red > 255) {
                    red = 255;
                }

                green = Color.green(pixel);
                green = (int) (((((green / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if (green < 0) {
                    green = 0;
                } else if (green > 255) {
                    green = 255;
                }

                blue = Color.blue(pixel);
                blue = (int) (((((blue / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if (blue < 0) {
                    blue = 0;
                } else if (blue > 255) {
                    blue = 255;
                }

                bitmapOut.setPixel(x, y, Color.argb(alpha, red, green, blue));
            }
        }

        return bitmapOut;
    }

    public static Bitmap setRotation(Bitmap bitmapSource, float degree) {
        int width = bitmapSource.getWidth();
        int height = bitmapSource.getHeight();
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmapSource, 0, 0, width, height, matrix, true);
    }

    public static Bitmap setBrightness(Bitmap bitmapSource, int value) {
        int width = bitmapSource.getWidth();
        int height = bitmapSource.getHeight();
        Bitmap bitmapOut = Bitmap.createBitmap(width, height, bitmapSource.getConfig());
        int alpha;
        int red;
        int green;
        int blue;
        int pixel;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixel = bitmapSource.getPixel(x, y);
                alpha = Color.alpha(pixel);
                red = Color.red(pixel);
                green = Color.green(pixel);
                blue = Color.blue(pixel);
                red += value;
                if (red > 255) {
                    red = 255;
                } else if (red < 0) {
                    red = 0;
                }

                green += value;
                if (green > 255) {
                    green = 255;
                } else if (green < 0) {
                    green = 0;
                }

                blue += value;
                if (blue > 255) {
                    blue = 255;
                } else if (blue < 0) {
                    blue = 0;
                }

                bitmapOut.setPixel(x, y, Color.argb(alpha, red, green, blue));
            }
        }

        return bitmapOut;
    }

    public static Bitmap setGaussianBlur(Bitmap bitmapSource) {
        double[][] gaussianBlurConfig = new double[][]{
                {1, 2, 1},
                {2, 4, 2},
                {1, 2, 1}
        };
        ConvolutionMatrix convolutionMatrix = new ConvolutionMatrix(3);
        convolutionMatrix.applyConfig(gaussianBlurConfig);
        convolutionMatrix.setFactor(16);
        convolutionMatrix.setOffset(0);
        return ConvolutionMatrix.computeConvolution3x3(bitmapSource, convolutionMatrix);
    }

    public static Bitmap setSharpenEffect(Bitmap bitmapSource, double weight) {
        double[][] sharpenConfig = new double[][]{
                {0, -2, 0},
                {-2, weight, -2},
                {0, -2, 0}
        };

        ConvolutionMatrix convolutionMatrix = new ConvolutionMatrix(3);
        convolutionMatrix.applyConfig(sharpenConfig);
        convolutionMatrix.setFactor(weight - 8);
        return ConvolutionMatrix.computeConvolution3x3(bitmapSource, convolutionMatrix);
    }

    public static Bitmap setMeanRemoval(Bitmap bitmapSource) {
        double[][] meanRemovalConfig = new double[][]{
                {-1, -1, -1},
                {-1, 9, -1},
                {-1, -1, -1}
        };

        ConvolutionMatrix convolutionMatrix = new ConvolutionMatrix(3);
        convolutionMatrix.applyConfig(meanRemovalConfig);
        convolutionMatrix.setFactor(1);
        convolutionMatrix.setOffset(0);
        return ConvolutionMatrix.computeConvolution3x3(bitmapSource, convolutionMatrix);
    }

    public static Bitmap setSmoothEffect(Bitmap bitmapSource, double value) {
        ConvolutionMatrix convolutionMatrix = new ConvolutionMatrix(3);
        convolutionMatrix.setAll(1);
        convolutionMatrix.matrix[1][1] = value;
        convolutionMatrix.setFactor(value + 8);
        convolutionMatrix.setOffset(1);
        return ConvolutionMatrix.computeConvolution3x3(bitmapSource, convolutionMatrix);
    }

    public static Bitmap setEmbossEffect(Bitmap bitmapSource) {
        double[][] embossConfig = new double[][]{
                {-1, 0, -1},
                {0, 4, 0},
                {-1, 0, -1}
        };

        ConvolutionMatrix convolutionMatrix = new ConvolutionMatrix(3);
        convolutionMatrix.applyConfig(embossConfig);
        convolutionMatrix.setFactor(1);
        convolutionMatrix.setOffset(127);
        return ConvolutionMatrix.computeConvolution3x3(bitmapSource, convolutionMatrix);
    }

    public static Bitmap setEngraveEffect(Bitmap bitmapSource) {
        ConvolutionMatrix convolutionMatrix = new ConvolutionMatrix(ConvolutionMatrix.SIZE);
        convolutionMatrix.setAll(0);
        convolutionMatrix.matrix[0][0] = -2;
        convolutionMatrix.matrix[1][1] = 2;
        convolutionMatrix.setFactor(1);
        convolutionMatrix.setOffset(95);
        return ConvolutionMatrix.computeConvolution3x3(bitmapSource, convolutionMatrix);
    }

    public static Bitmap setBoostColor(Bitmap bitmapSource, int type, float percent) {
        int width = bitmapSource.getWidth();
        int height = bitmapSource.getHeight();
        Bitmap bitmapOut = Bitmap.createBitmap(width, height, bitmapSource.getConfig());
        int alpha;
        int red;
        int green;
        int blue;
        int pixel;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixel = bitmapSource.getPixel(x, y);
                alpha = Color.alpha(pixel);
                red = Color.red(pixel);
                green = Color.green(pixel);
                blue = Color.blue(pixel);
                if (type == BOOST_RED_COLOR_TYPE) {
                    red = (int) (red * (1 + percent));
                    if (red > 255) {
                        red = 255;
                    }
                } else if (type == BOOST_GREEN_COLOR_TYPE) {
                    green = (int) (green * (1 + percent));
                    if (green > 255) {
                        green = 255;
                    }
                } else if (type == BOOST_BLUE_COLOR_TYPE) {
                    blue = (int) (blue * (1 + percent));
                    if (blue > 255) {
                        blue = 255;
                    }
                }

                bitmapOut.setPixel(x, y, Color.argb(alpha, red, green, blue));
            }
        }

        return bitmapOut;
    }

    public static Bitmap setRoundedCornerImage(Bitmap bitmapSource, float round) {
        int width = bitmapSource.getWidth();
        int height = bitmapSource.getHeight();
        Bitmap bitmapOutput = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapOutput);
        canvas.drawARGB(0, 0, 0, 0);
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        final Rect rect = new Rect(0, 0, width, height);
        final RectF rectF = new RectF(rect);
        canvas.drawRoundRect(rectF, round, round, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmapSource, rect, rect, paint);
        return bitmapOutput;
    }

    public static Bitmap setWatermark(Bitmap bitmapSource, String watermark, Point location,
                                      int color, int alpha, int size, boolean underline) {
        int w = bitmapSource.getWidth();
        int h = bitmapSource.getHeight();
        Bitmap result = Bitmap.createBitmap(w, h, bitmapSource.getConfig());

        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(bitmapSource, 0, 0, null);

        Paint paint = new Paint();
        paint.setColor(color);
        paint.setAlpha(alpha);
        paint.setTextSize(size);
        paint.setAntiAlias(true);
        paint.setUnderlineText(underline);
        canvas.drawText(watermark, location.x, location.y, paint);

        return result;
    }

    public static Bitmap setFlip(Bitmap bitmapSource, int type) {
        int width = bitmapSource.getWidth();
        int height = bitmapSource.getHeight();
        Matrix matrix = new Matrix();
        if (type == FLIP_VERTICAL) {
            matrix.preScale(1.0f, -1.0f);
        } else if (type == FLIP_HORIZONTAL) {
            matrix.preScale(-1.0f, 1.0f);
        } else {
            return null;
        }

        return Bitmap.createBitmap(bitmapSource, 0, 0, width, height, matrix, true);
    }

    public static Bitmap setFleaEffect(Bitmap bitmapSource) {
        int width = bitmapSource.getWidth();
        int height = bitmapSource.getHeight();
        int[] pixels = new int[width * height];
        bitmapSource.getPixels(pixels, 0, width, 0, 0, width, height);
        Random random = new Random();
        int index = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                index = y * width + x;
                int randomColor = Color.rgb(random.nextInt(COLOR_MAX),
                        random.nextInt(COLOR_MAX), random.nextInt(COLOR_MAX));
                pixels[index] |= randomColor;
            }
        }

        Bitmap bitmapOut = Bitmap.createBitmap(width, height, bitmapSource.getConfig());
        bitmapOut.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmapOut;
    }

    public static Bitmap setBlackFilter(Bitmap bitmapSource) {
        int width = bitmapSource.getWidth();
        int height = bitmapSource.getHeight();
        int[] pixels = new int[width * height];
        bitmapSource.getPixels(pixels, 0, width, 0, 0, width, height);
        Random random = new Random();
        int red;
        int green;
        int blue;
        int index = 0;
        int threshold = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                index = y * width + x;
                red = Color.red(pixels[index]);
                green = Color.green(pixels[index]);
                blue = Color.blue(pixels[index]);
                threshold = random.nextInt(COLOR_MAX);
                if (red < threshold && green < threshold && blue < threshold) {
                    pixels[index] = Color.rgb(COLOR_MIN, COLOR_MIN, COLOR_MIN);
                }
            }
        }

        Bitmap bitmapOut = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmapOut.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmapOut;
    }

    public static Bitmap setSnowEffect(Bitmap bitmapSource) {
        int width = bitmapSource.getWidth();
        int height = bitmapSource.getHeight();
        int[] pixels = new int[width * height];
        bitmapSource.getPixels(pixels, 0, width, 0, 0, width, height);
        Random random = new Random();
        int red;
        int green;
        int blue;
        int index = 0;
        int threshold = 50;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                index = y * width + x;
                red = Color.red(pixels[index]);
                green = Color.green(pixels[index]);
                blue = Color.blue(pixels[index]);
                threshold = random.nextInt(COLOR_MAX);
                if (red > threshold && green > threshold && blue > threshold) {
                    pixels[index] = Color.rgb(COLOR_MAX, COLOR_MAX, COLOR_MAX);
                }
            }
        }

        Bitmap bitmapOut = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        bitmapOut.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmapOut;
    }

    public static Bitmap setShadingFilter(Bitmap bitmapSource, int shadingColor) {
        int width = bitmapSource.getWidth();
        int height = bitmapSource.getHeight();
        int[] pixels = new int[width * height];
        bitmapSource.getPixels(pixels, 0, width, 0, 0, width, height);
        int index = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                index = y * width + x;
                pixels[index] &= shadingColor;
            }
        }

        Bitmap bitmapOut = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmapOut.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmapOut;
    }

}

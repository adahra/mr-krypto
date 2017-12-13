package io.github.adahra.mrkrypto.data.utils.image;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.Arrays;

/**
 * Created on 12/13/17.
 */

public class ConvolutionMatrix {
    public static final int SIZE = 3;
    public double[][] matrix;
    private double factor = 1;
    private double offset = 1;

    public ConvolutionMatrix(int size) {
        matrix = new double[size][size];
    }

    public static Bitmap computeConvolution3x3(Bitmap bitmapSource, ConvolutionMatrix convolutionMatrix) {
        int width = bitmapSource.getWidth();
        int height = bitmapSource.getHeight();
        Bitmap bitmapOutput = Bitmap.createBitmap(width, height, bitmapSource.getConfig());
        int alpha;
        int red;
        int green;
        int blue;
        int sumRed;
        int sumGreen;
        int sumBlue;
        int[][] pixel = new int[SIZE][SIZE];
        for (int y = 0; y < height - 2; y++) {
            for (int x = 0; x < width - 2; x++) {
                for (int i = 0; i < SIZE; i++) {
                    for (int j = 0; j < SIZE; j++) {
                        pixel[i][j] = bitmapSource.getPixel(x + i, y + i);
                    }
                }

                alpha = Color.alpha(pixel[1][1]);
                sumRed = 0;
                sumGreen = 0;
                sumBlue = 0;
                for (int i = 0; i < SIZE; i++) {
                    for (int j = 0; j < SIZE; j++) {
                        sumRed += (Color.red(pixel[i][j]) * convolutionMatrix.matrix[i][j]);
                        sumGreen += (Color.green(pixel[i][j]) * convolutionMatrix.matrix[i][j]);
                        sumBlue += (Color.blue(pixel[i][j]) * convolutionMatrix.matrix[i][j]);
                    }
                }

                red = (int) (sumRed / convolutionMatrix.getFactor() + convolutionMatrix.getOffset());
                if (red < 0) {
                    red = 0;
                } else if (red > 255) {
                    red = 255;
                }

                green = (int) (sumGreen / convolutionMatrix.getFactor() + convolutionMatrix.getOffset());
                if (green < 0) {
                    green = 0;
                } else if (green > 255) {
                    green = 255;
                }

                blue = (int) (sumBlue / convolutionMatrix.getFactor() + convolutionMatrix.getOffset());
                if (blue < 0) {
                    blue = 0;
                } else if (blue > 255) {
                    blue = 255;
                }

                bitmapOutput.setPixel(x + 1, y + 1, Color.argb(alpha, red, green, blue));
            }
        }

        return bitmapOutput;
    }

    public double getFactor() {
        return factor;
    }

    public void setFactor(double factor) {
        this.factor = factor;
    }

    public double getOffset() {
        return offset;
    }

    public void setOffset(double offset) {
        this.offset = offset;
    }

    public void setAll(double value) {
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                matrix[x][y] = value;
            }
        }
    }

    public void applyConfig(double[][] config) {
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                matrix[x][y] = config[x][y];
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConvolutionMatrix)) return false;

        ConvolutionMatrix that = (ConvolutionMatrix) o;

        if (Double.compare(that.getFactor(), getFactor()) != 0) return false;
        if (Double.compare(that.getOffset(), getOffset()) != 0) return false;
        return Arrays.deepEquals(matrix, that.matrix);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = Arrays.deepHashCode(matrix);
        temp = Double.doubleToLongBits(getFactor());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getOffset());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "ConvolutionMatrix{" +
                "matrix=" + Arrays.toString(matrix) +
                ", factor=" + factor +
                ", offset=" + offset +
                '}';
    }
}

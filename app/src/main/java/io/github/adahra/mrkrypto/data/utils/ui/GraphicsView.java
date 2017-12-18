package io.github.adahra.mrkrypto.data.utils.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created on 12/18/17.
 */

public class GraphicsView extends View {
    private static final String MY_TEXT = "Draw Text on Curve";
    private Path pathArch;
    private Paint paintText;
    private String stringText;

    public GraphicsView(Context context) {
        super(context);
    }

    public GraphicsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GraphicsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public GraphicsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * Implement this to do your drawing.
     *
     * @param canvas the canvas on which the background will be drawn
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (stringText != null) {
            canvas.drawTextOnPath(stringText, pathArch, 0, 20, paintText);
        } else {
            canvas.drawTextOnPath(MY_TEXT, pathArch, 0, 20, paintText);
        }

        invalidate();
    }

    private void initialize() {
        pathArch = new Path();
        RectF rectFOval = new RectF(50, 100, 200, 250);
        pathArch.addArc(rectFOval, -180, 200);
        paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintText.setStyle(Paint.Style.FILL_AND_STROKE);
        paintText.setColor(Color.WHITE);
        paintText.setTextSize(20f);
    }

    public String getStringText() {
        return stringText;
    }

    public void setStringText(String stringText) {
        this.stringText = stringText;
    }
}

package myutils.gui;

import myutils.maths.Vector2D;

import java.awt.*;

@SuppressWarnings("UnusedDeclaration")
public class Pixels {
    private final int[] pixels;
    @SuppressWarnings("FieldCanBeLocal")
    private final float DEFAULT_ZOOM_RATE = 1.05f;
    private final IterativeCanvas iterativeCanvas;
    private float scale;
    private float xOffset;
    private float yOffset;

    Pixels(int[] p, IterativeCanvas iterativeCanvas) {
        this.pixels = p;
        this.iterativeCanvas = iterativeCanvas;
        resetOffsetScale();
    }

    @SuppressWarnings("SameParameterValue")
    private static boolean inside(int x, int y, int xMin, int yMin, int xMax, int yMax) {
        return (x >= xMin) && (x <= xMax) && (y >= yMin) && (y <= yMax);
    }

    public void add(Vector2D l, int color) {
        int ix = Math.round((l.x + -xOffset) * scale + iterativeCanvas.cx);
        int iy = Math.round((l.y - yOffset) * scale + iterativeCanvas.cy);
        if (inside(ix, iy, 0, 0, iterativeCanvas.WIDTH - 1, iterativeCanvas.HEIGHT - 1))
            pixels[ix + iy * iterativeCanvas.WIDTH] = color;
    }

    public void add(float x, float y, int color) {
        int ix = Math.round((x + -xOffset) * scale + iterativeCanvas.cx);
        int iy = Math.round((y - yOffset) * scale + iterativeCanvas.cy);
        if (inside(ix, iy, 0, 0, iterativeCanvas.WIDTH - 1, iterativeCanvas.HEIGHT - 1))
            pixels[ix + iy * iterativeCanvas.WIDTH] = color;
    }

    public void clear(int c) {
        for (int i = 0; i < pixels.length; i++)
            pixels[i] = c;
    }

    public void clear(Color backGroundColor) {
        clear(backGroundColor.getRGB());
    }

    public void scrollX(int numTimesPressed) {
        xOffset += numTimesPressed / scale * Math.PI;
    }

    public void scrollY(int numTimesPressed) {
        yOffset += numTimesPressed / scale * Math.PI;
    }

    public void zoom(int r) {
        scale *= Math.pow(DEFAULT_ZOOM_RATE, r);
    }

    void resetOffsetScale() {
        xOffset = 0;
        yOffset = 0;
        scale = 1;
    }

    public void setOffset(Vector2D locationRelative) {
        xOffset = locationRelative.x;// * Math.PI;// scale;//Math.PI;
        yOffset = locationRelative.y;// * Math.PI;// scale;//Math.PI;
    }

    public void convertOnScreenScaled(Vector2D v, int x, int y) {
        v.x = x / scale - iterativeCanvas.cx;
        v.y = y / scale - iterativeCanvas.cy;
    }

    public void convertRelativeScaled(Vector2D v, int x, int y) {
        v.x = (x - iterativeCanvas.cx) / scale + xOffset;
        v.y = (y - iterativeCanvas.cy) / scale + yOffset;
    }

    public void convertOnScreenAbsolute(Vector2D v, int x, int y) {
        v.x = x - iterativeCanvas.cx;
        v.y = y - iterativeCanvas.cy;
    }

    public void convertRelativeAbsolute(Vector2D v, int x, int y) {
        v.x = x / iterativeCanvas.SCALE - iterativeCanvas.cx + xOffset;
        v.y = y / iterativeCanvas.SCALE - iterativeCanvas.cy + yOffset;
    }


}

package me.torhash.orfeus.util;


import java.awt.*;

public class ColorUtils {
    private double r, g, b;

    private double max = 255;
    private double min = 50;
    private double byNum = 0.5;

    public ColorUtils(double byNum) {
        r = max;
        g = min;
        b = min;
        this.byNum = byNum;
    }

    public void onUpdate() {
        if(r == max && g < max) {
            g += byNum;
        }
        if(g == max && b < max) {
            b += byNum;
        }
        if(b == max && r < max) {
            r += byNum;
        }
        if(r > min && g == max) {
            r -= byNum;
        }
        if(g > min && b == max) {
            g -= byNum;
        }
        if(b > min && r == max) {
            b -= byNum;
        }
    }

    public int getColor() {
        Color color = new Color((int)Math.floor(r), (int)Math.floor(g), (int)Math.floor(b));
        return color.getRGB();
    }

    public static int rainbow(int delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / 360.0f), 0.8f, 0.7f).getRGB();
    }
}

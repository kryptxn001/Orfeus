package me.torhash.orfeus.ui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class UIElement {
    protected HashMap<Character,Integer> map = new HashMap<>();
    protected MinecraftClient mc = MinecraftClient.getInstance();
    protected TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
    private String name;
    private boolean toggle;

    public UIElement(String name, boolean toggle) {
        this.name = name;
        this.toggle = toggle;

        map.put('0',0xFF000000);
        map.put('1',0xFF0000AA);
        map.put('2',0xFF00AA00);
        map.put('3',0xFF00AAAA);
        map.put('4',0xFFAA0000);
        map.put('5',0xFFAA00AA);
        map.put('6',0xFFFFAA00);
        map.put('7',0xFFAAAAAA);
        map.put('8',0xFF555555);
        map.put('9',0xFF5555FF);
        map.put('a',0xFF55FF55);
        map.put('b',0xFF55FFFF);
        map.put('c',0xFFFF5555);
        map.put('d',0xFFFF55FF);
        map.put('e',0xFFFFFF55);
        map.put('f',0xFFFFFFFF);
    }

    protected void onRender(MatrixStack matrixStack) { }

    public String getName() {
        return name;
    }

    public void drawWithColorCodes(MatrixStack matrixStack,String text, float x, float y, boolean shadow) {
        int color = 0xFFFFFFFF;

        int charnum = 0;
        int offset = 0;
        byte lastsymbol = 0;

        for(char forchar : text.toCharArray()) {
            if(forchar == '§' && charnum < text.length() - 2) {
                lastsymbol = 1;
                color = map.get(text.toCharArray()[charnum+1]);
                charnum++;
                continue;
            } if(lastsymbol == 1) {
                lastsymbol = 0;
                charnum++;
                continue;
            }

            if(shadow) {
                textRenderer.drawWithShadow(matrixStack,Character.toString(forchar),x+offset,y,color);
            } else {
                textRenderer.draw(matrixStack,Character.toString(forchar),x+offset,y,color);
            }

            offset = offset + textRenderer.getWidth(Character.toString(forchar));
            charnum++;
        }
    }

    public void drawRect(int x1, int y1, int x2, int y2, int hexcolor) {
        String scolor = Integer.toString(hexcolor).replace("0x", "#");
        Color color = Color.decode(scolor);

        GL11.glColor4f(color.getRed(),color.getGreen(),color.getBlue(),color.getAlpha());

        GL11.glBegin(GL11.GL_QUADS);
        {
            GL11.glVertex2i(x1, y1);
            GL11.glVertex2i(x2, y1);
            GL11.glVertex2i(x2, y2);
            GL11.glVertex2i(x1, y2);
        }
        GL11.glEnd();
    }

    public boolean isToggled() {
        return toggle;
    }

    public void setToggled(boolean toggled) {
        this.toggle = toggled;
    }

    public void toggle() {
        this.toggle = !toggle;
    }
}

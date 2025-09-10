package me.torhash.orfeus.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class RenderUtils {
    public static void offsetRender() {
        Camera camera = MinecraftClient.getInstance().gameRenderer.getCamera();
        Vec3d camPos = camera.getPos();
        GL11.glRotated(MathHelper.wrapDegrees(camera.getPitch()), 1, 0, 0);
        GL11.glRotated(MathHelper.wrapDegrees(camera.getYaw() + 180.0), 0, 1, 0);
        GL11.glTranslated(-camPos.x, -camPos.y, -camPos.z);
    }

    public static void glSetupForESP() {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glColor4f(0, 1, 0, 1);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glLineWidth(1);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LIGHTING);
    }

    public static void glResetFromESP() {
        GL11.glColor4f(1, 1, 1, 1);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glLineWidth(2);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
    }

    public static void drawOutlineBoxBlock(BlockPos blockPos, float r, float g, float b, float a) {
        drawOutlineBox(new Box(blockPos), r, g, b, a);
    }

    public static Vec3d getCameraPos()
    {
        return BlockEntityRenderDispatcher.INSTANCE.camera.getPos();
    }

    public static void drawOutlineBox(Box box, float r, float g, float b, float a) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        // Outline
        buffer.begin(3, VertexFormats.POSITION_COLOR);
        buffer.vertex(box.minX, box.minY, box.minZ).color(r, g, b, a).next();
        buffer.vertex(box.minX, box.minY, box.maxZ).color(r, g, b, a).next();
        buffer.vertex(box.maxX, box.minY, box.maxZ).color(r, g, b, a).next();
        buffer.vertex(box.maxX, box.minY, box.minZ).color(r, g, b, a).next();
        buffer.vertex(box.minX, box.minY, box.minZ).color(r, g, b, a).next();
        buffer.vertex(box.minX, box.maxY, box.minZ).color(r, g, b, a).next();
        buffer.vertex(box.maxX, box.maxY, box.minZ).color(r, g, b, a).next();
        buffer.vertex(box.maxX, box.maxY, box.maxZ).color(r, g, b, a).next();
        buffer.vertex(box.minX, box.maxY, box.maxZ).color(r, g, b, a).next();
        buffer.vertex(box.minX, box.maxY, box.minZ).color(r, g, b, a).next();
        buffer.vertex(box.minX, box.minY, box.maxZ).color(r, g, b, 0f).next();
        buffer.vertex(box.minX, box.maxY, box.maxZ).color(r, g, b, a).next();
        buffer.vertex(box.maxX, box.minY, box.maxZ).color(r, g, b, 0f).next();
        buffer.vertex(box.maxX, box.maxY, box.maxZ).color(r, g, b, a).next();
        buffer.vertex(box.maxX, box.minY, box.minZ).color(r, g, b, 0f).next();
        buffer.vertex(box.maxX, box.maxY, box.minZ).color(r, g, b, a).next();
        tessellator.draw();
    }

    public static void drawFilledBoxBlock(BlockPos blockPos, float r, float g, float b, float a) {
        drawFilledBox(new Box(blockPos), r, g, b, a);
    }


    public static void drawFilledBox(Box box, float r, float g, float b, float a) {
        // Fill
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(5, VertexFormats.POSITION_COLOR);
        WorldRenderer.drawBox(buffer,
                box.minX, box.minY, box.minZ,
                box.maxX, box.maxY, box.maxZ, r, g, b, a / 2f);
        tessellator.draw();

        // Outline
        buffer.begin(3, VertexFormats.POSITION_COLOR);
        buffer.vertex(box.minX, box.minY, box.minZ).color(r, g, b, a).next();
        buffer.vertex(box.minX, box.minY, box.maxZ).color(r, g, b, a).next();
        buffer.vertex(box.maxX, box.minY, box.maxZ).color(r, g, b, a).next();
        buffer.vertex(box.maxX, box.minY, box.minZ).color(r, g, b, a).next();
        buffer.vertex(box.minX, box.minY, box.minZ).color(r, g, b, a).next();
        buffer.vertex(box.minX, box.maxY, box.minZ).color(r, g, b, a).next();
        buffer.vertex(box.maxX, box.maxY, box.minZ).color(r, g, b, a).next();
        buffer.vertex(box.maxX, box.maxY, box.maxZ).color(r, g, b, a).next();
        buffer.vertex(box.minX, box.maxY, box.maxZ).color(r, g, b, a).next();
        buffer.vertex(box.minX, box.maxY, box.minZ).color(r, g, b, a).next();
        buffer.vertex(box.minX, box.minY, box.maxZ).color(r, g, b, 0f).next();
        buffer.vertex(box.minX, box.maxY, box.maxZ).color(r, g, b, a).next();
        buffer.vertex(box.maxX, box.minY, box.maxZ).color(r, g, b, 0f).next();
        buffer.vertex(box.maxX, box.maxY, box.maxZ).color(r, g, b, a).next();
        buffer.vertex(box.maxX, box.minY, box.minZ).color(r, g, b, 0f).next();
        buffer.vertex(box.maxX, box.maxY, box.minZ).color(r, g, b, a).next();
        tessellator.draw();
    }
}

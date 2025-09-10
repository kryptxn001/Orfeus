package me.torhash.orfeus.module.modules;

import com.mojang.blaze3d.platform.GlStateManager;
import me.torhash.orfeus.event.EventTarget;
import me.torhash.orfeus.event.events.EventRender3D;
import me.torhash.orfeus.module.Category;
import me.torhash.orfeus.module.Module;
import me.torhash.orfeus.util.RenderUtils;
import me.torhash.orfeus.valuesystem.ValueManager;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.Monster;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

public class ESP extends Module {
    public ESP() {
        super("ESP", GLFW.GLFW_KEY_G, Category.RENDER);
    }

    @EventTarget
    public void onRender3D(EventRender3D e) {
        RenderUtils.glSetupForESP();

        GL11.glPushMatrix();

        RenderUtils.offsetRender();
        for(Entity entity : mc.world.getEntities()) {
            GL11.glPushMatrix();
            if(entity instanceof Monster && entity != mc.player) {
                if(entity.isAlive()) {
                    Entity target = (Entity) ValueManager.getValue("target","killaura").getValue();

                    if(target != null && entity == target && mc.player.distanceTo(target) < mc.interactionManager.getReachDistance() + 1) {
                        RenderUtils.drawOutlineBox(entity.getVisibilityBoundingBox(),1f,0f,0f,1f);
                        RenderUtils.drawFilledBox(entity.getVisibilityBoundingBox(),1f,0f,0f,0.2f);
                    } else {
                        RenderUtils.drawOutlineBox(entity.getVisibilityBoundingBox(),0f,1f,0f,1f);
                        RenderUtils.drawFilledBox(entity.getVisibilityBoundingBox(),0f,1f,0f,0.2f);
                    }

                }
            } else if(entity instanceof ItemEntity) {
                RenderUtils.drawOutlineBox(entity.getVisibilityBoundingBox(),1f,1f,0f,1f);
                RenderUtils.drawFilledBox(entity.getVisibilityBoundingBox(),1f,1f,0f,0.2f);
            }
            GL11.glPopMatrix();
        }
        GL11.glPopMatrix();

        RenderUtils.glResetFromESP();
    }

    @Override
    public void onEnable() { super.onEnable(); }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}

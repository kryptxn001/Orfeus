package me.torhash.orfeus.module.modules;

import me.torhash.orfeus.event.EventTarget;
import me.torhash.orfeus.event.events.EventRender3D;
import me.torhash.orfeus.module.Category;
import me.torhash.orfeus.module.Module;
import me.torhash.orfeus.util.BlockUtils;
import me.torhash.orfeus.util.RenderUtils;
import net.minecraft.block.Block;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.*;
import net.minecraft.datafixer.fix.EntityItemFrameDirectionFix;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

public class BlockESP extends Module {
    public BlockESP() {
        super("BlockESP", GLFW.GLFW_KEY_B, Category.RENDER);
    }

    @EventTarget
    public void onRender(EventRender3D event) {
        RenderUtils.glSetupForESP();
        GL11.glPushMatrix();
        RenderUtils.offsetRender();

        for(BlockEntity blockEntity : mc.world.blockEntities) {
            GL11.glPushMatrix();
            if(blockEntity instanceof ChestBlockEntity) {
                RenderUtils.drawOutlineBox(BlockUtils.getChestBox(blockEntity.getPos()), 1f,0.5f,0f,1f);
                RenderUtils.drawFilledBox(BlockUtils.getChestBox(blockEntity.getPos()),1f,0.5f,0f,0.2f);
            } else if(blockEntity instanceof EnderChestBlockEntity) {
                    RenderUtils.drawOutlineBox(BlockUtils.getChestBox(blockEntity.getPos()), 1f,0f,1f,1f);
                    RenderUtils.drawFilledBox(BlockUtils.getChestBox(blockEntity.getPos()),1f,0f,1f,0.2f);
            } else if(blockEntity instanceof TrappedChestBlockEntity) {
                    RenderUtils.drawOutlineBox(BlockUtils.getChestBox(blockEntity.getPos()), 1f,0.25f,0f,1f);
                    RenderUtils.drawFilledBox(BlockUtils.getChestBox(blockEntity.getPos()),1f,0.25f,0f,0.2f);
            } else if(blockEntity instanceof DispenserBlockEntity ||
                    blockEntity instanceof DropperBlockEntity ||
                    blockEntity instanceof FurnaceBlockEntity ||
                    blockEntity instanceof  HopperBlockEntity) {
                RenderUtils.drawOutlineBoxBlock(blockEntity.getPos(), 0.4f,0.4f,0.4f,1f);
                RenderUtils.drawFilledBoxBlock(blockEntity.getPos(),0.4f,0.4f,0.4f,0.4f);
            }
            GL11.glPopMatrix();
        }

        for(Entity entity : mc.world.getEntities()) {
            if(entity instanceof ItemFrameEntity) {
                GL11.glPushMatrix();
                RenderUtils.drawOutlineBox(entity.getBoundingBox(), 0f,1f,1f,1f);
                RenderUtils.drawFilledBox(entity.getBoundingBox(),0f,1f,1f,0.2f);
                GL11.glPopMatrix();
            }
        }

        GL11.glPopMatrix();
        RenderUtils.glResetFromESP();
    }
}

package me.torhash.orfeus.module.modules;

import me.torhash.orfeus.event.EventTarget;
import me.torhash.orfeus.event.events.EventMouseClick;
import me.torhash.orfeus.module.Category;
import me.torhash.orfeus.module.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import org.lwjgl.glfw.GLFW;

public class Criticals extends Module {
    public Criticals() {
        super("Criticals", GLFW.GLFW_KEY_C, Category.COMBAT);
    }

    @EventTarget
    public void onLeftClick(EventMouseClick event) {
        if(event.getClickType() != EventMouseClick.clickType.LEFT) {
            return;
        }

        if(mc.crosshairTarget != null && mc.crosshairTarget.getType() == HitResult.Type.ENTITY) {
            EntityHitResult hitResult = (EntityHitResult) mc.crosshairTarget;

            if(hitResult.getEntity() instanceof LivingEntity) {
                //Player is clicking living entity
                doPacketCriticals();
            }
        }
    }

    public static void doPacketCriticals() {
        MinecraftClient mc = MinecraftClient.getInstance();

        double posX = mc.player.getX();
        double posY = mc.player.getY();
        double posZ = mc.player.getZ();

        mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionOnly(posX, posY + 0.0625D, posZ, true));
        mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionOnly(posX, posY, posZ, false));
        mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionOnly(posX, posY + 1.1E-5D, posZ, false));
        mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionOnly(posX, posY, posZ, false));
    }
}

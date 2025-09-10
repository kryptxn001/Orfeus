package me.torhash.orfeus.module.modules;

import me.torhash.orfeus.event.EventTarget;
import me.torhash.orfeus.event.events.EventMotion;
import me.torhash.orfeus.module.Category;
import me.torhash.orfeus.module.Module;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import org.lwjgl.glfw.GLFW;

public class NoFall extends Module {
    public NoFall() {
        super("NoFall", GLFW.GLFW_KEY_U, Category.PLAYER);
    }

    @EventTarget
    public void onMotion(EventMotion event) {
        if(mc.player.fallDistance > 3) {
            mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket(true));
        }
    }
}

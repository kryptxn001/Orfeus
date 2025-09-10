package me.torhash.orfeus.module.modules;

import me.torhash.orfeus.event.EventTarget;
import me.torhash.orfeus.event.events.EventMotion;
import me.torhash.orfeus.module.Category;
import me.torhash.orfeus.module.Module;
import org.lwjgl.glfw.GLFW;

public class Step extends Module {
    public Step() {
        super("Step", GLFW.GLFW_KEY_END, Category.MOVEMENT);
    }

    @EventTarget
    public void onMotion(EventMotion event) {
        if(mc.player.horizontalCollision) {
            mc.player.setVelocity(mc.player.getVelocity().x,0.3,mc.player.getVelocity().z);
        }
    }
}

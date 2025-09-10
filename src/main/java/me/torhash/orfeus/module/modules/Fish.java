package me.torhash.orfeus.module.modules;

import me.torhash.orfeus.event.EventTarget;
import me.torhash.orfeus.event.events.EventMotion;
import me.torhash.orfeus.event.events.EventVelocityFluid;
import me.torhash.orfeus.module.Category;
import me.torhash.orfeus.module.Module;
import org.lwjgl.glfw.GLFW;

public class Fish extends Module {
    public Fish() {
        super("Fish", GLFW.GLFW_KEY_END, Category.MOVEMENT);
    }

    @EventTarget
    public void onLiquidPush(EventVelocityFluid event) {
        event.setCancelled(true);
    }

    @EventTarget
    public void onMotion(EventMotion event) {
        if(!mc.player.isTouchingWater() || mc.options.keySneak.isPressed() || mc.options.keyJump.isPressed()) {
            return;
        }
        mc.player.setVelocity(mc.player.getVelocity().x,0,mc.player.getVelocity().z);
    }
}

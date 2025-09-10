package me.torhash.orfeus.module.modules;

import me.torhash.orfeus.event.EventTarget;
import me.torhash.orfeus.event.events.EventMotion;
import me.torhash.orfeus.event.events.EventReceivePacket;
import me.torhash.orfeus.module.Category;
import me.torhash.orfeus.module.Module;
import me.torhash.orfeus.util.TimeHelper;
import me.torhash.orfeus.util.oVec;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

public class Velocity extends Module {
    public Velocity() {
        super("Velocity", GLFW.GLFW_KEY_V, Category.MOVEMENT);
    }

    oVec vec = new oVec();

    @EventTarget
    public void onMotion(EventReceivePacket event) {
        if(event.getPacket() instanceof EntityVelocityUpdateS2CPacket) {
            if(((EntityVelocityUpdateS2CPacket) event.getPacket()).getId() == mc.player.getEntityId()) {
                EntityVelocityUpdateS2CPacket velocitypacket = (EntityVelocityUpdateS2CPacket) event.getPacket();
                event.setCancelled(true);
            }
        }
    }
}
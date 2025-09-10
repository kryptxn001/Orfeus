package me.torhash.orfeus.event.events;

import me.torhash.orfeus.event.Event;
import net.minecraft.util.math.Vec3d;

public class EventVelocityFluid extends Event {
    private Vec3d velocity;

    public EventVelocityFluid(Vec3d vec3d) {
        this.velocity = vec3d;
    }

    public Vec3d getVelocity() {
        return velocity;
    }

    public void setVelocity(Vec3d velocity) {
        this.velocity = velocity;
    }
}

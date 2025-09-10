package me.torhash.orfeus.mixin;

import org.objectweb.asm.Opcodes;
import me.torhash.orfeus.event.events.EventVelocityFluid;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Entity.class)
public class EntityMixin {
    @Shadow
    public float yaw;

    @Shadow
    public float pitch;

    @Shadow
    public boolean onGround;

    @Redirect(at = @At(value = "INVOKE",
            target = "Lnet/minecraft/entity/Entity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V",
            opcode = Opcodes.INVOKEVIRTUAL,
            ordinal = 0),
            method = {"updateMovementInFluid(Lnet/minecraft/tag/Tag;D)Z"})
    private void setVelocityFromFluid(Entity entity, Vec3d velocity)
    {
        EventVelocityFluid event = new EventVelocityFluid(velocity);
        event.call();

        if(!event.isCancelled()) {
            entity.setVelocity(event.getVelocity());
        }
    }
}

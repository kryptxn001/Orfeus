package me.torhash.orfeus.mixin;

import com.sun.org.apache.xpath.internal.operations.Or;
import me.torhash.orfeus.Orfeus;
import me.torhash.orfeus.event.Event;
import me.torhash.orfeus.event.events.EventMotion;
import me.torhash.orfeus.event.events.EventUpdate;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin extends me.torhash.orfeus.mixin.EntityMixin {
    private double cachedX;
    private double cachedY;
    private double cachedZ;

    private float cachedPitch;
    private float cachedYaw;




    @Inject(at = @At("HEAD"), method = "tick")
    private void preTick(CallbackInfo callbackInfo) {
        new EventUpdate().call();
    }

    @Inject(at = {@At("HEAD")}, method = {"sendMovementPackets()V"})
    private void sendMovementPacketsHead(CallbackInfo callbackInfo) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;



        EventMotion em = new EventMotion(EventMotion.Type.PRE, player.getX(), player.getY(), player.getZ(), yaw, pitch, onGround);
        em.call();

        cachedX = player.getX();
        cachedY = player.getY();
        cachedZ = player.getZ();

        cachedYaw = yaw;
        cachedPitch = pitch;

        if(em.isCancelled()) {
            return;
        }

        player.setPos(em.getX(),em.getY(), em.getZ());

        yaw = em.getYaw();
        pitch = em.getPitch();

    }


    @Inject(at = {@At("TAIL")}, method = {"sendMovementPackets()V"})
    private void sendMovementPacketsTail(CallbackInfo callbackInfo) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;

        player.setPos(cachedX,cachedY, cachedZ);

        yaw = cachedYaw;
        pitch = cachedPitch;


        EventMotion em = new EventMotion(EventMotion.Type.POST, player.getX(), player.getY(), player.getZ(), yaw, pitch, onGround);
        em.call();
    }

    @Inject(at = {@At("HEAD")}, method = {"tick()V"})
    public void tick(CallbackInfo ci) {
        Orfeus.Instance.colorUtils.onUpdate();
    }
}

package me.torhash.orfeus.mixin;

import me.torhash.orfeus.event.events.EventSendPacket;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    @Shadow
    ClientConnection connection;

    @Overwrite
    public void sendPacket(Packet<?> packet) {
        EventSendPacket eventSendPacket = new EventSendPacket(packet);
        eventSendPacket.call();

        if(eventSendPacket.isCancelled()) {
            return;
        }
        this.connection.send(packet);
    }
}

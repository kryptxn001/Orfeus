package me.torhash.orfeus.mixin;

import me.torhash.orfeus.Orfeus;
import me.torhash.orfeus.event.events.EventMouseClick;
import me.torhash.orfeus.valuesystem.ValueManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Session;
import net.minecraft.item.BlockItem;
import net.minecraft.util.hit.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Inject(method = {"updateWindowTitle"}, at = @At("RETURN"))
    public void updateWindowTitle(CallbackInfo callbackInfo) {
        MinecraftClient.getInstance().getWindow().setTitle(Orfeus.Instance.name + " v" + Orfeus.Instance.version + " by " + Orfeus.Instance.author);
    }

    @Inject(at = @At("HEAD"), method = "run")
    private void run(CallbackInfo info) {
        System.out.println("Here!");
        Orfeus.Instance.start();
    }

    @Inject(at = {@At(value = "FIELD",
            target = "Lnet/minecraft/client/MinecraftClient;crosshairTarget:Lnet/minecraft/util/hit/HitResult;",
            ordinal = 0)}, method = {"doAttack()V"}, cancellable = true)
    private void onDoAttack(CallbackInfo ci)
    {
        EventMouseClick event = new EventMouseClick(EventMouseClick.clickType.LEFT);
        event.call();

        if(event.isCancelled())
            ci.cancel();
    }

    @Inject(at = {@At(value = "FIELD",
            target = "Lnet/minecraft/client/MinecraftClient;itemUseCooldown:I",
            ordinal = 0)}, method = {"doItemUse()V"}, cancellable = true)
    private void onDoItemUse(CallbackInfo ci)
    {
        EventMouseClick event = new EventMouseClick(EventMouseClick.clickType.RIGHT);
        event.call();

        if(MinecraftClient.getInstance().crosshairTarget != null && MinecraftClient.getInstance().crosshairTarget.getType() == HitResult.Type.BLOCK &&
                MinecraftClient.getInstance().player.inventory.getMainHandStack().getItem() instanceof BlockItem) {
            if((boolean) ValueManager.getValue("rotated","scaffold").getValue()==true) {
                event.setCancelled(true);
            }
        }

        if(event.isCancelled())
            ci.cancel();
    }

    @Shadow
    private Session session;

    @Overwrite
    public Session getSession() {
        if(Orfeus.Instance.orfeussession == null) {
            return this.session;
        } else {
            return Orfeus.Instance.orfeussession;
        }
    }
}

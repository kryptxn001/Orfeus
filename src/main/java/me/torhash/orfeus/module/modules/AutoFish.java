package me.torhash.orfeus.module.modules;

import me.torhash.orfeus.event.EventTarget;
import me.torhash.orfeus.event.events.EventReceivePacket;
import me.torhash.orfeus.event.events.EventUpdate;
import me.torhash.orfeus.module.Category;
import me.torhash.orfeus.module.Module;
import me.torhash.orfeus.util.TimeHelper;
import me.torhash.orfeus.util.oVec;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

import java.lang.reflect.Field;

public class AutoFish extends Module {
    public AutoFish() {
        super("AutoFish", GLFW.GLFW_KEY_END, Category.MISC);
    }

    TimeHelper timer = new TimeHelper();

    @EventTarget
    public void onMotion(EventUpdate event) {
        if(caughtFish() && timer.hasTimeReached(100)) {
            mc.interactionManager.interactItem(mc.player,mc.world,Hand.MAIN_HAND);
            mc.player.swingHand(Hand.MAIN_HAND);
            timer.setLastMS();
        } else if(mc.player.fishHook == null && timer.hasTimeReached(100)) {
            if(mc.player.inventory.getMainHandStack().getItem() instanceof FishingRodItem) {
                mc.interactionManager.interactItem(mc.player,mc.world,Hand.MAIN_HAND);
                mc.player.swingHand(Hand.MAIN_HAND);
                timer.setLastMS();
            }
        }
    }

    private boolean caughtFish() {
        if(mc.player.fishHook == null) {
            return false;
        }
        try {
            Field field = FishingBobberEntity.class.getDeclaredField("caughtFish");
            field.setAccessible(true);

            return (boolean) field.get(mc.player.fishHook);

        } catch (Exception e) {
            return false;
        }
    }
}

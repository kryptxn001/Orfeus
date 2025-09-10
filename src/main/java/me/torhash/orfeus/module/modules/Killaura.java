package me.torhash.orfeus.module.modules;

import me.torhash.orfeus.Orfeus;
import me.torhash.orfeus.event.Event;
import me.torhash.orfeus.event.EventTarget;
import me.torhash.orfeus.event.events.EventMotion;
import me.torhash.orfeus.module.Category;
import me.torhash.orfeus.module.Module;
import me.torhash.orfeus.util.RotationUtils;
import me.torhash.orfeus.valuesystem.Value;
import me.torhash.orfeus.valuesystem.ValueManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import org.lwjgl.glfw.GLFW;

public class Killaura extends Module {
    public Killaura() {
        super("Killaura", GLFW.GLFW_KEY_K, Category.COMBAT);
    }

    Value<Entity> target = new Value<>("target",this.getName(),null);
    Value<Double> reach = new Value<>("reach",this.getName(),3D);

    @EventTarget
    public void onPre(EventMotion e) {
        if(e.getType() != EventMotion.Type.PRE || (Boolean) ValueManager.getValue("rotated","scaffold").getValue() == true) {
            return;
        }

        target.setValue(getClosestTarget());
        if(target.getValue() == null || !target.getValue().isAlive()) {
            return;
        }

        float[] rotation = RotationUtils.getNeededRotations(target.getValue().getBoundingBox().getCenter());



        if(mc.player.distanceTo(target.getValue()) <= reach.getValue()) {
            e.setYaw(rotation[0]);
            e.setPitch(rotation[1]);

            if(mc.player.getAttackCooldownProgress(0) == 1 ) {
                if(Orfeus.Instance.moduleManager.getModule("Criticals").isToggle()) {
                    Criticals.doPacketCriticals();
                }
                mc.interactionManager.attackEntity(mc.player, target.getValue());
                mc.player.swingHand(Hand.MAIN_HAND);
            }
        }
    }

    @Override
    public void onDisable() {
        target.setValue(null);
        super.onDisable();
    }

    public Entity getClosestTarget() {
        Entity bestEntity = null;
        for(Entity entity : mc.world.getEntities()) {
            if((entity instanceof Monster || entity instanceof PlayerEntity || entity instanceof AnimalEntity) && entity != mc.player && entity.isAlive()) {
                if(bestEntity == null || mc.player.distanceTo(bestEntity) > mc.player.distanceTo(entity)) {
                    bestEntity = entity;
                }
            }
        }
        return bestEntity;
    }
}

package me.torhash.orfeus.module.modules;

import jdk.nashorn.internal.objects.NativeUint8Array;
import me.torhash.orfeus.Orfeus;
import me.torhash.orfeus.event.Event;
import me.torhash.orfeus.event.EventTarget;
import me.torhash.orfeus.event.events.EventMotion;
import me.torhash.orfeus.event.events.EventRender3D;
import me.torhash.orfeus.module.Category;
import me.torhash.orfeus.module.Module;
import me.torhash.orfeus.util.BlockUtils;
import me.torhash.orfeus.util.RenderUtils;
import me.torhash.orfeus.util.RotationUtils;
import me.torhash.orfeus.valuesystem.Value;
import net.minecraft.block.*;
import net.minecraft.entity.EntityPose;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.CallbackI;

public class Scaffold extends Module {
    private Value<Boolean> rotated = new Value<>("rotated","scaffold",false);
    private Value<BlockPos> blockpos = new Value<>("blockpos","scaffold",null);
    private Value<Direction> currentdirection = new Value<>("direction","scaffold",null);
    private Value<Integer> oldslot = new Value<>("rotated","scaffold",null);
    private Value<Boolean> downmode = new Value<>("downmode","scaffold",false);

    public Scaffold() {
        super("Scaffold", GLFW.GLFW_KEY_M, Category.MISC);
    }

    @Override
    public void onEnable() {
        oldslot.setValue(mc.player.inventory.selectedSlot);
        rotated.setValue(false);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        rotated.setValue(false);
        super.onDisable();
    }

    @EventTarget
    public void onMotionUpdate(EventMotion event) {
        if(event.getType() == EventMotion.Type.PRE) {
            boolean foundItem = false;

            if(mc.world.getBlockState(mc.player.getBlockPos().add(0,-1,0)).getMaterial() != Material.AIR &&
                    mc.world.getBlockState(mc.player.getBlockPos().add(0,-1,0)).getMaterial() != Material.LAVA &&
                    mc.world.getBlockState(mc.player.getBlockPos().add(0,-1,0)).getMaterial() != Material.WATER) {
                return;
            }

            for(int i = 0; i < 9; i++) {
                ItemStack itemStack = mc.player.inventory.getStack(i);
                if(itemStack == null || !(itemStack.getItem() instanceof BlockItem) || ((BlockItem) itemStack.getItem()).getBlock() instanceof FallingBlock ||
                        ((BlockItem) itemStack.getItem()).getBlock() instanceof LilyPadBlock ||
                        ((BlockItem) itemStack.getItem()).getBlock() instanceof CobwebBlock ||
                        ((BlockItem) itemStack.getItem()).getBlock() instanceof TripwireBlock ||
                        ((BlockItem) itemStack.getItem()).getBlock() instanceof FlowerBlock
                ) {
                    continue;
                } else {
                    oldslot.setValue(mc.player.inventory.selectedSlot);
                    foundItem = true;
                    mc.player.inventory.selectedSlot = i;
                    break;
                }
            }

            if(foundItem == false) {
                return;
            }



            if(downmode.getValue() == true && getClosestBlockToPlaceOn() != null) {
                BlockPos closestBlockUnder = getClosestBlockToPlaceOn().add(0,-1,0);

                BlockPos blockPos = null;
                Direction direction = null;

                if(mc.world.getBlockState(mc.player.getBlockPos().add(0,-2,0)).getMaterial() != Material.AIR &&
                        mc.world.getBlockState(mc.player.getBlockPos().add(0,-2,0)).getMaterial() != Material.WATER &&
                        mc.world.getBlockState(mc.player.getBlockPos().add(0,-2,0)).getMaterial() != Material.LAVA) {
                    System.out.println(mc.world.getBlockState(mc.player.getBlockPos().add(0,-2,0)).getMaterial());
                    return;
                }

                if(!(mc.world.getBlockState(closestBlockUnder).getBlock() instanceof AirBlock)) {
                    blockPos = closestBlockUnder;
                    direction = BlockUtils.getFacingToBlockForDown(blockPos);
                }else {
                    blockPos = getClosestBlockToPlaceOn();
                    direction = Direction.DOWN;
                }

                if(direction != null) {
                    float facing[] = RotationUtils.getDirectionToBlock(blockPos.getX(), blockPos.getY(), blockPos.getZ(), direction);

                    float yaw = facing[0];
                    float pitch = Math.min(90, facing[1] + 6);

                    event.setYaw(yaw);
                    event.setPitch(pitch);
                    blockpos.setValue(blockPos);
                    currentdirection.setValue(direction);
                    rotated.setValue(true);
                }
            }
            else if(getClosestBlockToPlaceOn() != null) {
                BlockPos blockPos = getClosestBlockToPlaceOn();
                Direction direction = BlockUtils.getFacingToBlock(blockPos);
                if(direction != null) {
                    float facing[] = RotationUtils.getDirectionToBlock(blockPos.getX(), blockPos.getY(), blockPos.getZ(), direction);

                    float yaw = facing[0];
                    float pitch = Math.min(90, facing[1] + 6);

                    event.setYaw(yaw);
                    event.setPitch(pitch);
                    blockpos.setValue(blockPos);
                    currentdirection.setValue(direction);
                    rotated.setValue(true);
                }
            }
        } else if(event.getType() == EventMotion.Type.POST) {
            if(rotated.getValue() == false) {
                return;
            }
            if(currentdirection.getValue() == null || blockpos.getValue() == null) {
                return;
            }

            BlockHitResult blockHit = new BlockHitResult(mc.player.getPos(),currentdirection.getValue(), blockpos.getValue(),false);
            if(mc.interactionManager.interactBlock(mc.player,mc.world,Hand.MAIN_HAND,blockHit).isAccepted()) {
                mc.player.swingHand(Hand.MAIN_HAND);
                mc.player.inventory.selectedSlot = oldslot.getValue();
            } else {
                mc.player.inventory.selectedSlot = oldslot.getValue();
            }
            rotated.setValue(false);
        }
    }

    @EventTarget
    public void onRender(EventRender3D event) {
        RenderUtils.glSetupForESP();
        GL11.glPushMatrix();
        RenderUtils.offsetRender();
        if(getClosestBlockToPlaceOn() != null) {
            GL11.glPushMatrix();
            RenderUtils.drawOutlineBoxBlock(getClosestBlockToPlaceOn(), 0f,0f,1f,1f);
            RenderUtils.drawFilledBoxBlock(getClosestBlockToPlaceOn() ,0f,0f,1f,0.2f);
            GL11.glPopMatrix();
        }
        GL11.glPopMatrix();
        RenderUtils.glResetFromESP();
    }

    public BlockPos getClosestBlockToPlaceOn() {
        BlockPos bestPos = null;

        for(BlockPos blockPos : BlockUtils.getBlockPosInArea(mc.player.getX()-3,mc.player.getY()-3,mc.player.getZ()-2,mc.player.getX()+4,mc.player.getY(),mc.player.getZ()+4)) {
            if(mc.world.getBlockState(blockPos).getMaterial() != Material.AIR &&
                    mc.world.getBlockState(blockPos).getMaterial() != Material.WATER &&
                    mc.world.getBlockState(blockPos).getMaterial() != Material.LAVA) {
                if(bestPos == null || BlockUtils.distanceToBlock(blockPos, mc.player.getBlockPos().add(0,-1,0)) < BlockUtils.distanceToBlock(bestPos, mc.player.getBlockPos().add(0,-1,0))) {
                    bestPos = blockPos;
                }
            }
        }
        return bestPos;
    }
}

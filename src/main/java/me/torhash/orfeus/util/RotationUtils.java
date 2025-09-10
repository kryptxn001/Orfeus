package me.torhash.orfeus.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.datafixer.fix.ItemInstanceSpawnEggFix;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.thrown.EggEntity;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.system.CallbackI;

import java.util.Vector;

public class RotationUtils {
    public static Vec3d getEyesPos()
    {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;

        return new Vec3d(player.getX(), player.getY() + player.getEyeHeight(player.getPose()), player.getZ());
    }

    public static float[] getDirectionToBlock(int var0, int var1, int var2, Direction var3) {
        double posX = var0, posY = var1, posZ = var2;

        EggEntity var4 = new EggEntity(EntityType.EGG, MinecraftClient.getInstance().world);

        posX = (double) var0 + 0.5D;
        posY = (double) var1 + 0.5D;
        posZ = (double) var2 + 0.5D;
        posX += (double) var3.getUnitVector().getX() * 0.25D;
        posY += (double) var3.getUnitVector().getY() * 0.25D;
        posZ += (double) var3.getUnitVector().getZ() * 0.25D;

        var4.setPos(posX,posY,posZ);
        return getDirectionToEntity(var4);
    }

    private static float[] getDirectionToEntity(Entity var0) {
        return new float[]{getYaw(var0) + MinecraftClient.getInstance().player.yaw, getPitch(var0) + MinecraftClient.getInstance().player.pitch};
    }

    public static float getPitch(Entity var0) {
        double var1 = var0.getX() - MinecraftClient.getInstance().player.getX();
        double var3 = var0.getZ() - MinecraftClient.getInstance().player.getZ();
        double var5 = var0.getY() - 1.6D + (double) var0.getEyeHeight(EntityPose.STANDING) - MinecraftClient.getInstance().player.getY();
        double var7 = (double) MathHelper.sqrt(var1 * var1 + var3 * var3);
        double var9 = -Math.toDegrees(Math.atan(var5 / var7));
        return -MathHelper.wrapDegrees(MinecraftClient.getInstance().player.pitch - (float) var9);
    }

    public static float getYaw(Entity var0) {
        double var1 = var0.getX() - MinecraftClient.getInstance().player.getX();
        double var3 = var0.getZ() - MinecraftClient.getInstance().player.getZ();
        double var5;

        if (var3 < 0.0D && var1 < 0.0D) {
            var5 = 90.0D + Math.toDegrees(Math.atan(var3 / var1));
        } else if (var3 < 0.0D && var1 > 0.0D) {
            var5 = -90.0D + Math.toDegrees(Math.atan(var3 / var1));
        } else {
            var5 = Math.toDegrees(-Math.atan(var1 / var3));
        }

        return MathHelper.wrapDegrees(-(MinecraftClient.getInstance().player.yaw - (float) var5));
    }

    public static float[] getNeededRotations(Vec3d vec)
    {
        Vec3d eyesPos = getEyesPos();

        double diffX = vec.x - eyesPos.x;
        double diffY = vec.y - eyesPos.y;
        double diffZ = vec.z - eyesPos.z;

        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);

        float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90F;
        float pitch = (float)-Math.toDegrees(Math.atan2(diffY, diffXZ));

        return new float[] {yaw, pitch};
    }
}

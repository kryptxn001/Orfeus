package me.torhash.orfeus.util;

import net.minecraft.util.math.Vec3d;
import org.lwjgl.system.CallbackI;

public class PositionUtils {
    public static Vec3d calculatePositionBetween(Vec3d start, Vec3d end, int procent) {
        double pointX = (start.x - end.x) / (100/procent);
        double pointY = (start.y - end.y) / (100/procent);
        double pointZ = (start.z - end.z) / (100/procent);

        return start.add(-pointX, -pointY, -pointZ);
    }
}

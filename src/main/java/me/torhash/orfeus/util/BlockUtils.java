package me.torhash.orfeus.util;

import net.minecraft.block.AirBlock;
import net.minecraft.block.Material;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.system.CallbackI;

import java.util.ArrayList;
import java.util.List;

public class BlockUtils {
    private static MinecraftClient mc = MinecraftClient.getInstance();

    public static Box getChestBox(BlockPos blockPos) {
        Box box = new Box(blockPos);
        Box chestbox = new Box(box.minX+0.0625,box.minY,box.minZ+0.0625,box.maxX-0.0625,box.maxY-0.125,box.maxZ-0.0625);

        return chestbox;
    }

    public static List<BlockPos> getBlockPosInArea(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        List<BlockPos> blocks = new ArrayList<>();
        for(double forx = minX; forx < maxX; forx = forx+1) {
            for(double fory = minY; fory < maxY; fory = fory+1) {
                for(double forz = minZ; forz < maxZ; forz = forz+1) {
                    blocks.add(new BlockPos(forx,fory,forz));
                }
            }
        }
        return blocks;
    }

    public static Direction getFacingToBlock(BlockPos blockPosition) {
        List<BlockPos> alldirections = new ArrayList<>();
        List<BlockPos> directions = new ArrayList<>();


        alldirections.add(new BlockPos(-1,0,0));
        alldirections.add(new BlockPos(1,0,0));
        alldirections.add(new BlockPos(0,1,0));
        alldirections.add(new BlockPos(0,0,-1));
        alldirections.add(new BlockPos(0,0,1));

        for(BlockPos possiblePos : alldirections) {
            if(mc.world.getBlockState(blockPosition.add(possiblePos)).getMaterial() == Material.AIR ||
                    mc.world.getBlockState(blockPosition.add(possiblePos)).getMaterial() == Material.LAVA ||
                    mc.world.getBlockState(blockPosition.add(possiblePos)).getMaterial() == Material.WATER) {
                directions.add(possiblePos);
            }
        }

        BlockPos bestdir = new BlockPos(0,-1,0);

        for(BlockPos direction : directions) {
            float fordistance = distanceToBlock(blockPosition.add(direction),mc.player.getBlockPos().add(0,-1,0));
            float bestdistance = distanceToBlock(blockPosition.add(bestdir),mc.player.getBlockPos().add(0,-1,0));

            if(fordistance < bestdistance) {
                bestdir = direction;
            }
        }

        if(bestdir.getX() == 1) {
            return Direction.EAST;
        } else if(bestdir.getX() == -1) {
            return Direction.WEST;
        } else if(bestdir.getY() == 1) {
            return Direction.UP;
        } else if(bestdir.getZ() == 1) {
            return Direction.SOUTH;
        } else if(bestdir.getZ() == -1) {
            return Direction.NORTH;
        }
        return null;
    }

    public static Direction getFacingToBlockForDown(BlockPos blockPosition) {
        List<BlockPos> directions = new ArrayList<>();


        directions.add(new BlockPos(-1,0,0));
        directions.add(new BlockPos(1,0,0));
        directions.add(new BlockPos(0,0,-1));
        directions.add(new BlockPos(0,0,1));

        BlockPos bestdir = new BlockPos(0,-1,0);

        for(BlockPos direction : directions) {
            float fordistance = distanceToBlock(blockPosition.add(direction),mc.player.getBlockPos().add(0,-1,0));
            float bestdistance = distanceToBlock(blockPosition.add(bestdir),mc.player.getBlockPos().add(0,-1,0));

            if(fordistance < bestdistance) {
                bestdir = direction;
            }
        }

        if(bestdir.getX() == 1) {
            return Direction.EAST;
        } else if(bestdir.getX() == -1) {
            return Direction.WEST;
        } else if(bestdir.getY() == 1) {
            return Direction.UP;
        } else if(bestdir.getZ() == 1) {
            return Direction.SOUTH;
        } else if(bestdir.getZ() == -1) {
            return Direction.NORTH;
        }
        return null;
    }

    
    public static float distanceToBlock(BlockPos pos1, BlockPos pos2) {
        float f = (float)(pos1.getX() - pos2.getX());
        float g = (float)(pos1.getY() - pos2.getY());
        float h = (float)(pos1.getZ() - pos2.getZ());
        return MathHelper.sqrt(f * f + g * g + h * h);
    }
}

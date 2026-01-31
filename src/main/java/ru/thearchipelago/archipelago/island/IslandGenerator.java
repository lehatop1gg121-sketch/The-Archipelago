package ru.thearchipelago.archipelago.island;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;

public class IslandGenerator {

    public static void generate(ServerLevel level, IslandPos center) {

        BlockPos base = new BlockPos(center.x(), center.y(), center.z());

        generateGround(level, base);
        generateTree(level, base);
        generateWater(level, base);
        generateRocks(level, base);
    }

    // 🌱 Основа острова
    private static void generateGround(ServerLevel level, BlockPos center) {

        for (int x = -4; x <= 4; x++) {
            for (int z = -4; z <= 4; z++) {

                int distance = Math.abs(x) + Math.abs(z);
                if (distance > 6) continue;

                int height = 1 + level.random.nextInt(2);

                for (int y = 0; y <= height; y++) {
                    BlockPos pos = center.offset(x, -y, z);

                    if (y == 0) {
                        level.setBlockAndUpdate(pos, Blocks.GRASS_BLOCK.defaultBlockState());
                    } else {
                        level.setBlockAndUpdate(pos, Blocks.DIRT.defaultBlockState());
                    }
                }
            }
        }
    }

    // 🌳 Дерево
    private static void generateTree(ServerLevel level, BlockPos center) {

        BlockPos treeBase = center.offset(2, 1, 2);

        // ствол
        for (int y = 0; y < 4; y++) {
            level.setBlockAndUpdate(treeBase.above(y),
                    Blocks.OAK_LOG.defaultBlockState());
        }

        // листья
        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                for (int y = 3; y <= 5; y++) {

                    if (Math.abs(x) + Math.abs(z) > 3) continue;

                    level.setBlockAndUpdate(
                            treeBase.offset(x, y, z),
                            Blocks.OAK_LEAVES.defaultBlockState()
                    );
                }
            }
        }
    }

    // 💧 Вода
    private static void generateWater(ServerLevel level, BlockPos center) {

        BlockPos waterPos = center.offset(-3, 1, 0);
        level.setBlockAndUpdate(waterPos,
                Blocks.WATER.defaultBlockState());
    }

    // 🪨 Камни
    private static void generateRocks(ServerLevel level, BlockPos center) {

        BlockPos rock1 = center.offset(1, 1, -2);
        BlockPos rock2 = center.offset(-2, 1, 1);

        level.setBlockAndUpdate(rock1, Blocks.COBBLESTONE.defaultBlockState());
        level.setBlockAndUpdate(rock2, Blocks.ANDESITE.defaultBlockState());
    }
}

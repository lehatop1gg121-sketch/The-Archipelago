package ru.thearchipelago.archipelago.island;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.LevelResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class IslandManager {

    // расстояние между островами
    public static final int ISLAND_DISTANCE = 2000;
    public static final int ISLAND_Y = 100;

    // файл-счётчик островов
    private static final String FILE_NAME = "archipelago_island_index.txt";

    /**
     * Выдаёт координаты для нового острова
     */
    public static IslandPos getNextIslandPos(ServerLevel level) {
        int index = loadIndex(level);

        int x = index * ISLAND_DISTANCE;
        int y = ISLAND_Y;
        int z = 0;

        saveIndex(level, index + 1);

        return new IslandPos(x, y, z);
    }

    // ===== Работа с файлом =====

    private static int loadIndex(ServerLevel level) {
        Path path = getFilePath(level);

        if (!Files.exists(path)) {
            return 0;
        }

        try {
            String text = Files.readString(path);
            return Integer.parseInt(text.trim());
        } catch (Exception e) {
            return 0;
        }
    }

    private static void saveIndex(ServerLevel level, int index) {
        Path path = getFilePath(level);

        try {
            Files.writeString(path, String.valueOf(index));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Path getFilePath(ServerLevel level) {
        return level.getServer()
                .getWorldPath(LevelResource.ROOT)
                .resolve(FILE_NAME);
    }
}

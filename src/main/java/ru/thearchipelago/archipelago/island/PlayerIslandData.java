package ru.thearchipelago.archipelago.island;


public interface PlayerIslandData {

    boolean hasIsland();

    void setIslandPos(int x, int y, int z);

    int getIslandX();
    int getIslandY();
    int getIslandZ();

    int getLevel();
    void setLevel(int level);

    boolean canTeleport();
    void setCanTeleport(boolean value);
}

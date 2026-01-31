package ru.thearchipelago.archipelago.island;



public class PlayerIslandDataStorage implements PlayerIslandData {

    private boolean hasIsland = false;

    private int islandX;
    private int islandY;
    private int islandZ;

    private int level = 1;
    private boolean canTeleport = false;

    @Override
    public boolean hasIsland() {
        return hasIsland;
    }

    @Override
    public void setIslandPos(int x, int y, int z) {
        this.islandX = x;
        this.islandY = y;
        this.islandZ = z;
        this.hasIsland = true;
    }

    @Override
    public int getIslandX() {
        return islandX;
    }

    @Override
    public int getIslandY() {
        return islandY;
    }

    @Override
    public int getIslandZ() {
        return islandZ;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public boolean canTeleport() {
        return canTeleport;
    }

    @Override
    public void setCanTeleport(boolean value) {
        this.canTeleport = value;
    }
}

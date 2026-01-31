package ru.thearchipelago.archipelago.island;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerIslandDataProvider implements ICapabilitySerializable<CompoundTag> {

    public static final Capability<PlayerIslandData> PLAYER_ISLAND =
            CapabilityManager.get(new CapabilityToken<>() {});

    private final PlayerIslandDataStorage data = new PlayerIslandDataStorage();

    @Override
    public @NotNull <T> @Nullable T getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == PLAYER_ISLAND ? PLAYER_ISLAND.cast(data) : null;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();

        tag.putBoolean("HasIsland", data.hasIsland());
        tag.putInt("IslandX", data.getIslandX());
        tag.putInt("IslandY", data.getIslandY());
        tag.putInt("IslandZ", data.getIslandZ());

        tag.putInt("Level", data.getLevel());
        tag.putBoolean("CanTeleport", data.canTeleport());

        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        if (tag.getBoolean("HasIsland")) {
            data.setIslandPos(
                    tag.getInt("IslandX"),
                    tag.getInt("IslandY"),
                    tag.getInt("IslandZ")
            );
        }

        data.setLevel(tag.getInt("Level"));
        data.setCanTeleport(tag.getBoolean("CanTeleport"));
    }
}

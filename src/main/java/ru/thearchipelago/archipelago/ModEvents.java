package main.java.ru.thearchipelago.archipelago;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.world.entity.player.Player;
import ru.thearchipelago.archipelago.island.PlayerIslandDataProvider;

@Mod.EventBusSubscriber(modid = ExampleMod.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Player> event) {
        event.addCapability(
                new ResourceLocation(ExampleMod.MOD_ID, "player_island"),
                new PlayerIslandDataProvider()
        );
    }
    @SubscribeEvent
    public static void onPlayerLogin(net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent event) {

        if (!(event.getEntity() instanceof net.minecraft.server.level.ServerPlayer player)) return;

        player.getCapability(
                ru.thearchipelago.archipelago.island.PlayerIslandDataProvider.PLAYER_ISLAND
        ).ifPresent(data -> {

            if (!data.hasIsland()) {

                var level = player.serverLevel();

                // получаем координаты
                var pos = ru.thearchipelago.archipelago.island.IslandManager.getNextIslandPos(level);

                // генерируем остров
                ru.thearchipelago.archipelago.island.IslandGenerator.generate(level, pos);

                // сохраняем данные
                data.setIslandPos(pos.x(), pos.y(), pos.z());

                // телепортируем игрока
                player.teleportTo(
                        level,
                        pos.x() + 0.5,
                        pos.y() + 2,
                        pos.z() + 0.5,
                        player.getYRot(),
                        player.getXRot()
                );
            }
        });
    }
    @SubscribeEvent
    public static void onPlayerRespawn(net.minecraftforge.event.entity.player.PlayerEvent.PlayerRespawnEvent event) {

        if (!(event.getEntity() instanceof net.minecraft.server.level.ServerPlayer player)) return;

        player.getCapability(
                ru.thearchipelago.archipelago.island.PlayerIslandDataProvider.PLAYER_ISLAND
        ).ifPresent(data -> {

            if (data.hasIsland()) {

                player.teleportTo(
                        player.serverLevel(),
                        data.getIslandX() + 0.5,
                        data.getIslandY() + 2,
                        data.getIslandZ() + 0.5,
                        player.getYRot(),
                        player.getXRot()
                );
            }
        });
    }
    @SubscribeEvent
    public static void onChangeDimension(net.minecraftforge.event.entity.player.PlayerEvent.PlayerChangedDimensionEvent event) {

        if (!(event.getEntity() instanceof net.minecraft.server.level.ServerPlayer player)) return;

        player.getCapability(
                ru.thearchipelago.archipelago.island.PlayerIslandDataProvider.PLAYER_ISLAND
        ).ifPresent(data -> {

            if (data.hasIsland()) {
                player.teleportTo(
                        player.serverLevel(),
                        data.getIslandX() + 0.5,
                        data.getIslandY() + 2,
                        data.getIslandZ() + 0.5,
                        player.getYRot(),
                        player.getXRot()
                );
            }
        });
    }
}
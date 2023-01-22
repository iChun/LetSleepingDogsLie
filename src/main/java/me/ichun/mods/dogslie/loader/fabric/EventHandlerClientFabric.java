package me.ichun.mods.dogslie.loader.fabric;

import me.ichun.mods.dogslie.common.core.EventHandlerClient;
import me.ichun.mods.dogslie.loader.fabric.events.FabricClientEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientLoginConnectionEvents;
import net.minecraft.client.multiplayer.ClientLevel;

public class EventHandlerClientFabric extends EventHandlerClient
{
    public EventHandlerClientFabric()
    {
        ClientEntityEvents.ENTITY_LOAD.register((entity, world) -> onEntityJoinLevel(world, entity));
        ClientTickEvents.END_CLIENT_TICK.register(this::onClientTickEnd);
        FabricClientEvents.CLIENT_LEVEL_LOAD.register(level -> onLevelLoad());
        ClientLoginConnectionEvents.DISCONNECT.register((handler, client) -> onClientDisconnected());
    }

    @Override
    public void fireClientLevelLoad(ClientLevel level)
    {
        FabricClientEvents.CLIENT_LEVEL_LOAD.invoker().onClientLevelLoad(level);
    }
}

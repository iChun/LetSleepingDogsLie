package me.ichun.mods.dogslie.loader.fabric;

import me.ichun.mods.dogslie.common.core.EventHandlerClient;
import me.ichun.mods.ichunutil.loader.fabric.event.client.FabricClientEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;

public class EventHandlerClientFabric extends EventHandlerClient
{
    public EventHandlerClientFabric()
    {
        ClientEntityEvents.ENTITY_LOAD.register((entity, world) -> onEntityJoinLevel(world, entity));
        FabricClientEvents.CLIENT_LEVEL_LOAD.register(level -> onLevelLoad());
    }
}

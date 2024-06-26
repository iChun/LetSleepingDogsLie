package me.ichun.mods.dogslie.loader.forge;

import me.ichun.mods.dogslie.common.core.EventHandlerClient;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EventHandlerClientForge extends EventHandlerClient
{
    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event)
    {
        onEntityJoinLevel(event.getWorld(), event.getEntity());
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event)
    {
        onLevelLoad();
    }
}

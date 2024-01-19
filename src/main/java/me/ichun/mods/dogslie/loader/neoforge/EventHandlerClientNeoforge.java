package me.ichun.mods.dogslie.loader.neoforge;

import me.ichun.mods.dogslie.common.core.EventHandlerClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.level.LevelEvent;

public class EventHandlerClientNeoforge extends EventHandlerClient
{
    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinLevelEvent event)
    {
        onEntityJoinLevel(event.getLevel(), event.getEntity());
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event)
    {
        if(event.phase == TickEvent.Phase.END)
        {
            onClientTickEnd(Minecraft.getInstance());
        }
    }

    @SubscribeEvent
    public void onWorldLoad(LevelEvent.Load event)
    {
        onLevelLoad();
    }

    @SubscribeEvent
    public void onLoggedOutEvent(ClientPlayerNetworkEvent.LoggingOut event)
    {
        onClientDisconnected();
    }

    @Override
    public void fireClientLevelLoad(ClientLevel level){}
}

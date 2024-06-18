package me.ichun.mods.dogslie.loader.forge;

import me.ichun.mods.dogslie.common.core.EventHandlerClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EventHandlerClientForge extends EventHandlerClient
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

package me.ichun.mods.dogslie.loader.forge;

import me.ichun.mods.dogslie.common.core.EventHandlerClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.event.TickEvent;
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
    public void onClientTick(TickEvent.ClientTickEvent event)
    {
        if(event.phase == TickEvent.Phase.END)
        {
            onClientTickEnd(Minecraft.getInstance());
        }
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event)
    {
        onLevelLoad();
    }

    @SubscribeEvent
    public void onLoggedOutEvent(ClientPlayerNetworkEvent.LoggedOutEvent event)
    {
        onClientDisconnected();
    }

    @Override
    public void fireClientLevelLoad(ClientLevel level){}
}

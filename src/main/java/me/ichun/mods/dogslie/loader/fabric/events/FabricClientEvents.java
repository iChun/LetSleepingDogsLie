package me.ichun.mods.dogslie.loader.fabric.events;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.multiplayer.ClientLevel;

@Environment(EnvType.CLIENT)
public class FabricClientEvents
{
    private FabricClientEvents(){}//no init!

    public static final Event<ClientLevelLoad> CLIENT_LEVEL_LOAD = EventFactory.createArrayBacked(ClientLevelLoad.class, callbacks -> level -> {
        for(ClientLevelLoad callback : callbacks)
        {
            callback.onClientLevelLoad(level);
        }
    });

    @FunctionalInterface
    public interface ClientLevelLoad
    {
        void onClientLevelLoad(ClientLevel level);
    }
}

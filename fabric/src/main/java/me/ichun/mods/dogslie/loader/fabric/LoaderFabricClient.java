package me.ichun.mods.dogslie.loader.fabric;

import me.ichun.mods.dogslie.common.LetSleepingDogsLie;
import me.ichun.mods.dogslie.common.core.Config;
import me.ichun.mods.ichunutil.common.iChunUtil;
import net.fabricmc.api.ClientModInitializer;

public class LoaderFabricClient extends LetSleepingDogsLie
        implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        modProxy = this;

        //register config
        config = iChunUtil.d().registerConfig(new Config());

        LetSleepingDogsLie.eventHandlerClient = new EventHandlerClientFabric();
    }
}

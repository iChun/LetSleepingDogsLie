package me.ichun.mods.dogslie.loader.fabric;

import me.ichun.mods.dogslie.common.LetSleepingDogsLie;
import me.lortseam.completeconfig.data.Config;
import net.fabricmc.api.ClientModInitializer;

public class LoaderFabricClient extends LetSleepingDogsLie
        implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        modProxy = this;

        //register config
        ConfigFabric configFabric = new ConfigFabric();
        config = configFabric;
        configFabric.configInstance = new Config(MOD_ID, new String[]{}, configFabric);
        configFabric.configInstance.load();
        Runtime.getRuntime().addShutdownHook(new Thread(configFabric.configInstance::save));

        LetSleepingDogsLie.eventHandlerClient = new EventHandlerClientFabric();
    }
}

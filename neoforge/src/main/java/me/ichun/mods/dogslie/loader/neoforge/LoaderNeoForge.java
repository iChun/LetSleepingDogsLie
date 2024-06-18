package me.ichun.mods.dogslie.loader.neoforge;

import me.ichun.mods.dogslie.common.LetSleepingDogsLie;
import me.ichun.mods.dogslie.common.core.Config;
import me.ichun.mods.ichunutil.common.iChunUtil;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;

@Mod(LetSleepingDogsLie.MOD_ID)
public class LoaderNeoForge extends LetSleepingDogsLie
{
    public LoaderNeoForge(IEventBus modEventBus)
    {
        modProxy = this;

        if(FMLEnvironment.dist.isClient())
        {
            initClient(modEventBus);
        }
        else
        {
            LOGGER.error("You are loading " + MOD_NAME + " on a server. " + MOD_NAME + " is a client only mod!");
        }
    }

    @OnlyIn(net.neoforged.api.distmarker.Dist.CLIENT)
    private void initClient(IEventBus modEventBus)
    {
        setupConfig(modEventBus);
        NeoForge.EVENT_BUS.register(LetSleepingDogsLie.eventHandlerClient = new EventHandlerClientNeoForge());
    }

    @OnlyIn(Dist.CLIENT)
    private void setupConfig(IEventBus modEventBus)
    {
        //register config
        config = iChunUtil.d().registerConfig(new Config(), modEventBus);
    }
}

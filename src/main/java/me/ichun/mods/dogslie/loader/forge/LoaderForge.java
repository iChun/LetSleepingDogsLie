package me.ichun.mods.dogslie.loader.forge;

import me.ichun.mods.dogslie.common.LetSleepingDogsLie;
import me.ichun.mods.dogslie.common.core.Config;
import me.ichun.mods.ichunutil.common.iChunUtil;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;


@Mod(LetSleepingDogsLie.MOD_ID)
public class LoaderForge extends LetSleepingDogsLie
{
    public LoaderForge()
    {
        modProxy = this;

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            setupConfig();
            MinecraftForge.EVENT_BUS.register(LetSleepingDogsLie.eventHandlerClient = new EventHandlerClientForge());
        });
        DistExecutor.unsafeRunWhenOn(Dist.DEDICATED_SERVER, () -> () -> LOGGER.error("You are loading " + MOD_NAME + " on a server. " + MOD_NAME + " is a client only mod!"));

        //Make sure the mod being absent on the other network side does not cause the client to display the server as incompatible
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, IExtensionPoint.DisplayTest.IGNORE_ALL_VERSION);
    }

    private void setupConfig()
    {
        //register config
        config = iChunUtil.d().registerConfig(new Config());
    }
}

package me.ichun.letsleepingdogslie.common;

import me.ichun.letsleepingdogslie.common.core.TickHandlerClient;
import me.ichun.letsleepingdogslie.common.model.ModelWolf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderWolf;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;

@Mod(modid = LetSleepingDogsLie.MODID, name = LetSleepingDogsLie.NAME,
        version = LetSleepingDogsLie.VERSION,
        clientSideOnly = true,
        acceptableRemoteVersions = "*",
        dependencies = "required-after:forge@[13.19.0.2141,)",
        acceptedMinecraftVersions = "[1.12,1.13)"
)
public class LetSleepingDogsLie
{
    public static final String MODID = "dogslie";
    public static final String NAME = "LetSleepingDogsLie";

    public static final String VERSION = "1.0.0";

    @Mod.Instance(MODID)
    public static LetSleepingDogsLie instance;

    private static Logger logger;

    public static TickHandlerClient tickHandlerClient;

    //config

    public static boolean dogsSpawnLying = true;

    public static int timeBeforeLie = 15 * 20; //in ticks

    public static float rangeBeforeGettingUp = 3F;

    public static int getsUpTo = 1;

    public static String[] enabledPoses = new String[]
            {
                    "forelegStraight",
                    "forelegSprawled",
                    "forelegSprawledBack",
                    "forelegSkewed",
                    "forelegSide",
                    "hindlegStraight",
                    "hindlegStraightBack",
                    "hindlegSprawled",
                    "hindlegSprawledBack",
                    "hindlegSide"
            };

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();

        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();

        dogsSpawnLying = config.getBoolean("dogsSpawnLying", "general", dogsSpawnLying, I18n.translateToLocal("lsdl.config.dogsSpawnLying"));
        timeBeforeLie = config.getInt("timeBeforeLie", "general", timeBeforeLie, 1, 6000000, I18n.translateToLocal("lsdl.config.timeBeforeLie"));
        rangeBeforeGettingUp = config.getFloat("rangeBeforeGettingUp", "general", rangeBeforeGettingUp, 0F, 32F, I18n.translateToLocal("lsdl.config.rangeBeforeGettingUp"));
        getsUpTo = config.getInt("getsUpToLie", "general", getsUpTo, 0, 3, I18n.translateToLocal("lsdl.config.getsUpTo"));
        enabledPoses = config.getStringList("enabledPoses", "general", enabledPoses, I18n.translateToLocal("lsdl.config.enabledPoses"), enabledPoses, enabledPoses);

        if(config.hasChanged())
        {
            config.save();
        }
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        if(Loader.isModLoaded("doggytalents"))
        {
            logger.error("Detected Doggy Talents installed, we're incompatible with them, so we won't do anything!");
        }
        else
        {
            init();
        }
    }

    @SideOnly(Side.CLIENT)
    public void init()
    {
        Render<EntityWolf> renderer = Minecraft.getMinecraft().getRenderManager().getEntityClassRenderObject(EntityWolf.class);
        if(renderer instanceof RenderWolf)
        {
            RenderWolf renderWolf = (RenderWolf)renderer;
            if(renderWolf.mainModel.getClass().equals(net.minecraft.client.model.ModelWolf.class)) //It's a vanilla wolf model
            {
                renderWolf.mainModel = new ModelWolf();

                tickHandlerClient = new TickHandlerClient();
                MinecraftForge.EVENT_BUS.register(tickHandlerClient);
                logger.info("Overrode Vanilla Wolf model. We are ready!");
            }
            else
            {
                logger.error("RenderWolf model is not ModelWolf, so we won't do anything!");
            }
        }
        else
        {
            logger.error("Wolf renderer isn't RenderWolf, so we won't do anything!");
        }
    }

}

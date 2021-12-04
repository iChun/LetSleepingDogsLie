package me.ichun.letsleepingdogslie.common;

import me.ichun.letsleepingdogslie.client.core.EventHandler;
import me.ichun.letsleepingdogslie.client.model.WolfModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkConstants;
import org.apache.logging.log4j.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mod(LetSleepingDogsLie.MOD_ID)
public class LetSleepingDogsLie
{
    public static final String MOD_ID = "dogslie";
    public static final String MOD_NAME = "Let Sleeping Dogs Lie";

    private static final Logger LOGGER = LogManager.getLogger();
    private static final Marker INIT = MarkerManager.getMarker("Init");
    private static final Marker MOD_WOLF_SUPPORT = MarkerManager.getMarker("ModWolfSupport");

    public static Config config;
    public static EventHandler eventHandler;

    public LetSleepingDogsLie()
    {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            setupConfig();
            FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onAddLayers);
        });
        DistExecutor.unsafeRunWhenOn(Dist.DEDICATED_SERVER, () -> () -> LOGGER.log(Level.ERROR, "You are loading " + MOD_NAME + " on a server. " + MOD_NAME + " is a client only mod!"));

        //Make sure the mod being absent on the other network side does not cause the client to display the server as incompatible
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> NetworkConstants.IGNORESERVERONLY, (a, b) -> true));
    }

    private void setupConfig()
    {
        //build the config
        ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();

        config = new Config(configBuilder);

        //register the config. This loads the config for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, configBuilder.build(), MOD_ID + ".toml");
    }


    @OnlyIn(Dist.CLIENT)
    private void onAddLayers(EntityRenderersEvent.AddLayers event)
    {
        if(ModList.get().isLoaded("doggytalents"))
        {
            LOGGER.error(INIT, "Detected Doggy Talents installed, they have their own lying down mechanic, meaning we're incompatible with them, so we won't do anything!");
            return;
        }

        boolean replaced = false;

        if(config.attemptModWolfSupport.get())
        {
            Map<EntityType<?>, EntityRenderer<?>> renderers = Minecraft.getInstance().getEntityRenderDispatcher().renderers;
            for(Map.Entry<EntityType<?>, EntityRenderer<? extends Entity >> e : renderers.entrySet())
            {
                if(e.getKey() != EntityType.WOLF && e.getValue() instanceof WolfRenderer && ((WolfRenderer)e.getValue()).model.getClass().equals(net.minecraft.client.model.WolfModel.class)) //we don't do the entity wolf here, just look for mod mobs
                {
                    ((WolfRenderer)e.getValue()).model = new WolfModel();
                    replaced = true;

                    LOGGER.info(MOD_WOLF_SUPPORT, "Overrode " + e.getValue().getClass().getSimpleName() + " model.");
                }
            }
        }

        EntityRenderer<?> renderer = Minecraft.getInstance().getEntityRenderDispatcher().renderers.get(EntityType.WOLF);
        if(renderer instanceof WolfRenderer renderWolf)
        {
            if(renderWolf.model.getClass().equals(net.minecraft.client.model.WolfModel.class)) //It's a vanilla wolf model
            {
                renderWolf.model = new WolfModel();
                replaced = true;

                LOGGER.info(INIT, "Overrode Vanilla Wolf model. We are ready!");
            }
            else
            {
                LOGGER.error(INIT, "WolfRenderer model is not WolfModel, so we won't do anything! {}", renderWolf.model.getClass().getSimpleName());
            }
        }
        else
        {
            LOGGER.error(INIT, "Wolf renderer isn't WolfRenderer, so we won't do anything! {}", renderer != null ? renderer.getClass().getSimpleName() : "null");
        }

        if(replaced && eventHandler == null)
        {
            MinecraftForge.EVENT_BUS.register(eventHandler = new EventHandler());
        }
    }

    public enum GetsUpFor
    {
        NOBODY,
        OWNER,
        PLAYERS,
        ANY_LIVING_ENTITY
    }

    public class Config
    {
        public final ForgeConfigSpec.BooleanValue dogsSpawnLying;
        public final ForgeConfigSpec.IntValue timeBeforeLie;
        public final ForgeConfigSpec.DoubleValue rangeBeforeGettingUp;
        public final ForgeConfigSpec.EnumValue<GetsUpFor> getsUpTo;
        public final ForgeConfigSpec.BooleanValue attemptModWolfSupport;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> enabledPoses;

        public Config(ForgeConfigSpec.Builder builder)
        {
            builder.comment("General settings").push("general");

            dogsSpawnLying = builder.comment("Do dogs spawn into the world lying down if they are already sitting.")
                    .translation("config.dogslie.prop.dogsSpawnLying.desc")
                    .define("dogsSpawnLying", true);
            timeBeforeLie = builder.comment("Time to spend sitting (in ticks) before dogs lie down.")
                    .translation("config.dogslie.prop.timeBeforeLie.desc")
                    .defineInRange("timeBeforeLie", 15 * 20, 1, 6000000);
            rangeBeforeGettingUp = builder.comment("Range for target to get to dog before dog gets up (in blocks)")
                    .translation("config.dogslie.prop.rangeBeforeGettingUp.desc")
                    .defineInRange("rangeBeforeGettingUp", 3D, 0D, 32D);
            getsUpTo = builder.comment("Who the dog gets up to")
                    .translation("config.dogslie.prop.getsUpTo.desc")
                    .defineEnum("getsUpTo", GetsUpFor.OWNER);
            attemptModWolfSupport = builder.comment("Allow the mod to attempt to add support for mod wolves? (Still doesn't allow support for Doggy Talents)")
                    .translation("config.dogslie.prop.attemptModWolfSupport.desc")
                    .define("attemptModWolfSupport", true);

            final List<String> defaultPoses = new ArrayList<>();
            defaultPoses.add("forelegStraight");
            defaultPoses.add("forelegSprawled");
            defaultPoses.add("forelegSprawledBack");
            defaultPoses.add("forelegSkewed");
            defaultPoses.add("forelegSide");
            defaultPoses.add("hindlegStraight");
            defaultPoses.add("hindlegStraightBack");
            defaultPoses.add("hindlegSprawled");
            defaultPoses.add("hindlegSprawledBack");
            defaultPoses.add("hindlegSide");

            enabledPoses = builder.comment("Poses for lying down that are enabled. If the mod can't find compatible poses, it will randomly pick one set.")
                    .translation("config.dogslie.prop.enabledPoses.desc")
                    .defineList("enabledPoses", defaultPoses, x -> x instanceof String && defaultPoses.contains(x));

            builder.pop();
        }
    }
}

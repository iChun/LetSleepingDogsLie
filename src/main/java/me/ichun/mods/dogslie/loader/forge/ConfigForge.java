package me.ichun.mods.dogslie.loader.forge;

import me.ichun.mods.dogslie.common.LetSleepingDogsLie;
import me.ichun.mods.dogslie.common.core.Config;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.List;

public class ConfigForge extends Config
{
    public ConfigForge(ForgeConfigSpec.Builder builder)
    {
        builder.comment("General settings").push("general");

        final ForgeConfigSpec.BooleanValue cDogsSpawnLying = builder.comment(Reference.DOGS_SPAWN_LYING_COMMENT)
            .translation("config.dogslie.prop.dogsSpawnLying.desc")
            .define("dogsSpawnLying", true);
        dogsSpawnLying = new ConfigWrapper<>(cDogsSpawnLying::get, cDogsSpawnLying::set, cDogsSpawnLying::save);

        final ForgeConfigSpec.IntValue cTimeBeforeLie = builder.comment(Reference.TIME_BEFORE_LIE_COMMENT)
                .translation("config.dogslie.prop.timeBeforeLie.desc")
                .defineInRange("timeBeforeLie", 15 * 20, 1, 6000000);
        timeBeforeLie = new ConfigWrapper<>(cTimeBeforeLie::get, cTimeBeforeLie::set, cTimeBeforeLie::save);

        final ForgeConfigSpec.DoubleValue cRangeBeforeGettingUp = builder.comment(Reference.RANGE_BEFORE_GETTING_UP_COMMENT)
                .translation("config.dogslie.prop.rangeBeforeGettingUp.desc")
                .defineInRange("rangeBeforeGettingUp", 3D, 0D, 32D);
        rangeBeforeGettingUp = new ConfigWrapper<>(cRangeBeforeGettingUp::get, cRangeBeforeGettingUp::set, cRangeBeforeGettingUp::save);

        final ForgeConfigSpec.EnumValue<LetSleepingDogsLie.GetsUpFor> cGetsUpTo = builder.comment(Reference.GETS_UP_TO_COMMENT)
                .translation("config.dogslie.prop.getsUpTo.desc")
                .defineEnum("getsUpTo", LetSleepingDogsLie.GetsUpFor.OWNER);
        getsUpTo = new ConfigWrapper<>(cGetsUpTo::get, cGetsUpTo::set, cGetsUpTo::save);

        final ForgeConfigSpec.ConfigValue<List<? extends String>> cEnabledPoses = builder.comment(Reference.ENABLED_POSES_COMMENT)
                .translation("config.dogslie.prop.enabledPoses.desc")
                .defineList("enabledPoses", new ArrayList<>(DEFAULT_POSES), x -> x instanceof String && DEFAULT_POSES.contains(x));
        enabledPoses = new ConfigWrapper<>(() -> new ArrayList<>(cEnabledPoses.get()), cEnabledPoses::set, cEnabledPoses::save);

        builder.pop();
    }
}

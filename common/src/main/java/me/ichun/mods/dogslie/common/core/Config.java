package me.ichun.mods.dogslie.common.core;

import com.google.common.collect.ImmutableList;
import me.ichun.mods.dogslie.common.LetSleepingDogsLie;
import me.ichun.mods.ichunutil.common.config.ConfigBase;
import me.ichun.mods.ichunutil.common.config.annotations.Prop;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Config extends ConfigBase
{
    public static final List<String> DEFAULT_POSES = ImmutableList.of(
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
    );

    public boolean dogsSpawnLying = true;

    @Prop(min = 1, max = 6000000)
    public int timeBeforeLie = 15 * 20;

    @Prop(min = 0D, max = 32D)
    public double rangeBeforeGettingUp = 3D;

    public LetSleepingDogsLie.GetsUpFor getsUpFor = LetSleepingDogsLie.GetsUpFor.OWNER;

    public boolean playSoundWhenLieStateChanges = true;

    @Prop(validator = "validateEnabledPoses")
    public List<String> enabledPoses = new ArrayList<>(DEFAULT_POSES);

    public Config()
    {
        super("letsleepingdogslie.toml");
    }

    public boolean validateEnabledPoses(Object o)
    {
        if(o instanceof String s)
        {
            return DEFAULT_POSES.contains(s);
        }
        return false;
    }

    @NotNull
    @Override
    public String getModId()
    {
        return LetSleepingDogsLie.MOD_ID;
    }

    @NotNull
    @Override
    public String getConfigName()
    {
        return LetSleepingDogsLie.MOD_NAME;
    }

    @Override
    public Type getConfigType()
    {
        return Type.CLIENT;
    }
}

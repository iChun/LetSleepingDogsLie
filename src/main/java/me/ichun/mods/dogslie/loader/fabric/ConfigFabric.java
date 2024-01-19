package me.ichun.mods.dogslie.loader.fabric;

import me.ichun.mods.dogslie.common.LetSleepingDogsLie;
import me.ichun.mods.dogslie.common.core.Config;
import me.lortseam.completeconfig.api.ConfigContainer;
import me.lortseam.completeconfig.api.ConfigEntries;
import me.lortseam.completeconfig.api.ConfigEntry;
import me.lortseam.completeconfig.api.ConfigGroup;

import java.util.ArrayList;
import java.util.List;

public class ConfigFabric extends Config
        implements ConfigContainer
{
    public static General GENERAL = null;

    public me.lortseam.completeconfig.data.Config configInstance;

    public ConfigFabric()
    {
        dogsSpawnLying = new ConfigWrapper<>(() -> GENERAL.dogsSpawnLying, v -> GENERAL.dogsSpawnLying = v);
        timeBeforeLie = new ConfigWrapper<>(() -> GENERAL.timeBeforeLie, v -> GENERAL.timeBeforeLie = v);
        rangeBeforeGettingUp = new ConfigWrapper<>(() -> GENERAL.rangeBeforeGettingUp, v -> GENERAL.rangeBeforeGettingUp = v);
        getsUpTo = new ConfigWrapper<>(() -> GENERAL.getsUpTo, v -> GENERAL.getsUpTo = v);
        enabledPoses = new ConfigWrapper<>(() -> GENERAL.enabledPoses, v -> GENERAL.enabledPoses = v);
    }

    @Transitive
    @ConfigEntries(includeAll = true)
    public static class General implements ConfigGroup
    {
        public General()
        {
            GENERAL = this;
        }

        @Override
        public String getComment()
        {
            return "General configs that don't fit any other category.";
        }

        @ConfigEntry(nameKey = "prop.dogsSpawnLying.name", descriptionKey = "prop.dogsSpawnLying.desc", comment = Reference.DOGS_SPAWN_LYING_COMMENT)
        public boolean dogsSpawnLying = true;

        @ConfigEntry(nameKey = "prop.timeBeforeLie.name", descriptionKey = "prop.timeBeforeLie.desc", comment = Reference.TIME_BEFORE_LIE_COMMENT)
        @ConfigEntry.BoundedInteger(min = 1, max = 6000000)
        public int timeBeforeLie = 15 * 20;

        @ConfigEntry(nameKey = "prop.rangeBeforeGettingUp.name", descriptionKey = "prop.rangeBeforeGettingUp.desc", comment = Reference.RANGE_BEFORE_GETTING_UP_COMMENT)
        @ConfigEntry.BoundedDouble(min = 0D, max = 32D)
        public double rangeBeforeGettingUp = 3D;

        @ConfigEntry(nameKey = "prop.getsUpTo.name", descriptionKey = "prop.getsUpTo.desc", comment = Reference.GETS_UP_TO_COMMENT)
        public LetSleepingDogsLie.GetsUpFor getsUpTo = LetSleepingDogsLie.GetsUpFor.OWNER;

        @ConfigEntry(nameKey = "prop.enabledPoses.name", descriptionKey = "prop.enabledPoses.desc", comment = Reference.ENABLED_POSES_COMMENT)
        public List<String> enabledPoses = new ArrayList<>(DEFAULT_POSES);
    }
}

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

    public ConfigFabric()
    {
        dogsSpawnLying = new ConfigWrapper<>(() -> GENERAL.dogsSpawnLying, v -> GENERAL.dogsSpawnLying = v);
        timeBeforeLie = new ConfigWrapper<>(() -> GENERAL.timeBeforeLie, v -> GENERAL.timeBeforeLie = v);
        rangeBeforeGettingUp = new ConfigWrapper<>(() -> GENERAL.rangeBeforeGettingUp, v -> GENERAL.rangeBeforeGettingUp = v);
        getsUpTo = new ConfigWrapper<>(() -> GENERAL.getsUpTo, v -> GENERAL.getsUpTo = v);
        enabledPoses = new ConfigWrapper<>(() -> GENERAL.enabledPoses, v -> GENERAL.enabledPoses = v);
    }

    @Transitive
    @ConfigEntries
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

        @ConfigEntry(comment = "Do dogs spawn into the world lying down if they are already sitting.")
        public boolean dogsSpawnLying = true;

        @ConfigEntry(comment = "Time to spend sitting (in ticks) before dogs lie down.")
        @ConfigEntry.BoundedInteger(min = 1, max = 6000000)
        public int timeBeforeLie = 15 * 20;

        @ConfigEntry(comment = "Range for target to get to dog before dog gets up (in blocks)")
        @ConfigEntry.BoundedDouble(min = 0D, max = 32D)
        public double rangeBeforeGettingUp = 3D;

        @ConfigEntry(comment = "Who the dog gets up to?\nAccepts: NOBODY, OWNER, PLAYERS, ANY_LIVING_ENTITY")
        public LetSleepingDogsLie.GetsUpFor getsUpTo = LetSleepingDogsLie.GetsUpFor.OWNER;

        @ConfigEntry(comment = "Poses for lying down that are enabled. If the mod can't find compatible poses, it will randomly pick one set.")
        public List<String> enabledPoses = new ArrayList<>(DEFAULT_POSES);
    }
}

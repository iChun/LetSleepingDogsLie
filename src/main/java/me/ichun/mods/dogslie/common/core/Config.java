package me.ichun.mods.dogslie.common.core;

import com.google.common.collect.ImmutableList;
import me.ichun.mods.dogslie.common.LetSleepingDogsLie;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class Config
{
    public ConfigWrapper<Boolean> dogsSpawnLying;
    public ConfigWrapper<Integer> timeBeforeLie;
    public ConfigWrapper<Double> rangeBeforeGettingUp;
    public ConfigWrapper<LetSleepingDogsLie.GetsUpFor> getsUpTo;
    public ConfigWrapper<List<String>> enabledPoses;

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

    protected static class Reference
    {
        public static final String DOGS_SPAWN_LYING_COMMENT = "Do dogs spawn into the world lying down if they are already sitting.";
        public static final String TIME_BEFORE_LIE_COMMENT = "Time to spend sitting (in ticks) before dogs lie down.";
        public static final String RANGE_BEFORE_GETTING_UP_COMMENT = "Range for target to get to dog before dog gets up (in blocks)";
        public static final String GETS_UP_TO_COMMENT = "Who the dog gets up to?\nAccepts: NOBODY, OWNER, PLAYERS, ANY_LIVING_ENTITY";
        public static final String ENABLED_POSES_COMMENT = "Poses for lying down that are enabled. If the mod can't find compatible poses, it will randomly pick one set.";
    }

    public static class ConfigWrapper<T>
    {
        public final Supplier<T> getter;
        public final Consumer<T> setter;
        public final Runnable saver;

        public ConfigWrapper(Supplier<T> getter, Consumer<T> setter) {
            this.getter = getter;
            this.setter = setter;
            this.saver = null;
        }

        public ConfigWrapper(Supplier<T> getter, Consumer<T> setter, Runnable saver) {
            this.getter = getter;
            this.setter = setter;
            this.saver = saver;
        }

        public T get()
        {
            return getter.get();
        }

        public void set(T obj)
        {
            setter.accept(obj);
        }

        public void save()
        {
            if(saver != null)
            {
                saver.run();
            }
        }
    }
}

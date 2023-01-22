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

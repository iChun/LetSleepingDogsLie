package me.ichun.mods.dogslie.common;

import com.mojang.logging.LogUtils;
import me.ichun.mods.dogslie.common.core.Config;
import me.ichun.mods.dogslie.common.core.EventHandlerClient;
import org.slf4j.Logger;

public abstract class LetSleepingDogsLie
{
    public static final String MOD_ID = "dogslie";
    public static final String MOD_NAME = "Let Sleeping Dogs Lie";

    public static final Logger LOGGER = LogUtils.getLogger();

    public static LetSleepingDogsLie modProxy;

    public static Config config;
    public static EventHandlerClient eventHandlerClient;

    public enum GetsUpFor
    {
        NOBODY,
        OWNER,
        PLAYERS,
        ANY_LIVING_ENTITY
    }
}

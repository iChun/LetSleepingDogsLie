package me.ichun.letsleepingdogslie.client.core;

import me.ichun.letsleepingdogslie.common.LetSleepingDogsLie;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.WeakHashMap;

@OnlyIn(Dist.CLIENT)
public class EventHandler
{
    public Random rand = new Random();

    public int worldLoadCooldown = 0;

    public WeakHashMap<WolfEntity, WolfInfo> wolfInfo = new WeakHashMap<>();

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event)
    {
        if(event.getWorld().isRemote && event.getEntity() instanceof WolfEntity)
        {
            WolfEntity wolf = (WolfEntity)event.getEntity();
            if(!wolfInfo.containsKey(wolf))
            {
                wolfInfo.put(wolf, new WolfInfo(LetSleepingDogsLie.config.dogsSpawnLying.get() && worldLoadCooldown > 0));
            }
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event)
    {
        if(event.phase == TickEvent.Phase.END)
        {
            worldLoadCooldown--;

            if(!Minecraft.getInstance().isGamePaused())
            {
                wolfInfo.entrySet().removeIf(e -> !e.getValue().tick(e.getKey()));
            }
        }
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event)
    {
        Minecraft.getInstance().execute(this::clean);
    }

    @SubscribeEvent
    public void onLoggedOutEvent(ClientPlayerNetworkEvent.LoggedOutEvent event)
    {
        Minecraft.getInstance().execute(this::clean);
    }

    public void clean()
    {
        wolfInfo.clear();
        worldLoadCooldown = 20;
    }

    @Nonnull
    public WolfInfo getWolfInfo(WolfEntity wolf)
    {
        if(wolfInfo.containsKey(wolf))
        {
            return wolfInfo.get(wolf);
        }
        else
        {
            WolfInfo info = new WolfInfo(LetSleepingDogsLie.config.dogsSpawnLying.get() && worldLoadCooldown > 0);
            wolfInfo.put(wolf, info);
            return info;
        }
    }


    public class WolfInfo
    {
        public int sitTime;

        public String[] setPoses = null;

        public WolfInfo(boolean lying)
        {
            sitTime = lying ? LetSleepingDogsLie.config.timeBeforeLie.get() : 0;
        }

        public boolean tick(WolfEntity parent)
        {
            if(parent.removed)
            {
                return false;
            }
            if(parent.isEntitySleeping()) //func_233684_eK_() = isEntitySleeping()
            {
                boolean isLying = isLying();
                sitTime++;

                if(!isLying && isLying() && worldLoadCooldown < 0)
                {
                    parent.getEntityWorld().playSound(parent.getPosX(), parent.getPosY() + parent.getEyeHeight(), parent.getPosZ(), SoundEvents.ENTITY_WOLF_WHINE, parent.getSoundCategory(), 0.4F, parent.isChild() ? (parent.getRNG().nextFloat() - parent.getRNG().nextFloat()) * 0.2F + 1.5F : (parent.getRNG().nextFloat() - parent.getRNG().nextFloat()) * 0.2F + 1.0F, false);
                }

                LetSleepingDogsLie.GetsUpFor getsUpFor = LetSleepingDogsLie.config.getsUpTo.get();
                if(parent.ticksExisted % 10 == 0 && getsUpFor != LetSleepingDogsLie.GetsUpFor.NOBODY && LetSleepingDogsLie.config.rangeBeforeGettingUp.get() > 0.1D)
                {
                    List<Entity> ents = parent.getEntityWorld().getEntitiesWithinAABBExcludingEntity(parent, parent.getBoundingBox().grow(LetSleepingDogsLie.config.rangeBeforeGettingUp.get()));
                    if(ents.stream().anyMatch(entity -> (getsUpFor == LetSleepingDogsLie.GetsUpFor.OWNER && entity instanceof LivingEntity && parent.isOwner((LivingEntity)entity) ||
                            getsUpFor == LetSleepingDogsLie.GetsUpFor.PLAYERS && entity instanceof PlayerEntity && !entity.isSpectator() ||
                            getsUpFor == LetSleepingDogsLie.GetsUpFor.ANY_LIVING_ENTITY && entity instanceof LivingEntity && !(entity instanceof PlayerEntity && entity.isSpectator())) && parent.canEntityBeSeen(entity)))
                    {
                        if(isLying)
                        {
                            parent.getEntityWorld().playSound(parent.getPosX(), parent.getPosY() + parent.getEyeHeight(), parent.getPosZ(), SoundEvents.ENTITY_WOLF_AMBIENT, parent.getSoundCategory(), 0.4F, parent.isChild() ? (parent.getRNG().nextFloat() - parent.getRNG().nextFloat()) * 0.2F + 1.5F : (parent.getRNG().nextFloat() - parent.getRNG().nextFloat()) * 0.2F + 1.0F, false);
                        }
                        sitTime = 0;
                        setPoses = null;
                    }
                }
            }
            else
            {
                sitTime = 0;
                setPoses = null;
            }

            return true;
        }

        public boolean isLying()
        {
            return sitTime > LetSleepingDogsLie.config.timeBeforeLie.get();
        }

        public String[] getCompatiblePoses(WolfEntity parent)
        {
            if(setPoses == null)
            {
                String[] poses = new String[2];

                ArrayList<String> front = new ArrayList<>();
                ArrayList<String> rear = new ArrayList<>();
                for(String s : LetSleepingDogsLie.config.enabledPoses.get())
                {
                    if(s.startsWith("foreleg") && (!parent.isChild() || (s.equalsIgnoreCase("forelegSprawledBack") || s.equalsIgnoreCase("forelegSide"))))
                    {
                        front.add(s);
                    }
                    if(s.startsWith("hindleg"))
                    {
                        rear.add(s);
                    }
                }

                if(front.isEmpty())
                {
                    if(!parent.isChild())
                    {
                        front.add("forelegStraight");
                        front.add("forelegSprawled");
                        front.add("forelegSkewed");
                    }
                    front.add("forelegSprawledBack");
                    front.add("forelegSide");
                }

                if(rear.isEmpty())
                {
                    rear.add("hindlegStraight");
                    rear.add("hindlegStraightBack");
                    rear.add("hindlegSprawled");
                    rear.add("hindlegSprawledBack");
                    rear.add("hindlegSide");
                }

                if(rand.nextBoolean()) //front first
                {
                    poses[0] = front.get(rand.nextInt(front.size()));
                    poses[1] = rear.get(rand.nextInt(rear.size()));
                }
                else //back first
                {
                    poses[1] = rear.get(rand.nextInt(rear.size()));
                    poses[0] = front.get(rand.nextInt(front.size()));
                }

                if(poses[0].endsWith("Side") && poses[1].endsWith("Side"))
                {
                    String side = rand.nextBoolean() ? "L" : "R";
                    poses[0] = poses[0] + side;
                    poses[1] = poses[1] + side;
                }
                else
                {
                    if(poses[0].endsWith("Side") || poses[0].endsWith("Skewed"))
                    {
                        poses[0] = poses[0] + (rand.nextBoolean() ? "L" : "R");
                    }
                    if(poses[1].endsWith("Side"))
                    {
                        poses[1] = poses[1] + (rand.nextBoolean() ? "L" : "R");
                    }
                }

                setPoses = poses;
            }
            return setPoses;
        }
    }
}

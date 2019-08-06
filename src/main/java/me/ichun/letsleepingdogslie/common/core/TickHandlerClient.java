package me.ichun.letsleepingdogslie.common.core;

import me.ichun.letsleepingdogslie.common.LetSleepingDogsLie;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.WeakHashMap;

public class TickHandlerClient
{
    public Random rand = new Random();

    public int worldLoadCooldown = 0;

    public WeakHashMap<EntityWolf, WolfInfo> wolfInfo = new WeakHashMap<>();

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event)
    {
        if(event.getWorld().isRemote && event.getEntity() instanceof EntityWolf)
        {
            EntityWolf wolf = (EntityWolf)event.getEntity();
            if(!wolfInfo.containsKey(wolf))
            {
                wolfInfo.put(wolf, new WolfInfo(LetSleepingDogsLie.dogsSpawnLying && worldLoadCooldown > 0));
            }
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event)
    {
        if(event.phase == TickEvent.Phase.END)
        {
            worldLoadCooldown--;

            if(!Minecraft.getMinecraft().isGamePaused())
            {
                wolfInfo.entrySet().removeIf(e -> !e.getValue().tick(e.getKey()));
            }
        }
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event)
    {
        Minecraft.getMinecraft().addScheduledTask(this::clean);
    }

    @SubscribeEvent
    public void onClientDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent event)
    {
        Minecraft.getMinecraft().addScheduledTask(this::clean);
    }

    public void clean()
    {
        wolfInfo.clear();
        worldLoadCooldown = 20;
    }

    public WolfInfo getWolfInfo(EntityWolf wolf)
    {
        if(wolfInfo.containsKey(wolf))
        {
            return wolfInfo.get(wolf);
        }
        else
        {
            WolfInfo info = new WolfInfo(LetSleepingDogsLie.dogsSpawnLying && worldLoadCooldown > 0);
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
            sitTime = lying ? LetSleepingDogsLie.timeBeforeLie : 0;
        }

        public boolean tick(EntityWolf parent)
        {
            if(parent.isDead)
            {
                return false;
            }
            if(parent.isSitting())
            {
                boolean isLying = isLying();
                sitTime++;

                if(!isLying && isLying() && worldLoadCooldown < 0)
                {
                    parent.getEntityWorld().playSound(parent.posX, parent.posY + parent.getEyeHeight(), parent.posZ, SoundEvents.ENTITY_WOLF_WHINE, parent.getSoundCategory(), 0.4F, parent.isChild() ? (parent.getRNG().nextFloat() - parent.getRNG().nextFloat()) * 0.2F + 1.5F : (parent.getRNG().nextFloat() - parent.getRNG().nextFloat()) * 0.2F + 1.0F, false);
                }

                if(parent.ticksExisted % 10 == 0 && LetSleepingDogsLie.getsUpTo > 0 && LetSleepingDogsLie.rangeBeforeGettingUp > 0.1F)
                {
                    List<Entity> ents = parent.getEntityWorld().getEntitiesWithinAABBExcludingEntity(parent, parent.getEntityBoundingBox().grow(LetSleepingDogsLie.rangeBeforeGettingUp));
                    if(ents.stream().anyMatch(entity -> (LetSleepingDogsLie.getsUpTo == 1 && entity instanceof EntityLivingBase && parent.isOwner((EntityLivingBase)entity) ||
                            LetSleepingDogsLie.getsUpTo == 2 && entity instanceof EntityPlayer && !((EntityPlayer)entity).isSpectator() ||
                            LetSleepingDogsLie.getsUpTo == 3 && entity instanceof EntityLivingBase && !(entity instanceof EntityPlayer && ((EntityPlayer)entity).isSpectator())) && parent.canEntityBeSeen(entity)))
                    {
                        if(isLying)
                        {
                            parent.getEntityWorld().playSound(parent.posX, parent.posY + parent.getEyeHeight(), parent.posZ, SoundEvents.ENTITY_WOLF_AMBIENT, parent.getSoundCategory(), 0.4F, parent.isChild() ? (parent.getRNG().nextFloat() - parent.getRNG().nextFloat()) * 0.2F + 1.5F : (parent.getRNG().nextFloat() - parent.getRNG().nextFloat()) * 0.2F + 1.0F, false);
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
            return sitTime > LetSleepingDogsLie.timeBeforeLie;
        }

        public String[] getCompatiblePoses(EntityWolf parent)
        {
            if(setPoses == null)
            {
                String[] poses = new String[2];

                ArrayList<String> front = new ArrayList<>();
                ArrayList<String> rear = new ArrayList<>();
                for(String s : LetSleepingDogsLie.enabledPoses)
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
                else if(poses[0].endsWith("Side") || poses[0].endsWith("Skewed"))
                {
                    poses[0] = poses[0] + (rand.nextBoolean() ? "L" : "R");
                }
                else if(poses[1].endsWith("Side"))
                {
                    poses[1] = poses[1] + (rand.nextBoolean() ? "L" : "R");
                }

                setPoses = poses;
            }
            return setPoses;
        }
    }
}

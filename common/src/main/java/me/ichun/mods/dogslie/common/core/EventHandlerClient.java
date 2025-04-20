package me.ichun.mods.dogslie.common.core;

import me.ichun.mods.dogslie.common.LetSleepingDogsLie;
import me.ichun.mods.dogslie.mixin.WolfAccessorMixin;
import me.ichun.mods.ichunutil.common.iChunUtil;
import me.ichun.mods.ichunutil.loader.event.client.LivingRenderPreEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.WolfModel;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.client.renderer.entity.state.WolfRenderState;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.animal.wolf.WolfSoundVariant;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.WeakHashMap;

public class EventHandlerClient
{
    public final LivingRenderPreEvent.LastRenderedEntitySupplier<Wolf> wolfRendered;

    public Random rand = new Random();

    public WeakHashMap<Wolf, WolfInfo> wolfInfo = new WeakHashMap<>();


    public EventHandlerClient()
    {
        wolfRendered = new LivingRenderPreEvent.LastRenderedEntitySupplier<>(event -> event.renderer() instanceof WolfRenderer renderer && renderer.getModel().getClass().equals(WolfModel.class) && event.livingEntity() instanceof Wolf && event.renderState() instanceof WolfRenderState);

        iChunUtil.eC().registerClientTickEndListener(this::onClientTickEnd);
        iChunUtil.eC().registerClientLevelLoadListener(level -> onLevelLoad());
        iChunUtil.eC().registerClientEntityJoinLevelListener(this::onClientEntityJoinLevel);

        iChunUtil.eC().registerOnClientDisconnectListener(client -> onClientDisconnected());
    }

    public void onClientEntityJoinLevel(Level level, Entity entity)
    {
        if(entity instanceof Wolf wolf)
        {
            if(!wolfInfo.containsKey(wolf))
            {
                wolfInfo.put(wolf, new WolfInfo(LetSleepingDogsLie.config.dogsSpawnLying && Minecraft.getInstance().cameraEntity != null && Minecraft.getInstance().cameraEntity.tickCount < 20));
            }
        }
    }

    public void onClientTickEnd(Minecraft client)
    {
        if(!client.isPaused())
        {
            wolfInfo.entrySet().removeIf(e -> !e.getValue().tick(e.getKey()));
        }
    }

    public void onLevelLoad()
    {
        Minecraft.getInstance().execute(this::clean);
    }

    public void onClientDisconnected()
    {
        Minecraft.getInstance().execute(this::clean);
    }

    public void clean()
    {
        wolfInfo.clear();
    }

    @NotNull
    public WolfInfo getWolfInfo(Wolf wolf)
    {
        return wolfInfo.computeIfAbsent(wolf, w -> new WolfInfo(LetSleepingDogsLie.config.dogsSpawnLying && Minecraft.getInstance().cameraEntity != null && Minecraft.getInstance().cameraEntity.tickCount < 20));
    }


    public class WolfInfo
    {
        public int sitTime;

        public String[] setPoses = null;

        public WolfInfo(boolean lying)
        {
            sitTime = lying ? LetSleepingDogsLie.config.timeBeforeLie : 0;
        }

        public boolean tick(Wolf parent)
        {
            if(parent.isRemoved())
            {
                return false;
            }
            if(parent.isInSittingPose()) //isInSittingPose() = isEntitySleeping()
            {
                boolean isLying = isLying();
                sitTime++;

                Holder<WolfSoundVariant> wolfSoundVariant = ((WolfAccessorMixin)parent).invokeGetSoundVariant();

                if(!isLying && isLying() && Minecraft.getInstance().cameraEntity != null && Minecraft.getInstance().cameraEntity.tickCount > 20 && LetSleepingDogsLie.config.playSoundWhenLieStateChanges)
                {
                    parent.getCommandSenderWorld().playLocalSound(parent.getX(), parent.getY() + parent.getEyeHeight(), parent.getZ(), wolfSoundVariant.value().whineSound().value(), parent.getSoundSource(), 0.4F, parent.isBaby() ? (parent.getRandom().nextFloat() - parent.getRandom().nextFloat()) * 0.2F + 1.5F : (parent.getRandom().nextFloat() - parent.getRandom().nextFloat()) * 0.2F + 1.0F, false);
                }

                LetSleepingDogsLie.GetsUpFor getsUpFor = LetSleepingDogsLie.config.getsUpFor;
                if(parent.tickCount % 10 == 0 && getsUpFor != LetSleepingDogsLie.GetsUpFor.NOBODY && LetSleepingDogsLie.config.rangeBeforeGettingUp > 0.1D)
                {
                    List<Entity> ents = parent.getCommandSenderWorld().getEntities(parent, parent.getBoundingBox().inflate(LetSleepingDogsLie.config.rangeBeforeGettingUp));
                    if(ents.stream().anyMatch(entity -> (getsUpFor == LetSleepingDogsLie.GetsUpFor.OWNER && entity instanceof LivingEntity && parent.isOwnedBy((LivingEntity)entity) ||
                        getsUpFor == LetSleepingDogsLie.GetsUpFor.PLAYERS && entity instanceof Player && !entity.isSpectator() ||
                        getsUpFor == LetSleepingDogsLie.GetsUpFor.ANY_LIVING_ENTITY && entity instanceof LivingEntity && !(entity instanceof Player && entity.isSpectator())) && parent.hasLineOfSight(entity)))
                    {
                        if(isLying && LetSleepingDogsLie.config.playSoundWhenLieStateChanges)
                        {
                            parent.getCommandSenderWorld().playLocalSound(parent.getX(), parent.getY() + parent.getEyeHeight(), parent.getZ(), wolfSoundVariant.value().ambientSound().value(), parent.getSoundSource(), 0.4F, parent.isBaby() ? (parent.getRandom().nextFloat() - parent.getRandom().nextFloat()) * 0.2F + 1.5F : (parent.getRandom().nextFloat() - parent.getRandom().nextFloat()) * 0.2F + 1.0F, false);
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
            return sitTime > LetSleepingDogsLie.config.timeBeforeLie;
        }

        public String[] getCompatiblePoses(Wolf parent)
        {
            if(setPoses == null)
            {
                String[] poses = new String[2];

                ArrayList<String> front = new ArrayList<>();
                ArrayList<String> rear = new ArrayList<>();
                for(String s : LetSleepingDogsLie.config.enabledPoses)
                {
                    if(s.startsWith("foreleg") && (!parent.isBaby() || (s.equalsIgnoreCase("forelegSprawledBack") || s.equalsIgnoreCase("forelegSide"))))
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
                    if(!parent.isBaby())
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

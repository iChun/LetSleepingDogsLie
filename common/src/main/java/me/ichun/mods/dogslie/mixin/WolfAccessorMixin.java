package me.ichun.mods.dogslie.mixin;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.animal.wolf.WolfSoundVariant;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Wolf.class)
public interface WolfAccessorMixin
{
    @Invoker
    Holder<WolfSoundVariant> invokeGetSoundVariant();
}

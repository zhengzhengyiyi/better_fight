package com.zhengzhengyiyimc.fighting_villager.mixin;

import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin {
    @Shadow
    protected GoalSelector targetSelector;
    
    @Shadow
    protected GoalSelector goalSelector;
}
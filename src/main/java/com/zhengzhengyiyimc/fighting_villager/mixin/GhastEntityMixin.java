package com.zhengzhengyiyimc.fighting_villager.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.world.World;

@Mixin(GhastEntity.class)
public class GhastEntityMixin extends MobEntityMixin {
    @Inject(method = "<init>", at = @At("TAIL"))
    private void addVillagerTargetGoal(EntityType<? extends GhastEntity> entityType, World world, CallbackInfo ci) {
        this.targetSelector.add(3, new ActiveTargetGoal<>(
            (GhastEntity)(Object)this,
            VillagerEntity.class,
            true
        ));
    }
}

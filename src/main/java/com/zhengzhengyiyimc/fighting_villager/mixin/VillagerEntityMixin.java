package com.zhengzhengyiyimc.fighting_villager.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin extends MobEntityMixin implements RangedAttackMob {
    @ModifyReturnValue(
        method = "createVillagerAttributes",
        at = @At("RETURN")
    )
    private static DefaultAttributeContainer.Builder addAttackAttribute(DefaultAttributeContainer.Builder original) {
        return original.add(EntityAttributes.ATTACK_DAMAGE, 6.0D);
    }

    @Override
    public void shootAt(LivingEntity target, float pullProgress) {
        ArrowEntity arrow = new ArrowEntity(
            EntityType.ARROW, ((VillagerEntity) (Object) this).getWorld()
        );
        double dx = target.getX() - ((VillagerEntity)(Object)this).getX();
        double dy = target.getBodyY(0.333) - arrow.getY();
        double dz = target.getZ() - ((VillagerEntity)(Object)this).getZ();
        double distance = Math.sqrt(dx * dx + dz * dz);

        arrow.setVelocity(dx, dy + distance * 0.2, dz, 1.6F, 14 - ((VillagerEntity)(Object)this).getWorld().getDifficulty().getId() * 4);
        ((VillagerEntity)(Object)this).getWorld().spawnEntity(arrow);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(EntityType<? extends VillagerEntity> entityType, World world, CallbackInfo ci) {
        VillagerEntity villager = (VillagerEntity) (Object) this;

        villager.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
        villager.equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.BOW));

        if (world.getRandom().nextFloat() < 0.3f) {
            villager.equipStack(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
            villager.equipStack(EquipmentSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
        }

        addAttackGoals();
    }

    private void addAttackGoals() {
        this.targetSelector.add(3, new ActiveTargetGoal<>(
            (MobEntity) (Object) this,
            HostileEntity.class,
            true
        ));

        this.goalSelector.add(4, new MeleeAttackGoal(
            (PathAwareEntity) (Object) this,
            1.0,
            true
        ));
    }
}
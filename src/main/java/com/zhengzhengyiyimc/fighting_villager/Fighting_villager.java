package com.zhengzhengyiyimc.fighting_villager;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.VillagerEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Fighting_villager implements ModInitializer {
	public static final String MOD_ID = "fighting_villager";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		createVillagerAttributes();
	}

	public static DefaultAttributeContainer.Builder createVillagerAttributes() {
        return VillagerEntity.createVillagerAttributes()
            .add(EntityAttributes.ATTACK_DAMAGE, 4.0);
    }
}
// Copyright 2023 Justin Hu
//
// This file is part of Total War
//
// Total War is free software: you can redistribute it and/or modify it under
// the terms of the GNU Affero General Public License as published by the Free
// Software Foundation, either version 3 of the License, or (at your option)
// any later version.
//
// This program is distributed in the hope that it will be useful, but WITHOUT
// ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
// FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License
// for more details.
//
// You should have received a copy of the GNU Affero General Public License
// along with Total War. If not, see <https://www.gnu.org/licenses/>.
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package ca.zootron.total_war;

import java.util.ArrayList;
import java.util.List;

import ca.zootron.total_war.blockentities.CreativeGeneratorBlockEntity;
import ca.zootron.total_war.blockentities.LVCableCopperBlockEntity;
import ca.zootron.total_war.blockentities.ResistorBlockEntity;
import ca.zootron.total_war.blocks.CreativeGeneratorBlock;
import ca.zootron.total_war.blocks.LVCableCopperBlock;
import ca.zootron.total_war.blocks.ResistorBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder.Factory;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

/**
 * Block entities registration static class
 */
public abstract class TWBlockEntities {
  public static final List<BlockEntityRecord<?>> blockEntities = new ArrayList<>();

  public static final BlockEntityRecord<ResistorBlockEntity> RESISTOR = blockEntity("Resistor", "resistor",
      new ResistorBlock(FabricBlockSettings.of(Material.STONE).strength(5, 6).requiresTool()),
      ResistorBlockEntity::new);
  static {
    FlammableBlockRegistry.getDefaultInstance().add(RESISTOR.block().getBlock(), 5, 5);
  }

  public static final BlockEntityRecord<LVCableCopperBlockEntity> LV_CABLE_COPPER = blockEntity("LV Cable (Copper)",
      "lv_cable_copper",
      new LVCableCopperBlock(FabricBlockSettings.of(Material.METAL).strength(3, 6).requiresTool()),
      LVCableCopperBlockEntity::new);

  public static final BlockEntityRecord<CreativeGeneratorBlockEntity> CREATIVE_GENERATOR = blockEntity(
      "Creative Generator", "creative_generator",
      new CreativeGeneratorBlock(FabricBlockSettings.of(Material.METAL).strength(-1.0f, 3600000.0f).dropsNothing()
          .allowsSpawning((state, view, pos, entity) -> false)),
      CreativeGeneratorBlockEntity::new);

  public static <T extends BlockEntity, U extends Block & BlockEntityProvider> BlockEntityRecord<T> blockEntity(
      String englishName, String id,
      U block, Factory<T> entityCtor) {
    return blockEntity(englishName, id, block, entityCtor, false);
  }

  public static <T extends BlockEntity, U extends Block & BlockEntityProvider> BlockEntityRecord<T> blockEntity(
      String englishName, String id,
      U block, Factory<T> entityCtor, boolean customModel) {
    return blockEntity(englishName, id, block, new FabricItemSettings(), entityCtor, customModel);
  }

  public static <T extends BlockEntity, U extends Block & BlockEntityProvider> BlockEntityRecord<T> blockEntity(
      String englishName, String id,
      U block, FabricItemSettings itemSettings, Factory<T> entityCtor, boolean customModel) {
    Identifier identifier = new Identifier(TotalWar.MODID, id);
    BlockItem blockItem = new BlockItem(block, itemSettings);
    Registry.register(Registries.BLOCK, identifier, block);
    Registry.register(Registries.ITEM, identifier, (Item) blockItem);
    BlockEntityType<T> blockEntityType = Registry.register(Registries.BLOCK_ENTITY_TYPE, identifier,
        FabricBlockEntityTypeBuilder.create(entityCtor, blockItem.getBlock()).build());
    BlockEntityRecord<T> record = new BlockEntityRecord<>(blockEntityType, blockItem, identifier, englishName,
        customModel);
    blockEntities.add(record);
    return record;
  }

  public static void init() {
  }

  private TWBlockEntities() {
  }

  public static record BlockEntityRecord<T extends BlockEntity>(BlockEntityType<T> blockEntity, BlockItem block,
      Identifier identifier, String englishName, boolean customModel) {
  }
}

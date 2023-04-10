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

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

/**
 * Blocks registration static class
 */
public abstract class TWBlocks {
  public static final List<BlockRecord> blocks = new ArrayList<>();

  public static final BlockRecord REINFORCED_CONCRETE = block("Reinforced Concrete", "reinforced_concrete",
      FabricBlockSettings.of(Material.STONE).strength(6f, 12f).requiresTool());

  public static BlockRecord block(String englishName, String id, FabricBlockSettings blockSettings) {
    return block(englishName, id, blockSettings, false);
  }

  public static BlockRecord block(String englishName, String id, FabricBlockSettings blockSettings,
      boolean customModel) {
    return block(englishName, id, blockSettings, new FabricItemSettings(), customModel);
  }

  public static BlockRecord block(String englishName, String id, FabricBlockSettings blockSettings,
      FabricItemSettings itemSettings, boolean customModel) {
    return block(englishName, id, new Block(blockSettings), itemSettings, customModel);
  }

  public static BlockRecord block(String englishName, String id, Block block, FabricItemSettings itemSettings,
      boolean customModel) {
    Identifier identifier = new Identifier(TotalWar.MODID, id);
    BlockItem blockItem = new BlockItem(block, itemSettings);
    Registry.register(Registries.BLOCK, identifier, block);
    Registry.register(Registries.ITEM, identifier, (Item) blockItem);
    BlockRecord record = new BlockRecord(blockItem, identifier, englishName, customModel);
    blocks.add(record);
    return record;
  }

  public static void init() {
  }

  private TWBlocks() {
  }

  public static record BlockRecord(BlockItem block, Identifier identifier, String englishName, boolean customModel) {
  }
}

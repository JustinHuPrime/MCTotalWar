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

package ca.zootron.total_war.datagen;

import java.util.concurrent.CompletableFuture;

import ca.zootron.total_war.TWBlockEntities;
import ca.zootron.total_war.TWBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.registry.tag.BlockTags;

public class TWBlockTagProvider extends FabricTagProvider.BlockTagProvider {
  public TWBlockTagProvider(FabricDataOutput output, CompletableFuture<WrapperLookup> completableFuture) {
    super(output, completableFuture);
  }

  @Override
  protected void configure(WrapperLookup arg) {
    // blocks
    getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(TWBlocks.REINFORCED_CONCRETE.block().getBlock());
    getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).add(TWBlocks.REINFORCED_CONCRETE.block().getBlock());
    getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(TWBlocks.SLAG.block().getBlock());
    getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).add(TWBlocks.SLAG.block().getBlock());
    getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(TWBlocks.ORE_ROCK_IRON.block().getBlock());
    getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).add(TWBlocks.ORE_ROCK_IRON.block().getBlock());
    getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(TWBlocks.ORE_ROCK_COPPER.block().getBlock());
    getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).add(TWBlocks.ORE_ROCK_COPPER.block().getBlock());
    getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(TWBlocks.ORE_ROCK_TIN.block().getBlock());
    getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).add(TWBlocks.ORE_ROCK_TIN.block().getBlock());

    // block entities
    getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(TWBlockEntities.RESISTOR.block().getBlock());
    getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).add(TWBlockEntities.RESISTOR.block().getBlock());
    getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(TWBlockEntities.LV_CABLE_COPPER.block().getBlock());
    getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).add(TWBlockEntities.LV_CABLE_COPPER.block().getBlock());
    getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(TWBlockEntities.MINER.block().getBlock());
    getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).add(TWBlockEntities.MINER.block().getBlock());
  }
}

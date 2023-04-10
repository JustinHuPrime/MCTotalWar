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

import ca.zootron.total_war.TWBlockEntities;
import ca.zootron.total_war.TWBlocks;
import ca.zootron.total_war.TWItems;
import ca.zootron.total_war.TWBlockEntities.BlockEntityRecord;
import ca.zootron.total_war.TWBlocks.BlockRecord;
import ca.zootron.total_war.TWItems.ItemRecord;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

/**
 * Model generator
 */
public class TWModelProvider extends FabricModelProvider {
	public TWModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
		for (BlockRecord block : TWBlocks.blocks) {
			blockStateModelGenerator.registerSimpleCubeAll(block.block().getBlock());
		}
		for (BlockEntityRecord<?> blockEntity : TWBlockEntities.blockEntities) {
			blockStateModelGenerator.registerSimpleCubeAll(blockEntity.block().getBlock());
		}
	}

	@Override
	public void generateItemModels(ItemModelGenerator itemModelGenerator) {
		for (ItemRecord item : TWItems.items) {
			itemModelGenerator.register(item.item(), Models.GENERATED);
		}
	}
}

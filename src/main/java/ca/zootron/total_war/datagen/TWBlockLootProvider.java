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

import ca.zootron.total_war.TWBlocks;
import ca.zootron.total_war.TWBlocks.BlockRecord;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;

public class TWBlockLootProvider extends FabricBlockLootTableProvider {
  public TWBlockLootProvider(FabricDataOutput dataOutput) {
    super(dataOutput);
  }

  @Override
  public void generate() {
    for (BlockRecord block : TWBlocks.blocks) {
      addDrop(block.block().getBlock());
    }
    addDrop(TWBlocks.REINFORCED_CONCRETE.block().getBlock(), drops(TWBlocks.REINFORCED_CONCRETE.block()));
  }
}

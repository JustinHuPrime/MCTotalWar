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

package ca.zootron.total_war.blocks;

import ca.zootron.total_war.TWBlockEntities;
import ca.zootron.total_war.blockentities.ResistorBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ResistorBlock extends Block implements BlockEntityProvider {
  public ResistorBlock(Settings settings) {
    super(settings);
  }

  @Override
  public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
    return new ResistorBlockEntity(pos, state);
  }

  @Override
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state,
      BlockEntityType<T> type) {
    if (TWBlockEntities.RESISTOR.blockEntity() != type) {
      return null;
    }

    return (world_, pos, state_, resistor) -> ResistorBlockEntity.tick(world_, pos, state_,
        (ResistorBlockEntity) resistor);
  }
}

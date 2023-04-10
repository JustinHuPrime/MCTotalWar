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

import ca.zootron.total_war.blockentities.LVCableCopperBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.ConnectingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;

public class LVCableCopperBlock extends ConnectingBlock implements BlockEntityProvider {
  public static final BooleanProperty NORTH = Properties.NORTH;
  public static final BooleanProperty EAST = Properties.EAST;
  public static final BooleanProperty SOUTH = Properties.SOUTH;
  public static final BooleanProperty WEST = Properties.WEST;
  public static final BooleanProperty UP = Properties.UP;
  public static final BooleanProperty DOWN = Properties.DOWN;

  public LVCableCopperBlock(Settings settings) {
    super(1f / 16f, settings);
    setDefaultState(getDefaultState().with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false)
        .with(UP, false).with(DOWN, false));
  }

  @Override
  public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
    return new LVCableCopperBlockEntity(pos, state);
  }

  @Override
  protected void appendProperties(Builder<Block, BlockState> builder) {
    builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
  }
}

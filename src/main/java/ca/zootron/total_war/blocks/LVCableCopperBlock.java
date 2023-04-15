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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.zootron.total_war.TWBlockEntities;
import ca.zootron.total_war.TotalWar;
import ca.zootron.total_war.blockentities.LVCableCopperBlockEntity;
import ca.zootron.total_war.logistics.energy.EnergyNetComponent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.ConnectingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class LVCableCopperBlock extends ConnectingBlock implements BlockEntityProvider {
  public static final Logger LOGGER = LoggerFactory.getLogger(TotalWar.MODID);

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

  @Override
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state,
      BlockEntityType<T> type) {
    if (TWBlockEntities.LV_CABLE_COPPER.blockEntity() != type) {
      return null;
    }

    return (world_, pos, state_, resistor) -> LVCableCopperBlockEntity.tick(world_, pos, state_,
        (LVCableCopperBlockEntity) resistor);
  }

  @Override
  public BlockState getPlacementState(ItemPlacementContext ctx) {
    return this.getDefaultState().with(NORTH,
        ctx.getWorld().getBlockEntity(ctx.getBlockPos().north()) instanceof EnergyNetComponent)
        .with(EAST,
            ctx.getWorld().getBlockEntity(ctx.getBlockPos().east()) instanceof EnergyNetComponent)
        .with(SOUTH,
            ctx.getWorld().getBlockEntity(ctx.getBlockPos().south()) instanceof EnergyNetComponent)
        .with(WEST,
            ctx.getWorld().getBlockEntity(ctx.getBlockPos().west()) instanceof EnergyNetComponent)
        .with(UP,
            ctx.getWorld().getBlockEntity(ctx.getBlockPos().up()) instanceof EnergyNetComponent)
        .with(DOWN,
            ctx.getWorld().getBlockEntity(ctx.getBlockPos().down()) instanceof EnergyNetComponent);
  }

  @Override
  public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState,
      WorldAccess world, BlockPos pos, BlockPos neighborPos) {
    switch (direction) {
      case NORTH: {
        return state.with(NORTH, world.getBlockEntity(neighborPos) instanceof EnergyNetComponent);
      }
      case EAST: {
        return state.with(EAST, world.getBlockEntity(neighborPos) instanceof EnergyNetComponent);
      }
      case SOUTH: {
        return state.with(SOUTH, world.getBlockEntity(neighborPos) instanceof EnergyNetComponent);
      }
      case WEST: {
        return state.with(WEST, world.getBlockEntity(neighborPos) instanceof EnergyNetComponent);
      }
      case UP: {
        return state.with(UP, world.getBlockEntity(neighborPos) instanceof EnergyNetComponent);
      }
      case DOWN: {
        return state.with(DOWN, world.getBlockEntity(neighborPos) instanceof EnergyNetComponent);
      }
    }

    LOGGER.warn("invalid neighbour direction {} for neighbour position {} for cable at {}", direction, neighborPos,
        pos);
    return state;
  }
}

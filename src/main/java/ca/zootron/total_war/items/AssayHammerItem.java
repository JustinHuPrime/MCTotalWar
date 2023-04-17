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

package ca.zootron.total_war.items;

import java.util.Objects;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionTypes;

public class AssayHammerItem extends AbstractOreScannerItem {
  public AssayHammerItem(Settings settings) {
    super(settings);
  }

  @Override
  public ActionResult useOnBlock(ItemUsageContext context) {
    World world = context.getWorld();
    if (!world.isClient) {
      BlockState hitBlock = world.getBlockState(context.getBlockPos());
      if (Objects.equals(world.getDimensionKey().getValue(), DimensionTypes.THE_NETHER.getValue())) {
        // nether
        if (hitBlock.isOf(Blocks.NETHERRACK)) {
          doScan(world, context.getPlayer(), context.getStack(), context.getHand());
        }
      } else if (Objects.equals(world.getDimensionKey().getValue(), DimensionTypes.THE_END.getValue())) {
        // the end
        if (hitBlock.isOf(Blocks.END_STONE)) {
          doScan(world, context.getPlayer(), context.getStack(), context.getHand());
        }
      } else {
        // overworld, default
        if (hitBlock.isIn(BlockTags.STONE_ORE_REPLACEABLES) || hitBlock.isIn(BlockTags.DEEPSLATE_ORE_REPLACEABLES)
            || hitBlock.isOf(Blocks.BEDROCK)) {
          doScan(world, context.getPlayer(), context.getStack(), context.getHand());
        }
      }
    }
    return ActionResult.SUCCESS;
  }

  private void doScan(World world, PlayerEntity user, ItemStack stack, Hand hand) {
    doScan((ServerWorld) world, user);
    stack.damage(1, user, (PlayerEntity user_) -> {
      user_.sendToolBreakStatus(hand);
    });
  }
}

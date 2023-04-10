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

import ca.zootron.total_war.TotalWar;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import vazkii.patchouli.api.PatchouliAPI;

/**
 * Guidebook - created manually to be able to customize placement in creative
 * tab
 */
public class GuidebookItem extends Item {
  public GuidebookItem(Settings settings) {
    super(settings);
  }

  @Override
  public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
    if (world.isClient()) {
      PatchouliAPI.get().openBookGUI(new Identifier(TotalWar.MODID, "guidebook"));
    }
    return TypedActionResult.success(user.getStackInHand(hand), false);
  }
}

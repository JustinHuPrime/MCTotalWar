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

import ca.zootron.total_war.oregen.OreMap;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;

public abstract class AbstractOreScannerItem extends Item {
  public AbstractOreScannerItem(Settings settings) {
    super(settings);
  }

  protected void doScan(ServerWorld world, PlayerEntity user) {
    Item ore = OreMap.getOre(world, user.getBlockPos());
    int yieldMultiplier = OreMap.getYield((ServerWorld) world, user.getBlockPos());
    user.sendMessage(Text.translatable("hud.total_war.ore_scanner_message", yieldMultiplier, ore.getName()), true);
  }
}

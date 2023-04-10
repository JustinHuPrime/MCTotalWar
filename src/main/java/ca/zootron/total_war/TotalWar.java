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

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.zootron.total_war.TWBlocks.BlockRecord;
import ca.zootron.total_war.TWItems.ItemRecord;

/**
 * Main entrypoint
 */
public class TotalWar implements ModInitializer {
  public static final String MODID = "total_war";

  public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

  public static final ItemGroup ITEM_GROUP = FabricItemGroup.builder(new Identifier(TotalWar.MODID, "group"))
      .icon(() -> new ItemStack(TWItems.CANNON_SHELL_EMPTY.item())).build();

  @Override
  public void onInitialize() {
    LOGGER.info("Initializing {}", MODID);

    TWItems.init();
    TWBlocks.init();

    ItemGroupEvents.modifyEntriesEvent(ITEM_GROUP).register(content -> {
      content.add(TWItems.GUIDEBOOK.item());
      for (BlockRecord block : TWBlocks.blocks) {
        content.add(block.block());
      }
      for (ItemRecord item : TWItems.items) {
        if (item != TWItems.GUIDEBOOK) {
          content.add(item.item());
        }
      }
    });

    LOGGER.info("Initialized {}", MODID);
  }
}

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

import java.util.ArrayList;
import java.util.List;

import ca.zootron.total_war.items.GuidebookItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

/**
 * Items registration static class
 */
public abstract class TWItems {
  public static final List<ItemRecord> items = new ArrayList<>();

  public static final ItemRecord GUIDEBOOK = item("Total War Guidebook", "guidebook",
      new GuidebookItem(new FabricItemSettings()));

  public static final ItemRecord CANNON_SHELL = item("Cannon Shell (Empty)", "cannon_shell_empty",
      new FabricItemSettings());

  public static final ItemGroup ITEM_GROUP = FabricItemGroup.builder(new Identifier(TotalWar.MODID, "group"))
      .icon(() -> new ItemStack(CANNON_SHELL.item())).build();
  static {
    ItemGroupEvents.modifyEntriesEvent(ITEM_GROUP).register(content -> {
      for (ItemRecord item : items) {
        content.add(item.item());
      }
    });
  }

  private static ItemRecord item(String englishName, String id, FabricItemSettings settings) {
    return item(englishName, id, new Item(settings));
  }

  private static ItemRecord item(String englishName, String id, Item item) {
    Registry.register(Registries.ITEM, new Identifier(TotalWar.MODID, id), item);
    ItemRecord record = new ItemRecord(item, englishName);
    items.add(record);
    return record;
  }

  public static void init() {
  }

  private TWItems() {
  }

  public static record ItemRecord(Item item, String englishName) {
  }
}

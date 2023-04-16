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

import ca.zootron.total_war.items.CreativeOreFinder;
import ca.zootron.total_war.items.CreativeOreScanner;
import ca.zootron.total_war.items.GuidebookItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
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

  public static final ItemRecord CREATIVE_ORE_SCANNER = item("Creative Ore Scanner", "ore_scanner_creative",
      new CreativeOreScanner(new FabricItemSettings()));
  public static final ItemRecord CREATIVE_ORE_FINDER = item("Creative Ore Finder", "ore_finder_creative",
      new CreativeOreFinder(new FabricItemSettings()), true);

  public static final ItemRecord CANNON_SHELL_EMPTY = item("Cannon Shell (Empty)", "cannon_shell_empty");
  public static final ItemRecord CANNON_SHELL_SHRAPNEL = item("Cannon Shell (Shrapnel)", "cannon_shell_shrapnel");
  public static final ItemRecord CANNON_SHELL_EXPLOSIVE = item("Cannon Shell (Explosive)", "cannon_shell_explosive");
  public static final ItemRecord CANNON_SHELL_GAS = item("Cannon Shell (Gas)", "cannon_shell_gas");
  public static final ItemRecord CANNON_SHELL_SMOKE = item("Cannon Shell (Smoke)", "cannon_shell_smoke");

  public static final ItemRecord COPPER_NUGGET = item("Copper Nugget", "copper_nugget");

  private static ItemRecord item(String englishName, String id) {
    return item(englishName, id, false);
  }

  private static ItemRecord item(String englishName, String id, boolean customModel) {
    return item(englishName, id, new FabricItemSettings(), customModel);
  }

  private static ItemRecord item(String englishName, String id, FabricItemSettings settings, boolean customModel) {
    return item(englishName, id, new Item(settings), customModel);
  }

  private static ItemRecord item(String englishName, String id, Item item) {
    return item(englishName, id, item, false);
  }

  private static ItemRecord item(String englishName, String id, Item item, boolean customModel) {
    Registry.register(Registries.ITEM, new Identifier(TotalWar.MODID, id), item);
    ItemRecord record = new ItemRecord(item, englishName, customModel);
    items.add(record);
    return record;
  }

  public static void init() {
  }

  private TWItems() {
  }

  public static record ItemRecord(Item item, String englishName, boolean customModel) {
  }
}

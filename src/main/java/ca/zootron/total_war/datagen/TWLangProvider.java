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

import ca.zootron.total_war.TWBlockEntities;
import ca.zootron.total_war.TWBlocks;
import ca.zootron.total_war.TWItems;
import ca.zootron.total_war.TotalWar;
import ca.zootron.total_war.TWBlockEntities.BlockEntityRecord;
import ca.zootron.total_war.TWBlocks.BlockRecord;
import ca.zootron.total_war.TWItems.ItemRecord;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

/**
 * Translation mapper
 */
public class TWLangProvider extends FabricLanguageProvider {
  public TWLangProvider(FabricDataOutput dataOutput) {
    super(dataOutput, "en_us");
  }

  @Override
  public void generateTranslations(TranslationBuilder translationBuilder) {
    // guidebook
    translationBuilder.add("book.total_war.landing_text", "A tech and military mod for Minecraft");
    translationBuilder.add("book.total_war.subtitle", "For Total War version ${version}");

    // ore scanner and finder
    translationBuilder.add("hud.total_war.ore_scanner_message", "Found %dx vein of %s");
    translationBuilder.add("hud.total_war.ore_finder_message", "Searching for %s");

    // items
    translationBuilder.add(TotalWar.ITEM_GROUP, "Total War");
    for (ItemRecord item : TWItems.items) {
      translationBuilder.add(item.item(), item.englishName());
    }

    // blocks
    for (BlockRecord block : TWBlocks.blocks) {
      translationBuilder.add(block.block().getBlock(), block.englishName());
    }

    // block entity blocks
    for (BlockEntityRecord<?> blockEntity : TWBlockEntities.blockEntities) {
      translationBuilder.add(blockEntity.block().getBlock(), blockEntity.englishName());
    }
  }
}

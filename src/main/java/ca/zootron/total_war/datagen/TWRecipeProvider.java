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

import java.util.function.Consumer;

import ca.zootron.total_war.TWItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class TWRecipeProvider extends FabricRecipeProvider {
  public TWRecipeProvider(FabricDataOutput output) {
    super(output);
  }

  @Override
  public void generate(Consumer<RecipeJsonProvider> exporter) {
    // copper nugget
    TagKey<Item> copperNuggets = TagKey.of(RegistryKeys.ITEM, new Identifier("c", "copper_nuggets"));
    ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, TWItems.COPPER_NUGGET.item(), 9).input(Items.COPPER_INGOT)
        .criterion("has_copper_ingot", conditionsFromItem(Items.COPPER_INGOT))
        .offerTo(exporter);
    ShapedRecipeJsonBuilder.create(RecipeCategory.MISC,
        Items.COPPER_INGOT).pattern("xxx").pattern("xxx").pattern("xxx").input('x', copperNuggets)
        .criterion("has_copper_nugget", conditionsFromItem(TWItems.COPPER_NUGGET.item()))
        .offerTo(exporter);
  }
}

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

import java.util.concurrent.CompletableFuture;

import ca.zootron.total_war.TWItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class TWItemTagProvider extends FabricTagProvider.ItemTagProvider {
  public TWItemTagProvider(FabricDataOutput output, CompletableFuture<WrapperLookup> completableFuture) {
    super(output, completableFuture);
  }

  @Override
  protected void configure(WrapperLookup arg) {
    getOrCreateTagBuilder(TagKey.of(RegistryKeys.ITEM, new Identifier("c", "copper_nuggets")))
        .add(TWItems.COPPER_NUGGET.item());
  }
}

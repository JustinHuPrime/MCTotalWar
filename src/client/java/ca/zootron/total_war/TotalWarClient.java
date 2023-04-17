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

import static ca.zootron.total_war.TotalWar.MODID;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.item.CompassAnglePredicateProvider;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.zootron.total_war.items.CreativeOreFinderItem;

/**
 * Client entrypoint
 */
public class TotalWarClient implements ClientModInitializer {
  public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

  @Override
  public void onInitializeClient() {
    LOGGER.info("Initializing {} client", MODID);

    TWScreens.init();

    // item display stuff: ore finders
    ModelPredicateProviderRegistry.register(TWItems.CREATIVE_ORE_FINDER.item(), new Identifier("angle"),
        new CompassAnglePredicateProvider((ClientWorld world, ItemStack stack, Entity entity) -> {
          return CreativeOreFinderItem.getCoordinates(stack, world);
        }));

    LOGGER.info("Initialized {} client", MODID);
  }
}

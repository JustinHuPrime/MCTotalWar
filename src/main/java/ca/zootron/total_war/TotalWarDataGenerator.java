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

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator.Pack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.zootron.total_war.datagen.TWBlockLootProvider;
import ca.zootron.total_war.datagen.TWBlockTagProvider;
import ca.zootron.total_war.datagen.TWItemTagProvider;
import ca.zootron.total_war.datagen.TWLangProvider;
import ca.zootron.total_war.datagen.TWModelProvider;
import ca.zootron.total_war.datagen.TWRecipeProvider;

/**
 * Data generator entrypoint
 */
public class TotalWarDataGenerator implements DataGeneratorEntrypoint {
    public static final Logger LOGGER = LoggerFactory.getLogger(TotalWar.MODID);

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator gen) {
        LOGGER.info("Initializing {} data generator", TotalWar.MODID);

        Pack pack = gen.createPack();
        pack.addProvider(TWBlockLootProvider::new);
        pack.addProvider(TWBlockTagProvider::new);
        pack.addProvider(TWItemTagProvider::new);
        pack.addProvider(TWLangProvider::new);
        pack.addProvider(TWModelProvider::new);
        pack.addProvider(TWRecipeProvider::new);

        LOGGER.info("Initialized {} data generator", TotalWar.MODID);
    }
}

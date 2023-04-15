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

import ca.zootron.total_war.gui.CreativeGeneratorScreenHandler;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.screen.ScreenHandlerType.Factory;

public abstract class TWScreenHandlers {
  public static final ScreenHandlerType<CreativeGeneratorScreenHandler> CREATIVE_GENERATOR = register(
      "creative_generator", CreativeGeneratorScreenHandler::new);

  private static <T extends ScreenHandler> ScreenHandlerType<T> register(String id, Factory<T> ctor) {
    return Registry.register(Registries.SCREEN_HANDLER, new Identifier(TotalWar.MODID, id),
        new ScreenHandlerType<T>(ctor));
  }

  private TWScreenHandlers() {
  }

  public static void init() {
  }
}

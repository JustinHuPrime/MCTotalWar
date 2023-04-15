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

package ca.zootron.total_war.gui;

import ca.zootron.total_war.TWScreenHandlers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;

public class ResistorScreenHandler extends DynamicHeightBackgroundScreenHandler {
  private final PropertyDelegate propertyDelegate;
  private final ScreenHandlerContext context;

  public ResistorScreenHandler(int syncId, PlayerInventory playerInventory) {
    this(syncId, playerInventory, new ArrayPropertyDelegate(1), ScreenHandlerContext.EMPTY);
  }

  public ResistorScreenHandler(int syncId,
      PlayerInventory playerInventory, PropertyDelegate propertyDelegate, ScreenHandlerContext context) {
    super(TWScreenHandlers.RESISTOR, syncId, 136);

    this.propertyDelegate = propertyDelegate;
    this.addProperties(propertyDelegate);
    this.context = context;

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 9; ++col) {
        this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, menuHeight - 82 + row * 18));
      }
    }

    for (int col = 0; col < 9; ++col) {
      this.addSlot(new Slot(playerInventory, col, 8 + col * 18, menuHeight - 24));
    }
  }

  public int getThroughput() {
    return propertyDelegate.get(0);
  }

  @Override
  public ItemStack quickMove(PlayerEntity var1, int var2) {
    return ItemStack.EMPTY;
  }

  @Override
  public boolean canUse(PlayerEntity player) {
    return true;
  }
}

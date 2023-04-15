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

package ca.zootron.total_war.blockentities;

import org.jetbrains.annotations.Nullable;

import ca.zootron.total_war.TWBlockEntities;
import ca.zootron.total_war.gui.ResistorScreenHandler;
import ca.zootron.total_war.logistics.energy.EnergyNet;
import ca.zootron.total_war.logistics.energy.EnergyNetConsumer;
import ca.zootron.total_war.logistics.energy.EnergyNet.ConnectionDescription;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ResistorBlockEntity extends BlockEntity implements EnergyNetConsumer, NamedScreenHandlerFactory {
  private EnergyNet energyNet;
  private int dissipated;

  private final PropertyDelegate propertyDelegate = new PropertyDelegate() {
    @Override
    public int get(int index) {
      return dissipated;
    }

    @Override
    public void set(int index, int value) {
      dissipated = value;
    }

    @Override
    public int size() {
      return 1;
    }
  };

  public ResistorBlockEntity(BlockPos pos, BlockState state) {
    super(TWBlockEntities.RESISTOR.blockEntity(), pos, state);

    energyNet = null;
    dissipated = 0;
  }

  public static void tick(World world, BlockPos pos, BlockState state, ResistorBlockEntity resistor) {
    if (!world.isClient) {
      if (resistor.energyNet == null) {
        ConnectionDescription connection = EnergyNet.findOrCreateEnergyNet(world, pos);
        resistor.energyNet = connection.energyNet();
        resistor.energyNet.addConsumer(resistor, connection.neighbours());
      }
      resistor.energyNet.tickComponent(world.getTime());

      // no block-specific tick actions; this is a resistor and will void any energy
    }
  }

  @Override
  public @Nullable EnergyNet getEnergyNet() {
    return energyNet;
  }

  @Override
  public void setEnergyNet(EnergyNet energyNet) {
    this.energyNet = energyNet;
  }

  @Override
  public void markRemoved() {
    if (!this.world.isClient && energyNet != null) {
      energyNet.removeComponent(this);
    }

    super.markRemoved();
  }

  @Override
  public void setInput(double energy) {
    // explicitly ignore the input - this is a resistor and will void any energy
    this.dissipated = (int) Math.round(energy);
  }

  @Override
  public void setThroughput(double throughput) {
    // ignored
  }

  @Override
  public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
    return new ResistorScreenHandler(syncId, playerInventory, propertyDelegate);
  }

  @Override
  public Text getDisplayName() {
    return Text.translatable(getCachedState().getBlock().getTranslationKey());
  }
}

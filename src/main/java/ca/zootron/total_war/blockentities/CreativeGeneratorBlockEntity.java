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
import ca.zootron.total_war.gui.CreativeGeneratorScreenHandler;
import ca.zootron.total_war.logistics.energy.EnergyNet;
import ca.zootron.total_war.logistics.energy.EnergyNetProducer;
import ca.zootron.total_war.logistics.energy.EnergyNetTier;
import ca.zootron.total_war.logistics.energy.EnergyNet.ConnectionDescription;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CreativeGeneratorBlockEntity extends BlockEntity implements EnergyNetProducer, NamedScreenHandlerFactory {
  private EnergyNet energyNet;
  private int output;

  private final PropertyDelegate propertyDelegate = new PropertyDelegate() {
    @Override
    public int get(int index) {
      return output;
    }

    @Override
    public void set(int index, int value) {
      output = value;
    }

    @Override
    public int size() {
      return 1;
    }
  };

  public CreativeGeneratorBlockEntity(BlockPos pos, BlockState state) {
    super(TWBlockEntities.CREATIVE_GENERATOR.blockEntity(), pos, state);

    energyNet = null;
    output = (int) EnergyNetTier.LV.throughputLimit;
  }

  public static void tick(World world, BlockPos pos, BlockState state, CreativeGeneratorBlockEntity generator) {
    if (!world.isClient) {
      if (generator.energyNet == null) {
        ConnectionDescription connection = EnergyNet.findOrCreateEnergyNet(world, pos);
        generator.energyNet = connection.energyNet();
        generator.energyNet.addProducer(generator, connection.neighbours());
      }
      generator.energyNet.tickComponent(world.getTime());

      // no block specific actions - this is a creative block
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
  public double getOutput() {
    return output;
  }

  @Override
  public void setOpenCircuit() {
    // ignore this condition - this is a creative block
  }

  @Override
  public void setThroughput(double throughput) {
    // ignore throughput overload - this is a creative block
  }

  @Override
  public void readNbt(NbtCompound nbt) {
    super.readNbt(nbt);
    output = nbt.getInt("output");
  }

  @Override
  protected void writeNbt(NbtCompound nbt) {
    super.writeNbt(nbt);
    nbt.putInt("output", output);
  }

  @Override
  public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
    return new CreativeGeneratorScreenHandler(syncId, playerInventory, propertyDelegate,
        ScreenHandlerContext.create(world, pos));
  }

  @Override
  public Text getDisplayName() {
    return Text.translatable(getCachedState().getBlock().getTranslationKey());
  }
}

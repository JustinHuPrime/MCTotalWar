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
import ca.zootron.total_war.TWBlocks;
import ca.zootron.total_war.logistics.energy.EnergyNet;
import ca.zootron.total_war.logistics.energy.EnergyNetConsumer;
import ca.zootron.total_war.logistics.energy.EnergyNetTier;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MinerBlockEntity extends BlockEntity implements EnergyNetConsumer, NamedScreenHandlerFactory, Inventory {
  public static final double OPERATION_ENERGY_MULTIPLIER = 1;

  private EnergyNet energyNet;
  private double input;

  private double energyLeft;
  ItemStack output;

  public MinerBlockEntity(BlockPos pos, BlockState state) {
    super(TWBlockEntities.MINER.blockEntity(), pos, state);

    energyNet = null;
    input = 0;
    energyLeft = EnergyNetTier.LV.operationEnergy * OPERATION_ENERGY_MULTIPLIER;
    output = ItemStack.EMPTY;
  }

  public static void tick(World world, BlockPos pos, BlockState state, MinerBlockEntity miner) {
    if (!world.isClient) {
      EnergyNetConsumer.tickConsumerNet(world, pos, state, miner);

      if (miner.input > EnergyNetTier.LV.machineUpperLimit) {
        // melt
        world.setBlockState(pos, TWBlocks.SLAG.block().getBlock().getDefaultState());
        if (world.getBlockState(pos.up()).getBlock() == Blocks.AIR) {
          world.setBlockState(pos.up(), Blocks.FIRE.getDefaultState());
        }
        world.playSound(null, pos, SoundEvents.ENTITY_GENERIC_BURN, SoundCategory.BLOCKS, 1f, 0.5f);
        return;
      }

      miner.energyLeft -= miner.input;
      miner.input = 0;

      while (miner.energyLeft <= 0) {
        // TODO add appropriate item to the inventory
        miner.energyLeft += EnergyNetTier.LV.operationEnergy * OPERATION_ENERGY_MULTIPLIER;
      }

      miner.markDirty();
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
  public void setThroughput(double throughput) {
    // ignored
  }

  @Override
  public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'createMenu'");
  }

  @Override
  public Text getDisplayName() {
    return Text.translatable(getCachedState().getBlock().getTranslationKey());
  }

  @Override
  public void setInput(double energy) {
    input = energy;
  }

  @Override
  public void readNbt(NbtCompound nbt) {
    super.readNbt(nbt);
    this.energyLeft = nbt.getDouble("EnergyLeft");
  }

  @Override
  protected void writeNbt(NbtCompound nbt) {
    super.writeNbt(nbt);
    nbt.putDouble("EnergyLeft", energyLeft);
  }

  @Override
  public void clear() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'clear'");
  }

  @Override
  public int size() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'size'");
  }

  @Override
  public boolean isEmpty() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'isEmpty'");
  }

  @Override
  public ItemStack getStack(int var1) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getStack'");
  }

  @Override
  public ItemStack removeStack(int var1, int var2) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'removeStack'");
  }

  @Override
  public ItemStack removeStack(int var1) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'removeStack'");
  }

  @Override
  public void setStack(int var1, ItemStack var2) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'setStack'");
  }

  @Override
  public boolean canPlayerUse(PlayerEntity var1) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'canPlayerUse'");
  }
}

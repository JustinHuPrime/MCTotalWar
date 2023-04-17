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

package ca.zootron.total_war.logistics.energy;

import ca.zootron.total_war.logistics.energy.EnergyNet.ConnectionDescription;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * A component that produces energy
 */
public interface EnergyNetProducer extends EnergyNetComponent {
  /**
   * @return amount of energy (in EU) produced this tick
   */
  double getOutput();

  /**
   * called when there are no consumers
   */
  void setOpenCircuit();

  /**
   * Handle generic energy net per-tick actions (server side only)
   * 
   * @param world    from static tick params
   * @param pos      from static tick params
   * @param state    from static tick params
   * @param consumer from static tick params
   */
  public static void tickConsumerNet(World world, BlockPos pos, BlockState state, EnergyNetProducer consumer) {
    if (consumer.getEnergyNet() == null) {
      ConnectionDescription connection = EnergyNet.findOrCreateEnergyNet(world, pos);
      consumer.setEnergyNet(connection.energyNet());
      connection.energyNet().addProducer(consumer, connection.neighbours());
    }
    consumer.getEnergyNet().tickComponent(world.getTime());
  }
}

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

import org.jetbrains.annotations.Nullable;

/**
 * A generic component (e.g. a cable, a meter, a switch)
 * 
 * @implSpec implementer *MUST* ensure that two equal EnergyNetComponents refer
 *           to the same BlockEntity
 */
public interface EnergyNetComponent {
  /**
   * @return the energy net this component is a part of
   */
  @Nullable
  EnergyNet getEnergyNet();

  /**
   * Set the energy net this component is a part of
   */
  void setEnergyNet(EnergyNet energyNet);

  /**
   * Set the current network throughput
   * 
   * @param throughput current network throughput, in EU
   */
  void setThroughput(double throughput);
}

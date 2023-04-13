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

package ca.zootron.total_war.energy;

public enum EnergyNetTier {
  LV(256),
  MV(2048),
  HV(16384),
  EV(131072),
  SV(Double.POSITIVE_INFINITY, 64, 256, 25600),
  ;

  final public double throughputLimit;
  final public double machineLowerLimit;
  final public double machineUpperLimit;
  final public double operationEnergy;

  private EnergyNetTier(double throughputLimit, double machineLowerLimit, double machineUpperLimit,
      double operationEnergy) {
    this.throughputLimit = throughputLimit;
    this.machineLowerLimit = machineLowerLimit;
    this.machineUpperLimit = machineUpperLimit;
    this.operationEnergy = operationEnergy;
  }

  private EnergyNetTier(double throughputLimit) {
    this(throughputLimit, throughputLimit / 64d, throughputLimit / 16d, throughputLimit / 16d * 100d);
  }
}

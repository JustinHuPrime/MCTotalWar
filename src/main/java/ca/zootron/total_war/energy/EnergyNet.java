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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * energy net controller
 * 
 * if current time according to the ticking thing is not the last ticked time,
 * tick everything in the net
 * 
 * else, you were already ticked
 * 
 * so the energy net ticks first, then all blocks individually tick; the energy
 * net will query each block as to how much they can provide or consume, and
 * then on the block's tick per-block actions happen
 */
public class EnergyNet {
  private List<EnergyNetConsumer> consumers;
  private List<EnergyNetProducer> producers;
  private List<EnergyNetSwitchable> switchables;
  private List<EnergyNetMeter> meters;
  private List<EnergyNetComponent> components;

  private Map<EnergyNetComponent, List<EnergyNetComponent>> connectivity;

  private long currentTime;

  public EnergyNet() {
    this(0);
  }

  private EnergyNet(long currentTime) {
    consumers = new ArrayList<>();
    producers = new ArrayList<>();
    switchables = new ArrayList<>();
    meters = new ArrayList<>();
    components = new ArrayList<>();

    connectivity = new HashMap<>();

    this.currentTime = currentTime;
  }

  public void addComponent(EnergyNetComponent energyNetComponent, List<EnergyNetComponent> neighbours) {
    components.add(energyNetComponent);
    connectivity.put(energyNetComponent, neighbours);
    for (EnergyNetComponent neighbour : neighbours) {
      connectivity.get(neighbour).add(energyNetComponent);
    }
  }

  public void addConsumer(EnergyNetConsumer energyNetComponent, List<EnergyNetComponent> neighbours) {
    consumers.add(energyNetComponent);
    addComponent(energyNetComponent, neighbours);
  }

  public void addProducer(EnergyNetProducer energyNetComponent, List<EnergyNetComponent> neighbours) {
    producers.add(energyNetComponent);
    addComponent(energyNetComponent, neighbours);
  }

  public void addSwitchableConsumerProducer(EnergyNetSwitchable energyNetComponent,
      List<EnergyNetComponent> neighbours) {
    switchables.add(energyNetComponent);
    addComponent(energyNetComponent, neighbours);
  }

  public void addMeter(EnergyNetMeter energyNetComponent, List<EnergyNetComponent> neighbours) {
    meters.add(energyNetComponent);
    addComponent(energyNetComponent, neighbours);
  }

  // TODO: batch removals to just before next active tick
  // performance when melting down 4096 cables is poor
  public void removeComponent(EnergyNetComponent energyNetComponent) {
    consumers.remove(energyNetComponent);
    producers.remove(energyNetComponent);
    switchables.remove(energyNetComponent);
    meters.remove(energyNetComponent);
    components.remove(energyNetComponent);

    for (List<EnergyNetComponent> neighbours : connectivity.values()) {
      neighbours.remove(energyNetComponent);
    }
    List<EnergyNetComponent> disconnected = connectivity.get(energyNetComponent);
    connectivity.remove(energyNetComponent);

    // do connectivity check
    if (disconnected.size() == 0) {
      // this was the last component in the network; goodbye
      return;
    }

    if (disconnected.size() == 1) {
      // this was just a leaf node leaving; still no issues
      return;
    }

    // for each disconnected component, which other disconnected components can it
    // still transitively reach?
    EnergyNetComponent first = disconnected.get(0);
    Set<EnergyNetComponent> firstReachable = reachable(first);
    if (firstReachable.containsAll(disconnected)) {
      // not a disconnection; number of subsets == 1, we're good to go
      return;
    }

    // form subsets - everything a component can reach is in one subset (and ignore
    // the components known to be in a subset)
    List<Set<EnergyNetComponent>> subsets = new ArrayList<>();
    subsets.add(firstReachable);
    makeSubsets: for (int idx = 1; idx < disconnected.size(); ++idx) {
      EnergyNetComponent leader = disconnected.get(idx);
      for (Set<EnergyNetComponent> subset : subsets) {
        if (subset.contains(leader)) {
          // already in a subset - can't start another
          continue makeSubsets;
        }
      }

      // not in a subset
      subsets.add(reachable(leader));
    }

    // split second and later subsets into their own networks
    for (int idx = 1; idx < subsets.size(); ++idx) {
      EnergyNet newNet = new EnergyNet(currentTime);
      Set<EnergyNetComponent> subset = subsets.get(idx);

      // copy over our records of these components
      for (Iterator<EnergyNetConsumer> it = consumers.iterator(); it.hasNext();) {
        EnergyNetConsumer consumer = it.next();
        if (subset.contains(consumer)) {
          newNet.consumers.add(consumer);
        }
      }
      consumers.removeAll(subset);

      for (Iterator<EnergyNetProducer> it = producers.iterator(); it.hasNext();) {
        EnergyNetProducer producer = it.next();
        if (subset.contains(producer)) {
          newNet.producers.add(producer);
        }
      }
      producers.removeAll(subset);

      for (Iterator<EnergyNetSwitchable> it = switchables.iterator(); it.hasNext();) {
        EnergyNetSwitchable switchable = it.next();
        if (subset.contains(switchable)) {
          newNet.switchables.add(switchable);
        }
      }
      switchables.removeAll(subset);

      for (Iterator<EnergyNetMeter> it = meters.iterator(); it.hasNext();) {
        EnergyNetMeter meter = it.next();
        if (subset.contains(meter)) {
          newNet.meters.add(meter);
        }
      }
      meters.removeAll(subset);

      for (Iterator<EnergyNetComponent> it = components.iterator(); it.hasNext();) {
        EnergyNetComponent component = it.next();
        if (subset.contains(component)) {
          newNet.components.add(component);
        }
      }
      components.removeAll(subset);

      for (EnergyNetComponent component : subset) {
        newNet.connectivity.put(component, connectivity.get(component));
        connectivity.remove(component);
      }

      // bind these components to their new net
      for (EnergyNetComponent component : subset) {
        component.setEnergyNet(newNet);
      }
    }
  }

  private Set<EnergyNetComponent> reachable(EnergyNetComponent start) {
    Set<EnergyNetComponent> visited = new HashSet<>();
    Stack<EnergyNetComponent> toVisit = new Stack<>();
    toVisit.add(start);

    while (!toVisit.isEmpty()) {
      EnergyNetComponent node = toVisit.pop();
      if (visited.add(node)) {
        // set must not contain element; if it didn't, we skip it
        toVisit.addAll(connectivity.get(node));
      }
    }

    return visited;
  }

  public void tickComponent(long tickedTime) {
    if (tickedTime > currentTime) {
      currentTime = tickedTime;
      tick();
    }
  }

  private void tick() {
    // calculate production this tick
    double production = 0;
    for (EnergyNetProducer producer : producers) {
      production += producer.getOutput();
    }
    for (EnergyNetSwitchable switchable : switchables) {
      if (switchable.isProducer()) {
        production += switchable.getOutput();
      }
    }

    // check for overloads
    for (EnergyNetComponent component : components) {
      component.setThroughput(production);
    }

    // distribute energy amongst consumers, if any; update meters
    int consumerCount = consumers.size();
    for (EnergyNetSwitchable switchable : switchables) {
      if (switchable.isConsumer()) {
        ++consumerCount;
      }
    }
    if (consumerCount == 0) {
      for (EnergyNetProducer producer : producers) {
        producer.setOpenCircuit();
      }
      for (EnergyNetSwitchable switchable : switchables) {
        if (switchable.isProducer()) {
          switchable.setOpenCircuit();
        }
      }
      for (EnergyNetMeter meter : meters) {
        meter.setRation(Double.POSITIVE_INFINITY);
      }
    } else {
      double ration = production / consumerCount;
      for (EnergyNetConsumer consumer : consumers) {
        consumer.setInput(ration);
      }
      for (EnergyNetSwitchable switchable : switchables) {
        if (switchable.isConsumer()) {
          switchable.setInput(ration);
        }
      }
      for (EnergyNetMeter meter : meters) {
        meter.setRation(ration);
      }
    }
  }

  private void merge(EnergyNet energyNet) {
    consumers.addAll(energyNet.consumers);
    producers.addAll(energyNet.producers);
    switchables.addAll(energyNet.switchables);
    meters.addAll(energyNet.meters);
    components.addAll(energyNet.components);

    connectivity.putAll(energyNet.connectivity);

    currentTime = Math.max(currentTime, energyNet.currentTime);

    energyNet.components.forEach(component -> component.setEnergyNet(this));
  }

  public static ConnectionDescription findOrCreateEnergyNet(World world, BlockPos pos) {
    // find neighbouring nets
    List<BlockPos> neighbours = List.of(pos.north(), pos.south(), pos.east(), pos.west(), pos.up(), pos.down());
    Set<EnergyNet> neighbourNets = new HashSet<>();
    List<EnergyNetComponent> neighbourComponents = new ArrayList<>();
    for (BlockPos neighbourPos : neighbours) {
      BlockEntity entity = world.getBlockEntity(neighbourPos);
      if (entity != null && entity instanceof EnergyNetComponent energyNetComponent) {
        EnergyNet neighbourNet = energyNetComponent.getEnergyNet();
        if (neighbourNet != null) {
          neighbourNets.add(neighbourNet);
          neighbourComponents.add(energyNetComponent);
        }
      }
    }

    // decide which to join
    if (neighbourNets.size() == 0) {
      // new net
      return new ConnectionDescription(new EnergyNet(), neighbourComponents);
    } else if (neighbourNets.size() == 1) {
      // only one neighbouring net
      return new ConnectionDescription(neighbourNets.iterator().next(), neighbourComponents);
    } else {
      // must merge multiple nets - merge the nets
      // NOTE: could also merge the remaining nets into the first net
      EnergyNet energyNet = new EnergyNet();
      for (EnergyNet neighbourNet : neighbourNets) {
        energyNet.merge(neighbourNet);
      }
      return new ConnectionDescription(energyNet, neighbourComponents);
    }
  }

  public static record ConnectionDescription(EnergyNet energyNet, List<EnergyNetComponent> neighbours) {
  }
}

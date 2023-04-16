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

package ca.zootron.total_war.oregen;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import ca.zootron.total_war.TWBlocks;
import net.minecraft.item.Item;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public abstract class OreMap {
  private static long HASH_CONSTANT = 2654435769l; // 2^32 / phi

  public static final List<OreRecord> ORES = new ArrayList<>();
  static {
    ORES.add(new OreRecord(TWBlocks.ORE_ROCK_IRON.block(), 200));
    ORES.add(new OreRecord(TWBlocks.ORE_ROCK_COPPER.block(), 100));
    ORES.add(new OreRecord(TWBlocks.ORE_ROCK_TIN.block(), 25));
  }
  private static final double TOTAL_ORE_WEIGHT = (new Supplier<Double>() {
    @Override
    public Double get() {
      double acc = 0;
      for (OreRecord record : ORES) {
        acc += record.weight();
      }
      return acc;
    }
  }).get();

  private static final List<YieldRecord> YIELDS = new ArrayList<>();
  static {
    YIELDS.add(new YieldRecord(1, 80));
    YIELDS.add(new YieldRecord(2, 5));
    YIELDS.add(new YieldRecord(3, 5));
    YIELDS.add(new YieldRecord(5, 5));
    YIELDS.add(new YieldRecord(10, 5));
  }
  private static final double TOTAL_YIELD_WEIGHT = (new Supplier<Double>() {
    @Override
    public Double get() {
      double acc = 0;
      for (YieldRecord record : YIELDS) {
        acc += record.weight();
      }
      return acc;
    }
  }).get();

  public static Item getOre(ServerWorld world, BlockPos pos) {
    // get a random fraction based on the chunk
    double fraction = hashFractionType(world, pos);

    // find the ore for that chunk
    for (OreRecord ore : ORES) {
      fraction -= ore.weight() / TOTAL_ORE_WEIGHT;
      if (fraction <= 0) {
        return ore.item();
      }
    }

    // edge case (floating point error?) - return last ore
    return ORES.get(ORES.size() - 1).item();
  }

  public static int getYield(ServerWorld world, BlockPos pos) {
    // get a random fraction based on the chunk (using second hash method)
    double fraction = hashFractionYield(world, pos);

    // find the yield for that chunk
    for (YieldRecord yieldRecord : YIELDS) {
      fraction -= yieldRecord.weight() / TOTAL_YIELD_WEIGHT;
      if (fraction <= 0) {
        return yieldRecord.yieldMultiplier();
      }
    }

    // edge case
    return YIELDS.get(YIELDS.size() - 1).yieldMultiplier();
  }

  /**
   * A variant on Knuth's multiplicative hash
   * 
   * Hashes an integer by multiplying it by the hash constant, then reversing the
   * bits and multiplying it again
   * 
   * Combines the two chunk hashes asymmetrically
   * 
   * Also mixes in the world seed
   * 
   * Finally, converts hash into a fraction between 0 and one
   */
  private static double hashFractionType(ServerWorld world, BlockPos pos) {
    return ((double) hashPosition(world, pos)) / ((double) 0x100000000l);
  }

  /**
   * A variant on the type hash for calculating yields
   */
  private static double hashFractionYield(ServerWorld world, BlockPos pos) {
    long hash = hashPosition(world, pos);

    hash *= HASH_CONSTANT;
    hash = Long.reverse(hash) >>> 32;

    hash *= HASH_CONSTANT;

    return (double) (hash & 0xFFFFFFFFl) / ((double) 0x100000000l);
  }

  private static long hashPosition(ServerWorld world, BlockPos pos) {
    long chunkX = Integer.toUnsignedLong(pos.getX() >> 4);
    long chunkZ = Integer.toUnsignedLong(pos.getZ() >> 4);

    chunkX *= HASH_CONSTANT;
    chunkZ *= HASH_CONSTANT;

    chunkX = Long.reverse(chunkX) >>> 32;
    chunkZ = Long.reverse(chunkZ) >>> 32;

    chunkX *= HASH_CONSTANT;
    chunkZ *= HASH_CONSTANT;

    long seed = world.getSeed();
    long dimension = Integer.toUnsignedLong(world.getDimensionKey().getValue().hashCode());
    return (seed ^ (seed >>> 32) ^ (dimension * 3) ^ (chunkX * 5) ^ (chunkZ * 7)) & 0xFFFFFFFFl;
  }

  private OreMap() {
  }

  public static record OreRecord(Item item, double weight) {
  }

  public static record YieldRecord(int yieldMultiplier, double weight) {
  }
}

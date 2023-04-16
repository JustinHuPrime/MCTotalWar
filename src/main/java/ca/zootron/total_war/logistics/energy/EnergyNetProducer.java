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

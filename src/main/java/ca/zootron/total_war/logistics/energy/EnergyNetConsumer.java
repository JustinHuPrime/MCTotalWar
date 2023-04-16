package ca.zootron.total_war.logistics.energy;

import ca.zootron.total_war.logistics.energy.EnergyNet.ConnectionDescription;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * A component that consumes energy
 */
public interface EnergyNetConsumer extends EnergyNetComponent {
  /**
   * Called every tick with amount of available energy
   * 
   * @param energy energy that must be consumed this tick
   */
  void setInput(double energy);

  /**
   * Handle generic energy net per-tick actions (server side only)
   * 
   * @param world    from static tick params
   * @param pos      from static tick params
   * @param state    from static tick params
   * @param consumer from static tick params
   */
  public static void tickConsumerNet(World world, BlockPos pos, BlockState state, EnergyNetConsumer consumer) {
    if (consumer.getEnergyNet() == null) {
      ConnectionDescription connection = EnergyNet.findOrCreateEnergyNet(world, pos);
      consumer.setEnergyNet(connection.energyNet());
      connection.energyNet().addConsumer(consumer, connection.neighbours());
    }
    consumer.getEnergyNet().tickComponent(world.getTime());
  }
}

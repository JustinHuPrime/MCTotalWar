package ca.zootron.total_war.logistics.energy;

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
}

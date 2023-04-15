package ca.zootron.total_war.logistics.energy;

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
}

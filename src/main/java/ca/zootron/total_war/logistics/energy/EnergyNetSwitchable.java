package ca.zootron.total_war.logistics.energy;

/**
 * Toggled between being a consumer and a producer
 */
public interface EnergyNetSwitchable extends EnergyNetConsumer, EnergyNetProducer {
  boolean isConsumer();

  default boolean isProducer() {
    return !isConsumer();
  }
}

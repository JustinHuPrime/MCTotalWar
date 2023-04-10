package ca.zootron.total_war;

import static ca.zootron.total_war.TotalWar.MODID;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Client entrypoint
 */
public class TotalWarClient implements ClientModInitializer {
  public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

  @Override
  public void onInitializeClient() {
    LOGGER.info("Initialized {} client", MODID);
  }
}

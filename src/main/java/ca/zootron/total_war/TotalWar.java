package ca.zootron.total_war;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TotalWar implements ModInitializer {
    public static final String MODID = "total_war";

    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initialized {}", MODID);
    }
}

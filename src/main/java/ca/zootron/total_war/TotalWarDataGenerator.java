package ca.zootron.total_war;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TotalWarDataGenerator implements DataGeneratorEntrypoint {
    public static final Logger LOGGER = LoggerFactory.getLogger(TotalWar.MODID);

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator gen) {
        LOGGER.info("Initialized {} data generator", TotalWar.MODID);
    }
}

package ca.zootron.total_war.datagen;

import static ca.zootron.total_war.TotalWar.MODID;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TotalWarDatagenClient implements DataGeneratorEntrypoint {
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        LOGGER.info("Initialized {} datagen client", MODID);
    }
}

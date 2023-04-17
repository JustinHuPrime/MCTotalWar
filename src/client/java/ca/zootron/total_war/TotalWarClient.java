package ca.zootron.total_war;

import static ca.zootron.total_war.TotalWar.MODID;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.item.CompassAnglePredicateProvider;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.zootron.total_war.items.CreativeOreFinderItem;

/**
 * Client entrypoint
 */
public class TotalWarClient implements ClientModInitializer {
  public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

  @Override
  public void onInitializeClient() {
    LOGGER.info("Initializing {} client", MODID);

    TWScreens.init();

    // item display stuff: ore finders
    ModelPredicateProviderRegistry.register(TWItems.CREATIVE_ORE_FINDER.item(), new Identifier("angle"),
        new CompassAnglePredicateProvider((ClientWorld world, ItemStack stack, Entity entity) -> {
          return CreativeOreFinderItem.getCoordinates(stack, world);
        }));

    LOGGER.info("Initialized {} client", MODID);
  }
}

package ca.zootron.total_war.datagen;

import java.util.concurrent.CompletableFuture;

import ca.zootron.total_war.TWItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class TWItemTagProvider extends FabricTagProvider.ItemTagProvider {
  public TWItemTagProvider(FabricDataOutput output, CompletableFuture<WrapperLookup> completableFuture) {
    super(output, completableFuture);
  }

  @Override
  protected void configure(WrapperLookup arg) {
    getOrCreateTagBuilder(TagKey.of(RegistryKeys.ITEM, new Identifier("c", "copper_nuggets")))
        .add(TWItems.COPPER_NUGGET.item());
  }
}

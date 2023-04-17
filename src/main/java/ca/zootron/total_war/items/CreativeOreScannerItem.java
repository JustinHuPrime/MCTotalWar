package ca.zootron.total_war.items;

import ca.zootron.total_war.oregen.OreMap;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class CreativeOreScannerItem extends Item {
  public CreativeOreScannerItem(Settings settings) {
    super(settings);
  }

  @Override
  public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
    if (!world.isClient()) {
      Item ore = OreMap.getOre((ServerWorld) world, user.getBlockPos());
      int yieldMultiplier = OreMap.getYield((ServerWorld) world, user.getBlockPos());
      user.sendMessage(Text.translatable("hud.total_war.ore_scanner_message", yieldMultiplier, ore.getName()), true);
    }
    return TypedActionResult.success(user.getStackInHand(hand), true);
  }
}

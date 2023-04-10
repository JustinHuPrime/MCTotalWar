package ca.zootron.total_war.items;

import ca.zootron.total_war.TotalWar;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import vazkii.patchouli.api.PatchouliAPI;

public class GuidebookItem extends Item {
  public GuidebookItem(Settings settings) {
    super(settings);
  }

  @Override
  public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
    if (world.isClient()) {
      PatchouliAPI.get().openBookGUI(new Identifier(TotalWar.MODID, "guidebook"));
    }
    return TypedActionResult.success(user.getStackInHand(hand), false);
  }
}

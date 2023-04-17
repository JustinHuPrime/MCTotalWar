package ca.zootron.total_war.items;

import java.util.List;

import org.spongepowered.include.com.google.common.base.Objects;

import ca.zootron.total_war.oregen.OreMap;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.World;

public class CreativeOreFinderItem extends Item {
  public static final int SEARCH_RADIUS = 100;

  public CreativeOreFinderItem(Settings settings) {
    super(settings);
  }

  @Override
  public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
    if (!world.isClient()) {
      ItemStack stack = user.getStackInHand(hand);
      int newIndex = user.isSneaking() ? getIndex(stack) - 1 : getIndex(stack) + 1;
      if (newIndex >= OreMap.ORES.size()) {
        newIndex = 0;
      } else if (newIndex < 0) {
        newIndex = OreMap.ORES.size() - 1;
      }
      setIndex(stack, newIndex);
      user.sendMessage(Text.translatable("hud.total_war.ore_finder_message", getOreType(stack).getName()), true);
    }
    return TypedActionResult.success(user.getStackInHand(hand), true);
  }

  @Override
  public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
    tooltip.add(Text.translatable("hud.total_war.ore_finder_message", getOreType(stack).getName()));
  }

  @Override
  public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
    if (!world.isClient) {
      ServerWorld serverWorld = (ServerWorld) world;

      BlockPos searchCentre = new BlockPos(entity.getPos());
      int targetIndex = getIndex(stack);

      // for a square of chunks, centred on the player
      BlockPos closest = null;
      for (int dx = -SEARCH_RADIUS; dx <= SEARCH_RADIUS; ++dx) {
        for (int dz = -SEARCH_RADIUS; dz <= SEARCH_RADIUS; ++dz) {
          BlockPos toSearch = searchCentre.add(dx * 16, 0, dz * 16);
          Item ore = OreMap.getOre(serverWorld, toSearch);
          if (ore == OreMap.ORES.get(targetIndex).item()) {
            // found it!
            if (closest == null
                || closest.getSquaredDistance(searchCentre) > toSearch.getSquaredDistance(searchCentre)) {
              closest = toSearch;
            }
          }
        }
      }

      if (closest == null || Objects.equal(closest, searchCentre)) {
        unsetCoordinates(stack);
      } else {
        setCoordinates(stack, closest);
      }
    }
  }

  private static NbtCompound getNbt(ItemStack stack) {
    NbtCompound nbt = stack.getNbt();
    if (nbt == null) {
      nbt = new NbtCompound();
      stack.setNbt(nbt);
      nbt.putInt("OreIndex", 0);
    }

    return nbt;
  }

  private static int getIndex(ItemStack stack) {
    NbtCompound nbt = getNbt(stack);
    int index = nbt.getInt("OreIndex");
    if (index >= OreMap.ORES.size()) {
      nbt.putInt("OreIndex", 0);
      index = 0;
    }

    return index;
  }

  private static void setIndex(ItemStack stack, int index) {
    NbtCompound nbt = getNbt(stack);
    nbt.putInt("OreIndex", index);
  }

  private static void setCoordinates(ItemStack stack, BlockPos pos) {
    getNbt(stack).put("OrePos", NbtHelper.fromBlockPos(pos));
  }

  private static void unsetCoordinates(ItemStack stack) {
    getNbt(stack).remove("OrePos");
  }

  public static GlobalPos getCoordinates(ItemStack stack, World world) {
    NbtCompound nbt = getNbt(stack);
    return nbt.contains("OrePos")
        ? GlobalPos.create(world.getRegistryKey(), NbtHelper.toBlockPos(nbt.getCompound("OrePos")))
        : null;
  }

  private static Item getOreType(ItemStack stack) {
    return OreMap.ORES.get(getIndex(stack)).item();
  }

  @Override
  public boolean allowNbtUpdateAnimation(PlayerEntity player, Hand hand, ItemStack oldStack, ItemStack newStack) {
    return false;
  }
}

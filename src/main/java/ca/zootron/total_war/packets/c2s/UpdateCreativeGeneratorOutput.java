package ca.zootron.total_war.packets.c2s;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;

public class UpdateCreativeGeneratorOutput {
  public final int output;

  public UpdateCreativeGeneratorOutput(int output) {
    this.output = output;
  }

  public UpdateCreativeGeneratorOutput(PacketByteBuf buf) {
    this.output = buf.readInt();
  }

  public PacketByteBuf serialize() {
    PacketByteBuf buffer = PacketByteBufs.create();
    buffer.writeInt(output);
    return buffer;
  }
}

// Copyright 2023 Justin Hu
//
// This file is part of Total War
//
// Total War is free software: you can redistribute it and/or modify it under
// the terms of the GNU Affero General Public License as published by the Free
// Software Foundation, either version 3 of the License, or (at your option)
// any later version.
//
// This program is distributed in the hope that it will be useful, but WITHOUT
// ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
// FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License
// for more details.
//
// You should have received a copy of the GNU Affero General Public License
// along with Total War. If not, see <https://www.gnu.org/licenses/>.
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package ca.zootron.total_war;

import ca.zootron.total_war.gui.CreativeGeneratorScreenHandler;
import ca.zootron.total_war.packets.c2s.UpdateCreativeGeneratorOutput;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public abstract class TWPackets {
  public static class S2C {
  }

  public static class C2S {
    public static final Identifier UPDATE_CREATIVE_GENERATOR_OUTPUT = new Identifier(TotalWar.MODID,
        "update_creative_generator_output");
    public static final ServerPlayNetworking.PlayChannelHandler ON_UPDATE_CREATIVE_GENERATOR_OUTPUT = (
        MinecraftServer server, ServerPlayerEntity player,
        ServerPlayNetworkHandler handler, PacketByteBuf buf,
        PacketSender responseSender) -> {
      UpdateCreativeGeneratorOutput packet = new UpdateCreativeGeneratorOutput(buf);
      server.execute(() -> {
        if (player.currentScreenHandler instanceof CreativeGeneratorScreenHandler screenHandler) {
          screenHandler.setOutput(packet.output);
        }
      });
    };
    static {
      ServerPlayNetworking.registerGlobalReceiver(UPDATE_CREATIVE_GENERATOR_OUTPUT,
          ON_UPDATE_CREATIVE_GENERATOR_OUTPUT);
    }
  }

  public static void init() {
  }

  private TWPackets() {
  }
}

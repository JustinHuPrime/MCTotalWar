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

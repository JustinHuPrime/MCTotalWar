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

package ca.zootron.total_war.gui;

import ca.zootron.total_war.TWPackets;
import ca.zootron.total_war.gui.widget.TextButtonWidget;
import ca.zootron.total_war.packets.c2s.UpdateCreativeGeneratorOutput;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;

public class CreativeGeneratorScreen extends DynamicHeightBackgroundScreen<CreativeGeneratorScreenHandler> {
  public CreativeGeneratorScreen(CreativeGeneratorScreenHandler handler, PlayerInventory inventory, Text title) {
    super(handler, inventory, title);
  }

  @Override
  protected void init() {
    super.init();
    addDrawableChild(new HalveOutputButtonWidget(x + backgroundWidth / 2 - 30, y + 34));
    addDrawableChild(new DecrementOutputButtonWidget(x + backgroundWidth / 2 - 10, y + 34));
    addDrawableChild(new IncrementOutputButtonWidget(x + backgroundWidth / 2, y + 34));
    addDrawableChild(new DoubleOutputButtonWidget(x + backgroundWidth / 2 + 10, y + 34));
  }

  @Override
  protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
    super.drawForeground(matrices, mouseX, mouseY);

    String output = "Output: " + Integer.toString(handler.getOutput()) + " EU/t";
    textRenderer.draw(matrices, output, (backgroundWidth - textRenderer.getWidth(output)) / 2, 24, 0x404040);
  }

  abstract class ChangeOutputButtonWidget extends TextButtonWidget {
    public ChangeOutputButtonWidget(int x, int y, int width, int height, String symbol) {
      super(x, y, width, height, Text.of(symbol));
    }

    @Override
    public void onPress() {
      ClientPlayNetworking.send(TWPackets.C2S.UPDATE_CREATIVE_GENERATOR_OUTPUT,
          new UpdateCreativeGeneratorOutput(handler.getOutput()).serialize());
    }
  }

  class IncrementOutputButtonWidget extends ChangeOutputButtonWidget {
    public IncrementOutputButtonWidget(int x, int y) {
      super(x, y, 10, 10, "+");
    }

    @Override
    public void onPress() {
      handler.setOutput(handler.getOutput() + 1);
      super.onPress();
    }
  }

  class DoubleOutputButtonWidget extends ChangeOutputButtonWidget {
    public DoubleOutputButtonWidget(int x, int y) {
      super(x, y, 20, 10, "++");
    }

    @Override
    public void onPress() {
      handler.setOutput(handler.getOutput() * 2);
      super.onPress();
    }
  }

  class DecrementOutputButtonWidget extends ChangeOutputButtonWidget {
    public DecrementOutputButtonWidget(int x, int y) {
      super(x, y, 10, 10, "-");
    }

    @Override
    public void onPress() {
      handler.setOutput(handler.getOutput() - 1);
      super.onPress();
    }
  }

  class HalveOutputButtonWidget extends ChangeOutputButtonWidget {
    public HalveOutputButtonWidget(int x, int y) {
      super(x, y, 20, 10, "--");
    }

    @Override
    public void onPress() {
      handler.setOutput(handler.getOutput() / 2);
      super.onPress();
    }
  }
}

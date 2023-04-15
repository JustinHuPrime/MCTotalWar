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

import com.mojang.blaze3d.systems.RenderSystem;

import ca.zootron.total_war.TotalWar;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public abstract class DynamicHeightBackgroundScreen<T extends DynamicHeightBackgroundScreenHandler>
    extends HandledScreen<T> {
  public static final Identifier BACKGROUND = new Identifier(TotalWar.MODID, "textures/gui/background.png");

  public DynamicHeightBackgroundScreen(T handler, PlayerInventory inventory, Text title) {
    super(handler, inventory, title);
  }

  @Override
  protected void init() {
    super.init();
    backgroundHeight = handler.menuHeight;
    playerInventoryTitleY = backgroundHeight - 94;
  }

  @Override
  public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
    renderBackground(matrices);
    super.render(matrices, mouseX, mouseY, delta);
    drawMouseoverTooltip(matrices, mouseX, mouseY);
  }

  @Override
  protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
    RenderSystem.setShader(GameRenderer::getPositionTexProgram);
    RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
    RenderSystem.setShaderTexture(0, BACKGROUND);

    // upper part
    drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight - 83);
    // lower part
    drawTexture(matrices, x, y + backgroundHeight - 83, 0, 173, backgroundWidth, 83);
  }
}

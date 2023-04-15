package ca.zootron.total_war.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public abstract class TextButtonWidget extends ButtonWidget {
  public static final Identifier WIDGETS_TEXTURE = new Identifier("textures/gui/widgets.png");

  public TextButtonWidget(int x, int y, int width, int height, Text message) {
    super(x, y, width, height, message);
  }

  @Override
  public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
    MinecraftClient minecraftClient = MinecraftClient.getInstance();
    RenderSystem.setShader(GameRenderer::getPositionTexProgram);
    RenderSystem.setShaderTexture(0, WIDGETS_TEXTURE);
    RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
    int v = 46;
    if (!this.active) {
      // v += 0 * 20;
    } else if (this.hovered) {
      v += 2 * 20;
    } else {
      v += 1 * 20;
    }
    // upper-left
    this.drawTexture(matrices, this.getX(), this.getY(), 0, v, this.width - 2,
        this.height - 3);
    // right
    this.drawTexture(matrices, this.getX() + this.width - 2, this.getY(), 198, v, 2, this.height - 3);
    // bottom
    this.drawTexture(matrices, this.getX(), this.getY() + this.height - 3, 0, v + 17,
        this.width - 2, 3);
    // bottom-right
    this.drawTexture(matrices, this.getX() + this.width - 2, this.getY() + this.height - 3, 198, v + 17, 2, 3);
    int j = this.active ? 0xFFFFFF : 0xA0A0A0;
    ClickableWidget.drawCenteredText(matrices,
        minecraftClient.textRenderer,
        this.getMessage(), this.getX() + this.width / 2,
        this.getY() + (this.height - 8) / 2, j | MathHelper.ceil(this.alpha * 255.0f) << 24);
  }
}

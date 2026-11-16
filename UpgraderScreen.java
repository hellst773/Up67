package up67.rolesmod.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import up67.rolesmod.RolesMod;
import up67.rolesmod.menu.UpgraderMenu;

public class UpgraderScreen extends AbstractContainerScreen<UpgraderMenu> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(RolesMod.MOD_ID, "textures/gui/upgrader.png");

    public UpgraderScreen(UpgraderMenu menu, net.minecraft.world.entity.player.Inventory inv, Component title) {
        super(menu, inv, title);
        this.imageWidth = 176;
        this.imageHeight = 196;
    }

    @Override
    protected void init() {
        super.init();
        this.addRenderableWidget(Button.builder(Component.literal("ПРОКАЧАТЬ"), b -> {
                    this.minecraft.gameMode.handleInventoryButtonClick(
                            this.menu.containerId, UpgraderMenu.BUTTON_UPGRADE);
                })
                .bounds(this.leftPos + 90, this.topPos + 45, 76, 20)
                .build());
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        // простой фон без кастомной текстуры, если файла текстуры нет - рисуем плашками
        guiGraphics.fill(leftPos, topPos, leftPos + imageWidth, topPos + imageHeight, 0xFFC6C6C6);
        guiGraphics.fill(leftPos + 1, topPos + 1, leftPos + imageWidth - 1, topPos + imageHeight - 1, 0xFF8B8B8B);
        // рамки слотов
        drawSlotFrame(guiGraphics, leftPos + 44, topPos + 35);
        drawSlotFrame(guiGraphics, leftPos + 44, topPos + 65);
    }

    private void drawSlotFrame(GuiGraphics g, int x, int y) {
        g.fill(x - 1, y - 1, x + 17, y + 17, 0xFF373737);
        g.fill(x, y, x + 16, y + 16, 0xFF8B8B8B);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, this.title, 8, 6, 0x404040, false);
        guiGraphics.drawString(this.font, Component.literal("Апгрейдер"), 8, 18, 0x404040, false);
    }
}

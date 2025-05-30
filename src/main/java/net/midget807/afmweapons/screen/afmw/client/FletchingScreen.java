package net.midget807.afmweapons.screen.afmw.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.midget807.afmweapons.screen.afmw.FletchingTableScreenHandler;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.ForgingScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Optional;

@Environment(EnvType.CLIENT)
public class FletchingScreen extends ForgingScreen<FletchingTableScreenHandler> {
    private static final Identifier ERROR_TEXTURE = new Identifier("container/smithing/error");
    private static final Identifier TEXTURE = new Identifier("afmweapons", "textures/gui/container/fletching_table_gui.png");
    private static final Text MISSING_ARROW_TOOLTIP = Text.translatable("container.afmweapons.fletching.missing_arrow_tooltip");
    private static final Text ERROR_TOOLTIP = Text.translatable("container.afmweapons.fletching.error_tooltip");
    private static final Text ADD_ADDITION_TOOLTIP = Text.translatable("container.afmweapons.fletching.add_addition_tooltip");

    public FletchingScreen(FletchingTableScreenHandler handler, PlayerInventory playerInventory, Text title) {
        super(handler, playerInventory, title, TEXTURE);
        this.titleX = 44;
        this.titleY = 15;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        this.renderSlotTooltip(context, mouseX, mouseY);
    }

    private void renderSlotTooltip(DrawContext context, int mouseX, int mouseY) {
        Optional<Text> optional = Optional.empty();
        if (this.hasInvalidRecipe() && this.isPointWithinBounds(65, 46, 28, 21, mouseX, mouseY)) {
            optional = Optional.of(ERROR_TOOLTIP);
        }
        if (this.focusedSlot != null) {
            ItemStack itemStack = this.handler.getSlot(0).getStack();
            ItemStack itemStack2 = this.focusedSlot.getStack();
            if (itemStack.isEmpty()) {
                if (this.focusedSlot.id == 0) {
                    optional = Optional.of(MISSING_ARROW_TOOLTIP);
                }
            } else if ((itemStack.isOf(Items.ARROW) || itemStack.isOf(Items.SPECTRAL_ARROW)) && itemStack2.isEmpty()) {
                if (this.focusedSlot.id == 1 || this.focusedSlot.id == 2) {
                    optional = Optional.of(ADD_ADDITION_TOOLTIP);
                }
            }
        }
        optional.ifPresent(text -> context.drawOrderedTooltip(this.textRenderer, this.textRenderer.wrapLines(text, 115), mouseX, mouseY));
    }

    @Override
    protected void drawInvalidRecipeArrow(DrawContext context, int x, int y) {
        if (this.hasInvalidRecipe()) {
            context.drawTexture(ERROR_TEXTURE, x + 65, y + 66, this.backgroundWidth, 0, 28, 21);
        }
    }

    private boolean hasInvalidRecipe() {
        return this.handler.getSlot(0).hasStack()
                && this.handler.getSlot(1).hasStack()
                && this.handler.getSlot(2).hasStack()
                && !this.handler.getSlot(this.handler.getResultSlotIndex()).hasStack();
    }
}

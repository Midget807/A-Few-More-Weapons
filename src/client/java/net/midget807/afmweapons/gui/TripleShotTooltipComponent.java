package net.midget807.afmweapons.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.midget807.afmweapons.item.afmw.client.TripleShotTooltipData;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
@Environment(EnvType.CLIENT)
public class TripleShotTooltipComponent implements TooltipComponent {
    private static final Identifier BACKGROUND_TEXTURE = new Identifier("afmweapons","container/triple_shot/background");
    private static final int MARGIN_Y = 4;
    private static final int BORDER_WIDTH = 1;
    private static final int WIDTH_PER_COLUMN = 18;
    private static final int HEIGHT_PER_ROW = 20;
    private final DefaultedList<ItemStack> inventory;
    private final int occupancy;
    public TripleShotTooltipComponent(TripleShotTooltipData data) {
        this.inventory = data.getInventory();
        this.occupancy = data.getTripleShotOccupancy();

    }
    @Override
    public int getHeight() {
        return this.getRowsHeight() + MARGIN_Y;
    }

    private int getRowsHeight() {
        return this.getRows() * HEIGHT_PER_ROW + 2;
    }

    private int getRows() {
        return (int)Math.ceil(((double) this.inventory.size() + 1.0) / (double)this.getColumns());
    }

    @Override
    public int getWidth(TextRenderer textRenderer) {
        return this.getColumnsWidth();
    }

    private int getColumnsWidth() {
        return this.getColumns() + WIDTH_PER_COLUMN + 2;
    }

    private int getColumns() {
        return Math.max(2, (int) Math.ceil(Math.sqrt((double) this.inventory.size() + 1.0)));
    }

    @Override
    public void drawItems(TextRenderer textRenderer, int x, int y, DrawContext context) {
        int i = this.getColumns();
        int j = this.getRows();
        boolean bl = this.occupancy >= 64;
        int k = 0;
        context.drawGuiTexture(BACKGROUND_TEXTURE, x, y, WIDTH_PER_COLUMN * 3 + 2, HEIGHT_PER_ROW);
        for (int l = 0; l < 3; l++) {
            int n = x + l * WIDTH_PER_COLUMN + BORDER_WIDTH;
            int o = y + BORDER_WIDTH;
            this.drawSlot(n, o, k++, bl, context, textRenderer);

        }
    }

    private void drawSlot(int x, int y, int index, boolean shouldBlock, DrawContext context, TextRenderer textRenderer) {
        if (index >= this.inventory.size()) {
            this.draw(context, x, y, shouldBlock ? Sprite.BLOCKED_SLOT : Sprite.SLOT);
        } else {
            ItemStack itemStack = (ItemStack) this.inventory.get(index);
            this.draw(context, x, y, Sprite.SLOT);
            context.drawItem(itemStack, x + BORDER_WIDTH, y + BORDER_WIDTH, index);
            context.drawItemInSlot(textRenderer, itemStack, x + BORDER_WIDTH, y + BORDER_WIDTH);
            if (index == 0) {
                HandledScreen.drawSlotHighlight(context, x + BORDER_WIDTH, y + BORDER_WIDTH, 0);
            }
        }
    }

    private void draw(DrawContext context, int x, int y, Sprite sprite) {
        context.drawGuiTexture(sprite.texture, x, y, 0, sprite.width, sprite.height);
    }

    @Environment(EnvType.CLIENT)
    static enum Sprite {
        BLOCKED_SLOT(new Identifier("afmweapons","container/triple_shot/blocked_slot"), 18, 20),
        SLOT(new Identifier("afmweapons", "container/triple_shot/slot"), 18, 20);
        public final Identifier texture;
        public final int width;
        public final int height;
        private Sprite(Identifier texture, int width, int height) {
            this.texture = texture;
            this.width = width;
            this.height = height;
        }
    }
}

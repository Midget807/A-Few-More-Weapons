package net.midget807.afmweapons.item.afmw.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.midget807.afmweapons.AFMWMain;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
@Environment(EnvType.CLIENT)
public class TripleShotTooltipComponent implements TooltipComponent {
    private static final Identifier BACKGROUND_TEXTURE = AFMWMain.id("textures/gui/container/triple_shot.png");
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
        boolean bl = this.occupancy >= 192;
        int k = 0;
        this.draw(context, x, y);
        for (int l = 0; l < 3; l++) {
            int n = x + l * WIDTH_PER_COLUMN + BORDER_WIDTH + 6;
            int o = y + BORDER_WIDTH + 6;
            this.drawSlot(n, o, k++, bl, context, textRenderer);
        }
    }

    private void drawSlot(int x, int y, int index, boolean shouldBlock, DrawContext context, TextRenderer textRenderer) {
        if (index < this.inventory.size()) {
            ItemStack itemStack = (ItemStack) this.inventory.get(index);
            context.drawItem(itemStack, x + BORDER_WIDTH, y + BORDER_WIDTH, index);
            context.drawItemInSlot(textRenderer, itemStack, x + BORDER_WIDTH, y + BORDER_WIDTH);
            if (index == 0) {
                HandledScreen.drawSlotHighlight(context, x + BORDER_WIDTH, y + BORDER_WIDTH, 0);
            }
        }
    }

    private void draw(DrawContext context, int x, int y) {
        context.drawTexture(BACKGROUND_TEXTURE, x, y, 0, 0, 0, 68, 32, 68, 32);
    }

    @Environment(EnvType.CLIENT)
    static enum Sprite {
        SLOT(0, 0, 18, 20),
        BLOCKED_SLOT(0, 40, 18, 20),
        BORDER_VERTICAL(0, 18, 1, 20),
        BORDER_HORIZONTAL_TOP(0, 20, 18, 1),
        BORDER_HORIZONTAL_BOTTOM(0, 60, 18, 1),
        BORDER_CORNER_TOP(0, 20, 1, 1),
        BORDER_CORNER_BOTTOM(0, 60, 1, 1);

        public final int width;
        public final int height;
        public final int u;
        public final int v;
        private Sprite(int width, int height, int u, int v) {
            this.width = width;
            this.height = height;
            this.u = u;
            this.v = v;
        }
    }
}

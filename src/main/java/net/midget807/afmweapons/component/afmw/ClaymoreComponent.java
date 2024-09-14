package net.midget807.afmweapons.component.afmw;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.CommonTickingComponent;
import net.midget807.afmweapons.component.ModComponents;
import net.midget807.afmweapons.datagen.ModItemTagProvider;
import net.midget807.afmweapons.item.ModItems;
import net.midget807.afmweapons.item.afmw.ClaymoreItem;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ClaymoreComponent implements AutoSyncedComponent, CommonTickingComponent {
    // TODO: 9/09/2024 Add deflection enchantment to increase the timeframe for a parry
    private final PlayerEntity player;
    private boolean blocking = false;
    public boolean hasBlocked = false;
    public int parryCharge = 0;
    private boolean hasParried = false;
    public static final int PARRY_MAX_CHARGE = 400; //player.getMainHandStack.getEnchantment level for deflection > 0 ? 400 : 200
    public static final int PARRY_TICK_COST = 40;
    public static final int PARRY_COLOR = 0x5555FF;
    public static final Vec3i PARRY_BAR_COLOR = new Vec3i(85, 85, 255);
    public float parryDamage;
    public float damageBlocked;

    public ClaymoreComponent(PlayerEntity player) {
        this.player = player;
    }
    public static ClaymoreComponent get(@NotNull PlayerEntity player) {
        return ModComponents.CLAYMORE_COMPONENT.get(player);
    }
    public void sync() {
        ModComponents.CLAYMORE_COMPONENT.sync(this.player);
    }
    public float getParryDamage() {
        return parryDamage;
    }

    public void setParryDamage() {
        this.parryDamage = Math.min((float) (this.player.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE) + getDamageBlocked()), (float) (this.player.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE) * 2));;
    }

    public void setDamageBlocked(float amount) {
        this.damageBlocked = amount;
        this.setParryDamage();
    }

    public float getDamageBlocked() {
        return damageBlocked;
    }

    public boolean isBlocking() {
        if (!this.player.getWorld().isClient && (!this.player.getMainHandStack().isIn(ModItemTagProvider.CLAYMORES) || !this.player.isUsingItem())) {
            this.setBlocking(false);
            this.sync();
        }
        return blocking;
    }

    public void setBlocking(boolean blocking) {
        this.blocking = blocking;
    }

    public boolean isHasBlocked() {
        return hasBlocked;
    }

    public void setHasBlocked(boolean hasBlocked) {
        this.hasBlocked = hasBlocked;
    }

    public boolean isHasParried() {
        return hasParried;
    }

    public void setHasParried(boolean hasParried) {
        this.hasParried = hasParried;
    }

    public boolean canParry() {
        return this.parryCharge > 0 && hasBlocked;
    }
    public float getParryPercent() {
        return (float) this.parryCharge / PARRY_MAX_CHARGE;
    }

    public void setParryCharge(int parryCharge) {
        this.parryCharge = parryCharge;
        this.sync();
    }

    /*
    * Is used to differentiate which ability the Amarite sword should use based on its enchantments
    * */
    public boolean canUseWhichAbility() {
        ItemStack stack = this.player.getMainHandStack();
        return this.canParry();
    }
    public void useAbility() {
        ItemStack stack = this.player.getMainHandStack();
        if (this.canParry()) {
            this.useParry();
        }
    }
    //=====

    private void useParry() {
        if (this.canParry()) {
            this.sync();
        }
    }
    public int getChargeTint(ItemStack stack) {
        Vec3i color;
        float percent;
        //wrap in if for enchantment
        color = PARRY_BAR_COLOR;
        percent = Math.min(this.getParryPercent(), 1.0f);
        //===
        percent = Math.max(0.0f, percent);
        int r = (int) (255.0f - percent * (float) (255 - color.getX()));
        int g = (int) (255.0f - percent * (float) (255 - color.getY()));
        int b = (int) (255.0f - percent * (float) (255 - color.getZ()));
        return MathHelper.packRgb(r, g, b);
    }

    @Override
    public void tick() {
        ItemStack stack = this.player.getMainHandStack();
        boolean isClaymore = this.player.isAlive() && !this.player.isDead() && !this.player.isRemoved() && stack.isIn(ModItemTagProvider.CLAYMORES);
        if (
                this.player.getItemCooldownManager().isCoolingDown(ModItems.WOODEN_CLAYMORE) ||
                this.player.getItemCooldownManager().isCoolingDown(ModItems.STONE_CLAYMORE) ||
                this.player.getItemCooldownManager().isCoolingDown(ModItems.IRON_CLAYMORE) ||
                this.player.getItemCooldownManager().isCoolingDown(ModItems.GOLDEN_CLAYMORE) ||
                this.player.getItemCooldownManager().isCoolingDown(ModItems.DIAMOND_CLAYMORE) ||
                this.player.getItemCooldownManager().isCoolingDown(ModItems.NETHERITE_CLAYMORE)
        ) {
            this.setBlocking(false);
            this.sync();
        }
        if (!this.isHasParried() && parryCharge <= 0 && this.hasBlocked) {
            this.setHasParried(false);
            this.parryCharge = -40;
            this.setHasBlocked(false);
            for (int i = 0; i <= 5; i++) {
                this.player.getItemCooldownManager().set(
                        List.of(
                                ModItems.WOODEN_CLAYMORE,
                                ModItems.STONE_CLAYMORE,
                                ModItems.IRON_CLAYMORE,
                                ModItems.GOLDEN_CLAYMORE,
                                ModItems.DIAMOND_CLAYMORE,
                                ModItems.NETHERITE_CLAYMORE
                        ).get(i), 25
                );
            }
            this.sync();
        }
        if (this.parryCharge >= 0) {
            this.parryCharge -= PARRY_TICK_COST;
        }
        if (stack.getItem() instanceof ClaymoreItem) {
            ClaymoreItem.parryChargePercent = (float) MathHelper.clamp(this.getParryPercent(), 0.0, 1.0);
            this.sync();
        }
    }

    @Override
    public void serverTick() {
        this.tick();
    }

    @Override
    public void clientTick() {
        this.tick();
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.blocking = tag.getBoolean("isBlocking");
        this.parryCharge = tag.getInt("parryCharge");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putBoolean("isBlocking", this.blocking);
        tag.putInt("parryCharge", this.parryCharge);
    }
}

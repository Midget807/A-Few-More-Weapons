package net.midget807.afmweapons.cca.afmw;

public class LanceComponent /*implements AutoSyncedComponent, CommonTickingComponent*/ {
    /*private final PlayerEntity player;
    private boolean ridingHorse = false;
    private final EntityAttributeModifier ATTACK_ATTRIBUTE_MODIFIER = new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", 2, EntityAttributeModifier.Operation.MULTIPLY_BASE);
    protected static final UUID ATTACK_DAMAGE_MODIFIER_ID = UUID.fromString("4f9dbf1b-99c7-4578-ba1c-bf05e573d82c");

    public LanceComponent(PlayerEntity player) {
        this.player = player;
    }
    public static LanceComponent get(@NotNull PlayerEntity player) {
        return ModComponents.LANCE_COMPONENT.get(player);
    }
    private void sync() {
        ModComponents.LANCE_COMPONENT.sync(this.player);
    }
    public boolean isRidingHorse() {
        if (!this.player.getWorld().isClient && (!this.player.getMainHandStack().isIn(ModItemTagProvider.LANCES))) {
            this.setRidingHorse(false);
        }
        return this.ridingHorse;
    }

    public void setRidingHorse(boolean ridingHorse) {
        this.ridingHorse = ridingHorse;
        this.sync();
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.ridingHorse = tag.getBoolean("isRidingHorse");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putBoolean("isRidingHorse", this.ridingHorse);
    }

    *//*@Override
    public void serverTick() {
        if (this.isRidingHorse()) {
            this.player.getMainHandStack().addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", player.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE) + 1, EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.MAINHAND);
        }
        CommonTickingComponent.super.serverTick();
    }*//*

    @Override
    public void tick() {
        if (!player.hasVehicle() && isRidingHorse()) {
            this.setRidingHorse(false);
        }
        EntityAttributeInstance attackDamageInstance = player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        if (this.isRidingHorse()) {
            if (attackDamageInstance != null && !attackDamageInstance.hasModifier(ATTACK_ATTRIBUTE_MODIFIER)) {
                attackDamageInstance.addTemporaryModifier(ATTACK_ATTRIBUTE_MODIFIER);
            }
        } else {
            if (attackDamageInstance != null && attackDamageInstance.hasModifier(ATTACK_ATTRIBUTE_MODIFIER)) {
                attackDamageInstance.removeModifier(ATTACK_DAMAGE_MODIFIER_ID);
            }
        }
    }*/
}
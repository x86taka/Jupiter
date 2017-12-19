package cn.nukkit.item.enchantment;

import java.util.Random;

import cn.nukkit.item.Item;

/**
 * author: MagicDroidX
 * Nukkit Project
 */
public class EnchantmentDurability extends Enchantment {

    protected EnchantmentDurability() {
        super(ID_DURABILITY, "durability", 5, EnchantmentType.BREAKABLE);
    }

    @Override
    public int getMinEnchantAbility(int level) {
        return 5 + (level - 1) * 8;
    }

    @Override
    public int getMaxEnchantAbility(int level) {
        return this.getMinEnchantAbility(level) + 50;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean isCompatibleWith(Enchantment enchantment) {
        return super.isCompatibleWith(enchantment) && enchantment.id != ID_FORTUNE_DIGGING;
    }

    @Override
    public boolean canEnchant(Item item) {
        return item.getMaxDurability() >= 0 || super.canEnchant(item);
    }

    public static boolean negateDamage(Item item, int level, Random random) {
        return !(item.isArmor() && random.nextFloat() < 0.6f) && random.nextInt(level + 1) > 0;
    }

    public int getRepairCost(boolean isBook) {
        if (isBook) {
            return 1 * this.getLevel();
        } else {
            return 2 * this.getLevel();
        }
    }

}

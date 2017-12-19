package cn.nukkit.item.enchantment;

/**
 * @author tedo0627
 */

public class EnchantmentFrostWalker extends Enchantment{

    protected EnchantmentFrostWalker() {
        super(ID_FROST_WALKER, "frostwalker", 2, EnchantmentType.ARMOR_FEET);
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    public int getRepairCost(boolean isBook) {
        if (isBook) {
            return 2 * this.getLevel();
        } else {
            return 4 * this.getLevel();
        }
    }

}

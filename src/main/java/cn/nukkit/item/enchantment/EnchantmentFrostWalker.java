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
}

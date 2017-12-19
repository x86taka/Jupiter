package cn.nukkit.item.enchantment;

/**
 * @author tedo0627
 */

public class EnchantmentMending extends Enchantment{

    protected EnchantmentMending() {
        super(ID_MENDING, "mending", 2, EnchantmentType.ALL);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    public int getRepairCost(boolean isBook) {
        if (isBook) {
            return 2 * this.getLevel();
        } else {
            return 4 * this.getLevel();
        }
    }

}

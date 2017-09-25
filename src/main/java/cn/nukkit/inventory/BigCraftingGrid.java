package cn.nukkit.inventory;

public class BigCraftingGrid extends CraftingGrid {

    public BigCraftingGrid(InventoryHolder holder) {
        super(holder);
    }

    @Override
    public int getSize() {
        return 9;
    }
}
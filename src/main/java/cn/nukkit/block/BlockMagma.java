package cn.nukkit.block;

import cn.nukkit.item.Item;
import cn.nukkit.item.ItemTool;

public class BlockMagma extends BlockSolid {

    public BlockMagma() {
        this(0);
    }

    public BlockMagma(int meta) {
        super(meta);
    }

    @Override
    public String getName() {
        return "Magma Block";
    }

    @Override
    public int getId() {
        return MAGMA_BLOCK;
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_PICKAXE;
    }

    @Override
    public double getResistance() {
        return 2.5;
    }

    @Override
    public Item[] getDrops(Item item) {
        if (item.isPickaxe() && item.getTier() >= ItemTool.TIER_WOODEN) {
            return new Item[]{
                    toItem()
            };
        } else {
            return new Item[0];
        }
    }
}

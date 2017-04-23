package cn.nukkit.block;

import cn.nukkit.item.Item;
import cn.nukkit.item.ItemTool;

public class BlockStickyPiston extends BlockSolid{


    public BlockStickyPiston(int meta) {
        super(meta);
    }

    public BlockStickyPiston() {
        this(0);
    }

    @Override
    public String getName() {
        return "Sticky Piston";
    }

    @Override
    public int getId() {
        return STICKY_PISTON;
    }

    @Override
    public double getHardness() {
        return 1.5;
    }

    @Override
    public double getResistance() {
        return 10;
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_PICKAXE;
    }

    @Override
    public int[][] getDrops(Item item) {
        return new int[][]{
                {Item.STICKY_PISTON, 0, 1}
        };
    }


}

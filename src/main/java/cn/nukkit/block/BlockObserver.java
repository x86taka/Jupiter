package cn.nukkit.block;

import cn.nukkit.item.ItemTool;

/**
 * @author Megapix96
 */
public class BlockObserver extends BlockSolid {

    public BlockObserver() {
        this(0);
    }

    public BlockObserver(int meta) {
        super(meta);
    }

    @Override
    public String getName() {
        return "Observer";
    }

    @Override
    public int getId() {
        return OBSERVER;
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_PICKAXE;
    }
}

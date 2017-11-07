package cn.nukkit.block;

import cn.nukkit.item.Item;

public class BlockGlassPaneStained extends BlockGlassPane {

    public BlockGlassPaneStained() {
        this(0);
    }

    public BlockGlassPaneStained(int meta) {
        super(meta);
    }

    @Override
    public String getName() {
        return "Glass Pane Stained";
    }

    @Override
    public int getId() {
        return STAINED_GLASS_PANE;
    }

    @Override
    public Item[] getDrops(Item item) {
        return item.isSilkTouch() ? new Item[]{this.toItem()} : new Item[0];
    }
}

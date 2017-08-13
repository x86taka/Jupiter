package cn.nukkit.block;

import cn.nukkit.item.Item;
import cn.nukkit.utils.BlockColor;

/**
 * Created on 2015/11/22 by xtypr.
 * Package cn.nukkit.block in project Nukkit .
 */
public class BlockPodzol extends BlockDirt {

    public BlockPodzol() {
        this(0);
    }

    public BlockPodzol(int meta) {
        super(0);
    }

    @Override
    public int getId() {
        return PODZOL;
    }

    @Override
    public String getName() {
        return "Podzol";
    }

    @Override
    public Item[] getDrops(Item item) {
        return new Item[]{
                item.isSilkTouch() ? this.toItem() : Block.get(Block.DIRT).toItem()
        };
    }
    
    @Override
    public BlockColor getColor(){
    	return BlockColor.DIRT_BLOCK_COLOR;
    }
}

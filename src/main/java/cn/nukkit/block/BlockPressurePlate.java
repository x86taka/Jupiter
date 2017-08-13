package cn.nukkit.block;

import cn.nukkit.utils.BlockColor;

/**
 * Created by Snake1999 on 2016/1/11.
 * Package cn.nukkit.block in project nukkit
 */
public abstract class BlockPressurePlate extends BlockTransparent {

    protected BlockPressurePlate() {
        this(0);
    }

    protected BlockPressurePlate(int meta) {
        super(meta);
    }

    @Override
    public boolean canPassThrough() {
        return true;
    }
    
    @Override
    public BlockColor getColor(){
    	return BlockColor.STONE_BLOCK_COLOR;
    }

    //todo redstone here?
    //todo bounding box
}

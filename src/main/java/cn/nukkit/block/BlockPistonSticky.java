package cn.nukkit.block;

import cn.nukkit.utils.BlockColor;

/**
 * @author CreeperFace
 */
public class BlockPistonSticky extends BlockPistonBase {

    public BlockPistonSticky() {
        this(0);
    }

    public BlockPistonSticky(int meta) {
        super(meta);
        this.sticky = true;
    }
    
    @Override
    public BlockColor getColor(){
    	return BlockColor.STONE_BLOCK_COLOR;
    }

    @Override
    public int getId() {
        return STICKY_PISTON;
    }

    @Override
    public String getName() {
        return "Sticky Piston";
    }
}

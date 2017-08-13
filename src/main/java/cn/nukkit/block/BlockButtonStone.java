
package cn.nukkit.block;

import cn.nukkit.item.ItemTool;
import cn.nukkit.utils.BlockColor;

/**
 * Created by CreeperFace on 27. 11. 2016.
 */
public class BlockButtonStone extends BlockButton {

    public BlockButtonStone() {
        this(0);
    }

    public BlockButtonStone(int meta) {
        super(meta);
    }

    @Override
    public int getId() {
        return STONE_BUTTON;
    }

    @Override
    public String getName() {
        return "Stone Button";
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_PICKAXE;
    }
    
    @Override
    public BlockColor getColor(){
    	return BlockColor.STONE_BLOCK_COLOR;
    }
}

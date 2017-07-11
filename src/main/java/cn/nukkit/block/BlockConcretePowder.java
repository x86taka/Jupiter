package cn.nukkit.block;

import cn.nukkit.item.ItemTool;
import cn.nukkit.utils.BlockColor;
import cn.nukkit.utils.DyeColor;

/**
 * Created by CreeperFace on 2.6.2017.
 */
public class BlockConcretePowder extends BlockFallable {

    public BlockConcretePowder() {
        this(0);
    }

    public BlockConcretePowder(int meta) {
        super(meta);
    }
    
    public BlockConcretePowder(DyeColor dyeColor) {
        this(dyeColor.getDyedData());
    }

    @Override
    public int getId() {
        return CONCRETE_POWDER;
    }

    @Override
    public String getName() {
    	return getDyeColor().getName() + " Concrete Powder";
    }

    @Override
    public double getResistance() {
        return 2.5;
    }

    @Override
    public double getHardness() {
        return 0.5;
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_SHOVEL;
    }
    
    @Override
    public BlockColor getColor() {
        return DyeColor.getByDyedData(meta).getColor();
    }
    
    public DyeColor getDyeColor() {
        return DyeColor.getByDyedData(meta);
    }
}

package cn.nukkit.block;

import cn.nukkit.item.Item;
import cn.nukkit.item.ItemTool;
import cn.nukkit.utils.DyeColor;
import cn.nukkit.utils.BlockColor;

/**
 * Created by CreeperFace on 2.6.2017.
 */
public class BlockConcrete extends BlockSolid {

    public BlockConcrete() {
        this(0);
    }

    public BlockConcrete(int meta) {
        super(meta);
    }
    
    public BlockConcrete(DyeColor dyeColor) {
        this(dyeColor.getBlockColorData());
    }

    @Override
    public int getId() {
        return CONCRETE;
    }

    @Override
    public double getResistance() {
        return 9;
    }

    @Override
    public double getHardness() {
        return 1.8;
    }

    @Override
    public String getName() {
    	return getDyeColor().getName() + " Concrete";
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_PICKAXE;
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
    
    @Override
    public BlockColor getColor() {
        return DyeColor.getByBlockColorData(meta).getColor();
    }
    
    public DyeColor getDyeColor() {
        return DyeColor.getByBlockColorData(meta);
    }
}

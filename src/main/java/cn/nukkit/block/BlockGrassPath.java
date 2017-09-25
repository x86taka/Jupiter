package cn.nukkit.block;

import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBlock;
import cn.nukkit.item.ItemTool;
import cn.nukkit.math.AxisAlignedBB;
import cn.nukkit.utils.BlockColor;

/**
 * Created on 2015/11/22 by xtypr.
 * Package cn.nukkit.block in project Nukkit .
 */
public class BlockGrassPath extends BlockGrass {

    public BlockGrassPath() {
        this(0);
    }

    public BlockGrassPath(int meta) {
        super(0);
    }

    @Override
    public int getId() {
        return GRASS_PATH;
    }

    @Override
    public double getResistance() {
        return 3;
    }

    @Override
    public double getHardness() {
        return 0.6;
    }

    @Override
    public String getName() {
        return "Grass Path";
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_SHOVEL;
    }

    @Override
    protected AxisAlignedBB recalculateBoundingBox() {
        return new AxisAlignedBB(
                this.x,
                this.y,
                this.z,
                this.x + 1,
                this.y + 0.9375,
                this.z + 1
        );
    }

    @Override
    public Item[] getDrops(Item item) {
        return new Item[]{
                item.isSilkTouch() ? this.toItem() : new ItemBlock(new BlockDirt())
        };
    }

    @Override
    public BlockColor getColor() {
        return BlockColor.GRASS_BLOCK_COLOR;
    }

}

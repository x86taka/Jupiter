package cn.nukkit.block;

import cn.nukkit.item.ItemTool;

public class BlockBone extends BlockSolid {

    public BlockBone() {
        this(0);
    }

    public BlockBone(int meta) {
        super(meta);
    }

    @Override
    public String getName() {
        return "Bone Block";
    }

    @Override
    public int getId() {
        return BONE_BLOCK;
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_AXE;
    }
}

package cn.nukkit.block;

public class BlockIceFrosted extends BlockSolid{

    public BlockIceFrosted() {
        this(0);
    }

    public BlockIceFrosted(int meta) {
        super(meta);
    }

    @Override
    public String getName() {
        return "Ice Frosted";
    }

    @Override
    public int getId() {
        return ICE_FROSTED;
    }
}

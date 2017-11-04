package cn.nukkit.block;

public class BlockMagma extends BlockSolid{

    public BlockMagma() {
        this(0);
    }

    public BlockMagma(int meta) {
        super(meta);
    }

    @Override
    public String getName() {
        return "Magma Block";
    }

    @Override
    public int getId() {
        return MAGMA_BLOCK;
    }
}

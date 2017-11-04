package cn.nukkit.block;

public class BlockNetherWartBlock extends BlockSolid{

    public BlockNetherWartBlock() {
        this(0);
    }

    public BlockNetherWartBlock(int meta) {
        super(meta);
    }

	@Override
	public String getName() {
		return "Nether Wart Block";
	}

	@Override
	public int getId() {
		return NETHER_WART_BLOCK2;
	}
}

package cn.nukkit.block;

public class BlockRedNetherBricks extends BlockSolid{

    public BlockRedNetherBricks() {
        this(0);
    }

    public BlockRedNetherBricks(int meta) {
        super(meta);
    }

	@Override
	public String getName() {
		return "Red Nether Bricks";
	}

	@Override
	public int getId() {
		return RED_NETHER_BRICKS;
	}
}
